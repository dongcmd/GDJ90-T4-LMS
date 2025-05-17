package controllers;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;

import gdu.mskim.MskimRequestMapping;
import gdu.mskim.RequestMapping;
import models.boards.Article;
import models.boards.ArticleDao;
import models.boards.BoardDao;
import models.boards.Comment;
import models.boards.CommentDao;
import models.classes.Reg_classDao;
import models.users.User;
import models.users.UserDao;

//이동원
@WebServlet(urlPatterns = {"/board/*"}
	, initParams = {@WebInitParam(name = "view", value="/views/")})
public class BoardController extends MskimRequestMapping {
	private Reg_classDao reg_classDao = new Reg_classDao();
	private BoardDao boardDao = new BoardDao();
	private ArticleDao artiDao = new ArticleDao();
	private CommentDao commDao = new CommentDao();
	private UserDao userDao = new UserDao();
	
	@RequestMapping("board")
	public String board(HttpServletRequest req,
			HttpServletResponse res) {
		int pageNum = 1;
		try { pageNum = Integer.parseInt(req.getParameter("pageNum"));
		} catch ( NumberFormatException e) {}
		
		String board_id = req.getParameter("board_id");
		if(board_id == null || board_id.trim().equals("")) {
			board_id = "9999"; }
		
		String column = req.getParameter("column");
		String keyword = req.getParameter("keyword");
		if(column == null || column.trim().equals("") 
				|| keyword == null || keyword.trim().equals("")) {
			column = null;
			keyword = null;
		}
		
		int limit = 10;
		int artiCount = artiDao.count(board_id, column, keyword);
		List<Article> artiList = artiDao.list(board_id, pageNum, limit, column, keyword);
		int maxPage = (int)Math.ceil(1.0 * artiCount/limit);
		int startPage = ((int)Math.ceil(pageNum/10.0) - 1) * 10 + 1;
		int endPage = Math.min(startPage + 9, maxPage);
		if(endPage > maxPage) endPage = maxPage;
		
		String board_name = boardDao.selectName(board_id);
		req.setAttribute("board_id", board_id);
		req.setAttribute("board_name", board_name); // 게시판명
		req.setAttribute("artiCount", artiCount); // 글 개수
		req.setAttribute("artiList", artiList); // 글 List
		req.setAttribute("pageNum", pageNum); // 페이지번호
		req.setAttribute("startPage", startPage); // 시작 페이지
		req.setAttribute("endPage", endPage); // 마지막 페이지
		req.setAttribute("maxPage", maxPage); // 최대 페이지
		int artiIndex = artiCount - (pageNum - 1) * limit;
		req.setAttribute("artiIndex", artiIndex);
		
		String user_no = (String)req.getAttribute("user_no");
		
		req.setAttribute("class1", 3);
		return "board/board";
	}
	
	@RequestMapping("article")
	public String article(HttpServletRequest req,
			HttpServletResponse res) {
		int arti_no = Integer.parseInt(req.getParameter("arti_no"));
		Article arti = artiDao.selectOne(arti_no);
		
//		String user_no = (String)req.getSession().getAttribute("user_no"); 수정필요
		String user_no = "999";
		User login = userDao.selectOne(user_no);
		
		List<Comment> commList = commDao.list(arti_no);
		
		req.setAttribute("arti", arti);
		req.setAttribute("login", login);
		req.setAttribute("commList", commList);
		req.setAttribute("board_id", arti.getBoard_id());
		return "board/article";
	}
	
	@RequestMapping("writeComment")
	public String writeComment(HttpServletRequest req,
			HttpServletResponse res) {
		Comment comm = new Comment();
		comm.setArti_no(Integer.parseInt(req.getParameter("arti_no")));
		comm.setUser_no((String)req.getSession().getAttribute("user_no"));
		comm.setComm_content((String)req.getParameter("comm_content"));
		
		if(commDao.insert(comm)) {
			return "redirect:article?arti_no="+comm.getArti_no();
		}
		req.setAttribute("msg", "댓글 등록시 오류");
		req.setAttribute("url", "board/article?arti_no="+comm.getArti_no());
		return "alert";
	}
	@RequestMapping("writeForm")
	public String writeForm(HttpServletRequest req,
			HttpServletResponse res) {
		String board_id = req.getParameter("board_id");
		String board_name = boardDao.selectName(board_id);
		req.setAttribute("board_id", board_id); // 게시판명
		req.setAttribute("board_name", board_name); // 게시판명
		return "board/writeForm";
	}
	@RequestMapping("write")
	public String write(HttpServletRequest req,
			HttpServletResponse res) {
		String path = req.getServletContext().getRealPath("/") +"../files";
		File f = new File(path);
		if(!f.exists()) f.mkdirs();
		int size = 5*1024*1024;
		MultipartRequest multi = null;
		try { multi = new MultipartRequest(req, path, size, "utf-8");
		} catch(IOException e) { e.printStackTrace(); }
		
		Article arti = new Article();
		String board_id = multi.getParameter("board_id");
		String arti_title = multi.getParameter("title");
		String arti_content = multi.getParameter("content");
		String user_no = (String)req.getSession().getAttribute("login");
		
		arti.setBoard_id(board_id);
		arti.setArti_title(arti_title);
		arti.setArti_content(arti_content);
		arti.setUser_no(user_no);
		
		if(artiDao.insert(arti)) {
			return "redirect:board?board_id="+board_id;
		}
		req.setAttribute("msg", "게시물 등록 실패");
		req.setAttribute("url", "writeForm?board_id="+board_id);
		return "alert";
	}
	@RequestMapping("deleteForm")
	public String deleteForm(HttpServletRequest req,
			HttpServletResponse res) {
		req.setAttribute("board_id", 
			artiDao.getBoard_id(
				req.getParameter("arti_no"))
		);
		return "board/deleteForm";
	}
	
	@RequestMapping("delete")
	public String delete(HttpServletRequest req,
			HttpServletResponse res) {
		String arti_no = req.getParameter("arti_no");
		String user_no = (String)req.getSession().getAttribute("login");
		String password = req.getParameter("password");
		String board_id = artiDao.getBoard_id(arti_no);
		
		if(userDao.pwCheck(user_no, password)) {
			if(artiDao.delete(arti_no)) {
				req.setAttribute("msg", arti_no + "번 게시글을 삭제했습니다.");
				req.setAttribute("url", "board?board_id="+board_id);
				req.setAttribute("close", true);
				return "alert";
			}
		}
		req.setAttribute("msg", "게시글 삭제 실패");
		req.setAttribute("url", "board/article?arti_no="+arti_no);
		req.setAttribute("close", true);
		return "alert";
	}
	
}

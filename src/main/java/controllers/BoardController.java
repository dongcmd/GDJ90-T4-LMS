package controllers;

import java.util.List;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import gdu.mskim.MskimRequestMapping;
import gdu.mskim.RequestMapping;
import models.boards.Article;
import models.boards.ArticleDao;
import models.boards.BoardDao;
import models.boards.CommentDao;
import models.classes.Reg_classDao;

//이동원
@WebServlet(urlPatterns = {"/board/*"}
	, initParams = {@WebInitParam(name = "view", value="/views/")})
public class BoardController extends MskimRequestMapping {
	private Reg_classDao reg_classDao = new Reg_classDao();
	private BoardDao boardDao = new BoardDao();
	private ArticleDao artiDao = new ArticleDao();
	private CommentDao commDao = new CommentDao();
	
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
		req.setAttribute("arti", arti);
		return "board/article";
	}
}

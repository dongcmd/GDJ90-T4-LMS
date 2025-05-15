package controller;

import java.util.List;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import gdu.mskim.MskimRequestMapping;
import gdu.mskim.RequestMapping;
import models.boards.Article;
import models.boards.ArticlesDao;
import models.boards.BoardDao;
import models.boards.CommentsDao;

//이동원
@WebServlet(urlPatterns = {"/board/*"}
	, initParams = {@WebInitParam(name = "view", value="/views/")})
public class BoardController extends MskimRequestMapping {
	private BoardDao boardDao = new BoardDao();
	private ArticlesDao artiDao = new ArticlesDao();
	private CommentsDao commDao = new CommentsDao();
	
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
		int maxpage = (int)Math.ceil(1.0 * artiCount/limit);
		int startpage = ((int)Math.ceil(pageNum/10.0) - 1) * 10 + 1;
		int endpage = Math.min(startpage + 9, maxpage);
		if(endpage > maxpage) endpage = maxpage;
		
		String board_name = boardDao.selectName(board_id);
		req.setAttribute("board_name", board_name);
		req.setAttribute("artiCount", artiCount);
		req.setAttribute("artiList", artiList);
		req.setAttribute("pageNum", pageNum);
		req.setAttribute("startpage", startpage);
		req.setAttribute("endpage", endpage);
		req.setAttribute("maxpage", maxpage);
		int artiIndex = artiCount - (pageNum - 1) * limit;
		req.setAttribute("artiIndex", artiIndex);
		return "board";
	}
}

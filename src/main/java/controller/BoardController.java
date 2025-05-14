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
import models.boards.Board_listDao;
import models.boards.CommentsDao;

//이동원
@WebServlet(urlPatterns = {"/board.jsp"}
	, initParams = {@WebInitParam(name = "view", value="/views/")})
public class BoardController extends MskimRequestMapping {
	private Board_listDao boardDao = new Board_listDao();
	private ArticlesDao artiDao = new ArticlesDao();
	private CommentsDao commDao = new CommentsDao();
	
	@RequestMapping("board")
	public String board(HttpServletRequest req,
			HttpServletResponse res) {
		int pageNum = 1;
		try { pageNum = Integer.parseInt(req.getParameter("pageNum"));
		} catch ( NumberFormatException e) {}
		
		String boardid = req.getParameter("boardid");
		if(boardid == null || boardid.trim().equals("")) {
			boardid = "1"; }
		
		String column = req.getParameter("column");
		String keyword = req.getParameter("keyword");
		if(column == null || column.trim().equals("") 
				|| keyword == null || keyword.trim().equals("")) {
			column = null;
			keyword = null;
		}
		
		int limit = 10;
		int artiCount = artiDao.count(boardid, column, keyword);
		List<Article> artiList = artiDao.list(boardid, pageNum, limit, column, keyword);
		int maxpage = (int)Math.ceil(1.0 * artiCount/limit);
		int startpage = ((int)Math.ceil(pageNum/10.0) - 1) * 10 + 1;
		int endpage = Math.min(startpage + 9, maxpage);
		if(endpage > maxpage) endpage = maxpage;
				
		String boardname = boardDao.selectOne(boardid);
		req.getSession().setAttribute("boardid", boardid);
		req.getSession().setAttribute("boardname", boardname);
		req.getSession().setAttribute("artiCount", boardname);
		req.getSession().setAttribute("pageNum", pageNum);
		req.getSession().setAttribute("artiList", artiList);
		req.getSession().setAttribute("startpage", startpage);
		req.getSession().setAttribute("endpage", endpage);
		req.getSession().setAttribute("maxpage", maxpage);
		int boardNum = artiCount - (pageNum - 1) * limit;
		return "board";
	}
}

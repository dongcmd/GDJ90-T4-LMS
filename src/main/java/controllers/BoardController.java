package controllers;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import gdu.mskim.MskimRequestMapping;
import gdu.mskim.RequestMapping;
import models.boards.Article;
import models.boards.ArticleDao;
import models.boards.BoardDao;
import models.boards.Comment;
import models.boards.CommentDao;
import models.classes.Class1;
import models.classes.Class1Dao;
import models.classes.Reg_classDao;
import models.classes.Student;
import models.users.User;
import models.users.UserDao;

//이동원
@WebServlet(urlPatterns = {"/board/*"} , initParams = {@WebInitParam(name = "view", value="/views/")})
public class BoardController extends MskimRequestMapping {
	private BoardDao boardDao = new BoardDao();
	private ArticleDao artiDao = new ArticleDao();
	private CommentDao commDao = new CommentDao();
	private UserDao userDao = new UserDao();
	private UserController uc = new UserController();
	
	public void setLMS(HttpServletRequest req, String board_id) {
		if(board_id.equals("9999")) { req.setAttribute("lms", "main"); // mainLMS
		} else if(board_id.equals("1000") || board_id.equals("2000") || board_id.equals("3000")) {
			req.setAttribute("lms", "dept"); // deptLMS
		} else { req.setAttribute("lms", "class"); } // classLMS
	}
	
	@RequestMapping("board")
	public String board(HttpServletRequest req,
			HttpServletResponse res) {
		String loginCheck = uc.loginIdCheck(req, res); 
		if(loginCheck != null) { return loginCheck; } // 로그인 확인
		User login = (User)req.getSession().getAttribute("login");
		Class1 class1 = (Class1)req.getSession().getAttribute("class1");
		String board_id = req.getParameter("board_id");
		System.out.println("1번 : " + board_id);
		
		if(board_id == null || board_id.trim().equals("") || board_id.trim().equals("9999")) {
			board_id = "9999"; // board_id 값이 없으면 공지사항으로 이동
			System.out.println("2번 : " + board_id);
		} else if(!board_id.equals(login.getMajor_no())) {
			System.out.println("3번 : " + board_id);
			boolean check = false;
			if(class1 != null) {
				System.out.println("4번 : " + board_id);
//				if(class1.getStudents().get(login.getUser_no()) != null) {
//수정
//					System.out.println("5번 : " + board_id);
//					check = true;
//				}
			} else if(!check) {
				System.out.println("6번 : " + board_id);
				req.setAttribute("msg", "접근 권한 없음");
				req.setAttribute("url", "../mainLMS/main");
				return "alert";
			}
		}

		System.out.println("7번 : " + board_id);
		int pageNum = 1;
		try { pageNum = Integer.parseInt(req.getParameter("pageNum"));
		} catch ( NumberFormatException e) {}
		System.out.println("8번 : " + board_id);
		
		String column = req.getParameter("column");
		String keyword = req.getParameter("keyword");
		System.out.println("9번 : " + board_id);
		if(column == null || column.trim().equals("") 
				|| keyword == null || keyword.trim().equals("")) {
			System.out.println("10번 : " + board_id);
			column = null;
			keyword = null;
		}
		System.out.println("11번 : " + board_id);
		
		int limit = 10;
		int artiCount = artiDao.count(board_id, column, keyword);
		List<Article> artiList = artiDao.list(board_id, pageNum, limit, column, keyword);
		int maxPage = (int)Math.ceil(1.0 * artiCount/limit);
		int startPage = ((int)Math.ceil(pageNum/10.0) - 1) * 10 + 1;
		int endPage = Math.min(startPage + 9, maxPage);
		if(endPage > maxPage) endPage = maxPage;
		System.out.println("12번 : " + board_id);
		
		String board_name = boardDao.selectName(board_id);
		setLMS(req, board_id); // 어떤 lms에서 접근하는지 전달
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
		System.out.println("13번 : " + board_id);
		
		return "board/board";
	}
	
	@RequestMapping("article")
	public String article(HttpServletRequest req,
			HttpServletResponse res) {
		User login = (User)req.getSession().getAttribute("login");
		int arti_no = Integer.parseInt(req.getParameter("arti_no"));
		Article arti = artiDao.selectOne(arti_no);
		List<Comment> commList = commDao.list(arti_no);
		String board_name = boardDao.selectName(arti.getBoard_id());
		setLMS(req, arti.getBoard_id());
		req.setAttribute("board_name", board_name);
		req.setAttribute("arti", arti);
		req.setAttribute("login", login);
		req.setAttribute("commList", commList);
		return "board/article";
	}
	
	@RequestMapping("writeComment")
	public String writeComment(HttpServletRequest req,
			HttpServletResponse res) {
		User login = (User)req.getSession().getAttribute("login");
		Comment comm = new Comment();
		comm.setArti_no(Integer.parseInt(req.getParameter("arti_no")));
		comm.setUser_no(login.getUser_no());
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
		setLMS(req, board_id);
		req.setAttribute("board_id", board_id); // 게시판명
		req.setAttribute("board_name", board_name); // 게시판명
		return "board/writeForm";
	}
	@RequestMapping("write")
	public String write(HttpServletRequest req, HttpServletResponse res) {
	    String path = req.getServletContext().getRealPath("/") + "files/";
	    File uploadDir = new File(path);
	    if (!uploadDir.exists()) uploadDir.mkdirs();

	    int size = 5 * 1024 * 1024; // 5MB 제한
	    Article arti = new Article();
	    User login = (User) req.getSession().getAttribute("login");

	    DiskFileItemFactory factory = new DiskFileItemFactory();
	    factory.setSizeThreshold(1024 * 1024); // 메모리에 저장할 임시 파일 크기 제한
	    factory.setRepository(uploadDir); // 임시 디렉토리

	    ServletFileUpload upload = new ServletFileUpload(factory);
	    upload.setSizeMax(size); // 전체 요청 크기 제한

	    try {
	        List<FileItem> items = upload.parseRequest(req);
	        for (FileItem item : items) {
	            if (item.isFormField()) {
	                // 일반 form 필드
	                String fieldName = item.getFieldName();
	                String value = item.getString("utf-8");
	                switch (fieldName) {
	                    case "board_id": arti.setBoard_id(value); break;
	                    case "title": arti.setArti_title(value); break;
	                    case "content": arti.setArti_content(value); break;
	                }
	            } else {
	                // 파일 업로드 처리 (원한다면)
	                String fileName = new File(item.getName()).getName();
	                if (!fileName.isEmpty()) {
	                    File uploadedFile = new File(path + fileName);
	                    item.write(uploadedFile);
	                    // 필요하다면: arti.setFilename(fileName);
	                }
	            }
	        }
	        arti.setUser_no(login.getUser_no());

	        if (artiDao.insert(arti)) {
	            return "redirect:board?board_id=" + arti.getBoard_id();
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    req.setAttribute("msg", "게시물 등록 실패");
	    req.setAttribute("url", "writeForm?board_id=" + arti.getBoard_id());
	    return "alert";
	}

	@RequestMapping("deleteForm")
	public String deleteForm(HttpServletRequest req,
			HttpServletResponse res) {
		String paramComm_no = req.getParameter("comm_no");
		String paramArti_no = req.getParameter("arti_no");
		if(paramComm_no != null) {
			int comm_no = Integer.parseInt(paramComm_no);
			Comment comm = commDao.selectOne(comm_no);
			Article arti = artiDao.selectOne(comm.getArti_no());
			setLMS(req, arti.getBoard_id());
			req.setAttribute("arti", arti);
			req.setAttribute("comm", comm);
			req.setAttribute("target", "댓글");
			return "board/deleteForm";
		}
		int arti_no = Integer.parseInt(paramArti_no);
		Article arti = artiDao.selectOne(arti_no);
		setLMS(req, arti.getBoard_id());
		req.setAttribute("arti", arti);
		req.setAttribute("target", "글");
		return "board/deleteForm";
	}
	
	@RequestMapping("delete")
	public String delete(HttpServletRequest req,
			HttpServletResponse res) {
		User login = (User)req.getSession().getAttribute("login");
		String paramComm_no = req.getParameter("comm_no");
		String paramArti_no = req.getParameter("arti_no");
		String inputPass = req.getParameter("password");
		int arti_no;
		if(userDao.pwCheck(login.getUser_no(), inputPass)) {
			if(paramComm_no != null && !paramComm_no.trim().equals("")) {
				int comm_no = Integer.parseInt(paramComm_no);
				Comment comm = commDao.selectOne(comm_no);
				if(commDao.delete(comm_no)) {
					req.setAttribute("msg", comm.getUser_name() + "님의 댓글을 삭제했습니다.");
					req.setAttribute("url", "article?arti_no="+comm.getArti_no());
					return "openeralert";
				}
			}
			arti_no = Integer.parseInt(paramArti_no);
			Article arti = artiDao.selectOne(arti_no);
			if(artiDao.delete(arti_no)) {
				req.setAttribute("msg", arti.getUser_name() + "님의 글을 삭제했습니다.");
				req.setAttribute("url", "board?board_id="+arti.getBoard_id());
				return "openeralert";
			}
		}
		req.setAttribute("msg", "삭제 실패");
		req.setAttribute("close", true);
		return "alert";
	}
	
	@RequestMapping("updateForm")
	public String updateForm(HttpServletRequest req,
			HttpServletResponse res) {
		int arti_no = Integer.parseInt(req.getParameter("arti_no"));
		Article arti = artiDao.selectOne(arti_no);
		setLMS(req, arti.getBoard_id());
		req.setAttribute("arti", arti);
		return "board/updateForm";
	}
	
	@RequestMapping("update")
	public String update(HttpServletRequest req, HttpServletResponse res) {
	    String path = req.getServletContext().getRealPath("/") + "files/";
	    File uploadDir = new File(path);
	    if (!uploadDir.exists()) uploadDir.mkdirs();

	    int size = 5 * 1024 * 1024; // 5MB 제한
	    Article arti = new Article();
	    String file1 = null;
	    String file2 = null;
	    int arti_no = 0;
	    String arti_title = null;
	    String arti_content = null;

	    DiskFileItemFactory factory = new DiskFileItemFactory();
	    factory.setSizeThreshold(1024 * 1024);
	    factory.setRepository(uploadDir);

	    ServletFileUpload upload = new ServletFileUpload(factory);
	    upload.setSizeMax(size);

	    try {
	        List<FileItem> items = upload.parseRequest(req);
	        for (FileItem item : items) {
	            if (item.isFormField()) {
	                String field = item.getFieldName();
	                String value = item.getString("utf-8");
	                switch (field) {
	                    case "arti_no":
	                        arti_no = Integer.parseInt(value);
	                        break;
	                    case "title":
	                        arti_title = value;
	                        break;
	                    case "content":
	                        arti_content = value;
	                        break;
	                    case "file2":
	                        file2 = value; // 이전 파일명
	                        break;
	                }
	            } else {
	                String fileName = new File(item.getName()).getName();
	                if (!fileName.isEmpty()) {
	                    File uploadedFile = new File(path + fileName);
	                    item.write(uploadedFile);
	                    file1 = fileName; // 새 파일 업로드
	                }
	            }
	        }

	        arti = artiDao.selectOne(arti_no);
	        arti.setArti_title(arti_title);
	        arti.setArti_content(arti_content);

	        // 새 첨부파일이 있으면 적용, 없으면 기존 파일 유지
	        if (file1 != null && !file1.isEmpty()) {
	            arti.setFile(file1);
	        } else {
	            arti.setFile(file2);
	        }

	        if (artiDao.update(arti)) {
	            return "redirect:board?board_id=" + arti.getBoard_id();
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    req.setAttribute("msg", "게시물 등록 실패");
	    req.setAttribute("url", "writeForm?board_id=" + (arti.getBoard_id() != null ? arti.getBoard_id() : ""));
	    return "alert";
	}

}

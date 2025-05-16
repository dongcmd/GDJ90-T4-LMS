package controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;

import gdu.mskim.MSLogin;
import gdu.mskim.MskimRequestMapping;
import gdu.mskim.RequestMapping;
import models.users.User;
import models.users.UserDao;
/*
	controllers/UserController.java - 김기흔
*/
@WebServlet(urlPatterns = {"/users/*"},
initParams = {@WebInitParam(name="view",value="/views/")})

public class UserController extends MskimRequestMapping{
	private UserDao dao = new UserDao();
	
	
	// 로그인 =================================================================
	@RequestMapping("login")
	public String login(HttpServletRequest request, HttpServletResponse response) {			
		// 파라미터 조회
		String user_no = request.getParameter("user_no");
		String password = request.getParameter("password");
		User user = dao.selectOne(user_no);
		String msg = null;
		String url = null;
		
		if(user == null){ // 아이디가 없는 경우
			msg = "아이디를 확인하세요";
			url = "loginForm";
		} else if (!password.equals(user.getPassword())) {
			msg = "비밀번호를 확인하세요";
			url = "loginForm";
		} else { 
			request.getSession().setAttribute("user_name", user.getUser_name());
			request.getSession().setAttribute("password", user.getPassword());
			request.getSession().setAttribute("user_no", user_no);
			msg = user.getUser_name() + "님 반갑습니다.";
			url = "main";
		}
		request.setAttribute("msg", msg);
		request.setAttribute("url", url);
		return "alert";
	}
	
	// 아이디 찾기=========================================================================
	@RequestMapping("id")
	public String id(HttpServletRequest request, HttpServletResponse response) {
		String user_name = request.getParameter("user_name");
		String email = request.getParameter("email");
			
		String user_no = dao.userSearch(user_name, email);	
		  if (user_no == null) {
		        request.setAttribute("msg", "아이디가 없습니다.");
		        request.setAttribute("url", "idForm");
		        System.out.println("null");
		        return "alert";
		    } else if ("999".equals(user_no)) {  // ✅ null-safe 방식으로 비교
		        request.setAttribute("msg", "조회할 수 없는 계정입니다.");
		        request.setAttribute("url", "idForm");
		        System.out.println("admin");
		        return "alert";
		    } else {
		        request.setAttribute("user_no", user_no);
		        System.out.println("not null");
		        return "users/id";
		    }
	}
	// 비밀번호 초기화 =================================================================
	@RequestMapping("resetpw")
	public String resetpw(HttpServletRequest request, HttpServletResponse response) {
		String user_no = request.getParameter("user_no");
		String email = request.getParameter("email");
		String randpw = null;
			
		//존재하는 계정인지 확인
		String isUser_no = dao.isUser(user_no);	
			
		if(isUser_no != null && isUser_no.equals(user_no)) {
			Random rand = new Random();
			int x = 100000 + rand.nextInt(900000);
			randpw = String.valueOf(x);
			int pass = dao.pwReset(user_no, randpw);
			if(pass == 1) {
				request.setAttribute("msg", "비밀번호:"+ randpw);
				request.setAttribute("close", true);
				return "alert";
			} else {
				request.setAttribute("msg", "오류발생");
				return "users/resetPwForm";
			}
			
		}
		request.setAttribute("msg", "비밀번호를 찾을 수 없습니다.");
		request.setAttribute("url", "resetPwForm");
		return "alert";
	}
	
	// 마이페이지 =================================================================
	@RequestMapping("main")
	public String main(HttpServletRequest request, HttpServletResponse response) { 
		String user_no = request.getParameter("user_no");
		String login = (String)request.getSession().getAttribute("user_no");
		// 로그아웃 상태
		if(login.equals(null) || login.trim().equals("")) {
			request.setAttribute("msg", "로그인하세요");
			request.setAttribute("url", "loginForm");
			return "alert";
		}
		// 로그인 상태
		return "users/main"; // forward 방식 
	}
	
	// 로그아웃 =================================================================
	@RequestMapping("logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		request.getSession().invalidate();
		request.setAttribute("msg", "로그아웃되었습니다");
		request.setAttribute("url", "loginForm");
		return "alert";
	}
	// 관리자 계정 체크 =================================================================
	public String loginAdminCheck(HttpServletRequest request, HttpServletResponse response) {
		String login = (String) request.getSession().getAttribute("user_no");
		if(login == null) {
			request.setAttribute("msg", "로그인 하세요");
			request.setAttribute("url", "loginForm");
			return "alert";
		} else if (!login.equals("999")) {
			request.setAttribute("msg", "관리자만 가능한 업무입니다.");
			request.setAttribute("url", "main");
			return "alert";
		}
		return null;
	}
	
	// 로그인 아이디 체크 =================================================================
	public String loginIdCheck(HttpServletRequest request, HttpServletResponse response) {
		String user_no = request.getParameter("user_no");
		String login = (String)request.getSession().getAttribute("user_no");
		if(login == null) {
			request.setAttribute("msg", "로그인 하세요");
			request.setAttribute("url", "loginForm");
			return "alert";
		} else if (!login.equals("999") && !user_no.equals(login)) {
			request.setAttribute("msg", "본인만 조회 가능합니다");
			request.setAttribute("url", "main");
			return "alert";
		}
		return null; // 정상인 경우
	}
	// 로그인 아이디 체크(팝업창) =================================================================
	public String loginIdCheckPopup(HttpServletRequest request, HttpServletResponse response) {
		String login = (String) request.getSession().getAttribute("user_no");
		if(login == null || login.trim().equals("")) {
			request.setAttribute("msg", "로그인 하세요");
			request.setAttribute("url", "loginForm");
			return "alert";
		}
		return null; // 정상인 경우
	}
	
	/* 회원정보(DB 에서 가져오기) =================================================================*/
	@RequestMapping("info")
	@MSLogin("loginIdCheckPopup")
	public String info(HttpServletRequest request, HttpServletResponse response) {
		String login = (String) request.getSession().getAttribute("user_no");
		User user = dao.selectOne(login);
		request.setAttribute("user", user);  
		return "users/info";
	}
	
	// 회원정보 수정 페이지
	@RequestMapping("updateForm")
	@MSLogin("loginIdCheckPopup") // 로그인 아이디 체크
	public String updateForm(HttpServletRequest request, HttpServletResponse response) {
		String login = (String) request.getSession().getAttribute("user_no");
		User user = dao.selectOne(login);
		request.setAttribute("user", user);
		return "users/updateForm";
	}
		
	/* 회원정보 수정 =================================================================   */
	@RequestMapping("update")
	@MSLogin("loginIdCheckPopup")
	public String update(HttpServletRequest request, HttpServletResponse response) {
		//세션에서 요청객체 정보 불러오기		
		User user = new User();
		user.setUser_no(request.getParameter("user_no"));
		user.setPassword(request.getParameter("password"));
		user.setUser_name(request.getParameter("user_name"));
		user.setGender(Integer.parseInt(request.getParameter("gender")));
		user.setTel(request.getParameter("tel"));
		user.setEmail(request.getParameter("email"));
		user.setGrade(Integer.parseInt(request.getParameter("grade")));
		user.setMajor_no(request.getParameter("major_no"));
		// 비밀번호를 위한 db의 데이터 조회. : login 정보로 조회하기
		String login = (String)request.getSession().getAttribute("user_no");
		User dbUser = dao.selectOne(login);		
		String msg = "비밀번호가 틀립니다.";
		String url = "updateForm?id=" + user.getUser_no();
		
		// 비밀번호 같을시
		if(user.getPassword().equals(dbUser.getPassword())) {
			if(dao.update(user)) {
				msg = "회원정보 수정완료";
				url = "info";
			} else {
				msg = "회원정보 수정실패";
			}
		}
		request.setAttribute("msg", msg);
		request.setAttribute("url", url);
		return "alert";
	}
	
	// 비밀번호 재설정 창 =================================================================
	@RequestMapping("pwForm")
	@MSLogin("passwordLoginCheck")
	public String passwordForm(HttpServletRequest request, HttpServletResponse response) {
		String login = (String) request.getSession().getAttribute("user_no");
		// 로그아웃 상태
		if(login == null || login.trim().equals("")) {
			request.setAttribute("msg", "로그인하세요");
			request.setAttribute("url", "loginForm");
			return "openeralert";
		}
		// 로그인 상태
		return "users/pwForm";
	}
    //비밀번호 재설정 ====================================================================	
	@RequestMapping("updatepw")
	@MSLogin("passwordLoginCheck")
	public String updatepw(HttpServletRequest request, HttpServletResponse response) {
		String password = request.getParameter("password");
		String n_pass1 = request.getParameter("new_password1");
		String n_pass2 = request.getParameter("new_password2");
		
		String login = (String) request.getSession().getAttribute("user_no");
		User dbUser = dao.selectOne(login);		
		
		// 비밀번호 일치시
		if(password.equals(dbUser.getPassword())){
	    	 if(!n_pass1.equals("") && !n_pass2.equals("") && n_pass1.equals(n_pass2)) {
	    		if(dao.updatePass(login, n_pass1)) { // 비밀번호 수정성공
	    			request.setAttribute("msg", "비밀번호 수정성공.");
	    			request.setAttribute("url", "info");
	    			return "alert"; // 알림창 띄우고 스스로 닫기
	    		} else { // 비밀번호 수정실패
	    			StringBuilder sb = new StringBuilder();
	    			sb.append("alert('비밀번호 수정시 오류가 발생했습니다.');\n");
	    			sb.append("self.close();");
	    			request.setAttribute("script", sb.toString());
	    			return "dummy"; // dummy.jsp 생성
	    		}
	    	} else if (n_pass1.equals("") || n_pass1.equals("")) {
	    		request.setAttribute("msg", "새로운 비밀번호를 입력하세요");
	    		request.setAttribute("url", "pwForm");
	    		return "alert";
	    	}
	    	 else { // 비밀번호 오류
	    		request.setAttribute("msg", "새로운 비밀번호들이 일치하지 않습니다.");
	    		request.setAttribute("url", "pwForm");
	    		return "alert";
	    	} 
	    } else {
			request.setAttribute("msg", "비밀번호가 틀립니다.");
			request.setAttribute("url", "pwForm");
			return "alert";
	    }
	}
	// 비밀번호 : 로그아웃 상태시 pwForm 이동 ======================================
	public String passwordLoginCheck(HttpServletRequest request, HttpServletResponse response) {
		String login = (String) request.getSession().getAttribute("user_no");
		if(login == null || login.trim().equals("")) {
			request.setAttribute("msg", "로그인하세요");
			request.setAttribute("url", "loginForm");
			return "openeralert";
		}
		return null;
	}
	// 관리자가 아니면 접근 불가 기능=================================================
	@RequestMapping("adminForm")
	public String adminForm(HttpServletRequest request, HttpServletResponse response) {
		String login = (String) request.getSession().getAttribute("user_no");
		if(login == null || login.trim().equals("")) {
			request.setAttribute("msg", "로그인하세요");
			request.setAttribute("url", "loginForm");
			return "openeralert";
		} else if(login != "999") {
			request.setAttribute("msg", "관리자만 접근 가능합니다");
			request.setAttribute("url", "main");			
		} 
		return null;
	}
	//유저리스트 검색필터기능 ========================================================
	@RequestMapping("searchUsers")
	private void searchUsers(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        // 검색 조건 파라미터 받기
        String type = request.getParameter("type");      // e.g. user_name, user_no, email
        String keyword = request.getParameter("keyword");
        List<User> users = dao.searchUsers(type, keyword);
        request.setAttribute("users", users);
    }
	//모든유저 리스트 =================================================================
	@RequestMapping("getAllUsers")
	public String getAllUsers(HttpServletRequest request, HttpServletResponse response) {
		List<User> users = dao.getAllUsers(); 
		request.setAttribute("users", users); 
		return "users/adminForm";      
	}
	

	
	/* 회원가입 =================================================================:
	 */
	@RequestMapping("join") // http://localhost:8080/model2Study/member/join
	public String join(HttpServletRequest request, HttpServletResponse response) {
		User user = new User();
		user.setUser_no(request.getParameter("user_no"));
		user.setPassword(request.getParameter("pass"));
		user.setRole(request.getParameter("role"));
		user.setGender(Integer.parseInt(request.getParameter("gender")));
		user.setTel(request.getParameter("tel"));
		user.setEmail(request.getParameter("email"));
		user.setPicture(request.getParameter("picture"));
		
		if(dao.insert(mem)) {
			request.setAttribute("msg", mem.getName()+"님 회원 가입 되었습니다.");
			request.setAttribute("url", "loginForm");
		} else {
			request.setAttribute
			("msg", mem.getName()+"님 회원가입시 오류 발생했습니다.");
			request.setAttribute("url", "joinForm");
		}
		return "alert"; 
	}
	
	// 관리자 탈퇴 방지 =================================================================
	@RequestMapping("deleteForm")
	@MSLogin("loginIdCheck")
	public String deleteForm(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		if(id.equals("admin")) {
			request.setAttribute("msg", "관리자는 탈퇴할 수 없습니다.");
			request.setAttribute("url", "info?id=" + id);
			return "alert";
		}
		return "member/deleteForm";
	}
	
	/* 탈퇴 =================================================================
	1. 2개의 파라미터 정보 저장 (id/pass)
	2. 로그인정보 검증
		- 로그아웃 상태 : 로그인하세요. loginForm.jsp 페이지 이동
		- 본인 탈퇴가능(관리자 제외) : 본인만 탈퇴 가능합니다. main.jsp 페이지 이동
		- 관리자가 탈퇴시 불가 : 관리자는 탈퇴불가합니다.
	3. 비밀번호 검증
		- 로그인 정보로 비밀번호 검증.
		- 비밀번호 불일치 : 비밀번호 오류 메세지 출력. deletForm.jsp로 페이지 이동
	4. db에서 id에 해당하는 회원 정보 삭제
		boolean MemberDao.delete(id)
		탈퇴 성공 
			- 일반사용자 : 로그아웃 실행. 탈퇴메세지 출력, loginForm.jsp로 페이지 이동
			- 관리자    : 탈퇴메세지 출력. list.jsp로 페이지 이동 
		탈퇴 실패 : 
			- 일반사용자 : 탈퇴실패메세지 main.jsp로 페이지 이동 
			- 관리자    : 탈퇴실패메세지 list.jsp로 페이지 이동 
	*/
	@RequestMapping("delete")
	@MSLogin("loginIdCheck")
	public String delete(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		String pass = request.getParameter("pass");
		String login = (String) request.getSession().getAttribute("login");
		
		if(id.equals("admin")) {
			request.setAttribute("msg", "관리자는 탈퇴할 수 없습니다.");
			request.setAttribute("url", "info?id=" + id);
			return "alert";
		}
		Member dbMem = dao.selectOne(login);
		String msg = "비밀번호 오류입니다.";
		String url = "deleteForm?id=" + id;
		// 비밀번호가 같을시 
		if(pass.equals(dbMem.getPass())) {
			if(login.equals("admin")) {url = "list";}
			if(dao.delete(id)) {
				msg = id + "회원 탈퇴 성공";
				// 관리자가 아닐경우
				if(!login.equals("admin")) {
					request.getSession().invalidate();
					url = "loginForm";
				}
			} else {
				msg = id + "회원 탈퇴 실패";
				// 관리자가 아닐경우
				if(!login.equals("admin")) {
					url = "main";
				}
			}
		}
		request.setAttribute("msg", msg);
		request.setAttribute("url", url);
		return "alert";
	}
	
	// 리스트 =================================================================
	@RequestMapping("list")
	@MSLogin("loginAdminCheck")
	public String list(HttpServletRequest request, HttpServletResponse response) {
		// 관리자로 로그인한 경우만 실행
		List<Member> list = dao.list();
		request.setAttribute("list", list);
		return "member/list";
	}
	
	
	
	// 메일폼 =================================================================
	/*
	구글 stmp 서버를 이용하여 메일 전송하기
	1. 구글계정에 접속하여 2단계 로그인 설정하기'
	2. 앱 비밀번호 생성하기
	3. 생성된 앱 비밀번호를 메모장을 이용하여 저장하기
	qwer : uxcarnnhmurzohdo
	4. mail-1.4.7.jar, activation-1.1.1.jar 파일 /lib/폴더에 복사
	5. mail.properties 파일 /WEB-INF/ 폴더에 생성하기
	*/
	@RequestMapping("mailForm")
	@MSLogin("loginAdminCheck")
	public String mailForm(HttpServletRequest request, HttpServletResponse response) {
		// id : 메일 전송을 위한 아이디 목록
		String[] ids = request.getParameterValues("idchks");
		List<Member> list = dao.emailList(ids);
		request.setAttribute("list", list);
		return "member/mailForm";
	}
	
	
	// 구글메일 보내기 =================================================================
	@RequestMapping("mailSend")
	@MSLogin("loginAdminCheck")
	public String mailSend(HttpServletRequest request, HttpServletResponse response) {
		// 구글 아이디 : dongin971228
		String sender = request.getParameter("googleid") + "@gmail.com";
		// 구글 앱 비밀번호 : uxcarnnhmurzohdo
		String passwd = request.getParameter("googlepw");
//		passwd="yokynyalewnaktcw";
		// recipient : 테스트 <test1이메일>, 테스트2 <test2이메일>
		String recipient = request.getParameter("recipient");
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String mtype = request.getParameter("mtype");
		String result = "메일 전송시 오류가 발생했습니다.";
		Properties prop = new Properties(); // 이메일 전송을 위한 환경설정값
		try {
			String path = request.getServletContext().getRealPath("/") + "WEB-INF/mail.properties";
			FileInputStream fis = new FileInputStream(path);
			prop.load(fis); // fis가 참조하는 파일의 내용을 properties 객체의 요소로 저장
			prop.put("mail.smtp.user",sender); // 전송 이메일 주소
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// 메일전송을 위한 인증 객체
		MyAuthenticator auth = new MyAuthenticator(sender, passwd);
		// prop : 메일 전송을 위한 시스템 환경설정
		// auth : 인증객체
		// 메일 전송을 위한 연결 객체
		Session mailSession = Session.getInstance(prop, auth);
		// msg : 메일로 전송되는 데이터 객체
		MimeMessage msg = new MimeMessage(mailSession);
		List<InternetAddress> addrs = new ArrayList<InternetAddress>();
		try {
			String[] emails = recipient.split(",");
			for(String email : emails) {
				try {
					// new String(이메일주소, 인코딩코드) 
					// email.getBytes("UTF-8") : byte[] 배열
					// 8859_1 : 웹의 기본 인코딩방식
					addrs.add(new InternetAddress(new String(email.getBytes("UTF-8"),"8859_1")));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
			
			InternetAddress[] address = new InternetAddress[emails.length];
			for(int i=0; i<addrs.size(); i++) {
				address[i] = addrs.get(i);
			}
	
			InternetAddress from = new InternetAddress(sender);
			msg.setFrom(from); // 보내는 이메일 주소
			// Message.RecipientType.TO : 수신자
			// Message.RecipientType.CC : 참고인
			msg.setRecipients(Message.RecipientType.TO, address);
			msg.setSubject(title); // 제목
			msg.setSentDate(new Date()); // 전송일자
			msg.setText(content); // 내용
			// multipart : 내용, 첨부파일1~2 내용분리 (파츠개념)
			MimeMultipart multipart = new MimeMultipart();
			MimeBodyPart body = new MimeBodyPart();
			body.setContent(content,mtype); // 내용
			multipart.addBodyPart(body);
			msg.setContent(multipart); // msg 안에 내용,첨부파일 등 저장
 			Transport.send(msg); // 메일 전송
 			result = "메일 전송이 완료 되었습니다.";
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		// mailForm.jsp 에 구글ID, 구글비밀번호 각자 계정을 value 속성값을 등록
		request.setAttribute("msg", result);
		request.setAttribute("url", "list");
		return "alert";
	}
	
	// ID/PW 인증 =================================================================
	// 내부클래스 :
	// final 다른클래스의 부모 클래스가 될수 없는 클래스
	public final class MyAuthenticator extends Authenticator{
		private String id;
		private String pw;
		public MyAuthenticator(String id, String pw) {
			this.id = id;
			this.pw = pw;
		}
		protected PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(id, pw);
		}
	}
	
	/* 비밀번호 변경 =================================================================
	1. 로그인한 사용자의 비밀번호 변경만 가능 => 로그인 부분 검증
	   로그아웃 상태 : 로그인 하세요 메세지 출력 후
	   				 opener 창을 loginForm 페이지로 이동. 현재 페이지 닫기
	   				 =>  passwordLoginCheck() 메서드 기능
	2. 파라미터 저장 (pass, chgpass)
	3. 비밀번호 검증 : 현재 비밀번호로 비교
	   비밀번호 오류 : 비밀번호 오류 메세지 출력 후 현재 페이지를 passwordForm로 이동
	4. db에 비밀번호 수정
		boolen MemberDao.updatePass(id, 변경 비밀번호)
		- 수정성공 : 성공 메세지 출력 후 opener 페이지 info로 이동. 현재페이지 닫기
		- 수정실패 : 수정실패 메세지 출력 후 현재 페이지 닫기 
	*/	
	
	/* 아이디 중복체크
	1. id 파라미터
	2. id를 이용하여 db에서 조회.
	3. DB에서 조회가 안되는 경우 : 사용한 아이디 입니다. 초록색으로 화면 출력
       DB에서 조회가 되는 경우 : 사용 중인 아이디 입니다. 빨간색 화면 출력
    4. 닫기 버튼 클릭되면 화면 닫기   
	*/
	@RequestMapping("idchk")
	public String idchk(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		Member mem = dao.selectOne(id);
		String msg = null;
		boolean able = true;
		if(mem == null) {
			msg = "사용가능한 아이디 입니다.";
		} else {
			msg = "사용 중인 아이디 입니다.";
			able = false;
		}
		request.setAttribute("msg", msg);
		request.setAttribute("able", able);
		return "member/idchk";
	}
	
	@RequestMapping("picture")
	public String picture (HttpServletRequest request,
			HttpServletResponse response) {
		String path = request.getServletContext().getRealPath("")+"picture/";
		String fname = null;
		File f = new File(path); //업로드되는 폴더 정보
		if(!f.exists()) f.mkdirs(); //폴더 생성
		MultipartRequest multi = null;
		try {
				multi = new MultipartRequest(request,path, 10*1024*1024, "utf-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		fname = multi.getFilesystemName("picture"); //선택된 파일의 이름
		request.setAttribute("fname", fname);
		return "member/picture";
	}
}

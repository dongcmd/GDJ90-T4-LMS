package controller;
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


@WebServlet(urlPatterns = {"/mainLMS/*"},
initParams = {@WebInitParam(name="view",value="/view/")})
public class MainController extends MskimRequestMapping{
	
	// 메인페이지 =================================================================
	@RequestMapping("main")
	public String main(HttpServletRequest request, HttpServletResponse response) {
		
	    String uri = request.getRequestURI(); // /lms/main/main2
	    String context = request.getContextPath(); // /lms
	    String path = uri.substring(context.length() + "/mainLMS/".length()); //  main2
	    
		request.setAttribute("currentURI", request.getRequestURI());
	    return "mainLMS/" + path;
	}
}
package controllers;

import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;


/**
 * Servlet implementation class UploadImageServlet
 */
//@WebServlet("/classLMS/upload")
//@MultipartConfig 	//업로드된 파일을 처리 
public class UploadFileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    public UploadFileServlet() {
        super();
    }

    private static final String UPLOAD_DIR = "uploaded_files";
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8"); // 응답객체의 인코딩 방식을 설정(한글 안깨지게)
		//filePart : 파일의 내용
		Part filePart = request.getPart("file");
		for (Part p : request.getParts()) {
			System.out.println(p.getName() + "=====");
		}
		String fileName = getFileName(filePart); //파일의 이름 추출
		String uploadPath = //파일업로드 되는 폴더
				getServletContext().getRealPath("") + File.separator + UPLOAD_DIR;
		File uploadDir = new File(uploadPath);
		if(!uploadDir.exists()) uploadDir.mkdirs(); //폴더 생성
		//이미지 파일이 업로드된 절대 경로
		String filePath = uploadPath + File.separator + fileName;
		filePart.write(filePath); //파일 업로드. @MultipartConfig 어노테이션 필수
		String fileUrl = request.getContextPath()
				+ "/" + UPLOAD_DIR + "/" + fileName;
		response.setContentType("text/plain"); //순수문자데이터.
		response.getWriter().write(fileUrl);
	}

	private String getFileName(Part part) {
		System.out.println
		("content=displosition=>"+part.getHeader("content-disposition"));
		for(String content : part.getHeader("content-disposition").split(";")) {
			if(content.trim().startsWith("filename")) {
				return content.substring
						(content.indexOf("=") + 1).trim().replace("\"","");
			}
		}
		return "unknown.dat";
	}
}
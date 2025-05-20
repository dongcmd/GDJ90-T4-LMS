package controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import models.classes.AsDao;
import models.classes.Assignment;
import models.classes.Class1;
import models.classes.Reg_classDao;
import models.classes.Student;
import models.classes.SubAsDao;

//이동원
@WebServlet(urlPatterns = {"/classLMS/upload_asCSV",
		"/classLMS/download_asXLSX"})
public class CSVxlsxServlet extends HttpServlet {
	AsDao asDao = new AsDao();
	SubAsDao subAsDao = new SubAsDao();
	Reg_classDao rcDao = new Reg_classDao(); 
	
	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		// multipart/form-data 형식인지 확인 (파일 업로드 요청인지 체크)
	    boolean isMultipart = ServletFileUpload.isMultipartContent(request);
	    if (!isMultipart) {
	    	request.setAttribute("msg", "파일 형식이 올바르지 않습니다.");
	        request.setAttribute("url", "classLMS/manageAs"); // 되돌아갈 페이지 경로
	        request.getRequestDispatcher("alert").forward(request, response);
	        return;
	    }
	    
	    Class1 class1 = (Class1)request.getSession().getAttribute("class1");
		Map<String, Student> students = class1.getStudents();
		Map<String, Assignment> assignments = class1.getAssignments();
		Assignment as = assignments.get(request.getAttribute("as_no"));
		int as_no = as.getAs_no();
		Map<Student, Integer> scores = as.getScores();
		String msg, url;
		
	    // 파일 아이템을 생성할 때 사용할 팩토리 객체 생성 (임시 저장소 등 설정 가능)
	    DiskFileItemFactory factory = new DiskFileItemFactory();

	    // 업로드된 요청을 처리할 업로더 객체 생성
	    ServletFileUpload upload = new ServletFileUpload(factory);

	    try {
	        // 요청에서 업로드된 파일/필드를 추출해서 리스트로 받음
	        List<FileItem> items = upload.parseRequest(request);

	        for (FileItem item : items) {
	            // 일반 폼 필드가 아니고, 파일 이름이 .csv로 끝나는 경우에만 처리
	            if (!item.isFormField() && item.getName().endsWith(".csv")) {

	                // 업로드된 파일을 한 줄씩 읽기 위해 BufferedReader 사용
	                try (BufferedReader reader = new BufferedReader(
	                        new InputStreamReader(item.getInputStream(), StandardCharsets.UTF_8))) {
	                	boolean isFirstLine = true;
	                	String line;
	                	while ((line = reader.readLine()) != null) {
	                	    if (isFirstLine) { isFirstLine = false; continue; }
	                	    String[] data = line.split(",");
	                	    if(as_no == Integer.parseInt(data[0])) {
		                	    String user_no = data[1];
		                	    if(data[3] == null || data[3].trim().equals("") || data.length != 4) {
		                	    	continue;
		                	    }
		                	    int as_score = Integer.parseInt(data[3]);
		                	    Student st = students.get(user_no);
		                	    scores.put(st, as_score);
	                	    }
	                	} // while
	                } // try
	            } // if
	        } // for
	        int count = 0;
	        if( (count = subAsDao.insertScores(as_no, scores)) == scores.size()) { // submitted_assignment 테이블에 점수 넣기
	        	msg = count + "명의 성적 반영 성공\n꼭 반영여부를 확인하세요.";
	        	url = "classLMS/manageAs?as_no=" + as_no;
	        } else {
	        	msg = count + "명의 성적 반영 성공\n성적 반영에 오류가 생겼으니 확인하세요.";
	        	url = "classLMS/manageAs?as_no=" + as_no;
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        msg = "오류 발생. 업로드 실패.";
        	url = "classLMS/manageAs?as_no=" + as_no;
	    }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getServletPath();
        String as_no = request.getParameter("as_no");
        Class1 class1 = (Class1)request.getSession().getAttribute("class1");
        System.out.println(class1);
        Assignment as = class1.getAssignments().get(as_no);
        request.setAttribute("as", as);
        
        if ("/classLMS/download_asXLSX".equals(path)) {
            handleXlsxDownload(request, response);
        }
    }
    
    private void handleXlsxDownload(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
    	 // Workbook 생성
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("template");
        CellStyle yellowCell = workbook.createCellStyle();
        CellStyle pinkCell = workbook.createCellStyle();
        Font font = workbook.createFont();
        Assignment as = (Assignment)request.getAttribute("as");
        
        font.setFontHeightInPoints((short) 20);
        font.setBold(true);
        yellowCell.setFont(font);
        yellowCell.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
        yellowCell.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        pinkCell.setFillForegroundColor(IndexedColors.PINK.getIndex());
        pinkCell.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        
        // 1행: 안내 메시지
        Row row0 = sheet.createRow(0);
        row0.createCell(0).setCellValue("※ 이 행은 삭제하고 업로드하세요\n노란칸의 값만 채워주세요.");
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 2));
        // 0번째 행의 0열 ~ 2열까지 병합 (총 3칸 병합)
        row0.getCell(0).setCellStyle(yellowCell);

        // 2행: 헤더
        Row row1 = sheet.createRow(1);
        row1.createCell(0).setCellValue("as_no (과제번호)");
        row1.createCell(1).setCellValue("user_no (학번)");
        row1.createCell(2).setCellValue("이름");
        row1.createCell(3).setCellValue("as_score (과제점수)");
        for(int i = 0; i < 4; i++) {
            row1.getCell(i).setCellStyle(pinkCell);
        }
        int i = 0;
        for(Student st : as.getScores().keySet()) {
        	Row r = sheet.createRow(2 + i);
    		r.createCell(0).setCellValue(as.getAs_no());
    		r.createCell(1).setCellValue(st.USER_NO);
    		r.createCell(2).setCellValue(st.USER_NAME);
    		r.createCell(3).setCellValue("");
    		r.getCell(0).setCellStyle(pinkCell);
    		r.getCell(1).setCellStyle(pinkCell);
    		r.getCell(2).setCellStyle(pinkCell);
    		r.getCell(3).setCellStyle(yellowCell);
    		i++;
    	}

        // 응답 설정
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=\"assignment_template.xlsx\"");

        try (OutputStream out = response.getOutputStream()) {
            workbook.write(out);
            workbook.close();
        }
    }
}

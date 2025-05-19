package controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.file.Paths;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

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

/**
 * Servlet implementation class CSVxlsx
 */
@MultipartConfig
@WebServlet(urlPatterns = {"/classLMS/upload_asCSV",
		"/classLMS/download_asXLSX"})
public class CSVxlsxServlet extends HttpServlet {
	AsDao asDao = new AsDao();
	Reg_classDao rcDao = new Reg_classDao(); 
	
	@Override
    protected void doPost(HttpServletRequest request
    		, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getServletPath();

        if ("/classLMS/upload_asCSV".equals(path)) {
            handleCsvUpload(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request
    		, HttpServletResponse response)
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

    private void handleCsvUpload(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Part filePart = request.getPart("csvFile");  // form name="csvFile"
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();

        try (InputStream fileContent = filePart.getInputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(fileContent))) {

            String line;
            while ((line = reader.readLine()) != null) {
                // 여기서 CSV 한 줄씩 파싱하여 처리
                System.out.println("CSV Line: " + line);
            }

            response.getWriter().write("CSV 업로드 완료");
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

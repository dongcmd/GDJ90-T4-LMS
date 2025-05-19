package controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import gdu.mskim.MskimRequestMapping;
import gdu.mskim.RequestMapping;
import models.classes.Class1;
import models.classes.Class1Dao;

@WebServlet(urlPatterns = { "/classLMS/*" }, initParams = { @WebInitParam(name = "view", value = "/views/") })
public class ClassLMSController extends MskimRequestMapping {

	private Class1Dao dao = new Class1Dao();
	
	@RequestMapping("upload_asCSV")
	public String upload_asCSV(HttpServletRequest req,
			HttpServletResponse res) throws IOException, ServletException {
		Part filePart = req.getPart("csvFile");
		InputStream inputStream = filePart.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
		
		String line;
		while((line = reader.readLine()) != null) {
			String[] data = line.split(",");
		}
		
		reader.close();
		return "업로드 완료";
	}
	@RequestMapping("download_asXLSX")
	public String download_asXLSX(HttpServletRequest req,
			HttpServletResponse res) throws IOException {
		res.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		res.setHeader("Content-Disposition", "attachment; filename=upload_template.xlsx");

		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("양식");
		Row header = sheet.createRow(0);
		header.createCell(0).setCellValue("이름");
		header.createCell(1).setCellValue("나이");
		header.createCell(2).setCellValue("이메일");
		
		workbook.write(res.getOutputStream());
		workbook.close();
		
	}
		

	/*
	 * @RequestMapping("signUpClass") public String list(HttpServletRequest request,
	 * HttpServletResponse response) { List<Class1> list = dao.list();
	 * request.setAttribute("classesList", list); return "mainLMS/signUpClass"; }
	 */
}

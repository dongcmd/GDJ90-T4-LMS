package controllers;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

import gdu.mskim.MskimRequestMapping;

// 개발자이름
@WebServlet(urlPatterns = {"/mainLMS/*"},
initParams = {@WebInitParam(name="view",value="/views/")})
public class MainLMSController extends MskimRequestMapping{

}
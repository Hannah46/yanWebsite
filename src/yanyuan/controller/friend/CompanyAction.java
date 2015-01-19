package yanyuan.controller.friend;

import yanyuan.model.Company;

import com.jfinal.core.Controller;
import com.jfinal.render.FreeMarkerRender;

import freemarker.template.TemplateModelException;

public class CompanyAction extends Controller{
	public void index(){
		render("../jiayuan/login_company.html");
	}
	
	public void doLogin(){
		Company company = getModel(Company.class);
		company = Company.dao.findFirst("select * from company where email=? and passwd=?", 
				company.getStr("email"),company.getStr("passwd"));
		if (company == null) {
			setAttr("emailMsg", "邮箱或密码错误，请确认后再试！");
			renderJson("{\"error\":1}");
		}
		else {
			setSessionAttr("company", company);
			setAttr("company",company);
			if(getSessionAttr("association")!=null)
				  removeSessionAttr("association");
			if(getSessionAttr("association_union")!=null)
				  removeSessionAttr("association_union");			
			if(getSessionAttr("user")!=null)
				  removeSessionAttr("user");
			try { FreeMarkerRender.getConfiguration().setSharedVariable("logged", 2); } 
			catch (TemplateModelException e) {System.out.println("set freemarkerrender share variable failed");}
			renderJson("{\"error\":0}");
		}
	}
	
}

package yanyuan.controller.friend;

import yanyuan.model.User;

import com.jfinal.core.Controller;
import com.jfinal.render.FreeMarkerRender;

import freemarker.template.TemplateModelException;

public class UserAction extends Controller{
	public void login(){
		render("/jiayuan/login_user.html");
	}
	//登录
	public void doLogin(){
		User user = getModel(User.class);
		user = User.dao.findFirst("select * from user where email=? and passwd=?", 
				user.getStr("email"),user.getStr("passwd"));
		if (user == null) {
			setAttr("emailMsg", "邮箱或密码错误，请确认后再试！");
			renderJson("{\"error\":1}");
		}
		else {
			setSessionAttr("user", user);
			if(getSessionAttr("association")!=null)
				  removeSessionAttr("association");
			if(getSessionAttr("association_union")!=null)
				  removeSessionAttr("association_union");			
			if(getSessionAttr("company")!=null)
				  removeSessionAttr("company");
			try { FreeMarkerRender.getConfiguration().setSharedVariable("logged", 1); } 
			catch (TemplateModelException e) {System.out.println("set freemarkerrender share variable failed");}
			renderJson("{\"error\":0}");
		}
	}
	
}

package yanyuan.controller.job;

import java.util.Date;

import yanyuan.interceptor.UserSessionInterceptor;
import yanyuan.model.Resume;
import yanyuan.model.User;
import yanyuan.validator.ResumeValidator;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;

@Before(UserSessionInterceptor.class)
public class ResumeController extends Controller{
	
	public void index() {
		Resume resume = getModel(Resume.class);
		User user = getAttr("user");
		setAttr("resumeList", resume.getResumeList(String.valueOf(user.getBigInteger("id"))));
		setAttr("resumeUser", user);
		render("/liye/resume.html");
	}
		
	@Before(ResumeValidator.class)
	public void save() {
		getModel(Resume.class).set("version", new Date()).save();
		//redirect("/yanyuan/resume/");
		renderText("添加成功！");
	}
	
	public void add() {
		setAttr("resumeUser", getAttr("user"));
		render("/liye/add_resume.html");
	}
	
	public void edit_resume() {
		setAttr("resume", Resume.dao.findById(getParaToInt()));
		setAttr("resumeUser", getAttr("user"));
		render("/liye/edit_resume.html");
	}

	@Before(ResumeValidator.class)
	public void update() {
		getModel(Resume.class).update();
		renderText("修改成功");
	}
	
	public void delete() {
		Resume.dao.deleteById(getParaToInt());
		redirect("/resume");
	}

}

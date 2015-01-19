package yanyuan.controller.job;

import java.math.BigInteger;

import yanyuan.interceptor.UserSessionInterceptor;
import yanyuan.model.Experience;
import yanyuan.model.Resume;
import yanyuan.model.User;
import yanyuan.validator.ExperienceValidator;
import yanyuan.validator.ResumeValidator;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;

@Before(UserSessionInterceptor.class)
public class ExperienceController extends Controller {
	
	public void index(){
		Experience experience = getModel((Experience.class));
		setAttr("resume", Resume.dao.findById(getPara(0)));
		setAttr("experienceList", experience.getListExperience(getPara(0)));
		render("/liye/experience.html");
	}
	
	@Before(ExperienceValidator.class)
	public void save() {
		Experience experience = getModel(Experience.class);
		experience.save();
		redirect("/experience/" + experience.getLong("id_resume"));
	}
	
	public void add() {
		Experience experience = new Experience();
		experience.set("id_resume", new BigInteger(getPara()));
		setAttr("experience", experience);
		render("/liye/add_experience.html");
	}
	
	public void edit() {
		setAttr("experience", Experience.dao.findById(getParaToInt()));
		render("/liye/edit_experience.html");
	}

	@Before(ExperienceValidator.class)
	public void update() {
		getModel(Experience.class).update();
		renderText("修改成功");
	}
	
	public void delete() {
		Experience.dao.deleteById(getParaToInt());
		redirect("/experience");
	}


}

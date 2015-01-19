package yanyuan.validator;

import yanyuan.model.Resume;
import yanyuan.model.User;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;

public class ResumeValidator extends Validator {

	protected void validate(Controller controller) {
		validateRequired("resume.property", "propertyMsg", "请选择工作性质");
		validateRequired("resume.vocation", "vocationMsg", "请选择行业类别");
		validateRequired("resume.job", "jobMsg", "请选择期望职位");
		validateRequired("resume.sala", "salaMsg", "请选择期望薪金");
		validateRequired("resume.self_intro", "self_introMsg", "请输入自我评价");
		validateRequired("resume.year1", "year1Msg", "请输入开始年份");
		validateRequired("resume.month1", "month1Msg", "请输入开始月份");
		validateRequired("resume.year2", "year2Msg", "请输入结束年份");
		validateRequired("resume.month2", "month2Msg", "请输入结束月份");
		validateRequired("resume.university", "universityMsg", "请输入学校名称");
		//validateRequired("resume.major", "majorMsg", "请选择专业名称");
		validateRequired("resume.degree", "degreeMsg", "请选择学历学位");
	}
	
	protected void handleError(Controller controller) {
		controller.keepModel(Resume.class);
		controller.keepModel(User.class);
		controller.keepPara();
		//Resume resume = controller.getModel(Resume.class);
		//controller.setAttr("resume", resume);
		//controller.setAttr("resumeUser", User.dao.findById(controller.getParaToInt("user")));
		String actionKey = getActionKey();
		//System.out.println(actionKey);
		if (actionKey.equals("/resume/save"))
			controller.render("/liye/resume.html");
		else if (actionKey.equals("/resume/update"))
			controller.render("/liye/edit_resume.html");
	}
}

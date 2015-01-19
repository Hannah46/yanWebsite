package yanyuan.validator;

import yanyuan.model.Experience;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;

public class ExperienceValidator extends Validator{
	
	protected void validate(Controller controller) {
		validateRequired("experience.enterprise", "enterpriseMsg", "请输入企业名称");
		validateRequired("experience.vocation", "vocationMsg", "请选择行业类别");
		validateRequired("experience.job", "jobMsg", "请选择期望职位");
		validateRequired("experience.salary", "salaMsg", "请选择期望薪金");
		validateRequired("experience.desc", "descMsg", "请输入工作描述");
		validateRequired("experience.year1", "year1Msg", "请输入开始年份");
		validateRequired("experience.month1", "month1Msg", "请输入开始月份");
		validateRequired("experience.year2", "year2Msg", "请输入结束年份");
		validateRequired("experience.month2", "month2Msg", "请输入结束月份");
		validateRequired("experience.enter_type", "enter_typeMsg", "请选择企业性质");
		//validateRequired("resume.major", "majorMsg", "请选择专业名称");
		validateRequired("experience.enter_scale", "enter_scaleMsg", "请选择企业规模");
	}
	
	protected void handleError(Controller controller) {
		controller.keepModel(Experience.class);
		
		String actionKey = getActionKey();
		if (actionKey.equals("save"))
			controller.render("/liye/add_experience.html");
		else if (actionKey.equals("/experience/update"))
			controller.render("/liye/edit_experience.html");
	}

}

package yanyuan.validator;

import yanyuan.model.Company;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;

public class CompanyRegister extends Validator{
	protected void validate(Controller controller) {
		validateRequired("company.fullname", "fullnameMsg", "请输入企业全称");
		validateRequired("company.city", "cityMsg", "请选择所在城市");
		validateRequired("company.addr", "addrMsg", "请输入企业地址");
		validateRequired("company.person", "personMsg", "请输入联系人");
		validateRequired("company.tel", "telMsg", "请输入联系电话");
		validateRequired("company.email", "emailMsg", "请输入电子邮件");
		validateRequired("company.passwd", "passwdMsg", "请输入密码");
	}
	
	protected void handleError(Controller controller) {
		controller.keepModel(Company.class);
		System.out.println("handle error");
		controller.render("/register_company.html");
	}
}

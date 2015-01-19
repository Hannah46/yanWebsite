package yanyuan.validator;

import yanyuan.model.Mail;
import yanyuan.model.User;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;

public class MailValidator extends Validator {
	protected void validate(Controller controller) {
		validateRequired("username", "usernameMsg", "请输入收件人名称");
		validateRequired("mail.title", "titleMsg", "请输入邮件主题");
		validateRequired("mail.content", "contentMsg", "请输入邮件内容");
	}
	
	protected void handleError(Controller controller) {
		controller.keepModel(Mail.class);
		controller.keepPara();
		String actionKey = getActionKey();
		//System.out.println(actionKey);
		if (actionKey.equals("/mail/save"))
			controller.render("../mail_send.html");
	}
}

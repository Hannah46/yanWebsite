package yanyuan.validator;

import yanyuan.model.User;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;

public class UserRegister extends Validator{

	@Override
	protected void validate(Controller arg0) {
		// TODO Auto-generated method stub
		validateRequired("user.nickname", "nicknameMsg", "昵称必须填写！");
		validateRequired("user.sex", "sexMsg", "必须选择性别！");
		validateRequired("user.birth", "birthMsg", "生日必须填写！");
		validateRequired("user.email", "emailMsg", "邮箱必须填写！");
		validateRequired("user.passwd", "passwdMsg", "密码必须填写！");	
	}
	
	@Override
	protected void handleError(Controller arg0) {
		// TODO Auto-generated method stub
		arg0.keepModel(User.class);
		arg0.render("/jiaoyou/register.html");
	}

}

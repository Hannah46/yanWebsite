package yanyuan.interceptor;

import yanyuan.model.User;

import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;

public class UserSessionInterceptor implements Interceptor{

	@Override
	public void intercept(ActionInvocation arg0) {
		// TODO Auto-generated method stub
		User user = arg0.getController().getSessionAttr("user");
		if (user == null) {
			arg0.getController().redirect("/association");
		}
		else {
			//
			arg0.getController().setAttr("user", User.dao.findById(user.get("id")));
			arg0.invoke();
		}
	}

}

package yanyuan.interceptor;

import yanyuan.model.Association;

import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;

public class AssociationSession implements Interceptor{

	@Override
	public void intercept(ActionInvocation arg0) {
		// TODO Auto-generated method stub
		Association aso = arg0.getController().getSessionAttr("association");
		if (aso == null) {
			arg0.getController().redirect("/association");
		}
		else {
			arg0.invoke();
		}
	}

}

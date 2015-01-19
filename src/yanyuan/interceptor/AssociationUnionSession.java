package yanyuan.interceptor;

import yanyuan.model.University;

import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;

public class AssociationUnionSession implements Interceptor{

	@Override
	public void intercept(ActionInvocation arg0) {
		// TODO Auto-generated method stub
		University uv = arg0.getController().getSessionAttr("association_union");
		if (uv == null) {
			arg0.getController().redirect("/association");
		}
		else {
			arg0.invoke();
		}
	}

}

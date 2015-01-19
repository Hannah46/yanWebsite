package yanyuan.interceptor;

import yanyuan.model.Company;

import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;

public class CompanySessionInterceptor implements Interceptor {

	@Override
	public void intercept(ActionInvocation ai) {
		Company company = ai.getController().getSessionAttr("company");
		if (company == null) {
			ai.getController().render("/association");
		}else{
			ai.getController().setAttr("company", company);
			ai.invoke();
		}
	}

}

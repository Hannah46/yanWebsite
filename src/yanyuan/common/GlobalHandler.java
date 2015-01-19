package yanyuan.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jfinal.handler.Handler;
import com.jfinal.render.FreeMarkerRender;

import freemarker.template.TemplateModelException;

public class GlobalHandler extends Handler {
	@Override
	public void handle(String target, HttpServletRequest request,
			HttpServletResponse response, boolean[] isHandled) {
		request.setAttribute("path", request.getContextPath());
		nextHandler.handle(target, request, response, isHandled);
	}

}

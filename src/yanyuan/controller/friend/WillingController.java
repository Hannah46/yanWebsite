package yanyuan.controller.friend;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;

import yanyuan.interceptor.UserSessionInterceptor;
import yanyuan.model.Willing;
import yanyuan.model.User;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.render.FreeMarkerRender;

import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

public class WillingController extends Controller{
	
	//分页显示
	public void list(){
		TemplateModel f = FreeMarkerRender.getConfiguration().getSharedVariable("logged"); 
        if( f==null)
			try { FreeMarkerRender.getConfiguration().setSharedVariable("logged", 0); } 
			catch (TemplateModelException e) {System.out.println("set freemarkerrender share variable failed");}
	
		setAttr("willingPage", Willing.dao.getWillingsWithUser(getParaToInt(0, 1),10));
		render("/jiaoyou/showWillings.html");
	}
	
	public void toAdd(){
		User user = getSessionAttr("user");
		setAttr("user",user);

		TemplateModel f = FreeMarkerRender.getConfiguration().getSharedVariable("logged"); 
		int ti = Integer.parseInt( f.toString() );
        if( ti==0 || f==null || user==null)
          render("/jiaoyou/login.html");
        else
		  render("/jiaoyou/addWilling.html");
	}
	
	//添加
	@Before(UserSessionInterceptor.class)
	public void doAdd(){
		Willing willing = getModel(Willing.class);
//		Date nowDate = new Date();
//		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//		String pub_date = sDateFormat.format(nowDate);
//		willing.set("pub_date", pub_date);  数据库取缺省值
		willing.save();
		
		list();
	}
	
	//阅读
	public void addReaders(){
		long idLong = getParaToLong("id");
		BigInteger id = BigInteger.valueOf(idLong);
		Willing willing = Willing.dao.findById(id);
		int readers = willing.getInt("readers");
		willing.set("readers", readers+1);
		willing.update();
		renderText(String.valueOf(readers+1));
	}
	
	//点赞
	public void addAgrees(){
		long idLong = getParaToLong("id");
		BigInteger id = BigInteger.valueOf(idLong);
		Willing willing = Willing.dao.findById(id);
		int agress = willing.getInt("agrees");
		willing.set("agrees", agress+1);
		willing.update();
		renderText(String.valueOf(agress+1));
	}
}

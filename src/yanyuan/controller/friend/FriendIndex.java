package yanyuan.controller.friend;

import yanyuan.model.Association;
import yanyuan.model.Company;
import yanyuan.model.University;
import yanyuan.model.User;

import com.jfinal.core.Controller;
import com.jfinal.render.FreeMarkerRender;

import freemarker.template.TemplateModelException;
import freemarker.template.TemplateModel;  

public class FriendIndex extends Controller{

	public void index()  //  家园的 index.html
	{
		TemplateModel f = FreeMarkerRender.getConfiguration().getSharedVariable("logged"); 
        if( f==null)
			try { FreeMarkerRender.getConfiguration().setSharedVariable("logged", 0); } 
			catch (TemplateModelException e) {System.out.println("set freemarkerrender share variable failed");}
        	
		render("/jiaoyou/index.html");
	}

	public void my_yujian()  //  家园的 index.html
	{
        User user = getSessionAttr("user");
	    if( user!=null) 
           render("/jiaoyou/my_yujian.html");
	    else
		   render("/jiaoyou/index.html");//未登录
	}

	//退出登录
	public void logout(){
		int from_where;
		try { from_where = getParaToInt(0); }
		catch(Exception e) { from_where=3;}
		removeSessionAttr("user");
		removeSessionAttr("company");
		removeSessionAttr("association");
		removeSessionAttr("association_union");

		try { FreeMarkerRender.getConfiguration().setSharedVariable("logged", 0); } 
		catch (TemplateModelException e) {System.out.println("set freemarkerrender share variable failed");}
		
		if( from_where ==1 )        // 来自交友
			   redirect("/friend");
		else if( from_where ==2 )   // 来自JOB
			   redirect("/job");
		if( from_where ==3 )        // 来自家园
		   redirect("/association");
	}		
}

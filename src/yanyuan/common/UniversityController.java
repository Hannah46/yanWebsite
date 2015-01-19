package yanyuan.common;

import com.jfinal.core.Controller;
import java.net.URLDecoder;
import yanyuan.model.University;
import java.util.List;
import java.io.UnsupportedEncodingException;

public class UniversityController extends Controller {
	
	public void list(){                //为选择学校对话框服务
		String province = getPara(0);
		try
		  {
		    province = URLDecoder.decode(province,"UTF-8");
		  }
	    catch (UnsupportedEncodingException ex) 
	       {throw new RuntimeException("Broken VM does not support UTF-8");}
		
		List<University> list = University.dao.find("select id,name from university where province='" + province + "'");
		setAttr("university_list", list);
		
		render("../university_list.html");
	}	

	public void index()
	{
		University uty = University.dao.findById( getPara(0));
		setAttr("university", uty);
		//setAttr("postResult","");
		render("../hout/group_leader.html");
	} 
	
	public void update()
	{
		University uty = getModel(University.class);
		uty.update();
		uty = University.dao.findById( uty.getStr("id"));
		setAttr("university", uty);
		setAttr("postResult","修改成功");
	    render("../hout/group_leader.html");
	}
}

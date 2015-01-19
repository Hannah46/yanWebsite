package yanyuan.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
//import com.jfinal.plugin.ehcache.CacheKit;
//import com.zcm.utils.EhcacheConst;


/**
 * CommonController
 */
public class HomeController extends Controller {
	
	/**
	 * 网站首页
	 */
	public void index(){
		redirect("/user/register");
		//reDirect()
	}

}

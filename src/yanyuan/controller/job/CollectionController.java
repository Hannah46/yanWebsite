package yanyuan.controller.job;

import java.math.BigInteger;
import java.util.Date;

import yanyuan.interceptor.UserSessionInterceptor;
import yanyuan.model.Collection;
import yanyuan.model.User;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;

public class CollectionController extends Controller{
	//添加收藏
	public void add(){
		Collection collection = new Collection();
		collection.set("id_user", new BigInteger(getPara(0)));
		collection.set("id_jobs", new BigInteger(getPara(1)));
		collection.set("collect_date", new Date());
		collection.save();
		renderText("收藏成功！");
	}
	//用户的收藏列表
	@Before(UserSessionInterceptor.class)
	public void myCollections(){
		User user = getSessionAttr("user");
		setAttr("collectionPage", Collection.dao.listForUser(getParaToInt(0, 1), 10, user.getBigInteger("id")));
		render("/liye/myCollectios.html");
	}
	
	public void delete(){
		Collection.dao.findById(new BigInteger(getPara())).delete();
		myCollections();
	}
}

package yanyuan.controller.job;

import java.math.BigInteger;
import java.util.Date;

import yanyuan.interceptor.CompanySessionInterceptor;
import yanyuan.interceptor.UserSessionInterceptor;
import yanyuan.model.Company;
import yanyuan.model.PostedNotification;
import yanyuan.model.Resume;
import yanyuan.model.User;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;

public class PostedNotificationController extends Controller{
	
	@Before(UserSessionInterceptor.class)
	public void toPost(){
		setAttr("jobId", getPara());
		User user = getAttr("user");
		setAttr("resumeList", Resume.dao.getResumeList(String.valueOf(user.getBigInteger("id"))));
		render("/liye/addPost.html");
	}
	//投递简历
	public void doPost(){
		PostedNotification post = getModel(PostedNotification.class);
		post.set("posted_date", new Date());
		post.save();
		renderText("投递成功！");
	}
	
	@Before(CompanySessionInterceptor.class)
	//某一公司的投递列表
	public void listForCompany(){
		Company company = getSessionAttr("company");
		BigInteger id_company = company.getBigInteger("id");
		setAttr("postPage", PostedNotification.dao.listForCompany(getParaToInt(0, 1), 10, id_company));
		render("/liye/postForCompany.html");
	}
	//转到公司操作
	public void toCompanyOp(){
		PostedNotification post = PostedNotification.dao.findById(getPara());
		Resume resume = Resume.dao.findById(post.get("id_resume"));
		User resumeUser = User.dao.findById(resume.get("id_user"));
		setAttr("resume", resume);
		setAttr("post",post);
		setAttr("resumeUser", resumeUser);
		render("/liye/companyOp.html");
	}
	//公司操作
	public void doCompanyOp(){
		PostedNotification post = getModel(PostedNotification.class);
		post.set("noti_date", new Date());
		post.update();
		renderText("修改成功！");
	}
	
	//转到求职者操作
	public void toUserOp(){
		PostedNotification post = PostedNotification.dao.findById(getPara());
		setAttr("post",post);
		render("/liye/userOp.html");
	}
	//求职者操作
	public void doUserOp(){
		PostedNotification post = getModel(PostedNotification.class);
		post.set("refused", 2);
		post.update();
		renderText("修改成功！");
	}
	
	@Before(UserSessionInterceptor.class)
	//用户的投递列表
	public void listForUser(){
		User user = getSessionAttr("user");
		BigInteger id_user = user.getBigInteger("id");
		setAttr("postPage", PostedNotification.dao.listForUser(getParaToInt(0, 1), 10, id_user));
		render("/liye/postForUser.html");
	}
}

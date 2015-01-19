package yanyuan.controller.job;

import java.math.BigInteger;
import java.util.Date;

import yanyuan.interceptor.CompanySessionInterceptor;
import yanyuan.model.Collection;
import yanyuan.model.Company;
import yanyuan.model.Job;
import yanyuan.model.User;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.render.FreeMarkerRender;

import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

public class JobController extends Controller{
	
	public void index()  //  家园的 index.html
	{
		TemplateModel f = FreeMarkerRender.getConfiguration().getSharedVariable("logged"); 
        if( f==null)
			try { FreeMarkerRender.getConfiguration().setSharedVariable("logged", 0); } 
			catch (TemplateModelException e) {System.out.println("set freemarkerrender share variable failed");}
        	
		setAttr("jobPage", Job.dao.jobList(getParaToInt(0, 1), 10));
		render("/liye/index.html");
	}
	
	//分页显示
//	public void list(){
//		setAttr("jobPage", Job.dao.jobList(getParaToInt(0, 1), 10));
//		render("/liye/jobsList.html");
//	}
	
	@Before(CompanySessionInterceptor.class)
	//显示特定公司的招聘信息
	public void listForCompany(){
		Company company = getSessionAttr("company");
		setAttr("jobPage", Job.dao.jobListForCompany(getParaToInt(0, 1), 10, company.getBigInteger("id")));
		render("/liye/myJobsList.html");
	}
	
	//显示职位信息
	public void showJob() {
		BigInteger id_job = new BigInteger(getPara());
		setAttr("job", Job.dao.getWithCompanyName(id_job));
		User user = getSessionAttr("user");
		if (user != null) {
			BigInteger id_user = user.getBigInteger("id");
			Collection collection = Collection.dao.findFirst("select * from collection where id_user=? and id_jobs=?", id_user,id_job);
			if (collection != null) {
				setAttr("isCollected", true);
			}
			setAttr("id_user", id_user);
		}
		
		render("/liye/showJob.html");
	}
	//添加
	@Before(CompanySessionInterceptor.class)
	public void toAdd(){
		Company company = getSessionAttr("company");
		Job job = new Job();
		job.set("id_company", company.get("id"));
		setAttr("job", job);
		render("/liye/addJob.html");
	}
	
	//添加
	public void doAdd(){
		getModel(Job.class).set("publishdate", new Date()).save();
		renderText("添加成功！");
	}
	public void toEdit(){
		Job job = Job.dao.findById(getPara());
		setAttr("job", job);
		render("/liye/editJob.html");
	}
	
	//修改
	public void doEdit(){
		getModel(Job.class).update();
		renderText("修改成功！");
	}
	
}

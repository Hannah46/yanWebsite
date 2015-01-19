package yanyuan.controller.friend;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.commons.codec.EncoderException;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

import yanyuan.interceptor.CompanySessionInterceptor;
import yanyuan.model.Association;
import yanyuan.model.Company;
import yanyuan.model.User;
import yanyuan.qiniu.QiNiuFile;
import yanyuan.qiniu.QiNiuUtils;
import yanyuan.validator.CompanyRegister;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.kit.StringKit;
import com.qiniu.api.auth.AuthException;

public class CompanyInfo extends Controller{
	
	public void register(){
		render("/register_company.html");
	}

	@Before(CompanyRegister.class)
	public void save() throws Exception{
		Company company = getModel(Company.class);
		String email = company.get("email");
		String fullname = company.get("fullname");
		
		if (company.findByFullname(fullname)) {
			keepModel(Company.class);
			setAttr("fullnameMsg", "该公司名称已存在！请尝试其他邮箱");
			redirect("register");
		}
		else if (company.findByEmail(email)) {
			keepModel(Company.class);
			setAttr("emailMsg", "该邮箱已存在！请尝试其他邮箱");
			redirect("register");
		}
		else {
			company.save();
			renderText("注册成功！");
		}
	}
	
	public void menu()
	{
		render("/liye/company_menu.html");
	}

	//保存企业详细信息，包括图片
	public void saveWithPhoto() throws Exception{
		
		Company company = getModel(Company.class);
		updatePhoto(company, "pict1", "pict2");
		System.out.println("save with photo");
		renderText("修改成功！");
	}
	
	//删除原有图片，处理新上传的图片，更新数据库，删除新上传的图片
	public void updatePhoto(Company company,String photo,String photob) throws EncoderException, AuthException, JSONException, ClientProtocolException, URISyntaxException, IOException{
		//company保存用户提交的表单中的信息
		//company2保存目前数据库中的信息
		Company company2 = Company.dao.findById(company.get("id"));
		String oldPhoto = company2.getStr(photo);
		String oldPhotob = company2.getStr(photob);
		String newPhoto = company.getStr(photo);
		//删除原有图片
		if (!StringKit.isBlank(oldPhoto)) {
			QiNiuFile.delete(oldPhoto);
		}
		if (!StringKit.isBlank(oldPhotob)) {
			QiNiuFile.delete(oldPhotob);
		}
					
		//如果图片宽度大于640，进行裁剪
		String smallPhoto;
		int width = QiNiuFile.getWidth(newPhoto);
		if (width > 640) {
			smallPhoto = QiNiuFile.makeSmall(newPhoto);
		}
		else {
			smallPhoto = newPhoto;
		}
					
		//生成缩略图
		String thumbPhoto = QiNiuFile.makeThumb(newPhoto);
					
		//更新数据库
		company.set(photo, smallPhoto);
		company.set(photob, thumbPhoto);
		
		company.update();
	    System.out.println("company update!");
		//删除原有图片
		if (width>640) {
			QiNiuFile.delete(newPhoto);
		}
	}

	@Before(CompanySessionInterceptor.class)
	public void update() {
		getModel(Company.class).update();
	    renderJson("{\"error\":0}");
	}

	@Before(CompanySessionInterceptor.class)
	//详细信息设置
	public void toDetail() {
		Company company = getSessionAttr("company");
		setAttr("company", Company.dao.findById(company.get("id")));
		render("/liye/company_detail.html");
	}
	 
	public void toPhoto1(){                                  //LOGO
		Company company = getSessionAttr("company");
		setAttr("company", company);
		setAttr("token", QiNiuUtils.getToken());
		setAttr("key", QiNiuUtils.getKey());
		render("../liye/company_photo1.html");
	}
	
	public void toPhoto2(){                                  //执照
		Company company = getSessionAttr("company");
		setAttr("company", company);
		setAttr("token", QiNiuUtils.getToken());
		setAttr("key", QiNiuUtils.getKey());
		render("../liye/company_photo2.html");
	}

	public void updatePhoto1()
   {
		Company company = getModel(Company.class);
		System.out.println("new pict1 =" + company.getStr("pict1") );		
		Company company_original = Company.dao.findById(company.get("id"));
		String oldPhoto;
		oldPhoto= company_original.getStr("pict1");         
		System.out.println("odl pic =" + oldPhoto );		
		System.out.println("new pict1 =" + company.getStr("pict1") );		
			
		//删除原有图片
		if (!StringKit.isBlank(oldPhoto)) 
		{ //QiNiuFile.delete(oldPhoto);
		  System.out.println("delete old=" + oldPhoto);
		}
		
		String currentPic; 
		String thumbPhoto="";
		//生成缩略图
			currentPic = company.getStr("pict1");
			  System.out.println("current=" + currentPic);
		    try {  thumbPhoto = QiNiuFile.makeThumb(currentPic); }
            catch(Exception e1) { e1.printStackTrace(); }
		    company.set("pict1",thumbPhoto);
        company.update();
			
		QiNiuFile.delete(currentPic);
		System.out.println("delete2=" + currentPic);
		renderText("");
	   
   }
	public void updatePhoto2()
	   {
			Company company = getSessionAttr("company");
			System.out.println("new pict1 =" + company.getStr("pict1") );		
			Company company_original = Company.dao.findById(company.get("id"));
			String oldPhoto;
			if( getParaToInt(0)==1 )                                //LOGO
				oldPhoto= company_original.getStr("pict1");         
			else
				oldPhoto= company_original.getStr("pict2");         //执照
			System.out.println("odl pic =" + oldPhoto );		
			System.out.println("new pict1 =" + company.getStr("pict1") );		
				
			//删除原有图片
			if (!StringKit.isBlank(oldPhoto)) 
			{ //QiNiuFile.delete(oldPhoto);
			  System.out.println("delete old=" + oldPhoto);
			}
			
			String currentPic; 
			String thumbPhoto="";
			//生成缩略图
			if( getParaToInt(0)==1 )                                //LOGO
			{
				currentPic = company.getStr("pict1");
				  System.out.println("current=" + currentPic);
			    try {  thumbPhoto = QiNiuFile.makeThumb(currentPic); }
	            catch(Exception e1) { e1.printStackTrace(); }
			    company.set("pict1",thumbPhoto);
	        }
			else                                                            //执照
			{
				currentPic = company.getStr("pict2");
			    try {  thumbPhoto = QiNiuFile.makeSmall(currentPic,1024); }
	            catch(Exception e1) { e1.printStackTrace(); }
			    company.set("pict2",thumbPhoto);
	        }
	        company.update();
				
			QiNiuFile.delete(currentPic);
			System.out.println("delete2=" + currentPic);
			//renderText("");
		   
	   }
}

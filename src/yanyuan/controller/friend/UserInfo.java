package yanyuan.controller.friend;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.codec.EncoderException;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

import yanyuan.interceptor.UserSessionInterceptor;
import yanyuan.model.User;
import yanyuan.qiniu.QiNiuFile;
import yanyuan.utils.AnimalAndZodiac;
import yanyuan.validator.UserRegister;

import com.jfinal.aop.Before;
import com.jfinal.aop.ClearInterceptor;
import com.jfinal.aop.ClearLayer;
import com.jfinal.core.Controller;
import com.jfinal.kit.StringKit;
import com.jfinal.render.FreeMarkerRender;
import com.qiniu.api.auth.AuthException;

import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

@Before(UserSessionInterceptor.class)
public class UserInfo extends Controller{
	
	@ClearInterceptor(ClearLayer.ALL)
	public void register(){
		render("/register.html");
	}
	
	@ClearInterceptor(ClearLayer.ALL)
	@Before(UserRegister.class)
	public void doRegister() throws ParseException{
		String nickname = getModel(User.class).getStr("nickname");
		String email = getModel(User.class).getStr("email");
		//邮箱不能重复
		if (User.dao.findByEmail(email)) {
			keepModel(User.class);
			setAttr("emailMsg", "邮箱已被使用！请不要重复注册！");
			render("/jiaoyou/register.html");
			return ;
		}
		//用户名不能重复
		if (User.dao.findByNickname(nickname)) {
			keepModel(User.class);
			setAttr("nicknameMsg", "用户名已存在！请尝试其他用户名");
			render("/jiaoyou/register.html");
		}
		else {
			User user = getModel(User.class);
			//设置生肖和星座
			Date birth = user.getDate("birth");
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(birth);
			user.set("animal", AnimalAndZodiac.date2Animal(calendar));
			user.set("zodiac", AnimalAndZodiac.date2Zodiac(calendar));
			
			user.save();
			setSessionAttr("user", user);
			if(getSessionAttr("association")!=null)
				  removeSessionAttr("association");
			if(getSessionAttr("association_union")!=null)
				  removeSessionAttr("association_union");			
			if(getSessionAttr("company")!=null)
				  removeSessionAttr("company");
			redirect("/user/toPersonalInfo");
		}
	}
	//个人信息	
	public void toPersonalInfo(){
		render("/jiaoyou/personalInfo.html");
	}
	public void updatePersonalInfo() throws ClientProtocolException, EncoderException, AuthException, JSONException, URISyntaxException, IOException{
		User user = getModel(User.class);
		String photo = "iden_picture1";
		String photob = "iden_picture2";
		
		updatePhoto(user, photo, photob);
		renderText("修改成功！");
	}
	//外貌特征
	public void toAppearance(){
		render("/jiaoyou/appearance.html");
	}
	public void updateAppearance(){
		User user = getModel(User.class);
		user.update();
		renderText("修改成功！");
	}
	//自白
	public void toSelfIntro(){
		render("/jiaoyou/selfIntro.html");
	}
	public void updateSelfIntro(){
		User user = getModel(User.class);
		user.update();
		renderText("修改成功！");
	}
	//兴趣爱好
	public void toInterest(){
		render("/jiaoyou/interest.html");
	}
	public void updateInterest(){
		User user = getModel(User.class);
		user.update();
		renderText("修改成功！");
	}
	//个人照片
	public void toPhoto(){
		render("/jiaoyou/photo.html");
	}
	public void updatePhoto1() throws ClientProtocolException, EncoderException, AuthException, JSONException, URISyntaxException, IOException {
		//user保存用户提交的表单中的信息，photo是裁剪的图片的字段名称，photob是缩略图的字段名称
		User user = getModel(User.class);
		String photo = "photo1";
		String photob = "photo1b";
		
		updatePhoto(user, photo, photob);
		renderText("修改成功！");
	}
	public void updatePhoto2() throws ClientProtocolException, EncoderException, AuthException, JSONException, URISyntaxException, IOException {
		//user保存用户提交的表单中的信息，photo是裁剪的图片的字段名称，photob是缩略图的字段名称
		User user = getModel(User.class);
		String photo = "photo2";
		String photob = "photo2b";
		
		updatePhoto(user, photo, photob);
		renderText("修改成功！");
	}
	public void updatePhoto3() throws ClientProtocolException, EncoderException, AuthException, JSONException, URISyntaxException, IOException {
		//user保存用户提交的表单中的信息，photo是裁剪的图片的字段名称，photob是缩略图的字段名称
		User user = getModel(User.class);
		String photo = "photo3";
		String photob = "photo3b";
		
		updatePhoto(user, photo, photob);
		renderText("修改成功！");
	}
	public void updatePhoto4() throws ClientProtocolException, EncoderException, AuthException, JSONException, URISyntaxException, IOException {
		//user保存用户提交的表单中的信息，photo是裁剪的图片的字段名称，photob是缩略图的字段名称
		User user = getModel(User.class);
		String photo = "photo4";
		String photob = "photo4b";
		
		updatePhoto(user, photo, photob);
		renderText("修改成功！");
	}
	public void updatePhoto5() throws ClientProtocolException, EncoderException, AuthException, JSONException, URISyntaxException, IOException {
		//user保存用户提交的表单中的信息，photo是裁剪的图片的字段名称，photob是缩略图的字段名称
		User user = getModel(User.class);
		String photo = "photo5";
		String photob = "photo5b";
		
		updatePhoto(user, photo, photob);
		renderText("修改成功！");
	}
	
	//删除原有图片，处理新上传的图片，更新数据库，删除新上传的图片
	public void updatePhoto(User user,String photo,String photob) throws EncoderException, AuthException, JSONException, ClientProtocolException, URISyntaxException, IOException{
		//user保存用户提交的表单中的信息
		//user2保存目前数据库中的信息
		User user2 = User.dao.findById(user.get("id"));
		String oldPhoto = user2.getStr(photo);
		String oldPhotob = user2.getStr(photob);
		String newPhoto = user.getStr(photo);
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
		user.set(photo, smallPhoto);
		user.set(photob, thumbPhoto);
		user.update();
		
		//删除原有图片
		if (width>640) {
			QiNiuFile.delete(newPhoto);
		}
	}
	
	//求职照片
	public void toPhotoJob(){
		render("/jiaoyou/photoJob.html");
	}
	public void updatePhotoJob() throws ClientProtocolException, EncoderException, AuthException, JSONException, URISyntaxException, IOException{
		User user = getModel(User.class);
		updatePhoto(user, "photo_job", "photo_job2");
	
		renderText("修改成功！");
	}
	
	@ClearInterceptor(ClearLayer.ALL)
	public void index() {
		setAttr("blogPage", User.dao.paginate(1, 24));
		render("/jiaoyou/picShow.html");
	
	}

	//分页显示用户列表，默认未认证
	@ClearInterceptor(ClearLayer.ALL)
	public void toUserIdentify() {
		TemplateModel f = FreeMarkerRender.getConfiguration().getSharedVariable("logged"); 
        if( f==null)
			try { FreeMarkerRender.getConfiguration().setSharedVariable("logged", 0); } 
			catch (TemplateModelException e) {System.out.println("set freemarkerrender share variable failed");}
		
		setAttr("userPage",User.dao.getUserListByEmail(1, 10, "", 0));
		setAttr("flag",0);
		render("../hout/user_identify.html");
	}
	
	//根据查询获取用户列表
	@ClearInterceptor(ClearLayer.ALL)
	public void userList(){
		int flag = getParaToInt(2);
		setAttr("flag",flag);
		setAttr("email",getPara(1));
		setAttr("userPage",getModel(User.class).getUserListByEmail(getParaToInt(0, 1), 10, getPara(1), flag));
		render("../hout/user_identify.html");
	}

	//认证或取消认证
	@ClearInterceptor(ClearLayer.ALL)
	public void identify(){ 
		int flag = getParaToInt(0);
		if (flag == 0) {
			User user = User.dao.findById(getParaToInt(1));
			user.set("flag", 1);
			user.update();
			renderText("认证成功");
		}
		else {
			User user = User.dao.findById(getParaToInt(1));
			user.set("flag", 0);
			user.update();
			renderText("取消认证成功");
		}
	}
	
	@ClearInterceptor(ClearLayer.ALL)
	public void toSearch() {
//		List<User> _user = User.dao.find("select * from user where id = 1");
		setAttr( "userList", User.dao.find("select * from user") );
		render("../jiaoyou/search.html");
	}
	
	@ClearInterceptor(ClearLayer.ALL)
	public void search() {
		//获取查询条件
		String sex = getPara(0);
		try {
			sex = URLDecoder.decode(sex,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Integer minheight = getParaToInt(1);
		Integer maxheight = getParaToInt(2);
		String university = getPara(3);
		try {
			university = URLDecoder.decode(university,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//根据查询条件动态拼sql
		StringBuilder sql = new StringBuilder();
		List<Object> paras = new ArrayList<Object>();
		sql.append("select * from user where 1=1 ");
		if ( sex != null && sex != "") {
			sql.append("and sex = ?");
			paras.add( sex );
		} 
		if ( minheight <= maxheight ) {
			sql.append(" and height between ? and ?");
			paras.add( minheight );
			paras.add( maxheight );
		}
		if ( !(university.equals("不限")) ){
			sql.append(" and university = ?");
			paras.add( university );
		}

		setAttr( "sql", sql.toString() );
		//renderText("成功");
		//setAttr( "userPage", getModel(User.class).dao.getSearchUserList(1, 10, "from user where 1=1 and sex = ?", "男");
		setAttr( "userList", getModel(User.class).dao.find( sql.toString(), paras.toArray() ));
		render( "../jiaoyou/search.html" );
	}

//	public void paginate() {
//		String sql = getPara(1);
//		setAttr( "sql", sql);
//		setAttr( "userPage", getModel(User.class).dao.getSearchUserList(1, 10, sql, paras));
//		render( "../jiaoyou/search.html" );
//	}

}

package yanyuan.controller.friend;

import org.apache.commons.codec.EncoderException;

import yanyuan.model.User;
import yanyuan.qiniu.QiNiuFile;

import com.jfinal.core.Controller;
import com.qiniu.api.auth.AuthException;

public class CImage extends Controller{
	//显示单张图片图片
	public void show() {
		String key = getPara();
		String url="";
		try{ url= QiNiuFile.getDownloadUrl(key); }
		catch(Exception e) {;}
		redirect(url);
	}
	//显示缩略图，单击缩略图后弹出原图片
	public void showPhoto(){
		int id = getParaToInt(0);
		User user = User.dao.findById(id);
		String photo = getPara(1);
		String photob = getPara(2);
		String url = user.getStr(photo);
		String urlb = user.getStr(photob);
		setAttr("photoUrl", url);
		setAttr("photobUrl", urlb);
		
		render("/jiaoyou/showPhoto.html");
	}
}

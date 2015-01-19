package yanyuan.qiniu;

import org.json.JSONException;
import org.json.JSONObject;

import com.jfinal.core.Controller;
import com.qiniu.api.auth.AuthException;
import com.qiniu.api.auth.digest.Mac;
import com.qiniu.api.rs.PutPolicy;

public class QiNiuUtils extends Controller{
	
	//获取上传文件的token和key
	public void getTokenAndKey() throws AuthException, JSONException{
		Mac mac = new Mac(QiNiuConfig.ACCESS_KEY, QiNiuConfig.SECRET_KEY);
		PutPolicy putPolicy = new PutPolicy(QiNiuConfig.BUCKET_NAME);
		putPolicy.expires = QiNiuConfig.UPLOAD_EXPIRES;
		putPolicy.returnBody = QiNiuConfig.RETURN_BODY;
		String uptoken = putPolicy.token(mac);
		String key = QiNiuConfig.getFileName();
		JSONObject jsonObject= new JSONObject();
		jsonObject.put("token", uptoken);
		jsonObject.put("key", key);
		renderJson(jsonObject.toString());
	}
	
	public static String getToken()
	{
	  String uptoken="";
	  Mac mac = new Mac(QiNiuConfig.ACCESS_KEY, QiNiuConfig.SECRET_KEY);
	  PutPolicy putPolicy = new PutPolicy(QiNiuConfig.BUCKET_NAME);
	  putPolicy.expires = QiNiuConfig.UPLOAD_EXPIRES;
	  putPolicy.returnBody = QiNiuConfig.RETURN_BODY;
	  try {
	    uptoken = putPolicy.token(mac);
	  }catch(Exception e1) 
	    {
		  e1.printStackTrace();
	    }
      return uptoken;                 
   }	
	
	public static String getKey()
	{
	  return QiNiuConfig.getFileName();
	}	
	
}

package yanyuan.qiniu;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.codec.EncoderException;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

import com.qiniu.api.auth.AuthException;
import com.qiniu.api.auth.digest.Mac;
import com.qiniu.api.fop.ImageInfo;
import com.qiniu.api.fop.ImageInfoRet;
import com.qiniu.api.io.IoApi;
import com.qiniu.api.io.PutExtra;
import com.qiniu.api.io.PutRet;
import com.qiniu.api.net.CallRet;
import com.qiniu.api.net.EncodeUtils;
import com.qiniu.api.rs.GetPolicy;
import com.qiniu.api.rs.PutPolicy;
import com.qiniu.api.rs.RSClient;
import com.qiniu.api.rs.URLUtils;

public class QiNiuFile {
	//上传
	public static PutRet upload(String uploadFile) throws AuthException, JSONException{
		Mac mac = new Mac(QiNiuConfig.ACCESS_KEY, QiNiuConfig.SECRET_KEY);
		PutPolicy putPolicy = new PutPolicy(QiNiuConfig.BUCKET_NAME);
		//
		putPolicy.expires = QiNiuConfig.UPLOAD_EXPIRES;
		putPolicy.returnBody = QiNiuConfig.RETURN_BODY;
		String uptoken = putPolicy.token(mac);
		PutExtra putExtra = new PutExtra();
		PutRet putRet = IoApi.putFile(uptoken, QiNiuConfig.getFileName(), uploadFile, putExtra);
		
		return putRet;
	}
	
	//获取图片链接
	public static String getDownloadUrl(String key) throws EncoderException, AuthException{
//		Mac mac = new Mac(QiNiuConfig.ACCESS_KEY, QiNiuConfig.SECRET_KEY);
//		String baseUrl =  URLUtils.makeBaseUrl(QiNiuConfig.DOMAIN, key);
//		GetPolicy getPolicy = new GetPolicy();
//		getPolicy.expires = QiNiuConfig.DOWNLOAD_EXPIRES;
//		return getPolicy.makeRequest(baseUrl, mac);
		return "http://" + QiNiuConfig.DOMAIN + "/" + key;
	}
	
	//缩略图
	public static String makeThumb(String key) throws ClientProtocolException, AuthException, URISyntaxException, IOException{
		//生成125*125的缩略图，居中裁剪
		//http://developer.qiniu.com/docs/v6/api/reference/fop/image/imageview2.html
		String change = "?imageView2/1/w/125/h/125";
		String newFileName = QiNiuConfig.getFileName();
		String response = doChange(key, newFileName, change);
		//System.out.println("makeThumb response : " + response);
		return newFileName;
	}
		
	//缩略图
	public static String makeThumb(String key, int width, int height) throws ClientProtocolException, AuthException, URISyntaxException, IOException{
		//生成 width * height 的缩略图，    居中裁剪
		//http://developer.qiniu.com/docs/v6/api/reference/fop/image/imageview2.html
		String change = "?imageView2/1/w/"+ width +"/h/"+ height;
		String newFileName = QiNiuConfig.getFileName();
		doChange(key, newFileName, change);
		return newFileName;
	}

	//缩小图片，宽度为640，按比例缩小
	public static String makeSmall(String key) throws ClientProtocolException, AuthException, URISyntaxException, IOException{
		//宽度为640，高度按比例调整
		//http://developer.qiniu.com/docs/v6/api/reference/fop/image/imagemogr2.html
		//String change = "?imageMogr2/gravity/Center/crop/640x/format/jpg/interlace/1";  //一律抓换为jpg,渐进显示
		String change = "?imageMogr2/strip/thumbnail/640x/format/jpg/interlace/1";  //一律抓换为jpg,渐进显示
		String newFileName = QiNiuConfig.getFileName();
		doChange(key, newFileName, change);
		return newFileName;
	}
	
	public static String makeSmall(String key, int width) throws ClientProtocolException, AuthException, URISyntaxException, IOException{
		//宽度为 width，高度按比例调整
		//http://developer.qiniu.com/docs/v6/api/reference/fop/image/imagemogr2.html
		String change = "?imageMogr2/strip/thumbnail/640x/format/jpg/interlace/1";  //一律抓换为jpg,渐进显示
		String newFileName = QiNiuConfig.getFileName();
		doChange(key, newFileName, change);
		return newFileName;
	}

	//获取图片宽度
	public static int getWidth(String key) throws EncoderException, AuthException, JSONException{
		int width = 0;
		String baseUrl = URLUtils.makeBaseUrl(QiNiuConfig.DOMAIN, key);
		Mac mac = new Mac(QiNiuConfig.ACCESS_KEY, QiNiuConfig.SECRET_KEY);
		ImageInfoRet ret = ImageInfo.call(baseUrl,mac);
		String response = ret.getResponse();
		JSONObject jsonObject = new JSONObject(response);
		width = jsonObject.getInt("width");
			
		return width;
	}
	
	//删除图片
	public static void delete(String key){
		 Mac mac = new Mac(QiNiuConfig.ACCESS_KEY, QiNiuConfig.SECRET_KEY);
	     RSClient client = new RSClient(mac);
	     CallRet callRet = client.delete(QiNiuConfig.BUCKET_NAME, key);
	}
	//修改图片
	public static String doChange(String key,String newFileName,String change) throws AuthException, URISyntaxException, ClientProtocolException, IOException{
		//生成sign的url中不包含前面的“http：//”
		String baseUrl =  QiNiuConfig.DOMAIN + "/" + key;
		//添加图像操作参数
		String smallString = baseUrl + change;
		//对新的文件名称进行编码
		String saveAsString = QiNiuConfig.BUCKET_NAME + ":" + newFileName;
		saveAsString = EncodeUtils.urlsafeEncode(saveAsString);
		//构造新的URL
		String newUrl = smallString + "|saveas/" + saveAsString;
		//对新的url签名
		Mac mac = new Mac(QiNiuConfig.ACCESS_KEY, QiNiuConfig.SECRET_KEY);
		String sign = mac.sign(newUrl.getBytes());
		//将“http://”和签名添加到URL中
		String finalUrl = "http://" + newUrl + "/sign/" + sign;
		//生成token
		GetPolicy getPolicy = new GetPolicy();
		finalUrl = getPolicy.makeRequest(finalUrl, mac);
		//发送请求，获取响应
		URL url = new URL(finalUrl);
		URLConnection connection = url.openConnection();
		java.util.Scanner scanner = new java.util.Scanner(connection.getInputStream());
		StringBuilder stringBuilder = new StringBuilder();
		while (scanner.hasNext()) {
			stringBuilder.append(scanner.nextLine());
		}
		return stringBuilder.toString();
	}
	
	public static void main(String[] args) throws EncoderException, AuthException{
		
	}
}

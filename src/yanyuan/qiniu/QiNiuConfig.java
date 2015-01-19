package yanyuan.qiniu;

import java.util.UUID;

public class QiNiuConfig {
	public static String ACCESS_KEY = "QX-g14g_FPaI-d9EQ1ROL2O2rqxwhSzabxHNZSAJ";
	public static String SECRET_KEY = "o0qpt-ehMjLQGE5ia25TG1Ak89nYdLws3QidsTGl";
	public static String BUCKET_NAME = "jfinaltest";
	public static String RETURN_BODY = "{\"name\": $(fname),\"size\": $(fsize),\"w\": $(imageInfo.width),\"h\": $(imageInfo.height),\"hash\": $(etag)}";
	public static String DOMAIN = "jfinaltest.qiniudn.com";
	public static int UPLOAD_EXPIRES = 360;
	public static int DOWNLOAD_EXPIRES = 60;
	
	public static long getUploadExpires(){
		return (System.currentTimeMillis()/1000) + 120;
	}
	
	public static long getDwonloadExpires(){
		return (System.currentTimeMillis()/1000) + 60;
	}
	
	public static String getFileName(){
		return UUID.randomUUID().toString();
	}
}

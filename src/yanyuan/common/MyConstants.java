package yanyuan.common;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class MyConstants {
	public static final SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm");
	public static final SimpleDateFormat SQL_NAME_FORMAT = new SimpleDateFormat("yyyy_MM_dd");
	
	public static Map<String, String> SHL_MAPPING = new HashMap<String, String>();
	static{
		SHL_MAPPING.put("abap", "Abap");
		SHL_MAPPING.put("java", "Java");
		SHL_MAPPING.put("js", "JScript");
		SHL_MAPPING.put("css", "Css");
		SHL_MAPPING.put("cpp", "Cpp");
		SHL_MAPPING.put("cs", "CSharp");
		SHL_MAPPING.put("xml", "Xml");
		SHL_MAPPING.put("bash", "Bash");
	}

	public static Integer PAGE_SIZE;

	
}

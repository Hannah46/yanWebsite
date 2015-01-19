package yanyuan.model;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

// 这个类是 由mblog 创建的一个视图

@SuppressWarnings("serial")
public class blogview extends Model<blogview>{
	public static blogview dao = new blogview();
	
	public Page<blogview> paginate(int pageNumber, int pageSize, long id) 
	{
		return paginate(pageNumber, pageSize, "select *", "from blogview where reply_to=? and forbidden=0 order by pub_date desc", id);
	}
}

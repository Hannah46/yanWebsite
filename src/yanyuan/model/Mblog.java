package yanyuan.model;

 
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

@SuppressWarnings("serial")
public class Mblog extends Model<Mblog>{
	public static Mblog dao = new Mblog();
	
	public Page<Mblog> paginate(int pageNumber, int pageSize) 
	{
		return paginate(pageNumber, pageSize, "select *", "from blogs where reply_to=0 and forbidden=0 order by pub_date desc");
	}

}

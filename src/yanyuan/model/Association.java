package yanyuan.model;
 
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

@SuppressWarnings("serial")
public class Association extends Model<Association> {

	public static Association dao = new Association();
 
	
	public Page<Association> paginate(int pageNumber, int pageSize, long univ_id) 
	{
		return paginate(pageNumber, pageSize, "select *", "from association  where id_university=?", univ_id);
	}
	
}

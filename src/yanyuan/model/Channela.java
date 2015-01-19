package yanyuan.model;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

@SuppressWarnings("serial")
public class Channela extends Model<Channela> {
	public static Channela dao = new Channela();
	
	public Page<Channela> paginate(int pageNumber, int pageSize, long aso_id) 
	{
		return paginate(pageNumber, pageSize, "select *", "from channela  where id_association=? order by 1", aso_id);
	}

}

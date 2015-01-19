package yanyuan.model;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

@SuppressWarnings("serial")
public class Willing extends Model<Willing>{
	public static Willing dao = new Willing();
	
	public Page<Willing> getWillingsWithUser(int pageNumber, int pageSize){
		return paginate(pageNumber, pageSize, "SELECT w.ID ,w.`desc`,w.readers,w.agrees,w.pub_date,u.nickname ,u.photo1b", "from willing w INNER JOIN `user` u on u.id=w.id_user order by pub_date desc");
	}
}

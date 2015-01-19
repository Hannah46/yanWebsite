package yanyuan.model;

import java.math.BigInteger;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

@SuppressWarnings("serial")
public class Collection extends Model<Collection>{
	public static Collection dao = new Collection();
	
	public Page<Collection> listForUser(int pageNum,int pageSize, BigInteger id_user){
		return paginate(pageNum, pageSize, "select collection.id,collection.id_jobs,jobs.job,collection.collect_date", 
			"from collection inner join jobs on collection.id_jobs=jobs.id where collection.id_user="+id_user);
	}
}

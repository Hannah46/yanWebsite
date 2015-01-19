package yanyuan.model;

import java.math.BigInteger;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

@SuppressWarnings("serial")
public class PostedNotification extends Model<PostedNotification>{
	public static PostedNotification dao = new PostedNotification();
	
	//公司的投递列表
	public Page<PostedNotification> listForCompany(int pageNum,int pageSize,BigInteger id_company){
		return paginate(pageNum, pageSize, "SELECT j.job as job,p.id as id,p.id_resume as id_resume,p.posted_date as posted_date,p.refused as refused",
			"from company as c INNER JOIN jobs j on j.id_company=c.id INNER JOIN posted_notification as p on j.id=p.id_jobs WHERE c.id="+id_company + " order by p.posted_date desc");
	}
	
	//用户的投递列表
	public Page<PostedNotification> listForUser(int pageNum,int pageSize,BigInteger id_user){
		return paginate(pageNum, pageSize, "SELECT p.id,p.posted_date,p.noti_date,p.refused,p.desc1,j.job",
			"FROM posted_notification as p INNER JOIN jobs as j ON p.id_jobs=j.id WHERE p.id_resume in (SELECT id FROM resume WHERE id_user="+id_user+") order by p.posted_date desc");
	}
}

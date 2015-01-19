package yanyuan.model;

import java.math.BigInteger;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

@SuppressWarnings("serial")
public class Job extends Model<Job>{
	public static Job dao = new Job();
	//列表显示
	public Page<Job> jobList(int pageNum,int pageSize){
		return paginate(pageNum, pageSize, "SELECT jobs.id,jobs.job,jobs.city,jobs.sala,jobs.publishdate,company.fullname,jobs.property",
				"FROM jobs INNER JOIN company ON jobs.id_company=company.id order by jobs.publishdate desc");
	}
	
	//求职信息，包含公司名称
	public Model<Job> getWithCompanyName(BigInteger id){
		return findFirst("select jobs.*,company.fullname from jobs inner join company on jobs.id_company=company.id where jobs.id=? order by jobs.publishdate desc", id);
	}
	
	//公司的招聘信息，列表显示
	public Page<Job> jobListForCompany(int pageNum,int pageSize,BigInteger companyId){
		return paginate(pageNum, pageSize, "SELECT jobs.id,jobs.job,jobs.publishdate,jobs.city,jobs.sala",
			"FROM jobs where jobs.id_company=" + companyId + " order by jobs.publishdate desc");
	}
}

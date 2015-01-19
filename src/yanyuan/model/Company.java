package yanyuan.model;

import com.jfinal.plugin.activerecord.Model;

@SuppressWarnings("serial")
public class Company extends Model<Company>{
	public static Company dao = new Company();

	public boolean findByEmail(String email) {
		Company company = findFirst("select * from company where email=\"" + email+ "\"");
		if (company == null) {
			return false;
		}
			return true;
	}
	
	public boolean findByFullname(String fullname) {
		Company company = findFirst("select * from company where fullname=\"" + fullname+ "\"");
		if (company == null) {
			return false;
		}
		
		return true;
	}
}

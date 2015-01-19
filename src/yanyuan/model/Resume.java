package yanyuan.model;

import java.util.List;

import com.jfinal.plugin.activerecord.Model;

@SuppressWarnings("serial")
public class Resume extends Model<Resume>{

	public static Resume dao = new Resume();
	
	public static String ID_USER = "id_user";
	
	protected List<Resume> _resumeList;
	public List<Resume> getResumeList(String id_user) {
		if (_resumeList == null) {
			_resumeList = Resume.dao.find("select * from resume where id_user = ?", id_user);
		}
		return _resumeList;
	}
			
}

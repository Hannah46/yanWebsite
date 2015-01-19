package yanyuan.model;

import java.util.List;

import com.jfinal.plugin.activerecord.Model;

@SuppressWarnings("serial")
public class Experience extends Model<Experience>{
	public static Experience dao = new Experience();
	
	protected List<Experience> _listExperience;
	public List<Experience> getListExperience(String id_resume)
	{
		if (_listExperience == null) {
			_listExperience = Experience.dao.find("select * from experience where id_resume = ?", id_resume);
		}
		return _listExperience;
	}

}

package yanyuan.model;

import java.util.List;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

@SuppressWarnings("serial")
public class User extends Model<User>{
	public static User dao = new User();
	
	//昵称存在时返回true，不存在时返回false
	public boolean findByNickname(String nickName){
		User user = findFirst("select * from user where nickname=\"" + nickName + "\"");
		if (user == null) {
			return false;
		}
		return true;
	}
	
	//email存在时返回true，不存在时返回false
	public boolean findByEmail(String email){
		User user = findFirst("select * from user where email=?", email);
		if (user == null) {
			return false;
		}
		return true;
	}
	
	public Page<User> paginate(int pageNumber, int pageSize){
		return paginate(pageNumber, pageSize, "select *", "from user");
	}
	
	//分页显示已认证或未认证的用户列表
	public Page<User> getUserList(int pageNumber, int pageSize, int flag) {
		return paginate(pageNumber, pageSize, "select * ", "from user where flag = ?",flag);
	}
	
	//分页显示认证中模糊查找的结果列表
	public Page<User> getUserListByEmail(int pageNumber, int pageSize,  String email, int flag) {
		if ((email == "") || (email.equals("null")) ) {
			email = "%%";
		}
		else {
			email = "%"+ email +"%";
		}
		return paginate(pageNumber, pageSize, "select * ", "from user where email like ? and flag = ? ", email, flag);
	}
	
	//分页显示搜索的结果列表
	public Page<User> getSearchUserList(int pageNumber, int pageSize, String sql, Object[] paras) {
		return paginate( pageNumber, pageSize, "select *", sql, paras);
	}
}

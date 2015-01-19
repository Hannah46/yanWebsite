package yanyuan.model;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import com.jfinal.plugin.activerecord.Model;

public class Mail extends Model<Mail>{
	public static Mail dao = new Mail(); 
	
	//获取发件箱邮件列表
	private List<Mail> _sendMailList;
	public List<Mail> getSendMailList (BigInteger from_id, int from_type) {
		String sql = "select m.*, a.name as to_name from mail m inner join association a  "
				+ "where m.spam = 0 and m.reply_to_id = 0 and m.id_to = a.id and m.to_type = 3 and m.id_from = ? and m.from_type = ? " 
				+ "UNION select m.*, u.nickname as to_name from mail m inner JOIN user u "
				+ "where m.spam = 0 and m.reply_to_id = 0 and m.id_to = u.id and m.to_type = 1 and m.id_from = ? and m.from_type = ? "
				+ "UNION select m.*, c.fullname as to_name from mail m inner JOIN company c "
				+ "where m.spam = 0 and m.reply_to_id = 0 and m.id_to = c.id and m.to_type = 2 and m.id_from = ? and m.from_type = ? "
				+ "order by pub_date DESC";
		_sendMailList = Mail.dao.find(sql,from_id, from_type,from_id, from_type, from_id, from_type);				
		return _sendMailList;
	} 
	
	//获取收件箱邮件列表
	private List<Mail> _recieveMailList;
	public List<Mail> getRecieveMailList (BigInteger to_id, int to_type) {
		String sql = "select m.*, a.name as from_name from mail m inner join association a "
				+ "where m.spam = 0 and m.reply_to_id = 0 and m.id_from = a.id and m.from_type = 3 and m.id_to = ? and m.to_type = ? " 
				+ "UNION select m.*, c.fullname as from_name from mail m inner JOIN company c "
				+ "where m.spam = 0 and m.reply_to_id = 0 and m.id_from = c.id and m.from_type = 2 and m.id_to = ? and m.to_type = ? "
				+ "UNION select m.*, u.nickname as from_name from mail m inner JOIN user u "
				+ "where m.spam = 0 and m.reply_to_id = 0 and m.id_from = u.id and m.from_type = 1 and m.id_to = ? and m.to_type = ? "
				+ "order by pub_date DESC";
		_recieveMailList = Mail.dao.find( sql, to_id, to_type, to_id, to_type,to_id, to_type);
		return _recieveMailList;
	}
	
	//获取垃圾箱邮件
	private List<Mail> _spamMailList;
	public List<Mail> getSpamMailList (BigInteger from_id, int from_type) {
		String sql = "select m.*, a.name as to_name from mail m inner join association a  "
				+ "where m.spam = 1 and m.reply_to_id = 0 and m.id_from = a.id and m.from_type = 3 and m.id_to = ? and m.to_type = ? " 
				+ "UNION select m.*, c.fullname as from_name from mail m inner JOIN company c "
				+ "where m.spam = 1 and m.reply_to_id = 0 and m.id_from = c.id and m.from_type = 2 and m.id_to = ? and m.to_type = ? "
				+ "UNION select m.*, u.nickname as from_name from mail m inner JOIN user u "
				+ "where m.spam = 1 and m.reply_to_id = 0 and m.id_from = u.id and m.from_type = 1 and m.id_to = ? and m.to_type = ? "
				+ "order by pub_date DESC";
		_spamMailList = Mail.dao.find(sql,from_id, from_type, from_id, from_type, from_id, from_type);				
		return _spamMailList;
	}

	//获取发件人编号
	private BigInteger id_to;
	public BigInteger getIdTo(String username, int to_type) {
		if (to_type == 2 ) {
			Company company = Company.dao.findFirst("select * from company where fullname = ?", username);
			id_to = company.getBigInteger("id");
		}
		else if (to_type == 1) {
			User user = User.dao.findFirst("select * from user where nickname = ?", username);
			id_to = user.getBigInteger("id");
		}
		return id_to;	
	}
	
	//获取私信回复列表
	private List<Mail> _replyMailList;
	public List<Mail> getReplyMailList (int id_mail) {
		Mail mail = dao.findById(id_mail);
		String sql = "select m.*, a.name as username from mail m inner join association a  "
				+ "where m.reply_to_id = ? and m.id_from = a.id and m.from_type = 3 " 
				+ "UNION select m.*, c.fullname as from_name from mail m inner JOIN company c "
				+ "where m.reply_to_id = ? and m.id_from = c.id and m.from_type = 2  "
				+ "UNION select m.*, u.nickname as from_name from mail m inner JOIN user u "
				+ "where m.reply_to_id = ? and m.id_from = u.id and m.from_type = 1  "
				+ "order by pub_date ASC";
		_replyMailList = Mail.dao.find(sql, id_mail, id_mail, id_mail);
		return _replyMailList;		
	}
}

package yanyuan.controller.friend;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import yanyuan.interceptor.CompanySessionInterceptor;
import yanyuan.model.Company;
import yanyuan.model.Mail;
import yanyuan.validator.MailValidator;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;

@Before(CompanySessionInterceptor.class)
public class MailController extends Controller{
	public void index() {
		Mail mail = getModel(Mail.class);
		Company company = getSessionAttr("company");
		//获取收件箱、发件箱、垃圾箱
		setAttr("sendMailList", mail.getSendMailList(company.getBigInteger("id"), 2));
		setAttr("recieveMailList", mail.getRecieveMailList(company.getBigInteger("id"), 2));
		setAttr("spamMailList", mail.getSpamMailList(company.getBigInteger("id"), 2));
		render("/mail.html");
	}
	
	public void viewMail() {
		Mail mail = Mail.dao.findById(getParaToInt(0));
		mail.set("flag", 1);
		mail.update();
		setAttr("mail", mail);
		String p2 = getPara(1);
		try {
			p2 = URLDecoder.decode(p2,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setAttr("username", p2);
		render("/mail_detail.html");
	}
	
	//查看回复列表
	public void viewReply() {
		Mail mail = getModel(Mail.class);
		mail = Mail.dao.findById(getParaToInt(0));
		mail.set("flag", 1);
		mail.update();
		setAttr("parent", mail);
		setAttr("replyMailList", mail.getReplyMailList(getParaToInt(0)));
		//获取登录公司的全称，方便回复列表显示为“我”
		Company company = getSessionAttr("company");
		String p1 = getPara(1);
		try {
			p1 = URLDecoder.decode(p1,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setAttr("from_name", p1);
		setAttr("self", company.getStr("fullname"));
		render("/reply_mail_list.html");
	}
	
	//设置垃圾私信&恢复正常私信
	public void setSpam() {
		Mail mail = Mail.dao.findById(getParaToInt(0));
		if (mail.getInt("spam") == 0)
			mail.set("spam", 1);
		else
			mail.set("spam", 0);
		mail.update();
		renderText("标记成功");
	}
	
	//回复私信
	public void reply() {
		Mail mail = Mail.dao.findById(getParaToInt(0));
		
		//对回复私信进行赋值
		Mail _newmail = getModel(Mail.class);
		_newmail.set("id_to", mail.getLong("id_from"));
		_newmail.set("to_type", mail.getInt("from_type"));
		int reply_to_id = mail.getLong("reply_to_id").intValue();
		if (reply_to_id == 0)
			_newmail.set("reply_to_id", mail.getBigInteger("id"));
		else
			_newmail.set("reply_to_id", mail.getLong("reply_to_id"));
		_newmail.set("id_from", mail.getLong("id_to"));
		_newmail.set("from_type", mail.getInt("to_type"));
		String p1 = getPara(1);
		try {
			p1 = URLDecoder.decode(p1,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Date date = new Date();
		_newmail.set("pub_date", date);		
		_newmail.set("content", p1);
		_newmail.set("title", "回复"+mail.getStr("title"));
		_newmail.save();
		
		//将原始邮件设置为未读
		mail.set("flag", 0);
		mail.update();
		renderText("回复成功");
	}
	
	//发送新私信
	public void add() {
		Mail mail = getModel(Mail.class);
		Company company = getSessionAttr("company");
		mail.set("id_from", company.get("id"));
		mail.set("from_type", 2);
		mail.set("reply_to_id", 0);
		setAttr("mail",mail);
		render("/add_mail.html");
	}

	@Before(MailValidator.class)
	public void save() {
		Mail mail = getModel(Mail.class);
		int reply_to_id = mail.getLong("reply_to_id").intValue();
		if (mail.getLong("id_to") == 0) {
			String userName = getPara("username");
			int to_type = mail.getInt("to_type");
			mail.set("id_to", Mail.dao.getIdTo(userName, to_type));
		}
		Date date = new Date();
		mail.set("pub_date", date);
		mail.save();
		renderText("保存成功！");
	}
	
	public void delete() {
		Mail.dao.deleteById(getParaToInt());
	}
	
	
}

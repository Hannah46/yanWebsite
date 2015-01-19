package yanyuan.controller.home;


import yanyuan.model.Association;
import yanyuan.model.Company;
import yanyuan.model.Channela;
import yanyuan.model.Mblog;
import yanyuan.model.User;
import yanyuan.model.blogview;
import yanyuan.qiniu.QiNiuFile;
import yanyuan.qiniu.QiNiuUtils;

import java.util.List;

import com.jfinal.plugin.activerecord.Page;
import com.jfinal.core.Controller;


public class MblogController extends Controller {
	
	public void report()  //举报
	{
		Association aso = getSessionAttr("association");
	    Company company = getSessionAttr("company");
	    User user = getSessionAttr("user");
	    if( aso==null && company==null && user==null)
	    { redirect("/jiayuan/login.html");
	      return;
	    }		
		
		long blog_id = Long.parseLong( getPara(0) );
        Mblog mblog = getModel( Mblog.class ).findById( blog_id );
		mblog.set("reported", 1);
		if( user!=null)
		{  mblog.set("id_report_user", user.get("id"));
		   mblog.set("report_user_type", 1);
		}
		else if( company!=null)
		{  mblog.set("id_report_user", company.get("id"));
		   mblog.set("report_user_type", 2);
		}
		else if( aso!=null)
		{  mblog.set("id_report_user", aso.get("id"));
		   mblog.set("report_user_type", 3);
		}
		
		mblog.update();
		renderJson("{\"error\":0}");
	}

	public void agree()  //点赞
	{
		Association aso = getSessionAttr("association");
	    Company company = getSessionAttr("company");
	    User user = getSessionAttr("user");
	    if( aso==null && company==null && user==null)
	    { redirect("/jiayuan/login.html");
	      return;
	    }		

	    long blog_id = Long.parseLong( getPara(0) );
        Mblog mblog = getModel( Mblog.class ).findById( blog_id );
        int agrees = mblog.get("agree");
        agrees++;
        mblog.set("agree", agrees);
        mblog.update();
        
        renderJson("{\"counter\":" + agrees + "}");
	}

	public void to_new()
	{   Association aso = getSessionAttr("association");
	    Company company = getSessionAttr("company");
	    if( aso==null && company==null)
	    { redirect("/jiayuan/login.html");
	      return;
	    }
		if( aso !=null )
		{
		   setAttr("company_type",0);
		   List<Channela> clist = Channela.dao.find("select * from channela where id_association=?" , aso.get("id") );
		   setAttr("channel_list", clist);
		}	
		if( company!=null )
		  setAttr("company_type",1);
		setAttr("token", QiNiuUtils.getToken());
		setAttr("key", QiNiuUtils.getKey());
		render("../jiayuan/mblog_new.html");			
	}
	
	public void add()
	{   Association aso = getSessionAttr("association");
        Company company = getSessionAttr("company");
		if( aso==null && company==null)
		{  redirect("/jiayuan/login.html");
		   return;
		}
		Mblog blog = getModel(Mblog.class);
		if(company!=null)
		  {  blog.set("id_user", company.get("id"));
		     blog.set("user_type", 2);
		  }
		else if(aso!=null)
		  {  blog.set("id_user", aso.get("id"));
		     blog.set("user_type", 3);
		  }
		String currentPic = blog.get("photo1");
		if( currentPic!=null )
		{
			//生成缩略图
			String thumbPhoto="";
			try {  thumbPhoto = QiNiuFile.makeThumb(currentPic); }
	        catch(Exception e1) { e1.printStackTrace(); }
			blog.set("photo1",thumbPhoto);
			QiNiuFile.delete(currentPic);
		}
		blog.save();
		renderJson("{\"error\":0}");
	}
	
	public void list()
	{   
		int currentPage = getParaToInt(0);
		blogview bv = getModel( blogview.class );
		Page<blogview> bloglist = bv.paginate(currentPage, 10 ,0);
		int tp = bloglist.getTotalPage();
		if( currentPage>tp )
		{  
		  currentPage=tp;
		  bloglist = bv.paginate(currentPage, 10 ,0);
		}
		setAttr("pages", bloglist);
		//System.out.println( FreeMarkerRender.getConfiguration().getSharedVariable("path") );    		
		render("../jiayuan/mblog_list.html");
	}

	public void comments_list()
	{   
		long blog_id = Long.parseLong( getPara(0) );
		int currentPage = getParaToInt(1);

		blogview bv = getModel( blogview.class );
 	    blogview topic = bv.findFirst("select * from blogview where reply_to=0 and id="+getPara(0));
        setAttr("topic", topic);

		Page<blogview> bloglist = bv.paginate(currentPage, 20 ,blog_id);
		setAttr("pages", bloglist);
		render("../jiayuan/mblog_comments.html");
	}	

	public void add_comment()
	{   
		Association aso = getSessionAttr("association");
        Company company = getSessionAttr("company");
        User user = getSessionAttr("user");
	    if( aso==null && company==null && user==null)
	    { redirect("/jiayuan/login.html");
	      return;
	    }		

	    long blog_id = Long.parseLong( getPara(0) );
		//int currentPage = getParaToInt(0);
		Mblog mblog = getModel( Mblog.class );
		if( user!= null)
          { mblog.set("user_type", 1);
            mblog.set("id_user", user.get("id"));
          }
		else if( company!= null)
        { mblog.set("user_type", 2);
          mblog.set("id_user", company.get("id"));
        } 
		else if( aso!= null)
        { mblog.set("user_type", 3);
          mblog.set("id_user", aso.get("id"));
        } 
		
        mblog.set("reply_to", blog_id);
        mblog.save();
        
        Mblog another = mblog.findById( blog_id );
        int comments = another.get("comments");
        another.set("comments", comments+1);
        another.update();
//FreeMarkerRender.getConfiguration().getSharedVariable("path")    
		redirect("/mblog/comments_list/"+getPara(0)+"-"+getPara(1));
 	}	

}

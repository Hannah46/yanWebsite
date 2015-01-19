package yanyuan.controller.home;

import java.math.BigInteger;

import yanyuan.common.CommonConfig;
import yanyuan.model.Association;
import yanyuan.model.Company;
import yanyuan.model.University;
import yanyuan.model.Channela;
import yanyuan.model.User;
import yanyuan.qiniu.QiNiuFile;
import yanyuan.qiniu.QiNiuUtils;
import yanyuan.interceptor.AssociationSession;
import yanyuan.interceptor.AssociationUnionSession;

import com.jfinal.kit.StringKit;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.render.FreeMarkerRender;
import com.jfinal.aop.Before;
import com.jfinal.aop.ClearInterceptor;
import com.jfinal.aop.ClearLayer;
import com.jfinal.core.Controller;

import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;


public class AssociationController extends Controller {
	
	public void index()  //  家园的 index.html
	{
		TemplateModel f = FreeMarkerRender.getConfiguration().getSharedVariable("logged"); 
        if( f==null)
			try { FreeMarkerRender.getConfiguration().setSharedVariable("logged", 0); } 
			catch (TemplateModelException e) {System.out.println("set freemarkerrender share variable failed");}
		render("../jiayuan/index.html");
	}

	@Before(AssociationUnionSession.class)
	public void list()
	{   
		long univ_id = Long.parseLong( getPara(0) );
		int currentPage = getParaToInt(1);
		Association ac = getModel( Association.class );
		Page<Association> aclist = ac.paginate(currentPage, 10 , univ_id );
		int tp = aclist.getTotalPage();
		if( currentPage>tp )
		{  
		  currentPage=tp;
		  aclist = ac.paginate(currentPage, 10 , univ_id );
		}
		setAttr("university_id", getPara(0) );
		setAttr("page", aclist);
		render("../jiayuan/association_list.html");
	}

	@Before(AssociationUnionSession.class)
	public void del()
	{
		long aso_id = Long.parseLong( getPara(0) );
		Association ac = getModel( Association.class );
		Association a2 = ac.findById(aso_id);
		String pict1 = a2.getStr("pict1");   //删除图片
		if( pict1!=null)
		  QiNiuFile.delete(pict1);
		String pict2 = a2.getStr("pict2");
		if( pict2!=null)
		  QiNiuFile.delete(pict2);
		ac.deleteById( aso_id );
		renderJson("{\"error\":0}");
	}

	@Before(AssociationUnionSession.class)
	public void pause()
	{  
		long aso_id = Long.parseLong( getPara(0) );
		int  flag   = Integer.parseInt( getPara(1) );
		flag = (flag==1 ? 0:1);
		Association ac = getModel( Association.class );
		ac.findById( aso_id ).set("flag", flag).update();
//		JSONObject json = new JSONObject();
//		json.put("error", 0);
//		json.put("msg", "error");
//		renderJson(json.toJSONString());
		renderJson("{\"error\":0}");
	}
	
//	@ClearInterceptor
//	public void login()
//	{   if( getSessionAttr("user")!=null || getSessionAttr("association")!=null || getSessionAttr("association_union")!=null)
//		  redirect("../jiayuan/index.html");
//	    else
//		  render("../jiayuan/login.html");
//	}
	
	public void doLogin(){  //社区管理员登录
		if( getSessionAttr("user")!=null || getSessionAttr("association")!=null || getSessionAttr("association_union")!=null)
		  { index();
		    return;
		  }
		Association association = getModel(Association.class);
		association = association.dao.findFirst("select * from association where email=? and passwd=?", 
				association.getStr("email"),association.getStr("passwd"));
		if (association == null) {
			renderJson("{\"error\":1}");
		}
		else {
			setSessionAttr("association", association);
			if(getSessionAttr("user")!=null)
			  removeSessionAttr("user");
			if(getSessionAttr("association_union")!=null)
				  removeSessionAttr("association_union");			
			if(getSessionAttr("company")!=null)
				  removeSessionAttr("company");
			try { FreeMarkerRender.getConfiguration().setSharedVariable("logged", 3); } 
			catch (TemplateModelException e) {System.out.println("set freemarkerrender share variable failed");}			
			renderJson("{\"error\":0}");
		}
	}

	
	@Before(AssociationSession.class)
	public void menu(){                        //协会管理员菜单
		Association association = getSessionAttr("association");
		setAttr("association", association);
		render("../jiayuan/association.html");
	}

	@Before(AssociationSession.class)
	public void detail(){
		Association association = getSessionAttr("association");
		//long aso_id = Long.parseLong( association.getStr("id") );
		association = association.dao.findFirst("select association.*, university.name as university_name from association left join university on association.id_university = university.id where association.id=?", association.get("id"));
		setAttr("association", association);
		setAttr("edit_mode",1);                            //有限编辑能力
		setAttr("token", QiNiuUtils.getToken());
		setAttr("key", QiNiuUtils.getKey());
		render("../jiayuan/association_detail.html");
	}
	
	public void detail_update()  
	{
		if( getSessionAttr("association")==null || getSessionAttr("association_union")==null)
		{ index();
		  return;
		}
		Association association = getModel(Association.class);
		association.update();
		renderJson("{\"error\":0}");
	}
	
	public void makeThumb()  //制作对应缩略图
	{   Association ac;
	    long aso_id;
	    try { aso_id = Long.parseLong( getPara(0) ); }
	    catch(Exception e1) { aso_id=-1; }

		System.out.println("aso_id=" + aso_id);
		String oldPicture = getPara();
		int i=oldPicture.indexOf("-");
		if( i>0)
		  oldPicture  = oldPicture.substring(i+1);
		if( aso_id>0 )
		   ac = getModel( Association.class ).findById( aso_id );
		else
		   ac = getModel( Association.class ).findFirst("select * from association where pict1=?", oldPicture );
		//删除原有图片
		if (!StringKit.isBlank(oldPicture) && aso_id>0) 
		{ QiNiuFile.delete(oldPicture);
		  System.out.println("delete old=" + oldPicture);
		}
		
		//生成缩略图
		String currentPic = ac.getStr("pict1");
		String thumbPhoto="";
		try {  thumbPhoto = QiNiuFile.makeThumb(currentPic); }
        catch(Exception e1) { e1.printStackTrace(); }
		ac.set("pict1",thumbPhoto);
        ac.update();
  	    System.out.println(" thumb=" + thumbPhoto + "current="+currentPic);

		QiNiuFile.delete(currentPic);
		System.out.println("delete2=" + currentPic);
		renderText("");
	}	

//	public void mlogin(){         
//		render("../jiayuan/login_m_association.html");
//	}
	
	public void domLogin(){   //社区联合会登录
		if( getSessionAttr("user")!=null || getSessionAttr("association")!=null || getSessionAttr("association_union")!=null)
		{ index();
		  return;
		}
		University uv = getModel(University.class);
		uv = uv.dao.findFirst("select * from university where email=? and passwd=?", 
				uv.getStr("email"),uv.getStr("passwd"));
		if (uv == null) {
			renderJson("{\"error\":1}");
		}
		else {
			setSessionAttr("association_union", uv);
			if(getSessionAttr("association")!=null)
				  removeSessionAttr("association");
			if(getSessionAttr("user")!=null)
				  removeSessionAttr("user");			
			if(getSessionAttr("company")!=null)
				  removeSessionAttr("company");
			try { FreeMarkerRender.getConfiguration().setSharedVariable("logged", 4); } 
			catch (TemplateModelException e) {System.out.println("set freemarkerrender share variable failed");}			
			renderJson("{\"error\":0}");
		}
	}
	
	@Before(AssociationUnionSession.class)
	public void manage(){
		University uv = getSessionAttr("association_union");
		setAttr("university_id", uv.get("id"));
		setAttr("university_name", uv.get("name")  );
		render("../jiayuan/association_manage2.html");
	}
	
	@Before(AssociationSession.class)
	public void channel_list(){
		Association association = getSessionAttr("association");
		int currentPage = getParaToInt(0);
		Channela channel = getModel( Channela.class );
		BigInteger tb = new BigInteger( association.get("id").toString() );
		long aso_id = tb.longValue();
		Page<Channela> channel_list = channel.paginate( currentPage, 10 , aso_id );
		int tp = channel_list.getTotalPage();
		if( currentPage>tp )
		{  
		  currentPage = tp;
		  channel_list = channel.paginate(currentPage, 10 , aso_id );
		}
		setAttr("association_id", aso_id);
		setAttr("page", channel_list);
		render("../jiayuan/association_channel.html");
	}
 
	@Before(AssociationUnionSession.class)
	public void add()
	{  
		getModel(Association.class).save();
		renderJson("{\"error\":0}");
	}
	
	@Before(AssociationUnionSession.class)
	public void to_edit()  //产生一个编辑页面
	{
		Association association = getModel(Association.class);
		association = association.dao.findFirst("select association.*, university.name as university_name from association left join university on association.id_university = university.id where association.id=?", getPara(0));
 
		setAttr("association", association);
		setAttr("edit_mode",2);                            //全编辑能力
		setAttr("token", QiNiuUtils.getToken());
		setAttr("key", QiNiuUtils.getKey());
		render("../jiayuan/association_detail.html");
	
	}
	
	@Before(AssociationUnionSession.class)
	public void to_add()  //产生一个新建页面
	{
		setAttr("university_id", getPara(0)); 
		setAttr("association", null);
		setAttr("edit_mode",3);                   
		setAttr("token", QiNiuUtils.getToken());
		setAttr("key", QiNiuUtils.getKey());
		render("../jiayuan/association_detail.html");
	}
}

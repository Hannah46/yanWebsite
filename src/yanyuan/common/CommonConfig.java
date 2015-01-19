package yanyuan.common;

import org.apache.log4j.Logger;

import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.wall.WallFilter;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.ext.handler.ContextPathHandler;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.plugin.ehcache.EhCachePlugin;
import com.jfinal.render.FreeMarkerRender;
import com.jfinal.render.ViewType;

import yanyuan.common.MyConstants;
import yanyuan.controller.friend.CImage;
import yanyuan.controller.friend.CompanyAction;
import yanyuan.controller.friend.CompanyInfo;
import yanyuan.controller.friend.MailController;
import yanyuan.controller.friend.UserAction;
import yanyuan.controller.friend.UserInfo;
import yanyuan.controller.friend.WillingController;
import yanyuan.controller.job.CollectionController;
import yanyuan.controller.job.ExperienceController;
import yanyuan.controller.job.JobController;
import yanyuan.controller.job.PostedNotificationController;
import yanyuan.controller.job.ResumeController;
import yanyuan.controller.home.AssociationController;
import yanyuan.controller.home.ChannelaController;
import yanyuan.controller.home.MblogController;
import yanyuan.controller.friend.FriendIndex;
import yanyuan.common.UniversityController;
import freemarker.template.TemplateModelException;
import yanyuan.model.Collection;
import yanyuan.model.Company;
import yanyuan.model.Experience;
import yanyuan.model.Job;
import yanyuan.model.Mail;
import yanyuan.model.PostedNotification;
import yanyuan.model.Resume;
import yanyuan.model.User;
import yanyuan.model.Willing;
import yanyuan.model.Association;
import yanyuan.model.University;
import yanyuan.model.Channela;
import yanyuan.model.Mblog;
import yanyuan.model.blogview;
import yanyuan.qiniu.QiNiuUtils;
//import com.jfinal.render.ViewType;
import yanyuan.utils.ReadPropertity;

 
public class CommonConfig extends JFinalConfig {
	
	private boolean devMode;
	private static final Logger log = Logger.getLogger(CommonConfig.class);	
	/**
	 * ���ó���
	 */
	@Override
	public void configConstant(Constants me) {
		loadPropertyFile("config.properties");
		devMode = Boolean.parseBoolean(ReadPropertity.getProperty("devMode"));
		me.setDevMode(devMode);
		//
		me.setBaseViewPath("/");
		//me.setBaseViewPath("/WEB-INF/ftl");
		//me.setError404View("/404.html");
		me.setViewType(ViewType.FREE_MARKER); 	
	}
	
	/**
	 * ����·��
	 */
	@Override
	public void configRoute(Routes me) {
		me.add("/", HomeController.class);
		//用户信息
		me.add("/user",UserInfo.class);
		//取得token
		me.add("/qiNiuUtils",QiNiuUtils.class);
		//照片
		me.add("/photo",CImage.class);
		//用户登录
		me.add("/userAction",UserAction.class);
		//许愿墙
		me.add("/willing",WillingController.class);
		//职位
		me.add("/job",JobController.class);
		//投递和通知
		me.add("/post",PostedNotificationController.class);
		//收藏
		me.add("collection",CollectionController.class);
		
		me.add("/company",CompanyInfo.class);
		me.add("/resume",ResumeController.class);
		me.add("/experience", ExperienceController.class);
		me.add("/companylogin", CompanyAction.class);
		me.add("/mail",MailController.class);
		
		me.add("/university", UniversityController.class);
		me.add("/association", AssociationController.class);
		me.add("/channela", ChannelaController.class);		
		me.add("/mblog", MblogController.class);		
		me.add("/friend", FriendIndex.class);
		
		//me.add("/sysadmin", AdminController.class);
		//me.add("/article", com.mike.controller.ArticleController.class,"article");
		//me.add("/categorySuper", CategorySuperController.class, "article");
		//me.add("/article", ArticleController.class);
	}
	
	/**
	 * ���ò��
	 */
	@Override
	public void configPlugin(Plugins me) {
		/**����Druid���ݿ����ӳز��**/
		DruidPlugin dp = new DruidPlugin(ReadPropertity.getProperty("jdbcUrl"), ReadPropertity.getProperty("user"), ReadPropertity.getProperty("password"));
		dp.addFilter(new StatFilter());
		//dp.setMaxActive(maxActive)  �������Ż�����
		WallFilter wall = new WallFilter();
		wall.setDbType(ReadPropertity.getProperty("dbType"));
		dp.addFilter(wall);
		me.add(dp);
		
		/**����ActiveRecord���**/
		ActiveRecordPlugin arp = new ActiveRecordPlugin(dp);
		if( devMode )
		  arp.setShowSql(true);
		me.add(arp);
		
		arp.addMapping("user", User.class);
		arp.addMapping("company", Company.class);
		arp.addMapping("willing", Willing.class);
		arp.addMapping("jobs", Job.class);
		arp.addMapping("posted_notification", PostedNotification.class);
		arp.addMapping("collection", Collection.class);
		
		arp.addMapping("company", Company.class);
		arp.addMapping("resume", Resume.class);
		arp.addMapping("experience", Experience.class);
		arp.addMapping("mail", Mail.class);
		arp.addMapping("university", University.class);
		arp.addMapping("association", Association.class);
		arp.addMapping("channela", Channela.class);
		arp.addMapping("mblog", Mblog.class);
		arp.addMapping("blogview", blogview.class);
		
		/**����EhCache���**/
		//me.add(new EhCachePlugin());
		
		//arp.addMapping("admin", Admin.class);
		//arp.addMapping("admin", Admin.class)�����������Ϊ������ΪĬ��Ϊ��id���������������Ϊ ��user_id������Ҫ�ֶ�ָ�����磺arp.addMapping(arp.addMapping(arp.addMapping(arp.addMapping(arp.addMapping(��useruser��, ��user_id��, User, User, User.class).
	}
	
	/**
	 * ����ȫ��������
	 */
	@Override
	public void configInterceptor(Interceptors me) {
		
	}
	
	/**
	 * ���ô�����
	 */
	@Override
	public void configHandler(Handlers me) {
		//me.add(new FakeStaticHandler());
		me.add(new GlobalHandler());
	}

	@Override
	public void afterJFinalStart() {
		//  TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
		MyConstants.PAGE_SIZE = getPropertyToInt("pageSize");
		try {
			FreeMarkerRender.getConfiguration().setSharedVariable("pageSize", MyConstants.PAGE_SIZE);
		} catch (TemplateModelException e) {
			log.error("set freemarkerrender share variable failed", e);
		}
	}

}

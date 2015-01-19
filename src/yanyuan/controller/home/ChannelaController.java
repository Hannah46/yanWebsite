package yanyuan.controller.home;


import yanyuan.model.Channela;

import com.jfinal.plugin.activerecord.Page;
import com.jfinal.core.Controller;

public class ChannelaController extends Controller {
	
	public void add()
	{  
		getModel(Channela.class).save();
		//new Channela().set("id_association", getPara("channela.id_association")).set("name", getPara("channela.name")).save();
		renderJson("{\"error\":0}");
	}
 
	public void del()
	{
		Channela a = getModel( Channela.class );
		a.deleteById( getPara(0) );
		renderJson("{\"error\":0}");
	}
 
	public void update()
	{
		Channela channelA = getModel(Channela.class);
		channelA.update();
		renderJson("{\"error\":0}");
	}
	
}

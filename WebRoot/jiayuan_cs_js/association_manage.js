$.ajaxSetup ({ 
    cache: false //关闭AJAX相应的缓存 
});

var univ_name, univ_id;


function onMSGClosed(){  //对话框关闭后，根据选择值动态加载内容
	if( univ_name !="")
	  { $("#university_name").val( univ_name );
	    //$("#list").html("");
	    $("#list").load( '../association/list/' + univ_id + "-1" );

	  }
}
function selectUniversity() {
	    univ_name = "";
        window.m2 = new MessageBox(onMSGClosed, "");
        m2.CloseButtonValue = "";
        m2.Title = "";
        m2.InnerDivHeight = 700;
        m2.InnerDivWidth = 825;
        m2.ShowIframeDialog("../data3.html");
}

function refresh_page(pageNumber)  //换页
{
  $("#list").load( '../association/list/' + univ_id + "-" + pageNumber ); 
  
}
 
function del_aso(id,university_id,pageNumber)
{
	$.ajax({
		url:'../association/del/'+id,
		type:'post',
		dataType:'json',
		data:{ },
		success : function(data) //刷新画面
		     {
			   $("#list").load( "../association/list/" + university_id  + "-" + pageNumber );
		     }
	});
};

function edit_aso(id,university_id,pageNumber)
{
	//window.location.href = '../jiayuan/association_detail_wrapper.html?id='+id ;
	window.location.href = '../association/to_edit/'+id ;
};

function pause_aso(id,status,university_id,pageNumber)
{
	$.ajax({
		url:'../association/pause/'+id+'-'+status,
		type:'post',
		dataType:'json',
		data:{ },
		success : function(data)
		     {
			   $("#list").load( "../association/list/" + university_id  + "-" + pageNumber );
		     }
	});
};

function GetQueryString(name)      
{      
     var reg = new RegExp("(^|&)"+     name     +"=([^&]*)(&|$)");      
     var r = window.location.search.substr(1).match(reg);      
     if(r!=null)
        return unescape(r[2]);   
     return   null;      
}      

$.ajaxSetup ({ 
    cache: false //关闭AJAX相应的缓存 
});


function refresh_page(pageNumber)  //换页
{
  $("#menu2").load( '../association/channel_list/' + pageNumber ); 
}
 
function add_channel(association_id)
{
	$.ajax({
		url:'../channela/add/',
		type:'post',
		dataType:'json',
		data:{ 
			   'channela.id_association':association_id,
			   'channela.name':$('#new_channela').val()
		      },
		success : function(data) //刷新画面
		     {
			   $("#menu2").load( "../association/channel_list/99999");
		     }
	});
}

function edit_channel(channel_id,index,pageNumber)
{   
	var tx = $("#edit"+index).text();
	if( tx=='编辑')
	{
		$("#edit"+index).html('保存');
		//$("#edit"+index).css('display','none');
		$('#channela_rowa'+index).css('display','none');
		$('#channela_rowb'+index).css('display','inherit');		
	}	
	else
	  {	
	    $.ajax({
		url:'../channela/update/'+channel_id,
		type:'post',
		dataType:'json',
		data:{ 
			   'channela.id':channel_id,
			   'channela.name':$('#channela_rowb'+index).val()
		      },
		success : function(data) //刷新画面
		     {
			   $("#menu2").load( "../association/channel_list/" + pageNumber );
		     }
	  });
	  } 
};

function del_channel(channel_id,pageNumber)
{   
	    $.ajax({
		url:'../channela/del/'+channel_id,
		type:'post',
		dataType:'json',
		data:{  },
		success : function(data) //刷新画面
		     {
			   $("#menu2").load( "../association/channel_list/" + pageNumber );
		     }
	  });
};


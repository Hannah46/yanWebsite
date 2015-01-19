function process_file(company_flag)
{
	if(company_flag == 0)
	  { val=$('input:radio[name="mblog.id_channel"]:checked').val();
	    if(val==null)
	      {
		    alert("请选择内容标签!");
		    return false;
	      }
	  }
	if ($("#file").val()!='' )
		{ $("#mblog_photo1").val( $("#key").val() );
		  upclk('',callback);
		}
	else
		{ $("#mblog_photo1").val( '' );
	      callback();
		}  
    return true;		
}

function callback()
{
	 $.ajax({
			url:'../mblog/add',
			type:'post',
			dataType:'json',
			data:{ 'mblog.id_channel':$('input:radio[name="mblog.id_channel"]:checked').val(),
				   'mblog.content': $('#mblog_content').val(),
				   'mblog.photo1': $('#mblog_photo1').val(),
				   'mblog.public':$('input:radio[name="mblog.public"]:checked').val()},
			success : function(data)
			    {if( data.error ==1)
				   $("#aso_detail_errorMsg").html("邮件已使用");
			    else
			    		 alert('修改成功');
			    }
			});		
}

function refresh_page(pageNumber)  //换页
{
  $("#blogs").load( '../../mblog/list/' + pageNumber );   
}

function report_it( blog_id )
{
	 $.ajax({
			url:'../../mblog/report/'+blog_id,
			type:'post',
			dataType:'json',
			data:{ },
			success : function(data)
			    {if( data.error ==0)
			       alert('举报成功');
			    }
			});		
}

function agree( blog_id )
{
	 $.ajax({
			url:'../../mblog/agree/'+blog_id,
			type:'post',
			dataType:'json',
			data:{ },
			success : function(data)
			    { $('#agree'+blog_id).html( '赞(' + data.counter + ')' );
			    }
			});
}
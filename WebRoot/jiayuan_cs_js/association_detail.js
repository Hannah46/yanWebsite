function association_detail_Click(edit_mode)
{ 
 var updateThumb;
 
 if( edit_mode!=1 &&$.trim($('#association_name').val())=='')
	{ alert('名称不能为空'); return;}	
 if( $.trim($('#association_email').val())=='')
	{ alert('电子邮箱不能为空'); return;}
 if( $.trim($('#association_passwd').val())=='')
	{ alert('密码不能为空'); return;} 

 if( edit_mode ==1 )
	updateThumb = '../association/makeThumb/';
 else
	updateThumb = '../../association/makeThumb/';
 
 if( edit_mode !=3 )
    updateThumb = updateThumb + $('#association_id').val() + "-" + $('#association_pict1').val();
 else
    updateThumb = updateThumb + $('#association_id').val() + "-" + $('#key').val();
	 
 if( $('#file').val()!='')
	 $('#association_pict1').val( $('#key').val() );
 
 if( edit_mode ==1 )
    $.ajax({
		url:'../association/detail_update',
		type:'post',
		dataType:'json',
		data:{ 'association.id':$('#association_id').val(),
			   'association.tel': $('#association_tel').val(),
			   'association.email': $.trim($('#association_email').val()),
			   'association.pict1':$('#association_pict1').val(),
			   'association.passwd':$('#association_passwd').val(),
			   'association.intro': $.trim($('#association_intro').val())},
		success : function(data)
		    {if( data.error ==1)
			   $("#aso_detail_errorMsg").html("邮件已使用");
		    else
		    	 if( $('#file').val()!='')
		    		 upclk(updateThumb,callback1);
		    	 else
		    		 alert('修改成功');
		    }
		});		
 else if( edit_mode ==2 )
	     $.ajax({
			url:'../detail_update',
			type:'post',
			dataType:'json',
			data:{ 'association.id':$('#association_id').val(),
				   'association.name': $('#association_name').val(),
				   'association.person': $('#association_person').val(),
				   'association.tel': $('#association_tel').val(),
				   'association.email': $('#association_email').val(),
				   'association.pict1':$('#association_pict1').val(),
				   'association.passwd':$('#association_passwd').val(),
				   'association.intro': $.trim($('#association_intro').val())},
			success : function(data)
			    {if( data.error ==1)
				   $("#aso_detail_errorMsg").html("邮件已使用");
			    else
			    	 if( $('#file').val()!='')
			    		 upclk(updateThumb,callback2);
			    	 else
			           window.history.go(-1);
			    }
			});		
 else if( edit_mode ==3 )
     $.ajax({
		url:'../add',
		type:'post',
		dataType:'json',
		data:{ 'association.name': $('#association_name').val(),
			   'association.id_university': $('#university_id').val(),
			   'association.person': $('#association_person').val(),
			   'association.tel': $('#association_tel').val(),
			   'association.email': $('#association_email').val(),
			   'association.pict1':$('#association_pict1').val(),
			   'association.passwd':$('#association_passwd').val(),
			   'association.intro': $.trim($('#association_intro').val())},
		success : function(data)
		    {if( data.error ==1)
			   $("#aso_detail_errorMsg").html("邮件已使用");
		    else
		      { 
		    	 if( $('#file').val()!='')
		    		 upclk(updateThumb,callback3);
		    	 else
		             window.history.go(-1);
		    	//window.location.href='../list/'+$('#university_id').val()+'-9999';
		      }
		    }
		});		
};

function callback1()
{
	 $('#menu1').load("../association/detail");
	 alert('修改成功');
}

function callback2()
{
	window.history.go(-1);
}

function callback3()
{
	window.history.go(-1);
}


 
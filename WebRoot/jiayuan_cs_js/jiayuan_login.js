function m4_loginClick()
 { 
 $.ajax({
		url:'../association/domLogin',
		type:'post',
		dataType:'json',
		data:{ 'university.email': $('#m4_email').val(),
			   'university.passwd':$('#m4_password').val()},
		success : function(data)
		    {if( data.error ==1)
			   $("#m4_errorMsg").html("账号或密码错误");
		    else
		      window.location.href = '../association/manage';
		    }
		});		
};

function m3_loginClick()
{ 
$.ajax({
		url:'../association/doLogin',
		type:'post',
		dataType:'json',
		data:{ 'association.email': $('#m3_email').val(),
			   'association.passwd':$('#m3_password').val()},
		success : function(data)
		    {if( data.error ==1)
			   $("#m3_errorMsg").html("账号或密码错误");
		    else
		      window.location.href = '../association/menu';
		    }
		});		
};

function m2_loginClick()
{ 
$.ajax({
		url:'../companylogin/doLogin',
		type:'post',
		dataType:'json',
		data:{ 'company.email': $('#m2_email').val(),
			   'company.passwd':$('#m2_password').val()},
		success : function(data)
		    {if( data.error ==1)
			   $("#m2_errorMsg").html("账号或密码错误");
		    else
		      window.location.href = '../association';
		    }
		});		
};

function m1_loginClick()
{ 
$.ajax({
		url:'../userAction/doLogin',
		type:'post',
		dataType:'json',
		data:{ 'user.email': $('#m1_email').val(),
			   'user.passwd':$('#m1_password').val()},
		success : function(data)
		    {if( data.error ==1)
			   $("#m1_errorMsg").html("账号或密码错误");
		    else
		      window.location.href = '../association';
		    }
		});		
};
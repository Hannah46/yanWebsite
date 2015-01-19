/**
 * 
 */
 var ajaxOptionsQiNiu1 = {
		 beforeSubmit : null,
         url : "http://up.qiniu.com/",
         success : function(responseText){
			 $("#photo1_my").ajaxSubmit(ajaxOptionsMy1);
		 },
         error : function(XMLResponse) {
        	 alert("呃，图片上传失败了，请待会再试试！");
          }
 };
 var ajaxOptionsMy1 = {
		 beforeSubmit : null,
		 success : function(responseText){
			//修改图片的url
			alert("修改成功！");
			id = $("#photo1_my").find("input[name='user.id']").val();
			photo = "photo1";
			photob = "photo1b";
			$("#photo1").load("../photo/showPhoto/" + id + "-" + photo + "-" + photob);
		 },
         url : "updatePhoto1",
         error : function(XMLResponse) {
             alert('修改失败了，请待会再试一次吧！');
          }
 };
function submitPhoto1(){
	$.ajax({
        type: "GET",
        url: "../qiNiuUtils/getTokenAndKey",
        dataType: "json",
        success: function(data){
        	$("#photo1_qiNiu").find("#token").val(data.token);
        	$("#photo1_qiNiu").find("#key").val(data.key);
        	$("input[name='user.photo1']").val(data.key);
        	$("#photo1_qiNiu").ajaxSubmit(ajaxOptionsQiNiu1);
        },
		error: function (XMLHttpRequest, textStatus, errorThrown) { 
			alert("呃，目前无法上传图片，请待会再试试！");
		} 
    });
}

var ajaxOptionsQiNiu2 = {
		beforeSubmit : null,
        url : "http://up.qiniu.com/",
        success : function(responseText){
			 $("#photo2_my").ajaxSubmit(ajaxOptionsMy2);
		 },
        error : function(XMLResponse) {
       	 alert("呃，图片上传失败了，请待会再试试！");
         }
};
var ajaxOptionsMy2 = {
		 beforeSubmit : null,
		 success : function(responseText){
			//修改图片的url
			alert("修改成功！");
			id = $("#photo2_my").find("input[name='user.id']").val();
			photo = "photo2";
			photob = "photo2b";
			$("#photo2").load("../photo/showPhoto/" + id + "-" + photo + "-" + photob);
		 },
        url : "updatePhoto2",
        error : function(XMLResponse) {
            alert('修改失败了，请待会再试一次吧！');
         }
};
function submitPhoto2(){
	$.ajax({
       type: "GET",
       url: "../qiNiuUtils/getTokenAndKey",
       dataType: "json",
       success: function(data){
    	   	$("#photo2_qiNiu").find("#token").val(data.token);
    	   	$("#photo2_qiNiu").find("#key").val(data.key);
       		$("input[name='user.photo2']").val(data.key);
       		$("#photo2_qiNiu").ajaxSubmit(ajaxOptionsQiNiu2);
       },
		error: function (XMLHttpRequest, textStatus, errorThrown) { 
			alert("呃，目前无法上传图片，请待会再试试！");
		} 
   });
}

var ajaxOptionsQiNiu3 = {
		 beforeSubmit : null,
        url : "http://up.qiniu.com/",
        success : function(responseText){
			 $("#photo3_my").ajaxSubmit(ajaxOptionsMy3);
		 },
        error : function(XMLResponse) {
       	 alert("呃，图片上传失败了，请待会再试试！");
         }
};
var ajaxOptionsMy3 = {
		 beforeSubmit : null,
		 success : function(responseText){
			//修改图片的url
			alert("修改成功！");
			id = $("#photo3_my").find("input[name='user.id']").val();
			photo = "photo3";
			photob = "photo3b";
			$("#photo3").load("../photo/showPhoto/" + id + "-" + photo + "-" + photob);
		 },
        url : "updatePhoto3",
        error : function(XMLResponse) {
            alert('修改失败了，请待会再试一次吧！');
         }
};
function submitPhoto3(){
	$.ajax({
       type: "GET",
       url: "../qiNiuUtils/getTokenAndKey",
       dataType: "json",
       success: function(data){
    	   	$("#photo3_qiNiu").find("#token").val(data.token);
       		$("#photo3_qiNiu").find("#key").val(data.key);
       		$("input[name='user.photo3']").val(data.key);
       		$("#photo3_qiNiu").ajaxSubmit(ajaxOptionsQiNiu3);
       },
		error: function (XMLHttpRequest, textStatus, errorThrown) { 
			alert("呃，目前无法上传图片，请待会再试试！");
		} 
   });
}

var ajaxOptionsQiNiu4 = {
		 beforeSubmit : null,
        url : "http://up.qiniu.com/",
        success : function(responseText){
			 $("#photo4_my").ajaxSubmit(ajaxOptionsMy4);
		 },
        error : function(XMLResponse) {
       	 alert("呃，图片上传失败了，请待会再试试！");
         }
};
var ajaxOptionsMy4 = {
		 beforeSubmit : null,
		 success : function(responseText){
			//修改图片的url
			alert("修改成功！");
			id = $("#photo4_my").find("input[name='user.id']").val();
			photo = "photo4";
			photob = "photo4b";
			$("#photo4").load("../photo/showPhoto/" + id + "-" + photo + "-" + photob);
		 },
        url : "updatePhoto4",
        error : function(XMLResponse) {
            alert('修改失败了，请待会再试一次吧！');
         }
};
function submitPhoto4(){
	$.ajax({
       type: "GET",
       url: "../qiNiuUtils/getTokenAndKey",
       dataType: "json",
       success: function(data){
    	   	$("#photo4_qiNiu").find("#token").val(data.token);
       		$("#photo4_qiNiu").find("#key").val(data.key);
       		$("input[name='user.photo4']").val(data.key);
       		$("#photo4_qiNiu").ajaxSubmit(ajaxOptionsQiNiu4);
       },
		error: function (XMLHttpRequest, textStatus, errorThrown) { 
			alert("呃，目前无法上传图片，请待会再试试！");
		} 
   });
}

var ajaxOptionsQiNiu5 = {
		beforeSubmit : null,
        url : "http://up.qiniu.com/",
        success : function(responseText){
			 $("#photo5_my").ajaxSubmit(ajaxOptionsMy5);
		 },
        error : function(XMLResponse) {
       	 	alert("呃，图片上传失败了，请待会再试试！");
        }
};
var ajaxOptionsMy5 = {
		 beforeSubmit : null,
		 success : function(responseText){
			//修改图片的url
			alert("修改成功！");
			id = $("#photo5_my").find("input[name='user.id']").val();
			photo = "photo5";
			photob = "photo5b";
			$("#photo5").load("../photo/showPhoto/" + id + "-" + photo + "-" + photob);
		 },
        url : "updatePhoto5",
        error : function(XMLResponse) {
            alert('修改失败了，请待会再试一次吧！');
         }
};
function submitPhoto5(){
	$.ajax({
       type: "GET",
       url: "../qiNiuUtils/getTokenAndKey",
       dataType: "json",
       success: function(data){
    	   	$("#photo5_qiNiu").find("#token").val(data.token);
       		$("#photo5_qiNiu").find("#key").val(data.key);
       		$("input[name='user.photo5']").val(data.key);
       		$("#photo5_qiNiu").ajaxSubmit(ajaxOptionsQiNiu5);
       },
		error: function (XMLHttpRequest, textStatus, errorThrown) { 
			alert("呃，目前无法上传图片，请待会再试试！");
		} 
   });
}

/**
 * 
 */
var ajaxOptionsQiNiu = {
		 beforeSubmit : null,
         url : "http://up.qiniu.com/",
         success : function(responseText){
			 $("#personalInfo").ajaxSubmit(ajaxOptionsMy);
		 },
         error : function(XMLResponse) {
        	 alert("呃，图片上传失败了，请待会再试试！");
          }
 };
 var ajaxOptionsMy = {
		 beforeSubmit : null,
		 success : function(responseText){
			//修改图片的url
			alert("修改成功！");
		 },
         url : "updatePersonalInfo",
         error : function(XMLResponse) {
             alert('修改失败了，请待会再试一次吧！');
          }
 };
function submitPhoto(){
	$.ajax({
        type: "GET",
        url: "http://localhost:8080/yanyuan/qiNiuUtils/getTokenAndKey",
        dataType: "json",
        success: function(data){
        	$("#token").val(data.token);
        	$("#key").val(data.key);
        	$("input[name='user.iden_picture1']").val(data.key);
        	$("#formQiNiu").ajaxSubmit(ajaxOptionsQiNiu);
        },
		error: function (XMLHttpRequest, textStatus, errorThrown) { 
			alert("呃，目前无法上传图片，请待会再试试！");
		} 
    });
}
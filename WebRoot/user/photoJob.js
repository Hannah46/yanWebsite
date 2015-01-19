/**
 * 
 */
 var ajaxOptionsQiNiu = {
		 beforeSubmit : null,
         url : "http://up.qiniu.com/",
         success : function(responseText){
			 $("#photoJob_my").ajaxSubmit(ajaxOptionsMy);
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
			id = $("#photoJob_my").find("#userId").val();
			photo = "photo_job";
			photob = "photo_job2";
			$("#showPhotoJob").load("../photo/showPhoto/" + id + "-" + photo + "-" + photob);
		 },
         url : "updatePhotoJob",
         error : function(XMLResponse) {
             alert('修改失败了，请待会再试一次吧！');
          }
 };
function submitPhotoJob(){
	$.ajax({
        type: "GET",
        url: "../qiNiuUtils/getTokenAndKey",
        dataType: "json",
        success: function(data){
        	$("#token").val(data.token);
        	$("#key").val(data.key);
        	$("input[name='user.photo_job']").val(data.key);
        	$("#photoJob_qiNiu").ajaxSubmit(ajaxOptionsQiNiu);
        },
		error: function (XMLHttpRequest, textStatus, errorThrown) { 
			alert("呃，目前无法上传图片，请待会再试试！");
		} 
    });
}
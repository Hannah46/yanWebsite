var Qiniu_UploadUrl = "http://up.qiniu.com"; 

function upclk(proc_after_success, callback) 
{
//普通上传
var Qiniu_upload = function(f, token, key) {
  var xhr = new XMLHttpRequest();
  xhr.open('POST', Qiniu_UploadUrl, true);
  var formData, startDate;
  formData = new FormData();
  if (key !== null  ) formData.append('key', key);
  formData.append('token', token);
  formData.append('file', f);
  var taking;
  xhr.upload.addEventListener("progress", function(evt) {
  if (evt.lengthComputable) {
    var nowDate = new Date().getTime();
    taking = nowDate - startDate;
    var x = (evt.loaded) / 1024;
    var y = taking / 1000;
    var uploadSpeed = (x / y);
    var formatSpeed;
    if (uploadSpeed > 1024) {
       formatSpeed = (uploadSpeed / 1024).toFixed(2) + "Mb\/s";
    } else {
       formatSpeed = uploadSpeed.toFixed(2) + "Kb\/s";
    }
    var percentComplete = Math.round(evt.loaded * 100 / evt.total);
    //console && console.log(percentComplete, ",", formatSpeed);
    $('#progressbar').html(percentComplete + "%" );
   } //end of if
  }, false);

  xhr.onreadystatechange = function(response) {
    if (xhr.readyState == 4 && xhr.status == 200 && xhr.responseText != "") 
     {                                                   //success
       if(proc_after_success)
    	 {
    	   if(callback)
    	     $('#for_thumb').load( proc_after_success,callback);
    	   else
      	     $('#for_thumb').load( proc_after_success);    		   
    	 }  
       else
    	   if(callback)
    		 callback();
       //var blkRet = JSON.parse(xhr.responseText);
       //console && console.log(blkRet);
     } 
    else if (xhr.status != 200 && xhr.responseText) {}
   };

  startDate = new Date().getTime();
  xhr.send(formData);
  };
var token = $("#token").val();
if ($("#file")[0].files.length > 0 && token != "") {
Qiniu_upload($("#file")[0].files[0], token, $("#key").val());
} else {
console && console.log("form input error");
}
};
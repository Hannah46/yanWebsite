$.ajaxSetup ({ 
    cache: false //关闭AJAX相应的缓存 
});


var detailOption = {
  beforeSubmit : null,
  url : "../company/update",
  success : function(responseText){
	$('#menu2').load('../company/toDetail');
  },
  error : function(XMLResponse) {
   ;
  }
};

var photo1Option = {
		  beforeSubmit : null,
		  url : "../company/update",
		  success : function(responseText){
			  upclk('',refresh_photo1);  			
		  },
		  error : function(XMLResponse) {
		   ;
		  }
		};

function save_detail()
{
  $('#companyDetail').ajaxSubmit(detailOption);
}

function refresh_photo1()
{
	$('#menu4').load('../company/toPhoto1');	
}

function upload_photo1()
{
  $("input[name='company.pict1]").val( $('#key').val() );
  $('#photo1').ajaxSubmit(photo1Option);
}

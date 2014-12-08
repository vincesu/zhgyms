var aaa = null;
var productBulkOperMsgDia = null;
var param = null;
$(function(){
	aaa=$('#multiple').agileUploader({
//    		submitRedirect: 'results.html',
		formId: 'multipleFile',
		flashSrc: 'upload/agile-uploader.swf',
		flashVars: {
			firebug: false,
    			form_action: 'service/mFileUpload?serviceid=uploadMultipleFiles',
			file_limit: 30,
			jpg_quality:85,
			max_height:1000,
			max_width:1000,
			 js_event_handler: '$.fn.myUploadEventHandler'
		}
	});	
	
	$.fn.myUploadEventHandler = function (event) {

        // First call the default event handler (optional, you could completely rewrite)
        $.fn.agileUploaderEvent(event); 

        // Now handle the events we want to 
        switch (event.type) {
            case 'preview':
//                	$.fn.agileUploaderPreview(event.file);
//            	$("#id-"+event.file.uid).append($('<div style="clear:both;">')).
//            		append($('<label>').html("编号:")).
//            		append($('<input type="input" name="filename-'+event.file.uid+'">'));
            	afterPreview(event);
				break;
            case 'server_response':
            	responseCall(event.response);
            	break;
        };
    };
    
    $('#bulkposubmitbutton').click(function(){
    	return submitUploadForm();
    });
});

function afterPreview(event)
{
	$("#id-"+event.file.uid).append($('<div style="clear:both;">')).
	append($('<label>').html("编号:")).
	append($('<input type="text" name="filename-'+event.file.uid+'" value="'+
			getFileNameWithoutExp($($("#id-"+event.file.uid).children()[1]).html())+'">'));
}

function responseCall(response)
{
	document.getElementById('agileUploaderSWF').removeAllFiles();
	if(response=="error0")
	{
		productBulkOperMsgDia.close();
		art.dialog("网络出错，请重新上传");
	}
	else if(response=="error1")
	{
		productBulkOperMsgDia.close();
		art.dialog("存在相同文件不同拓展名的图片，请重命名后重试");
	}
	else if(response=="error2")
	{
		productBulkOperMsgDia.close();
		art.dialog("登录超时，请重新登陆");
	}
	else if(response=="error3")
	{
		productBulkOperMsgDia.close();
		art.dialog("保存文件出错");
	}
	else
	{
		
		param[0]["fileid"]=response;
		var rs = new RemoteService("bulkProductImport");
		rs.send(param, pbosuc, null, null);
	}
}

function pbosuc(resp)
{
	param = null;
	productBulkOperMsgDia.close();
	art.dialog(resp.message);
}

function getParam()
{
	var lis = $("#agileUploaderFileList").children();
	param = new Array();
	param[0] = FormUtil.getValue("productBaseInfo");
	if(param[0]["category2"]!="-1")
		param[0]["category"]=param[0]["category2"];
	var i=0;
	for(i=0;i<lis.length;i++)
	{
		if(param[i]==null)
			param[i]={};
		param[i]["number"] = $(lis[i]).children().last()[0].value;
	}
	return param;
}

function getFilenames()
{
	var lis = $("#agileUploaderFileList").children();
	var result = "";
	var i=0;
	for(i=0;i<lis.length;i++)
	{
		result = result+$($(lis[i]).children()[1]).html()+'|';
	}
	return result.substring(0,result.length-1);
}

function submitUploadForm()
{
	var lis = $("#agileUploaderFileList").children();
	if(lis.length==0)
	{
		art.dialog("未选择上传图片");
		return;
	}
	var i=0;
	param = getParam();
	$("#pbofilenames").attr("name","filename|"+getFilenames());
	for(i=0;i<lis.length;i++)
	{
		$($(lis[i]).children().last()[0]).remove();
	}
	document.getElementById('agileUploaderSWF').submit();
	productBulkOperMsgDia = art.dialog({title: "提示",lock:true,content:"正在上传文件..."});
	return false;
}

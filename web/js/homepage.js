var IntervalId = null;
$(function() {
	$("#menumain .item a,#menumain .item span").mouseover(function(){
    	$("#menumain .item a").removeClass("hidebg");
    	$("#menumain .item span").removeClass("hidebg");
    }).mouseout(function(){
    	$("#menumain .item a").addClass("hidebg");
    	$("#menumain .item span").addClass("hidebg");
    });
	
//	$("#currentDate").mouseover(function(){
//		WdatePicker({
//			el:'hiddendate',
//			onpicked:function(dp){$dp.show();}});
//    });
	
	WdatePicker({eCont:'cal',lang:'zh-cn'});
	
	setTime();
	
	IntervalId = window.setInterval(setTime,1000);
	
	$("#pdsearchbutton").click(function() {
		searchProductDir();
	});
});

function setTime()
{
	var now = new Date();
//	$("#currentDate").html(now.getFullYear().toString() + '-' +
//            now.getMonth().toString() + '-' +
//            now.getDate().toString() + '&nbsp;&nbsp;&nbsp;&nbsp;' +
//            now.getHours().toString() + ':' +
//            now.getMinutes().toString() + ':' +
//            now.getSeconds().toString()+ '&nbsp;&nbsp;&nbsp;&nbsp;星期' +
//            "天一二三四五六".charAt(now.getDay()));
	$("#currentdate").html('当前日期：'+now.getFullYear().toString() + '-' +
          (now.getMonth()+1).toString() + '-' +
          now.getDate().toString());
	$("#currenttime").html('当前时间：'+now.getHours().toString() + ':' +
          now.getMinutes().toString() + ':' +
          now.getSeconds().toString());
	$("#currentweek").html('星期' +
          "天一二三四五六".charAt(now.getDay()));
}

function showNotice(title,id,fileid)
{
	if(fileid!=''&&fileid!='null') {
		new PreviewComponent().createPreviewComponent('previewFiles',fileid,title);
	} else {
		var dialog = art.dialog({title:"通知",lock:true});
	
		$.ajax({
		    url: "pages/module/office/Notice/NoticeDialog.html",
		    success: function (data) {
		        dialog.content(data);
		        $("#notice-title").val(title);
		        $("#notice-content").val($("#notice-"+id).val());
		    },
		    cache: false
		});
	}
}

function searchProductDir() 
{
//	if($("#pdsearchname").val() == undefined || $("#pdsearchname").val() == null 
//			|| $("#pdsearchname").val() == "")
//		return;
	$("#productDirItems").html('<img src="css/framework/images/load.gif">');
	var rs = new RemoteService("getProductDirectorys");
	var param = new Array();
	param[0] = {};
	param[0]["name"] = $("#pdsearchname").val();
	param[0]["type"] = 3;
	param[0]["rows"] = 5;
	param[0]["start"] = 0;
	function suc(resp)
	{
		if(resp.data.length==0)
		{
			$("#productDirItems").html('无相关产品目录');
		}
		else
		{
			var htmlstr = '';
			var ename = null;
			for(var o in resp.data)
			{
				if(resp.data[o].name==undefined || resp.data[o].id==undefined)
					continue;
				ename = getExpandingName(resp.data[o].path);
				htmlstr = htmlstr+'<div class="information_box">'+
				'<span style="float:left;"><img style="margin:6px 4px 0px 0px;" src="css/framework/images/tag.png"></span>'+
				'<span style="float:left;margin:0px 5px;"><a href="javascript:new DownloadComponent().createDownloadComponent(\'homepageForm\',\'downloadFiles\',\''+resp.data[o].name+'.'+ename+'\',\''+resp.data[o].id+'\');">'+resp.data[o].name+'['+resp.data[o].date+']</a></span>'+
				'<span style="float:right;margin-right:7px;"><a href="javascript:new PreviewComponent().createPreviewComponent(\'previewFiles\',\''+resp.data[o].id+'\');">预览打印</a></span>'+
				'<div class="cleaner"></div>'+
				'</div>';
			}
			$("#productDirItems").html(htmlstr);
		}
			
	}
	rs.send(param,suc,null,null);
}
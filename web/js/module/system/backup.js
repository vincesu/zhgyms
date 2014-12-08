$(function(){
	$("#backupSubmit").click(function(){
		$("#backupmessage").html("备份数据成功，正在准备下载文件，这可能需要一点时间，请耐心等待");
		new DownloadComponent().createDownloadComponent("backupForm", "backupSystemData", "backup.rar", null);
	});
	
	$("#restoreSubmit").click(function(){
		$("#backupmessage").html("正在还原数据，这可能需要一点时间，请耐心等待...");
		var rs = new RemoteService("restoreDB");
		rs.send(null, function() {$("#backupmessage").html("数据恢复成功");}, null,null);
	});
});
//
//function suc(resp)
//{
//	if(resp == null)
//		art.dialog("备份失败");
//	else if(resp.type=="error")
//		art.dialog("备份失败:"+resp.message);
//	else
//		art.dialog({
//			content:"备份成功",
//			time:mainComponent.closeDialogTime
//		});
//}
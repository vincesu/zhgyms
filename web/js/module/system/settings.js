$(function(){
	$("#modifybutton").click(function(){
		var rs = new RemoteService("restoreUser");
		var params = new Array();
		params[0] = FormUtil.getValue("user_form");
		rs.send(params, suc, null, "正在修改数据...");
		function suc(resp)
		{
			if(resp == null || resp.type=="error")
			{
				art.dialog("修改失败");
				
			} else {
				art.dialog({
					content:"修改成功",
					time:mainComponent.closeDialogTime
				});
				var r = new RemoteService("freshUserData");
				r.send(null,refreshResp,null,'正在刷新用户数据...');
			}
		}
	});
	
	var r = new RemoteService("getCurrentUser");
	r.send(null, suc, null, "正在查询数据...");
	function suc(resp)
	{
		if(resp == null || resp.type=="error")
		{
			art.dialog("查询失败");
			
		} else {
			FormUtil.setValue("user_form",resp.data[0]);
		}
	}
});

function refreshResp(resp) {
	if(resp == null || resp.type=="error") {
		art.dialog("刷新用户信息失败");
	} else {
		var rs = new RemoteService("queryContractDefault");
		rs.send(null, suc, null, '正在刷新用户数据...');
		function suc(resp)
		{
			if(resp==undefined || resp.type=="error") {
				art.dialog("刷新用户信息失败");
			} else {
				if(contractDefaultVar != null && contractDefaultVar != undefined)
					contractDefaultVar = resp.data[0];
			}
		}
	}
}
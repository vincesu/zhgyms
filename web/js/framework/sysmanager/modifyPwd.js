$(function(){
	$("#modifypwdbutton").click(function(){
		if($("#newpwd1").val()!=$("#newpwd2").val())
		{
			alert("新密码输入有误！");
			return;
		}
		var rs = new RemoteService("modifyPwd");
		var params = new Array();
		
		params[0] = FormUtil.getValue("userModifyPwdForm");
		rs.send(params, suc, null, "正在修改数据...");
		function suc(resp)
		{
			if(resp == null) {
				art.dialog("修改失败");
			} else if(resp.type=="error") {
				art.dialog(resp.message);
			} else {
				art.dialog("修改成功");
			}
		}
	});
});
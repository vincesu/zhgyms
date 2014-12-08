$(function() {
	$("#alertdiv").hide();
});
function newLogin()
{
	$("#alertdiv").show();
	$("#loadpic").show();
	$("#message").html("正在登陆...");
	
	var param = new Array();
	param[0] = FormUtil.getValue("fm1");
	var rs = new RemoteService("login");
	rs.send(param,suc,null,null);
	function suc(resp)
	{
		if(resp == null || resp.type == "error")
		{
//			alert("用户名或密码错误！");
			$("#alertdiv").show();
			$("#loadpic").hide();
			$("#message").html("用户名或密码错误!");
			return;
		}
		window.location.href="index.html";
//		$('#signoutbutton').show();
//		mainComponent.createContent(resp.data);
//		if(mainComponent.loginInitEvent)
//			mainComponent.loginInitEvent();
	}
	function err(resp)
	{
//		alert("登入失败");
		$("#alertdiv").show();
		$("#loadpic").hide();
		$("#message").html("登入失败");
		return;
	}
}

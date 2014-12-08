/**
 * author vince.su
 * 2012-09-22
 */

var to_homepage=false;
var sys_username = {};
var timeoutLoginDialog;

var mainComponent = null;

$(function() {
	init();
});

function init()
{
	var rs = new RemoteService("islogin");
	rs.send(null,suc,null,"正在登陆");
	function suc(resp)
	{
		if(resp == null || resp.type == "error")	return;
		if (resp.message == "notlogin") 
		{
			window.location.href="login.html";
//			$("#content").load("pages/framework/login/login.html");
//			$('#signoutbutton').hide();

		} else 
		{
			mainComponent = new components("mainComponent","content");

			mainComponent.addAfterSelectTreeNodeEvent(function() {
				mainComponent.createCodeComboxBox();
				$("#FinancialStatementsForm_year").val(new Date().getFullYear().toString());
			});
			
			mainComponent.addLoginInitEvent(function() {
				var rs = new RemoteService("getEncoding");
				rs.send(null,suc,err,"正在获得编码表");
				function suc(resp) {
					mainComponent.encodeingArray = resp.data;
				}
				function err(resp) {
					alert("加载编码表失败!");
				}
			});
			
			$("#signoutbutton").click(function() {
				var rs = new RemoteService("signout");
				rs.send(null,suc,null,"正在注销...");
				function suc(resp)
				{
					if(resp == null || resp.type == "error")
					{
						alert("注销失败，请联系管理员！");
						return;
					}
//					window.location.reload();
					window.location.href="login.html";
				}
				function err(resp)
				{
					alert("注销失败，请联系管理员！");
				}
			});
			
			$("#tohomepagebutton").click(function(){
				to_homepage=true;
				return true;
			});
			
			to_homepage=false;
			
			$('#signoutbutton').show();
			formatUser(resp.message);
			$("#sys_username_div").html(sys_username["username"]+",你好");
			mainComponent.createContent(resp.data);
			if(mainComponent.loginInitEvent)
				mainComponent.loginInitEvent();
		}
	}
}

function login()
{
	var param = new Array();
	param[0] = FormUtil.getValue("login_form");
	var rs = new RemoteService("login");
	rs.send(param,suc,null,"正在登陆");
	function suc(resp)
	{
		if(resp == null || resp.type == "error")
		{
			alert("用户名或密码错误！");
			return;
		}
		$('#signoutbutton').show();
		formatUser(resp.message);
		$("#sys_username_div").html(sys_username["username"]+",你好");
		mainComponent.createContent(resp.data);
		if(mainComponent.loginInitEvent)
			mainComponent.loginInitEvent();
	}
	function err(resp)
	{
		alert("登入失败");
	}
}


function timeoutLogin()
{
	var param = new Array();
	param[0] = FormUtil.getValue("login_form");
	var rs = new RemoteService("login");
	rs.send(param,suc,null,"正在登陆");
	function suc(resp)
	{
		if(resp == null || resp.type == "error")
		{
			$("#logindialogmessagediv").html("用户名或密码错误！");
			return;
		}
		$('#signoutbutton').show();
		formatUser(resp.message);
		$("#sys_username_div").html(sys_username["username"]+",你好");
//		mainComponent.createContent(resp.data);
		if(mainComponent.loginInitEvent)
			mainComponent.loginInitEvent();
		timeoutLoginDialog.close();
	}
	function err(resp)
	{
		alert("登入失败");
	}
}

function refresh(data) {
	formatUser(resp.message);
	$("#sys_username_div").html(sys_username["username"]+",你好");
}

function formatUser(data)
{
	var items = data.split("|");
	sys_username["username"]=items[0];
	sys_username["reserved"]=items[1];
	sys_username["realname"]=items[2];
}

/**
 * author vince.su
 * 2012-09-27 
 */

var usergrid = null;
var userquerygrid = null;
var cd = null;
var sysUserVerdialog = null;
var sysUserMgrOperType = 0;

userquerygrid = new QueryGrid(
		"userform",
		[ {name:'rolename',type:'角色名称',text:'角色名称'},
		  {name:'username',type:'text',text:'用户名'}
//			  {name:'name',type:'text',text:'名称'}
//		  {name:'bt',type:'text',text:'省厅回盘起始时间',classname:'My97DatePicker'},
//		  {name:'et',type:'text',text:'省厅回盘截止时间',classname:'My97DatePicker'},
//		  {name:'yc0076',type:'select',text:'发送审核状态',options: [
//		                                         {value:"'038000','041200'",text:'全部',selected:"selected"},
//		                                         {value:"041200",text:'已发送审核'},
//		                                         {value:"038000",text:'未发送审核'}
//		                                         ] }
		 ],
		 null,
		 "getUser",
		 "restoreUser",
		 "removeUser",
		 "restoreUser",
		 "pages/framework/sysmanager/user_dialog.html",
		 "user_form"
);

usergrid = $("#usergrid").jqGrid(
	{	
		url:'service/dataInteraction?serviceid=getUsersExceptRoot',
		mtype: "POST",
		datatype: "local", 
		colNames:['用户ID','真实姓名','用户名','角色ID','角色名称'], 
		colModel:[ 
				   {name:'id',index:'id', width:100},
				   {name:'realname',index:'realname', width:120},
		           {name:'username',index:'username', width:120},
		           {name:'roleid',index:'roleid', width:120},
		           {name:'rolename',index:'rolename', width:120}
		         ],
		 width:mainComponent.mainPanelContentWidth,
		 height:mainComponent.mainPanelContentHeight,
		 rowNum:50, 
		 rowList:[50,100,150],
		 pager: '#userpager', 
		 //sortname: 'id',
		 viewrecords: true,
		 //sortorder: "desc", 
		 caption:"模块列表"

	}
);

jQuery("#usergrid").jqGrid('navGrid','#userpager',{edit:false,add:false,del:false});

userquerygrid.setGrid(usergrid);

userquerygrid.createDoubleClick();

userquerygrid.afterPopup = function(data) {
	
	data["pwd"]="";
	return true;
};


function chooseRole()
{
	cd = new ChooseDialog(
			null,
			[ {name:'id',type:'text',text:'角色ID'},
			  {name:'rolename',type:'text',text:'名称'}],
			"getRoleListExceptRoot",
			['ID','名称'], 
			[ 
			   {name:'id',index:'id', width:200},	
	           {name:'rolename',index:'rolename', width:400}
	         ],
			function(param) {
				document.getElementById("roleId").value = param[0].id;
				document.getElementById("roleName").value = param[0].rolename;
			},
			false
	); 
	cd.open();
}

function sysRemoveUser(param) {
	sysUserMgrOperType = param;
	sysUserVerdialog = art.dialog({
		title: "验证",
		lock:true,
		ok: function () {
			sysUserVer();
			return false;
		},
		cancelVal: '关闭',
		cancel: true
	});

	$.ajax({
	    url: "pages/framework/sysmanager/verifyRoot.html",
	    success: function (data) {
	    	sysUserVerdialog.content(data);
	    },
	    cache: false
	});
}

function sysUserVer()
{
	var data = {"rootpwd":$("#verifyroot_input").val()};
	debugger;
	var r = new RemoteService("verifyRoot");
	var p = new Array();
	p[0] = data;
	r.send(p, s, null, "正在验证...");
	function s(resp)
	{
		if(resp == null || resp.type=="error")
		{
			if(resp.message!=null)
				art.dialog(resp.message);
			else
				art.dialog("验证失败");
			
		} else {
			if(sysUserMgrOperType==1) {
				userquerygrid.popupToModify();
			} else if(sysUserMgrOperType==2) {
				userquerygrid.remove();
			}
		}
		if(sysUserVerdialog!=undefined)
			sysUserVerdialog.close();
	}
	return true;
}

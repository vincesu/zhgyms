$(function(){
	$("#adduserbutton").click(function(){
		var rs = new RemoteService("addSystemUser");
		var params = new Array();
		
		params[0] = FormUtil.getValue("addUserForm");
		rs.send(params, suc, null, "正在添加用户...");
		function suc(resp)
		{
			if(resp == null)
			{
				art.dialog("添加失败");
				
			}
			else if(resp.type=="error")
			{
				art.dialog(resp.message);
			}
			else {
				art.dialog({content:"添加成功",time:2});
			}
		}
	});
});

function chooseRole()
{
	cd = new ChooseDialog(
			null,
			[ {name:'id',type:'text',text:'角色ID'},
			  {name:'rolename',type:'text',text:'名称'}],
			"getRoleListExceptRoot",
			['ID','名称','reserved'], 
			[ 
			   {name:'id',index:'id', width:200,align:"center"},	
	           {name:'rolename',index:'rolename', width:400,align:"center"},
	           {name:'reserved',index:'reserved', width:400,hidden:true,align:"center"}
	         ],
			function(param) {
				document.getElementById("roleId").value = param[0].id;
				document.getElementById("roleName").value = param[0].rolename;
			},
			false
	); 
	cd.open();
}

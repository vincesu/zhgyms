
var ClientGrid = null;
var ClientQueryGrid = null;
var ClientDetailGrid = null;
var ClientDetailQueryGrid = null;
var ClientDetailQueryGridDialog = null;


var ClientViewTPST=null;

$(function(){
	ClientQueryGrid = new QueryGrid(
			"ClientForm",
			[
			  {name:'contact',type:'text',text:'联系人'},
			  {name:'unit',type:'text',text:'单位'},
			  {name:'saleman',type:'text',text:'业务员',code:'userrealname',classname:'combox',blankLine:'全部'},
			  {name:'potential',type:'text',text:'客户类型',code:'potential',classname:'combox',blankLine:'全部'},
			  {name:'clientfrom',type:'text',text:'客户來源',code:'clientfrom',classname:'combox',blankLine:'全部'}
	//		  {name:'bt',type:'text',text:'省厅回盘起始时间',classname:'My97DatePicker'},
	//		  {name:'et',type:'text',text:'省厅回盘截止时间',classname:'My97DatePicker'},
	//		  {name:'yc0076',type:'select',text:'发送审核状态',options: [
	//		                                         {value:"'038000','041200'",text:'全部',selected:"selected"},
	//		                                         {value:"041200",text:'已发送审核'},
	//		                                         {value:"038000",text:'未发送审核'}
	//		                                         ] }
			 ],
			 null,
			 "getClient",
			 "addClient",
			 "removeClient",
			 "updateClient",
			 "pages/module/trading/client/ClientDialog.html",
			 "ClientDialogForm"
	);
	
	ClientGrid = $("#ClientGrid").jqGrid(
		{	
			url:'service/dataInteraction?serviceid=getClientList',
			mtype: "POST",
			datatype: "local", 
			colNames:clientColNames, 
			colModel:clientColModel,
			 width:mainComponent.mainPanelContentWidth,
			 height:mainComponent.mainPanelContentHeight,
			 rowNum:mainComponent.rowNum, 
			 rowList:[10,50,100],
			 pager: '#ClientPager', 
			 //sortname: 'id',
			 viewrecords: true,
			 //sortorder: "desc", 
			 caption:"客户信息列表",
			 rownumbers: true
		}
	);
	
	jQuery("#ClientGrid").jqGrid('navGrid','#ClientPager',{edit:false,add:false,del:false});
	
	ClientQueryGrid.setGrid(ClientGrid,["potential","havequote","mailingsample"],["potential","truefalse","truefalse"]);
	
	ClientQueryGrid.createDoubleClick();
	
	ClientQueryGrid.afterSave = function(resp) {
		EncodingUtil.refreshSysEncodingCodeWithoutWarn();
	};
	
	ClientQueryGrid.createloadComplete();
	
	ClientQueryGrid.beforeSave = function(data) {
		if(!FormUtil.check(document.getElementById("ClientDialogForm"),true))
		{
			return false;
		}
		if($("#clientdialogclientfrom").val() == "其他" || $("#clientdialogclientfrom").val()== "-1")
		{
			if($("#clientdialogclientfo").val() == "")
			{
				art.dialog("请填写其他客户来源！");
				return false;
			}
		}
		data["number"] = "-1";
		return true;
	};
	
	ClientQueryGrid.afterPopup = function(data) {
		createAdaptiveComponent();
		
		if(sys_username["reserved"]=="0") 
		{
			$("#salemanF").show();
			mainComponent.createCodeComboxBox();
			$("#ClientDialogFormSMN").val(sys_username["realname"]);
			$("#ClientDialogFormSM").val("0");
		} else {
			$("#salemanF").hide();
			$("#ClientDialogFormSMN").val("-1");
			$("#ClientDialogFormSM").val("-1");
		}
		return true;
	};
});
function mgrOtherClient()
{
	var selectedId = ClientGrid.jqGrid("getGridParam", "selrow");
	if(selectedId == null)
	{
		art.dialog("请选择一条记录");
		return;
	}
	
	var d = ClientGrid.jqGrid("getRowData", selectedId);

	var dialog = art.dialog({
		lock:true,
		title: "公司联系人",
		cancelVal: '关闭',
		cancel: true
	});

	$.ajax({
	    url: "pages/module/trading/client/ClientDetail.html",
	    success: function (data) {
	        dialog.content(data);

			ClientDetailQueryGrid = new QueryGrid(
				"ClientDetailForm",
				[ 
				  {name:'unit',type:'hidden',text:'',value:d["unit"]},
				  {name:'id',type:'hidden',text:'',value:d["id"]}
		//		  {name:'bt',type:'text',text:'省厅回盘起始时间',classname:'My97DatePicker'},
		//		  {name:'et',type:'text',text:'省厅回盘截止时间',classname:'My97DatePicker'},
		//		  {name:'yc0076',type:'select',text:'发送审核状态',options: [
		//		                                         {value:"'038000','041200'",text:'全部',selected:"selected"},
		//		                                         {value:"041200",text:'已发送审核'},
		//		                                         {value:"038000",text:'未发送审核'}
		//		                                         ] }
				 ],
				 null,
				 "getClient",
				 "addClient",
				 "removeClient",
				 "updateClient",
				 "pages/module/trading/client/ClientDialog.html",
				 "ClientDialogForm"
			);
			
			ClientDetailGrid = $("#ClientDetailGrid").jqGrid({	
				url:'service/dataInteraction?serviceid=getCompanyClientList',
				mtype: "POST",
				datatype: "local", 
				colNames:clientColNames,
				colModel:colNames,
				 width:mainComponent.mainPanelContentWidth,
				 height:mainComponent.mainPanelContentHeight,
				 rowNum:mainComponent.rowNum, 
				 rowList:[10,50,100],
				 pager: '#ClientDetailPager', 
				 //sortname: 'id',
				 viewrecords: true,
				 //sortorder: "desc", 
				 caption:"客户信息列表",
				 postData: {unit:d["unit"],id:d["id"]},
				 rownumbers: true
			});
			
			jQuery("#ClientDetailGrid").jqGrid('navGrid','#ClientDetailPager',{edit:false,add:false,del:false});
			
			ClientDetailQueryGrid.setGrid(ClientDetailGrid,["potential","havequote","mailingsample"],["potential","truefalse","truefalse"]);
			
			ClientDetailQueryGrid.createDoubleClick();
			
			ClientDetailQueryGrid.beforeSave = function(data) {
				data["number"] = d["id"];
				$('#clientunit').removeAttr('readonly');
				return true;
			};
			
			ClientDetailQueryGrid.afterPopup = function(data) {
				if(data == null || data== undefined)
				{
					
					var param = {};
					$.extend(param,d);
					param["number"]=param["id"];
					delete param["id"];
					delete param["contact"];
					delete param["email"];
					FormUtil.setValue("ClientDialogForm",param);
				}
				$('#clientunit').attr('readonly', 'readonly');
				createAdaptiveComponent();
			};
			
			ClientDetailQueryGrid.afterLoadData=function(data){
				createAdaptiveComponent();
			};

			dialog.position("50%","50%");
			
	    },
	    cache: false
	});

}

function chooseSaleman()
{
	var cd = new ChooseDialog(
			null,
			[
			  {name:'contact',type:'text',text:'联系人'},
			  {name:'unit',type:'text',text:'单位'},
			  {name:'saleman',type:'text',text:'业务员',code:'userrealname',classname:'combox',blankLine:'全部'}
			 ],
			"getClientList",
			clientColNames,
			clientColModel,
			function(data){
				$("#contract-buyerid").val(data[0]["id"]);
				$("#contract-buyerName").val(data[0]["unit"]);
				$("#contract-address").val(data[0]["address"]);
			},
			false,
			false,/*true or false*/
			null,
		    null
		);
	
	cd.createCompleted = function() {
		document.getElementById("choosedialogform_reset").onclick=function(){};
		$("#choosedialogform_reset").click(function(){
			popupToAdd('addClient',
					'pages/module/trading/client/ClientDialog.html',
					'ClientDialogForm',
					function(data){
						if(!FormUtil.check(document.getElementById("ClientDialogForm"),true))
						{
							return false;
						}
						data["number"] = "-1";
						return true;
					},
					function(thedata){
						createAdaptiveComponent();
						return true;
					},
					function(data){
						var param = {contact:data["contact"]};
						cd.querygird.queryWithParam(param);
					});
		});
		$("#choosedialogform_resetbuttontext").html($("#choosedialogform_resetbuttontext").html().replace('重置','添加'));
	};
	
	cd.open();
}

function viewTrackprogress()
{
	var selectedId = ClientGrid.jqGrid("getGridParam", "selrow");
	if(selectedId == null)
	{
		art.dialog("请选择一条记录");
		return;
	}
	
	var d = ClientGrid.jqGrid("getRowData", selectedId);
	
	trackProgressClientId = d["id"];
	trackProgressClientUnit = d["unit"];
	trackProgressClientName = d["contact"];
	
	queryTrackPUnit = trackProgressClientUnit;
	
	if(mainComponent.tabs.getTabPosision("idmaincomponents-tree-999")==("idmaincomponents-tree-999"))
	{
		mainComponent.createTab(
			"idmaincomponents-tree-999",
			"跟进描述",
			"pages/module/trading/client/TrackProgressMgr.html",
			true,
			false,
			function() {
				$("#TrackProgressForm_unit").val(d["unit"]);
				mainComponent.createCodeComboxBox();
//				ClientDetailQueryGridDialog = art.dialog({
//				    title: '提示',
//				    content: '正在查询...',
//				    lock:true
//				});
				//TrackProgressQueryGrid.query();
//				ClientDetailQueryGrid = setTimeout("afterViewTrackprogress()",1000);
			}
		);
	}
	else
	{
		mainComponent.tabs.show("idmaincomponents-tree-999");
		$("#TrackProgressForm_unit").val(d["unit"]);
		TrackProgressQueryGrid.query();
	}
}

function afterViewTrackprogress()
{
	ClientDetailQueryGridDialog.close();
	TrackProgressQueryGrid.query();
}

function changeClientType()
{
	var selectedId = ClientGrid.jqGrid("getGridParam", "selrow");
	if(selectedId == null)
	{
		art.dialog("请选择一条记录");
		return;
	}
	
	var d = ClientGrid.jqGrid("getRowData", selectedId);
	var param = new Array();
	param[0] = {id:d["id"]};
	var rs = new RemoteService("changeClientType");
	rs.send(param, changeClientTypesuc, null, "正在修改...");
	
}

function changeClientTypesuc(resp)
{
	if(resp == null || resp.type=="error")
	{
		if(resp.message==null)
			art.dialog("修改失败");
		else
			art.dialog(resp.message);
		
	} else {
		ClientQueryGrid.query();
		art.dialog({
			content:"修改成功",
			time:mainComponent.closeDialogTime
		});
	}
}

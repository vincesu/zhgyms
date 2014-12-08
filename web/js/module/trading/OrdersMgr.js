
var OrdersGrid = null;
var OrdersQueryGrid = null;

OrdersQueryGrid = new QueryGrid(
		"OrdersForm",
		[ {name:'buyer',type:'text',text:'采购商'},
		  {name:'name',type:'text',text:'姓名'},
		  {name:'contractNumber',type:'text',text:'合同号'}
//		  {name:'bt',type:'text',text:'省厅回盘起始时间',classname:'My97DatePicker'},
//		  {name:'et',type:'text',text:'省厅回盘截止时间',classname:'My97DatePicker'},
//		  {name:'yc0076',type:'select',text:'发送审核状态',options: [
//		                                         {value:"'038000','041200'",text:'全部',selected:"selected"},
//		                                         {value:"041200",text:'已发送审核'},
//		                                         {value:"038000",text:'未发送审核'}
//		                                         ] }
		 ],
		 null,
		 "getOrder",
		 "addOrder",
		 "removeOrder",
		 "updateOrder",
		 "pages/module/trading/Orders/OrdersDialog.html",
		 "OrdersDialogForm"
);

OrdersGrid = $("#OrdersGrid").jqGrid(
	{	
		url:'service/dataInteraction?serviceid=getOrdersList',
		mtype: "POST",
		datatype: "local", 
		colNames:["ID","采购商", "产品名称", "合同单号", "金额", "下单日期", "发货日期", "业务员", "预留字段","联系人","电话","地址"], 
		colModel:[ 
					{name:'id',index:'id',width:120,hidden:true,align:"center"},
					{name:'buyer',index:'buyer',width:120,align:"center"},
					{name:'name',index:'name',width:120,align:"center"},
					{name:'contractNumber',index:'contractNumber',width:120,align:"center"},
					{name:'sum',index:'sum',width:120,align:"center"},
					{name:'orderDate',index:'orderDate',width:120,align:"center"},
					{name:'deliveryDate',index:'deliveryDate',width:120,align:"center"},
					{name:'salesman',index:'salesman',width:120,align:"center"},
					{name:'reserved',index:'reserved',width:120,hidden:true,align:"center"},
					{name:'contact',index:'contact',width:120,hidden:true,align:"center"},
					{name:'phone',index:'phone',width:120,hidden:true,align:"center"},
					{name:'address',index:'address',width:120,hidden:true,align:"center"}
		         ],
		 width:mainComponent.mainPanelContentWidth,
		 height:mainComponent.mainPanelContentHeight,
		 rowNum:mainComponent.rowNum, 
		 rowList:[10,50,100],
		 pager: '#OrdersPager', 
		 //sortname: 'id',
		 viewrecords: true,
		 //sortorder: "desc", 
		 caption:"订单信息",
		 rownumbers: true
	}
);

jQuery("#OrdersGrid").jqGrid('navGrid','#OrdersPager',{edit:false,add:false,del:false});

OrdersQueryGrid.setGrid(OrdersGrid);

OrdersQueryGrid.afterPopup = function(data) {
	createAdaptiveComponent();
	return true;
};

OrdersQueryGrid.afterLoadData=function(data){
	createAdaptiveComponent();
};

//OrdersQueryGrid.createDoubleClick();
OrdersQueryGrid.setDoubleClickFunc(function() {
	viewOrder();
//	var selectedId = OrdersGrid.jqGrid("getGridParam", "selrow");
//	if(selectedId == null)
//	{
//		art.dialog("请选择一条记录");
//		return;
//	}
//	var data = OrdersGrid.jqGrid("getRowData", selectedId);
//	mainComponent.createTab(
//		"order-id-"+data["id"],
//		data["buyer"]+"订单",
//		"pages/module/trading/Orders/OrderDetail.html",
//		true,
//		false,
//		function() {
//			$("#OrdersDialogForm").attr("id","OrdersDialogForm"+data["id"]);
//			$("#orderFiles").attr("id","orderFiles"+data["id"]);
//			FormUtil.setValue("OrdersDialogForm"+data["id"],data);
//			var rs = new RemoteService("queryRelatedFilesWithOrderId");
//			var param = new Array();
//			param[0] = {id:data["id"]};
//			rs.send(param, suc, null, "正在获取文件...");
//			function suc(resp) 
//			{
//				if(resp.data.length == 0)
//				{
//					$("#orderFiles"+data["id"]).html("无任何相关文件");
//					return;
//				}
//				for(var s in resp.data)
//				{
//					addShowFile("orderFiles"+data["id"],resp.data[s]);
//				}
//			}
//		}
//	);
});

function viewOrder()
{
	var selectedId = OrdersGrid.jqGrid("getGridParam", "selrow");
	if(selectedId == null)
	{
		art.dialog("请选择一条记录");
		return;
	}
	var data = OrdersGrid.jqGrid("getRowData", selectedId);
	
	if(mainComponent.tabs.getTabPosision("order-id-"+data["id"])=="order-id-"+data["id"])
	{
		mainComponent.createTab(
			"order-id-"+data["id"],
			data["buyer"]+"订单",
			"pages/module/trading/Orders/OrderDetail.html",
			true,
			false,
			function() {
				$("#OrdersDialogForm").attr("id","OrdersDialogForm"+data["id"]);
				$("#orderFiles").attr("id","orderFiles"+data["id"]);
				FormUtil.setValue("OrdersDialogForm"+data["id"],data);
				var rs = new RemoteService("queryRelatedFilesWithOrderId");
				var param = new Array();
				param[0] = {id:data["id"]};
				rs.send(param, suc, null, "正在获取文件...");
				function suc(resp) 
				{
					if(resp.data.length == 0)
					{
						$("#orderFiles"+data["id"]).html("无任何相关文件");
						return;
					}
					for(var s in resp.data)
					{
						addShowFile("orderFiles"+data["id"],resp.data[s]);
					}
				}
			}
		);
	}
	else
	{
		mainComponent.tabs.show("order-id-"+data["id"]);
	}
}

function addRelateFile()
{
	var selectedId = OrdersGrid.jqGrid("getGridParam", "selrow");
	if(selectedId == null)
	{
		art.dialog("请选择一条记录");
		return;
	}
	var d = OrdersGrid.jqGrid("getRowData", selectedId);
	var dialog = art.dialog({
		lock:true,
		title: "添加",
		ok: function () {
			
			if($("#id").val()==null ||$("#id").val()==undefined)
			{
				art.dialog("请先上传文件");
				return false;
			}
			
			var data = FormUtil.getValue("OrderFilesDialogForm");
			
			if(data["relatedDescription2"]!='-1')
				data["relatedDescription"]=data["relatedDescription2"];
			
			var r = new RemoteService("addOrderFile");
			var p = new Array();
			p[0] = data;
			r.send(p, s, null, "正在添加数据...");
			function s(resp)
			{
				if(resp == null || resp.type=="error")
				{
					if(resp.message==null)
						art.dialog("添加失败");
					else
						art.dialog(resp.message);
					
				} else {
					art.dialog({
						content:"添加成功",
						time:mainComponent.closeDialogTime
					});
				}
			}
			return true;
		},
		cancelVal: '关闭',
		cancel: true
	});

	$.ajax({
	    url: "pages/module/trading/Orders/OrderFilesDialog.html",
	    success: function (data) {
	        dialog.content(data);
	        mainComponent.createCodeComboxBox();
	        $("#orderid").val(d["id"]);
	        $("#reserved").val(d["reserved"]);
	        new UploadComponent().createUploadComponent(
    			"upload-form","upload",
    			function(data) {
    				var ind = data.toString().indexOf("&");
    				if(ind == -1)
    				{
    					$("#id").val(data);
    				}
    				else
    				{
    					var fileid=data.toString().substr(0,ind);
    					$("#id").val(fileid);
    				}
    			},
    			function(errmsg) {
    				alert("上传失败，错误原因："+errmsg);
    			},
    			"uploadFile","message"
    		);
	    },
	    cache: false
	});
};

function addShowFile(divid,data)
{
	var expandName = getExpandingName(data["filename"]).toLowerCase();
	var type = null;
	if(expandName=="pdf")
		type = "pdf";
	else if(expandName=="xls" || expandName=="xlsx")
		type = "exl";
	else if(expandName=="dox" || expandName=="docx")
		type = "doc";
	else if(expandName=="rar" || expandName=="zip" || expandName=="7z")
		type = "rar";
	else
		type = "unknow";
	var div = $('<div id="orderfile-'+data["id"]+'" style="float: left; width: 450px; padding-right: 10px;" >');
	div.html('<table><tr><td><img src="css/framework/img/'+type+'.gif" /></td><td class="bb"><div><div style="float:left;width:240px;">'+data["rd"]+'：'+data["filename"]+'</div><a href="###" style="margin-left:15px;" onclick="javascript:downloadOrderFile(\''+data["filename"]+'\',\''+data["id"]+'\');">下载</a><a href="###" onclick="javascript:previewOrderFile(\''+data["id"]+'\');" style="margin-left:15px;">预览打印</a><a href="###" onclick="javascript:deleteOrderFile(\''+data["id"]+'\',\''+data["reserved"]+'\');" style="margin-left:15px;">删除</a></div></td></tr></table>');
	$("#"+divid).append(div);
}

function deleteOrderFile(id,reserved)
{
	var m="确定删除？";
	if(confirm(m))
	{
		var param = new Array();
		param[0] = {fileid:id,reserved:reserved};
		var rs = new RemoteService("removeOrderFile");
		rs.send(param, suc, null, "正在删除...");
	}
	function suc(resp)
	{
		if(resp == null || resp.type=="error")
		{
			if(resp.message==null)
				art.dialog("删除失败");
			else
				art.dialog(resp.message);
			
		} else {
			$("#orderfile-"+id).remove();
			art.dialog({
				content:"删除成功",
				time:mainComponent.closeDialogTime
			});
		}
	}
}

function downloadOrderFile(filename,id)
{
	new DownloadComponent().createDownloadComponent("OrderFilesDownloadForm","downloadFiles",filename,id);
}

function previewOrderFile(id)
{
	new PreviewComponent().createPreviewComponent("previewFiles",id);
}

function chooseClient()
{
	var cd = new ChooseDialog(
			null, 
			[
			  {name:'contact',type:'text',text:'联系人'},
			  {name:'unit',type:'text',text:'单位'}
			 ], 
			"getClientList",
			clientColNames,
			clientColModel, 
			function(data) {
				$("#dialogBuyer").val(data[0]["unit"]);
				createAdaptiveComponent();
//				$("#orderDialogName").val(data[0]["contact"]);
			}, 
			false, 
			null, 
			null, 
			null);
	cd.open();
}

function lockOrder()
{
	var selectedId = OrdersGrid.jqGrid("getGridParam", "selrow");
	if(selectedId == null)
	{
		art.dialog("请选择一条记录");
		return;
	}
	var d = OrdersGrid.jqGrid("getRowData", selectedId);
	var rs = new RemoteService("lockOrder");
	var param = new Array();
	param[0] = {id:d["id"]};
	rs.send(param, suc, null, "正在提交订单...");
	function suc(resp)
	{
		if(resp == null || resp.type=="error")
		{
			if(resp.message==null)
				art.dialog("提交失败");
			else
				art.dialog(resp.message);
			
		} else {
			art.dialog({
				content:"提交成功",
				time:mainComponent.closeDialogTime
			});
		}
	}
}

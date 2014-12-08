
var OrderFilesGrid = null;
var OrderFilesQueryGrid = null;

OrderFilesQueryGrid = new QueryGrid(
		"OrderFilesForm",
		[  {name:'type',type:'select',text:'文件类型',options: [
			{value:"",text:'全部',selected:"selected"},
			{value:"6",text:'报价单'},
			{value:"7",text:'产品设计'},
			{value:"8",text:'采购合同'},
			{value:"9",text:'生产单'},
			{value:"10",text:'水单'},
			{value:"11",text:'报关资料'}
			] },
		  {name:'buyer',type:'text',text:'采购商'},
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
		 "getOrderFile",
		 "addOrderFile",
		 "removeOrderFile",
		 null,
		 "pages/module/trading/Orders/OrderFilesDialog.html",
		 "OrderFilesDialogForm"
);

OrderFilesGrid = $("#OrderFilesGrid").jqGrid(
	{	
		url:'service/dataInteraction?serviceid=getOrderFilesList',
		mtype: "POST",
		datatype: "local", 
		colNames:["文件类型","文件名","ID","采购商", "姓名", "合同单号", "金额", "下单日期", "发货日期", "业务员", "预留字段","fileid","path"], 
		colModel:[ 
		          	{name:'type',index:'type',width:120,align:"center"},
		          	{name:'filename',index:'filename',width:120,hidden:true,align:"center"},
					{name:'id',index:'id',width:120,hidden:true,align:"center"},
					{name:'buyer',index:'buyer',width:120,align:"center"},
					{name:'name',index:'name',width:120,align:"center"},
					{name:'contractNumber',index:'contractNumber',width:120,align:"center"},
					{name:'sum',index:'sum',width:120,align:"center"},
					{name:'orderDate',index:'orderDate',width:120,align:"center"},
					{name:'deliveryDate',index:'deliveryDate',width:120,align:"center"},
					{name:'salesman',index:'salesman',width:120,align:"center"},
					{name:'reserved',index:'reserved',width:120,hidden:true,align:"center"},
					{name:'fileid',index:'fileid',width:120,hidden:true,align:"center"},
					{name:'path',index:'path',width:120,hidden:true,align:"center"}
		         ],
		 width:mainComponent.mainPanelContentWidth,
		 height:mainComponent.mainPanelContentHeight,
		 rowNum:mainComponent.rowNum, 
		 rowList:[10,50,100],
		 pager: '#OrderFilesPager', 
		 //sortname: 'id',
		 viewrecords: true,
		 //sortorder: "desc", 
		 caption:"订单相关文件",
		 gridComplete:function() {
			 OrderFilesQueryGrid.createCodeField(["type"],["orderfilestype"]);
		 },
		 rownumbers: true
	}
);

jQuery("#OrderFilesGrid").jqGrid('navGrid','#OrderFilesPager',{edit:false,add:false,del:false});

OrderFilesQueryGrid.setGrid(OrderFilesGrid);

OrderFilesQueryGrid.afterPopup = function() {
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
	mainComponent.createCodeComboxBox();
	$("#chooseOrder").click(function() {
		var cd = new ChooseDialog(
				null,
				[ {name:'buyer',type:'text',text:'采购商'},
				  {name:'name',type:'text',text:'姓名'},
				  {name:'contractNumber',type:'text',text:'合同号'}
//				  {name:'bt',type:'text',text:'省厅回盘起始时间',classname:'My97DatePicker'},
//				  {name:'et',type:'text',text:'省厅回盘截止时间',classname:'My97DatePicker'},
//				  {name:'yc0076',type:'select',text:'发送审核状态',options: [
//				                                         {value:"'038000','041200'",text:'全部',selected:"selected"},
//				                                         {value:"041200",text:'已发送审核'},
//				                                         {value:"038000",text:'未发送审核'}
//				                                         ] }
				 ],
				"getOrdersList",
				["ID","采购商", "姓名", "合同单号", "金额", "下单日期", "发货日期", "业务员", "预留字段","fileid","path"],
				[ 
					{name:'id',index:'id',width:120,hidden:true,align:"center"},
					{name:'buyer',index:'buyer',width:120,align:"center"},
					{name:'name',index:'name',width:120,align:"center"},
					{name:'contractNumber',index:'contractNumber',width:120,align:"center"},
					{name:'sum',index:'sum',width:120,align:"center"},
					{name:'orderDate',index:'orderDate',width:120,align:"center"},
					{name:'deliveryDate',index:'deliveryDate',width:120,align:"center"},
					{name:'salesman',index:'salesman',width:120,align:"center"},
					{name:'reserved',index:'reserved',width:120,hidden:true,align:"center"},
					{name:'fileid',index:'fileid',width:120,hidden:true,align:"center"},
					{name:'path',index:'path',width:120,hidden:true,align:"center"}
		         ],
				function(data) {
					$("#orderid").val(data[0]["id"]);
					$("#reserved").val(data[0]["reserved"]);
				},false,false,null,null
		);
		cd.open();
	});
};

OrderFilesQueryGrid.beforeSave = function() {
	if($("#id").val()==null || $("#id").val()=="")
	{
		alert("请先上传文件");
		return false;
	}
	if($("#orderid").val()==null || $("#orderid").val()=="")
	{
		alert("请先选择订单");
		return false;
	}
	return true;
};

function downloadOrderFile() {
	var selectedId = OrderFilesGrid.jqGrid("getGridParam", "selrow");
	if(selectedId == null)
	{
		art.dialog("请选择一条记录");
		return;
	}
	
	var d = OrderFilesGrid.jqGrid("getRowData", selectedId);
	
	new DownloadComponent().createDownloadComponent("OrderFilesDownloadForm","downloadFiles",d["filename"],d["fileid"]);
}



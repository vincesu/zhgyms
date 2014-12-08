
var TradeDataGrid = null;
var TradeDataQueryGrid = null;

TradeDataQueryGrid = new QueryGrid(
		"TradeDataForm",
		[ {name:'name',type:'text',text:'名称'},
		  {name:'type',type:'hidden',text:'',value:'13'}
//		  {name:'bt',type:'text',text:'省厅回盘起始时间',classname:'My97DatePicker'},
//		  {name:'et',type:'text',text:'省厅回盘截止时间',classname:'My97DatePicker'},
//		  {name:'yc0076',type:'select',text:'发送审核状态',options: [
//		                                         {value:"'038000','041200'",text:'全部',selected:"selected"},
//		                                         {value:"041200",text:'已发送审核'},
//		                                         {value:"038000",text:'未发送审核'}
//		                                         ] }
		 ],
		 null,
		 "getOneFile",
		 "addFile",
		 "removeFile",
		 null,//"updateTradeData",
		 "pages/module/archives/tradedata/TradeDataDialog.html",
		 "TradeDataDialogForm"
);

TradeDataGrid = $("#TradeDataGrid").jqGrid(
	{	
		url:'service/dataInteraction?serviceid=getFiles',
		mtype: "POST",
		datatype: "local", 
		colNames:['ID','名称','描述','path','pdfpath','type','相关文件描述','相关实体','上传时间','操作者','primary','reserved','webpath'], 
		colModel:[ 
				   {name:'id',index:'id', width:100,hidden:true,align:"center"},	
		           {name:'name',index:'name', width:120,align:"center"},
		           {name:'description',index:'description', width:120,hidden:true,align:"center"},
		           {name:'path',index:'path', width:120,hidden:true,align:"center"},
		           {name:'pdfpath',index:'pdfpath', width:120,hidden:true,align:"center"},
		           {name:'type',index:'type', width:120,hidden:true,align:"center"},
		           {name:'related_description',index:'related_description', width:120,hidden:true,align:"center"},
		           {name:'related_object',index:'related_object', width:120,hidden:true,align:"center"},
		           {name:'date',index:'date', width:120,align:"center"},
		           {name:'operator',index:'operator', width:120,align:"center"},
		           {name:'is_primary',index:'is_primary', width:120,hidden:true,align:"center"},
		           {name:'reserved',index:'reserved', width:120,hidden:true,align:"center"},
		           {name:'webpath',index:'webpath', width:120,hidden:true,align:"center"}
		         ],
		 width:mainComponent.mainPanelContentWidth,
		 height:mainComponent.mainPanelContentHeight,
		 rowNum:mainComponent.rowNum, 
		 rowList:[10,50,100],
		 pager: '#TradeDataPager', 
		 //sortname: 'id',
		 viewrecords: true,
		 //sortorder: "desc", 
		 caption:"外贸资料",
		 postData:{type:"13"},
		 rownumbers: true
	}
);

jQuery("#TradeDataGrid").jqGrid('navGrid','#TradeDataPager',{edit:false,add:false,del:false});

TradeDataQueryGrid.setGrid(TradeDataGrid);

//TradeDataQueryGrid.createDoubleClick();

//var d = FormUtil.getValue("TradeDataForm");
//alert(d["type"]);
//TradeDataQueryGrid.query();

TradeDataQueryGrid.afterReset = function() {
	FormUtil.setValue("TradeDataForm",{type:"13"});
};

TradeDataQueryGrid.afterPopup = function() {
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
	
	$("#upload").change(function(){
		$("#TradeDataDialogFormName").val(getFileNameWithoutExp(getFileNameFormPath($("#upload").val())));
	});
};

TradeDataQueryGrid.beforeSave = function() {
	if($("#id").val()==null || $("#id").val()=="")
	{
		alert("请先上传文件");
		return false;
	}
	else return true;
};

function downloadTradeData() {
	var selectedId = TradeDataGrid.jqGrid("getGridParam", "selrow");
	if(selectedId == null)
	{
		art.dialog("请选择一条记录");
		return;
	}
	
	var d = TradeDataGrid.jqGrid("getRowData", selectedId);
	
	var exname = getExpandingName(d["path"]);
	
	new DownloadComponent().createDownloadComponent("TradeDataDownloadForm","downloadFiles",d["name"]+"."+exname,d["id"]);
}

function previewTradeDataFile()
{
	var selectedId = TradeDataGrid.jqGrid("getGridParam", "selrow");
	if(selectedId == null)
	{
		art.dialog("请选择一条记录");
		return;
	}
	
	var d = TradeDataGrid.jqGrid("getRowData", selectedId);
	
	new PreviewComponent().createPreviewComponent("previewFiles",d["id"]);
}

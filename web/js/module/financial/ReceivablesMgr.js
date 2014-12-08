
var ReceivablesGrid = null;
var ReceivablesQueryGrid = null;

ReceivablesQueryGrid = new QueryGrid(
		"ReceivablesForm",
		[ {name:'name',type:'text',text:'名称'},
		  {name:'type',type:'hidden',text:'',value:'15'}
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
		 null,//"updateReceivables",
		 "pages/module/financial/receivables/ReceivablesDialog.html",
		 "ReceivablesDialogForm"
);

ReceivablesGrid = $("#ReceivablesGrid").jqGrid(
	{	
		url:'service/dataInteraction?serviceid=getFiles',
		mtype: "POST",
		datatype: "local", 
		colNames:['ID','名称','描述','path','pdfpath','type','相关文件描述','相关实体','上传时间','操作者','primary','reserved','webpath'], 
		colModel:[ 
				   {name:'id',index:'id', width:100,align:"center"},	
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
		 pager: '#ReceivablesPager', 
		 //sortname: 'id',
		 viewrecords: true,
		 //sortorder: "desc", 
		 caption:"应收款",
		 postData:{type:"15"},
		 rownumbers: true
	}
);

jQuery("#ReceivablesGrid").jqGrid('navGrid','#ReceivablesPager',{edit:false,add:false,del:false});

ReceivablesQueryGrid.setGrid(ReceivablesGrid);

//ReceivablesQueryGrid.createDoubleClick();

//var d = FormUtil.getValue("ReceivablesForm");
//alert(d["type"]);
//ReceivablesQueryGrid.query();

ReceivablesQueryGrid.afterReset = function() {
	FormUtil.setValue("ReceivablesForm",{type:"15"});
};

ReceivablesQueryGrid.afterPopup = function() {
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
};

ReceivablesQueryGrid.beforeSave = function() {
	if($("#id").val()==null || $("#id").val()=="")
	{
		alert("请先上传文件");
		return false;
	}
	else return true;
};

function downloadReceivables() {
	var selectedId = ReceivablesGrid.jqGrid("getGridParam", "selrow");
	if(selectedId == null)
	{
		art.dialog("请选择一条记录");
		return;
	}
	
	var d = ReceivablesGrid.jqGrid("getRowData", selectedId);
	
	var exname = getExpandingName(d["path"]);
	
	new DownloadComponent().createDownloadComponent("ReceivablesDownloadForm","downloadFiles",d["name"]+"."+exname,d["id"]);
}

function previewReceivablesFile()
{
	var selectedId = ReceivablesGrid.jqGrid("getGridParam", "selrow");
	if(selectedId == null)
	{
		art.dialog("请选择一条记录");
		return;
	}
	
	var d = ReceivablesGrid.jqGrid("getRowData", selectedId);
	
	new PreviewComponent().createPreviewComponent("previewFiles",d["id"]);
}

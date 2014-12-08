
var SemifinishedProductGrid = null;
var SemifinishedProductQueryGrid = null;

SemifinishedProductQueryGrid = new QueryGrid(
		"SemifinishedProductForm",
		[ {name:'name',type:'text',text:'名称'},
		  {name:'size',type:'text',text:'尺寸'}
//		  {name:'bt',type:'text',text:'省厅回盘起始时间',classname:'My97DatePicker'},
//		  {name:'et',type:'text',text:'省厅回盘截止时间',classname:'My97DatePicker'},
//		  {name:'yc0076',type:'select',text:'发送审核状态',options: [
//		                                         {value:"'038000','041200'",text:'全部',selected:"selected"},
//		                                         {value:"041200",text:'已发送审核'},
//		                                         {value:"038000",text:'未发送审核'}
//		                                         ] }
		 ],
		 null,
		 "getSemifinishedProduct",
		 "addSemifinishedProduct",
		 "removeSemifinishedProduct",
		 "updateSemifinishedProduct",
		 "pages/module/product/SemifinishedProduct/SemifinishedProductDialog.html",
		 "SemifinishedProductDialogForm"
);

SemifinishedProductGrid = $("#SemifinishedProductGrid").jqGrid(
	{	
		url:'service/dataInteraction?serviceid=getSemifinishedProductList',
		mtype: "POST",
		datatype: "local", 
		colNames:['fid','downpath','图片','编号','名称','尺寸'], 
		colModel:[ 
		           {name:'fid',index:'fid', width:60,hidden:true,align:"center",align:"center"},
		           {name:'downpath',index:'downpath', width:60,hidden:true,align:"center",align:"center"},
		           {name:'path',index:'path', width:60,align:"center",align:"center"},
				   {name:'id',index:'id', width:100,hidden:true,align:"center",align:"center"},	
		           {name:'name',index:'name', width:120,align:"center",align:"center"},
		           {name:'size',index:'size', width:120,align:"center",align:"center"}
		         ],
		 width:mainComponent.mainPanelContentWidth,
		 height:mainComponent.mainPanelContentHeight,
		 rowNum:mainComponent.rowNum, 
		 rowList:[10,50,100],
		 pager: '#SemifinishedProductPager', 
		 //sortname: 'id',
		 viewrecords: true,
		 //sortorder: "desc", 
		 caption:"最新产品列表",
		 gridComplete:function() {
				var ids = $("#SemifinishedProductGrid").jqGrid('getDataIDs');
				var img = null;
				var cl = null;
				var rowdata = null;
				for ( var i = 0; i < ids.length; i++) {
					cl = ids[i];
					rowdata = $("#SemifinishedProductGrid").jqGrid("getRowData", cl);
					img = '<img style="width:100px;height:100px" src="'+rowdata.path+'" />';
					$("#SemifinishedProductGrid").jqGrid('setRowData', cl, {
						"path" : img
					});
				}
			},
			 rownumbers: true

	}
);

jQuery("#SemifinishedProductGrid").jqGrid('navGrid','#SemifinishedProductPager',{edit:false,add:false,del:false});

SemifinishedProductQueryGrid.setGrid(SemifinishedProductGrid);

SemifinishedProductQueryGrid.createDoubleClick();

SemifinishedProductQueryGrid.afterPopup = function() {
	new UploadComponent().createUploadComponent(
			"upload-form","upload",
			function(data) {
				var ind = data.toString().indexOf("&");
				if(ind == -1)
				{
					$("#fileid").val(data);
				}
				else
				{
					var picid=data.toString().substr(0,ind);
					var picpath=data.toString().substr(ind+1);
					$("#fileid").val(picid);
					$("#imgpath").attr("src",picpath);
				}
			},
			function(errmsg) {
				alert("上次图片失败，错误原因："+errmsg);
			},
			"uploadFile","message"
		);
};

SemifinishedProductQueryGrid.beforeView = function(data) {
	$("#upload-form").hide();
};

SemifinishedProductQueryGrid.beforeSave = function() {
	
	if($("#fileid").val()==null || $("#fileid").val()=="")
	{
		alert("请先上传图片");
		return false;
	}
	else return true;
};

function downloadPic()
{
	
	var selectedId = SemifinishedProductQueryGrid.grid.jqGrid("getGridParam", "selrow");
	if(selectedId == null)
	{
		art.dialog("请选择一条记录");
		return;
	}
	var d = SemifinishedProductQueryGrid.grid.jqGrid("getRowData", selectedId);
	if(d["downpath"]=="")
	{
		art.dialog("无图片可下载");
	}
	var exname = getExpandingName(d["downpath"]);
	
	new DownloadComponent().createDownloadComponent("SemifinishedProductpicdownloadForm","downloadFiles",d["name"]+"."+exname,d["fid"]);
}

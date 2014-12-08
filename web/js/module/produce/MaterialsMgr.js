
var MaterialsGrid = null;
var MaterialsQueryGrid = null;

$(function() {

	MaterialsQueryGrid = new QueryGrid(
			"MaterialsForm",
			[ {name:'name',type:'text',text:'名称'},
			  {name:'category',type:'text',text:'分类',code:'materialcategory',classname:'combox',blankLine:'全部' }
	//		  {name:'bt',type:'text',text:'省厅回盘起始时间',classname:'My97DatePicker'},
	//		  {name:'et',type:'text',text:'省厅回盘截止时间',classname:'My97DatePicker'},
	//		  {name:'yc0076',type:'select',text:'发送审核状态',options: [
	//		                                         {value:"'038000','041200'",text:'全部',selected:"selected"},
	//		                                         {value:"041200",text:'已发送审核'},
	//		                                         {value:"038000",text:'未发送审核'}
	//		                                         ] }
			 ],
			 null,
			 "getMaterials",
			 "addMaterials",
			 "removeMaterials",
			 "updateMaterials",
			 "pages/module/produce/materials/MaterialsDialog.html",
			 "MaterialsDialogForm"
	);
	
	MaterialsGrid = $("#MaterialsGrid").jqGrid(
		{	
			url:'service/dataInteraction?serviceid=getMaterialsList',
			mtype: "POST",
			datatype: "local", 
			colNames:['图片','ID','编号','名称','尺寸','价格','厂家','联系人','电话','类别'], 
			colModel:[ 
			           {name:'path',index:'path', width:120,align:"center"},
					   {name:'id',index:'id', width:100,hidden:true,align:"center"},	
					   {name:'number',index:'number', width:120,hidden:true,align:"center"},
			           {name:'name',index:'name', width:120,align:"center"},
			           {name:'size',index:'size', width:120,align:"center"},
			           {name:'price',index:'price', width:120,align:"center"},
			           {name:'manufacturers',index:'manufacturers', width:120,align:"center"},
			           {name:'contact',index:'contact', width:120,align:"center"},
			           {name:'phone',index:'phone', width:120,align:"center"},
			           {name:'category',index:'category', width:120,align:"center"}
			         ],
			 width:mainComponent.mainPanelContentWidth,
			 height:mainComponent.mainPanelContentHeight,
			 rowNum:mainComponent.rowNum, 
			 rowList:[10,50,100],
			 pager: '#MaterialsPager', 
			 //sortname: 'id',
			 viewrecords: true,
			 //sortorder: "desc", 
			 caption:"材料信息列表",
			 rownumbers: true,
			 gridComplete:function() {
					var ids = $("#MaterialsGrid").jqGrid('getDataIDs');
					var img = null;
					var cl = null;
					var rowdata = null;
					for ( var i = 0; i < ids.length; i++) {
						cl = ids[i];
						rowdata = $("#MaterialsGrid").jqGrid("getRowData", cl);
						if(rowdata.path!='')
						{
							img = '<img style="width:100px;" src="'+rowdata.path+'" />';
							$("#MaterialsGrid").jqGrid('setRowData', cl, {
								"path" : img
							});
						}
					}
				}
		}
	);
	
	jQuery("#MaterialsGrid").jqGrid('navGrid','#MaterialsPager',{edit:false,add:false,del:false});
	
	MaterialsQueryGrid.setGrid(MaterialsGrid);
	
	MaterialsQueryGrid.setDoubleClickFunc(function(id) {
		rowdata = $("#MaterialsGrid").jqGrid("getRowData", id);
		if(rowdata['path']!=null && rowdata['path']!=undefined && rowdata['path']!="") {
			art.dialog({
			    content: rowdata.path.replace("100px","500px"),
			    title: '查看大图'
			});
		}
	});
	
	MaterialsQueryGrid.beforeView = function(data) {
		$("#upload-form").hide();
	};
	
	MaterialsQueryGrid.afterPopup = function() {
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
		mainComponent.createCodeComboxBox();
		$("#upload").change(function(){
			$("#MaterialsDialogName").val(getFileNameWithoutExp(getFileNameFormPath($("#upload").val())));
		});
		
	};
	
	MaterialsQueryGrid.beforeSave = function(data) {
		if($("#materialdialogcategory").val()!='-1')
		{
			data["category"] = $("#materialdialogcategory").val();
		}
		return true;
	};

});

function exportMaterials() {
	new DownloadComponent().createDownloadComponent("exportMaterialsForm","exportMaterials","Materials.xls");
}

function importMaterials() {
	new UploadComponent().pupupUploadDialog("importMaterials");
}

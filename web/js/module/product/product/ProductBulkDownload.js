
var ProductBulkDownloadGrid = null;
var ProductBulkDownloadQueryGrid = null;

$(function(){
	ProductBulkDownloadQueryGrid = new QueryGrid(
			"ProductBulkDownload",
			[ {name:'name',type:'text',text:'名称'},
			  {name:'number',type:'text',text:'编号'},
			  {name:'category',type:'text',text:'分类',code:'productcategory',classname:'combox',blankLine:'全部' },
//			  {name:'theorder',type:'text',text:'排序',code:'productorder',classname:'combox',blankLine:'a.have_sales_program desc,a.id desc ' },
			  {name:'theorder',type:'select',text:'排序',options: [
			                                         {value:"a.have_sales_program desc,a.id desc ",text:'价格在先'},
			                                         {value:"a.id desc ",text:'添加时间在先',selected:"selected"}
			                                         ] }
			 ],
			 null,
			 null,
			 null,
			 null,
			 null,
			 "pages/module/product/product/ProductDialog.html",
			 "ProductDialogForm"
	);
	
	ProductBulkDownloadGrid = $("#ProductBulkDownloadGrid").jqGrid(
		{	
			url:'service/dataInteraction?serviceid=getProductList',
			mtype: "POST",
			datatype: "local", 
			colNames:['fid','下载路径','图片','ID','名称','编号','尺寸','参考价格','起订量','描述','分类','是否有价格信息','业务员'], 
			colModel:[ 
			           {name:'fid',index:'fid', width:120,hidden:true,align:"center"},
			           {name:'downpath',index:'downpath', width:120,hidden:true,align:"center"},
			           {name:'path',index:'path', width:120,align:"center"},
					   {name:'id',index:'id', width:100,hidden:true,align:"center"},	
			           {name:'name',index:'name', width:100,align:"center"},
			           {name:'number',index:'number', width:100,align:"center"},
			           {name:'size',index:'size', width:100,align:"center"},
			           {name:'price',index:'price', width:100,align:"center"},
			           {name:'moq',index:'moq', width:100,align:"center"},
			           {name:'des',index:'des', width:100,align:"center",hidden:true},
			           {name:'category',index:'category', width:100,align:"center"},
			           {name:'haveSalesProgram',index:'haveSalesProgram', width:60},
			           {name:'username',index:'username', width:60}
			         ],
			 width:mainComponent.mainPanelContentWidth,
			 height:mainComponent.mainPanelContentHeight,
			 rowNum:10, 
			 rowList:[20,50,100,200,500,1000],
			 multiselect:true,
			 postData:{theorder:'a.id desc  '},
			 pager: '#ProductBulkDownloadPager', 
			 //sortname: 'id',
			 viewrecords: true,
			 //sortorder: "desc", 
			 caption:"产品列表",
			 gridComplete:function() {
				var ids = $("#ProductBulkDownloadGrid").jqGrid('getDataIDs');
				var img = null;
				var cl = null;
				var rowdata = null;
				for ( var i = 0; i < ids.length; i++) {
					cl = ids[i];
					rowdata = $("#ProductBulkDownloadGrid").jqGrid("getRowData", cl);
					img = "";
					if(rowdata["path"] != null && rowdata["path"]!=undefined &&
							rowdata["path"] != "") {
						img = '<img style="width:120px" src="'+rowdata.path+'" />';
						$("#ProductBulkDownloadGrid").jqGrid('setRowData', cl, {
							"path" : img
						});
					}
				}
//				ProductBulkDownloadQueryGrid.createCodeField(["haveSalesProgram"],["truefalse"]);
			 },
			 rownumbers: true
	
		}
	);
	
	jQuery("#ProductBulkDownloadGrid").jqGrid('navGrid','#ProductBulkDownloadPager',{edit:false,add:false,del:false});
	
	ProductBulkDownloadQueryGrid.setGrid(ProductBulkDownloadGrid,["haveSalesProgram"],["truefalse"]);
	
	//ProductBulkDownloadQueryGrid.createDoubleClick();
	
	/*
	ProductBulkDownloadQueryGrid.setDoubleClickFunc(function(){
		viewProductBulkDownloadPic();
	});
	
	ProductBulkDownloadQueryGrid.beforeView = function(data) {
		$("#upload-form").hide();
	};
	*/

});

function viewProductBulkDownloadPic()
{
	var selectedId = ProductBulkDownloadGrid.jqGrid("getGridParam", "selrow");
	if(selectedId == null)
	{
		art.dialog("请选择一条记录");
		return;
	}
	var d = ProductBulkDownloadGrid.jqGrid("getRowData", selectedId);
	art.dialog({
	    content: d.path.replace("120px","500px"),
	    title: '查看大图'
	});
}

function productBulkDownload()
{
	var selectedIds = ProductBulkDownloadGrid.jqGrid("getGridParam", "selarrrow");
	if(selectedIds.length == 0)
	{
		alert("请至少选择一个产品");
		return;
	}
	
	var params = "";
	var delimiter = ",";
	for(var i in selectedIds)
	{
		params = params + delimiter + ProductBulkDownloadGrid.jqGrid("getRowData", selectedIds[i])["fid"];
	}
	new DownloadComponent().createDownloadComponent("ProductPictureBulkDownloadForm", "productPicBulkDownload", "product_pictures(acount:"+selectedIds.length+").zip", params);
	
}



var SuppliersGrid = null;
var SuppliersQueryGrid = null;

SuppliersQueryGrid = new QueryGrid(
		"SuppliersForm",
		[ {name:'unit',type:'text',text:'单位'},
		  {name:'mainitems',type:'text',text:'主营项目'},
		  {name:'contact',type:'text',text:'联系人'}
//		  {name:'bt',type:'text',text:'省厅回盘起始时间',classname:'My97DatePicker'},
//		  {name:'et',type:'text',text:'省厅回盘截止时间',classname:'My97DatePicker'},
//		  {name:'yc0076',type:'select',text:'发送审核状态',options: [
//		                                         {value:"'038000','041200'",text:'全部',selected:"selected"},
//		                                         {value:"041200",text:'已发送审核'},
//		                                         {value:"038000",text:'未发送审核'}
//		                                         ] }
		 ],
		 null,
		 "getSuppliers",
		 "addSuppliers",
		 "removeSuppliers",
		 "updateSuppliers",
		 "pages/module/produce/Suppliers/SuppliersDialog.html",
		 "SuppliersDialogForm"
);

SuppliersGrid = $("#SuppliersGrid").jqGrid(
	{	
		url:'service/dataInteraction?serviceid=getSuppliersList',
		mtype: "POST",
		datatype: "local", 
		colNames:["ID","序号", "联系人", "单位", "电话", "主营项目", "联系方式", "地址", "开户行", "银行账户", "银行账户名", "预留字段"], 
		colModel:[ 
				   {name:'id',index:'id', width:100,hidden:true,align:"center"},	
		           {name:'number',index:'project', width:120,hidden:true,align:"center"},
		           {name:'contact',index:'project', width:120,align:"center"},
		           {name:'unit',index:'project', width:120,align:"center"},
		           {name:'phone',index:'project', width:120,align:"center"},
		           {name:'mainItems',index:'project', width:120,align:"center"},
		           {name:'contactWay',index:'project', width:120,align:"center"},
		           {name:'address',index:'price', width:120,align:"center"},
		           {name:'bank',index:'contact', width:120,align:"center"},
		           {name:'bankAccount',index:'unit', width:120,align:"center"},
		           {name:'bankAccount_name',index:'contactWay', width:120,align:"center"},
		           {name:'reserved',index:'remark', width:120,hidden:true,align:"center"}
		         ],
		 width:mainComponent.mainPanelContentWidth,
		 height:mainComponent.mainPanelContentHeight,
		 rowNum:mainComponent.rowNum, 
		 rowList:[10,50,100],
		 pager: '#SuppliersPager', 
		 //sortname: 'id',
		 viewrecords: true,
		 //sortorder: "desc", 
		 caption:"供应商信息",
		 rownumbers: true
	}
);

jQuery("#SuppliersGrid").jqGrid('navGrid','#SuppliersPager',{edit:false,add:false,del:false});

SuppliersQueryGrid.setGrid(SuppliersGrid);

SuppliersQueryGrid.createDoubleClick();

function exportSuppliers()
{
	new DownloadComponent().createDownloadComponent("exportSupplierstForm","exportSuppliers","suppliers.xls");
}

function importSuppliers()
{
	new UploadComponent().pupupUploadDialog("importSuppliers");
}

function freshEncodingTable()
{
	var rs = new RemoteService("getEncoding");
	rs.send(null,suc,err,"正在获得编码表");
	
	function suc(resp2) {
		mainComponent.encodeingArray = resp2.data;
		art.dialog({
			content:"刷新成功",
			time:mainComponent.closeDialogTime
		});
	}
	
	function err(resp2) {
		alert("加载编码表失败!");
	}
	
}

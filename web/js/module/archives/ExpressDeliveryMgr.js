
var ExpressDeliveryGrid = null;
var ExpressDeliveryQueryGrid = null;

ExpressDeliveryQueryGrid = new QueryGrid(
		"ExpressDeliveryForm",
		[ {name:'company',type:'text',text:'快递公司'}
//		  {name:'bt',type:'text',text:'省厅回盘起始时间',classname:'My97DatePicker'},
//		  {name:'et',type:'text',text:'省厅回盘截止时间',classname:'My97DatePicker'},
//		  {name:'yc0076',type:'select',text:'发送审核状态',options: [
//		                                         {value:"'038000','041200'",text:'全部',selected:"selected"},
//		                                         {value:"041200",text:'已发送审核'},
//		                                         {value:"038000",text:'未发送审核'}
//		                                         ] }
		 ],
		 null,
		 "getExpressDelivery",
		 "addExpressDelivery",
		 "removeExpressDelivery",
		 "updateExpressDelivery",
		 "pages/module/archives/ExpressDelivery/ExpressDeliveryDialog.html",
		 "ExpressDeliveryDialogForm"
);

ExpressDeliveryGrid = $("#ExpressDeliveryGrid").jqGrid(
	{	
		url:'service/dataInteraction?serviceid=getExpressDeliveryList',
		mtype: "POST",
		datatype: "local", 
		colNames:['ID','快递公司','联系人','电话'], 
		colModel:[ 
				   {name:'id',index:'id', width:100,hidden:true,align:"center"},	
		           {name:'company',index:'company', width:120,align:"center"},
		           {name:'contact',index:'contact', width:120,align:"center"},
		           {name:'phone',index:'phone', width:120,align:"center"}
		         ],
		 width:mainComponent.mainPanelContentWidth,
		 height:mainComponent.mainPanelContentHeight,
		 rowNum:mainComponent.rowNum, 
		 rowList:[10,50,100],
		 pager: '#ExpressDeliveryPager', 
		 //sortname: 'id',
		 viewrecords: true,
		 //sortorder: "desc", 
		 caption:"快递联系人",
		 rownumbers: true
	}
);

jQuery("#ExpressDeliveryGrid").jqGrid('navGrid','#ExpressDeliveryPager',{edit:false,add:false,del:false});

ExpressDeliveryQueryGrid.setGrid(ExpressDeliveryGrid);

ExpressDeliveryQueryGrid.createDoubleClick();

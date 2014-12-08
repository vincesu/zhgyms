var OperationLogGrid = null;
var OperationLogQueryGrid = null;

$(function(){
	OperationLogQueryGrid = new QueryGrid(
			"OperationLogForm",
			[ {name:'name',type:'text',text:'操作人'},
			  {name:'bt',type:'text',text:'操作起始时间',classname:'date'},
			  {name:'et',type:'text',text:'操作截止时间',classname:'date'},
			 ],
			 null,
			 null,
			 null,
			 null,
			 null,
			 null,
			 null
	);
	
	OperationLogGrid = $("#OperationLogGrid").jqGrid(
		{	
			url:'service/dataInteraction?serviceid=queryOperationLogList',
			mtype: "POST",
			datatype: "local",
			colNames:['id','操作','超时时间','操作人','操作人ID','业务类型'], 
			colModel:[ 
			           {name:'id',index:'id', width:80,hidden:true,align:"center"},
			           {name:'operation',index:'operation', width:300,align:"center"},
			           {name:'date',index:'date', width:80,align:"center"},
					   {name:'operator',index:'operator', width:80,align:"center"},	
			           {name:'operatorid',index:'operatorid', width:80,hidden:true,align:"center"},
			           {name:'type',index:'type', width:80,align:"center"}
			         ],
			 width:mainComponent.mainPanelContentWidth,
			 height:mainComponent.mainPanelContentHeight,
			 rowNum:2000, 
			 rowList:[500,1000,2000,5000,10000],
			 pager: '#OperationLogPager', 
			 //sortname: 'id',
			 viewrecords: true,
			 //sortorder: "desc", 
			 caption:"操作日志",
			 rownumbers: true
		}
	);
	
	jQuery("#OperationLogGrid").jqGrid('navGrid','#OperationLogPager',{edit:false,add:false,del:false});
	
	OperationLogQueryGrid.setGrid(OperationLogGrid);

});
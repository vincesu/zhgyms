
var SysParamGrid = null;
var SysParamQueryGrid = null;

SysParamQueryGrid = new QueryGrid(
		"SysParamForm",
		null,
		 null,
		 "getParameters",
		 "restoreParameter",
		 "removeParameter",
		 "restoreParameter",
		 "pages/module/system/SysParamDialog.html",
		 "SysParamDialogForm"
);

SysParamGrid = $("#SysParamGrid").jqGrid(
	{	
		url:'service/dataInteraction?serviceid=getParamList',
		mtype: "POST",
		datatype: "local", 
		colNames:['ID','code','参数值','是否可用','描述'], 
		colModel:[ 
				   {name:'id',index:'id', width:100,hidden:true,align:"center"},	
		           {name:'code',index:'code', width:120,align:"center",hidden:true},
		           {name:'value',index:'value', width:450,align:"center"},
		           {name:'available',index:'available', width:70,align:"center"},
		           {name:'description',index:'description', width:350,align:"center"}
		         ],
		 width:mainComponent.mainPanelContentWidth,
		 height:mainComponent.mainPanelContentHeight,
		 rowNum:50, 
		 rowList:[50,100,200],	
		 pager: '#SysParamPager', 
		 //sortname: 'id',
		 viewrecords: true,
		 //sortorder: "desc", 
		 caption:"系统参数配置",
		 rownumbers: true,
		 gridComplete:function() {
			 SysParamQueryGrid.createCodeField(["available"],["truefalse"]);
		 },
		 shrinkToFit:false
	}
);

jQuery("#SysParamGrid").jqGrid('navGrid','#SysParamPager',{edit:false,add:false,del:false});

SysParamQueryGrid.setGrid(SysParamGrid);


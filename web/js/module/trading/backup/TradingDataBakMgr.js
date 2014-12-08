var TradingDataBakGrid = null;
var TradingDataBakQueryGrid = null;
var TradingDataBakDetailGrid = null;
var TradingDataBakDetailQueryGrid = null;

$(function(){
	TradingDataBakQueryGrid = new QueryGrid(
			"TradingDataBakForm",
			[
			  {name:'contact',type:'text',text:'联系人'},
			  {name:'unit',type:'text',text:'单位'},
			  {name:'saleman',type:'text',text:'业务员',code:'userrealname',classname:'combox',blankLine:'全部'},
			  {name:'bt',type:'text',text:'起始时间',classname:'date'},
			  {name:'et',type:'text',text:'截止时间',classname:'date'}
	//		  {name:'yc0076',type:'select',text:'发送审核状态',options: [
	//		                                         {value:"'038000','041200'",text:'全部',selected:"selected"},
	//		                                         {value:"041200",text:'已发送审核'},
	//		                                         {value:"038000",text:'未发送审核'}
	//		                                         ] }
			 ],
			 null,
			 null,
			 null,
			 null,
			 null,
			 null,
			 null
	);
	
	TradingDataBakGrid = $("#TradingDataBakGrid").jqGrid(
		{	
			url:'service/dataInteraction?serviceid=getClientList',
			mtype: "POST",
			datatype: "local", 
			colNames:clientColNames,
			colModel:clientColModel,
			 width:mainComponent.mainPanelContentWidth,
			 height:mainComponent.mainPanelContentHeight,
			 rowNum:mainComponent.rowNum, 
			 rowList:[50,200,1000],
			 multiselect:true,
			 pager: '#TradingDataBakPager', 
			 //sortname: 'id',
			 viewrecords: true,
			 //sortorder: "desc", 
			 caption:"客户信息列表",
			 rownumbers: true
		}
	);
	
	jQuery("#TradingDataBakGrid").jqGrid('navGrid','#TradingDataBakPager',{edit:false,add:false,del:false});
	
	TradingDataBakQueryGrid.setGrid(TradingDataBakGrid);
	
	TradingDataBakQueryGrid.createloadComplete();
	
});

function tradingDataBackup()
{
	var selectedIds = TradingDataBakQueryGrid.grid.jqGrid("getGridParam", "selarrrow");
	if(selectedIds.length == 0)
	{
		alert("请至少选择一个客户");e
		return;
	}
	var delimiter = "_";
	var dateParam = FormUtil.getValue(TradingDataBakQueryGrid.queryfieldid);
	var bt = null,et = null;
	
	if(dateParam["bt"]!=undefined && dateParam["bt"]!="")
		bt = dateParam["bt"];
	else
		bt = "20010101";
	
	if(dateParam["et"]!=undefined && dateParam["et"]!="")
		et = dateParam["et"];
	else
		et = "99991231";
	
	var params = bt+"_"+et;
	
	for(var i in selectedIds)
	{
		params = params + delimiter + TradingDataBakQueryGrid.grid.jqGrid("getRowData", selectedIds[i])["id"];
		if(delimiter == "_")
			delimiter = "-";
	}
	new DownloadComponent().createDownloadComponent("tradingDataBakForm", "tradingDataBak", "backup-"+bt+"-"+et+".zip", params);
	/*
	var param = new Array();
	var selectedIds = TradingDataBakQueryGrid.grid.jqGrid("getGridParam", "selarrrow");
	for(var i in selectedIds)
	{
		param[i] = TradingDataBakQueryGrid.grid.jqGrid("getRowData", selectedIds[i]);
	}
	var rs = new RemoteService("tradingDataBak");
	rs.send(param,tradingDataBackupSuc,null,"正在备份文件...");
	*/
}


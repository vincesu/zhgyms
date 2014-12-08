$(function() {
	
	$("#AnnualTargetsSubmit").click(function() {
		var param = new Array();
		param[0] = {};
		param[0]["value"] = $("#annualtargets-content").val();
		param[0]["code"] = "ndmbb";
		param[0]["available"] = "1";
		if($("#annualtargets-id").val()!=null && $("#annualtargets-id").val()!='')
			param[0]["id"] = $("#annualtargets-id").val();
		var rs = new RemoteService("restoreParameter");
		rs.send(param, modifySuc, null, "正在修改数据...");
	});
	
	var rs2 = new RemoteService("getParameters");
	var p = new Array();
	p[0] = {id:1};
	rs2.send(p, loadAnnualTargetsData, null, "正在查询数据...");
	
});

function modifySuc(resp)
{
	if(resp == null || resp.type=="error")
		art.dialog("修改失败");
	else
		art.dialog({
			content:"修改成功",
			time:mainComponent.closeDialogTime
		});
}

function loadAnnualTargetsData(resp)
{
	if(resp == null || resp.type=="error")
	{
		art.dialog("查询失败");
	}
	else
	{
		if(resp.data.length>0)
		{
			$("#annualtargets-content").val(resp.data[0]["value"]);
			$("#annualtargets-id").val(resp.data[0]["id"]);
		}
	}
}
//
//var NoticeGrid = null;
//var NoticeQueryGrid = null;
//
//NoticeQueryGrid = new QueryGrid(
//		"NoticeForm",
//		[ {name:'title',type:'text',text:'标题'}
////		  {name:'bt',type:'text',text:'省厅回盘起始时间',classname:'My97DatePicker'},
////		  {name:'et',type:'text',text:'省厅回盘截止时间',classname:'My97DatePicker'},
////		  {name:'yc0076',type:'select',text:'发送审核状态',options: [
////		                                         {value:"'038000','041200'",text:'全部',selected:"selected"},
////		                                         {value:"041200",text:'已发送审核'},
////		                                         {value:"038000",text:'未发送审核'}
////		                                         ] }
//		 ],
//		 null,
//		 "getNotice",
//		 "addNotice",
//		 "removeNotice",
//		 "updateNotice",
//		 "pages/module/office/Notice/NoticeDialog.html",
//		 "NoticeDialogForm"
//);
//
//NoticeGrid = $("#NoticeGrid").jqGrid(
//	{	
//		url:'service/dataInteraction?serviceid=getNoticeList',
//		mtype: "POST",
//		datatype: "local", 
//		colNames:['ID','标题','发布者','日期'], 
//		colModel:[ 
//				   {name:'id',index:'id', width:100,align:"center"},	
//		           {name:'title',index:'company', width:120,align:"center"},
//		           {name:'author',index:'contact', width:120,align:"center"},
//		           {name:'date',index:'phone', width:120,align:"center"}
//		         ],
//		 width:mainComponent.mainPanelContentWidth,
//		 height:mainComponent.mainPanelContentHeight,
//		 rowNum:mainComponent.rowNum, 
//		 rowList:[10,50,100],
//		 pager: '#NoticePager', 
//		 //sortname: 'id',
//		 viewrecords: true,
//		 //sortorder: "desc", 
//		 caption:"通知",
//		 rownumbers: true
//	}
//);
//
//jQuery("#NoticeGrid").jqGrid('navGrid','#NoticePager',{edit:false,add:false,del:false});
//
//NoticeQueryGrid.setGrid(NoticeGrid);

NoticeQueryGrid.createDoubleClick();

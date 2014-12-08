var FinancialStatementsGrid = null;
var FinancialStatementsQueryGrid = null;
var lastsel4 = null;

$(function() {
	
	FinancialStatementsQueryGrid = new QueryGrid(
			"FinancialStatementsForm",
			[ {name:'startdate',type:'text',text:'起始时间',classname:'date'},
			  {name:'enddate',type:'text',text:'截止时间',classname:'date'},
			  {name:'year',type:'text',text:'年度',code:'year',classname:'combox',blankLine:'所有' },
			  {name:'status',type:'text',text:'状态',code:'financialstatementstatus',classname:'combox',blankLine:'所有' },
			  {name:'number',type:'text',text:'合同编号'},
			  {name:'username',type:'text',text:'业务员',code:'userrealname',classname:'combox',blankLine:'全部'}
	//		  {name:'bt',type:'text',text:'省厅回盘起始时间',classname:'My97DatePicker'},
	//		  {name:'et',type:'text',text:'省厅回盘截止时间',classname:'My97DatePicker'},
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
			 null,//"updateBalance",
			 null,
			 null
	);
	
	FinancialStatementsGrid = $("#FinancialStatementsGrid").jqGrid(
		{	
			url:'service/dataInteraction?serviceid=FinancialStatementsQueryList',
			mtype: "POST",
			datatype: "local", 
			colNames:["theid","合同时间","发货时间","合同编号","客户","国籍","客户来源","品名","合同金额","定金","余款",
						"运费","实际收款","RMB汇总","出口方式","买单费","代理费","开票费",
						"退税","状态","业务员","提成点","操作"], 
			colModel:[ 
					   {name:'theid',index:'theid', width:100,hidden:true,align:"center"},	
			           {name:'orderDate',index:'orderDate', width:80,align:"center"},
			           {name:'deliveryDate',index:'deliveryDate', width:80,align:"center"},
			           {name:'number',index:'number', width:70,align:"center"},
			           {name:'unit',index:'unit', width:150,align:"center"},
			           {name:'nationality',index:'nationality', width:60,align:"center"},
			           {name:'clientfrom',index:'clientfrom', width:60,align:"center"},
			           {name:'name',index:'name', width:100,align:"center"},
			           {name:'amount',index:'amount', width:85,align:"center"},
			           {name:'earnest',index:'earnest', width:85,align:"center",editable: true},
			           {name:'balance',index:'balance', width:85,align:"center",editable: true},
			           {name:'shipping',index:'shipping', width:85,align:"center",editable: true},
			           {name:'actualReceipts',index:'actualReceipts', width:85,align:"center",editable: true},
			           {name:'fee',index:'fee', width:85,align:"center",editable: true},
			           {name:'payment',index:'payment', width:85,align:"center"},
			           {name:'payFees',index:'payFees', width:85,align:"center",editable: true},
			           {name:'agencyFees',index:'agencyFees', width:85,align:"center",editable: true},
			           {name:'billingFee',index:'billingFee', width:85,align:"center",editable: true},
			           {name:'drawback',index:'drawback', width:85,align:"center",editable: true},
			           {name:'status',index:'status', width:60,align:"center"},
			           {name:'username',index:'username', width:60,align:"center"},
			           {name:'makepoint',index:'makepoint', width:60,align:"center"},
			           {name:'operator',index:'operator', width:60,align:"center",hidden:true}
			         ],
			 width:mainComponent.mainPanelContentWidth,
			 height:mainComponent.mainPanelContentHeight,
			 rowNum:mainComponent.rowNum, 
			 rowList:[10,50,100],
			 onSelectRow: function(id){
				var rowdata = jQuery("#FinancialStatementsGrid").jqGrid("getRowData", id);
				if(rowdata['status']=='完成') {
					return;
				}
				var operator = rowdata["operator"]+"+";
				$("#FinancialStatementsGrid").jqGrid('setRowData', id, {
					"operator" : operator
				});
				if(id && id!==lastsel4){
					jQuery('#FinancialStatementsGrid').jqGrid('saveRow',lastsel4);
					jQuery('#FinancialStatementsGrid').jqGrid('editRow',id,true);
					lastsel4=id;
				}
			},
			ondblClickRow: function(id){
				var rowdata = jQuery("#FinancialStatementsGrid").jqGrid("getRowData", id);
				if(rowdata['status']=='完成') {
					return;
				}
				var operator = rowdata["operator"]+"+";
				$("#FinancialStatementsGrid").jqGrid('setRowData', id, {
					"operator" : operator
				});
//				if(id && id!==lastsel4){
					jQuery('#FinancialStatementsGrid').jqGrid('saveRow',lastsel4);
					jQuery('#FinancialStatementsGrid').jqGrid('editRow',id,true);
					lastsel4=id;
//				}
			},
			gridComplete:function() {
				var ids = jQuery("#FinancialStatementsGrid").jqGrid('getDataIDs');
				var cl = null;
				var rowdata = null;
				for ( var i = 0; i < ids.length; i++) {
					cl = ids[i];
					rowdata = jQuery("#FinancialStatementsGrid").jqGrid("getRowData", cl);
					if(rowdata["actualReceipts"]==null || rowdata["actualReceipts"]=="") {
						continue;
					}
					jQuery("#FinancialStatementsGrid").jqGrid('setRowData', cl, {
						"actualReceipts" : rowdata["amount"].toString().substring(0,1)+rowdata["actualReceipts"]
					});
				}
//				ProductQueryGrid.createCodeField(["haveSalesProgram"],["truefalse"]);
				var rs = new RemoteService("queryFinancialstatementsTotal");
				var param = new Array();
				param[0] = FormUtil.getValue("FinancialStatementsForm");
				rs.send(param,queryFinancialstatementsTotalSuc,null,"正在查询统计...请稍后...");
			 },
			editurl: "",
			 shrinkToFit:false,
			 pager: '#FinancialStatementsPager', 
			 //sortname: 'id',
			 viewrecords: true,
			 //sortorder: "desc", 
			 caption:"财务报表",
			 postData:{type:"20"},
			 rownumbers: true
		}
	);
	
	jQuery("#FinancialStatementsGrid").jqGrid('navGrid','#FinancialStatementsPager',{edit:false,add:false,del:false});
	
	FinancialStatementsQueryGrid.setGrid(FinancialStatementsGrid,["status"],["financialstatementstatus"]);

	$("div#gview_FinancialStatementsGrid .ui-jqgrid-bdiv").keyup(function(event){
		var v1=0,v2=0,v3=0,v4=0;
		var selectedId = jQuery("#FinancialStatementsGrid").jqGrid("getGridParam", "selrow");
		if(selectedId) {
			var rowdata = jQuery("#FinancialStatementsGrid").jqGrid("getRowData", selectedId);
			if(event.keyCode!=8 && event.keyCode!=46 && event.keyCode!=16 ) {
				v1 = FormUtil.checkDouble(rowdata['earnest']);
				v2 = FormUtil.checkDouble(rowdata['balance']);
//				v3 = FormUtil.checkDouble(rowdata['fee']);
//				v4 = FormUtil.checkDouble(rowdata['shipping']);
//				v2 = checkContractDetail(rowdata, "unit");
				if(v1>=0 && v2>=0 
//						&& v3>=0 
//						&& v4>=0
						) {
					var amount = FormUtil.formatCurrencyD(rowdata['earnest'])
						+ FormUtil.formatCurrencyD(rowdata['balance'])
//						- FormUtil.formatCurrencyD(rowdata['fee'])
//						- FormUtil.formatCurrencyD(rowdata['shipping'])
						;
					if(rowdata['actualReceipts'].indexOf("input")>0) {
						rowdata = jQuery("#FinancialStatementsGrid").jqGrid("getRowData", selectedId);
						$("#"+$(rowdata['actualReceipts'])[0].id).val(rowdata["amount"].toString().substring(0,1)+ForDight(amount,2));
					}
				}
			}
		}
	});
});

function countFinancialStatementsAmount(rowdata) {
	var q,u,a;
	if(data["qty"].indexOf("input")>0) {
		q = $("#"+$(data["qty"])[0].id).val();
		$("#"+$(data["qty"])[0].id).css("background","inherit");
	} else {
		q = data["qty"];
	}
	q = contractFormat(q);
	if(data["unit"].indexOf("input")>0) {
		u = $("#"+$(data["unit"])[0].id).val();
		$("#"+$(data["unit"])[0].id).css("background","inherit");
	} else {
		u = data["unit"];
	}
	u = contractFormat(u);
	var i = new Number(q) * new Number(u);
	i = ForDight(i,2);
	if(data["amount"].indexOf("input")>0) {
		$("#"+$(data["amount"])[0].id).val(i);
	} 
}

function financialstatementsSave() {
	$('#FinancialStatementsGrid').jqGrid('saveRow',lastsel4);
	var ids = $('#FinancialStatementsGrid').jqGrid('getDataIDs');
	var param = new Array();
	var p = null;
	for ( var i = 0; i < ids.length; i++) {
		p = $('#FinancialStatementsGrid').jqGrid("getRowData", ids[i]);
		if(p["operator"].toString().trim() != "") {
			param[i] = p;
			param[i]["id"] = param[i]["theid"];
			param[i]["earnest"] = FormUtil.formatCurrency(param[i]["earnest"]);
			param[i]["balance"] = FormUtil.formatCurrency(param[i]["balance"]);
			param[i]["fee"] = FormUtil.formatCurrency(param[i]["fee"]);
			param[i]["shipping"] = FormUtil.formatCurrency(param[i]["shipping"]);
		}
//		param[i+1] = $('#FinancialStatementsGrid').jqGrid("getRowData", ids[i]);
	}
	var rs = new RemoteService("FinancialStatementsSave");
	rs.send(param,saveSuc,null,"正在保存数据...");
}

function saveSuc(resp) {
	if(resp==null||resp==undefined) {
		art.dialog("保存失败，请确认网络是否通畅");
	} else if(resp.type!=null && resp.type=='error') {
		art.dialog("保存失败，失败原因："+resp.message);
	} else {
		art.dialog("保存成功");
		FinancialStatementsQueryGrid.query();
	}
}

function financialstatementsFinish() {
	$('#FinancialStatementsGrid').jqGrid('saveRow',lastsel4);
	var selectedId = $('#FinancialStatementsGrid').jqGrid("getGridParam", "selrow");
//	var ids = $('#FinancialStatementsGrid').jqGrid('getDataIDs');
	var param = new Array();
	var p = $('#FinancialStatementsGrid').jqGrid("getRowData", selectedId);
	p["id"] = p["theid"];
	param[0]=p;
	var rs = new RemoteService("FinancialStatementsFinish");
	rs.send(param,saveSuc,null,"正在保存数据...");
}


function queryFinancialstatementsTotalSuc(resp)
{
	if(resp==null || resp.type=="error")
	{
		art.dialog("统计合计信息时出错");
		return;
	}
	else if(resp.data.length==0)
	{
		FinancialStatementsGrid.jqGrid('setCaption', "财务报表");
	}
	else if(resp.data.length==1)
	{
		if(resp.data[0]["currencySymbol"]!=undefined && resp.data[0]["amount"]!=undefined)
			FinancialStatementsGrid.jqGrid('setCaption', "财务报表————实际收款合计：共"+resp.data[0]["currencySymbol"]+ForDight(resp.data[0]["amount"],2)
				+"，  合同金额合计：共"+resp.data[0]["currencySymbol"]+ForDight(resp.data[0]["contractamount"],2));
		else
			FinancialStatementsGrid.jqGrid('setCaption', "财务报表");
	}
	else if(resp.data.length==2)
	{
		if(resp.data[0]["currencySymbol"]!=undefined && resp.data[0]["amount"]!=undefined
				&& resp.data[1]["currencySymbol"]!=undefined && resp.data[1]["amount"]!=undefined)
			FinancialStatementsGrid.jqGrid('setCaption', "财务报表————实际收款合计：共  "+resp.data[0]["currencySymbol"]+ForDight(resp.data[0]["amount"],2)+
				"，及  "+resp.data[1]["currencySymbol"]+ForDight(resp.data[1]["amount"],2)+
				"， 合同金额合计：共"+resp.data[0]["currencySymbol"]+ForDight(resp.data[0]["contractamount"],2)+
				"，及  "+resp.data[1]["currencySymbol"]+ForDight(resp.data[1]["contractamount"],2));
		else
			FinancialStatementsGrid.jqGrid('setCaption', "财务报表");
			
	}
}

function exportFSData() 
{
	new DownloadComponent().createDownloadComponent("exportFSDataForm","exportFSData","财务报表.xls");
}
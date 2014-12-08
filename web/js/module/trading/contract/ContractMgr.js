
var ContractGrid = null;
var ContractQueryGrid = null;
var theContractData = null;
var contractDefaultVar = null;
var _create = false;
var _autoSaveTime = 15000;

function queryContractDefaultSuc(resp) {
	if(resp==undefined || resp.type=="error") {
		art.dialog("获取系统参数失败，请刷新页面后重试");
	} else {
		contractDefaultVar = resp.data[0];
		if(_create) {
			_createAddContractTab();
		}
	}
	_create = false;
}

$(function(){
	
	ContractQueryGrid = new QueryGrid(
			"ContractForm",
			[ {name:'number',type:'text',text:'编号'},
			  {name:'status',type:'select',text:'审核状态',options: [
			                                	                 {value:".*",text:'全部',selected:"selected"},
			                                	                 {value:"^[0-9]",text:'未提交'},
			                                	                 {value:"lock[^e]",text:'审核中'},
			                                	                 {value:"locked",text:'已审核'},
			                                	                 {value:"finish",text:'完成'}
			                                	                 ] },
			  {name:'bt',type:'text',text:'起始时间',classname:'date'},
			  {name:'et',type:'text',text:'截止时间',classname:'date'},
			  {name:'client',type:'text',text:'客户'},
              {name:'salesman',type:'text',text:'业务员',code:'userrealname',classname:'combox',blankLine:'全部'}
			 ],
			 null,
			 "getContract",
			 null,
			 "removeContract",
			 null,
			 null,
			 null
	);
	
	ContractGrid = $("#ContractGrid").jqGrid(
		{	
			url:'service/dataInteraction?serviceid=queryContractList',
			mtype: "POST",
			datatype: "local", 
			colNames:['合同时间','发货时间','id','编号','客户名称','国籍',"客户来源",'地址','品名','金额','付款方式',
			          '发货方式','出口方式','备注','货币符','完整度','状态','提成点','业务员','reserved','fileid','path',
			          'filename','email','tel','fax','createc'], 
			colModel:[ 
						{name:'orderDate',index:'orderDate', width:120,align:"center"},
						{name:'deliveryDate',index:'deliveryDate', width:120,align:"center"},
						{name:'id',index:'id', width:100,hidden:true,align:"center"},
						 {name:'number',index:'number', width:180,align:"center"},
					   {name:'clientname',index:'clientname', width:180,align:"center"},
					   {name:'nationality',index:'nationality', width:80,align:"center"},
					   {name:'clientfrom',index:'clientfrom', width:80,align:"center"},
					   {name:'address',index:'address', width:120,align:"center",hidden:true},
					   {name:'name',index:'name', width:120,align:"center"},
					   {name:'amount',index:'amount', width:120,align:"center"},
					   {name:'leadTime',index:'leadTime', width:120,align:"center"},
			           {name:'shippingTrem',index:'shippingTrem', width:120,align:"center"},
			           {name:'payment',index:'payment', width:120,align:"center",hidden:true},
			           {name:'remark',index:'remark', width:120,align:"center",hidden:true},
			           {name:'currencySymbol',index:'currencySymbol', width:120,hidden:true},
			           {name:'complete',index:'complete', width:80,align:"center"},
			           {name:'status',index:'status', width:80,align:"center"},
			           {name:'makepoint',index:'makepoint', width:80,align:"center",hidden:false},
			           {name:'saleman',index:'saleman', width:80,align:"center"},
			           {name:'reserved',index:'reserved', width:120,align:"center",hidden:true},
			           {name:'fileid',index:'fileid', width:120,align:"center",hidden:true},
			           {name:'path',index:'path', width:120,align:"center",hidden:true},
			           {name:'filename',index:'filename', width:120,align:"center",hidden:true},
			           {name:'email',index:'email', width:120,align:"center",hidden:true},
			           {name:'tel',index:'tel', width:120,align:"center",hidden:true},
			           {name:'fax',index:'fax', width:120,align:"center",hidden:true},
			           {name:'createc',index:'createc', width:120,align:"center",hidden:true}
			         ],
			 width:mainComponent.mainPanelContentWidth,
			 height:mainComponent.mainPanelContentHeight,
			 rowNum:mainComponent.rowNum, 
			 rowList:[10,50,100],
			 pager: '#ContractPager', 
			 //sortname: 'id',
			 viewrecords: true,
			 //sortorder: "desc", 
			 caption:"合同列表",
			 rownumbers: true,
			 gridComplete:function() {
				 var rs = new RemoteService("queryContractTotal");
				 var param = new Array();
				 param[0] = FormUtil.getValue("ContractForm");
				 rs.send(param,queryContractTotalSuc,null,"正在查询统计...请稍后...");
			 }
		}
	);
	
	jQuery("#ContractGrid").jqGrid('navGrid','#ContractPager',{edit:false,add:false,del:false});
	
	var now = new Date();
	$("#ContractForm_bt").val(now.getFullYear().toString() + '-01-01');
	
	ContractQueryGrid.setGrid(ContractGrid);
	
	if(sys_username["reserved"]=="0") {
		$("#contract_audit_button").show();
		$("#contract_unaudit_button").show();
		$("#contract_finish_button").show();
	} else {
		$("#contract_audit_button").hide();
		$("#contract_unaudit_button").hide();
		$("#contract_finish_button").hide();
	}

	ContractQueryGrid.setDoubleClickFunc(function() {
		viewContractRelatedFiles();
	});
	
	var rs = new RemoteService("queryContractDefault");
	rs.send(null, queryContractDefaultSuc, null, '正在查询系统参数...');
	
});

function queryContractTotalSuc(resp)
{
	if(resp==null || resp.type=="error")
	{
		art.dialog("统计合计信息时出错");
		return;
	}
	else if(resp.data.length==0)
	{
		ContractGrid.jqGrid('setCaption', "合同列表");
	}
	else if(resp.data.length==1)
	{
		if(resp.data[0]["currencySymbol"]!=undefined && resp.data[0]["amount"]!=undefined)
			ContractGrid.jqGrid('setCaption', "合同列表————合计信息：共"+resp.data[0]["currencySymbol"]+resp.data[0]["amount"]);
		else
			ContractGrid.jqGrid('setCaption', "合同列表");
	}
	else if(resp.data.length==2)
	{
		if(resp.data[0]["currencySymbol"]!=undefined && resp.data[0]["amount"]!=undefined
				&& resp.data[1]["currencySymbol"]!=undefined && resp.data[1]["amount"]!=undefined)
			ContractGrid.jqGrid('setCaption', "合同列表————合计信息：共  "+resp.data[0]["currencySymbol"]+resp.data[0]["amount"]+
				"，及  "+resp.data[1]["currencySymbol"]+resp.data[1]["amount"]);
		else
			ContractGrid.jqGrid('setCaption', "合同列表");
			
	}
}

function downloadContract()
{
	var selectedId = ContractGrid.jqGrid("getGridParam", "selrow");
	if(selectedId == null)
	{
		art.dialog("请选择一条记录");
		return;
	}
	
	var d = ContractGrid.jqGrid("getRowData", selectedId);
	
	new DownloadComponent().createDownloadComponent("exportContractForm","downloadFiles",d["filename"],d["fileid"]);
}

function previewContract()
{
	var selectedId = ContractGrid.jqGrid("getGridParam", "selrow");
	if(selectedId == null)
	{
		art.dialog("请选择一条记录");
		return;
	}
	
	var d = ContractGrid.jqGrid("getRowData", selectedId);
	var filename = getFileNameWithoutExp(d["filename"]);
	new PreviewComponent().createPreviewComponent("previewFiles",d["fileid"],filename+".pdf");
}

function checkOpenContractTab()
{
	for(var i=0; i<mainComponent.tabs.tabs.length; i++)
    {
      if(mainComponent.tabs.tabs[i].id.substr(0,12)=='viewContract')
      {
    	  alert("已打开一个生产单详细页面，请先关闭后重试打开新页面。");
    	  return false;
      }
    }
	return true;
}

function addContract()
{
	if(!checkOpenContractTab()) return;
	
	turnOnAutoSave();
	
	if(mainComponent.tabs.getTabPosision("viewContract")=="viewContract")
	{
		if(contractDefaultVar==null || contractDefaultVar==undefined) {
			_create = true;
			var remoteService = new RemoteService("queryContractDefault");
			remoteService.send(null, queryContractDefaultSuc, null, '正在查询系统参数...');
		} else {
			_createAddContractTab();
		}
	}
	else
	{
		mainComponent.tabs.show("viewContract");
	}
}

function _createAddContractTab() {
	mainComponent.createTab(
		"viewContract",
		"添加合同",
		"pages/module/trading/contract/addContract.html",
		true,
		false,
		function() {
			mainComponent.createCodeComboxBox();
			$("#contract-email-input").val(contractDefaultVar["email"]);
			$("#contractPayment").val(contractDefaultVar["paymentEn"]);
			$("#contractShippingTrem").val(contractDefaultVar["shippingtrem"]);
			$("#contract-phone-input").val(contractDefaultVar["phone"]);
			$("#contract-fax-input").val(contractDefaultVar["fax"]);
			$("#contract_remark_input").val(contractDefaultVar["contractNote"]);
			var rs = new RemoteService("getCurrentContractNumber");
			rs.send(null, getContractNumberSuc, null, "正在获取合同编号...");
			
			contractLanguageChangeEvent();
			/*
			$("#contractLanguage").change(function(){
				
				var contractpaymentVal = $("#contractPayment").val();
				
				if($("#contractLanguage").val()=='0')
				{
					$("#contractPayment").attr("code","contractpayment_en");
					mainComponent.createCodeComboxBox();
//					$("#contractPayment").val(contractDefaultVar["paymentEn"]);
					$("#contractPayment").val(contractpaymentVal);
					$("#contract_remark_input").val(contractDefaultVar["contractNote"]);
				}
				else
				{
					$("#contractPayment").attr("code","contractpayment_cn");
					mainComponent.createCodeComboxBox();
//					$("#contractPayment").val(contractDefaultVar["paymentCn"]);
					$("#contractPayment").val(contractpaymentVal);
					$("#contract_remark_input").val(contractDefaultVar["contractNoteCn"]);
				}
			});
			*/
		}
	);
}

function getContractNumberSuc(resp)
{
	if(resp == undefined)
	{
		art.dialog("获取合同编号失败");
	}
	else if(resp.type == "error")
	{
		art.dialog(resp.message);
	}
	else
	{
		$("#contract_number_input").val(resp.message);
	}
}

function viewContract()
{
	if(!checkOpenContractTab()) return;
	
	var selectedId = ContractGrid.jqGrid("getGridParam", "selrow");
	if(selectedId == null)
	{
		art.dialog("请选择一条记录");
		return;
	}
	
	turnOffAutoSave();
	
	var data = ContractGrid.jqGrid("getRowData", selectedId);
	
	theContractData = data;

	if(mainComponent.tabs.getTabPosision("viewContract-"+data["id"])==("viewContract-"+data["id"]))
	{
		mainComponent.createTab(
			"viewContract-"+data["id"],
			data["number"],
			"pages/module/trading/contract/addContract.html",
			true,
			false,
			function() {
				if(theContractData['currencySymbol']=='$') {
					$("#contractPayment").attr("code","contractpayment_en");
					mainComponent.createCodeComboxBox();
				} else {
					$("#contractPayment").attr("code","contractpayment_cn");
					mainComponent.createCodeComboxBox();
				}
				mainComponent.createCodeComboxBox();
				FormUtil.setValue("addContractForm",theContractData);
				if(theContractData['createc']=='2') {
					$("#create-contract-flag").val("constant");
					var d = {};
					d["id"] = theContractData['fileid'];
					d["filename"] = theContractData['filename'];
					d["rd"] = "导入合同";
					_addContractShowFile("cus-contract",d);
				}
				$("#contract_remark_input").val(theContractData['remark']);
				$("#contractCurrencySymbol").val(theContractData['currencySymbol']);
				if(theContractData['currencySymbol']=='$') {
					$("#contractLanguage").val('0');
				} else {
					$("#contractLanguage").val('1');
				}
				var rs = new RemoteService("queryContractDetail");
				var param = new Array();
				param[0]={id:theContractData["id"]};
				rs.send(param,suc,null,"正在查询合同详细信息...");
				function suc(resp)
				{
					for(var i=0;i<resp.data.length;i++)
					{
						if(resp.data[i]["webpath"] != null && resp.data[i]["webpath"]!=undefined &&
								resp.data[i]["webpath"] != "" && resp.data[i]["webpath"]!="undefined") {
							jQuery("#addContractGrid").jqGrid(
								'addRowData',
								resp.data[i]["id"],
								{
									productId:resp.data[i]["productId"],
									number:resp.data[i]["number"],
									pic:'<img width="100px" src="'+resp.data[i]["webpath"]+'" />',
									description:resp.data[i]["description"],
									qty:resp.data[i]["qty"],
									unit:resp.data[i]["unit"],
									amount:resp.data[i]["amount"]
								} );
						} else {
							jQuery("#addContractGrid").jqGrid(
								'addRowData',
								resp.data[i]["id"],
								{
									productId:resp.data[i]["productId"],
									number:resp.data[i]["number"],
									pic:'',
									description:resp.data[i]["description"],
									qty:resp.data[i]["qty"],
									unit:resp.data[i]["unit"],
									amount:resp.data[i]["amount"]
								} );
						}

					}
					
					contractLanguageChangeEvent();
					
				}
				
			}
		);
	}
	else
	{
		mainComponent.tabs.show("viewContract-"+data["id"]);
	}
}

function contractLanguageChangeEvent()
{
	$("#contractLanguage").change(function(){
		
		var contractpaymentVal = $("#contractPayment").val();
		
		if($("#contractLanguage").val()=='0')
		{
			$("#contractPayment").attr("code","contractpayment_en");
			mainComponent.createCodeComboxBox();
//			$("#contractPayment").val(contractDefaultVar["paymentEn"]);
			$("#contractPayment").val(contractpaymentVal);
			$("#contract_remark_input").val(contractDefaultVar["contractNote"]);
		}
		else
		{
			$("#contractPayment").attr("code","contractpayment_cn");
			mainComponent.createCodeComboxBox();
//			$("#contractPayment").val(contractDefaultVar["paymentCn"]);
			$("#contractPayment").val(contractpaymentVal);
			$("#contract_remark_input").val(contractDefaultVar["contractNoteCn"]);
		}
	});
}

function contract_remove()
{
	var selectedId = ContractGrid.jqGrid("getGridParam", "selrow");
	if(selectedId == null)
	{
		art.dialog("请选择一条记录");
		return;
	}
	
	if(confirm("确定删除？"))
	{
		var param = new Array();
		param[0] = ContractGrid.jqGrid("getRowData", selectedId);
		var rs = new RemoteService("removeContract");
		
		rs.send(param,suc,null,"正在删除...");
	}
	
	function suc(resp)
	{
		if(resp == undefined)
		{
			art.dialog("删除失败");
		}
		else if(resp.type == "error")
		{
			art.dialog(resp.message);
		}
		else
		{
			ContractQueryGrid.query();
			art.dialog({
				content:"删除成功",
				time:mainComponent.closeDialogTime
			});
			
		}
	}
}

function contractAuditOrNot() {
	var selectedId = ContractGrid.jqGrid("getGridParam", "selrow");
	if(selectedId == null)
	{
		art.dialog("请选择一条记录");
		return;
	}
	var data = ContractGrid.jqGrid("getRowData", selectedId);
	if(data['status']=='已审核通过') {
		if(confirm("该合同已通过审核，是否取消审核？")) {
			UnApplicationReviewContract();
		}
	} else {
		ContractAudit();
	}
}

function ContractAudit()
{
	var selectedId = ContractGrid.jqGrid("getGridParam", "selrow");
	if(selectedId == null)
	{
		art.dialog("请选择一条记录");
		return;
	}
	
	var param = new Array();
	param[0] = ContractGrid.jqGrid("getRowData", selectedId);
	var rs = new RemoteService("ContractReview");
	
	rs.send(param,suc,null,"正在审核...");
	
	function suc(resp)
	{
		if(resp == undefined)
		{
			art.dialog("审核失败");
		}
		else if(resp.type == "error")
		{
			art.dialog(resp.message);
		}
		else
		{
			ContractQueryGrid.query();
			art.dialog({
				content:resp.message,
				time:mainComponent.closeDialogTime
			});
		}
	}
}

function ContractPreAudit()
{
	var selectedId = ContractGrid.jqGrid("getGridParam", "selrow");
	if(selectedId == null)
	{
		art.dialog("请选择一条记录");
		return;
	}
	
	var param = new Array();
	param[0] = ContractGrid.jqGrid("getRowData", selectedId);
	var rs = new RemoteService("ContractApplicationReview");
	
	rs.send(param,suc,null,"正在操作...");
	
	function suc(resp)
	{
		if(resp == undefined)
		{
			art.dialog("操作失败");
		}
		else if(resp.type == "error")
		{
			art.dialog(resp.message);
		}
		else
		{
			ContractQueryGrid.query();
			art.dialog({
				content:resp.message,
				time:mainComponent.closeDialogTime
			});
			
		}
	}
}

function addContractRelatedFiles()
{
//	var selectedId = ContractGrid.jqGrid("getGridParam", "selrow");
//	if(selectedId == null)
//	{
//		art.dialog("请选择一条记录");
//		return;
//	}
//	var d = ContractGrid.jqGrid("getRowData", selectedId);
	var dialog = art.dialog({
		lock:true,
		title: "添加",
		ok: function () {
			
			if($("#id").val()==null ||$("#id").val()==undefined)
			{
				art.dialog("请先上传文件");
				return false;
			}
			
			var data = FormUtil.getValue("OrderFilesDialogForm");
			
			if(data["relatedDescription2"]!='-1')
				data["relatedDescription"]=data["relatedDescription2"];
			
			var r = new RemoteService("addContractRelatedFile");
			var p = new Array();
			p[0] = data;
			r.send(p, s, null, "正在添加数据...");
			function s(resp)
			{
				if(resp == null || resp.type=="error")
				{
					if(resp.message==null)
						art.dialog("添加失败");
					else
						art.dialog(resp.message);
					
				} else {
//					if(ContractQueryGrid!=null)
//						ContractQueryGrid.query();
					queryContractRelatedFiles($('#contract-related-file-id').val());
					art.dialog({
						content:"添加成功",
						time:mainComponent.closeDialogTime
					});
				}
			}
			return true;
		},
		cancelVal: '关闭',
		cancel: true
	});

	$.ajax({
	    url: "pages/module/trading/Orders/OrderFilesDialog.html",
	    success: function (data) {
	        dialog.content(data);
	        mainComponent.createCodeComboxBox();
	        if($("#contract-related-file-id").val() == null || $("#contract-related-file-id").val()=="" ||
	        		$("#contract-related-file-reserved").val() == null || $("#contract-related-file-reserved").val()=="")
	        {
	        	alert("初始化上传控件失败，请于管理员联系!");
	        	return;
	        }
	        $("#orderid").val($("#contract-related-file-id").val());
	        $("#reserved").val($("#contract-related-file-reserved").val());
	        new UploadComponent().createUploadComponent(
    			"upload-form","upload",
    			function(data) {
    				var ind = data.toString().indexOf("&");
    				if(ind == -1)
    				{
    					$("#id").val(data);
    				}
    				else
    				{
    					var fileid=data.toString().substr(0,ind);
    					$("#id").val(fileid);
    				}
    			},
    			function(errmsg) {
    				alert("上传失败，错误原因："+errmsg);
    			},
    			"uploadFile","message"
    		);
	    },
	    cache: false
	});
	
}

function viewContractRelatedFiles()
{
	var selectedId = ContractGrid.jqGrid("getGridParam", "selrow");
	if(selectedId == null)
	{
		art.dialog("请选择一条记录");
		return;
	}
	var data = ContractGrid.jqGrid("getRowData", selectedId);
	debugger;
	if(mainComponent.tabs.getTabPosision("order-id-contractRelated")=="order-id-contractRelated")
	{
		mainComponent.createTab(
			"order-id-contractRelated",
			data["number"]+"合同",
			"pages/module/trading/contract/ContractDetail.html",
			true,
			false,
			function() {
				$("#OrdersDialogForm").attr("id","OrdersDialogForm"+data["id"]);
				$("#orderFiles").attr("id","orderFiles"+data["id"]);
				FormUtil.setValue("OrdersDialogForm"+data["id"],data);
				
				queryContractRelatedFiles(data['id']);
			}
		);
	}
	else
	{
		art.dialog("已经打开了一个合同相关页面，请先关闭之前页面");
//		mainComponent.tabs.show("order-id-contractRelated");
	}
}

function queryContractRelatedFiles(dataid) {
	
	var rs = new RemoteService("queryContractRelatedFiles");
	var param = new Array();
	param[0] = {id:dataid};
	rs.send(param, suc, null, "正在获取文件...");
	function suc(resp) 
	{
		if(resp.data.length == 0)
		{
			$("#orderFiles"+dataid).html("无任何相关文件");
			return;
		}
		$("#orderFiles"+dataid).empty();
		for(var s in resp.data)
		{
			addContractShowFile("orderFiles"+dataid,resp.data[s]);
		}
	}
}

function addContractShowFile(divid,data)
{
	var expandName = getExpandingName(data["filename"]).toLowerCase();
	var type = null;
	if(expandName=="pdf")
		type = "pdf";
	else if(expandName=="xls" || expandName=="xlsx")
		type = "exl";
	else if(expandName=="doc" || expandName=="docx")
		type = "doc";
	else if(expandName=="rar" || expandName=="zip" || expandName=="7z")
		type = "rar";
	else
		type = "unknow";
	var div = $('<div id="orderfile-'+data["id"]+'" style="float: left; width: 450px; padding-right: 10px;" >');
	div.html('<table><tr><td><img src="css/framework/img/'+type+'.gif" /></td><td class="bb"><div><div style="float:left;width:240px;">'+data["rd"]+'：'+data["filename"]+'</div><a href="###" style="margin-left:15px;" onclick="javascript:downloadContractFile(\''+data["filename"]+'\',\''+data["id"]+'\');">下载</a><a href="###" onclick="javascript:previewContractFile(\''+data["id"]+'\',\''+data["filename"]+'\');" style="margin-left:15px;">预览打印</a><a href="###" onclick="javascript:deleteContractFile(\''+data["id"]+'\',\''+data["reserved"]+'\');" style="margin-left:15px;">删除</a></div></td></tr></table>');
	if(data["rd"]=="合同") {
		$("#"+divid).prepend(div);
	} else {
		$("#"+divid).append(div);
	}
}

function downloadContractFile(filename,id)
{
	new DownloadComponent().createDownloadComponent("exportContractForm","downloadFiles",filename,id);
}

function previewContractFile(id,filename)
{
	var fn = getFileNameWithoutExp(filename);
	new PreviewComponent().createPreviewComponent("previewFiles",id,fn+".pdf");
//	new PreviewComponent().createPreviewComponent("previewFiles",id);
}

function deleteContractFile(id,reserved)
{
	var m="确定删除？";
	if(confirm(m))
	{
		var param = new Array();
		param[0] = {fileid:id,reserved:reserved};
		var rs = new RemoteService("removeContractRelatedObject");
		rs.send(param, suc, null, "正在删除...");
	}
	function suc(resp)
	{
		if(resp == null || resp.type=="error")
		{
			if(resp.message==null)
				art.dialog("删除失败");
			else
				art.dialog(resp.message);
			
		} else {
			$("#orderfile-"+id).remove();
			art.dialog({
				content:"删除成功",
				time:mainComponent.closeDialogTime
			});
		}
	}
}

function finishContract()
{
	var selectedId = ContractGrid.jqGrid("getGridParam", "selrow");
	if(selectedId == null)
	{
		art.dialog("请选择一条记录");
		return;
	}
	
	var param = new Array();
	param[0] = ContractGrid.jqGrid("getRowData", selectedId);
	var rs = new RemoteService("finishContract");
	
	rs.send(param,suc,null,"正在完结合同...");
	
	function suc(resp)
	{
		if(resp == undefined)
		{
			art.dialog("操作失败");
		}
		else if(resp.type == "error")
		{
			art.dialog(resp.message);
		}
		else
		{
			ContractQueryGrid.query();
			art.dialog({
				content:"操作成功",
				time:mainComponent.closeDialogTime
			});
			
		}
	}
}


function UnApplicationReviewContract()
{
	var selectedId = ContractGrid.jqGrid("getGridParam", "selrow");
	if(selectedId == null)
	{
		art.dialog("请选择一条记录");
		return;
	}
	
	var param = new Array();
	param[0] = ContractGrid.jqGrid("getRowData", selectedId);
	var rs = new RemoteService("UnApplicationReviewContract");
	
	rs.send(param,suc,null,"正在修改合同状态...");
	
	function suc(resp)
	{
		if(resp == undefined)
		{
			art.dialog("操作失败");
		}
		else if(resp.type == "error")
		{
			art.dialog(resp.message);
		}
		else
		{
			ContractQueryGrid.query();
			art.dialog({
				content:"操作成功",
				time:mainComponent.closeDialogTime
			});
			
		}
	}
}

function autoSaveContract()
{
	console.log("autosave");
	var isDialogClose = true;
	if(mainComponent.setttings["contractAutoSave"]  == undefined ||
			mainComponent.setttings["contractAutoSave"] == false)
		return;
	
	for(var i=0; i<mainComponent.tabs.tabs.length; i++)
    {
      if(mainComponent.tabs.tabs[i].id.substr(0,12)=='viewContract')
      {
    	  isDialogClose = false;
    	  break;
      }
    }
	
	if(isDialogClose)
		return;
	
	console.log("begin save");
	_finishContract(2);
	
}


function turnOffAutoSave()
{
	mainComponent.setttings["contractAutoSave"] = false;
	if(mainComponent.setttings["contractAutoSaveId"]!=undefined &&
			mainComponent.setttings["contractAutoSaveId"]!="") 
	{
		window.clearInterval(mainComponent.setttings["contractAutoSaveId"]);
	}
}

function turnOnAutoSave()
{
	turnOffAutoSave();
	mainComponent.setttings["contractAutoSave"] = true;
	mainComponent.setttings["contractAutoSaveId"]=window.setInterval(autoSaveContract,_autoSaveTime);
}

function calAllContract()
{
	var rs = new RemoteService("calAllContract");
	
	rs.send(null,suc,null,"正在重新计算全部合同的合同完整度...");
	
	function suc(resp)
	{
		if(resp == undefined)
		{
			art.dialog("操作失败");
		}
		else if(resp.type == "error")
		{
			art.dialog(resp.message);
		}
		else
		{
			ContractQueryGrid.query();
			art.dialog({
				content:"操作成功",
				time:mainComponent.closeDialogTime
			});
			
		}
	}
}



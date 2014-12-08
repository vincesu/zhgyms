
var ProduceGrid = null;
var ProduceQueryGrid = null;
var theProductionData = null;

$(function() {

	ProduceQueryGrid = new QueryGrid(
			"ProduceForm",
			[ {name:'number',type:'text',text:'编号'},
			  {name:'name',type:'text',text:'产品名称'},
			  {name:'salesman',type:'text',text:'业务员',code:'userrealname',classname:'combox',blankLine:'全部'},
			  {name:'bt',type:'text',text:'起始时间',classname:'date'},
			  {name:'et',type:'text',text:'截止时间',classname:'date'}
			 ],
			 null,
			 "getProduce",
			 "addProduce",
			 "removeProduce",
			 "updateProduce",
			 "pages/module/produce/produce/ProduceDialog.html",
			 "ProduceDialogForm"
	);
	
	ProduceGrid = $("#ProduceGrid").jqGrid(
		{	
			url:'service/dataInteraction?serviceid=getProduceList',
			mtype: "POST",
			datatype: "local", 
			colNames:['ID','产品名称','编号','时间','发货时间','业务员','目的地','是否审核','审核人','contractId','备注','fileid','path','filename','createc'], 
			colModel:[ 
					   {name:'id',index:'id', width:100,hidden:true,align:"center"},
					   {name:'name',index:'name', width:120,align:"center",hidden:true},
			           {name:'number',index:'number', width:120,align:"center"},
			           {name:'date',index:'date', width:120,align:"center"},
			           {name:'deliveryTime',index:'deliveryTime', width:120,align:"center"},
			           {name:'salesman',index:'salesman', width:120,align:"center"},
			           {name:'destination',index:'destination', width:120,align:"center"},
			           {name:'isAudit',index:'isAudit', width:120,align:"center",hidden:true},
			           {name:'audit',index:'audit', width:120,align:"center",hidden:true},
			           {name:'contractId',index:'contractId', width:120,align:"center",hidden:true},
			           {name:'remark',index:'remark', width:120,align:"center",hidden:true},
			           {name:'fileid',index:'fileid', width:120,align:"center",hidden:true},
			           {name:'path',index:'path', width:120,align:"center",hidden:true},
			           {name:'filename',index:'filename', width:120,align:"center",hidden:true},
			           {name:'createc',index:'createc', width:120,align:"center",hidden:true}
			         ],
			 width:mainComponent.mainPanelContentWidth,
			 height:mainComponent.mainPanelContentHeight,
			 rowNum:mainComponent.rowNum, 
			 rowList:[10,50,100],
			 pager: '#ProducePager', 
			 //sortname: 'id',
			 viewrecords: true,
			 //sortorder: "desc", 
			 caption:"生产单列表",
			 rownumbers: true
//			 ,
//			 gridComplete:function() {
//				 ProduceQueryGrid.createCodeField(["isAudit"],["trueorfalse"]);
//			 }
		}
	);
	
	jQuery("#ProduceGrid").jqGrid('navGrid','#ProducePager',{edit:false,add:false,del:false});
	
	ProduceQueryGrid.setGrid(ProduceGrid,["isAudit"],["trueorfalse"]);
	
	//ProduceQueryGrid.createDoubleClick();
	
	ProduceQueryGrid.afterPopup = function() {
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
					alert("上传失败，错误原因："+errmsg);
				},
				"uploadFile","message"
			);
		$("#producefiletable").hide();
	};
	
	ProduceQueryGrid.beforeView = function() {
		$("#upload-form").hide();
		var selectedId = ProduceGrid.jqGrid("getGridParam", "selrow");
		if(selectedId != null)
		{
			var d = ProduceGrid.jqGrid("getRowData", selectedId);
			$("#producefiletable").show();
			$("#producefilename").html("附件:"+d["filename"]);
		}
	};
	
	ProduceQueryGrid.beforeSave = function() {
		if($("#fileid").val()==null || $("#fileid").val()=="")
		{
			alert("请先上传文件");
			return false;
		}
		else return true;
	};

});
function downloadProduceFile()
{
	var selectedId = ProduceGrid.jqGrid("getGridParam", "selrow");
	if(selectedId == null)
	{
		art.dialog("请选择一条记录");
		return;
	}
	
	var d = ProduceGrid.jqGrid("getRowData", selectedId);
	
	new DownloadComponent().createDownloadComponent("exportProduceForm","downloadFiles",d["filename"],d["fileid"]);
}

function previewProduceFile()
{
	var selectedId = ProduceGrid.jqGrid("getGridParam", "selrow");
	if(selectedId == null)
	{
		art.dialog("请选择一条记录");
		return;
	}
	
	var d = ProduceGrid.jqGrid("getRowData", selectedId);
	
	new PreviewComponent().createPreviewComponent("previewFiles",d["fileid"]);
}

function addProduce()
{
	if(!checkOpenProduceTab()) return;
	if(mainComponent.tabs.getTabPosision("viewProduce")=="viewProduce")
	{
		mainComponent.createTab(
			"viewProduce",
			"添加生产单",
			"pages/module/produce/produce/addProduce.html",
			true,
			false,
			function() { }
		);
	}
	else
	{
		mainComponent.tabs.show("viewProduce");
	}
}

function viewProduce()
{
	if(!checkOpenProduceTab()) return;
	
	var selectedId = ProduceGrid.jqGrid("getGridParam", "selrow");
	if(selectedId == null)
	{
		art.dialog("请选择一条记录");
		return;
	}
	var data = ProduceGrid.jqGrid("getRowData", selectedId);
	
	theProductionData = data;

	if(mainComponent.tabs.getTabPosision("viewProduce-"+data["id"])==("viewProduce-"+data["id"]))
	{
		mainComponent.createTab(
			"viewProduce-"+data["id"],
			data["number"],
			"pages/module/produce/produce/addProduce.html",
			true,
			false,
			function() {
				if(theProductionData["createc"]=="2") {
					$("#createproducefile").val("produce");
					var d = {};
					d["id"] = theProductionData['fileid'];
					d["filename"] = theProductionData['filename'];
					d["rd"] = "导入生产单";
					_addProduceShowFile("cus-produce",d);
				}
				FormUtil.setValue("addProduceForm",theProductionData);
				var rs = new RemoteService("queryProductionList");
				var param = new Array();
				param[0]={id:theProductionData["id"]};
				rs.send(param,suc,null,"正在查询生产单信息...");
				function suc(resp)
				{
					for(var i=0;i<resp.data.length;i++)
					{
						if(resp.data[i]["pic"] != null && resp.data[i]["pic"]!=undefined &&
								resp.data[i]["pic"] != "" && resp.data[i]["pic"]!="undefined") {
							jQuery("#addProduceGrid").jqGrid(
								'addRowData',
								resp.data[i]["number"],
								{productId:resp.data[i]["productId"],number:resp.data[i]["number"],
									pic:'<img width="100px" src="'+resp.data[i]["pic"]+'" />',
									description:resp.data[i]["description"],amount:resp.data[i]["amount"],
									packing:resp.data[i]["packing"],remark:resp.data[i]["remark"]} );
						} else {
							jQuery("#addProduceGrid").jqGrid(
									'addRowData',
									resp.data[i]["number"],
									{productId:resp.data[i]["productId"],number:resp.data[i]["number"],
										pic:'',
										description:resp.data[i]["description"],amount:resp.data[i]["amount"],
										packing:resp.data[i]["packing"],remark:resp.data[i]["remark"]} );
						}
						

					}
				}
			}
		);
	}
	else
	{
		mainComponent.tabs.show("viewProduce-"+data["id"]);
	}
}

function checkOpenProduceTab()
{
	for(var i=0; i<mainComponent.tabs.tabs.length; i++)
    {
      if(mainComponent.tabs.tabs[i].id.substr(0,11)=='viewProduce')
      {
    	  alert("已打开一个生产单详细页面，请先关闭后重试打开新页面。");
    	  return false;
      }
    }
	return true;
}

function produceAudit()
{
	var selectedId = ProduceGrid.jqGrid("getGridParam", "selrow");
	if(selectedId == null)
	{
		art.dialog("请选择一条记录");
		return;
	}
	
	var param = new Array();
	param[0] = ProduceGrid.jqGrid("getRowData", selectedId);
	var rs = new RemoteService("auditProduce");
	
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
			ProduceQueryGrid.query();
			art.dialog({
				content:"审核成功",
				time:mainComponent.closeDialogTime
			});
			
		}
	}
}

function chooseContract()
{
	var cd = new ChooseDialog(
		null,
		[ {name:'number',type:'text',text:'编号'},
		  {name:'status',type:'select',text:'审核状态',options: [
		                                	                 {value:".*",text:'全部',selected:"selected"},
		                                	                 {value:"^[0-9]",text:'未审核'},
		                                	                 {value:"lock[^e]",text:'正在审核中'},
		                                	                 {value:"locked",text:'已审核通过'},
		                                	                 {value:"finish",text:'已完成'}
		                                	                 ] },
		  {name:'bt',type:'text',text:'起始时间',classname:'date'},
		  {name:'et',type:'text',text:'截止时间',classname:'date'},
          {name:'salesman',type:'text',text:'业务员',code:'userrealname',classname:'combox',blankLine:'全部'}
		 ],
		"queryContractList",
		['合同时间','发货时间','id','编号','客户名称','国籍',"客户来源",'地址','品名','金额','付款方式',
         '发货方式','出口方式','备注','货币符','完整度','状态','提成点','业务员','reserved','fileid','path',
         'filename','email','tel','fax','createc'],
         [ 
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
         {name:'makepoint',index:'makepoint', width:80,align:"center",hidden:true},
         {name:'status',index:'status', width:80,align:"center"},
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
		function(data){
			if(data[0]["reserved"].indexOf('locked')!=0)
			{
				alert('此订单未审核通过，不能生成生产单！');
				return false;
			}
			$("#produceNumber").val(data[0]["number"]);
			var rs = new RemoteService("getContractProduct");
			var param = new Array();
			param[0] = {contractid:data[0]["id"]};
			rs.send(param,addContarctProducts,null,"正在添加此合同产品...");
		},
		false,
		false,/*true or false*/
		null,
	    null
	);
	
//	cd.gridComplete = function() {
//		
//		var ids = cd.grid.jqGrid('getDataIDs');
//		var img = null;
//		var cl = null;
//		var rowdata = null;
//		for ( var i = 0; i < ids.length; i++) {
//			cl = ids[i];
//			rowdata = cd.grid.jqGrid("getRowData", cl);
//			img = '<img style="width:100px" src="'+rowdata.path+'" />';
//			cd.grid.jqGrid('setRowData', cl, {
//				"path" : img
//			});
//		}
//	};
	
	cd.open();
}

function addContarctProducts(resp) {
	if(resp == undefined)
	{
		art.dialog("获取合同产品失败");
	}
	else if(resp.type == "error")
	{
		art.dialog(resp.message);
	}
	else
	{
		jQuery("#addProduceGrid").jqGrid("clearGridData");
		for(obj in resp.data) {
			jQuery("#addProduceGrid").jqGrid(
					'addRowData',
					resp.data[obj]["number"],
					{productId:resp.data[obj]["productId"],number:resp.data[obj]["number"],pic:resp.data[obj]["path"],description:resp.data[obj]["description"],amount:resp.data[obj]["qty"],packaging:'',remark:''}
				);
		}
		formatProduceGridPicPath(jQuery("#addProduceGrid"),"pic");
	}
}

function formatProduceGridPicPath(grid,field) {
	if(grid!=undefined&&grid!=null) {
		var ids = grid.jqGrid('getDataIDs');
		var img = null;
		var cl = null;
		var rowdata = null;
		for ( var i = 0; i < ids.length; i++) {
			cl = ids[i];
			rowdata = grid.jqGrid("getRowData", cl);
			img = "";
			if(rowdata[field] != null && rowdata[field]!=undefined &&
					rowdata[field] != "") {
				if(rowdata[field].toString().indexOf("<img") >= 0)
					continue;
				img = '<img style="width:100px" src="'+rowdata[field]+'" />';
				var newrd = {};
				newrd[field] = img;
				grid.jqGrid('setRowData', cl, newrd);
			}
		}
	}
}
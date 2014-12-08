var lastsel2;
$(function(){
	jQuery("#addProduceGrid").jqGrid({
		datatype: "local",
	   	colNames:['productId','编号','图片', '描述', '数量','包装','备注'],
	   	colModel:[
//	   		{name:'id',index:'id', width:90, sorttype:"int", editable: true},
//	   		{name:'name',index:'name', width:150,editable: true,editoptions:{size:"20",maxlength:"30"}},
//	   		{name:'stock',index:'stock', width:60, editable: true,edittype:"checkbox",editoptions: {value:"Yes:No"}},
//	   		{name:'ship',index:'ship', width:90, editable: true,edittype:"select",editoptions:{value:"FE:FedEx;IN:InTime;TN:TNT;AR:ARAMEX"}},		
//	   		{name:'note',index:'note', width:200, sortable:false,editable: true,edittype:"textarea", editoptions:{rows:"2",cols:"10"}}
			{name:'productId',index:'productId', width:90, editable: false,hidden:true},
			{name:'number',index:'number', width:90, editable: true},
			{name:'pic',index:'pic', width:105, editable: false},
			{name:'description',index:'description', width:300, editable: true,edittype:"textarea", editoptions:{rows:"5",cols:"38"}},
			{name:'amount',index:'amount', width:90, editable: true},
			{name:'packing',index:'packing', width:90, editable: true,edittype:"textarea", editoptions:{rows:"5",cols:"10"}},
			{name:'remark',index:'remark', width:180, editable: true, editable: true,edittype:"textarea", editoptions:{rows:"5",cols:"20"}}
	   	],
		onSelectRow: function(id){
			if(id && id!==lastsel2){
				jQuery('#addProduceGrid').jqGrid('saveRow',lastsel2);
				jQuery('#addProduceGrid').jqGrid('editRow',id,true);
				lastsel2=id;
			}
		},
		ondblClickRow: function(id){
			jQuery('#addProduceGrid').jqGrid('editRow',id,true);
		},
		editurl: "",
		width:mainComponent.mainPanelContentWidth,
		height:mainComponent.mainPanelContentHeight,
		shrinkToFit:false
	});
	
	jQuery("#addProduceGrid").jqGrid('navGrid',"#addProducePager",{edit:false,add:false,del:false});
	
	$("#produceNumber").change(function(){
		var rs = new RemoteService("getContractProduct");
		var param = new Array();
		var ci = $("#produceNumber").val();
		param[0] = {contractid:ci};
		rs.send(param,addContarctProducts,null,"正在添加此合同产品...");
	});
});

//function addContarctProducts(resp) {
//	if(resp == undefined)
//	{
//		art.dialog("获取合同产品失败");
//	}
//	else if(resp.type == "error")
//	{
//		art.dialog(resp.message);
//	}
//	else
//	{
//		for(obj in resp.data) {
//			jQuery("#addProduceGrid").jqGrid(
//					'addRowData',
//					obj["number"],
//					{productId:obj["productId"],number:data[0]["number"],pic:obj["path"],description:obj["description"],amount:'',packaging:'',remark:''}
//				);
//		}
//	}
//}

function addP()
{
	var cd = new ChooseDialog(
		null,
		[ {name:'name',type:'text',text:'名称'},
		  {name:'number',type:'text',text:'编号'},
		  {name:'category',type:'text',text:'分类',code:'productcategory',classname:'combox',blankLine:'全部' },
		  {name:'theorder',type:'select',text:'排序',options: [
		                                         {value:"a.have_sales_program desc,a.id desc ",text:'价格在先'},
		                                         {value:"a.id desc ",text:'添加时间在先',selected:"selected"}
		                                         ] }
		 ],
		"getProductList",
		['fid','下载路径','图片','ID','名称','编号','尺寸','参考价格','起订量','描述','分类','是否有价格信息'],
		[ 
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
           {name:'haveSalesProgram',index:'haveSalesProgram', width:60}
         ],
		function(data){
			
			var pp = null;
			var ids = jQuery('#addProduceGrid').jqGrid('getDataIDs');
			for ( var i = 0; i < ids.length; i++) {
				pp = jQuery('#addProduceGrid').jqGrid("getRowData", ids[i]);
				pp = FormUtil.formatData(pp);
				if(pp["number"] == data[0]["number"])
				{
					art.dialog("已添加此产品");
					return;
				}
			}
			
			jQuery("#addProduceGrid").jqGrid(
					'addRowData',
					data[0]["number"],
					{productId:data[0]["id"],number:data[0]["number"],pic:data[0]["path"],description:'',amount:'',packaging:'',remark:''}
				);
		},
		false,
		false,/*true or false*/
		null,
	    null
	);
	cd.postData = {theorder:'a.id desc  '};
	cd.gridComplete = function() {
		formatProduceGridPicPath(cd.grid,'path');
//		var ids = cd.grid.jqGrid('getDataIDs');
//		var img = null;
//		var cl = null;
//		var rowdata = null;
//		for ( var i = 0; i < ids.length; i++) {
//			cl = ids[i];
//			rowdata = cd.grid.jqGrid("getRowData", cl);
//			img = "";
//			if(rowdata["path"] != null && rowdata["path"]!=undefined &&
//					rowdata["path"] != "") {
//				img = '<img style="width:100px" src="'+rowdata.path+'" />';
//				cd.grid.jqGrid('setRowData', cl, {
//					"path" : img
//				});
//			}
//		}
	};
	
	cd.createCompleted = function() {
		document.getElementById("choosedialogform_reset").onclick=function(){};
		$("#choosedialogform_reset").click(function(){
			popupToAdd('addProduct',
					'pages/module/product/product/ProductDialog.html',
					'ProductDialogForm',
					function(data){
						if($("#productdialogcategory").val()!="-1")
						{
							data["category"] = $("#productdialogcategory").val();
						}
						if($("#fileid").val()==null || $("#fileid").val()=="")
						{
							alert("请先上传图片");
							return false;
						}
						else return true;
					},
					function(thedata){
						if(thedata)
						{
							var d = mainComponent.getEncodingList("productcategory");
							var f = false;
							for(var j in d)
							{
								if(d[j].fieldValue==thedata["category"])
								{
									f=true;
									break;
								}
							}
							if(f)
								thedata["category2"]=thedata["category"];
							else
								thedata["category2"]="-1";
						}
						
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
								alert("上次图片失败，错误原因："+errmsg);
							},
							"uploadFile","message"
						);
						mainComponent.createCodeComboxBox();
					},
					function(data){
						var param = {number:data["number"]};
						cd.querygird.queryWithParam(param);
					});
		});
		$("#choosedialogform_resetbuttontext").html($("#choosedialogform_resetbuttontext").html().replace('重置','添加'));
	};
	
	cd.open();
}

function deleteP()
{
	var selectedId = jQuery("#addProduceGrid").jqGrid("getGridParam", "selrow");
	if(selectedId == null)
	{
		art.dialog("请选择一条记录");
		return;
	}
	$("#addProduceGrid").jqGrid("delRowData", selectedId);  
	
}

function finishP()
{
	var objectdata = FormUtil.getValue("addProduceForm");
	if(objectdata["date"]==null || objectdata["date"]==undefined || objectdata["date"]=="") {
		alert("请选择时间！");
		return;
	}
	if(objectdata["deliveryTime"]==null || objectdata["deliveryTime"]==undefined || 
			objectdata["deliveryTime"]=="") {
		alert("请选择发货时间！");
		return;
	}
	var ids = jQuery('#addProduceGrid').jqGrid('getDataIDs');
	if(ids.length == 0)
	{
		alert("请至少添加一个产品");
		return;
	}
	if($("#produceNumber").val()=="") {
		alert("请选择生产合同");
		return;
	}
	jQuery('#addProduceGrid').jqGrid('saveRow',lastsel2);
	var rs = new RemoteService("addProduction");
	var param = new Array();
	
	for ( var i = 0; i < ids.length; i++) {
		param[i+1] = jQuery('#addProduceGrid').jqGrid("getRowData", ids[i]);
	}
	if(param[0] == undefined)
		param[0]={};
	$.extend(param[0],FormUtil.getValue("addProduceForm"));
	rs.send(param,suc,null,"正在添加数据...");
}

function suc(resp)
{
	if(resp == undefined)
	{
		art.dialog("添加失败");
	}
	else if(resp.type == "error")
	{
		art.dialog(resp.message);
	}
	else
	{
		$("#add-product-id-input").val(resp.message);
		if(ProduceQueryGrid!=null && ProduceQueryGrid!=undefined) {
			ProduceQueryGrid.query();
		}
		art.dialog({
			content:"添加成功",
			time:mainComponent.closeDialogTime
		});
	}
}

function addExtraProduce()
{
	var seed = Math.floor(Math.random() * 1000);
//	jQuery("#addContractGrid").jqGrid(
//		'addRowData',
//		seed.toString(),
//		{productId:'',number:'',pic:'',description:'',qty:'',unit:'',amount:'0'}
//	);
	
	jQuery("#addProduceGrid").jqGrid(
			'addRowData',
			seed.toString(),
			{productId:'',number:'',pic:'',description:'',amount:'',packaging:'',remark:''}
		);
	
}


function _addProduceRelatedFiles()
{
	var dialog = art.dialog({
		lock:true,
		title: "添加",
		ok: function () {
			if($("#contractfileid").val()==null ||
					$("#contractfileid").val()==undefined || 
					$("#contractfileid").val()=="")
			{
				art.dialog("请先上传文件");
				return false;
			}
			var data = FormUtil.getValue("OrderFilesDialogForm");
			data["filename"] = getFileNameFormPath($("#upload").val());
			data["rd"] = "导入生产单";
			_addProduceShowFile("cus-produce",data);
			$("#createproducefile").val(data["id"]);
			return true;
		},
		cancelVal: '关闭',
		cancel: true
	});

	$.ajax({
	    url: "pages/module/trading/contract/ContractFilesDialog.html",
	    success: function (data) {
	        dialog.content(data);
	        mainComponent.createCodeComboxBox();
	        new UploadComponent().createUploadComponent(
    			"upload-form","upload",
    			function(data) {
    				var ind = data.toString().indexOf("&");
    				if(ind == -1) {
    					$("#contractfileid").val(data);
    				} else {
    					var fileid=data.toString().substr(0,ind);
    					$("#contractfileid").val(fileid);
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

function _removeProduceRelatedFiles() {
	$("#createproducefile").val("none");
	$("#cus-produce").empty();
}

function _addProduceShowFile(divid,data)
{
	$("#"+divid).empty();
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
	div.html('<table><tr><td><img src="css/framework/img/'+type+'.gif" /></td><td class="bb"><div><div style="float:left;width:240px;">'+data["rd"]+'：'+data["filename"]+'</div><a href="###" style="margin-left:15px;" onclick="javascript:_downloadProduceFile(\''+data["id"]+'\',\''+data["filename"]+'\');">下载</a><a href="###" onclick="javascript:_PerviewProduce(\''+data["id"]+'\');" style="margin-left:15px;">预览打印</a><a href="###" onclick="javascript:_removeProduceRelatedFiles();" style="margin-left:15px;">删除</a></div></td></tr></table>');
	if(data["rd"]=="生产单") {
		$("#"+divid).prepend(div);
	} else {
		$("#"+divid).append(div);
	}
	$("#"+divid+" .bb").removeClass("bb");
	$("#"+divid).val(data["id"]);
}

function _PerviewProduce(id) {
	new PreviewComponent().createPreviewComponent("previewFiles",id);
}

function _downloadProduceFile(id,filename) {
	new DownloadComponent().createDownloadComponent("exportProduceForm","downloadFiles",filename,id);
}
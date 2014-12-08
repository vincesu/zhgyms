var lastsel3;
var contract_detail_querygrid = new QueryGrid(
		null, null, null, "", "", "", "", "","");
var contract_detail_grid = null;
$(function(){
	contract_detail_grid = jQuery("#addContractGrid").jqGrid({
		datatype: "local",
	   	colNames:['productId','编号','图片', '描述', '数量','单价','总价'],
	   	colModel:[
			{name:'productId',index:'productId', width:90, editable: false,hidden:true},
			{name:'number',index:'number', width:90, editable: true,align:"center"},
			{name:'pic',index:'pic', width:105, editable: false},
			{name:'description',index:'description', width:400, editable: true,edittype:"textarea", editoptions:{rows:"5",cols:"62"}},
			{name:'qty',index:'qty', width:100, editable: true
//				,editrules: { integer: true, required: true} 
//				editrules:{
//					custom:true, 
//					custom_func: function(value, colname){
//						return [false,"error input"];  
//					}
//				}
			},
			{name:'unit',index:'unit', width:100, editable: true},
			{name:'amount',index:'amount', width:100, editable: true}
	   	],
		onSelectRow: function(id){
			if(id && id!==lastsel3){
				jQuery('#addContractGrid').jqGrid('saveRow',lastsel3);
				jQuery('#addContractGrid').jqGrid('editRow',id,true);
				lastsel3=id;
			}
		},
		ondblClickRow: function(id){
			jQuery('#addContractGrid').jqGrid('editRow',id,true);
		},
		editurl: "",
		width:mainComponent.mainPanelContentWidth-50,
		height:mainComponent.mainPanelContentHeight+30,
		shrinkToFit:false
	});
	
	jQuery("#addContractGrid").jqGrid('navGrid',"#addContractPager",{edit:false,add:false,del:false});
	
	$("#changeDaYang-button").click(function(){
		if($("#contract_number_input").val().indexOf('S')==0)
			$("#contract_number_input").val($("#contract_number_input").val().substring(1));
		else
			$("#contract_number_input").val('S'+$("#contract_number_input").val());
	});
	
	contract_detail_querygrid.grid = contract_detail_grid;
	
	$("#contract-buyerName").focus(function(){
		chooseClient();
	});
	
	$("#contract-address").focus(function(){
		chooseClient();
	});
	
	$("#contract-label-client").click(function(){
		chooseClient();
	});
	$("div#gview_addContractGrid .ui-jqgrid-bdiv").keyup(function(event){
		
		var v1 =0,v2 =0;
		var selectedId = jQuery("#addContractGrid").jqGrid("getGridParam", "selrow");
		if(selectedId) {
			var rowdata = jQuery("#addContractGrid").jqGrid("getRowData", selectedId);
//			if(event.keyCode!=8 && event.keyCode!=46 && event.keyCode!=16 ) {
				v1 = checkContractDetail(rowdata, "qty");
				v2 = checkContractDetail(rowdata, "unit");
				if(v1>0 && v2>0) 
					countAmount(rowdata);
				else
					resetAmount(rowdata);
//			} else {
				
//			}
		}
	});
});

function contract_addP()
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
			
			var ids = jQuery('#addContractGrid').jqGrid('getDataIDs');
			for ( var i = 0; i < ids.length; i++) {
				pp = jQuery('#addContractGrid').jqGrid("getRowData", ids[i]);
				pp = FormUtil.formatData(pp);
				if(pp["number"] == data[0]["number"])
				{
					art.dialog("已添加此产品");
					return;
				}
			}
			
			jQuery("#addContractGrid").jqGrid(
					'addRowData',
					data[0]["number"].toString().replace(/ /g,"_"),
					{productId:data[0]["id"],number:data[0]["number"],pic:data[0]["path"],description:'',qty:'',unit:'',amount:''}
				);
			
		},
		false,
		false,/*true or false*/
		null,
	    null
	);
	cd.postData = {theorder:'a.id desc  '};
	cd.gridComplete = function() {
		if(cd.querygrid!=undefined&&cd.querygrid!=null) {
			if(cd.querygrid.grid!=undefined && cd.querygrid.grid!=null) {
				var ids = cd.querygrid.grid.jqGrid('getDataIDs');
				var img = null;
				var cl = null;
				var rowdata = null;
				for ( var i = 0; i < ids.length; i++) {
					cl = ids[i];
					rowdata = cd.querygrid.grid.jqGrid("getRowData", cl);
					img = "";
					if(rowdata["path"] != null && rowdata["path"]!=undefined &&
							rowdata["path"] != "" && rowdata["path"]!="undefined") {
						img = '<img style="width:100px" src="'+rowdata.path+'" />';
						cd.querygrid.grid.jqGrid('setRowData', cl, {
							"path" : img
						});
					}
				}
			}
		}
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

function contract_deleteP()
{
	var selectedId = jQuery("#addContractGrid").jqGrid("getGridParam", "selrow");
	if(selectedId == null)
	{
		art.dialog("请选择一条记录");
		return;
	}
	if(confirm("确定删除？"))
	{
		$("#addContractGrid").jqGrid("delRowData", selectedId);  
	}
}

function contract_finishP()
{
	_finishContract(1);
}

function _finishContract(param)
{
	// flag 1 normal 2 auto save
	var flag = 0;
	if(param == undefined)
		flag = 1;
	else if(param==1)
		flag = 1;
	else
		flag = 2;
	var ids = jQuery('#addContractGrid').jqGrid('getDataIDs');
	
	if(ids.length == 0)
	{
		if(flag == 1)
			alert("请至少添加一个产品");
		return;
	}
	
	// not exists upload contract file
	/*
	if($("#create-contract-flag")==null || $("#create-contract-flag").val()==null
			|| $("#create-contract-flag").val()=="" || $("#create-contract-flag").val()=="none")
	{
		if(ids.length == 0)
		{
			if(flag == 1)
				alert("请至少添加一个产品");
			return;
		}
	}
	*/
	
	if(flag == 1)
	{
		jQuery('#addContractGrid').jqGrid('saveRow',lastsel3);
	}
	
	var rs = new RemoteService("restoreContract");
	var param = new Array();
	
	if(param[0] == undefined)
		param[0]={};
	$.extend(param[0],FormUtil.getValue("addContractForm"));
	if(param[0]["clientname"]==undefined ||param[0]["clientname"]=='') {
		if(flag == 1)
			alert('请选择客户');
		return;
	}
	if(param[0]["orderDate"]==undefined ||param[0]["orderDate"]=='') {
		if(flag == 1)
			alert('请填写下单时间');
		return;
	}
	
	if(!FormUtil.check(document.getElementById("addContractForm"),(flag==1?true:false)))
		return;
	
	var rows = jQuery('#addContractGrid').jqGrid("getRowData");
	var ok = true;
	jQuery(rows).each(function(){
		FormUtil.formatData(this);
		if(this.qty==null || this.qty==undefined || this.qty=="") {
			//do something
		} else {
			if(!(/^\d+$/.test(this.qty))) {
				if(flag == 1)
					art.dialog({
					    title: '错误',
					    content: '编号为：'+this.number+"产品的数量有误，错误值为："+this.qty
					});
				ok = false;
				return false;
			}
		}
		if(this.unit==null || this.unit==undefined || this.unit=="") {
			//do something
		} else {
			if(this.unit.toString().indexOf("$")==0 && $("#contractCurrencySymbol").val()=="￥") {
				if(flag == 1)
					art.dialog({
					    title: '错误',
					    content: '编号为：'+this.number+"产品的单价货币符有误，与合同货币符不符"
					});
				ok = false;
				return false;
			}
			if(this.unit.toString().indexOf("￥")==0 && $("#contractCurrencySymbol").val()=="$") {
				if(flag == 1)
					art.dialog({
					    title: '错误',
					    content: '编号为：'+this.number+"产品的单价货币符有误，与合同货币符不符"
					});
				ok = false;
				return false;
			}
			
			if(/^\d+\.\d+$/.test(contractFormat(this.unit))==false && /^\d+$/.test(contractFormat(this.unit))==false) {
				if(flag == 1)
					art.dialog({
					    title: '错误',
					    content: '编号为：'+this.number+"产品的单价有误，错误值为："+this.unit
					});
				ok = false;
				return false;
			}
		}
    });
	if(!ok)	return;
	param[0]["remark"]=$("#contract_remark_input").val();
	var pp = null;
	for ( var i = 0; i < ids.length; i++) {
		pp = jQuery('#addContractGrid').jqGrid("getRowData", ids[i]);
		pp = FormUtil.formatData(pp);
		if(pp['unit'].indexOf('$')==0) {
			pp['unit']=pp['unit'].substring(1);
		}
		if(pp['unit'].indexOf('￥')==0) {
			pp['unit']=pp['unit'].substring(1);
		}
		param[i+1] = pp;
	}
	if(flag == 1)
	{
		rs.send(param,suc,null,"正在添加数据...");
		
		if(turnOffAutoSave!=undefined)
		{
			turnOffAutoSave();
		}
		
	}
	else
	{
		param[0]["number"] = param[0]["number"]+"-自动保存";
		rs.send(param,sucWithoutMsg,null,null);
	}
	
}

function suc(resp)
{
	if(resp == undefined)
	{
		art.dialog("保存失败");
	}
	else if(resp.type == "error")
	{
		if(resp.errorCode=='001') {
			$("#contract_number_input").val(resp.message);
			art.dialog('该合同编号已经存在，已经重新获取合同编号，新编号为：'+resp.message+"。请点击完成重试。");
		}
		else {
			art.dialog(resp.message);
		}
	}
	else
	{
		$("#add-contract-id-input").val(resp.message);
		art.dialog({
			content:"保存成功",
			time:mainComponent.closeDialogTime
		});
		if(ContractQueryGrid!=null && ContractQueryGrid!=undefined) ContractQueryGrid.query();
	}
}


function sucWithoutMsg(resp)
{
	if(resp == undefined)
	{
		return;
	}
	else if(resp.type == "error")
	{
		return
	}
	else
	{
		$("#add-contract-id-input").val(resp.message);
	}
}

function chooseClient()
{
	var cd = new ChooseDialog(
			null,
			[
			  {name:'contact',type:'text',text:'联系人'},
			  {name:'unit',type:'text',text:'单位'},
			  {name:'saleman',type:'text',text:'业务员',code:'userrealname',classname:'combox',blankLine:'全部'}
			 ],
			"getClientList",
			clientColNames,
			clientColModelShort,
			function(data){
				$("#contract-buyerid").val(data[0]["id"]);
				$("#contract-buyerName").val(data[0]["unit"]);
				$("#contract-address").val(data[0]["address"]);
				if(data[0]["clientfrom"]=="广交会" && data[0]["jointime"].toString().substr(0,6) > "201403" ) 
				{
					$("#makepoint").val("1.5%");
				}
				else
				{
					$("#makepoint").val("2%");
				}
			},
			false,
			false,/*true or false*/
			null,
		    null
		);
	
	cd.createCompleted = function() {
		document.getElementById("choosedialogform_reset").onclick=function(){};
		$("#choosedialogform_reset").click(function(){
			popupToAdd('addClient',
					'pages/module/trading/client/ClientDialog.html',
					'ClientDialogForm',
					function(data){
						if(!FormUtil.check(document.getElementById("ClientDialogForm"),true))
						{
							return false;
						}
						data["number"] = "-1";
						return true;
					},
					function(thedata){
						createAdaptiveComponent();
						return true;
					},
					function(data){
						var param = {contact:data["contact"]};
						cd.querygird.queryWithParam(param);
					});
		});
		$("#choosedialogform_resetbuttontext").html($("#choosedialogform_resetbuttontext").html().replace('重置','添加'));
	};
	
	cd.open();
}

function addExtraFee()
{
	var seed = Math.floor(Math.random() * 1000);
	jQuery("#addContractGrid").jqGrid(
		'addRowData',
		seed.toString(),
		{productId:'',number:'',pic:'',description:'',qty:'',unit:'',amount:'0'}
	);
}

function contract_detail_querygrid_up()
{
	var data = contractDetailGetRowData();
	if(data)
		contract_detail_querygrid.up(data);
}

function contract_detail_querygrid_down()
{
	var data = contractDetailGetRowData();
	if(data)
		contract_detail_querygrid.down(data);
}

function contractDetailGetRowData()
{
	var selectedId = contract_detail_grid.jqGrid("getGridParam", "selrow");
	if(selectedId == null)
	{
		art.dialog("请选择一条记录");
		return undefined;
	}
	var rowdata = contract_detail_grid.jqGrid("getRowData", selectedId);
	var data = null;
	if(rowdata["description"].toString().indexOf("text")>=0)
		data = {
			productId:rowdata["productId"],
			number:rowdata["number"],
			pic:rowdata["pic"],
			description:$("#"+$(rowdata["description"]).attr("id")).val(),
			qty:$("#"+$(rowdata["qty"]).attr("id")).val(),
			unit:$("#"+$(rowdata["unit"]).attr("id")).val(),
			amount:$("#"+$(rowdata["amount"]).attr("id")).val()
		};
	else
		data = {
			productId:rowdata["productId"],
			number:rowdata["number"],
			pic:rowdata["pic"],
			description:rowdata["description"],
			qty:rowdata["qty"],
			unit:rowdata["unit"],
			amount:rowdata["amount"]
		};
	return data;
}

function countAmount(data) {
	var q,u;
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

function resetAmount(data) {
	if(data["amount"].indexOf("input")>0) {
		$("#"+$(data["amount"])[0].id).val("");
	} 
}

function contractFormat(val) {
	if(val[0]=="$"||val[0]=="￥") {
		return val.substring(1);
	}
	return val;
}

function checkContractDetail(rowdata,field,reg) {
	if(rowdata[field].indexOf("input")>0) {
		var _qtye = $(rowdata[field]);
		var qtye = $('#'+_qtye[0].id);
		if(qtye.val()!=undefined && qtye.val()!="")	{
			var v = contractFormat(qtye.val());
			if(v!=null && v!="") {
				if(field == "qty") {
					if(!(/^\d+$/.test(v))) {
//						showContractDtailError(_qtye[0].id);
						showContractDtailError(qtye,v);
						return -1;
					} else {
//						removeContractDtailError(_qtye[0].id);
						return 1;
					}
				} else if(field == "unit") {
					if(v!=null && v!="") {
						if(v[v.length-1]==".") {
							return 0;
						}
						if(/^\d+\.\d+$/.test(v)==false && /^\d+$/.test(v)==false) {
//							showContractDtailError(_qtye[0].id);
							showContractDtailError(qtye,v);
							return -1;
						} else {
//							removeContractDtailError(_qtye[0].id);
							return 1;
						}
					} else {
						return 0;
					}
				}
			} else {
				return 0;
			}
		} else {
			return 0;
		}
	}
	return 0;
}

/**
 * old showContractDtailError func
 * @param id
 
function showContractDtailError(id) {
	$($("#"+id).parent()[0]).css("background","#FF3737");
}
*/

function showContractDtailError(jqueryobj,val) {
	art.dialog({
		id:'contract-error-dialog',
	    title: '5秒后自动消失',
	    icon:'error',
	    time:5,
	    content: '输入值：'+val+' 有误！'
	});
	jqueryobj[0].focus();
}

function removeContractDtailError(id) {
	$($("#"+id).parent()[0]).css("background","inherit");
}


function _addContractRelatedFiles()
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
			data["rd"] = "导入合同";
			_addContractShowFile("cus-contract",data);
			$("#create-contract-flag").val(data["id"]);
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

function _removeContractRelatedFiles() {
	$("#create-contract-flag").val("none");
	$("#cus-contract").empty();
}

function _addContractShowFile(divid,data)
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
	div.html('<table><tr><td><img src="css/framework/img/'+type+'.gif" /></td><td class="bb"><div><div style="float:left;width:240px;">'+data["rd"]+'：'+data["filename"]+'</div><a href="###" style="margin-left:15px;" onclick="javascript:downloadContractFile(\''+data["filename"]+'\',\''+data["id"]+'\');">下载</a><a href="###" onclick="javascript:previewContractFile(\''+data["id"]+'\',\''+data["filename"]+'\');" style="margin-left:15px;">预览打印</a><a href="###" onclick="javascript:_removeContractRelatedFiles();" style="margin-left:15px;">删除</a></div></td></tr></table>');
	if(data["rd"]=="合同") {
		$("#"+divid).prepend(div);
	} else {
		$("#"+divid).append(div);
	}
	$("#"+divid+" .bb").removeClass("bb");
	$("#"+divid).val(data["id"]);
}

/*
function autoSaveContract()
{
	var isDialogClose = true;
	if(_isAddDialogOpen == undefined)
		return;
	if(!_isAddDialogOpen)
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
	
	console.log("autosave");
	console.log(FormUtil.getValue("addContractForm"));
	
}
*/


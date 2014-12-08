
var SysEncodingGrid = null;
var SysEncodingQueryGrid = null;
var SysEncodingConst = [
                        {value:"orderfilestype",text:'合同相关文件类型'},
                        {value:"productcategory",text:'产品分类'},
                        {value:"materialcategory",text:'材料分类'},
                        {value:"clientfrom",text:'客户来源'},
                        {value:"gender",text:'性别'},
                        {value:"truefalse",text:'是否'},
                        {value:"contractpayment_cn",text:'出口方式中文'},
                        {value:"contractpayment_en",text:'出口方式英文'},
                        {value:"financialstatementstatus",text:'财务报表完成状态'},
                        {value:"mainitems",text:'主营项目'},
                        {value:"userrealname",text:'用户真实姓名'},
                        {value:"year",text:'年份'},
                        {value:"productDirectoryType",text:'产品目录分类'}
                        ];
var SysEncodingQueryParam = [ {value:"",text:'全部',selected:"selected"}];

$(function(){

	SysEncodingQueryGrid = new QueryGrid(
			"SysEncodingForm",
			[ 
			  {name:'fieldname',type:'select',text:'编码类型',options: SysEncodingQueryParam.concat(SysEncodingConst) },
			  {name:'codingvalue',type:'text',text:'编码名称'}
			 ],
			 null,
			 "querySysEncodingObject",
			 "addSysEncoding",
			 "removeSysEncoding",
			 "updateSysEncoding",
			 "pages/module/system/SysEncodingDialog.html",
			 "SysEncodingDialogForm"
	);
	
	SysEncodingGrid = $("#SysEncodingGrid").jqGrid(
		{	
			url:'service/dataInteraction?serviceid=querySysEncodingList',
			mtype: "POST",
			datatype: "local", 
			colNames:['ID','编码类型','编码值','编码名称','描述','顺序'], 
			colModel:[ 
					   {name:'id',index:'id', width:100,hidden:true,align:"center"},	
			           {name:'fieldName',index:'fieldName', width:120,align:"center"},
			           {name:'fieldValue',index:'fieldValue', width:120,align:"center"},
			           {name:'codingValue',index:'codingValue', width:120,align:"center"},
			           {name:'reserved',index:'reserved', width:120,align:"center"},
			           {name:'ordernum',index:'ordernum', width:120,align:"center"}
			         ],
			 width:mainComponent.mainPanelContentWidth,
			 height:mainComponent.mainPanelContentHeight,
			 rowNum:50, 
			 rowList:[50,100,200,500,1000],	
			 pager: '#SysEncodingPager', 
			 //sortname: 'id',
			 viewrecords: true,
			 //sortorder: "desc", 
			 caption:"系统编码配置",
			 rownumbers: true
		}
	);
	
	jQuery("#SysEncodingGrid").jqGrid('navGrid','#SysEncodingPager',{edit:false,add:false,del:false});
	
	SysEncodingQueryGrid.setGrid(SysEncodingGrid);
	
	SysEncodingQueryGrid.beforeSave = function(data)
	{
//		data["codingValue"] = data["fieldValue"];
		if(!FormUtil.check(document.getElementById("SysEncodingDialogForm"),true))
		{
			return false;
		}
		return true;
	};
	
	SysEncodingQueryGrid.afterPopup = function(data) {
		
		var options = null;
		for(var i in SysEncodingConst)
		{
			options = $('<option>'); 
			options.html(SysEncodingConst[i].text);
			options.val(SysEncodingConst[i].value);
			$("#SysEncodingDialogFN").append(options);
		}
		
		$("#SysEncodingDialogFN").change(function() {
			$("#SysEncodingDialogRES").val($("#SysEncodingDialogFN  option:selected").text());
		});
		return true;
	};
	
});

function refreshSysEncodingCode() {
	EncodingUtil.refreshSysEncodingCodeWithWarn();
//	var r = new RemoteService("freshUserData");
//	r.send(null,refreshSysEncodingCodeSuc,null,'正在刷新编码表数据...');
}

//function refreshSysEncodingCodeSuc(resp) {
//	if(resp == null || resp.type=="error") {
//		art.dialog("刷新编码表信息失败");
//	} else {
//		var rs = new RemoteService("getEncoding");
//		rs.send(null,suc,err,"正在获得编码表");
//	}
//	
//	function suc(resp2) {
//		mainComponent.encodeingArray = resp2.data;
//		art.dialog({
//			content:"刷新成功",
//			time:mainComponent.closeDialogTime
//		});
//	}
//	function err(resp2) {
//		alert("加载编码表失败!");
//	}
//	
//}

//SysEncodingQueryGrid.createDoubleClick();

//var d = FormUtil.getValue("SysEncodingForm");
//alert(d["type"]);
//SysEncodingQueryGrid.query();

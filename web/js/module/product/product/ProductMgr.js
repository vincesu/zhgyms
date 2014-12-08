
var ProductGrid = null;
var ProductQueryGrid = null;

$(function(){
	ProductQueryGrid = new QueryGrid(
			"ProductForm",
			[ {name:'name',type:'text',text:'名称'},
			  {name:'number',type:'text',text:'编号'},
			  {name:'category',type:'text',text:'分类',code:'productcategory',classname:'combox',blankLine:'全部' },
//			  {name:'theorder',type:'text',text:'排序',code:'productorder',classname:'combox',blankLine:'a.have_sales_program desc,a.id desc ' },
			  {name:'theorder',type:'select',text:'排序',options: [
			                                         {value:"a.have_sales_program desc,a.id desc ",text:'价格在先'},
			                                         {value:"a.id desc ",text:'添加时间在先',selected:"selected"}
			                                         ] }
			 ],
			 null,
			 "getProduct",
			 "addProduct",
			 "removeProduct",
			 "updateProduct",
			 "pages/module/product/product/ProductDialog.html",
			 "ProductDialogForm"
	);
	
	ProductGrid = $("#ProductGrid").jqGrid(
		{	
			url:'service/dataInteraction?serviceid=getProductList',
			mtype: "POST",
			datatype: "local", 
			colNames:['fid','下载路径','图片','ID','名称','编号','尺寸','参考价格','起订量','描述','分类','是否有价格信息','业务员'], 
			colModel:[ 
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
			           {name:'haveSalesProgram',index:'haveSalesProgram', width:60},
			           {name:'username',index:'username', width:60}
			         ],
			 width:mainComponent.mainPanelContentWidth,
			 height:mainComponent.mainPanelContentHeight,
			 rowNum:10, 
			 rowList:[10,50,100],
			 postData:{theorder:'a.id desc  '},
			 pager: '#ProductPager', 
			 //sortname: 'id',
			 viewrecords: true,
			 //sortorder: "desc", 
			 caption:"产品列表",
			 gridComplete:function() {
				var ids = $("#ProductGrid").jqGrid('getDataIDs');
				var img = null;
				var cl = null;
				var rowdata = null;
				for ( var i = 0; i < ids.length; i++) {
					cl = ids[i];
					rowdata = $("#ProductGrid").jqGrid("getRowData", cl);
					img = "";
					if(rowdata["path"] != null && rowdata["path"]!=undefined &&
							rowdata["path"] != "") {
						img = '<img style="width:120px" src="'+rowdata.path+'" />';
						$("#ProductGrid").jqGrid('setRowData', cl, {
							"path" : img
						});
					}
				}
//				ProductQueryGrid.createCodeField(["haveSalesProgram"],["truefalse"]);
			 },
			 rownumbers: true
	
		}
	);
	
	jQuery("#ProductGrid").jqGrid('navGrid','#ProductPager',{edit:false,add:false,del:false});
	
	ProductQueryGrid.setGrid(ProductGrid,["haveSalesProgram"],["truefalse"]);
	
	//ProductQueryGrid.createDoubleClick();
	
	ProductQueryGrid.setDoubleClickFunc(function(){
		viewProductPic();
	});
	
	ProductQueryGrid.beforeView = function(data) {
		$("#upload-form").hide();
	};
	
	ProductQueryGrid.afterPopup = function(thedata) {
	
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
		$("#upload").change(function(){
			$("#productdialognumber").val(getFileNameWithoutExp(getFileNameFormPath($("#upload").val())));
		});
	};
	
	ProductQueryGrid.afterSave = function(resp) {
		if(resp != null && resp.type!="error") 
		{
			$("#imgpath").attr("src","images/no-products.gif");
			$("#fileid").val("");
			$("#productdialognumber").val("");
		}
	};
	
	ProductQueryGrid.beforeSave = function(data) {
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
	};

});

function exportProduct()
{
	new DownloadComponent().createDownloadComponent("exportProductForm","exportProduct","product.xls");
}

function addSalesProgram()
{
	var selectedId = ProductGrid.jqGrid("getGridParam", "selrow");
	if(selectedId == null)
	{
		art.dialog("请选择一条记录");
		return;
	}
	var d = ProductGrid.jqGrid("getRowData", selectedId);
	var dialog = art.dialog({
		title: "添加价格信息",
		lock:true,
		ok:function() {
			var r = new RemoteService("addSalesProgram");
			var p = new Array();
			p[0] = FormUtil.getValue("SalesProgramForm");
			r.send(p, s, null, "正在添加数据...");
			function s(resp)
			{
				if(resp == null || resp.type=="error")
				{
					art.dialog("添加失败");
					
				} else {
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
	    url: "pages/module/product/product/salesProgramDialog.html",
	    success: function (data) {
	        dialog.content(data);
	        $("#SalesProgramDialogProductid").val(d["id"]);
	    },
	    cache: false
	});
	
}

function viewSalesProgram()
{
	var selectedId = ProductGrid.jqGrid("getGridParam", "selrow");
	if(selectedId == null)
	{
		art.dialog("请选择一条记录");
		return;
	}
	var d = ProductGrid.jqGrid("getRowData", selectedId);
	var cd = new ChooseDialog(
			"&productid="+d["id"],
			null,
			"viewSalesProgram",
			['id','业务员','数量','价格','客户','日期'], 
			[ 
			   {name:'id',index:'id', width:120,hidden:true,align:"center"},
			   {name:'salesman',index:'salesman', width:120,align:"center"},	
	           {name:'quantity',index:'quantity', width:120,align:"center"},
	           {name:'price',index:'price', width:120,align:"center"},
	           {name:'reserved',index:'reserved', width:120,align:"center"},
	           {name:'date',index:'date', width:120,align:"center"}
	         ],
			null,false,false,null,null
	);
	cd.open();
}

function importProduct()
{
	new UploadComponent().pupupUploadDialog("importProduct");
}

function removeSalesProgram()
{
	var selectedId = ProductGrid.jqGrid("getGridParam", "selrow");
	if(selectedId == null)
	{
		art.dialog("请选择一条记录");
		return;
	}
	var d = ProductGrid.jqGrid("getRowData", selectedId);
	var cd = new ChooseDialog(
			"&productid="+d["id"],
			null,
			"viewSalesProgram",
			['id','业务员','数量','价格','客户','日期'], 
			[ 
			   {name:'id',index:'id', width:120,hidden:true,align:"center"},
			   {name:'salesman',index:'salesman', width:120,align:"center"},	
	           {name:'quantity',index:'quantity', width:120,align:"center"},
	           {name:'price',index:'price', width:120,align:"center"},
	           {name:'reserved',index:'reserved', width:120,align:"center"},
	           {name:'date',index:'date', width:120,align:"center"}
	         ],
			function(data) {
				var rs = new RemoteService("removeSalesProgram");
				rs.send(data,suc,null,"正在删除数据...");
				function suc(resp) {
					if(resp == null || resp.type=="error")
					{
						art.dialog("删除失败");
						
					} else {
						art.dialog({
							content:"删除成功",
							time:mainComponent.closeDialogTime
						});
					}
				}
			},true,false,null,null
	);
	cd.open();
}

function downloadPic()
{
	var selectedId = ProductGrid.jqGrid("getGridParam", "selrow");
	if(selectedId == null)
	{
		art.dialog("请选择一条记录");
		return;
	}
	var d = ProductGrid.jqGrid("getRowData", selectedId);
	if(d["downpath"]=="")
	{
		art.dialog("无图片可下载");
	}
	var exname = getExpandingName(d["downpath"]);
	
	new DownloadComponent().createDownloadComponent("exportProductForm","downloadFiles",d["number"]+"."+exname,d["fid"]);
	
}

function chooseExistProduct()
{
	var cd = new ChooseDialog(
			null,
			[ {name:'name',type:'text',text:'名称'},
			  {name:'number',type:'text',text:'编号'},
			  {name:'category',type:'text',text:'分类',code:'productcategory',classname:'combox',blankLine:'全部' },
//			  {name:'theorder',type:'text',text:'排序',code:'productorder',classname:'combox',blankLine:'a.have_sales_program desc,a.id desc ' },
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
	           {name:'haveSalesProgram',index:'haveSalesProgram', width:60,hidden:true}
	         ],
			function(data) {
				data[0]["id"]=null;
				data[0]["fileid"]=null;
				data[0]["path"]=null;
				data[0]["referencePrice"]=data[0]["price"];
				data[0]["description"]=data[0]["des"];
				data[0]["category2"]="-1";
				FormUtil.setValue("ProductDialogForm",data[0]);
			},false,false,null,null
	);
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
	}
	cd.open();
}

function viewProductPic()
{
	var selectedId = ProductGrid.jqGrid("getGridParam", "selrow");
	if(selectedId == null)
	{
		art.dialog("请选择一条记录");
		return;
	}
	var d = ProductGrid.jqGrid("getRowData", selectedId);
	art.dialog({
	    content: d.path.replace("120px","500px"),
	    title: '查看大图'
	});
}


/**
 * author vince.su
 * 2012-09-25
 */

function QueryGrid(
		queryfieldid,
		queryfield,
		grid,
		view_sid,
		add_sid,
		remove_sid,
		modify_sid,
		popupdialog,
		dialogform
		) {
	this.queryfieldid = queryfieldid;
	this.queryfield = queryfield;
	this.grid = grid;
	this.view_sid = view_sid;
	this.modify_sid = modify_sid;
	this.add_sid = add_sid;
	this.remove_sid = remove_sid;
	this.popupdialog = popupdialog;
	this.dialogform = dialogform;
	this.afterSave = null;
	
	this.createQueryField();
	
//	if(grid!=null)
//		this.createDoubleClick();
	
}

QueryGrid.prototype.setGrid = function(g,f1,f2){
	this.grid = g;
	this.createloadComplete();
	this.grid.jqGrid('setGridParam',{
		datatype:'json'
	});
	var deity = this;
	
	if(f1!=undefined && f1!=null && f2!=undefined && f2!=null) {
		var gc = this.grid.jqGrid('getGridParam','gridComplete');
		if(gc!=null && gc!=undefined) {
			this.grid.jqGrid('setGridParam',{
				gridComplete:function() {
					 gc();
					 deity.createCodeField(f1,f2);
				 }
			});
		} else {
			this.grid.jqGrid('setGridParam',{
				gridComplete:function() {
					 deity.createCodeField(f1,f2);
				 }
			});
		}
		
	}
	this.query();
};

QueryGrid.prototype.createloadComplete = function(){
	this.grid.jqGrid(   
            'setGridParam',  
            {
            	loadComplete: function(xhr) { 
            		
            		if(xhr!=undefined && xhr!=null) {
            			if(xhr.message!=undefined && xhr.message!=null && xhr.message.indexOf('重新登陆')>=0) {
	            			timeoutLoginDialog = art.dialog({id: "logindialog",title: "登陆超时，请重新登陆",lock:true});
	    					$.ajax({
	    					    url: "pages/framework/login/login.html",
	    					    success: function (data) {
	    					    	timeoutLoginDialog.content(data);
	    					        $("#logintimeoutdialogun").val(sys_username["username"]);
	    					    },
	    					    cache: false
	    					});
            			}
            		}
            	}
            }  
        );  
};

QueryGrid.prototype.createloadError = function(){
	if(this.grid!=null) {
//		alert("load complete");
	}
};

QueryGrid.prototype.createDoubleClick = function()
{
	if(this.grid!=null)
	{
		var deity = this;
		this.grid.jqGrid(   
            'setGridParam',  
            {
            	ondblClickRow: function(id) { 
            		deity.popupToView();
            	}
            }  
        );  
	}
};

QueryGrid.prototype.setDoubleClickFunc = function(func)
{
	if(this.grid!=null)
	{
		this.grid.jqGrid(   
	            'setGridParam',  
	            {
	            	ondblClickRow: function(id) { 
	            		func(id);
	            	}
	            }  
	        );  
	}
};

QueryGrid.prototype.query = function()
{
	debugger;
	if(this.grid==null)	return
	if(this.queryfieldid == null)
	{
		this.grid.trigger('reloadGrid');
	}
	else{
		debugger;
		this.grid.jqGrid(   
	            'setGridParam',  
	            { postData: FormUtil.getValue(this.queryfieldid) }  
	        );  
		this.grid.trigger('reloadGrid');
	}
};

QueryGrid.prototype.queryWithParam = function(param) {
	if(this.grid==null)	return
	if(!param) 
	{
		this.grid.trigger('reloadGrid');
	} else 
	{
		this.grid.jqGrid(   
	            'setGridParam',  
	            { postData: param }  
	        );  
		this.grid.trigger('reloadGrid');
	}
};

QueryGrid.prototype.popupToView = function()
{
	if(this.popupdialog == null || this.dialogform == null || this.grid == null)	return;
	
	var deity = this;
	var selectedId = this.grid.jqGrid("getGridParam", "selrow");
	if(selectedId == null)
	{
		art.dialog("请选择一条记录");
		return;
	}
	var param = new Array();
	param[0] = this.grid.jqGrid("getRowData", selectedId);
	var rs = new RemoteService(this.view_sid);
	rs.send(param,suc,null,"正在查询数据...");
	function suc(resp)
	{
		var dialog = art.dialog({id: deity.popupdialog,title: "查看",lock:true});

		$.ajax({
		    url: deity.popupdialog,
		    success: function (data) {
		        dialog.content(data);
		        mainComponent.createCodeComboxBox();
		        if(deity.afterPopup != null)
		        	deity.afterPopup(resp.data[0]);
		        if(deity.beforeView != null)
		        	deity.beforeView(resp.data[0]);
		        FormUtil.setValue(deity.dialogform,resp.data[0]);
		        if(deity.afterLoadData != null)
		        	deity.afterLoadData(resp.data[0]);
		        new components().createCodeComboxBox();
		    },
		    cache: false
		});
	}
	
};

QueryGrid.prototype.popupToAdd = function()
{
	if(this.popupdialog == null || this.dialogform == null || this.grid == null)	return;
	
	var deity = this;
	
	var dialog = art.dialog({
		id: deity.popupdialog,
		lock:true,
		title: "添加",
		ok: function () {
			
			var data = FormUtil.getValue(deity.dialogform);
			if(deity.beforeSave != null)
			{
				if(deity.beforeSave(data)) {}
				else return false;
			}
			var r = new RemoteService(deity.add_sid);
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
					deity.query();
					art.dialog({
						content:"添加成功",
						time:mainComponent.closeDialogTime
					});
				}
				if(deity.afterSave!=null)
				{
					deity.afterSave(resp);
				}
			}
			return true;
		},
		cancelVal: '关闭',
		cancel: true
	});

	$.ajax({
	    url: deity.popupdialog,
	    success: function (data) {
	        dialog.content(data);
	        mainComponent.createCodeComboxBox();
	        if(deity.afterPopup != null)
	        	deity.afterPopup(null);
	        new components().createCodeComboxBox();
	    },
	    cache: false
	});
};


QueryGrid.prototype.popupToAddWithoutClose = function()
{
	if(this.popupdialog == null || this.dialogform == null || this.grid == null)	return;
	
	var deity = this;
	
	var dialog = art.dialog({
		id: deity.popupdialog,
		lock:true,
		title: "添加",
		ok: function () {
			var data = FormUtil.getValue(deity.dialogform);
			if(deity.beforeSave != null)
			{
				if(deity.beforeSave(data)) {}
				else return false;
			}
			var r = new RemoteService(deity.add_sid);
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
					deity.query();
					art.dialog({
						content:"添加成功",
						time:mainComponent.closeDialogTime
					});
				}
				if(deity.afterSave != null)
				{
					deity.afterSave(resp);
				}
			}
			return false;
		},
		cancelVal: '关闭',
		cancel: true
	});

	$.ajax({
	    url: deity.popupdialog,
	    success: function (data) {
	        dialog.content(data);
	        mainComponent.createCodeComboxBox();
	        if(deity.afterPopup != null)
	        	deity.afterPopup(null);
	    },
	    cache: false
	});
};


QueryGrid.prototype.remove = function(message) 
{
	var m = message;
	if(this.grid == null)	return;
	
	var selectedId = this.grid.jqGrid("getGridParam", "selrow");
	if(selectedId == null)
	{
		art.dialog("请选择一条记录");
		return;
	}
	var deity = this;
	if(m==null||m==undefined)
		m="确定删除？";
	if(confirm(m))
	{
		var param = new Array();
		param[0] = this.grid.jqGrid("getRowData", selectedId);
		var rs = new RemoteService(this.remove_sid);
		rs.send(param,suc,null,"正在删除数据...");
	}
	function suc(resp) {
		if(resp == null || resp.type=="error")
		{
			if(resp.message!=null)
				art.dialog(resp.message);
			else
				art.dialog("删除失败");
			
		} else {
			deity.query();
			art.dialog({
				content:"删除成功",
				time:mainComponent.closeDialogTime
			});
		}
	}
};

QueryGrid.prototype.popupToModify = function()
{
	if(this.popupdialog == null || this.dialogform == null || this.grid == null)	return;
	
	var deity = this;
	var selectedId = this.grid.jqGrid("getGridParam", "selrow");
	if(selectedId == null)
	{
		art.dialog("请选择一条记录");
		return;
	}
	var param = new Array();
	param[0] = this.grid.jqGrid("getRowData", selectedId);
	var rs = new RemoteService(this.view_sid);
	rs.send(param,suc,null,"正在查询数据...");
	function suc(resp)
	{
		var dialog = art.dialog({
			id: deity.popupdialog,
			title: "修改",
			lock:true,
			ok: function () {
//				
				var data = FormUtil.getValue(deity.dialogform);
				if(deity.beforeSave != null)
				{
					if(deity.beforeSave(data)) {}
					else return false;
				}
				var r = new RemoteService(deity.modify_sid);
				var p = new Array();
				p[0] = data;
				r.send(p, s, null, "正在修改数据...");
				function s(resp)
				{
					if(resp == null || resp.type=="error")
					{
						if(resp.message!=null)
							art.dialog(resp.message);
						else
							art.dialog("修改失败");
						
					} else {
						deity.query();
						art.dialog({
							content:"修改成功",
							time:mainComponent.closeDialogTime
						});
					}
					if(deity.afterSave!=null)
						deity.afterSave(resp);
				}
				return true;
			},
			cancelVal: '关闭',
			cancel: true
		});

		$.ajax({
		    url: deity.popupdialog,
		    success: function (data) {
		    	
		        dialog.content(data);
		        mainComponent.createCodeComboxBox();
		        if(deity.afterPopup != null)
		        	deity.afterPopup(resp.data[0]);
		        FormUtil.setValue(deity.dialogform,resp.data[0]);
		        if(deity.afterLoadData != null)
		        	deity.afterLoadData(resp.data[0]);
		        new components().createCodeComboxBox();
		    },
		    cache: false
		});
	}
	
};

QueryGrid.prototype.createQueryField = function() {
	
	var show = false;
	
	if(this.queryfield == null || this.queryfield.length == 0) {
		if(document.getElementById(this.queryfieldid))
			document.getElementById(this.queryfieldid).style.display="none";
		return;
	}
	
	var len = this.queryfield.length;
	var fieldset = document.createElement("fieldset");
//	fieldset.setAttribute("class","ylzgroupbox");
	var legend = document.createElement("legend");
//	legend.setAttribute("class","ylzgroupboxtitle");
	legend.innerHTML = "查询条件";
	fieldset.appendChild(legend);
	
	var table = document.createElement("table");
	table.setAttribute("width","100%");
	
	var tr = null;
	var td = null;
	
	for (var i=0;i<len;i++) {
		if(i%3 == 0) {
			tr = table.insertRow(table.rows.length);
		}
		
		td = tr.insertCell(tr.cells.length);
		td.setAttribute("align", "right");
		td.setAttribute('width','11%');
		td.innerHTML=this.queryfield[i].text;
		
		td = tr.insertCell(tr.cells.length);
		td.setAttribute('width','22%');
		if(this.queryfield[i].type == "select")
		{
			input = document.createElement("select");
			if(this.queryfield[i].name != null)  {
				input.setAttribute("id",this.queryfieldid+"_"+this.queryfield[i].name);
				input.setAttribute("name",this.queryfield[i].name);
			}
			var option = null;
			for(var j = 0;j<this.queryfield[i].options.length;j++)
			{
				option = document.createElement("option");
				option.setAttribute("id",this.queryfieldid+"_"+this.queryfield[i].name+"_"+
						this.queryfield[i].options[j].value);
				option.setAttribute("value",this.queryfield[i].options[j].value);
				option.innerHTML=this.queryfield[i].options[j].text;
				if(this.queryfield[i].options[j].selected=='selected')
				{
					option.setAttribute("selected","selected");
				}
				input.appendChild(option);
			}
			show = true;
		} 
		else 
		{
			input = document.createElement("input");
			if(this.queryfield[i].type != null) { 
				input.setAttribute("type",this.queryfield[i].type);
				if(this.queryfield[i].type!="hidden")
					show=true;
			}
			if(this.queryfield[i].value != null) { 
				input.setAttribute("value",this.queryfield[i].value);
			}
			if(this.queryfield[i].name != null)  {
				input.setAttribute("id",this.queryfieldid+"_"+this.queryfield[i].name);
				input.setAttribute("name",this.queryfield[i].name);
			}
			if(this.queryfield[i].classname != null ) {
				if(this.queryfield[i].classname=="date")
				{
					input.setAttribute("className","Wdate");
					input.setAttribute("class","Wdate");
					input.setAttribute("onfocus", "WdatePicker({isShowWeek:true})");
				}
				else
				{
					input.setAttribute("className",this.queryfield[i].classname);
					input.setAttribute("class",this.queryfield[i].classname);
				}
			}
			if(this.queryfield[i].code != null) {
				input.setAttribute("code",this.queryfield[i].code);
			}
			if(this.queryfield[i].blankLine != null) {
				input.setAttribute("blankLine",this.queryfield[i].blankLine);
			}
		}
		input.setAttribute("style","width:100%;");
		
		td.appendChild(input);
		
		if(i%3 == 2) {
			tr = null;
		}
	}
	
	
//	innerhtml = "<a href='#' id='"+this.queryfieldid+"_query' class='ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only'><span class='ui-button-text'><span "+
//		"class='ui-icon ui-icon-search' style='float: left'></span>查询</span></a>"+
//		"<a href='#' id='"+this.queryfieldid+"_reset' class='ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only'><span class='ui-button-text'><span "+
//		"class='ui-icon ui-icon-calculator' style='float: left'></span>重置</span></a>";
	innerhtml = "<a class='button3' href='###' id='"+this.queryfieldid+"_query"+
		"'><span class='button-text' id='"+this.queryfieldid+"_querybuttontext'><span class='ui-icon ui-icon-search' style='float: left'></span>查询</span></a><a class='button3' href='###' id='"+this.queryfieldid+"_reset"+"'><span class='button-text' id='"+this.queryfieldid+"_resetbuttontext'><span class='ui-icon ui-icon-calculator' style='float: left'></span>重置</span></a>";
	
	if(tr == null) {
		tr = table.insertRow(table.rows.length);
		td = tr.insertCell(tr.cells.length);
		td.setAttribute("colSpan", "6");
		
	} else {
		var num = (3 - (len%3))*2;
		td = tr.insertCell(tr.cells.length);
		td.setAttribute("colSpan", num.toString());
	}
	
	td.setAttribute("align", "right");
	td.innerHTML = innerhtml;
	
	fieldset.appendChild(table);
	document.getElementById(this.queryfieldid).appendChild(fieldset);
	
	var a = document.getElementById(this.queryfieldid+"_query");
	var deity = this;
	a.onclick = function() {
		deity.query();
//		var param = CRUD.getValues('#'+deity.queryfieldid);
//		deity.pageGrid.ylzgrid('query',param); 
//		if(deity.queryFunc!=null)
//			deity.queryFunc();
	};
	
	a = document.getElementById(this.queryfieldid+"_reset");
	
	a.onclick=function() {
		document.getElementById(deity.queryfieldid).reset();
		if(navigator.userAgent.toLowerCase().indexOf("msie")>0)
		{
			for (var i=0;i<len;i++) 
			{
				if(deity.queryfield[i].type == "select")
				{
					for(var j = 0;j<deity.queryfield[i].options.length;j++)
					{
						if(deity.queryfield[i].options[j].selected=='selected')
						{
							document.getElementById(deity.queryfieldid+"_"+deity.queryfield[i].name+"_"+
									deity.queryfield[i].options[j].value).selected=true;
						}
					}
				}
			}
		}
		if(deity.afterReset!=null)
		{
			deity.afterReset();
		}
	};
	if(!show)
		document.getElementById(this.queryfieldid).style.display="none";
};

QueryGrid.prototype.createCodeField = function(fieldname,encoding) {
	var ids = this.grid.jqGrid('getDataIDs');
	var rowdata = null;
	var code = null;
	var codeField = null;
	var f = fieldname;
	for(var h=0;h<f.length;h++)
	{
		code = mainComponent.getEncodingList(encoding[h]);
		for ( var i = 0; i < ids.length; i++) {
			cl = ids[i];
			rowdata = this.grid.jqGrid("getRowData", cl);
			for(var j=0;j<code.length;j++)
			{
				if(code[j].fieldValue==rowdata[f[h]])
				{
					codeField = code[j].codingValue;
				}
			}
			var newRowData = {};
			newRowData[f[h]]=codeField;
			this.grid.jqGrid('setRowData', cl, newRowData);
		}
	}
};

QueryGrid.prototype.up = function(d) {
	
	var data = FormUtil.formatData(d);
	var selectedId = this.grid.jqGrid("getGridParam", "selrow");
	if(selectedId == null)
	{
		art.dialog("请选择一条记录");
		return;
	}
	var rowdata = this.grid.jqGrid("getRowData", selectedId);
	var ids = this.grid.jqGrid('getDataIDs');
	var i=0;
	for(i=0;i<ids.length;i++)
	{
		if(ids[i]==selectedId)
			break;
	}
	var pos = null;
	if(i==0)
		return;
	else
		pos=ids[--i];
	this.grid.jqGrid('delRowData',selectedId);
	if(data)
		this.grid.jqGrid('addRowData',selectedId,data,'before',pos);
	else
		this.grid.jqGrid('addRowData',selectedId,rowdata,'before',pos);
	this.grid.jqGrid('setSelection',selectedId);
	
};


QueryGrid.prototype.down = function(d) {
	
	var data = FormUtil.formatData(d);
	var selectedId = this.grid.jqGrid("getGridParam", "selrow");
	if(selectedId == null)
	{
		art.dialog("请选择一条记录");
		return;
	}
	var rowdata = this.grid.jqGrid("getRowData", selectedId);
	var ids = this.grid.jqGrid('getDataIDs');
	var i=0;
	for(i=0;i<ids.length;i++)
	{
		if(ids[i]==selectedId)
			break;
	}
	var pos = null;
	if(i==(ids.length-1))
		return;
	else
		pos=ids[++i];
	this.grid.jqGrid('delRowData',selectedId);
	if(data)
		this.grid.jqGrid('addRowData',selectedId,data,'after',pos);
	else
		this.grid.jqGrid('addRowData',selectedId,rowdata,'after',pos);
	this.grid.jqGrid('setSelection',selectedId);
};

QueryGrid.prototype.moveSelectedRowTo = function(targetIndex,data) {
	var selectedId = this.grid.jqGrid("getGridParam", "selrow");
	if(selectedId == null)
	{
		art.dialog("请选择一条记录");
		return;
	}
	var ids = this.grid.jqGrid('getDataIDs');
	var i=0;
	
	for(i=0;i<ids.length;i++)
	{
		if(ids[i]==selectedId)
			break;
	}
	this.moveRowTo(i,targetIndex,data);
	
};

QueryGrid.prototype.moveRowTo = function(ind,target,data) {
	if(ind==target || ind==(target-1))	return;
	var ids = this.grid.jqGrid('getDataIDs');
	
	//error --- out of index range
	if(ind<0 || ind>=ids.length)
		return;
	
	//error --- out of index range
	if(target<0 || ind>target.length)
		return;
	
	var rowdata = this.grid.jqGrid("getRowData", ids[ind]);
	
	if(target==0)
	{
		this.grid.jqGrid('delRowData',ids[ind]);
		if(data)
			this.grid.jqGrid('addRowData',ids[ind],data,'first');
		else
			this.grid.jqGrid('addRowData',ids[ind],rowdata,'first');
	}
	else
	{
		this.grid.jqGrid('delRowData',ids[ind]);
		if(data)
			this.grid.jqGrid('addRowData',ids[ind],data,'after',ids[--target]);
		else
			this.grid.jqGrid('addRowData',ids[ind],rowdata,'after',ids[--target]);
	}
	this.grid.jqGrid('setSelection',ids[ind]);
};

/*
function QueryGrid(
		queryfieldid,
		queryfield,
		pagegridid,
		pagerid,
		serviceid,
		colNames,
		colModel,
		popupeditor,
		popupdetail,
		completeFunc) {
	
	this.queryfieldid = queryfieldid;
	this.queryfield = queryfield;
	this.pagegridid = pagegridid;
	this.pagerid = pagerid;
	this.serviceid = serviceid;
	this.colnames = colNames;
	this.colmodel = colModel;
	this.popupeditor = popupeditor;
	this.popupdetail = popupdetail;
	this.pageGrid = null;
	this.popupDialog = null;
	this.completeFunc = completeFunc;
	
	this.createQueryField();
	
	this.createPagegrid(this);
	
	if(popupeditor != null)
	{
		this.createPopupDialog();
	}
	
}

QueryGrid.prototype.createQueryField = function() {
	
	if(this.queryfield == null || this.queryfield.length == 0) {
		document.getElementById(this.queryfieldid).style.display="none";
		return;
	}
	
	var len = this.queryfield.length;
	var fieldset = document.createElement("fieldset");
	fieldset.setAttribute("class","ylzgroupbox");
	var legend = document.createElement("legend");
	legend.setAttribute("class","ylzgroupboxtitle");
	legend.innerHTML = "查询条件";
	fieldset.appendChild(legend);
	
	var table = document.createElement("table");
	table.setAttribute("width","100%");
	
	var tr = null;
	var td = null;
	
	for (var i=0;i<len;i++) {
		if(i%3 == 0) {
			tr = table.insertRow(table.rows.length);
		}
		
		td = tr.insertCell(tr.cells.length);
		td.setAttribute("align", "right");
		td.setAttribute('width','11%');
		td.innerHTML=this.queryfield[i].text;
		
		td = tr.insertCell(tr.cells.length);
		td.setAttribute('width','22%');
		if(this.queryfield[i].type == "select")
		{
			input = document.createElement("select");
			if(this.queryfield[i].name != null)  {
				input.setAttribute("id",this.queryfieldid+"_"+this.queryfield[i].name);
				input.setAttribute("name",this.queryfield[i].name);
			}
			var option = null;
			for(var j = 0;j<this.queryfield[i].options.length;j++)
			{
				option = document.createElement("option");
				option.setAttribute("id",this.queryfieldid+"_"+this.queryfield[i].name+"_"+
						this.queryfield[i].options[j].value);
				option.setAttribute("value",this.queryfield[i].options[j].value);
				option.innerHTML=this.queryfield[i].options[j].text;
				if(this.queryfield[i].options[j].selected=='selected')
				{
					option.setAttribute("selected","selected");
				}
				input.appendChild(option);
			}
		} else 
		{
			input = document.createElement("input");
			if(this.queryfield[i].type != null) { 
				input.setAttribute("type",this.queryfield[i].type);
			}
			if(this.queryfield[i].name != null)  {
				input.setAttribute("id",this.queryfieldid+"_"+this.queryfield[i].name);
				input.setAttribute("name",this.queryfield[i].name);
			}
			if(this.queryfield[i].classname != null) {
				input.setAttribute("className",this.queryfield[i].classname);
				input.setAttribute("class",this.queryfield[i].classname);
			}
		}
		input.setAttribute("style","width:100%;");
		
		td.appendChild(input);
		
		if(i%3 == 2) {
			tr = null;
		}
	}
	
	
	innerhtml = "<a href='#' id='"+this.queryfieldid+"_query' class='ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only'><span class='ui-button-text'><span "+
		"class='ui-icon ui-icon-search' style='float: left'></span>查询</span></a>"+
		"<a href='#' id='"+this.queryfieldid+"_reset' class='ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only'><span class='ui-button-text'><span "+
		"class='ui-icon ui-icon-calculator' style='float: left'></span>重置</span></a>";
	
	
	if(tr == null) {
		tr = table.insertRow(table.rows.length);
		td = tr.insertCell(tr.cells.length);
		td.setAttribute("colSpan", "6");
		
	} else {
		var num = (3 - (len%3))*2;
		td = tr.insertCell(tr.cells.length);
		td.setAttribute("colSpan", num.toString());
	}
	
	td.setAttribute("align", "right");
	td.innerHTML = innerhtml;
	
	fieldset.appendChild(table);
	document.getElementById(this.queryfieldid).appendChild(fieldset);
	
	var a = document.getElementById(this.queryfieldid+"_query");
	var deity = this;
	a.onclick = function() {
		var param = CRUD.getValues('#'+deity.queryfieldid);
		deity.pageGrid.ylzgrid('query',param); 
		if(deity.queryFunc!=null)
			deity.queryFunc();
	};
	
	a = document.getElementById(this.queryfieldid+"_reset");
	
	a.onclick=function() {
		document.getElementById(deity.queryfieldid).reset();
		if(navigator.userAgent.toLowerCase().indexOf("msie")>0)
		{
			for (var i=0;i<len;i++) 
			{
				if(deity.queryfield[i].type == "select")
				{
					for(var j = 0;j<deity.queryfield[i].options.length;j++)
					{
						if(deity.queryfield[i].options[j].selected=='selected')
						{
							document.getElementById(deity.queryfieldid+"_"+deity.queryfield[i].name+"_"+
									deity.queryfield[i].options[j].value).selected=true;
						}
					}
				}
			}
		}
		
	};
	
//	a.onclick = this.query;
	
};

QueryGrid.prototype.getQueryParam = function() {
	var param = CRUD.getValues('#'+this.queryfieldid);
	return param;
}

QueryGrid.prototype.createPagegrid = function(deity) {
	
	this.pageGrid=$('#'+this.pagegridid).ylzgrid({
	    queryService : this.serviceid,
	    colNames : this.colnames,
	    colModel : this.colmodel,
	    ondblClickRow: function() {deity.popup();},
	    autowidth : true,
	    shrinkToFit : false,
		height: 280,
		rowNum : 20,
		rowTotal : 5,
		rowList : [ 10, 20, 40, 50, 100 ],
		rownumbers : true,
		rownumWidth : 20,
		pager : '#'+this.pagerid,
		viewrecords : true, // 是否显示行数
		sortorder : "asc",
		multiselect : true,
		multipleSearch : true,
		subGrid: false,
		gridComplete : deity.completeFunc
		
	});
	
};

QueryGrid.prototype.createPopupDialog = function() {

	this.popupDialog = $("#"+this.popupeditor).ylzdialog({
		autoOpen : false,
		modal : true,
		width: 720,
		buttons : {
			'关闭' : function() {
				$(this).ylzdialog("close");
			}
		}
	});
};

QueryGrid.prototype.popup = function() {
	
	if(this.pagegridid == null) return ;
	if(this.popupDialog == null)	return;
	var row = this.pageGrid.ylzgrid('getGridParam','selrow');
	if (row == null) {
		alert("必须选择一条记录！");
		return ;
	}
	var value = this.pageGrid.getRowItem(row);
	if(value != null) {
		CRUD.setValues("#"+this.popupdetail, value);
		this.popupDialog.ylzdialog('open');
	}
	
};

QueryGrid.prototype.query = function() {
	
	if(this.pagegridid == null) {
		alert("pagegrid未创建");
		return;
	}

	var param = CRUD.getValues('#'+this.queryfieldid);
	this.pageGrid.ylzgrid('query',param);
	if(this.queryFunc!=null)
		this.queryFunc();
};

QueryGrid.prototype.getData = function() {
	if(this.pagegridid == null) return null;
	var row = this.pageGrid.jqGrid('getGridParam','selarrrow');
	if(row.length == 0)
		return null;
	return row;
};

QueryGrid.prototype.addtoolbutton = function(button) {
	var len = button.length;
	for(var i=0;i<len;i++) {
		this.pageGrid.ylzgrid("toolbarButtonAdd",
			"#t_"+this.pagegridid, {
				caption : button[i].caption,
				position : "last",
				title : button[i].title,
				align : "right",
				buttonicon : 'ui-icon-newwin',
				onClickButton : button[i].func 
		});
	}
};

*/


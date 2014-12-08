/**
 * author vince.su
 * 2012-10-21
 */

function components(id,contentid) 
{
	// 变量名 也是标识id 如：var theId = new components("theId","content");
	this.id = id;
	// 主div id
	this.contentid = contentid;
	// 导航栏id
	this.navigationId = "navigation_"+this.contentid;
	// 主视图div id
	this.mainpanelId = "mainpanel_"+this.contentid;
	// 选项卡div id
	this.tabId = "tab_"+this.contentid;
	// footer div id
	this.footerId = "footer_"+this.contentid;
	//主显示区域边框最大宽度(不含滚动条)
	this.mainPanelWidth = null;
	//主显示区域边框高度
	this.mainPanelHeight = 650;
	//主显示区域可显示内容最大宽度
	this.mainPanelContentHeight = 400;
	//主显示区域可显示内容最大宽度
	this.mainPanelContentWidth = null;
	//弹出框关闭时间
	this.closeDialogTime = 2;
	//page grid 页面行数
	this.rowNum = 50;
	
	this.setttings = {description:'components settings'};
	
	/*
	 * 事件
	 */
	this.events = {
		afterSelectTreeNodeEvent : null,
		loginInitEvent: null
	};
	
	/*
	 * 组件 
	 */
	//编码表
	this.encodeingArray = null;
	//模块树形节点
	this.tree = null;
	//选项卡控件
	this.tabs = null;
	//模块map
	this.moduleMap = {};
}

components.prototype.footerContent = 'power by <a href="http://www.vincesu.com">sumaorong</a>';

components.prototype.trees = new Array();

/*
 * 事件
 */
components.prototype.addAfterSelectTreeNodeEvent = function(func) {
	this.addEvent("afterSelectTreeNodeEvent",func);
};

components.prototype.afterSelectTreeNodeEvent = function() {
	if(this.events["afterSelectTreeNodeEvent"])
		this.events["afterSelectTreeNodeEvent"]();
};

components.prototype.addLoginInitEvent = function(func) {
	this.addEvent("loginInitEvent",func);
};

components.prototype.loginInitEvent = function() {
	if(this.events["loginInitEvent"])
		this.events["loginInitEvent"]();
};

components.prototype.addEvent = function(event,func) {
	
	if(!func)	return;
	
	if(this.events[event])  
	{
		this.events[event] = function() {
			this.events[event]();
			func();
		};
		
	} else  {
		
		this.events[event] = func;
	}
};

/*
 * 创建主框架
 */
components.prototype.createContent = function(data) {
	
	var deity = this;
	
	if(document.getElementById(this.contentid) == null)
	{
		alert("无法找到id为"+this.contentid+"的主控件");
		return;
	}
	document.getElementById(this.contentid).innerHTML="<div id=\""+this.navigationId+"\" class=\"navigation\"></div><div id=\""+this.mainpanelId+"\" class=\"mainpanel\"><div id=\""+this.tabId+"\"></div>	<div id=\""+this.footerId+"\" class=\"footer\">"+this.footerContent+"</div></div>";
	
	this.createTree(data);
	
//	for(var i=0;i<data.length;i++)
//	{
//		if(data[i].address == null || data[i].address == "")
//			this.tree.add(data[i].id,data[i].fid,data[i].name,"",data[i].title);
//		else
//			this.tree.add(data[i].id,data[i].fid,data[i].name,"javascript:"+this.id+".selectTreeNode("+data[i].id+",'"+data[i].name+"','"+data[i].address+"');",data[i].title);
//	}
	
	this.mainPanelWidth = document.getElementById(this.mainpanelId).scrollWidth-20;
	this.mainPanelContentWidth = this.mainPanelWidth - 40;
//	document.getElementById(this.navigationId).innerHTML = this.tree.toString();
	this.tabs = new TabPanel({  
	        renderTo:deity.tabId,  
	        width:deity.mainPanelWidth,  
	        height:this.mainPanelHeight,  
	        //border:'none',  
	        active : 0,
	        //maxLength : 10,  
	        items : [
	            //{id:'-1',title:'主页',html:"&nbsphello world!",closable: true}
	        ]
	    });
	this.createTab('-1', '主页', 'pages/framework/home.html', true, false, null);
};

components.prototype.createTree = function(data)
{
	var item = null;
	var deity = this;
	
	item = $("<ul/>").attr("id","treeList").appendTo("#"+this.navigationId);
	$("<li/>").attr("id","maincomponents-tree-0").html("系统").appendTo(item);
	
	for(var i=0;i<data.length;i++)
	{
		if($("#maincomponents-tree-"+data[i].fid).children("ul").length==1)
		{
			item = $("#maincomponents-tree-"+data[i].fid).children("ul");
		}
		else
		{
			//bug
			$("#maincomponents-tree-"+data[i].fid).unbind('click');
			$("#maincomponents-tree-"+data[i].fid).click(function(e){
				if($(this).children("ul").length==1)
				{
					var b = $(this).children("ul").is(":visible");
					if(b) $('#treeList').treeList('closeNode',$(this));
					else $('#treeList').treeList('openNode',$(this));
				}
				e.stopPropagation();
			});
			item = $("<ul/>").appendTo("#maincomponents-tree-"+data[i].fid);
		}
		
		this.moduleMap[data[i].id.toString()] = {id:data[i].id,name:data[i].name,url:data[i].address};
		var strTmp = data[i].id.toString();
		li = $("<li/>").attr("id","maincomponents-tree-"+data[i].id).attr("thename",data[i].name).
			attr("url",data[i].address).html(data[i].title).click(function(e) {
				var d = $(this);
				deity.selectTreeNode(d.attr("id"),d.attr("thename"),d.attr("url"));
				e.stopPropagation();
			}).appendTo(item);
	}
	
	$('#treeList').treeList();
	
	$('#treeList').treeList('openNode',$("#maincomponents-tree-0"));
	
//	var l = this.trees.length;
//	this.tree = new dTree(this.id+'.trees['+l.toString()+']');
//	this.trees[l] = this.tree;
//	this.tree.add(0,-1,"系统");
//	return this.tree;
};


//components.prototype.createTree = function()
//{
//	var l = this.trees.length;
//	this.tree = new dTree(this.id+'.trees['+l.toString()+']');
//	this.trees[l] = this.tree;
//	this.tree.add(0,-1,"系统");
//	return this.tree;
//};


components.prototype.selectTreeNode = function(id,title,url)
{
	var deity = this;
	$.ajax({
	    url: url,
	    success: function (data) {
	    	if(deity.tabs.getTabPosision("id"+id.toString())=="id"+id.toString())
	    	{
	    		deity.tabs.addTab({id:"id"+id.toString(),
		    	    title:title ,
		    	    html:data,
		    	    closable: true,
		    	    disabled:false,
		    	    icon:"css/images/new.gif"
		    	 });
		    	if(deity.afterSelectTreeNodeEvent)
		    		deity.afterSelectTreeNodeEvent();
	    	}
	    	else
	    	{
	    		deity.tabs.show("id"+id.toString());
	    	}
	    	$('html,body').animate({scrollTop: '0px'}, 300);
	    },
	    cache: false
	});
};

components.prototype.createTab = function(id,title,url,closable,disabled,callback) 
{
	var deity = this;
	$.ajax({
	    url: url,
	    success: function (data) {
    		deity.tabs.addTab({
    			id:id,
	    	    title:title ,
	    	    html:data,
	    	    closable: closable,
	    	    disabled:disabled,
	    	    icon:"css/images/new.gif"
	    	 });
	    	if(callback)
	    		callback();
	    },
	    cache: false
	});
};

components.prototype.getEncodingList = function(fieldName) {
	
	var result = new Array();
	
	if(this.encodeingArray)
	{
		for(var i=0;i<this.encodeingArray.length;i++)
		{
			if(this.encodeingArray[i].fieldName == fieldName)
			{
				result.push(this.encodeingArray[i]);
			}
		}
	}
	
	return result;
};

/**
 * 根据编码表创建select元素
 */
components.prototype.createCodeComboxBox = function() 
{
	
	var s = null;
	var o = null;
	var f = true;
	var comboxArray = $(".combox");
	
	for(var i=0;i<comboxArray.length;i++)
	{
		if(comboxArray[i].getAttribute("code") == null || comboxArray[i].getAttribute("code") == "")
			continue;
		
		s = document.createElement("select");
		
		var oldValue = comboxArray[i].value;
		
		var d = mainComponent.getEncodingList(comboxArray[i].getAttribute("code").toString());
		
		if(comboxArray[i].getAttribute("blankLine"))
		{
			s.setAttribute("blankLine", comboxArray[i].getAttribute("blankLine"));
			o = document.createElement("option");
			o.setAttribute("value", "");
			if(f) { o.setAttribute("selected", "selected");f=false;	}
			o.innerHTML=comboxArray[i].getAttribute("blankLine");
			s.appendChild(o);
		}
		
		for(var j in d)
		{
			o = document.createElement("option");
			o.setAttribute("value", d[j].fieldValue);
			if(f) { o.setAttribute("selected", "selected");f=false;	}
			o.innerHTML=d[j].codingValue;
			s.appendChild(o);
		}
		
		if(comboxArray[i].getAttribute("id")!=null && comboxArray[i].getAttribute("id")!="")
			s.setAttribute("id", comboxArray[i].getAttribute("id"));
		if(comboxArray[i].getAttribute("name")!=null && comboxArray[i].getAttribute("name")!="")
			s.setAttribute("name", comboxArray[i].getAttribute("name"));
		s.setAttribute("code", comboxArray[i].getAttribute("code"));
		s.setAttribute("class", "combox");
		if(oldValue != null && oldValue != undefined && oldValue != "")
			s.value = oldValue;
		replaceNode(s,comboxArray[i]);
		
		if(comboxArray[i].getAttribute("style") != undefined
				&& comboxArray[i].getAttribute("style") != "") {
			s.setAttribute("style", comboxArray[i].getAttribute("style"));
		}
		
		if(comboxArray[i].getAttribute("required") != undefined
				&& comboxArray[i].getAttribute("required") != "") {
			s.setAttribute("required", comboxArray[i].getAttribute("required"));
		}
		
		if(comboxArray[i].getAttribute("alertmsg") != undefined
				&& comboxArray[i].getAttribute("alertmsg") != "") {
			s.setAttribute("alertmsg", comboxArray[i].getAttribute("alertmsg"));
		}
	}
};

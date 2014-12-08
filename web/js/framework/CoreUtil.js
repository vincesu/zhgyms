/**
 * author: vince.su
 */
/**
 * 这里的FormUtil之后要与framework.js里的function FormUtil() {} 合并
 */
var FormUtil = {
		
	setValue: function(formid,data)
	{
		var item = document.getElementById(formid);
		this._setValue(item, data);
	},
	_setValue: function(item,data)
	{
		if(item.tagName.toUpperCase() == "SELECT" || item.children.length == 0 )
		{
			if(item.name != null && item.name!="")
			{
				if(data == null)
				{
					item.value = null;
				}
				else if(data[item.name] != null)
				{
					if(item.tagName.toUpperCase()=="IMG")
						item.src=data[item.name];
					else
					item.value = data[item.name];
				}
			}
			
		} else
		{
			var nodes = item.children;
			for(var i=0;i<nodes.length;i++)
			{
				this._setValue(nodes[i], data);
			}
		}
	},
	getValue: function(formid)
	{
		var result = {};
		var item = document.getElementById(formid);
		this._getValue(item, result);
		return result;
	},
	_getValue: function(item,data)
	{
		if(item.tagName.toUpperCase() == "SELECT" || item.children.length == 0)
		{
			if(item.name!=null && item.name!="")
				data[item.name] = item.value;
			
		} else
		{
			var nodes = item.children;
			for(var i=0;i<nodes.length;i++)
			{
				this._getValue(nodes[i], data);
			}
		}
	},
	check: function(item,ifwarn) {
		if(ifwarn==null || ifwarn==undefined)
			ifwarn=false;
		if(item.tagName.toUpperCase() == "SELECT" || item.children.length == 0)
		{
			if(item.getAttribute("required")!=null)
			{
				if(item.getAttribute("required")=="true" || item.getAttribute("required")==true)
				{
					debugger;
					if(item.value == null || item.value=="")
					{
						if(ifwarn)
							this.warn(item);
						return false;
					}
				}
			}
			if(item.getAttribute("ver-type")!=null)
			{
				if(item.getAttribute("ver-type")=="email")
				{
					var reg = /^(?:[a-z\d]+[_\-\+\.]?)*[a-z\d]+@(?:([a-z\d]+\-?)*[a-z\d]+\.)+([a-z]{2,})+$/i;
					if(!reg.test(item.value))
					{
						if(ifwarn)
							this.warn(item);
						return false;
					}
				}
			}
			
			return true;
		} 
		else
		{
			var nodes = item.children;
			for(var i=0;i<nodes.length;i++)
			{
				if(this.check(nodes[i],ifwarn))
					continue;
				else
					return false;
			}
			return true;
		}
	},
	warn: function(item) {
		if(item.getAttribute("alertmsg")!=null && item.getAttribute("alertmsg")!="")
			alert(item.getAttribute("alertmsg"));
	},
	checkForEmpty: function(item) {
		
		if(item.tagName.toUpperCase() == "SELECT" || item.children.length == 0)
		{
			if(item.getAttribute("required")!=null)
			{
				if(item.getAttribute("required")=="true" || item.getAttribute("required")==true)
				{
					if(item.value == null || item.value=="")
					{
						if(item.getAttribute("alertmsg")!=null && item.getAttribute("alertmsg")!="")
							alert(item.getAttribute("alertmsg"));
						return false;
					}
					else
					{
						return true;
					}
				}
			}
			return true;
		} 
		else
		{
			var nodes = item.children;
			for(var i=0;i<nodes.length;i++)
			{
				if(this.checkForEmpty(nodes[i]))
					continue;
				else
					return false;
			}
			return true;
		}
	},
	/**
	 * 判断double类型值，
	 * @param input
	 * @returns {Number} -1 验证错误 1 验证正确 0 无法判断
	 */
	checkDouble : function(input) {
		var v = null;
		var qtye = null;
		if(input.indexOf("input")>0) {
			var _qtye = $(input);
			var qtye = $('#'+_qtye[0].id);
			if(qtye.val()!=undefined && qtye.val()!="")	{
				var v = this.formatCurrency(qtye.val());
			} else {
				return 0;
			}
		} else {
			v = input;
		}
		if(v!=null && v!="") {
			if(v!=null && v!="") {
				if(v[v.length-1]==".") {
					return 0;
				}
				if(/^\d+\.\d+$/.test(v)==false && /^\d+$/.test(v)==false) {
//						showContractDtailError(_qtye[0].id);
//					showContractDtailError(qtye,v);
					if(qtye)
						this.showCheckError(qtye,v);
					return -1;
				} else {
//						removeContractDtailError(_qtye[0].id);
					return 1;
				}
			} else {
				return 0;
			}
		} else {
			return 0;
		}
	},
	formatCurrency: function(val) {
		if(val==undefined || val==null) {
			return 0;
		}
		var q = null;
		if(val.indexOf("input")>0) {
			q = $("#"+$(val)[0].id).val();
			$("#"+$(val)[0].id).css("background","inherit");
		} else {
			q = val;
		}
		if(q) {
			if(q[0]=="$"||q[0]=="￥") {
				return q.substring(1);
			}
		}
		return q;
	},
	formatCurrencyD: function(val) {
		if(val==undefined || val==null) {
			return 0;
		}
		var q = null;
		if(val.indexOf("input")>0) {
			q = $("#"+$(val)[0].id).val();
			$("#"+$(val)[0].id).css("background","inherit");
		} else {
			q = val;
		}
		if(q) {
			if(q[0]=="$"||q[0]=="￥") {
				return new Number(q.substring(1));
			}
		}
		return new Number(q);
	},
	showCheckError:function(jqueryobj,val) {
		art.dialog({
			id:'formutil-check-error-dialog',
		    title: '5秒后自动消失',
		    icon:'error',
		    time:5,
		    content: '输入值：'+val+' 有误！'
		});
		jqueryobj[0].focus();
	},
	formatData : function(data) 
	{
		debugger;
		var props = null;
		for ( var p in data) 
		{
			if (typeof (data[p]) != "function") 
			{
				props = data[p].toString();
				if( props.indexOf("textarea", 0)>=0 || props.indexOf("input", 0)>=0 )
				{
					data[p] = $("#"+$(props).attr("id")).val();
				}
			} 
		}
		return data;
	}
};

function getExpandingName(filename)
{
	if(filename == null || filename == undefined)
		return "";
	var ind = filename.toString().lastIndexOf(".");
	var exname = "";
	if(ind != -1)
	{
		exname=filename.toString().substr(ind+1,filename.toString().length);
	}
	return exname;
}

function getFileNameWithoutExp(filename)
{
	if(filename == null || filename == undefined)
		return "";
	var ind = filename.toString().lastIndexOf(".");
	var result = "";
	if(ind != -1)
	{
		result=filename.toString().substring(0,ind);
	}
	return result;
}

function getFileNameFormPath(path)
{
	if(path == null || path == undefined)
		return "";
	var ind = path.toString().lastIndexOf("\\");
	var result = "";
	if(ind != -1) {
		result=path.toString().substring(ind+1);
	} else {
		return path;
	}
	return result;
}

var EncodingUtil = {
		refreshSysEncodingCodeWithWarn: function()
		{
			var r = new RemoteService("freshUserData");
			r.send(null,EncodingUtil.refreshSysEncodingCodeSuc1,null,'正在刷新编码表数据...');
		},
		refreshSysEncodingCodeSuc1: function(resp) {
			if(resp == null || resp.type=="error") {
				art.dialog("刷新编码表信息失败");
			} else {
				var rs = new RemoteService("getEncoding");
				rs.send(null,suc,err,"正在获得编码表");
			}
			
			function suc(resp2) {
				mainComponent.encodeingArray = resp2.data;
				art.dialog({
					content:"刷新成功",
					time:mainComponent.closeDialogTime
				});
			}
			function err(resp2) {
				alert("加载编码表失败!");
			}
			
		},
		refreshSysEncodingCodeWithoutWarn: function()
		{
			var r = new RemoteService("freshUserData");
			r.send(null,EncodingUtil.refreshSysEncodingCodeSuc2,null);
		},
		refreshSysEncodingCodeSuc2: function(resp) {
			if(resp == null || resp.type=="error") {
//				art.dialog("刷新编码表信息失败");
			} else {
				var rs = new RemoteService("getEncoding");
				rs.send(null,suc2,err2);
			}
			
			function suc2(resp2) {
				mainComponent.encodeingArray = resp2.data;
			}
			function err2(resp2) {
				
			}
			
		}
	};

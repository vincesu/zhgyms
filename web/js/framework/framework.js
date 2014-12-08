/**
 * author vince.su
 * 2012/9/4
 */

function RemoteService(serviceid)
{
	this._serviceid =serviceid; 
}

RemoteService.prototype.send = function(param,callback,errorFunc,message)
{
	if(this._serviceid == null)	return;
	var messagedislog = null;
	if(message !=null)
	{
		messagedislog = art.dialog({
		    content: message,
		    lock: true
		});
	}
	var data = {data:param,serviceid:this._serviceid};
	var responseEntity = null;
	$.ajax({
		type: "post",
		url: "service/dataInteraction",
		cache: false,
		data: data,
		dataType: "json",
		error: function(a,b,c) {
			if(messagedislog!=null)
				messagedislog.close();
			if(errorFunc!=null)
			{
				responseEntity = {type:"error",message:b};
				errorFunc(responseEntity);
			}
		},
		success: function(data, textStatus) {
			if(messagedislog!=null)
				messagedislog.close();
			if(data.type != undefined && data.type == "error") {
				if(data.message !=undefined && data.message.indexOf("重新登陆")>=0) {
					timeoutLoginDialog = art.dialog({id: "logindialog",title: "登陆超时，请重新登陆",lock:true});
					$.ajax({
					    url: "pages/framework/login/login.html",
					    success: function (data) {
					    	timeoutLoginDialog.content(data);
					        $("#logintimeoutdialogun").val(sys_username["username"]);
					    },
					    cache: false
					});
				} else {
					if(callback!=null)
						callback(data);
				}
			} else {
				if(callback!=null)
					callback(data);
			}
		}
	});
};

function getParam(name)
{
    var reg = new RegExp("(^|\\?|&)"+ name +"=([^&]*)(\\s|&|$)", "i"); 
    if (reg.test(location.href))
    {
    	return unescape(RegExp.$2.replace(/\+/g, " "));
    }
    return null;
}

function getQueryStringRegExp(name)
{
    var reg = new RegExp("(^|\\?|&)"+ name +"=([^&]*)(\\s|&|$)", "i"); 
    if (reg.test(location.href))
    {
    	return unescape(RegExp.$2.replace(/\+/g, " "));
    }
    return null;
}

//快排类加二分查找
function Half(sAry,compare)
{
	this.compareFunc = compare;
	this.sortArray = sAry;
}

Half.prototype.swap = function(i,j)
{
	if(this.sortArray == null || i==j)	return;
	var tmp = this.sortArray[i];
	this.sortArray[i] = this.sortArray[j];
	this.sortArray[j] = tmp;
};

Half.prototype.partition = function(low,high)
{
	var pivot,pos,i,j;
	pos = low;
	pivot = this.sortArray[pos];
	for(i=low+1;i<=high;i++)
	{
		if (this.compareFunc(pivot,this.sortArray[i])==1)
		{
			pos++;
			this.swap(pos,i);
		}
	}
	this.swap(low,pos);
	return pos;
};

Half.prototype.quickSort = function(low,high)
{
	var pos = null;
	if (low<high)
	{
		pos = this.partition(low,high);
		this.quickSort(low,pos-1);
		this.quickSort(pos+1,high);
	}
};

/*
 * 快排
 */
Half.prototype.QuickSort = function()
{
	if(this.sortArray == null || this.compareFunc == null)	return;
	this.quickSort(0,this.sortArray.length-1);
};

/*
 * 二分查找
 */
Half.prototype.Find = function(target)
{
	if(this.sortArray == null || this.compareFunc == null)	return;
	var mid = null,low=0,high=this.sortArray.length-1,tmp;
	mid = (low+high)/2;
	var ok = false;
	while(low<=high)
	{
		mid = Math.floor((low+high)/2);
		tmp = this.compareFunc(target,this.sortArray[mid]); 
		if(tmp == 0)
		{
			ok=true;
			break;
			
		} else if(tmp == 1)
		{
			low = mid+1;
			
		} else
		{
			high = mid-1;
		}
	}
	if(ok)	return mid;
	else	return null;
};

function replaceNode(newNode,oldNode) 
{
	var p = oldNode.parentNode;
	
	if(!p)	return;
	
	p.replaceChild(newNode,oldNode);
	
}



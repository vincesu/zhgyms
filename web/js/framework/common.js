/**
 * author vince.su
 * 2012-09-26
 */

/**
 * choose dialog
 */
function ChooseDialog(
		urlparam,
		queryfield,
		serviceid,
		colnames,
		colmodel,
		execFunc,
		multiselect,
		subGrid,/*true or false*/
		subGridUrl,
	    subGridModel
	)
{
	this.urlparam = urlparam;
	if(this.urlparam == null)
		this.urlparam="";
	this.queryfield = queryfield;
	this.serviceid = serviceid;
	this.colnames = colnames;
	this.colmodel = colmodel;
	this.execFunc = execFunc;
	this.createCompleted = null;
	this.gridComplete = function() {};
	if(multiselect == null)
		this.multiselect = true;
	else
		this.multiselect = multiselect;
	if(subGrid==null||subGrid==undefined)
		this.subGrid=false;
	else
		this.subGrid=subGrid;
	this.subGridUrl = subGridUrl;
	this.subGridModel = subGridModel;
	this.querygrid = null;
}

ChooseDialog.prototype.queryfieldid = "choosedialogform";
ChooseDialog.prototype.gridid = "choosedialoggrid";
ChooseDialog.prototype.pagerid = "choosedialogpager";

ChooseDialog.prototype.open = function()
{
	var deity = this;
	var posd = {};
	if(this.postData!=undefined && this.postData!=null)
		posd = this.postData;
	if(deity.gridComplete ==null || deity.gridComplete==undefined)
		deity.gridComplete = function(){};
	this.dialog = art.dialog(
		{
			id: "choosedialog",
			title: "",
			lock:true,
			ok: function () {
				if(deity.execFunc!=null)
				{
					var param = new Array();
					if(deity.grid.getGridParam().multiselect)
					{
						var selectedIds = deity.grid.jqGrid("getGridParam", "selarrrow");
						for(var i in selectedIds)
						{
							param[i] = deity.grid.jqGrid("getRowData", selectedIds[i]);
						}
					} else
					{
//						
						var selectedId = deity.grid.jqGrid("getGridParam", "selrow");
						if(selectedId != null)
							param[0] = deity.grid.jqGrid("getRowData", selectedId);
					}
					return deity.execFunc(param);
				}
			},
			cancelVal: '关闭',
			cancel: true
		}
	);
	
	$.ajax({
	    url: "pages/framework/dialog/ChooseDialog.html",
	    success: function (data) {
	        deity.dialog.content(data);
	        deity.grid = $("#"+deity.gridid).jqGrid( {	
    			url:"service/dataInteraction?serviceid="+deity.serviceid+deity.urlparam,
    			mtype: "POST",
    			datatype: "local", 
    			colNames:deity.colnames, 
    			colModel:deity.colmodel,
//		        			autowidth: true,
    			height: 210,
    			rowNum:50, 
    			rowList:[50,100,150],
    			pager: '#'+deity.pagerid, 
    			//sortname: 'id',
    			viewrecords: true,
    			//sortorder: "desc", 
    			caption:"请选择",
    			multiselect: deity.multiselect,
    			subGrid : deity.subGrid,
    			subGridUrl: "service/dataInteraction?serviceid="+deity.subGridUrl,
    		    subGridModel: deity.subGridModel,
    		    gridComplete: deity.gridComplete,
    		    postData:posd,
    		    ondblClickRow: function(id) {
    		    	if(deity.execFunc!=null)
    				{
    					var param = new Array();
//    					if(deity.grid.getGridParam().multiselect)
//    					{
//    						var selectedIds = deity.grid.jqGrid("getGridParam", "selarrrow");
//    						for(var i in selectedIds)
//    						{
//    							param[i] = deity.grid.jqGrid("getRowData", selectedIds[i]);
//    						}
//    					} else
//    					{
//    						var selectedId = deity.grid.jqGrid("getGridParam", "selrow");
    						if(id != null)
    							param[0] = deity.grid.jqGrid("getRowData", id);
//    					}
    					deity.dialog.close();
    					return deity.execFunc(param);
    				}
    		    }
    		});
	        	
        	deity.querygrid = new QueryGrid(
        			deity.queryfieldid,
        			deity.queryfield,
        			deity.grid,
        			null,
        			null,
        			null,
        			null,
        			null,
        			null
        			);
        	
        	deity.dialog.position("50%","50%");
	        mainComponent.createCodeComboxBox();
	        
	        deity.querygrid.setGrid(deity.grid);
	        
	        if(deity.createCompleted!=null){
	        	deity.createCompleted();
	        }
	        
	    },
	    cache: false
	});
};

/**
 * upload component
 */
function UploadComponent() {}

UploadComponent.prototype.uploadName = "upload";
/**
 * 
 * @param formid
 * @param file	使用ajax上传时是必须的，用iframe上传可不传
 * @param callbacksuc
 * @param callbackerr
 * @param serviceid
 * @param message
 */
UploadComponent.prototype.createUploadComponent = function(formid,file,callbacksuc,callbackerr,serviceid,message) {
	var callbackfunc1 = callbacksuc;
	var callbackfunc1err = callbackerr;
	var sid = serviceid;
	var msgdiv = $("#"+message);
	
	
	$("#"+formid).on("submit",function() 
	{
		
		if($("#"+file).val()=="")
		{
			alert("请先上传文件");
			return false;
		}
		var callbackfunc2 = callbackfunc1;
		var callbackfunc2err = callbackfunc1err;
		var msg = msgdiv;
		/*if(window.FormData) 
		{//support ajax upload file
			var formData = new FormData();
			// 建立一个upload表单项，值为上传的文件
			formData.append(new UploadComponent().uploadName,document.getElementById(file).files[0]);
			var xhr = new XMLHttpRequest();
			xhr.open('POST', $(this).attr('action')+"?serviceid="+sid);
			//上传描述信息
			xhr.upload.onprogress = function (event) {
				if (event.lengthComputable) 
				{
					var complete = (event.loaded / event.total * 100 | 0);
					msg.html("上传"+complete.toString()+"%");
				}
			};
			// 定义上传完成后的回调函数
			xhr.onload = function() {
				if (xhr.status === 200) 
				{
					callbackfunc2(null);
					msg.html("上传完成");
				}
				else 
				{
					alert("上传出错，请联系管理员");
				}
			};
			
			

			xhr.send(formData);
			return false;
		}
		else
		{//use iframe upload
		*/
		var filepath = $("#"+file).val();
		
		if(filepath.substr(filepath.length-1,1)=="/")
		{
			filepath = filepath.substring(0,filepath.length-1);
		}
		else if(filepath.substr(filepath.length-1,1)=="\\")
		{
			filepath = filepath.substring(0,filepath.length-1);
		}
		var filename = null;
		if(filepath.lastIndexOf("/")!=-1)
		{
			filename = filepath.substring(filepath.lastIndexOf("/")+1,filepath.length);
		}
		else if(filepath.lastIndexOf("\\")!=-1)
		{
			filename = filepath.substring(filepath.lastIndexOf("\\")+1,filepath.length);
		}
		else
		{
			filename = filepath;
		}
		
		$("#filename2").val(filename);
		
			var form = $(this);
			var seed = Math.floor(Math.random() * 1000);
			var seederr = Math.floor(Math.random() * 1000);
			var id = "uploader-frame-" + seed;
			var callback = "uploader-cb-" + seed;
			var callbackerr = "uploader-cb-" + seederr;
			var iframe = $('<iframe id="'+id+'" name="'+id+'" style="display:none;">');
//			var fn = $('<input type="hidden" name="filename" value="'+filename+'">');
			var url = $(this).attr('action');
			
			
			form
//			.append(fn)
			.attr('target', id).append(iframe).
				attr('action', 'service/fileUpload?callbackfunc='+callback+'&errorfunc='+callbackerr+'&serviceid='+sid+
						"&filename="+filename);
			msg.html("上传中...");
			window[callback] = function(data) {
				msg.html("上传完成");
				callbackfunc2(data);
//				console.log('received callback:', data);
				iframe.remove(); //removing iframe
				form.removeAttr('target');
				form.attr('action', url);
				window[callback] = undefined; //removing callback
			};
			window[callbackerr] = function(data) {
				msg.html("上传失败");
				callbackfunc2err(data);
//				console.log('received callback:', data);
				iframe.remove(); //removing iframe
				form.removeAttr('target');
				form.attr('action', url);
				window[callbackerr] = undefined; //removing callback
			};
			return true;
		/*}*/
		
	});
};

UploadComponent.prototype.pupupUploadDialog = function(serviceid) {
	var dialog = art.dialog({
		title: "上传",
		lock:true,
		ok: function () {
			$("#upload-form").submit();
			return false;
		},
		cancelVal: '关闭',
		cancel: true

	});
	$.ajax({
	    url: "pages/framework/dialog/UploadDialog.html",
		    success: function (data) {
		        dialog.content(data);
				new UploadComponent().createUploadComponent(
					"upload-form","upload",
					function(d) {
						dialog.close();
						art.dialog({
							time: 2,
    						content: d
						});
					},
					function(errmsg) {
						dialog.close();
						art.dialog({content: "上次文件失败，错误原因："+errmsg });
					},
					serviceid,"message"
				);
		    },
		    cache: false
		});

};

/**
 * download component
 */
function DownloadComponent() {}

DownloadComponent.prototype.createDownloadComponent = function(formid,serviceid,filename,params) {
	var form = $("#"+formid);
	if(params==null || params==undefined) {
		form.attr('action', 'service/fileDownload?serviceid='+serviceid+'&filename='+filename);
	} else {
		filename = filename.replace('/','-');
		filename = filename.replace(',','-');
		form.attr('action', 'service/fileDownload?serviceid='+serviceid+'&filename='+filename+"&params="+params);
	}
	form.attr('method','post');
	form.submit();
};

/**
 * preview component
 */
function PreviewComponent() {}

PreviewComponent.prototype.createPreviewComponent = function(serviceid,params,filename) {
	
	if(filename==null || filename==undefined)
	{
		window.open('service/filePreview?serviceid='+serviceid+'&params='+params,'_blank');
	}
	else
	{
		filename = filename.replace('/','-');
		filename = filename.replace(',','-');
		window.open('service/filePreview/'+filename+'?serviceid='+serviceid+'&params='+params,'_blank');
	}
//	var form = $("#"+formid);
//	if(params==null || params==undefined)
//		form.attr('action', 'service/filePreview?serviceid='+serviceid);
//	else
//		form.attr('action', 'service/filePreview?serviceid='+serviceid+"&params="+params);
//	form.attr('method','post');
//	form.submit();
};

function createAdaptiveComponent()
{
	var list = $(".adaptive");
	for(var i=0;i<list.length;i=i+1)
	{
		$(list[i]).height(list[i].scrollHeight);
	}
	$(".adaptive").keyup(function(event){
		this.style.height = "auto";
		$(this).height(this.scrollHeight);
	});
}

function popupToAdd(
		serviceid,
		dialogurl,
		formid,
		beforeSave,
		afterPopup,
		finish
	)
{
	var dialog = art.dialog({
		id: dialogurl,
		lock:true,
		title: "添加",
		ok: function () {
			var data = FormUtil.getValue(formid);
			if(beforeSave != null)
			{
				if(beforeSave(data)) {}
				else return false;
			}
			var r = new RemoteService(serviceid);
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
					if(finish!=null)
						finish(data);
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
	    url: dialogurl,
	    success: function (data) {
	        dialog.content(data);
	        if(afterPopup != null)
	        	afterPopup(null);
	    },
	    cache: false
	});
}

function  ForDight(Dight,How)    
{    
   Dight  =  Math.round  (Dight*Math.pow(10,How))/Math.pow(10,How);    
   return  Dight;    
} 

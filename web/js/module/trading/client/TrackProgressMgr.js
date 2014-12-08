
var TrackProgressGrid = null;
var TrackProgressQueryGrid = null;

$(function(){
	
	TrackProgressQueryGrid = new QueryGrid(
			"TrackProgressForm",
			[
			  {name:'contact',type:'text',text:'联系人'},
			  {name:'unit',type:'text',text:'单位',value:queryTrackPUnit},
			  {name:'saleman',type:'text',text:'业务员',code:'userrealname',classname:'combox',blankLine:'全部'}
	//		  {name:'bt',type:'text',text:'省厅回盘起始时间',classname:'My97DatePicker'},
	//		  {name:'et',type:'text',text:'省厅回盘截止时间',classname:'My97DatePicker'},
	//		  {name:'yc0076',type:'select',text:'发送审核状态',options: [
	//		                                         {value:"'038000','041200'",text:'全部',selected:"selected"},
	//		                                         {value:"041200",text:'已发送审核'},
	//		                                         {value:"038000",text:'未发送审核'}
	//		                                         ] }
			 ],
			 null,
			 "getTrackProgress",
			 "addTrackProgress",
			 "removeTrackProgress",
			 "updateTrackProgress",
			 "pages/module/trading/client/TrackProgressDialog.html",
			 "TrackProgressDialogForm"
	);
//	alert(queryTrackPUnit);
	
	
	TrackProgressGrid = $("#TrackProgressGrid").jqGrid(
		{	
			url:'service/dataInteraction?serviceid=getTrackProgressList',
			mtype: "POST",
			datatype: "local", 
			colNames:['单位','联系人','id','clientid','跟进时间','意向产品','跟进描述','报价单(有/无)','业务员',
			          'fileid1','fileid2','fileid3','fileid4',
			          'filename1','filename2','filename3','filename4','附件'], 
			colModel:[ 
						{name:'unit',index:'unit',width:200,align:"center"},
						{name:'contact',index:'contact',width:120,align:"center"},
					   {name:'id',index:'id', width:100,hidden:true,align:"center"},	
					   {name:'clientid',index:'clientid',width:120,hidden:true,align:"center"},
					   {name:'tracktime',index:'tracktime',width:200,align:"center"},
					   {name:'product',index:'product',width:120,align:"center"},
					   {name:'progress',index:'progress',width:120,align:"center"},
					   {name:'havequote',index:'havequote',width:120,align:"center",hidden:true},
					   {name:'realname',index:'realname',width:80,align:"center"},
					   {name:'fileid1',index:'fileid1',width:120,align:"center",hidden:true},
					   {name:'fileid2',index:'fileid2',width:120,align:"center",hidden:true},
					   {name:'fileid3',index:'fileid3',width:120,align:"center",hidden:true},
					   {name:'fileid4',index:'fileid4',width:120,align:"center",hidden:true},
					   {name:'filename1',index:'filename1',width:120,align:"center",hidden:true},
					   {name:'filename2',index:'filename2',width:120,align:"center",hidden:true},
					   {name:'filename3',index:'filename3',width:120,align:"center",hidden:true},
					   {name:'filename4',index:'filename4',width:120,align:"center",hidden:true},
					   {name:'tk',index:'tk',width:500,align:"left"}
			         ],
			 width:mainComponent.mainPanelContentWidth,
			 height:mainComponent.mainPanelContentHeight,
			 rowNum:mainComponent.rowNum, 
			 rowList:[10,50,100],
			 pager: '#TrackProgressPager',
//			 postData:{unit:queryTrackPUnit},
			 //sortname: 'id',
			 viewrecords: true,
			 //sortorder: "desc", 
			 caption:"信息列表",
			 rownumbers: true,
			 gridComplete:function() {
				 
				 queryTrackPUnit = '';
				 
				var ids = $("#TrackProgressGrid").jqGrid('getDataIDs');
				var img = null;
				var cl = null;
				var rowdata = null;
				
				for ( var i = 0; i < ids.length; i++) {
					debugger;
					cl = ids[i];
					rowdata = $("#TrackProgressGrid").jqGrid("getRowData", cl);
					
					var expandName1 = getExpandingName(rowdata["filename1"]).toLowerCase();
					var expandName2 = getExpandingName(rowdata["filename2"]).toLowerCase();
					var expandName3 = getExpandingName(rowdata["filename3"]).toLowerCase();
					var expandName4 = getExpandingName(rowdata["filename4"]).toLowerCase();
					
					cl = ids[i];
					img = '<table align="center">' +
					'<tr>';
					//file 1
					if(rowdata["fileid1"]!=null && rowdata["fileid1"]!="")
					{
						console.log(rowdata["filename1"]);
						img = img + 
						'<td style="border:0;"><img src="css/framework/img/'+checkFileType(expandName1)+'.gif"></td>'+
						'<td style="border:0;text-align:center;"><div>'+formatTPFilename(rowdata["filename1"])+'</div><div>'+
						'<a href="###" style="margin-left:15px;" onclick="javascript:downloadTrackProgressFile(\''+rowdata["filename1"]+'\',\''+rowdata["fileid1"]+'\');">下载</a>'+
						'<a href="###" style="margin-left:15px;" onclick="javascript:previewTrackProgressFile(\''+rowdata["filename1"]+'\',\''+rowdata["fileid1"]+'\');">打印预览</a>'+
						'<a href="###" style="margin-left:15px;" onclick="javascript:removeTrackProgressFile(\''+rowdata["filename1"]+'\',\''+rowdata["fileid1"]+'\');">删除</a>'+
						'</div></td>';
					}
					//file 2
					if(rowdata["fileid2"]!=null && rowdata["fileid2"]!="")
					{
						img = img +
						'<td style="border:0;"><img src="css/framework/img/'+checkFileType(expandName2)+'.gif"></td>'+
						'<td style="border:0;"><div>'+formatTPFilename(rowdata["filename2"])+'</div><div>'+
						'<a href="###" style="margin-left:15px;" onclick="javascript:downloadTrackProgressFile(\''+rowdata["filename2"]+'\',\''+rowdata["fileid2"]+'\');">下载</a>'+
						'<a href="###" style="margin-left:15px;" onclick="javascript:previewTrackProgressFile(\''+rowdata["filename2"]+'\',\''+rowdata["fileid2"]+'\');">打印预览</a>'+
						'<a href="###" style="margin-left:15px;" onclick="javascript:removeTrackProgressFile(\''+rowdata["filename2"]+'\',\''+rowdata["fileid2"]+'\');">删除</a>'+
						'</div></td>';
					}
					img = img + '</tr>'+
					
					'<tr>';
					
					//file 3
					if(rowdata["fileid3"]!=null && rowdata["fileid3"]!="")
					{
						img = img + 
						'<td style="border:0;"><img src="css/framework/img/'+checkFileType(expandName3)+'.gif"></td>'+
						'<td style="border:0;text-align:center;"><div>'+formatTPFilename(rowdata["filename3"])+'</div><div>'+
						'<a href="###" style="margin-left:15px;" onclick="javascript:downloadTrackProgressFile(\''+rowdata["filename3"]+'\',\''+rowdata["fileid3"]+'\');">下载</a>'+
						'<a href="###" style="margin-left:15px;" onclick="javascript:previewTrackProgressFile(\''+rowdata["filename3"]+'\',\''+rowdata["fileid3"]+'\');">打印预览</a>'+
						'<a href="###" style="margin-left:15px;" onclick="javascript:removeTrackProgressFile(\''+rowdata["filename3"]+'\',\''+rowdata["fileid3"]+'\');">删除</a>'+
						'</div></td>';
					}
					//file 4
					if(rowdata["fileid4"]!=null && rowdata["fileid4"]!="")
					{
						img = img +
						'<td style="border:0;"><img src="css/framework/img/'+checkFileType(expandName4)+'.gif"></td>'+
						'<td style="border:0;"><div>'+formatTPFilename(rowdata["filename4"])+'</div><div>'+
						'<a href="###" style="margin-left:15px;" onclick="javascript:downloadTrackProgressFile(\''+rowdata["filename4"]+'\',\''+rowdata["fileid4"]+'\');">下载</a>'+
						'<a href="###" style="margin-left:15px;" onclick="javascript:previewTrackProgressFile(\''+rowdata["filename4"]+'\',\''+rowdata["fileid4"]+'\');">打印预览</a>'+
						'<a href="###" style="margin-left:15px;" onclick="javascript:removeTrackProgressFile(\''+rowdata["filename4"]+'\',\''+rowdata["fileid4"]+'\');">删除</a>'+
						'</div></td>';
					}
					
					img = img + '</tr>'+
					
					'</table>';
					$("#TrackProgressGrid").jqGrid('setRowData', cl, {
						"tk" : img
					});
				}
			 },
		}
	);
	
	jQuery("#TrackProgressGrid").jqGrid('navGrid','#TrackProgressPager',{edit:false,add:false,del:false});
	
	TrackProgressQueryGrid.setGrid(TrackProgressGrid,["havequote"],["truefalse"]);
	
	TrackProgressQueryGrid.createDoubleClick();
	
	TrackProgressQueryGrid.beforeSave = function(data) {
		
		if(!FormUtil.check(document.getElementById("TrackProgressDialogForm"),true))
			return false;
		
		return true;
	};
	
	TrackProgressQueryGrid.afterSave = function(resp) {
		TrackProgressQueryGrid.query();
	};
	
	
	
	TrackProgressQueryGrid.afterPopup = function(data) 
	{
		if(trackProgressClientId!=undefined)
		{
			$('#TrackProgressDialogClientid').val(trackProgressClientId);
		}
		if(trackProgressClientUnit!=undefined)
		{
			$('#TrackProgressDialogcUnit').val(trackProgressClientUnit);
		}
		if(trackProgressClientName!=undefined)
		{
			$('#TrackProgressDialogcContact').val(trackProgressClientName);
		}
	};
	
	TrackProgressQueryGrid.createloadComplete();
	
});

function checkFileType(expandName)
{
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
	return type;
}


function chooseExistClient()
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
				$("#TrackProgressDialogcContact").val(data[0]["contact"]);
				$("#TrackProgressDialogcUnit").val(data[0]["unit"]);
				$("#TrackProgressDialogClientid").val(data[0]["id"]);
				$("#gridTable").setGridParam().hideCol("gh").trigger("reloadGrid");
			},
			false,
			false,/*true or false*/
			null,
		    null
		);
	cd.open();
}


function addTrackProgressFile()
{
	
	var selectedId = TrackProgressGrid.jqGrid("getGridParam", "selrow");
	if(selectedId == null)
	{
		art.dialog("请选择一条记录");
		return;
	}
	var d = TrackProgressGrid.jqGrid("getRowData", selectedId);
	
	var dialog = art.dialog({
		lock:true,
		title: "添加",
		ok: function () {
			
			if($("#id").val()==null ||$("#id").val()==undefined||$("#id").val()=="")
			{
				art.dialog("请先上传文件");
				return false;
			}
			
			var data = FormUtil.getValue("TrackProgressFileDialogForm");
			data["relatedId"] = d["id"];
			
			var r = new RemoteService("addTrackProgressFile");
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
					TrackProgressQueryGrid.query();
					TrackProgressQueryGrid.grid.jqGrid('setSelection',selectedId);
//					TrackProgressQueryGrid.grid.setSelection(selectedId);
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
	    url: "pages/module/trading/client/TrackProgressFileDialog.html",
	    success: function (data) {
	        dialog.content(data);
	        mainComponent.createCodeComboxBox();
	        new UploadComponent().createUploadComponent(
    			"upload-form","upload",
    			function(data) {
    				var ind = data.toString().indexOf("&");
    				if(ind == -1)
    				{
    					$("#id").val(data);
    				}
    				else
    				{
    					var fileid=data.toString().substr(0,ind);
    					$("#id").val(fileid);
    				}
    			},
    			function(errmsg) {
    				alert("上传失败，错误原因："+errmsg);
    			},
    			"uploadFile","message"
    		);
	        
	        $("#upload").change(function(){
				$("#TrackProgressFiletname").val(getFileNameWithoutExp(getFileNameFormPath($("#upload").val())));
			});
	        
	    },
	    cache: false
	});
	
}

function downloadTrackProgressFile(filename,id)
{
	new DownloadComponent().createDownloadComponent("exportTPFileForm","downloadFiles",filename,id);
}

function previewTrackProgressFile(filename,id)
{
	var fn = getFileNameWithoutExp(filename);
	new PreviewComponent().createPreviewComponent("previewFiles",id,fn+".pdf");
}

function removeTrackProgressFile(filename,id)
{
	var m="确定删除？";
	if(confirm(m))
	{
		var param = new Array();
		param[0] = {fileid:id};
		var rs = new RemoteService("removeTrackProgressFile");
		rs.send(param, suc, null, "正在删除...");
	}
	function suc(resp)
	{
		if(resp == null || resp.type=="error")
		{
			if(resp.message==null)
				art.dialog("删除失败");
			else
				art.dialog(resp.message);
			
		} else {
			$("#orderfile-"+id).remove();
			art.dialog({
				content:"删除成功",
				time:mainComponent.closeDialogTime
			});
		}
	}
}

function formatTPFilename(filename)
{
	if(filename.length>=11)
		return filename.substring(1,10)+'...';
	else
		return filename;
}
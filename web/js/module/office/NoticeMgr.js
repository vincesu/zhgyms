
var NoticeGrid = null;
var NoticeQueryGrid = null;

$(function() {

	NoticeQueryGrid = new QueryGrid(
			"NoticeForm",
			[ {name:'title',type:'text',text:'标题'}
	//		  {name:'bt',type:'text',text:'省厅回盘起始时间',classname:'My97DatePicker'},
	//		  {name:'et',type:'text',text:'省厅回盘截止时间',classname:'My97DatePicker'},
	//		  {name:'yc0076',type:'select',text:'发送审核状态',options: [
	//		                                         {value:"'038000','041200'",text:'全部',selected:"selected"},
	//		                                         {value:"041200",text:'已发送审核'},
	//		                                         {value:"038000",text:'未发送审核'}
	//		                                         ] }
			 ],
			 null,
			 "getNotice",
			 "addNotice",
			 "removeNotice",
			 "updateNotice",
			 "pages/module/office/Notice/NoticeDialog.html",
			 "NoticeDialogForm"
	);
	
	NoticeGrid = $("#NoticeGrid").jqGrid(
		{	
			url:'service/dataInteraction?serviceid=getNoticeList',
			mtype: "POST",
			datatype: "local", 
			colNames:['ID','标题','发布者','日期','fileid','filepath'], 
			colModel:[ 
					   {name:'id',index:'id', width:100,hidden:true,align:"center"},	
			           {name:'title',index:'company', width:120,align:"center"},
			           {name:'author',index:'contact', width:120,align:"center"},
			           {name:'date',index:'phone', width:120,align:"center"},
			           {name:'fileid',index:'fileid', width:120,align:"center",hidden:true},
			           {name:'filepath',index:'filepath', width:120,align:"center",hidden:true}
			         ],
			 width:mainComponent.mainPanelContentWidth,
			 height:mainComponent.mainPanelContentHeight,
			 rowNum:mainComponent.rowNum, 
			 rowList:[10,50,100],
			 pager: '#NoticePager', 
			 //sortname: 'id',
			 viewrecords: true,
			 //sortorder: "desc", 
			 caption:"通知",
			 rownumbers: true
		}
	);
	
	jQuery("#NoticeGrid").jqGrid('navGrid','#NoticePager',{edit:false,add:false,del:false});
	
	NoticeQueryGrid.setGrid(NoticeGrid);
	
	NoticeQueryGrid.setDoubleClickFunc(function(id) {
		NoticeView();
	});

});

function NoticeView() {
	var selectedId = NoticeGrid.jqGrid("getGridParam", "selrow");
	if(selectedId == null) {
		art.dialog("请选择一条记录");
		return;
	}
	var rowdata = NoticeGrid.jqGrid("getRowData", selectedId);
	if(rowdata['fileid']=='' && rowdata['filepath']==''){
		NoticeQueryGrid.popupToView();
	} else {
		new PreviewComponent().createPreviewComponent("previewFiles",rowdata["fileid"],rowdata['title']);
	}
}

function uploadNoticeFile() {
	
	var dialog = art.dialog({
		lock:true,
		title: "添加",
		ok: function () {
			
			if($("#notice-file-id").val()==null || $("#notice-file-id").val()==undefined 
					|| $("#notice-file-id").val()=="") {
				art.dialog("请先上传文件");
				return false;
			}
			
			if($('#noticefilename').val()=="") {
				art.dialog("请填写标题");
				return false;
			}
			
			var data = FormUtil.getValue("NoticeFileDialogForm");
			
			var r = new RemoteService("addNotice");
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
					NoticeQueryGrid.query();
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
	    url: "pages/module/office/Notice/NoticeFileDialog.html",
	    success: function (data) {
	        dialog.content(data);
	        new UploadComponent().createUploadComponent(
    			"upload-form","upload",
    			function(data) {
    				var ind = data.toString().indexOf("&");
    				if(ind == -1)
    				{
    					$("#notice-file-id").val(data);
    				}
    				else
    				{
    					var fileid=data.toString().substr(0,ind);
    					$("#notice-file-id").val(fileid);
    				}
    			},
    			function(errmsg) {
    				alert("上传失败，错误原因："+errmsg);
    			},
    			"uploadFile","message"
    		);
	        $("#upload").change(function(){
				console.log("upload change");
				$("#noticefilename").val(getFileNameWithoutExp(getFileNameFormPath($("#upload").val())));
			});
	    },
	    cache: false
	});
	
}

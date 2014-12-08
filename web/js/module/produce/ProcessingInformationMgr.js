
var ProcessingInformationGrid = null;
var ProcessingInformationQueryGrid = null;

ProcessingInformationQueryGrid = new QueryGrid(
		"ProcessingInformationForm",
		[ {name:'project',type:'text',text:'项目'},
			{name:'number',type:'text',text:'编号'}
//		  {name:'bt',type:'text',text:'省厅回盘起始时间',classname:'My97DatePicker'},
//		  {name:'et',type:'text',text:'省厅回盘截止时间',classname:'My97DatePicker'},
//		  {name:'yc0076',type:'select',text:'发送审核状态',options: [
//		                                         {value:"'038000','041200'",text:'全部',selected:"selected"},
//		                                         {value:"041200",text:'已发送审核'},
//		                                         {value:"038000",text:'未发送审核'}
//		                                         ] }
		 ],
		 null,
		 "getProcessingInformation",
		 "addProcessingInformation",
		 "removeProcessingInformation",
		 "updateProcessingInformation",
		 "pages/module/produce/ProcessingInformation/ProcessingInformationDialog.html",
		 "ProcessingInformationDialogForm"
);

ProcessingInformationGrid = $("#ProcessingInformationGrid").jqGrid(
	{	
		url:'service/dataInteraction?serviceid=getProcessingInformationList',
		mtype: "POST",
		datatype: "local", 
		colNames:['ID','合同编号','项目','价格','数量','联系人','单位','联系方式','备注'], 
		colModel:[ 
				   {name:'id',index:'id', width:100,hidden:true,align:"center"},
				   {name:'number',index:'number', width:120,align:"center"},
		           {name:'project',index:'project', width:120,align:"center"},
		           {name:'price',index:'price', width:120,align:"center"},
		           {name:'qty',index:'qty', width:120,align:"center"},
		           {name:'contact',index:'contact', width:120,align:"center"},
		           {name:'unit',index:'unit', width:120,align:"center"},
		           {name:'contactWay',index:'contactWay', width:120,align:"center"},
		           {name:'remark',index:'remark', width:120,align:"center"}
		         ],
		 width:mainComponent.mainPanelContentWidth,
		 height:mainComponent.mainPanelContentHeight,
		 rowNum:mainComponent.rowNum, 
		 rowList:[10,50,100],
		 pager: '#ProcessingInformationPager', 
		 //sortname: 'id',
		 viewrecords: true,
		 //sortorder: "desc", 
		 caption:"加工信息",
		 rownumbers: true
	}
);

jQuery("#ProcessingInformationGrid").jqGrid('navGrid','#ProcessingInformationPager',{edit:false,add:false,del:false});

ProcessingInformationQueryGrid.setGrid(ProcessingInformationGrid);

ProcessingInformationQueryGrid.createDoubleClick();

function pi_chooseContract()
{
	var cd = new ChooseDialog(
			null,
			[ {name:'number',type:'text',text:'编号'},
			  {name:'name',type:'text',text:'产品名称'},
			  {name:'salesman',type:'text',text:'业务员'},
			  {name:'bt',type:'text',text:'起始时间',classname:'date'},
			  {name:'et',type:'text',text:'截止时间',classname:'date'}
			 ],
			"getProduceList",
			['ID','产品名称','编号','时间','发货时间','业务员','目的地','是否审核','审核人','contractId','备注','fileid','path','filename'],
			[ 
			   {name:'id',index:'id', width:100,hidden:true,align:"center"},
			   {name:'name',index:'name', width:120,align:"center",hidden:true},
	           {name:'number',index:'number', width:120,align:"center"},
	           {name:'date',index:'date', width:120,align:"center"},
	           {name:'deliveryTime',index:'deliveryTime', width:120,align:"center"},
	           {name:'salesman',index:'salesman', width:120,align:"center"},
	           {name:'destination',index:'destination', width:120,align:"center"},
	           {name:'isAudit',index:'isAudit', width:120,align:"center",hidden:true},
	           {name:'audit',index:'audit', width:120,align:"center",hidden:true},
	           {name:'contractId',index:'contractId', width:120,align:"center",hidden:true},
	           {name:'remark',index:'remark', width:120,align:"center",hidden:true},
	           {name:'fileid',index:'fileid', width:120,align:"center",hidden:true},
	           {name:'path',index:'path', width:120,align:"center",hidden:true},
	           {name:'filename',index:'filename', width:120,align:"center",hidden:true}
	         ],
			function(data){
//				if(data[0]["reserved"].indexOf('locked')!=0)
//				{
//					alert('此订单未审核通过，不能生成生产单！');
//					return false;
//				}
				$("#processingInformation-number-input").val(data[0]["number"]);
			},
			false,
			false,/*true or false*/
			null,
		    null
		);
		
		cd.open();
}

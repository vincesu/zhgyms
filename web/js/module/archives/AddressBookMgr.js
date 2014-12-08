
var AddressBookGrid = null;
var AddressBookQueryGrid = null;

AddressBookQueryGrid = new QueryGrid(
		"AddressBookForm",
		[ {name:'name',type:'text',text:'姓名'},
//		  {name:'bt',type:'text',text:'省厅回盘起始时间',classname:'My97DatePicker'},
//		  {name:'et',type:'text',text:'省厅回盘截止时间',classname:'My97DatePicker'},
		  {name:'gender',type:'select',text:'性别',options: [
		                                         {value:"",text:'全部',selected:"selected"},
		                                         {value:"1",text:'男'},
		                                         {value:"2",text:'女'}
		                                         ] }
		 ],
		 null,
		 "getAddressBook",
		 "addAddressBook",
		 "removeAddressBook",
		 "updateAddressBook",
		 "pages/module/archives/AddressBook/AddressBookDialog.html",
		 "AddressBookDialogForm"
);

AddressBookGrid = $("#AddressBookGrid").jqGrid(
	{	
		url:'service/dataInteraction?serviceid=getAddressBookList',
		mtype: "POST",
		datatype: "local", 
		colNames:['ID',"姓名", "性别", "办公室电话", "移动电话", "电子邮件", "身份证号", "部门", "职务", "生日", "毕业学校", "学历", "入职日期"], 
		colModel:[ 
					{name:'id',index:'id',width:120,hidden:true,align:"center"},
					{name:'name',index:'name',width:120,align:"center"},
					{name:'gender',index:'gender',width:120,align:"center"},
					{name:'officePhone',index:'office_phone',width:120,align:"center"},
					{name:'phone',index:'phone',width:120,align:"center"},
					{name:'email',index:'email',width:120,align:"center"},
					{name:'number',index:'number',width:120,hidden:true,align:"center"},
					{name:'department',index:'department',width:120,align:"center"},
					{name:'posi',index:'posi',width:120,align:"center"},
					{name:'birthday',index:'birthday',width:120,hidden:true,align:"center"},
					{name:'graduateSchool',index:'graduateSchool',width:120,hidden:true,align:"center"},
					{name:'educationalBackground',index:'educationalBackground',width:120,hidden:true,align:"center"},
					{name:'entryDate',index:'entryDate',width:120,hidden:true,align:"center"}
		         ],
		 width:mainComponent.mainPanelContentWidth,
		 height:mainComponent.mainPanelContentHeight,
		 rowNum:mainComponent.rowNum, 
		 rowList:[10,50,100],
		 pager: '#AddressBookPager', 
		 //sortname: 'id',
		 viewrecords: true,
		 //sortorder: "desc", 
		 caption:"员工通讯录",
//		 gridComplete:function() {
//			 AddressBookQueryGrid.createCodeField(["gender"],["gender"]);
//		 },
		 rownumbers: true
	}
);

jQuery("#AddressBookGrid").jqGrid('navGrid','#AddressBookPager',{edit:false,add:false,del:false});

AddressBookQueryGrid.setGrid(AddressBookGrid,["gender"],["gender"]);

//AddressBookQueryGrid.createDoubleClick();

AddressBookQueryGrid.afterPopup = function() {
	mainComponent.createCodeComboxBox();
};


function exportAddressBook()
{
	new DownloadComponent().createDownloadComponent("exportAddressBookForm","exportAddressBook","AddressBook.xls");
}

function importAddressBook()
{
	new UploadComponent().pupupUploadDialog("importAddressBook");
}


var clientColNames = ['ID',"序号", "联系人", "单位", "电子邮件", "电话", "网址", "地址", "国籍", "备注", "预留字段",
			          "salemanid","客户来源","创建时间","客户类型","报价单(有/无)","是否寄样","","","","业务员"];

var clientColModel = [ 
					   {name:'id',index:'id', width:100,hidden:true,align:"center"},	
					   {name:'number',index:'number',width:120,hidden:true,align:"center"},
					   {name:'contact',index:'contact',width:120,align:"center"},
					   {name:'unit',index:'unit',width:200,align:"center"},
					   {name:'email',index:'email',width:200,align:"center"},
					   {name:'phone',index:'phone',width:120,align:"center"},
					   {name:'web',index:'web',width:120,align:"center"},
					   {name:'address',index:'address',width:120,align:"center",hidden:true},
					   {name:'nationality',index:'nationality',width:80,align:"center"},
					   {name:'remark',index:'remark',width:80,align:"center"},
					   {name:'reserved',index:'reserved',width:120,hidden:true,align:"center"},
					   {name:'salemanid',index:'salemanid',width:120,hidden:true,align:"center"},
					   {name:'clientfrom',index:'clientfrom',width:120,align:"center"},
					   {name:'jointime',index:'jointime',width:120,align:"center"},
					   {name:'potential',index:'potential',width:120,align:"center"},
					   {name:'havequote',index:'havequote',width:120,align:"center",hidden:true},
					   {name:'mailingsample',index:'mailingsample',width:120,align:"center",hidden:true},
					   {name:'potentialreserved1',index:'potentialreserved1',width:120,align:"center",hidden:true},
					   {name:'potentialreserved2',index:'potentialreserved2',width:120,align:"center",hidden:true},
					   {name:'potentialreserved3',index:'potentialreserved3',width:120,align:"center",hidden:true},
					   {name:'saleman',index:'saleman',width:80,align:"center"}
			         ];

var clientColModelShort = [ 
					   {name:'id',index:'id', width:100,hidden:true,align:"center"},	
					   {name:'number',index:'number',width:120,hidden:true,align:"center"},
					   {name:'contact',index:'contact',width:120,align:"center"},
					   {name:'unit',index:'unit',width:200,align:"center"},
					   {name:'email',index:'email',width:200,align:"center"},
					   {name:'phone',index:'phone',width:120,align:"center"},
					   {name:'web',index:'web',width:120,align:"center",hidden:true},
					   {name:'address',index:'address',width:120,align:"center",hidden:true},
					   {name:'nationality',index:'nationality',width:80,align:"center",hidden:true},
					   {name:'remark',index:'remark',width:80,align:"center",hidden:true},
					   {name:'reserved',index:'reserved',width:120,hidden:true,align:"center"},
					   {name:'salemanid',index:'salemanid',width:120,hidden:true,align:"center"},
					   {name:'clientfrom',index:'clientfrom',width:120,align:"center",hidden:true},
					   {name:'jointime',index:'jointime',width:120,align:"center",hidden:true},
					   {name:'potential',index:'potential',width:120,align:"center",hidden:true},
					   {name:'havequote',index:'havequote',width:120,align:"center",hidden:true},
					   {name:'mailingsample',index:'mailingsample',width:120,align:"center",hidden:true},
					   {name:'potentialreserved1',index:'potentialreserved1',width:120,align:"center",hidden:true},
					   {name:'potentialreserved2',index:'potentialreserved2',width:120,align:"center",hidden:true},
					   {name:'potentialreserved3',index:'potentialreserved3',width:120,align:"center",hidden:true},
					   {name:'saleman',index:'saleman',width:80,align:"center"}
			         ];

var trackProgressFirstQuery = "";

var trackProgressClientId = null;
var trackProgressClientUnit = null;
var trackProgressClientName = null;
var queryTrackPUnit = '';
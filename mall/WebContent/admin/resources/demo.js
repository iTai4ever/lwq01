$(function(){
	$("#table").dataTable({
		"autoWidth":false,  //禁止自动计算宽度
		"paging":true,  //分页
		"orderin":false,
		"info":false,
		"searching":false,
		"dom":'<"#tool-container"><"top" t> <"bottom" lp>',
		"sPaginationType":"full_numbers",
		"ajax":{
			"url":"http://localhost:8080/mall/mgr/user/finduserlist.do",
			"type":"get",
			"data":{
	               flag: 1
	           }
		},
		"columns":[
		     {"data":"id"},
		     {"data":"account"},
		     {"data":"name"},
		     {"data":"sex"},
		     {"data":"age"},
		     {"data":"phone"},
		     {"data":"email"},
		],
		"oLanguage":{
			"oProcess":"正在加载数据......",
			"sLengthMenu":"每页显示_MENU_条记录",
			"sZeroRecords":"抱歉，没有找到",
			"sInfo":"从_START_到_END_/共_TOTAL_条记录",
			"sInfoEmpty":"没有数据",
			"sInfoFiltered":"(从_MAX_条数据中检索)",
			"sZeroRecords":"没有检索到数据",
			"sSearch":"模糊查询",
			"oPaginate":{
				"sFirst":"首页",
				"sPrevious":"前一页",
				"sNext":"后一页",
				"sLast":"尾页"
			}
		}
	});
});
define(['common'],function (common){
	//1.商品表格初始化
	var baseUrl = common.baseUrl;
	function initialProducts(){
		//找到位置引用datatable
		$("#product-table").dataTable({
			"autoWidth":false, //禁止自动计算宽度
			"paging":true,     //分页
			"ordering":false,  //禁止排序
			"info":false,      //禁止统计信息
			"searching":false, //取消搜索
			"dom":'<"#tool-container"><"top" t><"bottom" lp>',  //dom
			"aPaginationType":"full_numbers",
			"ajax":{
				"xhrFields":{withCredentials:true},
				"crossDomain":true,
				"url":baseUrl+"mgr/product/productlist.do",
			},
			"columns":[
			    {"data":"id"},  
			    {"data":"name"},
			    {"data":"price"},
			    {"data":"statusDesc"},
			    {"data":"hotStatus"},
			    {"data":null}	    
			 ],
			 columnDefs:[
			     //状态列
			     {
			    	 targets:3,
			    	 render:function(data,type,row,meta){
			    		 return statusRender(row);
			    	 }
			     },
			     //热销列
			     {
			    	 targets:4,
			    	 render:function(data,type,row,meta){
			    		 return hotRender(row);
			    	 }
			     },
			     //最后一列
			     {
			    	 targets:5,
			    	 render:function(data,type,row,meta){
			    		 return render(row);
			    	 }
			     }
			 ],
			 "oLanguage":{
			    	"oProcessing":"正在加载数据....",
			    	"sLengthMenu":"每页显示_MENU_条记录",
			    	"sZeroRecords":"抱歉没有找到",
			    	"sInfo":"从_START_到_END_/共_TOTAL_条记录",
			    	"sInfoEmpty":"没有数据",
			    	"sInforFiltered":"从_MAX_条数据中检索",
			    	"sSearch":"模糊查询",
			    	"oPaginate":{
			    		"sFirst":"首页",
			    		"sPrevious":"前一页",
			    		"sNext":"后一页",
			    		"sLast":"尾页"  			
			    	}
			 }
			 
		});
	}
	//2.新增商品按钮和查询框
	function addBtn(){
		var str = '<label for="goodsId" class="control-label" style="margin-right:20px;">商品名称：</label>'
			+'<input type="text" class="from-control" id="goodsName" placeholder="前请输入商品名称" style="margin-right:15px">'
			+'<a class="btn btn-primary" href="javascript:void(0);" id="btn-search-product">查询</a>';
		$("#tool-container").addClass("clearfix");
		$("#tool-container").html(str);
	}
	//3.查询商品信息
	function selectProductInfo(){
		//添加一个点击事件
		$("#btn-search-product").click(function(){
			//创建一个table对象
			var table=$("#product-table").dataTable();
			//清空数据
			table.fnClearTable();
			//重新加载数据
			table.fnReloadAjax(baseUrl+"mgr/product/productlist.do?name="+$("#goodsName").val());
		})
		
	}

	return {
		initialProducts:initialProducts,
		addBtn:addBtn,
		selectProductInfo:selectProductInfo
	};
});

var baseUrl ="http://192.168.137.1:8080/mall/";
//渲染状态列
function statusRender(data){
	//商品为新增或下架时，按钮未上架，反之为下架
	var content = data.statusDesc;
	if(data.status==1||data.status==3){
		content+='&nbsp;&nbsp;<a class="btn btn-primary" href="javascript:void(0);" onclick=\"changeStatus('+data.id+',2)\">上架</a>';
	}else if(data.status==2){
		content+='&nbsp;&nbsp;<a class="btn btn-primary" href="javascript:void(0);" onclick=\"changeStatus('+data.id+',3)\">下架</a>';
	}
	return content;
}
//渲染热销列
function hotRender(data){
	//1是处于热销状态，2是非热销
	var content = data.hotStatus;
	if(data.hot==1){
		content+='&nbsp;&nbsp;<a class="btn btn-primary" href="javascript:void(0);" onclick=\"changeHotStatus('+data.id+','+data.status+',2)\">非热销</a>';
	}else if(data.hot==2){
		content+='&nbsp;&nbsp;<a class="btn btn-primary" href="javascript:void(0);" onclick=\"changeHotStatus('+data.id+','+data.status+',1)\">热销</a>';
	}
	return content;
}
//渲染操作列
function render(row){
	var str='<a class="button" href="javascript:void(0);" onclick="queryDetail(\''+row.id+'\')">编辑</a>';
	return str;
}

//查看详情
function queryDetail(id){
	$(window).attr("location","product_edit.html?productId="+id);
}

//更改商品状态
function changeStatus  (pid, newStatus) {
	$.ajax({
		"xhrFields":{withCredentials:true},
		"crossDomain":true,
		"url":baseUrl+"mgr/product/setstatus.do",
		data:{"productId":pid,"status":newStatus,"hot":-1},
		success:function(rs){
			if(rs.status==0){
				//成功，重新加载数据
				var table=$("#product-table").DataTable();
				table.ajax.reload();
			}else{
				//失败，返回错误信息
				alert(rs.msg);
			}

		}
	});
};

//更改热销状态
function changeHotStatus(pid,status,newStatus){
	//判断商品是否上架。未上架不允许热销
	if(status!=2){
		alert("只有在售商品才能设为热销，请更改状态为在售！");
		return ;
	}
	$.ajax({
		"xhrFields":{withCredentials:true},
		"crossDomain":true,
		"url":baseUrl+"mgr/product/setstatus.do",
		data:{"productId":pid,"status":-1,"hot":newStatus},
		success:function(rs){
			if(rs.status==0){
				//成功，重新加载数据
				var table=$("#product-table").DataTable();
				table.ajax.reload();
			}else{
				//失败，返回错误信息
				alert(rs.msg);
			}

		}
	});
}










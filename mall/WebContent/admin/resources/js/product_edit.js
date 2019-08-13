var initImages;//存放原来的图片
var baseUrl ="http://192.168.137.1:8080/mall/";
define(['common'],function (common) {
	function getType() {
		//初始化富文本编辑器
		initEditor();
		//获取商品id
		var pid = common.getParam("productId");
		// console.log(pid);
		//获取产品类型参数和产品信息
		$.ajax({
			"xhrFields":{withCredentials:true},
			"crossDomain":true,
			"url":baseUrl+"mgr/param/findptype.do",
			data:{productId:pid},
			success:function(rs){
				//插入数据
				if(rs.status==0){
					//原始位置清空
					$("#productType").html("");
				// }else{
					//添加数据
					var tpl = $("#param_item_tpl").html();
					// console.log(tpl);
					var func = Handlebars.compile(tpl);
					var result = func(rs.data);
					console.log(rs.data);
					$("#productType").html(result);
					$("#productType").prepend("<option selected value='-1'>请选择产品类型</option>");
					//加载商品详情
					loadProductDetail(pid);

				}

			}
		});

	}

	//为产品下拉绑定事件
	function dropDownEvent() {
		$("#productType").change((function () {
			//获取产品类型值
			var val = $("#productType").val();
			//判断 存在产品类型显示配件类型
			if(val!=-1){
				//加载产品类型
				// $("#parts-type-container").css({"display":"block"});
				loadPartsType(val,false);
			}else{
				// $("#parts-type-container").css({"display":"none"});
			}
		}));
	}

	//保存商品信息
	function btnSave() {
		$("#btn-save").click(function () {
			var goodsId = $("#goodsId").val();
			var goodsName = $("#goodsName").val();
			var productType = $("#productType").val();
			var partsType = $("#partsType").val();
			var goodsPrice = $("#goodsPrice").val();
			var goodsStock = $("#goodsStock").val();
			var images = $("#images").val();
			if(images.length>initImages.length){//长度相同，说明没有上传图片
				images = images.substring(initImages.length+1);
			}
			var detail = editor.getValue();
			$.ajax({
				"xhrFields":{withCredentials:true},
				"crossDomain":true,
				"url":baseUrl+"mgr/product/saveproduct.do",
				type:"post",
				data:{"id":goodsId,"name":goodsName,"productId":productType,"partsId":partsType,
					"detail":detail,"price":goodsPrice,"stock":goodsStock,"subImages":images},
				success:function(rs){
					//判断成功与否
					if(rs.status==0){
						//跳转页面
						alert("修改商品成功！");
						$(window).attr("location","index.html");
					}else{
						alert(rs.msg);
					}
				}
			});
		})

	}

	return{
		getType:getType,
		dropDownEvent:dropDownEvent,
		btnSave:btnSave

	};
});

//加载商品详情
function loadProductDetail(pid) {
	$.ajax({
		"xhrFields":{withCredentials:true},
		"crossDomain":true,
		"url":baseUrl+"product/getdetail.do",
		data:{"id":pid},
		type:"post",
		success:function(rs){
			//判断是否成功
			if(rs.status==0){
				//商品信息
				$("#goodsId").val(rs.data.id);
				$("#goodsName").val(rs.data.name);
				$("#goodsId").val(rs.data.id);
				$("#partsType").val("data-id",rs.data.partsId);
				$("#goodsPrice").val(rs.data.price);
				$("#goodsStock").val(rs.data.iconUrl+"rs.data.subImaes");//主图和子图地址链接在一起
				initImages=$("#images").val();//保存初始地址
				editor.setValue(rs.data.detail);
				//加载配件类型
				loadPartsType(rs.data.productId,true);
			}else{
				alert(rs.msg);
			}
		}
	});
}

//根据产品类型加载配件类型 是否初始化调用
function loadPartsType(productTypeId,isInit) {
    console.log(productTypeId);
	$.ajax({
		"xhrFields":{withCredentials:true},
		"crossDomain":true,
		"url":baseUrl+"mgr/param/findpartstype.do",
		data:{"id":productTypeId},
		type:"post",
		success:function(rs){
			//插入数据
			if(rs.status==0){
				//原始位置清空
				// $("#partsType").html("");
				// //添加数据
				// var tpl = $("#param_item_tpl").html();
                // console.log(tpl);
				// var func = Handlebars.complie(tpl);
				// var result = func(rs.data);
				// $("#partsType").html(result);
				if(isInit){
					// $("#parts-type-container").css({"display":"block"});
					$("#partsType").val($("#partsType").attr("data-id"));
				}

		    }

		}
	});
}

//初始化编辑器
function initEditor() {
	var $preview,mobileToolbar,toolbar;
	//Simditor.locale = 'zh-CN';
	toolbar = ['title','bold','italic','underline','strikethrough',
	         'color','|','ol','ul','blockquote','code','table','|',
		     'link','image','hr'];
	Simditor.local = 'zh-CN';//设置中文
	editor = new Simditor({
		textarea:$('#txt-content'),
		placeholder:'这里输入文字...',
		toolbar:toolbar,
		pasteImage:true,
		defaultImage:'resources/simditor/assets/images/image.png',
		upload:{
			url:baseUrl+'mgr/product/pic_upload.do',
			xhrFields:{withCredentials:true},
			crossDomain:true,
			params:null,//键值对，指定文件上传接口的额外参数，上传时随文件一起提交
			fileKey:'files',//服务器端获取文件参数的参数名
			connectionCount:3,
			leaveConfirm:'正在上传文件',
		},
		success:function (data) {
			alert(data);
		}
	});
}


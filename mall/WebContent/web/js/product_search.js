function getParam(name) {
    //构造一个含有目标参数的正则表达式对象
    var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
    //    匹配目标参数
    var r = window.location.search.substr(1).match(reg);
    //    返回参数值
    if (r != null) return decodeURI(r[2]);
    return null;
}

//储存产品类型参数
var paramData = new Array();
//输入搜索内容
var conditon = getParam("name");
//产品类型编号
var productTypeId = getParam("parentId");
//配件类型编号
var partsTypeId = getParam("partsId");
if (partsTypeId == null) {
    partsTypeId = '';
}

//加载获取所有产品类型

$(function ready() {
    $.ajax({
        //url: "http://192.168.137.1:8080/mall/param/findallparams.do",
        //url: "http://localhost:8080/mall/param/findallparams.do",
        url:"http://mall-01.test.app.yyuap.com/param/findallparams.do",
        xhrFields: {withCredentials: true},//允许跨域请求携带cookie数据
        crossDomain: true,//跨域请求
        success: function (rs) {
            console.log(rs);
            //将json格式数据转化为数组,参数编号为键，对象作为值
            $.each(rs.data, function (index, value) {
                paramData[value.id] = value;
            });
            //加载产品分类
            var tpl = $("#product_type_list_tpl").html();
            var func = Handlebars.compile(tpl);
            $("#productTypeContainer").html(func(rs.data));
            //设置选中产品分类
            $("#productTypeContainer li a").each(function (i, obj) {
                //清空原有选中
                $(obj).removeClass("selected");
                //添加现有选中
                if ($(obj).attr("product-type-id") == productTypeId) {
                    $(obj).addClass("selected");

                }
            });

            //设置选中配件类型分类
            setPartsType(productTypeId, partsTypeId);

            //产品分类设置点击事件
            $("#productTypeContainer li a").click(
                function () {
                    //清空选定
                    $("#productTypeContainer li a").removeClass("selected");
                    //设置选定
                    $(this).addClass("selected");
                    var ptid = $(this).attr("product-type-id");
                    //查询配件商品
                    setPartsType(ptid);

                    //查询产品
                    findProducts(ptid,'');
                }
            )
        }
    });
    //2、查询数据
    findProducts(productTypeId,partsTypeId,conditon);
    //单击搜索
    // $("#searchBtn").click(function () {
    //     var proName = $("#keyword").val();
    //     console.log(proName);
    //     $(window).attr("location","product_search.html?name="+proName);
    // });


});

function findProducts(productTypeId,partsTypeId,condition,pageNum,pageSize) {
    //判断页码
    if (pageNum==undefined || pageNum==''){
        pageNum=1;

    }
    //判断pageSize
    if (pageSize==undefined || pageSize==''){
        pageSize=20;
    }
    //产品类型
    if (productTypeId==undefined || productTypeId==''){
        productTypeId=0;
    }
    //配件类型
    if (partsTypeId==undefined || partsTypeId==''){
        partsTypeId=0;
    }
    //搜索条件
    if (condition==null){condition=="";}
    //向服务器发送请求查询数据
    $.ajax({
        //url: "http://192.168.137.1:8080/mall/product/findproducts.do",
        //url: "http://localhost:8080/mall/product/findproducts.do",
        url:"http://mall-01.test.app.yyuap.com/product/findproducts.do",
        xhrFields: {withCredentials: true},//允许跨域请求携带cookie数据
        crossDomain: true,//跨域请求
        type:"post",
        data:{'productTypeId':productTypeId,'partsId':partsTypeId,'name':condition,'pageNum':pageNum,'pageSize':pageSize},
        success: function (rs) {
            console.log(rs);
            //设定图片地址
            // for (var i = 0;i<rs.data.data.length;i++){
            //     rs.data.data[i].iconUrl ="http://192.168.137.1:8080" + rs.data.data[i].iconUrl;
            // }
            //获取数据后插入页面
            if(rs.data.data.length == 0) {
                var str1 = '<p>&nbsp;&nbsp;&nbsp;没有找到相关的商品</p>';
                $(".probox").html(str1);
            }else{
            var tpl = $("#products_list_tpl").html();
            var func = Handlebars.compile(tpl);
            $(".probox").html(func(rs.data.data));
            //处理分页显示
            $(".btn_prev").attr("data-page", rs.data.prePage);
            $(".btn_next").attr("data-page", rs.data.nextPage);
            $(".page_num").attr("data-page-num", rs.data.pageNum);
            $(".page_num").html(rs.data.pageNum);
            $(".page_count").html("共" + rs.data.totalPage + "页");

            //绑定分页点击事件
            $(".btn_prev").click(function () {
                var num = $(".btn_prev").attr("data-page");
                findProducts(productTypeId, partsTypeId, condition, num, pageSize);
            });
            $(".btn_next").click(function () {
                var num = $(".btn_next").attr("data-page");
                findProducts(productTypeId, partsTypeId, condition, num, pageSize);
            });
        }
        }
    });
}

//查询配件商品
function setPartsType(productTypeId,partsTypeId){
  //设置默认选中的分类
  var partsData = paramData[productTypeId];
  var tpl = $("#parts_type_list_tpl").html();
  var func = Handlebars.compile(tpl);
  $("#parts_type_container").html(func(partsData));
  //设置选中时的配件类型分类
  $("#parts_type_container").find("a").each(function(i,obj){
    $(obj).removeClass("selected");
    if($(obj).attr("product-type-id")==partsTypeId){
      $(obj).addClass("selected");
    }
  });
  //删除最后一个分割线
  $("#parts_type_container .listline:last").remove();
  //点击事件查询
  $("#parts_type_container").find("a").click(function(){
    $("#parts_type_container").find("a").removeClass("selected");
    $(this).addClass("selected");
    var productTid = $(this).attr("product-type-id");
    var partsTid = $(this).attr("parts-type-id");
    findProducts(productTid,partsTid);
  });
}

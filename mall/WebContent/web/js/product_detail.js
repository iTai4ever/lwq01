//读取传递过来的商品编号
function getParam(name) {
    //构造一个含有目标参数的正则表达式对象
    var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    console.log(unescape(r[2]));
    if(r!=null)return  unescape(r[2]); return null;
}
var pid = getParam("pid");
// $(function distpicker() {
//         $("#distpicker2").distpicker("destroy");
//         $("#distpicker2").distpicker({
//             autoSelect: false
//         });
//     }
// );
//获取商品数据
$(function ready(){
  $.ajax({
      //url: "http://192.168.137.1:8080/mall/product/getdetail.do",
      //url: "http://localhost:8080/mall/product/getdetail.do",
      url:"http://mall-01.test.app.yyuap.com/product/getdetail.do",
      type:"POST",
      xhrFields: {withCredentials: true},//允许跨域请求携带cookie数据
      crossDomain: true,//跨域请求
      data:{'id':pid},
      success: function (result) {
          console.log(result.data);
        //数据加载成功时
        if(result.status == 0){
          //产品名称
          $("#product_name_container").html(result.data.name);
          //将产品id作为属性添加到product_name_container中
          $("#product_name_container").attr("data-id",result.data.id);
          //产品价格
          $("#productPriceContainer").html(result.data.price);
          //添加主图
          // $("#productMainImage").attr("src","http://192.168.137.1:8080/mall/"+result.data.iconUrl);
          $("#productMainImage").attr("src",result.data.iconUrl);
          $("#productMainImage").addClass(".product_picture_img");
          //详情
          $("#detailContainer").html(result.data.detail);
          //规格参数
          $("#specParamContainer").html(result.data.specParam);
          //产品库存
          $("#product_num").attr("data-stock",result.data.stock);
          $("#stock_container").html("库存:"+result.data.stock);
          //商品子图
          var subimages = result.data.subImages;
          subimages = subimages.substring(0,subimages.length);
          //切割
          var images = subimages.split(",");
          var small_item = "";
          for(var i=0;i<images.length;i++){
            small_item = "<li><img src=\'";
            // images[i] = "http://192.168.137.1:8080/mall/" + images[i];
            small_item += images[i];
            small_item += "\'><li>";
              //将小图插入页面
              $("#piclist_container").html();
              $("#piclist_container").append(small_item);
          }

        }else{
            //数据加载失败
        }

      }
  });

    //购买数量增加
    $(".product_plus_1").click(function () {
        //获取库存
        var stock = $("#product_num").attr("data-stock");
        var num = $("#product_num").val();
        //点击增加
        num = parseInt(num)+1;
        if(num >= stock){
            num = stock;
        }
        $("#product_num").val(num);
    });

    //购买数量减少
    $(".product_minus_2").click(function () {
        //获取库存
        var stock = $("#product_num").attr("data-stock");
        var num = $("#product_num").val();
        //点击减少
        num = parseInt(num)-1;
        if(num <= 1){
            num = 1;
        }
        $("#product_num").val(num);
    });

    //加入购物车
    $("#addCart").click(function () {
        //验证数量是否符合规范
        var count = $("#product_num").val();
        if(count <= 0){
            alert("请填写正确的购买数量");
            return;
        }
        //请求服务器加入购物车
        $.ajax({
            //url: "http://192.168.137.1:8080/mall/cart/savecart.do",
            //url: "http://localhost:8080/mall/cart/savecart.do",
            url:"http://mall-01.test.app.yyuap.com/cart/savecart.do",
            type:"POST",
            xhrFields: {withCredentials: true},//允许跨域请求携带cookie数据
            crossDomain: true,//跨域请求
            data:{'productId':pid,'count':count},
            success: function (rs) {
                //判断是否成功
                if (rs.status==0){
                    //弹出提示消息
                    alert(rs.msg);
                    location.reload();
                }else{
                    alert(rs.msg);
                    $(window).attr('location','login.html');
                }
            }
        });
    });

    //商品小图切大图
    $(".product_picture_table_main ul li img").live("click",function () {
       //去掉其他图片的选中样式
        $(".product_picture_table_main ul li img").removeClass("product_picture_selected");
        $(this).addClass("product_picture_selected");
        //将小图放到主图位置
        var imgSrc = $(this).attr("src");
        $(".product_picture_img").attr("src",imgSrc);
    });

    // $("#searchBtn").click(function () {
    //     var proName = $("#keyword").val();
    //     console.log(proName);
    //     $(window).attr("location","product_search.html?name="+proName);
    // });
});


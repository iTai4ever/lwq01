$(document).ready(init());

function init() {
    getCartInfo();
    //绑定删除事件
    $(document).on('click','.delete',function(){
        var pid=$(this).attr("productId");
        $.ajax({
            url:commonURL+"cart/deletecarts.do",
            type:"post",
            data:{'productId':pid},
            xhrFields:{withCredentials:true},
            crossDomain:true,
            async:false,
            success:function(res){
                if(res.status===0){//请求执行成功
                    //更新页面
                    updatePageInfo(res);
                }
                else{//请求失败
                    alert(res.msg);
                    // $(window).attr("location","../cn/login.html");
                    $(window).parent().location.href="../cn/login.html";
                }
            }
        });
    });

    //清空购物车事件绑定
    $(document).on('click','#clear',function () {
        $.ajax({
            url:commonURL+"cart/clearcarts.do",
            type:"get",
            xhrFields:{withCredentials:true},
            crossDomain:true,
            success:function(res){
                if(res.status===0){//请求执行成功
                    $(window).attr("location","../cn/emptyCart.html");
                }
                else{//请求失败
                    alert(res.msg);
                }
            }
        });
    });

    //更新购物车数量，加、减事件绑定
    $(document).on("click",".minus_btn",function(){
        var quantity=$(this).next().val();
        quantity=parseInt(quantity)-1;
        if(quantity<=0){
            $(this).next().val(1);
            alert("商品数量不能小于1");
            return;
        }else{
            //更新数据
            var pid=$(this).attr("productId");
            updateCart(pid,quantity);
            getCartInfo();
        }
    });

    $(document).on('click','.plus_btn',function(){
        var quantity=$(this).prev().val();
        var stock=$(this).attr("stock");
        quantity=parseInt(quantity)+1;
        if(quantity>stock){
            $(this).prev().val(stock);
            alert("商品数量不能大于库存量");
            return;
        }else {
            //更新数据
            var pid = $(this).attr("productId");
            updateCart(pid, quantity);
            getCartInfo();
        }
    });

    //提交订单事件
    $(document).on('click','#submit',function () {
        if($("#total_price").text() ==="0"){
            alert("订单商品个数为零");
            return;
        }else{
            $(window).attr("location","deal_confirmation.html");
        }
    });

    //全选按钮
    $(document).on('click','#all',function () {
        //检测是否全部选中
        if($(this).prop("checked")){
            $(".all_check_box").each(function(i,obj){
                $(this).prop("checked",true);
                var productId=$(this).attr("productId");
                var quantity=$("#"+productId).val();
                updateCart(productId,quantity,1);
            });
        }else{
            //更新购物车信息
            $(".all_check_box").each(function (i,obj) {
                $(this).prop("checked",false);
                var productId=$(this).attr("productId");
                var quantity=$("#"+productId).val();
                updateCart(productId,quantity,0);
            });
        }
        //获取购物车信息
        getCartInfo();
    });

    //反选
    $(document).on('click','.all_check_box',function () {
        checkList();
        var checked;
        if($(this).prop("checked")){
            checked=1;
        }
        else
            checked=0;

        var productId=$(this).attr("productId");
        var quantity=$("#"+productId).val();
        updateCart(productId,quantity,checked);

    });

}

//读取购物车信息
function getCartInfo() {
    $.ajax({
        url:commonURL+"cart/findallcarts.do",
        type:"get",
        xhrFields:{withCredentials:true},
        crossDomain:true,
        async:false,
        success:function(res){
            if(res.status===0){//请求执行成功
                updatePageInfo(res);
            }
            else{//请求失败
                alert(res.msg);
                $(window).attr("location","../cn/login.html");
            }
        }
    });
}

//更新页面信息
function updatePageInfo(res) {
    if(res.data.lists.length===0){//购物车为空
        //显示为空的界面
        $(window).attr("location","../cn/emptyCart.html");
    }else{
        $(".cart_container").css({display:"block"});
        //更新购物车列表
        var tpl=$("#goodsItems").html();
        var fun=Handlebars.compile(tpl);
        var result=fun(res.data.lists);
        $("#goods_list").html(result);
        //更新购物车商品价格
        $("#total_price").html(res.data.totalPrice);
        //更新复选框状态
        for(var i=0;i<res.data.lists.length;i++){
            if(res.data.lists[i].checked===1){
                $(".pro00>input").get(i).checked=true;
            }
        }
        //判断是否反选
        checkList();
    }
}

//更新购物车信息
function updateCart(productId, quantity,checked) {
    $.ajax({
        url:commonURL+"cart/updatecarts.do",
        type:"get",
        data:{'productId':productId,'count':quantity,'checked':checked},
        xhrFields:{withCredentials:true},
        crossDomain:true,
        async:false,
        success:function(res){
            if(res.status===0){//请求执行成功
                updatePageInfo(res);
            }
            else{//请求失败
                alert(res.msg);
            }
        }
    });
}
//反选方法
function checkList() {
    var one=$(".all_check_box");
    var num=0;
    for (var i=0;i<one.length;i++){
        if(one[i].checked)
            num++;
    }
    if(num===one.length)
        $("#all").attr("checked",true);
    else
        $("#all").attr("checked",false);
}

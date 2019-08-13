var url;
var param = "";
$(document).ready(function(){
    $(".function_list>li").click(function () {
        var iframe=$("iframe");
        $(iframe).attr("src",$(this).attr("id"));
        $(".select").removeClass();
        $(this).addClass("select");
    });
    $(".account_info_list>li").click(function () {
        var iframe=$("iframe");
        $(iframe).attr("src",$(this).attr("id"));
        $(".select").removeClass();
        $(this).addClass("select");
    });

    getUrlParam();
    //判断搜索跳转、购物车跳转、个人信息跳转三种情况
    if(parseInt(param)) {
        if(param.length>1){
            param=decodeURI(param);
            $("#keyword").val(param);
            $(".searchbtn").click();
        }
        $("." + param).click();
    }
    else{
        param=decodeURI(param);
        $("#keyword").val(param);
        $(".searchbtn").click();
    }
});

//获取URL参数
function getUrlParam() {
    //获取URL中的参数orderNo
    url = window.location
    param = url.toString().split("?");
    param = param[1].split("=");
    param = param[1];
}

//加载登录用户信息
$(function getUserInfo() {
    //    向服务器请求数据
    $.ajax({
        url: commonURL + "mgr/user/getuserinfo.do",
        xhrFields: {withCredentials: true},//允许跨域请求携带cookie数据
        crossDomain: true,//跨域请求
        success: function (user) {
            //判断请求是否成功
            if (user.status == 0) {
                //隐藏登陆时的span标签
                $("#register-info").css({
                    display: "none"
                });
                //显示登录后的span标签
                $("#login-info").css(
                    {display: "block"}
                );
                //添加用户名
                $("#headerUsername").html(user.data.account);
                $("#account_name").html(user.data.account);
                //获取显示用户购物车商品数量
                // getCartCount();
            }
        }
    });
});

//获取购物车商品数量
$(function getCartCount() {
    $.ajax({
        url: commonURL + "cart/getcartcount.do",
        xhrFields: {withCredentials: true},//允许跨域请求携带cookie数据
        crossDomain: true,//跨域请求
        success: function (rs) {
            if (rs.status == 0) {
                //    插入数据
                $("#cartQuantity").html("[" + rs.data + "]");
            }

        }
    });
});

//用户登出
$(function logout() {
    //       给退出按钮挂上单击事件
    $("#headerLogout").click(function () {
        //向服务器请求数据
        $.ajax({
            url: commonURL + "mgr/user/do_logout.do",
            xhrFields: {withCredentials: true},//允许跨域请求携带cookie数据
            crossDomain: true,//跨域请求
            success: function (rs) {
                if (rs.status == 0) {
                    //显示登陆时的span标签
                    $("#register-info").css({
                        display: "block"
                    });
                    //隐藏登录后的span标签
                    $("#login-info").css(
                        {display: "none"}
                    );
                    //    清空购物车数量
                    $("#cartQuantity").html("[0]");


                }
            }
        });
    });
});

function search() {
    var proName = $("#keyword").val();
    // console.log(proName);
    $("iframe").attr("src","product_search.html?name="+proName);
}

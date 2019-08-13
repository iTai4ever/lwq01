//var baseUrl = "http://192.168.137.1:8080/mall/";
//var baseUrl = "http://localhost:8080/mall/";
var baseUrl = "http://mall-01.test.app.yyuap.com/";
define(function () {
    //获取url中的参数
    function getParam(name) {
        //构造一个含有目标参数的正则表达式对象
        var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
        //    匹配目标参数
        var r = window.location.search.substr(1).match(reg);
        //    返回参数值
        if (r != null) return decodeURI(r[2]);
        return null;
    }

    //获取用户信息
    function getUserInfo() {
        $.ajax({
            url: baseUrl + "mgr/user/getuserinfo.do",
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
                    $("#headerUsername").html(user.data.account+"&nbsp;");
                    //获取显示用户购物车商品数量
                    getCartCount();
                }
            }
        });
    }

    //获取购物车商品数量
    function getCartCount() {
        $.ajax({
            url: baseUrl+"cart/getcartcount.do",
            xhrFields: {withCredentials: true},//允许跨域请求携带cookie数据
            crossDomain: true,//跨域请求
            success: function (rs) {
                if (rs.status == 0) {
                    //    插入数据
                    $("#cartQuantity").html("[" + rs.data + "]");
                }

            }
        });
    }

    //用户登出
    function logout() {
        //给退出按钮加上单击事件
        $("#headerLogout").click(function () {
            //向服务器请求数据
            $.ajax({
                url: baseUrl + "mgr/user/do_logout.do",
                xhrFields: {withCredentials: true},//允许跨域请求携带cookie数据
                crossDomain: true,//跨域请求
                success: function (rs) {
                    if (rs.status == 0) {
                        //显示登陆时的span标签
                        $("#register_info").css({
                            display: "block"
                        });
                        //隐藏登录后的span标签
                        $("#login_info").css(
                            {display: "none"}
                        );
                        //    清空购物车数量
                        $("#cartQuantity").html("[0]");


                    }
                }
            });
        });
    }


    return{
        getParam:getParam,
        getUserInfo:getUserInfo,
        getCartCount:getCartCount,
        logout:logout
    };
});

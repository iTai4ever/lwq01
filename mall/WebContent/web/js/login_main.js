require.config({
    baseUrl:"../js",
    path:{
        "jquery":"../js/jquery-3.3.1.min.js"
    },
    shim:{

    }
    }
);

require(['jquery-3.3.1.min','login'],function (jquery,login) {
     $(function () {
         //失去光标时验证用户名
         login.accountCheck();
         //失去光标时验证密码
         login.pwdCheck();
         //验证登陆
         login.loginBtn();
     });


});
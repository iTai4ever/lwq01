require.config({
    baseUrl: "../js",
    path: {
        "jquery": "../js/jquery-3.3.1.min.js"
    },
    shim: {}
});

require(['jquery','common1','password_modification'],function (jquery, common1, password_modification) {
    $(function () {
        //加载用户的登陆信息
        common1.getUserInfo();
        //用户登出
        common1.logout();
        //验证及保存
        password_modification.ready();
    });
});
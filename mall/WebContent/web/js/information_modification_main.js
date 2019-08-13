require.config({
    baseUrl: "../js",
    path: {
        "jquery": "../js/jquery-3.3.1.min.js"
    },
    shim: {}
});

require(['jquery','common1','information_modification'],function (jquery, common1, information_modification) {
    $(function () {
        //加载用户登陆信息
        common1.getUserInfo();
        //用户登录
        common1.logout();
        //修改信息
        information_modification.ready();
    });
});
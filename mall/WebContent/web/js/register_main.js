require.config({
    baseUrl: "../js",
    path: {
        "jquery": "../js/jquery-3.3.1.min.js"
    },
    shim: {}
});

require(['jquery','register'],function (jquery, register) {
    $(function () {
        register.registerBtn();
    });
});
require.config({
    baseUrl: "../js",
    path: {
        "jquery": "../js/jquery-3.3.1.min.js"
    },
    shim: {}
});

require(['jquery','common1','password_back'],function (jquery, common1, password_back) {

});
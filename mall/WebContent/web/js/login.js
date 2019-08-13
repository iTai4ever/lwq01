define(['jquery-3.3.1.min'], function (jquery) {
    //两个变量
    var isAccountValidate=false;
    var isPasswordValidate=false;
    //失去光标时验证登录名
    function accountCheck() {
        $("#username").blur(function () {
            isAccountValidate= checkAccount();
        });
    }
    //失去光标时验证密码
    function pwdCheck() {
        $("#password").blur(function () {
            isPasswordValidate= checkPwd();
        })
    }
    // 登陆
    function loginBtn() {
        //创建单机事件
        $(".login_btn").click(function () {
            //判断验证信息
            if (!isAccountValidate){
                return checkAccount();
            }
            if (!isPasswordValidate){
                return checkPwd();
            }
            //成功进入接口登陆
            $.ajax({
                //url:"http://192.168.137.1:8080/mall/mgr/user/login.do",
                //url:"http://localhost:8080/mall/mgr/user/login.do",
                url:"http://mall-01.test.app.yyuap.com/mgr/user/login.do",
                type:"POST",
                data:{account:$("#username").val(),password:$("#password").val()},
                // data:{'account':'001','password':'222'},
                xhrFields:{withCredentials:true},
                crossDomain:true,
                // error:function(obj,msg,erroObj){
                //    console.log(msg);
                // },
                success:function (data) {
                    // $(window).attr("location","register.html");
                    // console.log(data.msg);
                    //判断是否登陆成功
                    if(data.status=="0"){
                        //成功判断是否是管理员
                         if (data.data.role == "2"){
                             $(window).attr("location","password_back.html");
                         }
                         else{
                             $(window).attr("location","index.html");
                         }
                    }
                    else{
                        $("#passwordError").css({display:"inline-block"});
                        $("#passwordError").html("用户名或密码错误");
                    }
                }
            });
        });

    }

    return{
        accountCheck:accountCheck,
        pwdCheck:pwdCheck,
        loginBtn:loginBtn,
    };

    //检查用户名
    function checkAccount() {
        //获取用户名
        var account = $("#username").val();
        // $("#usernameError").css({display:"none"});
        $("#usernameError").html('');
        if (account == ""){
            $("#usernameError").css({display:"inline-block"});
            $("#usernameError").html("用户名不能为空！");
            return false;
        }
        return true;
    }
    //检查密码
    function checkPwd() {
        //获取输入密码
        var pwd = $("#password").val();
        // $("#passwordError").css({display:"none"});
        $("#passwordError").html('');
        if (pwd == ""){
            $("#passwordError").css({display:"inline-block"});
            $("#passwordError").html("密码不能为空！");
            return false;
        }
        return true;}
});

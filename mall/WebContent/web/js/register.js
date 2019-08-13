define(['jquery','common1'], function (jquery, common1) {
    //用户名是否为空
    var isUserNameValidate = false;
    //密码是否为空
    var isUserPwdValidate = false;
    //确认密码是否为空
    var isUserRePwdValidate = false;
    //用户输入密码是否有效
    var isPwdValidate = false;
    //用户电子邮箱是否有效
    var isEmailValidate = false;
    //用户手机号是否有效
    var isPhoneValidate = false;
    //密码提示问题是否有效
    var isQuestionValidate = false;
    //密码提示回答是否有效
    var isAnswerValidate = false;

    //1.当用户名文本失去光标时开始验证
    $("#username").blur(function () {
        isUserNameValidate = checkUserName("username");
    });
    //验证用户名
    function checkUserName(objID) {
        //创建对象获取输入框内容
        var userName = $("#"+objID).val();
        $("#usernameError").css({display:"none"});
        //验证用户名是否为空
        if(userName == ""){
            $("#usernameError").html("请输入用户名！");
            $("#usernameError").css({display: "block"});
            return false;
        }
        //验证用户名长度是否错误
        if(userName.length<3 || userName.length>16){
            $("#usernameError").html("用户名长度错误！");
            $("#usernameError").css({display:"block"});
            return false;
        }
        //用户名内容是否规范
        var reg = /^[0-9a-zA-Z]+$/;
        var str = document.getElementById("username").value;
        if(!reg.test(str)){
            $("#usernameError").html("用户名只能为数字和英文！");
            $("#usernameError").css({display:"block"});
            return false;
        }
        //请求服务器验证用户名是否已经存在 同步请求
        var flag = false;
        $.ajax({
            url:commonURL+"mgr/user/do_check_info.do?number="+Math.random(),
            type:"post",
            data:{info:userName,type: "account"},
            async:false,
            success:function (rs) {
                // alert("666");
                //判断是否成功,成功即为有问题
                if(rs.status==1){
                    //显示错误信息
                    $("#usernameError").css({display:"block"});
                    //错误信息添加
                    $("#usernameError").html(data.msg);
                }
                else{
                    //隐藏错误信息
                    $("#usernameError").css({display:"none"});
                    flag = true;
                }
            }
        });
        return flag;

    }

    //2.密码是否为空
    $("#password").blur(function () {
       isUserPwdValidate = checkUserPwd("password");
       //确认密码经过校验之后，需要校验两者是否相同
        if(isUserRePwdValidate){
            isPwdValidate = checkPwdAndRePwd("password","rePassword");
        }
    });
    //检查密码是否为空
    function checkUserPwd(objID) {
        //创建对象输入框内容
        var pwd = $("#"+objID).val();
        $("#userPasswordError").css({display:"none"});
        //校验密码是否为空
        if(pwd == ""){
            $("#userPasswordError").html("请输入密码！");
            $("#userPasswordError").css({display: "block"});
            return false;
        }
        //验证密码长度是否错误
        if(pwd.length<6 || pwd.length>12){
            $("#userPasswordError").html("密码长度为6-12位！");
            $("#userPasswordError").css({display:"block"});
            return false;
        }
        //密码内容是否规范
        var reg = /^[0-9a-zA-Z]+$/;
        var str = document.getElementById("password").value;
        if(!reg.test(str)){
            $("#usernameError").html("密码只能为数学和英文！");
            $("#usernameError").css({display:"block"});
            return false;
        }
        return true;

    }

    //3.“确认密码”是否为空，验证两次密码输入是否一致
    $("#rePassword").blur(function () {
        isUserRePwdValidate = checkReUserPwd("rePassword");
        if (isUserPwdValidate && isUserRePwdValidate){
            isPwdValidate = checkPwdAndRePwd("password","rePassword");
        }
    });

    //校验“确认密码”是否为空
    function checkReUserPwd(reObjID) {
        //创建对象获取输入框内容
        var rePwd = $("#"+reObjID).val();
        $("#rePasswordError").css({display:"none"});
        if (rePwd == ""){
            $("#rePasswordError").css({display:"block"});
            return false;
        }
        return true;
    }

    //确认前后密码一致
    function checkPwdAndRePwd(objID,reObjID) {
        //创建对象获取输入框内容
        var pwd = $("#"+objID).val();
        var rePwd = $("#"+reObjID).val();
        $("#rePasswordError").css({display:"none"});
        if (!(pwd === rePwd)){
            $("#rePasswordError").css({display:"block"});
            $("#rePasswordError").html("前后两次密码不一致！");
            return false;
        }
        return true;
    }

    //4.当手机输入框失去光标
    $("#phone").blur(function () {
        isPhoneValidate =checkPhone("phone");
    });

    //验证手机号是否为空或格式是否正确
    function checkPhone(objID) {
        //创建对象获取输入框内容
        var phone = $("#"+objID).val();
        $("#phoneError").css({display:"none"});
        if (phone==""){
            $("#phoneError").css({display:"block"});
            $("#phoneError").html("请输入手机号！");
            return false;
        }
        //手机号格式验证
        var reg = /^1[0-9]{10}$/;
        var str = document.getElementById("phone").value;
        if(!reg.test(str)){
            $("#phoneError").css({display:"block"});
            $("#phoneError").html("请输入正确的手机号！");
            return false;
        }
        return true;
    }

    //5.当电子邮箱输入框失去光标
    $("#email").blur(function () {
        isEmailValidate = checkEmail("email");
    });

    //校验电子邮箱是否为空并且格式是否正确
    function checkEmail(objID) {
        //创建对象获取输入框内容
        var email = $("#"+objID).val();
        $("#emailError").css({display:"none"});
        if (email == ""){
            $("#emailError").css({display:"block"});
            $("#emailError").html("请输入电子邮箱！");
            return false;
        }
        // var reg = new RegExp("^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$")
        var reg = new RegExp("^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(?:\\.[a-zA-Z0-9_-]+)+$");
        var obj = document.getElementById("email").value;
        if(!reg.test(obj)){
            $("#emailError").css({display:"block"});
            $("#emailError").html("邮箱格式错误！");
            return false;
        }
        return true;
    }

    //6.当密码提示问题输入框失去光标
    $("#question").blur(function () {
        isQuestionValidate = checkQuestion("question");
    });

    //校验密码提示
    function checkQuestion(objID) {
        //创建对象获取输入框内容
        var question = $("#"+objID).val();
        $("#questionError").css({display:"none"});
        if (question == ""){
            $("#questionError").css({display:"block"});
            $("#questionError").html("请输入密码提示问题！");
            return false;
        }
        return true;
    }

    //7.当密码提示问题答案输入框失去光标
    $("#answer").blur(function () {
        isAnswerValidate = checkAnswer("answer");
    });

    //校验密码提示
    function checkAnswer(objID) {
        //创建对象获取输入框内容
        var answer = $("#"+objID).val();
        $("#answerError").css({display:"none"});
        if (answer == ""){
            $("#answerError").css({display:"block"});
            $("#answerError").html("请输入答案！");
            return false;
        }
        return true;
    }

    //8.点击注册
    function registerBtn() {
        //创建注册单击事件
        $(".register_btn").click(function () {
            //提交注册前校验结果
            if (!isUserNameValidate){
                return checkUserName("username");
            }
            if (!isUserPwdValidate){
                return checkUserPwd("password");
            }
            if (!isUserRePwdValidate){
                return checkReUserPwd("rePassword");
            }
            if (!isPwdValidate){
                return checkPwdAndRePwd("password","rePassword");
            }
            if (!isEmailValidate){
                return checkEmail("email");
            }
            if (!isPhoneValidate){
                return checkPhone("phone");
            }
            if (!isQuestionValidate){
                return checkQuestion("question");
            }
            if (!isAnswerValidate){
                return checkAnswer("answer");
            }
            //提交表单
            // var formData =;
            // console.log(formData);
            //请求服务器
            $.ajax({
                url:commonURL+"mgr/user/do_register.do",
                type:"post",
                data:{
                    account:$("#username").val(),
                    password:$("#password").val(),
                    email:$("#email").val(),
                    phone:$("#phone").val(),
                    question:$("#question").val(),
                    asw:$("#answer").val(),//可能出现问题
                },
                success:function (rs) {
                    //判断方法是否成功
                    if (rs.status == 0){
                        //注册成功跳转登陆页面
                        alert("注册成功");
                        $(window).attr("location","login.html");
                    }
                    else{
                        //失败弹出提示
                        alert(rs.msg);
                    }
                },
                fail:function () {
                    alert("whhsb");
                }
            });
        });
    }

    return{
        registerBtn:registerBtn
    };
});

define(['jquery','common1'], function (jquery, common1) {
var isPassed=true;
    function ready() {
        //显示当前用户信息
        showUser();
        //姓名非空验证
        $("#userName").blur(function(){
            if(nullValidate("userName","姓名")){
                isPassed=false;
                // alert("姓名错误");
                return;
            }
            $("#userName+div").css("display","none");
        });
        //验证年龄非空、范围以及必须为数字
        $("#age").blur(function(){
           if (nullValidate("age","年龄")){
               isPassed=false;
               // alert("年龄为空");
               return;
           }
           var re=/^[0-9]+.?[0-9]*$/;
           if (!re.test($("#age").val())){
               $("#age+div").css("display","block");
               $("#age+div").html("年龄必须为数字！");
               isPassed=false;
               // alert("年龄不为数字");
               return;
           }
           if ($("#age").val()<0 || $("#age").val()>120){
               $("#age+div").css("display","block");
               $("#age+div").html("年龄必须在0-120之间！");
               isPassed=false;
               // alert("年龄不在范围");
               return;
           }
            $("#age+div").css("display","none");
        });
        //验证电话非空、格式
        $("#phone").blur(function () {
            if (nullValidate("phone", "电话")) {
                isPassed=false;
                // alert("电话为空");
                return;
            }
            var phone = $("#phone").val();
            var reg=/^1[0-9]{10}$/;
            if (!reg.test(phone)){
                $("#phone+div").css("display","block");
                $("#phone+div").html("电话号码格式不正确！");
                isPassed=false;
                // alert("电话格式错误");
                return;
            }
            $("#phone+div").css("display","none");
        });
        //验证email非空和格式
        $("#email").blur(function () {
            if (nullValidate("email", "邮箱")) {
                isPassed=false;
                // alert("Email为空");
                return;
            }
            var email = $("#email").val();
            var reg=/^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(?:\.[a-zA-Z0-9_-]+)+$/;
            if (!reg.test(email)){
                $("#email+div").css("display","block");
                $("#email+div").html("邮箱格式不正确！");
                isPassed=false;
                // alert("邮箱格式不正确");
                return;
            }
            $("#email+div").css("display","none");
        });

        //保存添加事件
        $("#btnSave").click(function () {
            //非空验证
            if (nullValidate("userName","姓名")){
                return;
            }
            if (nullValidate("age","年龄")){
                return;
            }
            if (nullValidate("phone","电话")){
                return;
            }
            if (nullValidate("email","邮箱")){
                return;
            }
            $("#userName").blur();
            $("#age").blur();
            $("#phone").blur();
            $("#email").blur();

            if (!isPassed){
                isPassed=true;
                return;
            }
            //获取性别参数
            var sex='男';
            if ($("input[name='sex']:checked").attr('sex') == '1'){
                sex='女';
            }
            //提交后台修改
            var formData={
                name:$("#userName").val(),
                "sex":sex,
                //有问题
                age:$("#age").val(),
                phone:$("#phone").val(),
                email: $("#email").val(),
            };
            console.log(formData);
            $.ajax({
                //url:"http://192.168.137.1:8080/mall/mgr/user/updateuserinfo.do",
                //url:"http://localhost:8080/mall/mgr/user/updateuserinfo.do",
                url:"http://mall-01.test.app.yyuap.com/mgr/user/updateuserinfo.do",
                data:formData,
                xhrFields:{withCredentials:true},
                crossDomain:true,
                type:"post",
                success:function (rs) {
                    alert(rs.msg);
                }
            });
        });
    }

    //显示当前用户信息
    function showUser() {
        $.ajax({
            //url:"http://192.168.137.1:8080/mall/mgr/user/getuserinfo.do",
            //url:"http://localhost:8080/mall/mgr/user/getuserinfo.do",
            url:"http://mall-01.test.app.yyuap.com/mgr/user/getuserinfo.do",
            xhrFields:{withCredentials:true},
            crossDomain:true,
            success:function (rs) {
                console.log(rs);
                //判断方法是否成功
                if (rs.status==0){
                    //显示用户信息
                    $("#account").val(rs.data.account);
                    $("#userName").val(rs.data.name);
                    $("#age").val(rs.data.age);
                    $("#phone").val(rs.data.phone);
                    $("#email").val(rs.data.email);
                    if (rs.data.sex=='男'){
                        $("input:radio[name='sex']").get(0).checked=true;
                    }
                    else {
                        $("input:radio[name='sex']").get(1).checked=true;
                    }
                }
                else{
                    //失败弹出提示
                    alert(rs.msg);
                }
            }
        });
    }

    //通用非空验证
    function nullValidate(labelId,msg) {
        //判断是否为空
        if ($("#"+labelId).val() ==null || $("#"+labelId).val()==""){
            $("#"+labelId+"+div").css("display","block");
            $("#"+labelId+"+div").html(msg+"不能为空！");
            return true;
        }
        $("#"+labelId+"div").css("display","none");
        return false;
    }

    return{
        ready:ready,
    };
});

define(['common'],function(common){
    var baseUrl = common.baseUrl;
    //获得用户id
    var id = common.getParam("userId");
    //console.log(id);
    //获取用户参数
    function getuserInfo(){
        $.ajax({
            "xhrFields":{widthCredentials:true},
            "crossDomain":true,
            "url":baseUrl+"mgr/user/finduser.do",
            data:{"id":id},
            type:'post',
            //dataType:'json',
            success:function(rs){

                if(rs.status==0){
                    console.log(rs.data);

                    //成功，重新加载数据
                    $("#userId").val(rs.data.id);
                    $("#useraccount").val(rs.data.account);
                    $("#username").val(rs.data.name);
                    if(rs.data.sex=="男"){
                        document.getElementById("man").checked=" checked";
                    }else{
                        document.getElementById("woman").checked=" checked";
                    }
                    $("#userage").val(rs.data.age);
                    $("#userphone").val(rs.data.phone);
                    $("#useremail").val(rs.data.email);

                    // var table=$("#user-table").dataTable();
                    // table.fnReloadAjax(baseUrl+"mgr/usr/finduserlist.do");
                }else{
                    //失败，返回错误信息
                    alert(rs.msg);
                }

            }
        });
    }
    function saveUser(){
        //新建一个click事件
        $("#user-save").click(function () {
        //获取正确信息
            var userId = $("#userId").val();
            var useraccount = $("#useraccount").val();
            var username = $("#username").val();
            var sex = $('input:radio[name="sex"]:checked').val();
            var userage = $("#userage").val();
            if(userage==0){
                return alert("请填写年龄");
        }
            var userphone = $("#userphone").val();
            var useremail = $("#useremail").val();
            if(userphone<10000000000|userphone>99999999999){
                return alert("电话号码格式错误")
            };

            var patt=/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/;
            if(!patt.test(useremail)) {
                alert("邮箱格式错误");
                return;
            };


        //ajax
            $.ajax({
                "xhrFields":{widthCredentials:true},
                "crossDomain":true,
                "url":baseUrl+"mgr/user/backupdateuserinfo.do",
                data:{"id":userId,"name":username,"account":useraccount,"age":userage,
                        "phone":userphone,"email":useremail,"sex":sex},
                type:'post',
                //dataType:'json',
                success:function(rs){
                    if(rs.status==0){
                    $(window).attr("location","user_management.html");

                    }else{
                    //失败，返回错误信息
                        alert(rs.msg);
                }

            }
        });

    })

}

    return{
        getuserInfo:getuserInfo,
        saveUser:saveUser
    };
});
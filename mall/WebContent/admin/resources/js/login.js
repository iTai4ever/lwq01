define(['common'],function(common){
	var baseUrl = common.baseUrl;
	//绑定登录点击事件
	function clickLogin(){
		$("#btn_login").click(function(){
			//获取用户名
			var input_username = $("#input_username").val();
			//判断用户名是否为空
			if(input_username == ""){
				$("#errMsg").html("请填写用户名");
				$("#input_username").focus();
				return false;
			}
			//获取密码
			var input_password = $("#input_password").val();
			//判断密码是否为空
			if(input_password == ""){
				$("#errMsg").html("请填写密码");
				$("#input_password").focus();
				return false;
			}
			$.ajax({
				url:baseUrl+"mgr/user/backlogin.do",
				xhrFields:{withCredentials:true},
				crossDomain:true,
				type:"post",
				data:{"account":input_username,"password":input_password},
				success:function(rs){
					if(rs.status==0){
						//成功跳转界面
						$(window).attr("location","user_management.html");
					}else{
						//失败返回错误信息
						alert(rs.msg);
					}
				}
			});
		})
	};
	

	return {
		clickLogin:clickLogin,
	};
});




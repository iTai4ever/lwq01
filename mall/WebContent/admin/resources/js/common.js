define(function(){
	var baseUrl="http://192.168.137.1:8080/mall/";
	//获取url中的参数
	function getParam(name){
		var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");//构造一个含有目标参数的正则表达式对象
		//console.log(reg);
		var r = window.location.search.substr(1).match(reg);//匹配目标参数
		//console.log(r+"1");
		if(r != null) return decodeURI(r[2]); return null;//返回参数值
	}
	//用户校验
	function userCalibration(){
		var url = window.location.href;
		//判断是否登录页，如果是则不再请求登录用户信息
		if(url.indexOf("login.html")>=0){
			return;
		}
		//加载登录用户信息
		$.ajax({
			url:baseUrl+"mgr/user/getuserinfo2.do",
			xhrFields:{withCredentials:true},
			crossDomain:true,
			success:function(user){
				if(user.status==0){
					//判断是否管理员
					console.log(user.data);
					if(user.data.role==2){
						$("#user-info-container").html(user.data.account);
					}else{
						alert("无操作权");
						//$(window).attr("location","../../starter.html");
					}
				}else{
					//未登录直接跳转后台登录页面
					$(window).attr("location","../../login.html")
				}
			}
		});
	}
	
	return {
		baseUrl:baseUrl,
		getParam:getParam,
		userCalibration:userCalibration
	};
});
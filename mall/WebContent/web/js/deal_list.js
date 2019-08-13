var sta="1";//全部订单 0 ；待付款 1 ；待收货 3 ；
$(document).ready(getDeals(1,7,1,function () {
    getDeals(1,7,3,function () {
        getDeals(1,7,0);
    });
}));

//用户进入页面时订单信息的加载
function getDeals(pageNo,pageSize,status,callBack){
    $("tbody").html("");
    $.ajax({
        url:commonURL+"order/getlist.do",
        type:"POST",
        xhrFields:{withCredentials:true},
        crossDomain:true,
        data:{'pageNo':pageNo,'pageSize':pageSize,'status':status},
        success:function(res){
            var response=eval(res);
            if(response.status===0){//请求执行成功
                //添加订单数据
                $("tbody").html("");
                var tpl=$("#tpl").html();
                var func=Handlebars.compile(tpl);
                var result = func(response.data.data);//数据
                $("tbody").html(result);

                //修改操作按钮
                if(status=="0"){//请求的是全部订单
                    //修改顶部按钮样式
                    $("#allNum").html("（"+response.data.totalRecord+"）");
                    //修改按钮文本
                    $(".button"+2).html("查看订单");
                    $(".button"+3).html("查看订单");
                    $(".button"+4).html("查看订单");
                    $(".button"+6).html("查看订单");

                    $(".button"+1).on("click",function (i, obj) {
                        var href="../cn/deal_detail.html?id="+$(this).attr("id");
                        $(this).attr("href",href);
                    });
                    $(".button"+2).on("click",function (i, obj) {
                        var href="../cn/deal_detail.html?id="+$(this).attr("id");
                        $(this).attr("href",href);
                    });
                    $(".button"+3).on("click",function (i, obj) {
                        var href="../cn/deal_detail.html?id="+$(this).attr("id");
                        $(this).attr("href",href);
                    });
                    $(".button"+4).on("click",function (i, obj) {
                        var href="../cn/deal_detail.html?id="+$(this).attr("id");
                        $(this).attr("href",href);
                    });
                    $(".button"+6).on("click",function (i, obj) {
                        var href="../cn/deal_detail.html?id="+$(this).attr("id");
                        $(this).attr("href",href);
                    });
                }else if(status=="1"){//请求的是待付款订单
                    //修改顶部按钮样式
                    $("#noPayNum").html("（"+response.data.totalRecord+"）");

                }else if(status=="3"){//请求的是待收货订单
                    //修改顶部按钮样式
                    $("#noTakeNum").html("（"+response.data.totalRecord+"）");
                    //修改按钮文本
                    $(".button"+3).html("确认收货");
                    //修改按钮链接
                    $(".button"+3).on("click",function (i, obj) {
                        $.ajax({
                            url:commonURL+"order/confirmreceipt.do",
                            type:"POST",
                            xhrFields:{withCredentials:true},
                            crossDomain:true,
                            data:{orderNo:$(this).attr("id")},
                            success:function(res){
                                if(response.status===0){//请求执行成功
                                    alert(res.msg);
                                    $(window).attr("location","deal_list.html");
                                }
                                else{//请求失败
                                    alert(response.msg);
                                }
                            }
                        });
                    });
                }
                //执行回调函数
                if(callBack) {
                    callBack();
                }
            }
            else{//请求失败
                alert(response.msg);
            }
        }
    });
}

//支付、收货按钮
$("a").click(function (i,obj) {
    var id="../cn/deal_detail.html?id="+$(obj).attr("id");
    // alert("ee"+id);
    if($(this).attr("class")==="button1"){//查看订单详情
        $(window).attr("location",""+id);
    }else if($(this).attr("class")==="button3"){
        $(window).attr("location",""+id);
    }else if($(this).attr("class")==="button4"){
        $(this).attr("href",id);
        return;
    }else if($(this).attr("class")==="button6"){
        $(this).attr("href",id);
        return;
    }
});

// 顶部menu的点击响应事件
$("#top_list_bar>span").click(function () {
    $("tbody").html();
    var className=$(this).attr("class");
    var normalClass="navi";
    var selectClass="select_navi";
    sta=$(this).attr("id");
    if(className===normalClass){
        //修改按钮样式
        $(".select_navi").removeAttr("class").attr("class",normalClass);
        $(this).removeAttr("class").attr("class",selectClass);

    }
    //发送请求
    getDeals(1,7,sta);
});

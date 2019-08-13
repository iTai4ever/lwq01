var url;
var param = "";
$(document).ready(function () {
    //获取URL中的参数orderNo
    url = window.location
    param = url.toString().split("?");
    param=param[1].split("=");
    param=param[1];

    //向后台请求数据
    $.ajax({
        url:commonURL+"order/getdetail.do",
        type:"get",
        xhrFields:{withCredentials:true},
        crossDomain:true,
        data:{'orderNo':param},
        success:function(res){
            if(res.status===0){//请求执行成功
                //填充数据
                //1.地址信息
                var addr=res.data.address.province+" "
                    +res.data.address.city+" "
                    +res.data.address.addr+" "
                    +res.data.address.zip+" "
                    +res.data.address.name+" "
                    +res.data.address.mobile+" ";
                $("#deal_address").html(addr);
                //2.订单信息
                $("#orderNo").html(res.data.orderNo);
                $("#orderCreated").html(res.data.orderNo);
                $("#orderPrice").html(res.data.amount);
                $("#orderStatus").html(res.data.statusDesc);
                $("#payTime").html(res.data.paymentTime);
                $("#payType").html(res.data.typeDesc);
                //3.商品信息
                var tpl=$("#tp").html();
                var func=Handlebars.compile(tpl);
                var data=res.data.orderItems;
                var result=func(data);
                $("tbody").html(result);

                //支付取消按钮的显示控制
                if(res.data.status!==1){
                    $(".btn").remove();
                }else{
                    $("#orderCancel").on("click",cancelOrder);
                    $("#payCancel").on("click",cancelPay);
                }
            }
            else{//请求失败
                alert(res.msg);
            }
        }
    });
});

//取消订单按钮
function cancelOrder() {
    $.ajax({
        url:commonURL+"order/cancelorder.do",
        type:"post",
        xhrFields:{withCredentials:true},
        crossDomain:true,
        data:{'orderNo':param},
        success:function(res){
            if(res.status===0){//请求执行成功
                alert(res.msg);
                $(window).attr("location","deal_list.html");
            }
            else{//请求失败
                alert(res.msg);
            }
        }
    });
}

//取消支付
function cancelPay() {
    if(confirm("客官，确定要稍后支付吗"))
        $(window).attr("location","deal_list.html");
}

$(document).ready(init());

//页面初始化
function init(){
    getCart();
    getUserAddr();
    submitInfo();
}
//获取用户地址
function getUserAddr() {
    $.ajax({
        url:commonURL+"mgr/addr/findaddr.do",
        xhrFields:{withCredentials:true},
        crossDomain:true,
        success:function(res){
            if(res.status===0){//请求执行成功
                if(res.data.length===0){
                    // $("#iguo").css({display:'none'});
                    alert('地址信息为空，请先添加地址');
                    $(window).attr('location','address_management.html');
                }
                //填充数据
                $("#iguo").css({display:'inline-block'});
                $("#iguo").html("");
                var addrtpl=$("#addr_tpl").html();
                var fun=Handlebars.compile(addrtpl);
                var addrs=[];//只显示6条
                if(res.data.length>3){
                    addrs[0]=res.data[0];
                    addrs[1]=res.data[1];
                    addrs[2]=res.data[2];
                    // addrs[3]=res.data[3];
                    // addrs[4]=res.data[4];
                    // addrs[5]=res.data[5];
                }
                else
                    addrs=res.data;

                var result=fun(addrs);
                $("#iguo").html(result);

                //设置默认地址
                $(".addrItem").each(function (i, object) {
                    var cls=$(this).attr("class").split(" ");
                    if(cls.length>1){
                        $("#submit").attr("class",$(this).attr("id"));
                    }
                });

                deleteAddr();

                setDefault();
            }
            else{//请求失败
                alert(res.msg);
            }
        }
    });
}

//删除地址
function deleteAddr(){
    $(".deleteAddr").on("click",function (i, obj) {
        var id=$(this).attr("class").split(" ");
        id=id[1];
        // alert(id);
        $.ajax({
            url:commonURL+"mgr/addr/deladdr.do",
            type:"get",
            xhrFields:{withCredentials:true},
            crossDomain:true,
            data:{id:id},
            success:function(res){
                if(res.status===0){//请求执行成功
                    //删除成功
                    getUserAddr();
                }
                else{//请求失败
                    alert(res.msg);
                }
            }
        });
    })
}

//设置默认地址
function setDefault() {
    $(".setDefault").on("click",function (i,obj) {
        var id=$(this).attr("class").split(" ");
        id=id[1];
        $.ajax({
            url:commonURL+"mgr/addr/setdefault.do",
            type:"get",
            xhrFields:{withCredentials:true},
            crossDomain:true,
            data:{id:id},
            success:function(res){
                if(res.status===0){
                    //默认设置成功
                    getUserAddr();
                }
                else{//请求失败
                    alert(res.msg);
                }
            }
        });
    });
}

//设置商品信息
function getCart(){
    $.ajax({
        url:commonURL+"cart/findcheckedcarts.do",
        type:"get",
        xhrFields:{withCredentials:true},
        crossDomain:true,
        success:function(res){
            if(res.status===0){//请求执行成功
                //更新页面
                updateCartInfo(res);
            }
            else{//请求失败
                alert(res.msg);
            }
        }
    });
}

function updateCartInfo(res){
    var totalPrice=res.data.totalPrice;

    $("tbody").html("");
    var goodstpl=$("#gooods_tpl").html();
    var fun=Handlebars.compile(goodstpl);
    var result=fun(res.data.lists);
    $("tbody").html(result);
    $("#price").html(totalPrice);
}

//订单提交
function submitInfo(){
    // if($("#iguo").css('display')==='none') {
    //     alert("请添加地址！");
    //     $("#iguo").css({'display':'block'});
    //     $(window).attr('location','address_management.html');
    //     return;
    // }
    $("#submit").click(function () {
        var id=$(this).attr("class");
        $.ajax({
            url:commonURL+"order/createorder.do",
            type:"POST",
            xhrFields:{withCredentials:true},
            crossDomain:true,
            data:{addrId:parseInt(id)},
            success:function(res){
                if(res.status===0){//请求执行成功
                    $(window).attr("location","../cn/deal_detail.html?orderNo="+res.data.orderNo);
                }
                else{//请求失败
                    alert(res.msg);
                }
            }
        });
    });
}



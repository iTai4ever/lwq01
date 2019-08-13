var aId=null;
Handlebars.registerHelper("state",function (defAddr, id) {
    //判断是否是默认地址，绑定对应按钮
    if(defAddr===1)
        return new Handlebars.SafeString("<div addrId="+id+" class='tdefault'>默认地址</div>");
    return new Handlebars.SafeString("<button class='btn_default' addrId="+id+" style='background:#fff;border:none'>设为默认</button>");
})
$(document).ready(init);

function init() {
    //三级联动设置
    $("#distpicker1").distpicker("destroy");
    $("#distpicker1").distpicker({
        autoSelect: false
    });
    //显示所有地址信息
    showAddrInfo();
    //设置默认地址事件绑定
    $("tbody").on("click",".btn_default",function () {
        var aId=$(this).attr("addrId");
        $.ajax({
            url:commonURL+"mgr/addr/setdefault.do",
            type:"get",
            xhrFields:{withCredentials:true},
            crossDomain:true,
            data:{'id':aId},
            success:function(res){
                if(res.status===0){//请求执行成功
                    //页面加载地址信息
                    updatePage(res);
                }
                else{//请求失败
                    alert(res.msg);
                }
            }
        });
    });

    //修改事件绑定
    $(document).on("click",".addr_update",function () {
        aId=$(this).attr("addrId");
        // alert(aId);
        $.ajax({
            url:commonURL+"mgr/addr/findaddrbyid.do",
            type:"post",
            xhrFields:{withCredentials:true},
            crossDomain:true,
            data:{'addrId':aId},
            success:function(res){
                if(res.status===0){//请求执行成功
                    //页面加载地址信息
                    fillAddr(res.data);
                }
                else{//请求失败
                    alert(res.msg);
                }
            }
        });
    });

    //删除事件绑定
    $("tbody").on("click",".addr_delete",function () {
        var ques=confirm("要删除该地址吗？");
        if(!ques)
            return;
        var aId=$(this).attr("addrId");
        $.ajax({
            url:commonURL+"mgr/addr/deladdr.do",
            type:"get",
            xhrFields:{withCredentials:true},
            crossDomain:true,
            data:{'id':aId},
            success:function(res){
                if(res.status===0){//请求执行成功
                    //页面加载地址信息
                    updatePage(res);
                }
                else{//请求失败
                    alert(res.msg);
                }
            }
        });
    });

    //保存事件绑定
    $(".save").click(function () {
        //非空验证
        if(!validate()){
            return;
        }
        //提交后台
        var formData={
            'name': $("#consignee").val(),
            'mobile': $("#phone").val(),
            'province':$("#province").find("option:selected").text(),
            'city':$("#city").find("option:selected").text(),
            'district':$("#district").find("option:selected").text(),
            'zip':$("#zip").val(),
            'addr':$("#detaiAddr").val()
        };
        // alert(formData.city+formData.addr);
        //通过aId判断是添加还是修改
        if(aId!==null) {
            formData["id"] = aId;
            aId=null;
        }
        $.ajax({
            url:commonURL+"mgr/addr/saveaddr.do",
            type:"post",
            xhrFields:{withCredentials:true},
            crossDomain:true,
            data:formData,
            success:function(res){
                if(res.status===0){//请求执行成功
                    //页面加载地址信息
                    updatePage(res);
                    cleanAddr();
                }
                else{//请求失败
                    alert(res.msg);
                }
            }
        });
    });

}

//显示所有地址信息
function showAddrInfo(){
    $.ajax({
        url:commonURL+"mgr/addr/findaddr.do",
        type:"get",
        xhrFields:{withCredentials:true},
        crossDomain:true,
        success:function(res){
            if(res.status===0){//请求执行成功
                //页面加载地址信息
                updatePage(res);
            }
            else{//请求失败
                $(window).attr("location","../cn/login.html");
            }
        }
    });
}

//刷新地址栏信息
function updatePage(res) {
    $("tbody").html("");
    var tpl=$("#addrtpl").html();
    var fun=Handlebars.compile(tpl);
    var result=fun(res.data);
    $("tbody").html(result);
}

//获得地址信息填充标签显示
function fillAddr(data) {
    //填充省份、市区
    $("#distpicker1").distpicker("destroy");
    $("#distpicker1").distpicker({
        province:data.province,
        city:data.city,
        district:data.district
    });
    //填充其他信息
    $("#detaiAddr").val(data.addr);
    $("#zip").val(data.zip);
    $("#consignee").val(data.name);
    $("#phone").val(data.mobile);
}

//验证数据是否有效
function validate() {
    //验证三级联动
    var selected=$("#province").find("option:selected").attr("data-code");
    if(selected===null||selected===""){
        alert("请选择省份");
        return false;
    }
    selected=$("#city").find("option:selected").attr("data-code");
    if(selected===null||selected===""){
        alert("请选择城市");
        return false;
    }
    selected=$("#district").find("option:selected").attr("data-code");
    if(selected===null||selected===""){
        alert("请选择市区");
        return false;
    }

    selected=$("#detaiAddr").val();
    if(selected===null||selected===""){
        alert("请输入详细地址");
        return false;
    }
    //验证邮编
    selected=$("#zip").val();
    var reg=/^[0-9]{6}$/;
    if(!reg.test(selected)) {
        alert("请输入正确的邮编");
        return false;
    }
    //验证姓名
    selected=$("#consignee").val();
    if(selected===null||selected===""){
        alert("请输入收货人姓名");
        return false;
    }
    //验证电话
    selected=$("#phone").val();
    reg=/^[0-9]{11}$/;
    if(!reg.test(selected)){
        alert("请输入正确的手机格式");
        return false;
    }
    return true;
}

//清空输入框
function cleanAddr() {
    //回复省市区默认值

    //清空其他输入框内容
    $("#consignee").val("");
    $("#phone").val("");
    $("#zip").val("");
    $("#detailAddr").val("");
}

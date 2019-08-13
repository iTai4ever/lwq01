var parentId = 0;//父类型id

// //用户检验
// $(function userCalibration(){
//         var url = window.location.href;
//         //判断是否登录页，如果是则不再请求登录用户信息
//         if(url.indexOf("login.html")>=0){
//             return;
//         }
//         //加载登录用户信息
//         $.ajax({
//             url:"http://localhost:8080/mall/user/getuserinfo2.do",
//             xhrFields:{withCredentials:true},
//             crossDomain:true,
//             success:function(user){
//                 if(user.status==0){
//                     //判断是否管理员
//                     if(user.data.role==2){
//                         $("#user-info-container").html(user.data.account);
//                     }else{
//                         alert("无操作权");
//                         $(window).attr("location","../../starter.html");
//                     }
//                 }else{
//                     //未登录直接跳转后台登录页面
//                     $(window).attr("location","../../login.html")
//                 }
//             }
//         });
//     });

$(function productTable() {
    //1.商品表格初始化
    // 找到位置引用datatable
        $("#product-table").dataTable({
            "autoWidth":false, //禁止自动计算宽度
            "paging":true,     //分页
            "ordering":false,  //禁止排序
            "info":false,      //禁止统计信息
            "searching":false, //取消搜索
            "dom":'<"#tool-container"><"top" t><"bottom" lp>',  //dom
            "aPaginationType":"full_numbers",
            "ajax":{
                "xhrFields":{widthCredentials:true},
                "crossDomain":true,
                "url":"http://192.168.137.1:8080/mall/param/findallparams.do",
            },
            "columns":[
                {"data":"id"},
                {"data":"name"},
                {"data":null}
            ],
            columnDefs:[
                //最后一列
                {
                    targets:2,
                    render:function(data,type,row,meta){
                        return findChildren(row);
                    }
                }
            ],
            "oLanguage":{
                "oProcessing":"正在加载数据....",
                "sLengthMenu":"每页显示_MENU_条记录",
                "sZeroRecords":"抱歉没有找到",
                "sInfo":"从_START_到_END_/共_TOTAL_条记录",
                "sInfoEmpty":"没有数据",
                "sInforFiltered":"从_MAX_条数据中检索",
                "sSearch":"模糊查询",
                "oPaginate":{
                    "sFirst":"首页",
                    "sPrevious":"前一页",
                    "sNext":"后一页",
                    "sLast":"尾页"
                }
            }

        });
});
$(
  //2.加载新增商品按钮
  function addBtn(){
    var str = '<a class="btn btn-primary pull-right" href="param_save.html">新增类型参数</a>';
    $("#tool-container").addClass("clearfix");
    $("#tool-container").html(str);

  }
);


//渲染操作
function findChildren(row) {
    var str = '<a class="button" href="javascript:void(0);" onclick="queryDirectChild(\''+row.id+'\')">查看子节点</a>';
    str += '<a class="button" href="javascript:void(0);" id="del_params" style="margin-left:10px;" onclick="delParams(\''+row.id+'\')">删除</a>';
    str += '<a class="button" href="javascript:void(0);" id="update_params" style="margin-left:10px" onclick="updateParams(\''+row.id+'\',\''+row.name+'\')">编辑</a>';
    return str;
}

//查看子节点
function queryDirectChild(id){
  parentId = id;//父级id
  console.log(id);
  //清空数据
  var table = $("#product-table").dataTable();
  table.fnClearTable();//
  //重新加载数据
  table.fnReloadAjax("http://192.168.137.1:8080/mall/mgr/param/findchildren.do?id="+id);
}

//删除类型
function delParams(id){
  //提示确定删除
  if(confirm("确定删除吗?")){
    $.ajax({
        url: "http://192.168.137.1:8080/mall//mgr/param/delparam.do",
        xhrFields: {withCredentials: true},//允许跨域请求携带cookie数据
        crossDomain: true,//跨域请求
        data:{'id':id},
        type:"post",
        dataType:'json',
        success: function (rs) {
          console.log(rs);
          if(rs.status == 0){
            //清空数据
            var table = $("#product-table").dataTable();
            table.fnClearTable();//
            //重新加载数据
            table.fnReloadAjax("http://192.168.137.1:8080/mall//mgr/param/findchildren.do?id="+id);
          }else alert(rs.msg);
        }
      });
      return true;
  }return false;
}

//修改类型
function updateParams(id,paramName){
  $(window).attr("location","param_update.html?paramId="+id+"&parentId="+parentId+"&paramName="+paramName);
}

define(['common'],function(common){
    //商品初始化
	var baseUrl = common.baseUrl;
    function initialization() {
        //找到位置引用datatable
        $("#user-table").dataTable({
            "autoWidth": false, //禁止自动计算宽度
            "paging": true,     //分页
            "ordering": false,  //禁止排序
            "info": false,      //禁止统计信息
            "searching": false, //取消搜索
            "dom": '<"#tool-container"><"top" t><"bottom" lp>',  //dom
            "aPaginationType": "full_numbers",
            "ajax": {
                "xhrFields": {withCredentials: true},
                "type":'post',
                "crossDomain": true,
                "url": baseUrl + "mgr/user/finduserlist.do",
            },
            "columns": [
                {"data": "id"},
                {"data": "account"},
                {"data": "name"},
                {"data": "sex"},
                {"data": "age"},
                {"data": "phone"},
                {"data": "email"},
                {"data": null}
            ],
            columnDefs: [

                //最后一列
                {
                    targets: 7,
                    render: function (data, type, row, meta) {
                        return query(row);
                    }
                }
            ],
            "oLanguage": {
                "oProcessing": "正在加载数据....",
                "sLengthMenu": "每页显示_MENU_条记录",
                "sZeroRecords": "抱歉没有找到",
                "sInfo": "从_START_到_END_/共_TOTAL_条记录",
                "sInfoEmpty": "没有数据",
                "sInforFiltered": "从_MAX_条数据中检索",
                "sSearch": "模糊查询",
                "oPaginate": {
                    "sFirst": "首页",
                    "sPrevious": "前一页",
                    "sNext": "后一页",
                    "sLast": "尾页"
                }
            }
        });
    }
    return{
                initialization:initialization



    };
});

//渲染按钮
function query(row) {
    var str = '<a class="button" href="javascript:void(0);" onclick="updateUser(\''+row.id+'\')">编辑</a>';
    str+='<a class="button" href="javascript:void(0);" style="margin-left:10px;" onclick="delUser(\''+row.id+'\')">删除</a>'
    return str;
}
//编辑用户
function updateUser(id){
    $(window).attr("location","user_update.html?userId="+id);


}

//删除用户
function delUser(id){
    //确认是否删除
    if(confirm("确定删除吗")){
        //删除用户
        $.ajax({
            "xhrFields":{widthCredentials:true},
            "crossDomain":true,
            "url":"http://192.168.137.1:8080/mall/mgr/user/deleteUsers.do",
            data:{"id":id},
            type:'post',
            dataType:'json',
            success:function(rs){
                if(rs.status==0){
                    //成功，重新加载数据
                    var table=$("#user-table").dataTable();
                    table.fnReloadAjax("http://192.168.137.1:8080/mall/mgr/user/finduserlist.do");
                }else{
                    //失败，返回错误信息
                    alert(rs.msg);
                }

            }
        });
    }
}

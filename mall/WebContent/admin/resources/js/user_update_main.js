
require.config({
    paths: {
        'jquery':"../../adminlte/bower_components/jquery/dist/jquery.min",
        'bootstrap':"../../adminlte/bower_components/bootstrap/dist/js/bootstrap.min",
        'datatables.net':"../../adminlte/bower_components/datatables.net/js/jquery.dataTables.min",
        'bsdataTables':"../../adminlte/bower_components/datatables.net-bs/js/dataTables.bootstrap.min",
        'adminlte':"../../adminlte/dist/js/adminlte.min",
        //'fnReloadAjax':"../../adminlte/resource/js/fnReloadAjax",
        //'user_management':"../../adminlte/resource/js/user_manegement_main"





    },
    shim:{
        'bootstrap':['jquery'],
        'bsdataTables':['bootstrap'],
        'fnReloadAjax':['jquery','datatables.net'],
        'adminlte':['bootstrap']


    }




});

require(['jquery','bootstrap','datatables.net','bsdataTables','adminlte','common'
        ,'user_management','fnReloadAjax','user_update'],
    function (jqury,bootstrap,datatablesnet,bsdataTables,adminlte,common,
              user_management,fnReloadAjax,user_update){
        $(function(){
            //用户校验
            common.userCalibration();
            //获取用户信息
            user_update.getuserInfo();
            //保存
            user_update.saveUser();
        });

    });

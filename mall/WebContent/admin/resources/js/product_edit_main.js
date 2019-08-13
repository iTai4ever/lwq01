require(['common','product_edit','simditor_product','webuploader_product'],function (common,product_edit,simditor_product,webuploader_product) {
	$(function () {
		//用户校验
		//common.userCalibration();
		//获取产品类型参数和商品信息
		product_edit.getType();
		//为产品绑定下拉事件
		product_edit.dropDownEvent();
		//保存商品信息
		product_edit.btnSave();
	});


});
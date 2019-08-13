require(['common','product_save','simditor_product','webuploader_product'],
		function(common,product_save,simditor,webuploader_product){
	$(function(){
		//用户校验
		//common.userCalibration();

		product_save.getParams();
		product_save.dropDownEvent();
		product_save.saveBtn();
	});
});
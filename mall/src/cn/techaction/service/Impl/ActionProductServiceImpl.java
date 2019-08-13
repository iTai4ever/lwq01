package cn.techaction.service.Impl;

import java.util.Date;
//import java.sql.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import cn.techaction.common.SverResponse;
import cn.techaction.dao.ActionParamsDao;
import cn.techaction.dao.ActionProductDao;
import cn.techaction.pojo.ActionProduct;
import cn.techaction.service.ActionProductService;
import cn.techaction.utils.ConstUtil;
import cn.techaction.utils.PageBean;
import cn.techaction.vo.ActionProductFloorVo;
import cn.techaction.vo.ActionProductListVo;
@Service
public class ActionProductServiceImpl implements ActionProductService {

	
	@Autowired
	private ActionProductDao aProductDao;
	
	@Autowired
	private ActionParamsDao aParamsDao;
	
	@Override
	public SverResponse<PageBean<ActionProduct>> findProduct(Integer productId, Integer partsId, Integer pageNum,
			Integer pageSize) {
		// TODO Auto-generated method stub
		//1.根据条件获得查询的商品总条数
		int totalCount = aProductDao.getTotalCount(productId, partsId);
		PageBean<ActionProduct> pageBean = new PageBean<>(pageNum,pageSize,totalCount);
		//2.调用Dao层获得分页查询的商品信息
		pageBean.setData(aProductDao.findProductsByInfo(productId, partsId,pageNum,pageSize));
		return SverResponse.createRespBySuccess(pageBean);
	}

	@Override
	public SverResponse<List<ActionProduct>> findHotProducts(Integer num) {
		// TODO Auto-generated method stub
		//直接查询所需数据
		List<ActionProduct> products = aProductDao.findHotProducts(num);
		return SverResponse.createRespBySuccess(products);
	}
	
	@Override
	 public SverResponse<ActionProductFloorVo> findFloorProducts(){
		//创建vo
		ActionProductFloorVo vo = new ActionProductFloorVo();
		//1楼数据
		List<ActionProduct> products1 = aProductDao.findProductsByCategory(ConstUtil.ProductType.TYPE_HNTJX);
		vo.setFirstFloor(products1);
		//2楼数据
		List<ActionProduct> products2 = aProductDao.findProductsByCategory(ConstUtil.ProductType.TYPE_JZQZJJX);
		vo.setSecondFloor(products2);
		//3楼数据
		List<ActionProduct> products3 = aProductDao.findProductsByCategory(ConstUtil.ProductType.TYPE_GCQZJJX);
		vo.setThirdFloor(products3);
		//4楼数据
		List<ActionProduct> products4 = aProductDao.findProductsByCategory(ConstUtil.ProductType.TYPE_LMJX);
		vo.setForthFloor(products4);
		 return SverResponse.createRespBySuccess(vo);
	 }

	@Override
	public SverResponse<ActionProduct> findProductDetailForPortal(Integer id) {
		//判断产品编号是否为空
		if(id==null){
			return SverResponse.createByErrorMessage("产品编号不能为空");
		}
		
		//查询商品详情
		ActionProduct product = aProductDao.findProductById(id);
		
		//判断产品是否下架
		if(product==null){
			return SverResponse.createByErrorMessage("产品已经下架");
		}
		if(product.getStatus()==ConstUtil.ProductStatus.STATUS_OFF_SALE){
			return SverResponse.createByErrorMessage("产品已经下架");
		}
		return SverResponse.createRespBySuccess(product);
	}

	@Override
	public SverResponse<PageBean<ActionProductListVo>> findProductsForPortal(Integer productTypeId, Integer partsId,
			String name, int pageNum, int pageSize) {
		//创建对象
		ActionProduct product = new ActionProduct();
		int totalRecord = 0;
		//判断name是非为空
		if(name!=null&&!name.equals("")){
			product.setName(name);
		}
		if(productTypeId!=0){
			product.setProductId(productTypeId);
		}
		if(partsId!=0){
			product.setPartsId(partsId);
		}
		
		//前端显示商品都为在售
		product.setStatus(2);
		
		//查找符合条件的总记录数
		totalRecord = aProductDao.getTotalCount(product);
		//创建分页对象
		PageBean<ActionProductListVo> pageBean = new PageBean<>(pageNum,pageSize,totalRecord);
		//读取数据
		List<ActionProduct> products = aProductDao.findProducts(product,pageBean.getStartIndex(),pageSize);
		//封装
		List<ActionProductListVo> voList = Lists.newArrayList();
		for(ActionProduct p:products){
			voList.add(createProductListVo(p));
		}
		pageBean.setData(voList);
		return SverResponse.createRespBySuccess(pageBean);
	}
	
	@Override
	public SverResponse<PageBean<ActionProductListVo>> findProductsByName(String name, int pageNum, int pageSize) {
		// TODO Auto-generated method stub
		// 创建对象
		ActionProduct product = new ActionProduct();
		int totalRecord = 0;
		// 判断name是非为空
		if (name != null && !name.equals("")) {
			product.setName(name);
		}
		// 前端显示商品都为在售
		product.setStatus(2);

		// 查找符合条件的总记录数
		totalRecord = aProductDao.getTotalCount(name);
		System.out.println(totalRecord);
		// 创建分页对象
		PageBean<ActionProductListVo> pageBean = new PageBean<>(pageNum, pageSize, totalRecord);
		// 读取数据
		List<ActionProduct> products = aProductDao.findProductByName(name, pageBean.getStartIndex(), pageSize);
		// 封装
		List<ActionProductListVo> voList = Lists.newArrayList();
		for (ActionProduct p : products) {
			voList.add(createProductListVo(p));
		}
		pageBean.setData(voList);
		return SverResponse.createRespBySuccess(pageBean);
	
	}	
	
	@Override
	public SverResponse<List<ActionProductListVo>> findProducts(ActionProduct product) {
		// TODO Auto-generated method stub
		if(product.getName()!=null){
			product.setName("%"+product.getName()+"%");
		}
		//调用Dao类中的方法实现查询
		List<ActionProduct> products = aProductDao.findProductNoPage(product);
		//需将ActionProduct对象转化为业务实体对象
		List<ActionProductListVo> voList = Lists.newArrayList();
		for(ActionProduct pro:products){
			voList.add(createProductListVo(pro));
		}
		return SverResponse.createRespBySuccess(voList);
	}
	
	/**
	 * 封装vo对象
	 * @return
	 */
	private ActionProductListVo createProductListVo(ActionProduct product) {
		ActionProductListVo vo = new ActionProductListVo();
		vo.setId(product.getId());
		vo.setName(product.getName());
		//System.out.println("product.getPartsId()).getName() = " + product.getPartsId());
		//System.out.println("ParamsDao.findParamById(product.getPartsId()) = " + aParamsDao.findParamById(product.getPartsId()));
		vo.setPartsCategory(aParamsDao.findParamById(product.getPartsId()).getName());
		vo.setProductCategory(aParamsDao.findParamById(product.getProductId()).getName());
		vo.setPrice(product.getPrice());
		vo.setStatus(product.getStatus());
		vo.setIconUrl(product.getIconUrl());
		vo.setStatusDesc(ConstUtil.ProductStatus.getStatusDesc(product.getStatus()));
		vo.setHotStatus(ConstUtil.HotStatus.getHotDesc(product.getHot()));
		vo.setHot(product.getHot());
		
		return vo;
		
	}

	@Override
	public SverResponse<String> updateStatus(Integer productId, Integer status, Integer hot) {
		// TODO Auto-generated method stub
		if(productId==null||status==null||hot==null){
			return SverResponse.createByErrorMessage("非法参数");
		}
		ActionProduct product = new ActionProduct();
		product.setId(productId);
		Date updated = new Date();
		product.setUpdated(updated);
		
		//判断是修改上下架还是修改热销
		if(status==-1){
			product.setHot(hot);
		}else if(hot==-1){
			product.setStatus(status);
		}
		//调用dao层修改商品方法
		int rs = aProductDao.updateProduct(product);
		if(rs>0){
			return SverResponse.createRespBySuccess("修改商品成功！");
		}
		return SverResponse.createByErrorMessage("修改商品状态失败！");
	}

	@Override
	public SverResponse<String> savsOrUpdateProduct(ActionProduct product) {
		// TODO Auto-generated method stub
		if(product==null){
			return SverResponse.createByErrorMessage("商品的参数无效！");
		}
		//1.处理主图和子图的链接，从前端传递过来的图的链接放在了subimages里
		//第一个链接作为主图链接，其他的作为子图连接
		//修改时，如果上传了图片，会清空原来的，新的链接和新增相同
		//修改时如果没有重新上传图片，
		if(!StringUtils.isEmpty(product.getSubImages())){
			String[] array = product.getSubImages().split(",");
			//拿住第一元素作为主图
			product.setIconUrl(array[0]);
			String temp = product.getSubImages();
			int index = temp.indexOf(",");
			if(index!=-1){
				if(temp.substring(index+1).equals("null")){
					product.setSubImages(null);
				}
				else{
					product.setSubImages(temp.substring(index+1));
				}
				product.setSubImages(temp.substring(index+1));
			}
			else{
				product.setSubImages(null);
			}
		}
		int rs=0;
		//判断是修改还是新增
		if(product.getId()!=null){
			product.setUpdated(new Date());
			//调用Dao的修改方法
			rs = aProductDao.updateProduct(product);
			
		} else {
			// 2.处理其他的属性
			//product.setId(id);
			product.setStatus(ConstUtil.ProductStatus.STATUS_NEW);
			product.setHot(ConstUtil.HotStatus.NORMAL_STATUS);
			product.setCreated(new Date());
			product.setUpdated(new Date());
			// 调用dao层方法：新增方法
		    rs = aProductDao.insertProduct(product);
		}
		if (rs > 0) {
			return SverResponse.createRespBySuccessMessage("对商品操作成功！");
		}
		return SverResponse.createByErrorMessage("对商品操作失败！");
		
	}
	
}

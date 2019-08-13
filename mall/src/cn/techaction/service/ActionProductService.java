package cn.techaction.service;

import java.util.List;

import cn.techaction.common.SverResponse;
import cn.techaction.pojo.ActionProduct;
import cn.techaction.utils.PageBean;
import cn.techaction.vo.ActionProductFloorVo;
import cn.techaction.vo.ActionProductListVo;

public interface ActionProductService {
	
    /**
     * 门户，获得首页所有楼层数据
     */
    public SverResponse<ActionProductFloorVo> findFloorProducts ();

	// public SverResponse<List<ActionProduct>> findHotProducts(Integer num);
	/**
	 * 分页查询商品
	 * @param productId
	 * @param partsId
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public SverResponse<PageBean<ActionProduct>> findProduct(Integer productId,Integer partsId
			,Integer pageNum,Integer pageSize);

  
	/**
	 * 门户，查找热门商品
	 * @param num
	 * @return
	 */
	public SverResponse<List<ActionProduct>> findHotProducts(Integer num);

	/**
	 * 根据商品编号，查找商品信息
	 * @param productId
	 * @return
	 */
	public SverResponse<ActionProduct> findProductDetailForPortal(Integer id);

	/**
	 * 门户，根据产品类型和配件类型查找商品信息（模糊查询）
	 * @param productTypeId
	 * @param partsId
	 * @param name
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public SverResponse<PageBean<ActionProductListVo>> findProductsForPortal(Integer productTypeId, Integer partsId,
			String name, int pageNum, int pageSize);
	
	/**
	 * 多条件查询商品信息
	 * @param product
	 * @return
	 */
	public SverResponse<List<ActionProductListVo>> findProducts(ActionProduct product);

	/**
	 * 更新商品状态：上下架，热销
	 * @param productId
	 * @param status
	 * @param hot
	 * @return
	 */
	public SverResponse<String> updateStatus(Integer productId,Integer status,Integer hot);
	
	
	/**
	 * 保存商品信息（新增、修改）
	 * @param product
	 * @return
	 */
	public SverResponse<String> savsOrUpdateProduct(ActionProduct product);

	/**
	 * 根据商品名查找商品
	 * @param name
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public SverResponse<PageBean<ActionProductListVo>> findProductsByName(String name, int pageNum, int pageSize);
}

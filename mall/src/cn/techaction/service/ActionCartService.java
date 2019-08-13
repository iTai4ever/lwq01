package cn.techaction.service;

import cn.techaction.common.SverResponse;
import cn.techaction.vo.ActionCartVo;

public interface ActionCartService {
	/**
	 * 保存商品到购物车
	 * @param id
	 * @param productId
	 * @param count
	 * @return
	 */
	public SverResponse<String> saveOrUpdate(Integer userId, Integer productId, Integer count);
	/**
	 * 查询用户购物车中商品信息
	 * @param id
	 * @return
	 */
	public SverResponse<ActionCartVo> findAllCarts(Integer userId);
	/**
	 * 清空购物车
	 * @param id
	 * @return
	 */
	public SverResponse<String> clearCart(Integer userId);
	/**
	 * 更新购物车商品数据
	 * @param id
	 * @param productId
	 * @param count
	 * @param checked
	 * @return
	 */
	public SverResponse<ActionCartVo> updateCart(Integer userId, Integer productId, Integer count, Integer checked);
	/**
	 * 删除购物车商品
	 * @param id
	 * @param produtId
	 * @return
	 */
	public SverResponse<ActionCartVo> deleteCart(Integer userId, Integer productId);
	/**
	 * 获取登陆用户购物车中商品的个数
	 * @param id
	 * @return
	 */
	public SverResponse<Integer> getCartCount(Integer userId);
	/**
	 * 获取购物车中被选中的商品
	 * @param id
	 * @return
	 */
	public SverResponse<ActionCartVo> findCheckedCarts(Integer userId);

}

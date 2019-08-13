package cn.techaction.dao;

import java.util.List;

import cn.techaction.pojo.ActionCart;

public interface ActionCartDao {
	/**
	 * 查找用户购物车中的商品信息
	 * @param uid
	 * @return
	 */
	public List<ActionCart> findCartByUser(Integer uid);
	/**
	 * 根据userID和商品id查找购物车
	 * @param userId
	 * @param productId
	 * @return
	 */
	public ActionCart findCartByUserAndProductId(Integer userId, Integer productId);
	/**
	 * 保存购物车
	 * @param cart
	 */
	public int insertCart(ActionCart cart);
	/**
	 * 更新购物车中商品数量
	 * @param actionCart
	 */
	public int updateCartById(ActionCart actionCart);
	/**
	 * 删除某个用户购物车所有商品
	 * @param userId
	 * @return
	 */
	public int deleteCartByUserId(Integer userId);
	/**
	 * 更新购物车中的商品数量
	 * @param actionCart
	 */
	public int updateCartByUserIdAndProductId(ActionCart actionCart);
	/**
	 * 删除购物车商品信息
	 * @param userId
	 * @param productId
	 * @return
	 */
	public int deleteCarts(Integer userId, Integer productId);
	/**
	 * 获取当前用户购物车商品数量
	 * @param userId
	 * @return
	 */
	public int getCartCountByUserId(Integer userId);
	/**
	 * 获取被选中商品
	 * @param userId
	 * @return
	 */
	public List<ActionCart> findCheckedCartByUser(Integer userId);

}

package cn.techaction.dao;

import java.util.List;

import cn.techaction.pojo.ActionOrder;
import cn.techaction.vo.ActionUserVo;

public interface ActionOrderDao {
	/**
	 * 获取用户订单总数（各种状态）
	 * @param uid
	 * @param status
	 * @return
	 */
	public int getTotalRecord(Integer uid, Integer status);
	/**
	 * 获取用户订单分页列表
	 * @param uid
	 * @param status
	 * @param startIndex
	 * @param pageSize
	 * @return
	 */
	public List<ActionOrder> findOrders(Integer uid, Integer status, int startIndex, int pageSize);
	/**
	 * 根据用户和订单标号查询订单信息
	 * @param uid
	 * @param orderNo
	 * @return
	 */
	public ActionOrder findOrderByUserAndOrderNo(Integer uid, Long orderNo);
	/**
	 * 更新订单信息
	 * @param updateOrder
	 * @return
	 */
	public int updateOrder(ActionOrder updateOrder);
	/**
	 * 保存订单信息
	 * @param order
	 * @return
	 */
	public int insertOrder(ActionOrder order);
	/**
	 * 根据订单号查询订单信息，后台
	 * @param orderNo
	 * @return
	 */
	public List<ActionOrder> searchOrders(Long orderNo);
	/**
	 * 根据用户id 返回用户信息
	 * @param id
	 * @return
	 */
	public List<ActionOrder> findOrderByUid(Integer uid);
	/**
	 * 根据订单号获得订单详情，后台
	 * @param orderNo
	 * @return
	 */
	public ActionOrder findOrderDetailByNo(Long orderNo);

}

package cn.techaction.dao;

import java.util.List;

import cn.techaction.pojo.ActionOrderItem;

public interface ActionOrderItemDao {
	/**
	 * 根据订单号获得订单项
	 * @param orderNo
	 * @return
	 */
	public List<ActionOrderItem> getItemByOrderNo(Long orderNo);
	/**
	 * 批量插入订单项
	 * @param orderItems
	 */
	public int[] batchInsert(List<ActionOrderItem> orderItems);
	
}

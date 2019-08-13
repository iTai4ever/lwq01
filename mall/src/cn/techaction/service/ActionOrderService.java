package cn.techaction.service;

import java.util.List;

import cn.techaction.common.SverResponse;
import cn.techaction.utils.PageBean;
import cn.techaction.vo.ActionOrderVo;

public interface ActionOrderService {
	/**
	 * 订单分页
	 * @param id
	 * @param status
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public SverResponse<PageBean<ActionOrderVo>> findOrders(Integer uid, Integer status, int pageNum, int pageSize);
	/**
	 * 取消订单
	 * @param id
	 * @param orderNo
	 * @return
	 */
	public SverResponse<String> cancelOrder(Integer uid, Long orderNo);
	/**
	 * 根据编号获取订单详情
	 * @param id
	 * @param orderNo
	 * @return
	 */
	public SverResponse<ActionOrderVo> findOrderDetail(Integer uid, Long orderNo);
	/**
	 * 生成订单
	 * @param id
	 * @param addrId
	 * @return
	 */
	public SverResponse<ActionOrderVo> generateOrder(Integer uid, Integer addrId);
	/**
	 * 后台查询订单信息，不分页
	 * @param orderNo
	 * @return
	 */
	public SverResponse<List<ActionOrderVo>> findOrderForNoPages(Long orderNo);
	/**
	 * 后台订单详情
	 * @param orderNo
	 * @return
	 */
	public SverResponse<ActionOrderVo> mgrDetail(Long orderNo);
}

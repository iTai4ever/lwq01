package cn.techaction.service;

import java.util.List;

import cn.techaction.common.SverResponse;
import cn.techaction.pojo.ActionAddress;

public interface ActionAddrService {
    /**
     * 新增收件人地址信息
     * @param addr
     * @return
     */
	public SverResponse<String> addAddress(ActionAddress addr);
	/**
	 * 更新收件人地址
	 * @param addr
	 * @return
	 */
	public SverResponse<String> updateAddress(ActionAddress addr);
	/**
	 * 通过用户ID找到地址
	 * @param id
	 * @return
	 */
	public SverResponse<List<ActionAddress>> findAddrByUserId(Integer userId);
	/**
	 * 根据id删除用户信息
	 * @param id
	 * @param id2
	 * @return
	 */
	public SverResponse<String> delAddress(Integer userId, Integer id);
	/**
	 * 设置成默认地址
	 * @param id
	 * @param id2
	 * @return
	 */
	public SverResponse<String> updataAddrDefaultStatus(Integer userId, Integer id);
	/**
	 * 
	 * @param addrId
	 * @return
	 */
	public SverResponse<ActionAddress> findAddrById(Integer addrId);

	
}

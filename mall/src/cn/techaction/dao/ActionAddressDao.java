package cn.techaction.dao;
import java.util.List;

import cn.techaction.pojo.*;
public interface ActionAddressDao {
	/**
	 * 是否存在默认地址
	 * @param uid
	 * @return
	 */
	public int findDefaultAddrByUserId(Integer userId);
	/**
	 * 新增收货人地址信息
	 * 
	 * @param addr
	 * @return
	 */
	public int insertAddress(ActionAddress addr);
	/**
	 * 更新地址
	 * @param addr
	 * @return
	 */
	public int updateAddress(ActionAddress addr);
	/**
	 * 通过用户id找到地址
	 * @param userId
	 * @return
	 */
	public List<ActionAddress> findAddrByUserId(Integer userId);
	/**
	 * 读取用户默认地址
	 * @param userId
	 * @return
	 */
	
	public ActionAddress findDefaultAddr(Integer userId);
	
	/**
	 * 根据id查询收货人地址信息
	 * @param addrId
	 * @return
	 */
	public ActionAddress findAddrsById(Integer addrId);

	

}

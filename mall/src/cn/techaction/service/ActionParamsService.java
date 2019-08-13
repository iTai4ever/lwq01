package cn.techaction.service;

import java.util.List;

import cn.techaction.common.SverResponse;
import cn.techaction.pojo.ActionParam;
import cn.techaction.vo.ActionParamVo;

public interface ActionParamsService {
	/**
	 * 查找子节点
	 * @param id
	 * @return
	 */
	public SverResponse<List<ActionParam>> findParamChildren(Integer id);
	
	/**删除指定类型
	 * @param id
	 * @return
	 */
	public SverResponse<String> delParam(Integer id);
	
	/**
	 * 新增类型
	 * @param actionParam
	 * @return
	 */
	public SverResponse<String> addParam(ActionParam actionParam);
	
	/**修改商品类型
	 * @param actionParam
	 * @return
	 */
	public SverResponse<String> updateParam(ActionParam actionParam);

	/**
	 * 查询产品类型参数
	 * @param id
	 * @return
	 */
	public SverResponse<List<ActionParam>> findProductType();

	/**
	 * 查找子类型id和name
	 * @param id
	 * @return
	 */
	public SverResponse<List<ActionParamVo>> findPartsType(Integer id);

}

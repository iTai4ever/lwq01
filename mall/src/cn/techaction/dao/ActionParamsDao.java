package cn.techaction.dao;

import java.util.List;

import cn.techaction.pojo.ActionParam;
import cn.techaction.vo.ActionParamVo;
//import cn.techaction.service.Impl.Action;

public interface ActionParamsDao {
	
	/**
	 * 根据节点id查找参考对象
	 * @param id
	 * @return
	 */
	public ActionParam findParamById(Integer id);

	/**
	 * 根据父节点id查找子节点参数
	 * 根据父类型查询该父类型下所有子类型
	 * @param i
	 * @return
	 */
	public List<ActionParam> findParamsByParentId(Integer parentId);
	
	/**
	 * 根据id删除对象
	 * @param id
	 * @return
	 */
	public int deleteParam(Integer id);
	
	/**
	 * 根据父类型id和类型名查找信息
	 * @param parentId
	 * @param name
	 * @return
	 */
	public ActionParam findParamByParentIdAndName(Integer parentId,String name);
	
	/**
	 * 新增类型
	 * @param param
	 * @return
	 */
	public int insetParam(ActionParam param);
	//public List<ActionParam>
	
	/**修改商品类型
	 * @param param
	 * @return
	 */
	public int updateParam(ActionParam param);

	/**
	 * 根据id查询商品类型参数
	 * @param id
	 * @return
	 */
	public List<ActionParam> findParamsBypId(Integer id);

	public List<ActionParamVo> findPartsTypeByParentId(Integer id);

}

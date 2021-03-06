package cn.techaction.service.Impl;


import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import cn.techaction.common.SverResponse;
import cn.techaction.dao.ActionParamsDao;
import cn.techaction.dao.ActionProductDao;
import cn.techaction.pojo.ActionParam;
import cn.techaction.pojo.ActionProduct;
import cn.techaction.service.ActionParamsService;
import cn.techaction.vo.ActionParamVo;

@Service
public class ActionParamsServiceImpl implements ActionParamsService{

	@Autowired
	private ActionParamsDao actionParamsDao;
	@Autowired
	private ActionProductDao actionProductDao;
	
	@Override
	public SverResponse<List<ActionParam>> findParamChildren(Integer id) {
		// TODO Auto-generated method stub
		//调用Dao层方法
		List<ActionParam> params = actionParamsDao.findParamsByParentId(id);
		return SverResponse.createRespBySuccess(params);
	}

	@Override
	public SverResponse<String> delParam(Integer id) {
		// TODO Auto-generated method stub
		//1.判断当前类型是否有子类型
		List<ActionParam> params = actionParamsDao.findParamsByParentId(id);
		if(params.size()!=0){
			return SverResponse.createByErrorMessage("请先删除子类型！");
		}
		//2.判断当前类型是否被商品使用
		List<ActionProduct> products = actionProductDao.findProductsByPartsId(id);
		if(products.size()!=0){
			return SverResponse.createByErrorMessage("不能删除有商品的类型！");
		}
		//3.调用Dao层方法
		int rs = actionParamsDao.deleteParam(id);
		if(rs==0){
			return SverResponse.createByErrorMessage("删除失败!");
		}
		return SverResponse.createRespBySuccessMessage("删除成功");
	}

	@Override
	public SverResponse<String> addParam(ActionParam actionParam) {
		// TODO Auto-generated method stub
		if(StringUtils.isBlank(actionParam.getName())){
			return SverResponse.createByErrorMessage("参数异常!");
		}
		//判断新增的类型名在同一类型中是否重名
		ActionParam param = actionParamsDao.findParamByParentIdAndName(actionParam.getParent_id(), actionParam.getName());
		if(param!=null){
			return SverResponse.createByErrorMessage("商品类型名已经存在！");
		}
		//调用Dao层方法新增类型
		
		actionParam.setStatus(true);
		actionParam.setCreated(new Date());
		actionParam.setUpdated(new Date());
		actionParam.setLevel(this.getParamLevel(actionParam.getParent_id()));
		int rs = actionParamsDao.insetParam(actionParam);
		if(rs>0){
			return SverResponse.createRespBySuccessMessage("新增类型成功！");
		}
		return SverResponse.createByErrorMessage("新增失败！");
	}
	
	/**
	 * 计算新增类型节点的level，实际上是父类型加1
	 * 如果新增为跟类型，level为0
	 * @param parentId
	 * @return
	 */
	private int getParamLevel(int parentId){
		ActionParam param = actionParamsDao.findParamById(parentId);
		if(param!=null){
			return param.getLevel()+1;
		}
		
		return 0;
	}

	@Override
	public SverResponse<String> updateParam(ActionParam actionParam) {
		// TODO Auto-generated method stub
		//1.判断参数异常
		if(actionParam.getId()==0||StringUtils.isBlank(actionParam.getName())){
			return SverResponse.createByErrorMessage("参数异常！");
		}
		//2.判断重名问题
		ActionParam param = actionParamsDao.findParamByParentIdAndName(actionParam.getParent_id(), actionParam.getName());
		if(param!=null){
			return SverResponse.createByErrorMessage("商品类型名已经存在！");
		}
		//3.属性修改
		ActionParam origin = actionParamsDao.findParamById(actionParam.getId());
		origin.setName(actionParam.getName());
		origin.setUpdated(new Date());
		//调用Dao层方法
		int rs = actionParamsDao.updateParam(origin);
		if(rs>0){
			
			return SverResponse.createRespBySuccessMessage("修改类型成功！");
		}
		//return SverResponse.createRespBySuccessMessage("删除成功");
		return SverResponse.createByErrorMessage("修改类型失败!");
	
	}

	@Override
	public SverResponse<List<ActionParam>> findProductType() {
		// TODO Auto-generated method stub
		List<ActionParam> params = actionParamsDao.findParamsBypId(0);
		return SverResponse.createRespBySuccess(params);
	}

	@Override
	public SverResponse<List<ActionParamVo>> findPartsType(Integer id) {
		// TODO Auto-generated method stub
		List<ActionParamVo> params = actionParamsDao.findPartsTypeByParentId(id);
		return SverResponse.createRespBySuccess(params);
	}

}

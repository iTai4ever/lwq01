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
		//����Dao�㷽��
		List<ActionParam> params = actionParamsDao.findParamsByParentId(id);
		return SverResponse.createRespBySuccess(params);
	}

	@Override
	public SverResponse<String> delParam(Integer id) {
		// TODO Auto-generated method stub
		//1.�жϵ�ǰ�����Ƿ���������
		List<ActionParam> params = actionParamsDao.findParamsByParentId(id);
		if(params.size()!=0){
			return SverResponse.createByErrorMessage("����ɾ�������ͣ�");
		}
		//2.�жϵ�ǰ�����Ƿ���Ʒʹ��
		List<ActionProduct> products = actionProductDao.findProductsByPartsId(id);
		if(products.size()!=0){
			return SverResponse.createByErrorMessage("����ɾ������Ʒ�����ͣ�");
		}
		//3.����Dao�㷽��
		int rs = actionParamsDao.deleteParam(id);
		if(rs==0){
			return SverResponse.createByErrorMessage("ɾ��ʧ��!");
		}
		return SverResponse.createRespBySuccessMessage("ɾ���ɹ�");
	}

	@Override
	public SverResponse<String> addParam(ActionParam actionParam) {
		// TODO Auto-generated method stub
		if(StringUtils.isBlank(actionParam.getName())){
			return SverResponse.createByErrorMessage("�����쳣!");
		}
		//�ж���������������ͬһ�������Ƿ�����
		ActionParam param = actionParamsDao.findParamByParentIdAndName(actionParam.getParent_id(), actionParam.getName());
		if(param!=null){
			return SverResponse.createByErrorMessage("��Ʒ�������Ѿ����ڣ�");
		}
		//����Dao�㷽����������
		
		actionParam.setStatus(true);
		actionParam.setCreated(new Date());
		actionParam.setUpdated(new Date());
		actionParam.setLevel(this.getParamLevel(actionParam.getParent_id()));
		int rs = actionParamsDao.insetParam(actionParam);
		if(rs>0){
			return SverResponse.createRespBySuccessMessage("�������ͳɹ���");
		}
		return SverResponse.createByErrorMessage("����ʧ�ܣ�");
	}
	
	/**
	 * �����������ͽڵ��level��ʵ�����Ǹ����ͼ�1
	 * �������Ϊ�����ͣ�levelΪ0
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
		//1.�жϲ����쳣
		if(actionParam.getId()==0||StringUtils.isBlank(actionParam.getName())){
			return SverResponse.createByErrorMessage("�����쳣��");
		}
		//2.�ж���������
		ActionParam param = actionParamsDao.findParamByParentIdAndName(actionParam.getParent_id(), actionParam.getName());
		if(param!=null){
			return SverResponse.createByErrorMessage("��Ʒ�������Ѿ����ڣ�");
		}
		//3.�����޸�
		ActionParam origin = actionParamsDao.findParamById(actionParam.getId());
		origin.setName(actionParam.getName());
		origin.setUpdated(new Date());
		//����Dao�㷽��
		int rs = actionParamsDao.updateParam(origin);
		if(rs>0){
			
			return SverResponse.createRespBySuccessMessage("�޸����ͳɹ���");
		}
		//return SverResponse.createRespBySuccessMessage("ɾ���ɹ�");
		return SverResponse.createByErrorMessage("�޸�����ʧ��!");
	
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

package cn.techaction.service;

import java.util.List;

import cn.techaction.common.SverResponse;
import cn.techaction.pojo.ActionParam;
import cn.techaction.vo.ActionParamVo;

public interface ActionParamsService {
	/**
	 * �����ӽڵ�
	 * @param id
	 * @return
	 */
	public SverResponse<List<ActionParam>> findParamChildren(Integer id);
	
	/**ɾ��ָ������
	 * @param id
	 * @return
	 */
	public SverResponse<String> delParam(Integer id);
	
	/**
	 * ��������
	 * @param actionParam
	 * @return
	 */
	public SverResponse<String> addParam(ActionParam actionParam);
	
	/**�޸���Ʒ����
	 * @param actionParam
	 * @return
	 */
	public SverResponse<String> updateParam(ActionParam actionParam);

	/**
	 * ��ѯ��Ʒ���Ͳ���
	 * @param id
	 * @return
	 */
	public SverResponse<List<ActionParam>> findProductType();

	/**
	 * ����������id��name
	 * @param id
	 * @return
	 */
	public SverResponse<List<ActionParamVo>> findPartsType(Integer id);

}

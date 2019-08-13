package cn.techaction.dao;

import java.util.List;

import cn.techaction.pojo.ActionParam;
import cn.techaction.vo.ActionParamVo;
//import cn.techaction.service.Impl.Action;

public interface ActionParamsDao {
	
	/**
	 * ���ݽڵ�id���Ҳο�����
	 * @param id
	 * @return
	 */
	public ActionParam findParamById(Integer id);

	/**
	 * ���ݸ��ڵ�id�����ӽڵ����
	 * ���ݸ����Ͳ�ѯ�ø�����������������
	 * @param i
	 * @return
	 */
	public List<ActionParam> findParamsByParentId(Integer parentId);
	
	/**
	 * ����idɾ������
	 * @param id
	 * @return
	 */
	public int deleteParam(Integer id);
	
	/**
	 * ���ݸ�����id��������������Ϣ
	 * @param parentId
	 * @param name
	 * @return
	 */
	public ActionParam findParamByParentIdAndName(Integer parentId,String name);
	
	/**
	 * ��������
	 * @param param
	 * @return
	 */
	public int insetParam(ActionParam param);
	//public List<ActionParam>
	
	/**�޸���Ʒ����
	 * @param param
	 * @return
	 */
	public int updateParam(ActionParam param);

	/**
	 * ����id��ѯ��Ʒ���Ͳ���
	 * @param id
	 * @return
	 */
	public List<ActionParam> findParamsBypId(Integer id);

	public List<ActionParamVo> findPartsTypeByParentId(Integer id);

}

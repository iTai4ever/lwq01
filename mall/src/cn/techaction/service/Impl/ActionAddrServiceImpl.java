package cn.techaction.service.Impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.techaction.common.SverResponse;
import cn.techaction.dao.ActionAddressDao;
import cn.techaction.pojo.ActionAddress;
import cn.techaction.service.ActionAddrService;
@Service
public class ActionAddrServiceImpl implements ActionAddrService {
	@Autowired
	private ActionAddressDao aAddressDao;

	@Override
	public SverResponse<String> addAddress(ActionAddress addr) {
		// TODO Auto-generated method stub
		//判断参数
		if(addr==null){
			return SverResponse.createByErrorMessage("参数错误");
		}
		//判断已知地址中是否有当前地址
		//如果没有则本条地址为默认地址
		//否则为一般地址
		int count = aAddressDao.findDefaultAddrByUserId(addr.getUid());
		if(count==0){
			addr.setDefault_addr(1);
		}else{
			addr.setDefault_addr(0);
		}
		addr.setCreated(new Date());
		addr.setUpdated(new Date());
		int rs = aAddressDao.insertAddress(addr);
		if(rs>0){
			return SverResponse.createRespBySuccessMessage("地址新增成功");
			
			
		}
		return SverResponse.createByErrorMessage("地址新增失败");
	}

	@Override
	public SverResponse<String> updateAddress(ActionAddress addr) {
		// TODO Auto-generated method stub
		//判断参数
		if(addr==null){
			return SverResponse.createByErrorMessage("参数错误");
		}
		addr.setUpdated(new Date());
		int rs = aAddressDao.updateAddress(addr);
		if(rs>0){
			SverResponse.createRespBySuccessMessage("地址更新成功");
		}
		return SverResponse.createByErrorMessage("地址更新失败");
	}

	@Override
	public SverResponse<List<ActionAddress>> findAddrByUserId(Integer userId) {
		// TODO Auto-generated method stub
		//先来判断参数
		if(userId==null){
			return SverResponse.createByErrorMessage("参数错误");}
		List<ActionAddress> list = aAddressDao.findAddrByUserId(userId);
		return SverResponse.createRespBySuccess(list);
		//return null;
	}

	@Override
	public SverResponse<String> delAddress(Integer userId, Integer id) {
		// TODO Auto-generated method stub
		//先判断参数存不存在
		if(userId==null){
			return SverResponse.createByErrorMessage("参数错误");}
		//删除地址，对del_state进行更新
		ActionAddress address = new ActionAddress();
		address.setId(id);
		address.setDel_state(1);
		address.setUpdated(new Date());
		int rs = aAddressDao.updateAddress(address);
		if(rs>0){
			return SverResponse.createRespBySuccessMessage("地址删除成功");
		}
		return SverResponse.createByErrorMessage("地址删除失败");
	}

	@Override
	public SverResponse<String> updataAddrDefaultStatus(Integer userId, Integer id) {
		// TODO Auto-generated method stub
		//判断参数
//		System.out.println(id);
//		System.out.println(userId);
		if(id==null||userId==null){
			//System.out.println(id);
			return SverResponse.createByErrorMessage("参数错误");}
		ActionAddress OldAddr = aAddressDao.findDefaultAddr(userId);
		if(OldAddr!=null){
			OldAddr.setDefault_addr(0);
			OldAddr.setUpdated(new Date());
			if(aAddressDao.updateAddress(OldAddr)<=0){
				return SverResponse.createByErrorMessage("设置默认地址失败");
			}
			
		}//设置新的默认地址
		ActionAddress newAddr = new ActionAddress();
		newAddr.setDefault_addr(1);
		newAddr.setId(id);
		newAddr.setUpdated(new Date());
		if(aAddressDao.updateAddress(newAddr)<=0){
			return SverResponse.createByErrorMessage("默认地址设置失败");
		}
		return SverResponse.createRespBySuccessMessage("设置默认地址成功");
	}
	
	@Override
	public SverResponse <ActionAddress> findAddrById(Integer addrId) {
		// TODO Auto-generated method stub
		if(addrId==null){
			return SverResponse.createByErrorMessage("参数错误");}
		ActionAddress address = aAddressDao.findAddrsById(addrId);
		return SverResponse.createRespBySuccess(address);
		//return null;
	}

}

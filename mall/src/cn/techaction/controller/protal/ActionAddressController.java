package cn.techaction.controller.protal;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sun.jndi.cosnaming.IiopUrl.Address;

import cn.techaction.common.SverResponse;
import cn.techaction.pojo.ActionAddress;
import cn.techaction.pojo.User;
import cn.techaction.service.ActionAddrService;
import cn.techaction.utils.*;

@Controller
@RequestMapping("mgr/addr")
public class ActionAddressController {
	@Autowired
	private ActionAddrService aAddrService;
	/**
	 * 新增地址
	 * @param session
	 * @param addr
	 * @return
	 */
	@RequestMapping("/saveaddr.do")
	@ResponseBody
	public SverResponse<List<ActionAddress>> saveAddress(HttpSession session,ActionAddress addr){
		User user = (User) session.getAttribute(ConstUtil.CUR_USER);
		if(user==null){
			return SverResponse.createByErrorMessage("请登录后进行操作");
			
		}
		addr.setUid(user.getId());
		//System.out.println(addr.getId());
		//判断是修改还是添加
		SverResponse<String> result =null;
		if(addr.getId()==null){
			result = aAddrService.addAddress(addr);
			//System.out.println("增加地址"+result);
		}else{
			result=aAddrService.updateAddress(addr);
			//System.out.println("=更新地址"+result);
		}
		
		//添加成功，返回当前用户的所有地址
		if(result.isSuccess()){
			return aAddrService.findAddrByUserId(user.getId()); 
		}
		return SverResponse.createByErrorMessage(result.getMsg());
	}
	
	/**
	 * 删除地址
	 * @param session
	 * @param id
	 * @return
	 */
	@RequestMapping("/deladdr.do")
	@ResponseBody
	public SverResponse<List<ActionAddress>> deleteAddress(HttpSession session,Integer id){
		User user = (User) session.getAttribute(ConstUtil.CUR_USER);
		if(user==null){
			return SverResponse.createByErrorMessage("请登录后进行操作");
			
		}
		//隐性删除地址
		SverResponse<String> result = aAddrService.delAddress(user.getId(),id);
		//删除成功后返回当前用户
		if(result.isSuccess()){
			return aAddrService.findAddrByUserId(user.getId());
		}
		return SverResponse.createByErrorMessage(result.getMsg());
		
	}
	
	/**
	 * 设置默认地址
	 */
	@RequestMapping("/setdefault.do")
	@ResponseBody
	public SverResponse<List<ActionAddress>> setDefault(HttpSession session,Integer id){
		User user = (User) session.getAttribute(ConstUtil.CUR_USER);
		if(user==null){
			return SverResponse.createByErrorMessage("请登录后进行操作");
			
		}
		//System.out.println("当前的地址id是"+id);
		SverResponse<String> result = aAddrService.updataAddrDefaultStatus(user.getId(), id);
		if(result.isSuccess()){
			return aAddrService.findAddrByUserId(user.getId());
		}
		return SverResponse.createByErrorMessage(result.getMsg());
	}
	/**
	 * 通过登录用户id展示用户地址
	 * @param session
	 * @return
	 */
	@RequestMapping("/findaddr.do")
	@ResponseBody
	public SverResponse<List<ActionAddress>> findAddr(HttpSession session){
		User user = (User) session.getAttribute(ConstUtil.CUR_USER);
		if(user==null){
			return SverResponse.createByErrorMessage("请登录后进行操作");
			
		}
		return aAddrService.findAddrByUserId(user.getId());
	}
	/**
	 * 
	 * @param session
	 * @param addrId
	 * @return
	 */
	@RequestMapping("/findaddrbyid.do")
	@ResponseBody
	public SverResponse <ActionAddress> findAddrById(HttpSession session,Integer addrId){
		User user = (User) session.getAttribute(ConstUtil.CUR_USER);
		if(user==null){
			return SverResponse.createByErrorMessage("请登录后进行操作");
			
		}
		return aAddrService.findAddrById(addrId);
	}

}
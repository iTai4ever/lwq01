 package cn.techaction.controller.backstage;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mysql.jdbc.interceptors.SessionAssociationInterceptor;

import cn.techaction.common.ResponseCode;
import cn.techaction.common.SverResponse;
import cn.techaction.pojo.User;
import cn.techaction.service.ActionUserService;
import cn.techaction.utils.ConstUtil;
import cn.techaction.vo.ActionUserVo;

@Controller
@RequestMapping("/mgr/user")//映射地址，用于URL查找
public class ActionUserBackController {
	@Autowired//省略掉get，set方法，这样我们就不用重新写
	private ActionUserService actionUserService;
	@RequestMapping(value="/backlogin.do",method=RequestMethod.POST)
	@ResponseBody
	public SverResponse<User> BackLogin(HttpSession session,String account,String password) {
		//1.调用Sevice层的方法：登录
		SverResponse<User> response=actionUserService.doLogin(account, password);
		//2.判断是否能登录
		if(response.isSuccess()) { 
			//3.能登录则判断是否是管理员，是管理员存放在session，否则错误信息
			User user=response.getData();
			if(user.getRole()==ConstUtil.Role.ROLE_ADMIN) {
				session.setAttribute(ConstUtil.CUR_USER, user);
				return response;
			}
			return SverResponse.createByErrorMessage("不是管理员，无法登录！");
		}
		return response;
	}
	@RequestMapping(value="/finduserlist.do",method=RequestMethod.POST)
	@ResponseBody
	public SverResponse<List<ActionUserVo>> getUserDetail(HttpSession session){
		//判断用户是否登录
		//return actionUserService.findUserList();
		return actionUserService.findUserList();
		
		
//		User user = (User)session.getAttribute(ConstUtil.CUR_USER);
//		if(user==null){
//			//System.out.println(ResponseCode.UNLOGIN);
//			//return SverResponse.createByErrorCodeMessage(ResponseCode.UNLOGIN.getcode(), "请登陆后在进行操作");
//			return SverResponse.createByErrorMessage("请登录后进行操作");
//			
//		}
//		
//		//判断用户是否是管理员
//		SverResponse<String> reponse = actionUserService.isAdmin(user);
//		if(reponse.isSuccess()){
//		//调用service中的方法获取所有的用户信息
//			return actionUserService.findUserList();
//		}
//		
//		return null;
		
	}
	@RequestMapping("/finduser.do")
	@ResponseBody
	public SverResponse<ActionUserVo> findUser(HttpSession session,Integer id){
		//判断用户是否登录
		
		return actionUserService.findUser(id);
		
//		User user = (User)session.getAttribute(ConstUtil.CUR_USER);
//		if(user==null){
//			//System.out.println(ResponseCode.UNLOGIN);
//			//return SverResponse.createByErrorCodeMessage(ResponseCode.UNLOGIN.getcode(), "请登陆后在进行操作");
//			return SverResponse.createByErrorMessage("请登录后进行操作");
//			
//		}
//		
//		//判断用户是否是管理员
//		SverResponse<String> reponse = actionUserService.isAdmin(user);
//		if(reponse.isSuccess()){
//			return actionUserService.findUser(id);
//			
//		}
//		return SverResponse.createRespBySuccessMessage("暂无操作权限");
		//使用service层使用用户ID获得用户信息
	}
	
	/**
	 * 管理员修改用户信息
	 */
	@RequestMapping(value="/backupdateuserinfo.do",method=RequestMethod.POST)
	@ResponseBody
	public SverResponse<User> backupdateUserInfo(HttpSession session,ActionUserVo userVo){
		//判断用户是否登录
		return actionUserService.updateUserInfo(userVo);
//		
//		User curUser = (User) session.getAttribute(ConstUtil.CUR_USER);
//		if(curUser==null){
//			return SverResponse.createByErrorMessage("用户尚未登录");
//			
//		}
//		//判断用户是否是管理员
//		SverResponse<String> reponse = actionUserService.isAdmin(curUser);
//		if(reponse.isSuccess()){
//			return actionUserService.updateUserInfo(userVo);
//			
//		}
////		userVo.setId(curUser.getId());
////		userVo.setAccount(curUser.getAccount());
////		SverResponse<User> resp  =actionUserService.updateUserInfo(userVo);
////		if(resp.isSuccess()){
////			//重写session
////			session.setAttribute(ConstUtil.CUR_USER, resp.getData());
////			
////		}
//		return SverResponse.createByErrorMessage("暂无操作权限");
		
		
	}
	/**
	 * 删除用户信息，实际上就是把del置为1
	 * @param session
	 * @param id
	 * @return
	 */
	@RequestMapping("/deleteUsers.do")
	@ResponseBody
	public SverResponse<String> delUsers(HttpSession session,Integer id){
		
		return actionUserService.delUser(id);
//		
//		//判断用户登录
//		User curUser = (User)session.getAttribute(ConstUtil.CUR_USER);
//		if(curUser==null){
//			return SverResponse.createByErrorMessage("请登陆后在进行操作");
//			
//		}
//		//判断用户是否是管理员
//		SverResponse<String> reponse = actionUserService.isAdmin(curUser);
//		if(reponse.isSuccess()){
//		
//			//调用service层，删除方法
//			return actionUserService.delUser(id);
//		}
//			return SverResponse.createByErrorMessage("暂无操作权限");
//		
	}
}
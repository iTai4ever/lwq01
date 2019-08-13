 package cn.techaction.controller.protal;

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
public class ActionUserController {
	@Autowired//省略掉get，set方法，这样我们就不用重新写
	private ActionUserService actionUserService;
	/**
	 * 用户登录
	 * @param session
	 * @param account
	 * @param password
	 * @return
	 */
	@RequestMapping(value="/login.do",method=RequestMethod.POST)
	@ResponseBody
	public SverResponse<User> doLogin(HttpSession session,String account,String password) {
		//1.调用Sevice层的方法：登录
		SverResponse<User> response=actionUserService.doLogin(account, password);
		//2.判断是否能登录
		if(response.isSuccess()) {
			//3.能登录则判断是否是管理员，是管理员存放在session，否则错误信息
				//登录成功，我们将用户信息存入session
				session.setAttribute(ConstUtil.CUR_USER, response.getData());//第一个参数是类型
				//System.out.println("王哈哈最帅");
				return response;
			}
		
		return response;
		
	}
	/**
	 * 用户注册
	 * @param user
	 * @return
	 */
	@RequestMapping(value="/do_register.do",method=RequestMethod.POST)
	@ResponseBody
	public SverResponse<String> registerUser(User user){
		return actionUserService.doRegister( user);
	}
	/**
	 * 验证用户，获得用户对象
	 */
	@RequestMapping(value="/getuserbyaccount.do",method=RequestMethod.POST)
	@ResponseBody
	public SverResponse<User> getUserByAccount(String account){
		return actionUserService.findUserByAccount(account);
	}
	
	/**
	 * 获取用户修改密码时的问题与并提示答案
	 * @param account
	 * @param question
	 * @param asw
	 * @return
	 */
	@RequestMapping(value="/checkUserAnswer.do",method=RequestMethod.POST)
	@ResponseBody
	public SverResponse<String> checkUserAnswer(String account,String question,String asw){
		return actionUserService.checkUserAnswer(account,question,asw);
		
	}
	
	/**
	 * 设置新的密码
	 * @param userId
	 * @param newpwd
	 * @return
	 */
	@RequestMapping(value="/resetpassword.do",method=RequestMethod.POST)
	@ResponseBody
	public SverResponse<String> resetPasword(Integer userId, String newpwd){
		return actionUserService.resetpassword(userId,newpwd);
	}
	
	/**
	 * 修改用户个人资料
	 * @param session
	 * @param userVo
	 * @return
	 */
	@RequestMapping(value="/updateuserinfo.do",method=RequestMethod.POST)
	@ResponseBody
	public SverResponse<User> updateUserInfo(HttpSession session,ActionUserVo userVo){
		User curUser = (User) session.getAttribute(ConstUtil.CUR_USER);
		if(curUser==null){
			return SverResponse.createByErrorMessage("用户尚未登录");
			
		}
		userVo.setId(curUser.getId());
		userVo.setAccount(curUser.getAccount());
		SverResponse<User> resp  =actionUserService.updateUserInfo(userVo);
		if(resp.isSuccess()){
			//重写session
			session.setAttribute(ConstUtil.CUR_USER, resp.getData());
			
		}
		return resp;
		
		
	}
	/**
	 * 登录用户修改密码
	 * @param userId
	 * @param newpwd
	 * @param oldpwd
	 * @return
	 */
	@RequestMapping(value="/updatepassword.do",method=RequestMethod.POST)
	@ResponseBody
	public SverResponse<String> updatePassword(HttpSession session, String newpwd,String oldpwd){
		//将我们的session取出
		User user = (User)session.getAttribute(ConstUtil.CUR_USER);
		if(user==null){
			return SverResponse.createByErrorMessage("请登陆后在修改密码");
		}
		SverResponse<String> result = actionUserService.updatePassword(user,newpwd,oldpwd);
		//修改成功之后我们将session清空，准备重新登录
		if(result.isSuccess()){
			session.removeAttribute(ConstUtil.CUR_USER);
		}
		return result;
		//return actionUserService.resetpassword(userId,newpwd);
	}
	/**
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/getuserinfo.do",method=RequestMethod.GET)
	@ResponseBody
	public SverResponse<ActionUserVo> getuserinfo(HttpSession session){
		User curUser = (User) session.getAttribute(ConstUtil.CUR_USER);
		//return cu
		if(curUser==null){
			return SverResponse.createByErrorMessage("请登陆后在修改密码");		
		}
		return actionUserService.findUser(curUser.getId());
	}
	/**
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/getuserinfo2.do",method=RequestMethod.GET)
	@ResponseBody
	public SverResponse<ActionUserVo> getuserinfo2(HttpSession session){
		User curUser = (User) session.getAttribute(ConstUtil.CUR_USER);
		//return cu
		return actionUserService.findUser2(curUser.getId());
	}
	/**
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/logout.do",method=RequestMethod.POST)
	@ResponseBody
	public SverResponse<String> logout(HttpSession session){
		session.removeAttribute(ConstUtil.CUR_USER);
		return SverResponse.createRespBySuccessMessage("用户注销成功");
		
	}
	/**
	 * 
	 * @param str
	 * @param type
	 * @return
	 */
	@RequestMapping(value="/do_check_info.do",method=RequestMethod.POST)
	@ResponseBody
	public SverResponse<String> do_check_info(String str,String type){
		//System.out.println("检查信息部分");
		return actionUserService.checkValidation( str, type); 
		
	}


}

package cn.techaction.controller.backstage;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.techaction.common.SverResponse;
import cn.techaction.pojo.ActionParam;
import cn.techaction.pojo.User;
import cn.techaction.service.ActionParamsService;
import cn.techaction.service.ActionUserService;
import cn.techaction.utils.ConstUtil;
import cn.techaction.vo.ActionParamVo;

@Controller
@RequestMapping("/mgr/param")
public class ActionParamsBackController {
	
	@Autowired
	private ActionParamsService actionParamsService;
	@Autowired
	private ActionUserService userService;

	
	/**
	 * 查找下一级子类型（根类型）
	 * @param session
	 * @param id
	 * @return
	 */
	@RequestMapping(value="findchildren.do",method=RequestMethod.GET)
	@ResponseBody
	public SverResponse<List<ActionParam>> getChildrenParam(HttpSession session,@RequestParam(value="id",defaultValue="0")Integer id){
		// 1.判断用户是否登录
		User user = (User) session.getAttribute(ConstUtil.CUR_USER);
		if (user == null) {
			return SverResponse.createByErrorMessage("请登录后再进行操作!");
		}

		// 2.用户是否是管理员
		SverResponse<String> response = userService.isAdmin(user);
		if (response.isSuccess()) {
			// 3.调用Service中的方法获得子类型
			return actionParamsService.findParamChildren(id);
		}
		return SverResponse.createByErrorMessage("无操作权限！");
		
	}
	
	@RequestMapping("/delparam.do")
	@ResponseBody
	public SverResponse<String> delParam(HttpSession session,Integer id){
		// 1.判断用户是否登录
		User user = (User) session.getAttribute(ConstUtil.CUR_USER);
		if (user == null) {
			return SverResponse.createByErrorMessage("请登录后再进行操作!");
		}

		// 2.用户是否是管理员
		SverResponse<String> response = userService.isAdmin(user);
		if (response.isSuccess()) {
			// 3.调用Service中的方法:删除类型
			return actionParamsService.delParam(id);
		}
		return SverResponse.createByErrorMessage("无操作权限！");
		
	}
	
	/**
	 * 新增商品类型
	 * @param session
	 * @param actionParam
	 * @return
	 */
	@RequestMapping("/saveparam.do")
	@ResponseBody
	public SverResponse<String> saveParam(HttpSession session,ActionParam actionParam){
		// 1.判断用户是否登录
		User user = (User) session.getAttribute(ConstUtil.CUR_USER);
		if (user == null) {
			return SverResponse.createByErrorMessage("请登录后再进行操作!");
		}

		// 2.用户是否是管理员
		SverResponse<String> response = userService.isAdmin(user);
		if (response.isSuccess()) {
			// 3.调用Service中的方法:新增类型
			return actionParamsService.addParam(actionParam);
		}
		return SverResponse.createByErrorMessage("无操作权限！");
		
	}
	
	/**
	 * 修改商品类型
	 * @param session
	 * @param actionParam
	 * @return
	 */
	@RequestMapping("/updateparam.do")
	@ResponseBody
	public SverResponse<String> updateCategory(HttpSession session,ActionParam actionParam){
		// 1.判断用户是否登录
		User user = (User) session.getAttribute(ConstUtil.CUR_USER);
		if (user == null) {
			return SverResponse.createByErrorMessage("请登录后再进行操作!");
		}

		// 2.用户是否是管理员
		SverResponse<String> response = userService.isAdmin(user);
		if (response.isSuccess()) {
			// 3.调用Service中的方法:新增类型
			return actionParamsService.updateParam(actionParam);
		}
		return SverResponse.createByErrorMessage("无操作权限！");
		
	}
	
	/**
	 * 根据parent_id查询子类型id和name
	 * @param session
	 * @param id
	 * @return
	 */
	@RequestMapping("/findpartstype.do")
	@ResponseBody
	public SverResponse<List<ActionParamVo>> findPartsType(HttpSession session,Integer id){
		// 1.判断用户是否登录
		User user = (User) session.getAttribute(ConstUtil.CUR_USER);
		if (user == null) {
			return SverResponse.createByErrorMessage("请登录后再进行操作!");
		}

		// 2.用户是否是管理员
		SverResponse<String> response = userService.isAdmin(user);
		if (response.isSuccess()) {
			// 3.调用Service中的方法获得子类型
			return actionParamsService.findPartsType(id);
		}
		return SverResponse.createByErrorMessage("无操作权限！");
		
	}
	
	/**
	 * 获取产品类型参数
	 * @param session
	 * @param id
	 * @return
	 */
	@RequestMapping(value="findptype.do",method=RequestMethod.GET)
	@ResponseBody
	public SverResponse<List<ActionParam>> findProductType(HttpSession session){
		// 1.判断用户是否登录
		User user = (User) session.getAttribute(ConstUtil.CUR_USER);
		if (user == null) {
			return SverResponse.createByErrorMessage("请登录后再进行操作!");
		}

		// 2.用户是否是管理员
		SverResponse<String> response = userService.isAdmin(user);
		if (response.isSuccess()) {
			// 3.调用Service中的方法获得子类型
			return actionParamsService.findProductType();
		}
		return SverResponse.createByErrorMessage("无操作权限！");
		
	}
	
}

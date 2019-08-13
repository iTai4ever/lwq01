 package cn.techaction.service;

import java.util.List;

import cn.techaction.common.SverResponse;
import cn.techaction.pojo.User;
import cn.techaction.vo.ActionUserVo;

public interface ActionUserService {
	/**
	 * 用户登录
	 * @param account
	 * @param password
	 * @return
	 */
	public SverResponse<User> doLogin(String account,String password);
	/**
	 * 用户注册
	 * @param user
	 * @return
	 */
	public SverResponse<String> doRegister(User user);
	/**
	 * 信息校验验证
	 * @param str
	 * @param type
	 * @return
	 */
	public SverResponse<String> checkValidation(String str,String type);
	/**
	 * 根据用户名，获得用户对象
	 * @param account
	 * @return
	 */
	public SverResponse<User> findUserByAccount(String account);
	/**
	 * 获取用户名检查问题答案
	 * @param account
	 * @param question
	 * @param asw
	 * @return
	 */
	public SverResponse<String> checkUserAnswer(String account, String question, String asw);
	/**
	 * 重置密码
	 * @param userId
	 * @param newpwd
	 * @return
	 */
	public SverResponse<String> resetpassword(Integer userId, String password);
	/**
	 * 更新用户信息
	 * @param userVo
	 * @return
	 */
	public SverResponse<User> updateUserInfo(ActionUserVo userVo);
	/**
	 * 重设密码
	 * @param user
	 * @param newpwd
	 * @param oldpwd
	 * @return
	 */
	public SverResponse<String> updatePassword(User user, String newPassword, String oldPassword);
	/**
	 * 判断是否是管理员
	 * @param user
	 * @return
	 */
	public SverResponse<String> isAdmin(User user);
	/**
	 * 找到用户列表，转换为actionVo类型的
	 * @return
	 */
	public SverResponse<List<ActionUserVo>> findUserList();
	/**
	 * 按照ID找到用户
	 * @param id
	 * @return
	 */
	public SverResponse<ActionUserVo> findUser(Integer id);
	
	public SverResponse<String> delUser(Integer id);
	
	public SverResponse<ActionUserVo> findUser2(Integer id);
}

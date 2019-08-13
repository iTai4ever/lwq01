package cn.techaction.dao;

import java.util.List;

import cn.techaction.pojo.User;

public interface ActionUserDao {
	/**
	 * 根据账号查找用户
	 * @param account
	 * @return
	 */
	public int checkUserByAccount(String account);
	/**
	 * 根据用户名和密码查找用户
	 * @param account
	 * @param password
	 * @return
	 */
	public User findUserByAccountAndPassword(String account,String password);
	/**
	 * 验证我们的邮箱是否已经被注册
	 * @param email
	 * @return
	 */
	public int checkUserByEmail(String email);
	/**
	 * 验证电话是否已经被注册
	 * @param phone
	 * @return
	 */
	public int checkUserByPhone(String phone);
	/**
	 * 新增用户
	 * @param user
	 * @return
	 */
	public  int insertUser(User user) ;
	/**
	 * 根据用户名查找用户
	 * @param account
	 * @return
	 */
	public User findUserByAccount(String account);
	/**
	 * 检验用户问题答案是否正确
	 * @param account
	 * @param question
	 * @param asw
	 * @return
	 */
	public int checkUserAnswer(String account, String question, String asw);
	/**
	 * 通过Id查找用户信息
	 * @param userId
	 * @return
	 */
	public User findUserById(Integer userId);
	/**
	 * 更新密码
	 * @param user
	 * @return
	 */
	public int updateUserInfo(User user);
	/**
	 * 验证用户密码是否已经存在
	 * @param account
	 * @param oldPassword
	 * @return
	 */
	public int checkPassword(String account, String password);
	/**
	 * 找到所有用户
	 * @return
	 */
	public List<User> findAllUser();
	
	
		
	
	
	
	
	
	
	
	
	
}

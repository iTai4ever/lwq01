package cn.techaction.service.Impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import cn.techaction.common.SverResponse;
import cn.techaction.dao.ActionOrderDao;
import cn.techaction.dao.ActionUserDao;
import cn.techaction.pojo.User;
import cn.techaction.service.ActionUserService;
import cn.techaction.utils.ConstUtil;
import cn.techaction.utils.MD5Util;
import cn.techaction.utils.TokenCache;
import cn.techaction.vo.ActionUserVo;

@Service
public class ActionUserServiceImpl implements ActionUserService{
	@Autowired
	private ActionUserDao actionUserDao;
	@Autowired
	private ActionOrderDao actionOrderDao;
	@Override
	public SverResponse<User> doLogin(String account, String password) {
		// TODO Auto-generated method stub
		//1.判断用户名是否存在
		int rs=actionUserDao.checkUserByAccount(account);
		if(rs==0) {
			return SverResponse.createByErrorMessage("用户不存在！");
		}
		//2.根据用户名和密码查找用户
		//String md5pwd=MD5Util.MD5Encode(password, "utf-8", false);
		User user=actionUserDao.findUserByAccountAndPassword(account, password);
		//3.判断查找的用户是否存在
		if(user==null) {
			return SverResponse.createByErrorMessage("密码错误");
		}
		//置空密码
		user.setPassword(StringUtils.EMPTY);
		return SverResponse.createRespBySuccess("登录成功！",user);
	}
	@Override
	public SverResponse<String> doRegister(User user){
		//检查用户名是否存在
		SverResponse<String> resp = checkValidation(user.getAccount(),ConstUtil.TYPE_ACCOUNT);
		if(!resp.isSuccess()){
			return resp;
		}
		
		//检查邮箱是否被注册
		resp = checkValidation(user.getEmail(),ConstUtil.TYPE_EMAIL);
		if(!resp.isSuccess()){
			return resp;
		}
		//给我们的角色制定用户角色，
		
		user.setRole(ConstUtil.Role.ROLE_CUSTOMER);
		//第一次的时候我们没有使用MD5加密
		user.setPassword(user.getPassword());//
		
		Date curDate = new Date();
		user.setCreate_time(curDate);
		user.setUpdate_time(curDate);
		//在dao层里我们把这个方法设置成了static变量
		int rs = actionUserDao.insertUser(user);
		if(rs==0){
			return SverResponse.createByErrorMessage("注册失败");
					
		}
		return SverResponse.createRespBySuccessMessage("注册成功!");
		
		
		//通过前端通过的用户均为用户
		//设定角色后我们要对密码进行加密
		//执行注册
		
		
		
		 
	}
	@Override
	public SverResponse<String> checkValidation(String str,String type){
		//判断我们的字符串长度不为空
		if(StringUtils.isNotBlank(type)){
			if(ConstUtil.TYPE_ACCOUNT.equals(type)){
				int rs = actionUserDao.checkUserByAccount(str);
				if(rs > 0){
					return SverResponse.createByErrorMessage("用户名已经存在");
					
				}
				
			}//判断邮箱是否存在
			if(StringUtils.isNotBlank(type)){
				if(ConstUtil.TYPE_EMAIL.equals(type)){
					int rs = actionUserDao.checkUserByEmail(str);
					if(rs > 0){
						return SverResponse.createByErrorMessage("email 已经存在");
						
								
					}
				}
			}
			//判断电话号码是否存在
			if(StringUtils.isNotBlank(type)){
				if(ConstUtil.TYPE_PHONE.equals(type)){
					int rs = actionUserDao.checkUserByPhone(str);
					if(rs > 0){
						return SverResponse.createByErrorMessage("电话号码 已经存在");
					}
				}
			}
			
		}else{
			return SverResponse.createByErrorMessage("信息验证错误");
		}
		return SverResponse.createRespBySuccessMessage("信息验证成功");
		
		
	}
	@Override
	public SverResponse<User> findUserByAccount(String account) {
		// 通过用户名查找到用户
		User user= actionUserDao.findUserByAccount(account);
		//然后将密码置空
		if(user==null){
			return SverResponse.createByErrorMessage("用户名错误");
			
					
		}
		user.setPassword(StringUtils.EMPTY);
		user.setAsw(StringUtils.EMPTY);
		return SverResponse.createRespBySuccess(user);
	}
	
	
	@Override
	public SverResponse<String> checkUserAnswer(String account, String question, String asw) {
		// 获取校验的结果
		int rs = actionUserDao.checkUserAnswer(account,question,asw);
		if(rs > 0){
			//答案正确，生成taken
			String token = UUID.randomUUID().toString();
			//放入缓存
			TokenCache.setCacheData(TokenCache.PREFIX+account,token );
			return SverResponse.createRespBySuccessMessage(token);
			
			
		}
		//在内部校验答案是否正确，如果正确，放入缓存区校验，如果错误，返回错误提示
		return SverResponse .createByErrorMessage("用户问题答案错误");
	}
	@Override
	public SverResponse<String> resetpassword(Integer userId, String password) {
		// 重置新密码
		//ring pwd = password;
		//正常来说应该先加密密码，这里先不加进行验证
		//String pwd = MD5Util.MD5Encode(password,"utf-8",false);
		User user = actionUserDao.findUserById(userId);
		//或许用户对象，把获得的新密码添加进去
		user.setPassword(password);
		user.setUpdate_time(new Date());
		int rs = actionUserDao.updateUserInfo(user);
		if(rs >0){
			return SverResponse.createRespBySuccessMessage("密码修改成功");
			
			
		}
		return SverResponse.createByErrorMessage("密码修改失败");
	}
	@Override
	public SverResponse<User> updateUserInfo(ActionUserVo userVo) {
		// 根据ID 获得用户对象，才能userVo中操作
		User updateUser = actionUserDao.findUserById(userVo.getId());
		
		//更新数据
		updateUser.setAccount(userVo.getAccount());
		updateUser.setEmail(userVo.getEmail());
		updateUser.setPhone(userVo.getPhone());
		updateUser.setUpdate_time(new Date());
		updateUser.setAge(userVo.getAge());
		//判断男女
		/*if(userVo.getSex().equals("男")){
			updateUser.setSex(1);
			
		}else{
		updateUser.setSex(0);
		}*/
		updateUser.setSex(userVo.getSex());
		updateUser.setName(userVo.getName());
		int rs = actionUserDao.updateUserInfo(updateUser);
		if(rs > 0){
			return SverResponse .createRespBySuccess("用户信息修改成功！", updateUser);
			
		}
		
		
		return SverResponse.createByErrorMessage("用户信息修改失败！");
	}
	@Override
	public SverResponse<String> updatePassword(User user, String newPassword, String oldPassword) {
		// 检测旧密码是否是否正确
		int rs = actionUserDao.checkPassword(user.getAccount(),oldPassword);
		if(rs == 0){
			return SverResponse.createByErrorMessage("原始密码错误");
			
					
		}
		user.setPassword(newPassword);
		user.setUpdate_time(new Date());
		rs = actionUserDao.updateUserInfo(user);
		if(rs > 0){
			return SverResponse.createRespBySuccessMessage("密码修改成功");
			
		}
		return SverResponse.createByErrorMessage("密码修改失败");
		
		//如果正确，将新密码插入数据库
		
		
	}
	/**
	 * 判断用户是否是管理员
	 */
	@Override
	public SverResponse<String> isAdmin(User user) {
		// TODO Auto-generated method stub
		if(user.getRole()==ConstUtil.Role.ROLE_ADMIN){
			return SverResponse.createRespBySuccess();
		}
		return SverResponse.createRespByError();
		
	}
	/**
	 * 转换为ActionVo对象
	 */
	@Override
	public SverResponse<List<ActionUserVo>> findUserList() {
		// TODO Auto-generated method stub
		//调用dao类得方法
		List<ActionUserVo> vos = Lists.newArrayList();
		List<User> users = actionUserDao.findAllUser();
		
		//对User对象转换为ActionUserVo对象
		for(User u:users){
			vos.add(setNormalProperty(u));
		}
		return SverResponse.createRespBySuccess(vos);
	}
	/**
	 * 转换将User 转换ActionUser对象
	 * @param user
	 * @return
	 */
	private ActionUserVo setNormalProperty(User user){
		ActionUserVo vo= new ActionUserVo();
		vo.setAccount(user.getAccount());
		vo.setAge(user.getAge());
		vo.setEmail(user.getEmail());
		vo.setId(user.getId());
		vo.setName(user.getName());
		vo.setPhone(user.getPhone());
		vo.setSex(user.getSex());
		return vo;
	}
	@Override
	public SverResponse<ActionUserVo> findUser(Integer id) {
		// TODO Auto-generated method stub
		//调用Dao层类中的方法，获得User对象
		User user = actionUserDao.findUserById(id);
		ActionUserVo vo = setNormalProperty( user);
		
		//将User对象转换成ActionUserVo对象
		
		return SverResponse.createRespBySuccess(vo);
	}
	@Override
	public SverResponse<String> delUser(Integer id) {
		// TODO Auto-generated method stub
		
		//用户是否有订单，如果没有订单则需要删除
		if(actionOrderDao.findOrderByUid(id).size()>0){
			return SverResponse.createByErrorMessage("用户存在关联的订单，无法删除");
			
			
		}
		//删除用户，实际是修改用户的del状态
		User user=actionUserDao.findUserById(id);
		user.setDel(1);
		user.setUpdate_time(new Date());
		int rs = actionUserDao.updateUserInfo(user);
		if(rs>0){
			return SverResponse.createRespBySuccess("用户删除成功");
		}
		return SverResponse.createByErrorMessage("用户删除失败");
	}
	@Override
	public SverResponse<ActionUserVo> findUser2(Integer id) {
		// TODO Auto-generated method stub
		User user = actionUserDao.findUserById(id);
		ActionUserVo vo = setProperty(user);
		
		//将User对象转换成ActionUserVo对象
		
		return SverResponse.createRespBySuccess(vo);
	}
	private ActionUserVo setProperty(User user) {
		ActionUserVo vo= new ActionUserVo();
		vo.setAccount(user.getAccount());
		vo.setAge(user.getAge());
		vo.setEmail(user.getEmail());
		vo.setId(user.getId());
		vo.setRole(user.getRole());
		vo.setName(user.getName());
		vo.setPhone(user.getPhone());
		vo.setSex(user.getSex());
		return vo;
	}

}

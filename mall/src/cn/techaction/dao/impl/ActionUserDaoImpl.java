package cn.techaction.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.springframework.stereotype.Repository;

import com.sun.org.apache.bcel.internal.generic.NEW;

import cn.techaction.dao.ActionUserDao;
import cn.techaction.pojo.User;

@Repository
public class ActionUserDaoImpl implements ActionUserDao{
	@Resource
	private QueryRunner queryRunner;
	@Override
	public int checkUserByAccount(String account) {
		// TODO Auto-generated method stub
		String sql="select count(*) as num from action_users where account=?";
		try {
			List<Long> rs=queryRunner.query(sql, new ColumnListHandler<Long>("num"),account);
			return rs.size()>0?rs.get(0).intValue():0;
		}catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			return 0;
		}
	}
	@Override
	public User findUserByAccountAndPassword(String account, String password) {
		// TODO Auto-generated method stub
		String sql="select * from action_users where account=? and password=?";
		try {
			return queryRunner.query(sql,new BeanHandler<User>(User.class),account,password);
		}catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}

	}
	@Override
	public int checkUserByEmail(String email) {
		// TODO Auto-generated method stub
		String sql = "select count(account) as num from action_users where email=?";
		try{
			List<Long> rs =  queryRunner.query(sql, new ColumnListHandler<Long>("num"), email);
			return rs.size()>0?rs.get(0).intValue():0;
		}catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			return 0;
		}
	}
	@Override
	public int checkUserByPhone(String phone) {
		// TODO Auto-generated method stub
		String sql = "select count(account) as num from action_users where phone=?";
		try{
			List<Long> rs =  queryRunner.query(sql, new ColumnListHandler<Long>("num"), phone);
			return rs.size()>0?rs.get(0).intValue():0;
		}catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			return 0;
		}
	}
	@Override
	public int insertUser(User user) {
		String sql = "insert into action_users(account,password,email,phone,question,asw,role,create_time,update_time) values(?,?,?,?,?,?,?,?,?)";
		Object[] params = {user.getAccount(),user.getPassword(),user.getEmail(),user.getPhone(),user.getQuestion(),
				user.getAsw(),user.getRole(),user.getCreate_time(),user.getUpdate_time()};
 		// TODO Auto-generated method stub
		try {
			return queryRunner.update(sql, params);
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
			
			
		}
		
	}
	@Override
	public User findUserByAccount(String account) {
		// TODO Auto-generated method stub
		String sql = "select * from action_users where account = ?";
		try {
			return queryRunner.query(sql, new BeanHandler<User>(User.class), account);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		
	}
	
	@Override
	public int checkUserAnswer(String account, String question, String asw) {
		String sql = "select count(account) as num from action_users where account = ? and question = ? and asw = ? ";
		try {
			 List<Long> rs =queryRunner.query(sql, new ColumnListHandler<Long>("num"),account,question,asw );
			 return rs.size()>0?rs.get(0).intValue():0;
		} catch (SQLException e) {
			
			e.printStackTrace();
			return 0;
		}
		
	}
	@Override
	public User findUserById(Integer id){
		// 查询用户信息按照ID
		String sql = "select * from action_users where id = ?";
		try {
			return queryRunner.query(sql, new BeanHandler<User>(User.class ), id);
		} catch (SQLException e) {
			
			e.printStackTrace();
			return null;
		}
		
	}
	
	@Override
	public int updateUserInfo(User user) {
		// 跟新密码
		String sql = "update action_users set update_time = ?,password = ?,email = ?,phone = ?,question = ?,asw = ?,"
				+ "name = ?,age = ?,sex = ?,create_time = ?,account = ?,role = ?,del = ? where id = ?";
		List<Object> params = new ArrayList<>();
		params.add(user.getUpdate_time());
		params.add(user.getPassword());
		params.add(user.getEmail());
		params.add(user.getPhone());
		params.add(user.getQuestion());
		params.add(user.getAsw());
		params.add(user.getName());
		params.add(user.getAge());
		params.add(user.getSex());
		params.add(user.getCreate_time());
		params.add(user.getAccount());
		params.add(user.getRole());
		params.add(user.getDel());
		params.add(user.getId());
		
		try {
			return queryRunner.update(sql,params.toArray());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		
	}
	@Override
	public int checkPassword(String account, String password) {
		// 
        String sql = "select count(account) as num from action_users where account = ? and password = ?";
        try {
			List<Long> rs = queryRunner.query(sql, new ColumnListHandler<Long>("num"), account,password);
			return rs.size()>0?rs.get(0).intValue():0;
        } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		
	}
	@Override
	public List<User> findAllUser() {
		// TODO Auto-generated method stub
		String sql = "select * from action_users where del=0";
		try {
			return queryRunner.query(sql, new BeanListHandler<User>(User.class));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	
		

}

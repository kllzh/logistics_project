package cn.zj.logistics.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import cn.zj.logistics.mapper.UserMapper;
import cn.zj.logistics.pojo.User;
import cn.zj.logistics.pojo.UserExample;
import cn.zj.logistics.service.UserService;

@Component
public class UserServiceImpl implements UserService{
    
	@Autowired
	private UserMapper UserMapper;
	
	@Override
	public int deleteByPrimaryKey(Long userId) {
		
		return UserMapper.deleteByPrimaryKey(userId);
	}

	@Override
	public int insert(User record) {
		
		return UserMapper.insert(record);
	}

	@Override
	public int insertSelective(User record) {
				return UserMapper.insertSelective(record);
	}

	@Override
	public List<User> selectByExample(UserExample example) {
		
		return UserMapper.selectByExample(example);
	}

	@Override
	public User selectByPrimaryKey(Long userId) {
		
				return UserMapper.selectByPrimaryKey(userId);
	}

	@Override
	public int updateByPrimaryKey(User record) {
		
		return UserMapper.updateByPrimaryKey(record);
	}

	@Override
	public int updateByPrimaryKeySelective(User record) {
		// TODO Auto-generated method stub
		return UserMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public User selectByuser(String username) {
		// TODO Auto-generated method stub
		return UserMapper.selectByuser(username);
	}

	

}

package cn.zj.logistics.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import cn.zj.logistics.mapper.RoleMapper;
import cn.zj.logistics.pojo.Role;
import cn.zj.logistics.pojo.RoleExample;
import cn.zj.logistics.service.RoleService;

@Component
public class RoleServiceImpl implements RoleService{
    
	@Autowired
	private RoleMapper RoleMapper;
	
	@Override
	public int deleteByPrimaryKey(Long roleId) {
		
		return RoleMapper.deleteByPrimaryKey(roleId);
	}

	@Override
	public int insert(Role record) {
		
		return RoleMapper.insert(record);
	}

	@Override
	public int insertSelective(Role record) {
				return RoleMapper.insertSelective(record);
	}

	@Override
	public List<Role> selectByExample(RoleExample example) {
		
		return RoleMapper.selectByExample(example);
	}

	@Override
	public Role selectByPrimaryKey(Long roleId) {
		
	return RoleMapper.selectByPrimaryKey(roleId);
	}

	@Override
	public int updateByPrimaryKeySelective(Role role) {
		// TODO Auto-generated method stub
		return RoleMapper.updateByPrimaryKeySelective(role);
	}

}

package cn.zj.logistics.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import cn.zj.logistics.mapper.PermissionMapper;
import cn.zj.logistics.pojo.Permission;
import cn.zj.logistics.pojo.PermissionExample;
import cn.zj.logistics.service.PermissionService;

@Component
public class PermissionServiceImpl implements PermissionService{
    
	@Autowired
	private PermissionMapper PermissionMapper;
	
	@Override
	public int deleteByPrimaryKey(Long permissionId) {
		
		return PermissionMapper.deleteByPrimaryKey(permissionId);
	}

	@Override
	public int insert(Permission record) {
		
		return PermissionMapper.insert(record);
	}

	@Override
	public int insertSelective(Permission record) {
				return PermissionMapper.insertSelective(record);
	}

	@Override
	public List<Permission> selectByExample(PermissionExample example) {
		
		return PermissionMapper.selectByExample(example);
	}

	@Override
	public Permission selectByPrimaryKey(Long permissionId) {
		
				return PermissionMapper.selectByPrimaryKey(permissionId);
	}

	@Override
	public int updateByPrimaryKey(Permission record) {
		
		return PermissionMapper.updateByPrimaryKey(record);
	}

	@Override
	public int updateByPrimaryKeySelective(Permission record) {
		// TODO Auto-generated method stub
		return PermissionMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public List<String> selectBypermission(List<Long> list) {
		// TODO Auto-generated method stub
		return PermissionMapper.selectBypermission(list);
	}

	

}

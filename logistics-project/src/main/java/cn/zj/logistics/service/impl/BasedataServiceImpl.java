package cn.zj.logistics.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import cn.zj.logistics.mapper.BasedataMapper;
import cn.zj.logistics.pojo.Basedata;
import cn.zj.logistics.pojo.BasedataExample;
import cn.zj.logistics.service.BasedataService;

@Component
public class BasedataServiceImpl implements BasedataService{
    
	@Autowired
	private BasedataMapper BasedataMapper;
	
	@Override
	public int deleteByPrimaryKey(Long roleId) {
		
		return BasedataMapper.deleteByPrimaryKey(roleId);
	}

	@Override
	public int insert(Basedata record) {
		
		return BasedataMapper.insert(record);
	}

	@Override
	public int insertSelective(Basedata record) {
				return BasedataMapper.insertSelective(record);
	}

	@Override
	public List<Basedata> selectByExample(BasedataExample example) {
		
		return BasedataMapper.selectByExample(example);
	}

	@Override
	public Basedata selectByPrimaryKey(Long roleId) {
		
	return BasedataMapper.selectByPrimaryKey(roleId);
	}

	@Override
	public int updateByPrimaryKeySelective(Basedata role) {
		
		return BasedataMapper.updateByPrimaryKeySelective(role);
	}

	@Override
	public int updateByPrimaryKey(Basedata record) {
		
		return BasedataMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<Basedata> selectBaseName(String basename) {
		// TODO Auto-generated method stub
		return BasedataMapper.selectBaseName(basename);
	}

}

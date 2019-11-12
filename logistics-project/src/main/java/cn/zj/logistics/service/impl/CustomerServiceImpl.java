package cn.zj.logistics.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import cn.zj.logistics.mapper.CustomerMapper;
import cn.zj.logistics.pojo.Customer;
import cn.zj.logistics.pojo.CustomerExample;
import cn.zj.logistics.service.CustomerService;

@Component
public class CustomerServiceImpl implements CustomerService{
    
	@Autowired
	private CustomerMapper CustomerMapper;
	
	@Override
	public int deleteByPrimaryKey(Long customerId) {
		
		return CustomerMapper.deleteByPrimaryKey(customerId);
	}

	@Override
	public int insert(Customer record) {
		
		return CustomerMapper.insert(record);
	}

	@Override
	public int insertSelective(Customer record) {
				return CustomerMapper.insertSelective(record);
	}

	@Override
	public List<Customer> selectByExample(CustomerExample example) {
		
		return CustomerMapper.selectByExample(example);
	}

	@Override
	public Customer selectByPrimaryKey(Long customerId) {
		
	return CustomerMapper.selectByPrimaryKey(customerId);
	}

	@Override
	public int updateByPrimaryKeySelective(Customer role) {
		
		return CustomerMapper.updateByPrimaryKeySelective(role);
	}

	@Override
	public int updateByPrimaryKey(Customer record) {
		
		return CustomerMapper.updateByPrimaryKey(record);
	}

	
}

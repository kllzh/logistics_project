package cn.zj.logistics.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import cn.zj.logistics.mapper.OrderDetailMapper;
import cn.zj.logistics.mapper.OrderMapper;
import cn.zj.logistics.pojo.Order;
import cn.zj.logistics.pojo.OrderDetail;
import cn.zj.logistics.pojo.OrderDetailExample;
import cn.zj.logistics.pojo.OrderDetailExample.Criteria;
import cn.zj.logistics.pojo.OrderExample;
import cn.zj.logistics.service.OrderService;

@Component
public class OrderServiceImpl implements OrderService{
    
	@Autowired
	private OrderMapper OrderMapper;
	
	@Autowired
	private OrderDetailMapper OrderDetailMapper;
	
	@Override
	public int deleteByPrimaryKey(Long orderId) {
		
		
		  OrderDetailMapper.delete(orderId);
		
		
		return OrderMapper.deleteByPrimaryKey(orderId);
	}

	@Override
	public int insert(Order record) {
		
		
		int insert = OrderMapper.insert(record);
		
		List<OrderDetail> orderDetails = record.getOrderDetails();
		
		for (OrderDetail orderDetail : orderDetails) {
			
			orderDetail.setOrderId(record.getOrderId());
			
			int insert2 = OrderDetailMapper.insert(orderDetail);
		}
				
		return insert;
	}

	@Override
	public int insertSelective(Order record) {
				return OrderMapper.insertSelective(record);
	}

	@Override
	public List<Order> selectByExample(OrderExample example) {
		
		return OrderMapper.selectByExample(example);
	}

	@Override
	public Order selectByPrimaryKey(Long orderId) {
		
	return OrderMapper.selectByPrimaryKey(orderId);
	}

	@Override
	public int updateByPrimaryKeySelective(Order role) {
		
		return OrderMapper.updateByPrimaryKeySelective(role);
	}

	@Override
	public int updateByPrimaryKey(Order record) {
		
		return OrderMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<OrderDetail> selectByorderdetail(Long orderId){
		   
		OrderDetailExample example=new OrderDetailExample();
		
		Criteria createCriteria = example.createCriteria();
		
		createCriteria.andOrderIdEqualTo(orderId);
		
		List<OrderDetail> selectByExample = OrderDetailMapper.selectByExample(example);
		
		
		return selectByExample;
	}
	
}

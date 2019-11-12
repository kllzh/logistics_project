package cn.zj.logistics.service;

import java.util.List;

import cn.zj.logistics.pojo.Order;
import cn.zj.logistics.pojo.OrderDetail;
import cn.zj.logistics.pojo.OrderExample;

public interface OrderService {

	
	    int deleteByPrimaryKey(Long orderId);

	    int insert(Order record);

	    int insertSelective(Order record);

	    List<Order> selectByExample(OrderExample example);

	    Order selectByPrimaryKey(Long orderId);

	    int updateByPrimaryKeySelective(Order record);

	    int updateByPrimaryKey(Order record);
	   
	    List<OrderDetail> selectByorderdetail(Long orderId);
}

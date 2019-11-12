package cn.zj.logistics.service;

import java.util.List;

import cn.zj.logistics.pojo.Basedata;
import cn.zj.logistics.pojo.BasedataExample;
import cn.zj.logistics.pojo.Customer;

public interface BasedataService {

	
	    int deleteByPrimaryKey(Long baseId);

	    int insert(Basedata record);

	    int insertSelective(Basedata record);

	    List<Basedata> selectByExample(BasedataExample example);

	    Basedata selectByPrimaryKey(Long baseId);

	    int updateByPrimaryKeySelective(Basedata record);

	    int updateByPrimaryKey(Basedata record);
	   
	    List<Basedata> selectBaseName(String basename);
}

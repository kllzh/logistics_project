package cn.zj.logistics.mapper;

import cn.zj.logistics.pojo.Basedata;
import cn.zj.logistics.pojo.BasedataExample;
import cn.zj.logistics.pojo.Customer;

import java.util.List;

public interface BasedataMapper {
    int deleteByPrimaryKey(Long baseId);

    int insert(Basedata record);

    int insertSelective(Basedata record);

    List<Basedata> selectByExample(BasedataExample example);

    Basedata selectByPrimaryKey(Long baseId);

    int updateByPrimaryKeySelective(Basedata record);

    int updateByPrimaryKey(Basedata record);
    
    List<Basedata> selectBaseName(String basename);
}
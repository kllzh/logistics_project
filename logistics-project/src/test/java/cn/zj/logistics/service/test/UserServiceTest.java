package cn.zj.logistics.service.test;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.zj.logistics.pojo.User;
import cn.zj.logistics.pojo.UserExample;
import cn.zj.logistics.service.UserService;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring.xml")
public class UserServiceTest {
    
	@Autowired
	private DataSource DataSource;
	
	@Autowired
	private UserService UserService;
	
		
	
	@Test
	public void testDeleteByPrimaryKey() {
		
	}

	@Test
	public void testInsert() {
		fail("Not yet implemented");
	}

	@Test
	public void testInsertSelective() {
		fail("Not yet implemented");
	}

	@Test
	public void testSelectByExample() {
		UserExample example=new UserExample();
		int pageNum=1;
		int pageSize=10;
		PageHelper.startPage(pageNum, pageSize);
		
		List<User> user = UserService.selectByExample(example);
		
		PageInfo<User> pageInfo=new PageInfo<>(user);
		System.out.println(pageInfo);
	}

	@Test
	public void testSelectByPrimaryKey() {
		fail("Not yet implemented");
	}

}

package cn.zj.logistics.contorller;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.zj.logistics.mapper.OrderDetailMapper;
import cn.zj.logistics.mapper.OrderMapper;
import cn.zj.logistics.mo.MessageObject;
import cn.zj.logistics.pojo.Permission;
import cn.zj.logistics.pojo.PermissionExample;
import cn.zj.logistics.pojo.User;
import cn.zj.logistics.pojo.UserExample;
import cn.zj.logistics.pojo.Basedata;
import cn.zj.logistics.pojo.Customer;
import cn.zj.logistics.pojo.CustomerExample;
import cn.zj.logistics.pojo.Order;
import cn.zj.logistics.pojo.OrderDetail;
import cn.zj.logistics.pojo.OrderDetailExample;
import cn.zj.logistics.pojo.OrderExample;

import cn.zj.logistics.pojo.Order;
import cn.zj.logistics.pojo.OrderExample;
import cn.zj.logistics.pojo.OrderExample.Criteria;
import cn.zj.logistics.pojo.Order;
import cn.zj.logistics.pojo.OrderExample;
import cn.zj.logistics.service.PermissionService;
import cn.zj.logistics.service.UserService;
import cn.zj.logistics.service.BasedataService;
import cn.zj.logistics.service.CustomerService;
import cn.zj.logistics.service.OrderService;
import cn.zj.logistics.service.OrderService;
import cn.zj.logistics.service.OrderService;

@Controller
@RequestMapping("/order")
public class OrderContorller{

	@Autowired
	private OrderService orderservic;
	
    @Autowired
    private UserService UserService;
	
    @Autowired
    private BasedataService BasedataService;
    
    @Autowired
    private CustomerService CustomerService;
    
    @Autowired
    private OrderDetailMapper OrderDetailMapper;
    
	@RequiresPermissions("order:orderPage")
	@RequestMapping("/orderPage")
	public String adminpage() {
		
		return "orderPage";
	}
	
	@RequiresPermissions("order:list")
	@RequestMapping("/list")
	@ResponseBody
	public PageInfo<Order> adminpage(@RequestParam(defaultValue="1")Integer pageNum,
			@RequestParam(defaultValue="10")Integer pageSize,String keyword) {
		
		
		OrderExample example=new OrderExample();
		
	    //PageHelper:mybatis分页插件，必须写在查询之前
		//pageNum：当前页；pageSize：当前也多少列
		 PageHelper.startPage(pageNum, pageSize);
		  
		 //StringUtils:可以判断String st=null;或者String st=""或者String st="  "
		 //isNotBlank：不为空返回true;空返回false
		 if (StringUtils.isNotBlank(keyword)) {
			 
			Criteria create = example.createCriteria();
				
		}
		 
		 
         List<Order> order = orderservic.selectByExample(example);
		
		//创建分页插件的分页信息对象PageInfo
		//PageInfo对象封装所有分页信息
		//如：总天数；下一页，当前页数，当前也等等
		PageInfo<Order> pageInfo=new PageInfo<>(order);
		
		return pageInfo;
	}
	
	
	
	   @RequiresPermissions("order:delete")
	   @RequestMapping("/delete")
	   @ResponseBody
	   public  MessageObject delete(Long orderId) {
		   
		     MessageObject me=null;

             OrderExample example=new OrderExample();
             

		       int delete = orderservic.deleteByPrimaryKey(orderId);

			   me=new MessageObject(1, "删除成功");
		     
		       
		  return me; 
	}
	   	   
        //批量删除
	   @RequiresPermissions("order:delete")
	   @RequestMapping("/deleteall")
	   @ResponseBody
	   public  int deleteall(HttpServletRequest request) {
		   
		   int num=0;
		   
		  String [] orderId = request.getParameterValues("orderId[]");		   		  
		  
		   for (String string : orderId) { 
			   
			   long orderid=(new Integer(string));
			   
		 int delete = orderservic.deleteByPrimaryKey(orderid);
		   if (delete==1) {			   
			       num++;
		  }
	   }		   
		  return num; 
	}
	   
	   
	  //跳转到addorder页面进行添加客户
	   @RequestMapping("/addorder")
	   public String addorder(Model model){	
		   
		   /*	
		    * 	 业务员
		        *        客户
		        *        区间范围 
		        *        付货方式
		        *        货运方式           
		        *        取件方式  
		        *        单位             		               
		    */
		      //业务员
		      UserExample example=new UserExample();
	          cn.zj.logistics.pojo.UserExample.Criteria Criteria = example.createCriteria();
	          Criteria.andRolenameEqualTo("业务员");
			  List<User> users = UserService.selectByExample(example);
			  model.addAttribute("users", users);
			  //客户
			  CustomerExample customexample=new CustomerExample();
			  List<Customer> customers = CustomerService.selectByExample(customexample);
			  model.addAttribute("customers", customers);
			  //区间范围
			  List<Basedata> intervals = BasedataService.selectBaseName("区间管理");
			  model.addAttribute("intervals", intervals);
			  //System.err.println(intervals);
			  //付款方式
			  List<Basedata> payments = BasedataService.selectBaseName("付款方式");
			  model.addAttribute("payments", payments);
			  //货运方式
			  List<Basedata> freights = BasedataService.selectBaseName("货运方式");
			  model.addAttribute("freights", freights);
			  //取件方式 
			  List<Basedata> fetchTypes = BasedataService.selectBaseName("取件方式");
			  model.addAttribute("fetchTypes", fetchTypes);
			  //取件方式 
			  List<Basedata> units = BasedataService.selectBaseName("单位");
			  model.addAttribute("units", units);
			  			  
			   return "addOrder";
	}
           
	  
	   //添加客户
	   @RequiresPermissions("order:insert")
	   @RequestMapping("/insert")
	   @ResponseBody
	   public MessageObject insert(@RequestBody Order order) {
		     
		   
		   System.err.println(order);
 
		   MessageObject me=null;
		  //前台form表单传递的参数名跟Order对象中属性名一致，可以使用对象获取表单数据；
		   int insert = orderservic.insert(order);
		   
		   if (insert==1) {
			me=new MessageObject(1,"添加成功");
			return me;
		}
		   
		me=new MessageObject(0, "添加失败");  
		   return me;
	}
	   
	   
	   //修改客户信息
	   @RequestMapping("/editorder")
	   public String edit(Model model,Long orderId){
           
		   OrderDetailExample example=new OrderDetailExample();
		   cn.zj.logistics.pojo.OrderDetailExample.Criteria Criteria = example.createCriteria();
		   OrderDetailMapper.selectByExample(example);
		  
			   return "addOrder";
	}
	   
	  //进行修改语句
	   @RequestMapping("/update")
	   @RequiresPermissions("order:update")
	   @ResponseBody	   
	   public MessageObject update(Order order){
		   
		   MessageObject me=null;

		   //按主键选择；动态选择
		   int update=orderservic.updateByPrimaryKeySelective(order);
		   
		   if (update==1) {			
			  me=new MessageObject(1,"添加成功");
			   
			   return me;
		}
		 
		   me=new MessageObject(0,"添加失败");
		   
		   return me;
	}
	   
	   
	   @RequestMapping("/orderdetail")
	   @ResponseBody
	   public List<OrderDetail> orderdetail(Long orderId){
		   
		List<OrderDetail> list=orderservic.selectByorderdetail(orderId);
		
		return list;
	}
	 
}

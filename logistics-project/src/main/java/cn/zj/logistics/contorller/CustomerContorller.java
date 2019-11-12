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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.zj.logistics.mapper.CustomerMapper;
import cn.zj.logistics.mo.MessageObject;
import cn.zj.logistics.pojo.Permission;
import cn.zj.logistics.pojo.PermissionExample;
import cn.zj.logistics.pojo.User;
import cn.zj.logistics.pojo.UserExample;
import cn.zj.logistics.pojo.Basedata;
import cn.zj.logistics.pojo.Customer;
import cn.zj.logistics.pojo.CustomerExample;

import cn.zj.logistics.pojo.Customer;
import cn.zj.logistics.pojo.CustomerExample;
import cn.zj.logistics.pojo.CustomerExample.Criteria;
import cn.zj.logistics.pojo.Customer;
import cn.zj.logistics.pojo.CustomerExample;
import cn.zj.logistics.service.PermissionService;
import cn.zj.logistics.service.UserService;
import cn.zj.logistics.service.BasedataService;
import cn.zj.logistics.service.CustomerService;
import cn.zj.logistics.service.CustomerService;
import cn.zj.logistics.service.CustomerService;

@Controller
@RequestMapping("/customer")
public class CustomerContorller{

	@Autowired
	private CustomerService customerservic;
	
    @Autowired
    private UserService UserService;
	
    @Autowired
    private BasedataService BasedataService;
    
	@RequiresPermissions("customer:customerPage")
	@RequestMapping("/customerPage")
	public String adminpage() {
		
		return "customerPage";
	}
	
	@RequiresPermissions("customer:list")
	@RequestMapping("/list")
	@ResponseBody
	public PageInfo<Customer> adminpage(@RequestParam(defaultValue="1")Integer pageNum,
			@RequestParam(defaultValue="10")Integer pageSize,String keyword) {
		
		
		CustomerExample example=new CustomerExample();
		
	    //PageHelper:mybatis分页插件，必须写在查询之前
		//pageNum：当前页；pageSize：当前也多少列
		 PageHelper.startPage(pageNum, pageSize);
		  
		 //StringUtils:可以判断String st=null;或者String st=""或者String st="  "
		 //isNotBlank：不为空返回true;空返回false
		 if (StringUtils.isNotBlank(keyword)) {
			 
			Criteria create = example.createCriteria();
			create.andCustomerNameLike("%"+keyword+"%");
		           			
			example.or().andPhoneLike("%"+keyword+"%");
						
		}
		 

		   Subject subject = SecurityUtils.getSubject();
		     
		   User user = (User) subject.getPrincipal();
		   System.err.println(user);
		   
		   if (user.getRolename().equals("业务员")) {
			
			    Criteria Criteria = example.createCriteria();
			    
			    Criteria.andRealnameEqualTo(user.getRealname());
		  }
		 
		 List<Customer> customer = customerservic.selectByExample(example);
		
		//创建分页插件的分页信息对象PageInfo
		//PageInfo对象封装所有分页信息
		//如：总天数；下一页，当前页数，当前也等等
		PageInfo<Customer> pageInfo=new PageInfo<>(customer);
		
		return pageInfo;
	}
	
	
	
	   @RequiresPermissions("customer:delete")
	   @RequestMapping("/delete")
	   @ResponseBody
	   public  MessageObject delete(Long customId) {
		   
		   MessageObject me=null;

             CustomerExample example=new CustomerExample();
             

		       int delete = customerservic.deleteByPrimaryKey(customId);

			   me=new MessageObject(1, "删除成功");
		     
		       
		  return me; 
	}
	   
	   
        //批量删除
	   @RequiresPermissions("customer:delete")
	   @RequestMapping("/deleteall")
	   @ResponseBody
	   public  int deleteall(HttpServletRequest request) {
		   
		   int num=0;
		   
		  String [] customerId = request.getParameterValues("customerId[]");		   		  
		  
		   for (String string : customerId) { 
			   
			   long customerid=(new Integer(string));
			   
		 int delete = customerservic.deleteByPrimaryKey(customerid);
		   if (delete==1) {			   
			       num++;
		  }
	   }		   
		  return num; 
	}
	   
	   
	   //跳转到addcustomer页面进行添加客户
	  @RequestMapping("/addcustomer")
	   public String addcustomer(Model model){	
		  
		UserExample example=new UserExample();
		
		cn.zj.logistics.pojo.UserExample.Criteria createCriteria = example.createCriteria();
		
		createCriteria.andRolenameEqualTo("业务员");		
				
		List<User> realname = UserService.selectByExample(example);	
		
		
		
		model.addAttribute("realname", realname);
         
	    List<Basedata> basename = BasedataService.selectBaseName("区间管理");
	    
	     System.err.println(basename);
	     
	     model.addAttribute("basename", basename);
		
			   return "addCustomer";
	}
	   
	   
	   //检查客户是否存在	   
	   @RequestMapping("/checkbaseName")
	   @ResponseBody
	   public boolean checkcustomerName(String customerName){
			  
		   CustomerExample example=new CustomerExample();
		   
		   Criteria create = example.createCriteria();
		            
	       create.andCustomerNameEqualTo(customerName);
		    
		  List<Customer> customer = customerservic.selectByExample(example);
							
			return customer.size()>0? false:true;
		}
	   
	   
	   //添加客户
	   @RequiresPermissions("customer:insert")
	   @RequestMapping("/insert")
	   @ResponseBody
	   public MessageObject insert(Customer customer) {
		
		   MessageObject me=null;
		  //前台form表单传递的参数名跟Customer对象中属性名一致，可以使用对象获取表单数据；
		   int insert = customerservic.insert(customer);
		   
		   if (insert==1) {
			me=new MessageObject(1,"确认添加客户信息？");
			return me;
		}
		   
		me=new MessageObject(0, "客户信息添加失败");  
		   return me;
	}
	   
	   
	   //修改客户信息
	   @RequestMapping("/editcustomer")
	   public String edit(Model model,Long customerId){
		//循环表单中业务人员
		    UserExample example=new UserExample();
			
			cn.zj.logistics.pojo.UserExample.Criteria createCriteria = example.createCriteria();
			
			createCriteria.andRolenameEqualTo("业务员");		
			
			List<User> realname = UserService.selectByExample(example);	
			
			model.addAttribute("realname", realname);
		   
		   //循环表单中区间范围
		   List<Basedata> basename = BasedataService.selectBaseName("区间管理");
		   
		     model.addAttribute("basename", basename);
		   
		     
		   Subject subject = SecurityUtils.getSubject();
		   User user = (User) subject.getPrincipal();
		   System.err.println(user);
		   if (user.getRolename().equals("业务员")) {
			   
			   model.addAttribute("user", user);
		}
			
			//根据修改id返回一个Customer对象
		     
		   Customer CustomerObject = customerservic.selectByPrimaryKey(customerId);
			
		   model.addAttribute("CustomerObject", CustomerObject);
		   
			System.err.println(CustomerObject);

			   return "addCustomer";
	}
	   
	  //进行修改语句
	   @RequestMapping("/update")
	   @RequiresPermissions("customer:update")
	   @ResponseBody	   
	   public MessageObject update(Customer customer){
		   
		   MessageObject me=null;

		   //按主键选择；动态选择
		   int update=customerservic.updateByPrimaryKeySelective(customer);
		   
		   if (update==1) {			
			  me=new MessageObject(1,"确认修改管理员信息？");
			   
			   return me;
		}
		 
		   me=new MessageObject(0,"修改管理员信息失败");
		   
		   return me;
	}
	   
	   
	 
}

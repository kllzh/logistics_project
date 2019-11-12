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
		
	    //PageHelper:mybatis��ҳ���������д�ڲ�ѯ֮ǰ
		//pageNum����ǰҳ��pageSize����ǰҲ������
		 PageHelper.startPage(pageNum, pageSize);
		  
		 //StringUtils:�����ж�String st=null;����String st=""����String st="  "
		 //isNotBlank����Ϊ�շ���true;�շ���false
		 if (StringUtils.isNotBlank(keyword)) {
			 
			Criteria create = example.createCriteria();
			create.andCustomerNameLike("%"+keyword+"%");
		           			
			example.or().andPhoneLike("%"+keyword+"%");
						
		}
		 

		   Subject subject = SecurityUtils.getSubject();
		     
		   User user = (User) subject.getPrincipal();
		   System.err.println(user);
		   
		   if (user.getRolename().equals("ҵ��Ա")) {
			
			    Criteria Criteria = example.createCriteria();
			    
			    Criteria.andRealnameEqualTo(user.getRealname());
		  }
		 
		 List<Customer> customer = customerservic.selectByExample(example);
		
		//������ҳ����ķ�ҳ��Ϣ����PageInfo
		//PageInfo�����װ���з�ҳ��Ϣ
		//�磺����������һҳ����ǰҳ������ǰҲ�ȵ�
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

			   me=new MessageObject(1, "ɾ���ɹ�");
		     
		       
		  return me; 
	}
	   
	   
        //����ɾ��
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
	   
	   
	   //��ת��addcustomerҳ�������ӿͻ�
	  @RequestMapping("/addcustomer")
	   public String addcustomer(Model model){	
		  
		UserExample example=new UserExample();
		
		cn.zj.logistics.pojo.UserExample.Criteria createCriteria = example.createCriteria();
		
		createCriteria.andRolenameEqualTo("ҵ��Ա");		
				
		List<User> realname = UserService.selectByExample(example);	
		
		
		
		model.addAttribute("realname", realname);
         
	    List<Basedata> basename = BasedataService.selectBaseName("�������");
	    
	     System.err.println(basename);
	     
	     model.addAttribute("basename", basename);
		
			   return "addCustomer";
	}
	   
	   
	   //���ͻ��Ƿ����	   
	   @RequestMapping("/checkbaseName")
	   @ResponseBody
	   public boolean checkcustomerName(String customerName){
			  
		   CustomerExample example=new CustomerExample();
		   
		   Criteria create = example.createCriteria();
		            
	       create.andCustomerNameEqualTo(customerName);
		    
		  List<Customer> customer = customerservic.selectByExample(example);
							
			return customer.size()>0? false:true;
		}
	   
	   
	   //��ӿͻ�
	   @RequiresPermissions("customer:insert")
	   @RequestMapping("/insert")
	   @ResponseBody
	   public MessageObject insert(Customer customer) {
		
		   MessageObject me=null;
		  //ǰ̨form�����ݵĲ�������Customer������������һ�£�����ʹ�ö����ȡ�����ݣ�
		   int insert = customerservic.insert(customer);
		   
		   if (insert==1) {
			me=new MessageObject(1,"ȷ����ӿͻ���Ϣ��");
			return me;
		}
		   
		me=new MessageObject(0, "�ͻ���Ϣ���ʧ��");  
		   return me;
	}
	   
	   
	   //�޸Ŀͻ���Ϣ
	   @RequestMapping("/editcustomer")
	   public String edit(Model model,Long customerId){
		//ѭ������ҵ����Ա
		    UserExample example=new UserExample();
			
			cn.zj.logistics.pojo.UserExample.Criteria createCriteria = example.createCriteria();
			
			createCriteria.andRolenameEqualTo("ҵ��Ա");		
			
			List<User> realname = UserService.selectByExample(example);	
			
			model.addAttribute("realname", realname);
		   
		   //ѭ���������䷶Χ
		   List<Basedata> basename = BasedataService.selectBaseName("�������");
		   
		     model.addAttribute("basename", basename);
		   
		     
		   Subject subject = SecurityUtils.getSubject();
		   User user = (User) subject.getPrincipal();
		   System.err.println(user);
		   if (user.getRolename().equals("ҵ��Ա")) {
			   
			   model.addAttribute("user", user);
		}
			
			//�����޸�id����һ��Customer����
		     
		   Customer CustomerObject = customerservic.selectByPrimaryKey(customerId);
			
		   model.addAttribute("CustomerObject", CustomerObject);
		   
			System.err.println(CustomerObject);

			   return "addCustomer";
	}
	   
	  //�����޸����
	   @RequestMapping("/update")
	   @RequiresPermissions("customer:update")
	   @ResponseBody	   
	   public MessageObject update(Customer customer){
		   
		   MessageObject me=null;

		   //������ѡ�񣻶�̬ѡ��
		   int update=customerservic.updateByPrimaryKeySelective(customer);
		   
		   if (update==1) {			
			  me=new MessageObject(1,"ȷ���޸Ĺ���Ա��Ϣ��");
			   
			   return me;
		}
		 
		   me=new MessageObject(0,"�޸Ĺ���Ա��Ϣʧ��");
		   
		   return me;
	}
	   
	   
	 
}

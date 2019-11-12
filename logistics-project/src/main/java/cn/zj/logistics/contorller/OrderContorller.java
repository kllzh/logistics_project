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
		
	    //PageHelper:mybatis��ҳ���������д�ڲ�ѯ֮ǰ
		//pageNum����ǰҳ��pageSize����ǰҲ������
		 PageHelper.startPage(pageNum, pageSize);
		  
		 //StringUtils:�����ж�String st=null;����String st=""����String st="  "
		 //isNotBlank����Ϊ�շ���true;�շ���false
		 if (StringUtils.isNotBlank(keyword)) {
			 
			Criteria create = example.createCriteria();
				
		}
		 
		 
         List<Order> order = orderservic.selectByExample(example);
		
		//������ҳ����ķ�ҳ��Ϣ����PageInfo
		//PageInfo�����װ���з�ҳ��Ϣ
		//�磺����������һҳ����ǰҳ������ǰҲ�ȵ�
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

			   me=new MessageObject(1, "ɾ���ɹ�");
		     
		       
		  return me; 
	}
	   	   
        //����ɾ��
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
	   
	   
	  //��ת��addorderҳ�������ӿͻ�
	   @RequestMapping("/addorder")
	   public String addorder(Model model){	
		   
		   /*	
		    * 	 ҵ��Ա
		        *        �ͻ�
		        *        ���䷶Χ 
		        *        ������ʽ
		        *        ���˷�ʽ           
		        *        ȡ����ʽ  
		        *        ��λ             		               
		    */
		      //ҵ��Ա
		      UserExample example=new UserExample();
	          cn.zj.logistics.pojo.UserExample.Criteria Criteria = example.createCriteria();
	          Criteria.andRolenameEqualTo("ҵ��Ա");
			  List<User> users = UserService.selectByExample(example);
			  model.addAttribute("users", users);
			  //�ͻ�
			  CustomerExample customexample=new CustomerExample();
			  List<Customer> customers = CustomerService.selectByExample(customexample);
			  model.addAttribute("customers", customers);
			  //���䷶Χ
			  List<Basedata> intervals = BasedataService.selectBaseName("�������");
			  model.addAttribute("intervals", intervals);
			  //System.err.println(intervals);
			  //���ʽ
			  List<Basedata> payments = BasedataService.selectBaseName("���ʽ");
			  model.addAttribute("payments", payments);
			  //���˷�ʽ
			  List<Basedata> freights = BasedataService.selectBaseName("���˷�ʽ");
			  model.addAttribute("freights", freights);
			  //ȡ����ʽ 
			  List<Basedata> fetchTypes = BasedataService.selectBaseName("ȡ����ʽ");
			  model.addAttribute("fetchTypes", fetchTypes);
			  //ȡ����ʽ 
			  List<Basedata> units = BasedataService.selectBaseName("��λ");
			  model.addAttribute("units", units);
			  			  
			   return "addOrder";
	}
           
	  
	   //��ӿͻ�
	   @RequiresPermissions("order:insert")
	   @RequestMapping("/insert")
	   @ResponseBody
	   public MessageObject insert(@RequestBody Order order) {
		     
		   
		   System.err.println(order);
 
		   MessageObject me=null;
		  //ǰ̨form�����ݵĲ�������Order������������һ�£�����ʹ�ö����ȡ�����ݣ�
		   int insert = orderservic.insert(order);
		   
		   if (insert==1) {
			me=new MessageObject(1,"��ӳɹ�");
			return me;
		}
		   
		me=new MessageObject(0, "���ʧ��");  
		   return me;
	}
	   
	   
	   //�޸Ŀͻ���Ϣ
	   @RequestMapping("/editorder")
	   public String edit(Model model,Long orderId){
           
		   OrderDetailExample example=new OrderDetailExample();
		   cn.zj.logistics.pojo.OrderDetailExample.Criteria Criteria = example.createCriteria();
		   OrderDetailMapper.selectByExample(example);
		  
			   return "addOrder";
	}
	   
	  //�����޸����
	   @RequestMapping("/update")
	   @RequiresPermissions("order:update")
	   @ResponseBody	   
	   public MessageObject update(Order order){
		   
		   MessageObject me=null;

		   //������ѡ�񣻶�̬ѡ��
		   int update=orderservic.updateByPrimaryKeySelective(order);
		   
		   if (update==1) {			
			  me=new MessageObject(1,"��ӳɹ�");
			   
			   return me;
		}
		 
		   me=new MessageObject(0,"���ʧ��");
		   
		   return me;
	}
	   
	   
	   @RequestMapping("/orderdetail")
	   @ResponseBody
	   public List<OrderDetail> orderdetail(Long orderId){
		   
		List<OrderDetail> list=orderservic.selectByorderdetail(orderId);
		
		return list;
	}
	 
}

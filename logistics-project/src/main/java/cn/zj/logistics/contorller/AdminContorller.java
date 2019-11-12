package cn.zj.logistics.contorller;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Md5Hash;
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

import cn.zj.logistics.mapper.RoleMapper;
import cn.zj.logistics.mo.MessageObject;
import cn.zj.logistics.pojo.Role;
import cn.zj.logistics.pojo.RoleExample;

import cn.zj.logistics.pojo.User;
import cn.zj.logistics.pojo.UserExample;
import cn.zj.logistics.pojo.UserExample.Criteria;
import cn.zj.logistics.service.RoleService;
import cn.zj.logistics.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminContorller {

	@Autowired
	private UserService userservice;
	
	@Autowired
	private RoleService RoleService;
	   
	@RequestMapping("/adminPage")
	
	@RequiresPermissions("admin:adminPage")
	public String adminpage(){
		
		return "adminpage";
	}
	
	
	@RequestMapping("/login")
	public String login(HttpServletRequest request ,Model m) {
		
		String shiroLoginFailure =(String) request.getAttribute("shiroLoginFailure");
		System.out.println("�쳣����"+shiroLoginFailure);
		    if (shiroLoginFailure!=null) {
				if (UnknownAccountException.class.getName().equals(shiroLoginFailure)) {
					m.addAttribute("shiroLoginFileure", "�ף��˻�������");
				}else if (IncorrectCredentialsException.class.getName().equals(shiroLoginFailure)) {
					m.addAttribute("shiroLoginFileure", "�ף����벻��ȷ");
				}
			}
		return "forward:/login.jsp";
	}
	
	@RequiresPermissions("admin:list")
	@RequestMapping("/list")
	@ResponseBody
	public PageInfo<User> adminpage(@RequestParam(defaultValue="1")Integer pageNum,
			@RequestParam(defaultValue="10")Integer pageSize,String keyword) {
		
		
		UserExample example=new UserExample();
		
	    //PageHelper:mybatis��ҳ���������д�ڲ�ѯ֮ǰ
		//pageNum����ǰҳ��pageSize����ǰҲ������
		 PageHelper.startPage(pageNum, pageSize);
		  
		 //StringUtils:�����ж�String st=null;����String st=""����String st="  "
		 //isNotBlank����Ϊ�շ���true;�շ���false
		 if (StringUtils.isNotBlank(keyword)) {
			Criteria create = example.createCriteria();
			
			create.andUsernameLike("%"+keyword+"%");
		}
		 
		 List<User> user = userservice.selectByExample(example);
		
		//������ҳ����ķ�ҳ��Ϣ����PageInfo
		//PageInfo�����װ���з�ҳ��Ϣ
		//�磺����������һҳ����ǰҳ������ǰҲ�ȵ�
		PageInfo<User> pageInfo=new PageInfo<>(user);
		
		return pageInfo;
	}
	
	   @RequiresPermissions("admin:delete")
	   @RequestMapping("/delete")
	   @ResponseBody
	   public  MessageObject delete(Long userid) {
		
		   MessageObject me=null;
		 
		   int delete = userservice.deleteByPrimaryKey(userid);
		   
		   if (delete==1) {	
			   
			   me=new MessageObject(1, "ɾ���ɹ�");
		}
		   
		  return me; 
	}
	   
	   
        //����ɾ��
	   @RequiresPermissions("admin:delete")
	   @RequestMapping("/deleteall")
	   @ResponseBody
	   public  int deleteall(HttpServletRequest request) {
		   
		   int num=0;
		   
		  String [] userId = request.getParameterValues("userId[]");		   		  
		  
		   for (String string : userId) { 
			   
			   long userid=(new Integer(string));
			   
		 int delete = userservice.deleteByPrimaryKey(userid);
		   if (delete==1) {			   
			       num++;
		  }
	   }		   
		  return num; 
	}
	   
	   
	   //��ӹ���Ա
	   @RequestMapping("/adduser")
	   public String adduser(Model model){
		  	   
		RoleExample example=new RoleExample();
		
		List<Role> role = RoleService.selectByExample(example);
		
		   model.addAttribute("role", role);
		   return "adduser";
	}
	   
	   
	   //����û��Ƿ����
	   
	   @RequestMapping("/checkUsername")
	   @ResponseBody
	   public boolean checkUsername(String username){
			  
		   UserExample example=new UserExample();
		   
		   Criteria create = example.createCriteria();
		            
	       Criteria EqualTo = create.andUsernameEqualTo(username);
		    
		  List<User> user = userservice.selectByExample(example);
			
				
			return user.size()>0? false:true;
		}
	   
	   
	   //��ӹ���Ա
	   @RequiresPermissions("admin:insert")
	   @RequestMapping("/insert")
	   @ResponseBody
	   public MessageObject insert(User user) {
		
		   MessageObject me=null;
		   
		   user.setCreateDate(new Date());
		   
		   //�������һ����
		   String salt=UUID.randomUUID().toString().substring(0,5);
		   user.setSalt(salt);
		   //�Եõ���������м���
		   Md5Hash md=new Md5Hash(user.getPassword(), salt, 3);	   
		   user.setPassword(md.toString());
		   
		  //ǰ̨form�����ݵĲ�������User������������һ�£�����ʹ�ö����ȡ�����ݣ�
		   int insert = userservice.insert(user);
		   
		   if (insert==1) {
			me=new MessageObject(1,"ȷ����ӹ���Ա��");
			return me;
		}
		   
		me=new MessageObject(0, "��ӹ���Աʧ��");  
		   return me;
	}
	   
	   
	   //�޸Ĺ���Ա��Ϣ
	   @RequestMapping("/edit")
	   public String edit(Model model,Long userid) {
		
		User user= userservice.selectByPrimaryKey(userid);
		
		   model.addAttribute("user", user);
		   
		   
		    RoleExample example=new RoleExample();
			
			List<Role> role = RoleService.selectByExample(example);
			
			   model.addAttribute("role", role);
			   
			   return "adduser";
	}
	   
	  //�����޸����
	   @RequestMapping("/update")
	   @RequiresPermissions("admin:update")
	   @ResponseBody	   
	   public MessageObject update(User user){
		   
		   MessageObject me=null;
		   
		   //�������һ����
		   String salt=UUID.randomUUID().toString().substring(0,5);
		   user.setSalt(salt);
		   //�Եõ���������м���
		   Md5Hash md=new Md5Hash(user.getPassword(), salt, 3);	   
		   user.setPassword(md.toString());
		   
		   //������ѡ�񣻶�̬ѡ��
		   int update=userservice.updateByPrimaryKeySelective(user);
		   
		   if (update==1) {
			
			  me=new MessageObject(1,"ȷ���޸ģ�");
			   
			   return me;
		}
		 
		   return me;
	}
	   
}

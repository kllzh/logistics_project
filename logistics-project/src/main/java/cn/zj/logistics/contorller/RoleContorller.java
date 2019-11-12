package cn.zj.logistics.contorller;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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
import cn.zj.logistics.pojo.Permission;
import cn.zj.logistics.pojo.PermissionExample;
import cn.zj.logistics.pojo.Role;
import cn.zj.logistics.pojo.RoleExample;

import cn.zj.logistics.pojo.Role;
import cn.zj.logistics.pojo.RoleExample;
import cn.zj.logistics.pojo.RoleExample.Criteria;
import cn.zj.logistics.pojo.User;
import cn.zj.logistics.pojo.UserExample;
import cn.zj.logistics.service.PermissionService;
import cn.zj.logistics.service.RoleService;
import cn.zj.logistics.service.UserService;
import cn.zj.logistics.service.RoleService;

@Controller
@RequestMapping("/role")
public class RoleContorller{

	@Autowired
	private RoleService roleservic;
	
	@Autowired
    private PermissionService PermissionService;
	
	@Autowired
	private UserService UserService;
	   
	@RequestMapping("/rolePage")
	@RequiresPermissions("role:rolePage")
	public String adminpage() {
		
		return "rolePage";
	}
	
	@RequiresPermissions("role:list")
	@RequestMapping("/list")
	@ResponseBody
	public PageInfo<Role> adminpage(@RequestParam(defaultValue="1")Integer pageNum,
			@RequestParam(defaultValue="10")Integer pageSize,String keyword) {
		
		
		RoleExample example=new RoleExample();
		
	    //PageHelper:mybatis��ҳ���������д�ڲ�ѯ֮ǰ
		//pageNum����ǰҳ��pageSize����ǰҲ������
		 PageHelper.startPage(pageNum, pageSize);
		  
		 //StringUtils:�����ж�String st=null;����String st=""����String st="  "
		 //isNotBlank����Ϊ�շ���true;�շ���false
		 if (StringUtils.isNotBlank(keyword)) {
			 
			Criteria create = example.createCriteria();
			
		    create.andRolenameLike("%"+keyword+"%");
		}
		 
		 List<Role> role = roleservic.selectByExample(example);
		
		//������ҳ����ķ�ҳ��Ϣ����PageInfo
		//PageInfo�����װ���з�ҳ��Ϣ
		//�磺����������һҳ����ǰҳ������ǰҲ�ȵ�
		PageInfo<Role> pageInfo=new PageInfo<>(role);
		
		return pageInfo;
	}
	
	   @RequiresPermissions("role:delete")
	   @RequestMapping("/delete")
	   @ResponseBody
	   public  MessageObject delete(Long roleId) {
		   
		   MessageObject me=null;

             UserExample example=new UserExample();
             
            
             
      cn.zj.logistics.pojo.UserExample.Criteria Criteria = example.createCriteria();
      
                  Criteria.andRoleIdEqualTo(roleId);
                                   
             
			List<User> user = UserService.selectByExample(example);
			
			if (user.size()>0) {
				
				me=new MessageObject(0, "ɾ��ʧ���û�����ڸý�ɫ");
				
				return me;
			}
		   
		   int delete = roleservic.deleteByPrimaryKey(roleId);

			   me=new MessageObject(1, "ɾ���ɹ�");
		
		   
		  return me; 
	}
	   
	   
        //����ɾ��
	   @RequiresPermissions("role:delete")
	   @RequestMapping("/deleteall")
	   @ResponseBody
	   public  int deleteall(HttpServletRequest request) {
		   
		   int num=0;
		   
		  String [] roleId = request.getParameterValues("roleId[]");		   		  
		  
		   for (String string : roleId) { 
			   
			   long roleid=(new Integer(string));
			   
		 int delete = roleservic.deleteByPrimaryKey(roleid);
		   if (delete==1) {			   
			       num++;
		  }
	   }		   
		  return num; 
	}
	   
	   
	   //��ת��addroleҳ�������ӽ�ɫ
	  @RequestMapping("/addrole")
	   private String addrole(Model model){
		  	   
		   RoleExample example=new RoleExample();
			
			List<Role> Parent = roleservic.selectByExample(example);
			
			   model.addAttribute("Parent", Parent);
			   
			   return "addRole";
	}
	   
	   
	   //����ɫ�Ƿ����
	   
	   @RequestMapping("/checkRolerolename")
	   @ResponseBody
	   private boolean checkRolerolename(String rolename){
			  
		   RoleExample example=new RoleExample();
		   
		   Criteria create = example.createCriteria();
		            
	       Criteria EqualTo = create.andRolenameEqualTo(rolename);
		    
		  List<Role> role = roleservic.selectByExample(example);
			
				
			return role.size()>0? false:true;
		}
	   
	   
	   //��ӹ���Ա
	   @RequiresPermissions("role:insert")
	   @RequestMapping("/insert")
	   @ResponseBody
	   public MessageObject insert(Role role) {
		
		   MessageObject me=null;
		  //ǰ̨form�����ݵĲ�������Role������������һ�£�����ʹ�ö����ȡ�����ݣ�
		   int insert = roleservic.insert(role);
		   
		   if (insert==1) {
			me=new MessageObject(1,"ȷ����ӽ�ɫ��");
			return me;
		}
		   
		me=new MessageObject(0, "��ӽ�ɫʧ��");  
		   return me;
	}
	   
	   
	   //�޸Ĺ���Ա��Ϣ
	   @RequestMapping("/editrole")
	   public String edit(Model model,Long roleId){
		
		Role role= roleservic.selectByPrimaryKey(roleId);
		
		   model.addAttribute("role", role);
		   
		   System.err.println(role);
		   
		   RoleExample example=new RoleExample();
			
			List<Role> Parent = roleservic.selectByExample(example);
			
			   model.addAttribute("Parent", Parent);
			   
			   
			   
			   return "addRole";
	}
	   
	  //�����޸����
	   @RequestMapping("/update")
	   @RequiresPermissions("role:update")
	   @ResponseBody	   
	   public MessageObject update(Role role){
		   
		   MessageObject me=null;

		   //������ѡ�񣻶�̬ѡ��
		   int update=roleservic.updateByPrimaryKeySelective(role);
		   
		   if (update==1) {
			
			  me=new MessageObject(1,"ȷ���޸Ĺ���Ա��Ϣ��");
			   
			   return me;
		}
		 
		   me=new MessageObject(0,"�޸Ĺ���Ա��Ϣʧ��");
		   
		   return me;
	}
	   
	   
	   //��Ӧ�����첽����
	   @RequestMapping("/ztree")
	   @ResponseBody
	   public List<Permission> ztree() {
		
		   PermissionExample example=new PermissionExample();
		   
	List<Permission> permission = PermissionService.selectByExample(example);
	 
	     return permission;
	}
}

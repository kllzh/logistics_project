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
		
	    //PageHelper:mybatis分页插件，必须写在查询之前
		//pageNum：当前页；pageSize：当前也多少列
		 PageHelper.startPage(pageNum, pageSize);
		  
		 //StringUtils:可以判断String st=null;或者String st=""或者String st="  "
		 //isNotBlank：不为空返回true;空返回false
		 if (StringUtils.isNotBlank(keyword)) {
			 
			Criteria create = example.createCriteria();
			
		    create.andRolenameLike("%"+keyword+"%");
		}
		 
		 List<Role> role = roleservic.selectByExample(example);
		
		//创建分页插件的分页信息对象PageInfo
		//PageInfo对象封装所有分页信息
		//如：总天数；下一页，当前页数，当前也等等
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
				
				me=new MessageObject(0, "删除失败用户表存在该角色");
				
				return me;
			}
		   
		   int delete = roleservic.deleteByPrimaryKey(roleId);

			   me=new MessageObject(1, "删除成功");
		
		   
		  return me; 
	}
	   
	   
        //批量删除
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
	   
	   
	   //跳转到addrole页面进行添加角色
	  @RequestMapping("/addrole")
	   private String addrole(Model model){
		  	   
		   RoleExample example=new RoleExample();
			
			List<Role> Parent = roleservic.selectByExample(example);
			
			   model.addAttribute("Parent", Parent);
			   
			   return "addRole";
	}
	   
	   
	   //检查角色是否存在
	   
	   @RequestMapping("/checkRolerolename")
	   @ResponseBody
	   private boolean checkRolerolename(String rolename){
			  
		   RoleExample example=new RoleExample();
		   
		   Criteria create = example.createCriteria();
		            
	       Criteria EqualTo = create.andRolenameEqualTo(rolename);
		    
		  List<Role> role = roleservic.selectByExample(example);
			
				
			return role.size()>0? false:true;
		}
	   
	   
	   //添加管理员
	   @RequiresPermissions("role:insert")
	   @RequestMapping("/insert")
	   @ResponseBody
	   public MessageObject insert(Role role) {
		
		   MessageObject me=null;
		  //前台form表单传递的参数名跟Role对象中属性名一致，可以使用对象获取表单数据；
		   int insert = roleservic.insert(role);
		   
		   if (insert==1) {
			me=new MessageObject(1,"确认添加角色？");
			return me;
		}
		   
		me=new MessageObject(0, "添加角色失败");  
		   return me;
	}
	   
	   
	   //修改管理员信息
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
	   
	  //进行修改语句
	   @RequestMapping("/update")
	   @RequiresPermissions("role:update")
	   @ResponseBody	   
	   public MessageObject update(Role role){
		   
		   MessageObject me=null;

		   //按主键选择；动态选择
		   int update=roleservic.updateByPrimaryKeySelective(role);
		   
		   if (update==1) {
			
			  me=new MessageObject(1,"确认修改管理员信息？");
			   
			   return me;
		}
		 
		   me=new MessageObject(0,"修改管理员信息失败");
		   
		   return me;
	}
	   
	   
	   //响应树的异步请求
	   @RequestMapping("/ztree")
	   @ResponseBody
	   public List<Permission> ztree() {
		
		   PermissionExample example=new PermissionExample();
		   
	List<Permission> permission = PermissionService.selectByExample(example);
	 
	     return permission;
	}
}

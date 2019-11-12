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
import cn.zj.logistics.pojo.Role;
import cn.zj.logistics.pojo.RoleExample;

import cn.zj.logistics.pojo.Permission;
import cn.zj.logistics.pojo.PermissionExample;
import cn.zj.logistics.pojo.PermissionExample.Criteria;
import cn.zj.logistics.service.RoleService;
import cn.zj.logistics.service.PermissionService;

@Controller
@RequestMapping("/permission")
public class PermissionContorller{

	@Autowired
	private PermissionService permissionservic;
	
	@Autowired
	private RoleService RoleService;
	 
	@RequiresPermissions("permission:permissionPage")
	@RequestMapping("/permissionPage")
	public String adminpage() {
		
		return "permissionPage";
	}
	
	@RequiresPermissions("permission:list")
	@RequestMapping("/list")
	@ResponseBody
	public PageInfo<Permission> adminpage(@RequestParam(defaultValue="1")Integer pageNum,
			@RequestParam(defaultValue="10")Integer pageSize,String keyword) {
		
		
		PermissionExample example=new PermissionExample();
		
	    //PageHelper:mybatis分页插件，必须写在查询之前
		//pageNum：当前页；pageSize：当前也多少列
		 PageHelper.startPage(pageNum, pageSize);
		  
		 //StringUtils:可以判断String st=null;或者String st=""或者String st="  "
		 //isNotBlank：不为空返回true;空返回false
		 if (StringUtils.isNotBlank(keyword)) {
			 
			Criteria create = example.createCriteria();
			
		    create.andNameLike("%"+keyword+"%");
		}
		 
		 List<Permission> user = permissionservic.selectByExample(example);
		
		//创建分页插件的分页信息对象PageInfo
		//PageInfo对象封装所有分页信息
		//如：总天数；下一页，当前页数，当前也等等
		PageInfo<Permission> pageInfo=new PageInfo<>(user);
		
		return pageInfo;
	}
	
	   @RequiresPermissions("permission:delete")
	   @RequestMapping("/delete")
	   @ResponseBody
	   public  MessageObject delete(Long permissionId) {
		   
		   MessageObject me=null;

		   PermissionExample example=new PermissionExample();;
		   
		   Criteria criteria = example.createCriteria();
		   
		   criteria.andParentIdEqualTo(permissionId);
		   
		   System.err.println(permissionId);
		   List<Permission> permission = permissionservic.selectByExample(example);
		   
		   if (permission.size()>0) {
			
			   me=new MessageObject(0,"该权限有子权限不能删除");
			   
			   return me; 
		}
 
		   int delete = permissionservic.deleteByPrimaryKey(permissionId);
		   
		   if (delete==1) {	
			   
			   me=new MessageObject(1, "删除成功");
		}
		   
		  return me; 
	}
	   
	   
        //批量删除
	   @RequiresPermissions("permission:delete")
	   @RequestMapping("/deleteall")
	   @ResponseBody
	   public  int deleteall(HttpServletRequest request) {
		   
		   int num=0;
		   
		  String [] userId = request.getParameterValues("userId[]");		   		  
		  
		   for (String string : userId) { 
			   
			   long userid=(new Integer(string));
			   
		 int delete = permissionservic.deleteByPrimaryKey(userid);
		   if (delete==1) {			   
			       num++;
		  }
	   }		   
		  return num; 
	}
	   
	   
	   //跳转到addpermission页面进行添加权限
	  @RequestMapping("/addpermission")
	   private String adduser(Model model){
		  	   
		   PermissionExample example=new PermissionExample();
			
			List<Permission> Parent = permissionservic.selectByExample(example);
			
			   model.addAttribute("Parent", Parent);
			   
			   return "addpermission";
	}
	   
	   
	   //检查权限是否存在
	   
	   @RequestMapping("/checkPermissionname")
	   @ResponseBody
	   private boolean checkPermissionname(String name){
			  
		   PermissionExample example=new PermissionExample();
		   
		   Criteria create = example.createCriteria();
		            
	       Criteria EqualTo = create.andNameEqualTo(name);
		    
		  List<Permission> permission = permissionservic.selectByExample(example);
			
				
			return permission.size()>0? false:true;
		}
	   
	   
	   //添加管理员
	   @RequiresPermissions("permission:insert")
	   @RequestMapping("/insert")
	   @ResponseBody
	   public MessageObject insert(Permission user) {
		
		   MessageObject me=null;
		  //前台form表单传递的参数名跟Permission对象中属性名一致，可以使用对象获取表单数据；
		   int insert = permissionservic.insert(user);
		   
		   if (insert==1) {
			me=new MessageObject(1,"确认添加权限？");
			return me;
		}
		   
		me=new MessageObject(0, "添加权限失败失败");  
		   return me;
	}
	   
	   
	   //修改管理员信息
	   @RequestMapping("/editpermission")
	   public String edit(Model model,Long permissionId){
		
		Permission permission= permissionservic.selectByPrimaryKey(permissionId);
		
		   model.addAttribute("permission", permission);
		   
		   System.err.println(permission);
		   
		   PermissionExample example=new PermissionExample();
			
			List<Permission> Parent = permissionservic.selectByExample(example);
			
			   model.addAttribute("Parent", Parent);
			   
			   
			   
			   return "addpermission";
	}
	   
	  //进行修改语句
	   @RequestMapping("/update")
	   @RequiresPermissions("permission:update")
	   @ResponseBody	   
	   public MessageObject update(Permission permission){
		   
		   MessageObject me=null;

		   //按主键选择；动态选择
		   int update=permissionservic.updateByPrimaryKeySelective(permission);
		   
		   if (update==1) {
			
			  me=new MessageObject(1,"确认修改管理员信息？");
			   
			   return me;
		}
		 
		   return me;
	}
	   
}

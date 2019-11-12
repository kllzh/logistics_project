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
		
	    //PageHelper:mybatis��ҳ���������д�ڲ�ѯ֮ǰ
		//pageNum����ǰҳ��pageSize����ǰҲ������
		 PageHelper.startPage(pageNum, pageSize);
		  
		 //StringUtils:�����ж�String st=null;����String st=""����String st="  "
		 //isNotBlank����Ϊ�շ���true;�շ���false
		 if (StringUtils.isNotBlank(keyword)) {
			 
			Criteria create = example.createCriteria();
			
		    create.andNameLike("%"+keyword+"%");
		}
		 
		 List<Permission> user = permissionservic.selectByExample(example);
		
		//������ҳ����ķ�ҳ��Ϣ����PageInfo
		//PageInfo�����װ���з�ҳ��Ϣ
		//�磺����������һҳ����ǰҳ������ǰҲ�ȵ�
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
			
			   me=new MessageObject(0,"��Ȩ������Ȩ�޲���ɾ��");
			   
			   return me; 
		}
 
		   int delete = permissionservic.deleteByPrimaryKey(permissionId);
		   
		   if (delete==1) {	
			   
			   me=new MessageObject(1, "ɾ���ɹ�");
		}
		   
		  return me; 
	}
	   
	   
        //����ɾ��
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
	   
	   
	   //��ת��addpermissionҳ��������Ȩ��
	  @RequestMapping("/addpermission")
	   private String adduser(Model model){
		  	   
		   PermissionExample example=new PermissionExample();
			
			List<Permission> Parent = permissionservic.selectByExample(example);
			
			   model.addAttribute("Parent", Parent);
			   
			   return "addpermission";
	}
	   
	   
	   //���Ȩ���Ƿ����
	   
	   @RequestMapping("/checkPermissionname")
	   @ResponseBody
	   private boolean checkPermissionname(String name){
			  
		   PermissionExample example=new PermissionExample();
		   
		   Criteria create = example.createCriteria();
		            
	       Criteria EqualTo = create.andNameEqualTo(name);
		    
		  List<Permission> permission = permissionservic.selectByExample(example);
			
				
			return permission.size()>0? false:true;
		}
	   
	   
	   //��ӹ���Ա
	   @RequiresPermissions("permission:insert")
	   @RequestMapping("/insert")
	   @ResponseBody
	   public MessageObject insert(Permission user) {
		
		   MessageObject me=null;
		  //ǰ̨form�����ݵĲ�������Permission������������һ�£�����ʹ�ö����ȡ�����ݣ�
		   int insert = permissionservic.insert(user);
		   
		   if (insert==1) {
			me=new MessageObject(1,"ȷ�����Ȩ�ޣ�");
			return me;
		}
		   
		me=new MessageObject(0, "���Ȩ��ʧ��ʧ��");  
		   return me;
	}
	   
	   
	   //�޸Ĺ���Ա��Ϣ
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
	   
	  //�����޸����
	   @RequestMapping("/update")
	   @RequiresPermissions("permission:update")
	   @ResponseBody	   
	   public MessageObject update(Permission permission){
		   
		   MessageObject me=null;

		   //������ѡ�񣻶�̬ѡ��
		   int update=permissionservic.updateByPrimaryKeySelective(permission);
		   
		   if (update==1) {
			
			  me=new MessageObject(1,"ȷ���޸Ĺ���Ա��Ϣ��");
			   
			   return me;
		}
		 
		   return me;
	}
	   
}

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
		System.out.println("异常错误"+shiroLoginFailure);
		    if (shiroLoginFailure!=null) {
				if (UnknownAccountException.class.getName().equals(shiroLoginFailure)) {
					m.addAttribute("shiroLoginFileure", "亲：账户不存在");
				}else if (IncorrectCredentialsException.class.getName().equals(shiroLoginFailure)) {
					m.addAttribute("shiroLoginFileure", "亲：密码不正确");
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
		
	    //PageHelper:mybatis分页插件，必须写在查询之前
		//pageNum：当前页；pageSize：当前也多少列
		 PageHelper.startPage(pageNum, pageSize);
		  
		 //StringUtils:可以判断String st=null;或者String st=""或者String st="  "
		 //isNotBlank：不为空返回true;空返回false
		 if (StringUtils.isNotBlank(keyword)) {
			Criteria create = example.createCriteria();
			
			create.andUsernameLike("%"+keyword+"%");
		}
		 
		 List<User> user = userservice.selectByExample(example);
		
		//创建分页插件的分页信息对象PageInfo
		//PageInfo对象封装所有分页信息
		//如：总天数；下一页，当前页数，当前也等等
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
			   
			   me=new MessageObject(1, "删除成功");
		}
		   
		  return me; 
	}
	   
	   
        //批量删除
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
	   
	   
	   //添加管理员
	   @RequestMapping("/adduser")
	   public String adduser(Model model){
		  	   
		RoleExample example=new RoleExample();
		
		List<Role> role = RoleService.selectByExample(example);
		
		   model.addAttribute("role", role);
		   return "adduser";
	}
	   
	   
	   //检查用户是否存在
	   
	   @RequestMapping("/checkUsername")
	   @ResponseBody
	   public boolean checkUsername(String username){
			  
		   UserExample example=new UserExample();
		   
		   Criteria create = example.createCriteria();
		            
	       Criteria EqualTo = create.andUsernameEqualTo(username);
		    
		  List<User> user = userservice.selectByExample(example);
			
				
			return user.size()>0? false:true;
		}
	   
	   
	   //添加管理员
	   @RequiresPermissions("admin:insert")
	   @RequestMapping("/insert")
	   @ResponseBody
	   public MessageObject insert(User user) {
		
		   MessageObject me=null;
		   
		   user.setCreateDate(new Date());
		   
		   //随机产生一个盐
		   String salt=UUID.randomUUID().toString().substring(0,5);
		   user.setSalt(salt);
		   //对得到的密码进行加密
		   Md5Hash md=new Md5Hash(user.getPassword(), salt, 3);	   
		   user.setPassword(md.toString());
		   
		  //前台form表单传递的参数名跟User对象中属性名一致，可以使用对象获取表单数据；
		   int insert = userservice.insert(user);
		   
		   if (insert==1) {
			me=new MessageObject(1,"确认添加管理员？");
			return me;
		}
		   
		me=new MessageObject(0, "添加管理员失败");  
		   return me;
	}
	   
	   
	   //修改管理员信息
	   @RequestMapping("/edit")
	   public String edit(Model model,Long userid) {
		
		User user= userservice.selectByPrimaryKey(userid);
		
		   model.addAttribute("user", user);
		   
		   
		    RoleExample example=new RoleExample();
			
			List<Role> role = RoleService.selectByExample(example);
			
			   model.addAttribute("role", role);
			   
			   return "adduser";
	}
	   
	  //进行修改语句
	   @RequestMapping("/update")
	   @RequiresPermissions("admin:update")
	   @ResponseBody	   
	   public MessageObject update(User user){
		   
		   MessageObject me=null;
		   
		   //随机产生一个盐
		   String salt=UUID.randomUUID().toString().substring(0,5);
		   user.setSalt(salt);
		   //对得到的密码进行加密
		   Md5Hash md=new Md5Hash(user.getPassword(), salt, 3);	   
		   user.setPassword(md.toString());
		   
		   //按主键选择；动态选择
		   int update=userservice.updateByPrimaryKeySelective(user);
		   
		   if (update==1) {
			
			  me=new MessageObject(1,"确认修改？");
			   
			   return me;
		}
		 
		   return me;
	}
	   
}

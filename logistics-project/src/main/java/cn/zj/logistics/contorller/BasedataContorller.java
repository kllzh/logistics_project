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

import cn.zj.logistics.mapper.BasedataMapper;
import cn.zj.logistics.mo.MessageObject;
import cn.zj.logistics.pojo.Permission;
import cn.zj.logistics.pojo.PermissionExample;
import cn.zj.logistics.pojo.Basedata;
import cn.zj.logistics.pojo.BasedataExample;

import cn.zj.logistics.pojo.Basedata;
import cn.zj.logistics.pojo.BasedataExample;
import cn.zj.logistics.pojo.BasedataExample.Criteria;
import cn.zj.logistics.pojo.Basedata;
import cn.zj.logistics.pojo.BasedataExample;
import cn.zj.logistics.service.PermissionService;
import cn.zj.logistics.service.BasedataService;
import cn.zj.logistics.service.BasedataService;
import cn.zj.logistics.service.BasedataService;

@Controller
@RequestMapping("/basedata")
public class BasedataContorller{

	@Autowired
	private BasedataService basedataservic;
	

	@Autowired
	private BasedataService BasedataService;
	
	@RequiresPermissions("basicData:basicDatapage")
	@RequestMapping("/basedataPage")
	public String adminpage() {
		
		return "basedataPage";
	}
	
	@RequiresPermissions("basicData:list")
	@RequestMapping("/list")
	@ResponseBody
	public PageInfo<Basedata> adminpage(@RequestParam(defaultValue="1")Integer pageNum,
			@RequestParam(defaultValue="10")Integer pageSize,String keyword) {
		
		
		BasedataExample example=new BasedataExample();
		
	    //PageHelper:mybatis分页插件，必须写在查询之前
		//pageNum：当前页；pageSize：当前也多少列
		 PageHelper.startPage(pageNum, pageSize);
		  
		 //StringUtils:可以判断String st=null;或者String st=""或者String st="  "
		 //isNotBlank：不为空返回true;空返回false
		 if (StringUtils.isNotBlank(keyword)) {
			 
			Criteria create = example.createCriteria();
			
		    create.andBaseNameLike("%"+keyword+"%");
		}
		 
		 List<Basedata> basedata = basedataservic.selectByExample(example);
		
		//创建分页插件的分页信息对象PageInfo
		//PageInfo对象封装所有分页信息
		//如：总天数；下一页，当前页数，当前也等等
		PageInfo<Basedata> pageInfo=new PageInfo<>(basedata);
		
		return pageInfo;
	}
	
	   @RequiresPermissions("basicData:delete")
	   @RequestMapping("/delete")
	   @ResponseBody
	   public  MessageObject delete(Long baseId) {
		   
		   MessageObject me=null;

             BasedataExample example=new BasedataExample();
             
            
             
      cn.zj.logistics.pojo.BasedataExample.Criteria Criteria = example.createCriteria();
      
                  Criteria.andParentIdEqualTo(baseId);
                                   
             
			List<Basedata> user = BasedataService.selectByExample(example);
			
			if (user.size()>0) {
				
				me=new MessageObject(0, "删除失败该权限存在子权限");
				
				return me;
			}
		   
		   int delete = basedataservic.deleteByPrimaryKey(baseId);

			   me=new MessageObject(1, "删除成功");
		
		   
		  return me; 
	}
	   
	   
        //批量删除
	   @RequiresPermissions("basicData:delete")
	   @RequestMapping("/deleteall")
	   @ResponseBody
	   public  int deleteall(HttpServletRequest request) {
		   
		   int num=0;
		   
		  String [] basedataId = request.getParameterValues("basedataId[]");		   		  
		  
		   for (String string : basedataId) { 
			   
			   long basedataid=(new Integer(string));
			   
		 int delete = basedataservic.deleteByPrimaryKey(basedataid);
		   if (delete==1) {			   
			       num++;
		  }
	   }		   
		  return num; 
	}
	   
	   
	   //跳转到addbasedata页面进行基础数据角色
	  @RequestMapping("/addbasedata")
	   public String addbasedata(Model model){
		  	   
		   BasedataExample example=new BasedataExample();
			
		   Criteria Criteria = example.createCriteria();
		   
		   Criteria.andParentIdIsNull();
		   
			List<Basedata> Parent = basedataservic.selectByExample(example);
			
			   model.addAttribute("Parent", Parent);
			   
			   return "addBasedata";
	}
	   
	   
	   //检查角色是否存在
	   
	   @RequestMapping("/checkbaseName")
	   @ResponseBody
	   public boolean checkbaseName(String baseName){
			  
		   BasedataExample example=new BasedataExample();
		   
		   Criteria create = example.createCriteria();
		            
	       Criteria EqualTo = create.andBaseNameEqualTo(baseName);
		    
		  List<Basedata> basedata = basedataservic.selectByExample(example);
			
				
			return basedata.size()>0? false:true;
		}
	   
	   
	   //基础数据管理员
	   @RequiresPermissions("basicData:insert")
	   @RequestMapping("/insert")
	   @ResponseBody
	   public MessageObject insert(Basedata basedata) {
		
		   MessageObject me=null;
		  //前台form表单传递的参数名跟Basedata对象中属性名一致，可以使用对象获取表单数据；
		   int insert = basedataservic.insert(basedata);
		   
		   if (insert==1) {
			me=new MessageObject(1,"确认添加基础数据？");
			return me;
		}
		   
		me=new MessageObject(0, "基础数据添加失败");  
		   return me;
	}
	   
	   
	   //修改管理员信息
	   @RequestMapping("/editbasedata")
	   public String edit(Model model,Long baseId){
		
		Basedata basedata= basedataservic.selectByPrimaryKey(baseId);
		
		   model.addAttribute("basedata", basedata);
		   
		   System.err.println(basedata);
		   
		   BasedataExample example=new BasedataExample();
		   
           Criteria Criteria = example.createCriteria();
		   
		   Criteria.andParentIdIsNull();
			
			List<Basedata> Parent = basedataservic.selectByExample(example);
			
			   model.addAttribute("Parent", Parent);
			   
			   
			   
			   return "addBasedata";
	}
	   
	  //进行修改语句
	   @RequestMapping("/update")
	   @RequiresPermissions("basicData:update")
	   @ResponseBody
	   
	   public MessageObject update(Basedata basedata){
		   
		   MessageObject me=null;

		   //按主键选择；动态选择
		   int update=basedataservic.updateByPrimaryKeySelective(basedata);
		   
		   if (update==1) {
			
			  me=new MessageObject(1,"确认修改管理员信息？");
			   
			   return me;
		}
		 
		   me=new MessageObject(0,"修改管理员信息失败");
		   
		   return me;
	}
	   
	   
	 
}

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
		
	    //PageHelper:mybatis��ҳ���������д�ڲ�ѯ֮ǰ
		//pageNum����ǰҳ��pageSize����ǰҲ������
		 PageHelper.startPage(pageNum, pageSize);
		  
		 //StringUtils:�����ж�String st=null;����String st=""����String st="  "
		 //isNotBlank����Ϊ�շ���true;�շ���false
		 if (StringUtils.isNotBlank(keyword)) {
			 
			Criteria create = example.createCriteria();
			
		    create.andBaseNameLike("%"+keyword+"%");
		}
		 
		 List<Basedata> basedata = basedataservic.selectByExample(example);
		
		//������ҳ����ķ�ҳ��Ϣ����PageInfo
		//PageInfo�����װ���з�ҳ��Ϣ
		//�磺����������һҳ����ǰҳ������ǰҲ�ȵ�
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
				
				me=new MessageObject(0, "ɾ��ʧ�ܸ�Ȩ�޴�����Ȩ��");
				
				return me;
			}
		   
		   int delete = basedataservic.deleteByPrimaryKey(baseId);

			   me=new MessageObject(1, "ɾ���ɹ�");
		
		   
		  return me; 
	}
	   
	   
        //����ɾ��
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
	   
	   
	   //��ת��addbasedataҳ����л������ݽ�ɫ
	  @RequestMapping("/addbasedata")
	   public String addbasedata(Model model){
		  	   
		   BasedataExample example=new BasedataExample();
			
		   Criteria Criteria = example.createCriteria();
		   
		   Criteria.andParentIdIsNull();
		   
			List<Basedata> Parent = basedataservic.selectByExample(example);
			
			   model.addAttribute("Parent", Parent);
			   
			   return "addBasedata";
	}
	   
	   
	   //����ɫ�Ƿ����
	   
	   @RequestMapping("/checkbaseName")
	   @ResponseBody
	   public boolean checkbaseName(String baseName){
			  
		   BasedataExample example=new BasedataExample();
		   
		   Criteria create = example.createCriteria();
		            
	       Criteria EqualTo = create.andBaseNameEqualTo(baseName);
		    
		  List<Basedata> basedata = basedataservic.selectByExample(example);
			
				
			return basedata.size()>0? false:true;
		}
	   
	   
	   //�������ݹ���Ա
	   @RequiresPermissions("basicData:insert")
	   @RequestMapping("/insert")
	   @ResponseBody
	   public MessageObject insert(Basedata basedata) {
		
		   MessageObject me=null;
		  //ǰ̨form�����ݵĲ�������Basedata������������һ�£�����ʹ�ö����ȡ�����ݣ�
		   int insert = basedataservic.insert(basedata);
		   
		   if (insert==1) {
			me=new MessageObject(1,"ȷ����ӻ������ݣ�");
			return me;
		}
		   
		me=new MessageObject(0, "�����������ʧ��");  
		   return me;
	}
	   
	   
	   //�޸Ĺ���Ա��Ϣ
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
	   
	  //�����޸����
	   @RequestMapping("/update")
	   @RequiresPermissions("basicData:update")
	   @ResponseBody
	   
	   public MessageObject update(Basedata basedata){
		   
		   MessageObject me=null;

		   //������ѡ�񣻶�̬ѡ��
		   int update=basedataservic.updateByPrimaryKeySelective(basedata);
		   
		   if (update==1) {
			
			  me=new MessageObject(1,"ȷ���޸Ĺ���Ա��Ϣ��");
			   
			   return me;
		}
		 
		   me=new MessageObject(0,"�޸Ĺ���Ա��Ϣʧ��");
		   
		   return me;
	}
	   
	   
	 
}

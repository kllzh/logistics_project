package cn.zj.logistics.customrealm;

import static org.hamcrest.CoreMatchers.nullValue;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import cn.zj.logistics.pojo.User;
import cn.zj.logistics.service.PermissionService;
import cn.zj.logistics.service.UserService;

public class MycustomRealm extends AuthorizingRealm{

	@Autowired	
	private UserService UserService;
	
	@Autowired
	private PermissionService PermissionService;
	
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		
		    String username =(String)token.getPrincipal();
		          System.out.println(username);
		          
		    User user = UserService.selectByuser(username);
		      
		   
		    
		
		  if (user==null) {
		 
			  return null; 
		 }
		
		    
		    String password=user.getPassword();
		    
		   
		    
		    ByteSource byteSource=ByteSource.Util.bytes(user.getSalt());
		    
			SimpleAuthenticationInfo authenticationInfo=new SimpleAuthenticationInfo(user, password, byteSource, this.getName());
		
		    return authenticationInfo;
	}
	
	
	
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		         
		User user = (User) principals.getPrimaryPrincipal();
		
		String permissionIds = user.getPermissionIds();
		
		String [] str=permissionIds.split(",");
		
		List<Long>list=new ArrayList<>();
		
		for (String string : str) {
			
			//list.add(Long.valueOf(string));
			
			long long1=new Integer(string);
			list.add(long1);
		}
		
		
		List<String>list2=PermissionService.selectBypermission(list);
		     
		  //创建授权对象
		SimpleAuthorizationInfo authorizationInfo=new SimpleAuthorizationInfo();
		//添加权限
		authorizationInfo.addStringPermissions(list2);
	
		return authorizationInfo;
	}

}

package cn.zj.logistics.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

import cn.zj.logistics.pojo.User;

public class CustomLogout extends FormAuthenticationFilter{

	protected boolean onLoginSuccess(AuthenticationToken token, Subject subject,
            ServletRequest request, ServletResponse response) throws Exception {
		
		          WebUtils.getAndClearSavedRequest(request);
			
			return super.onLoginSuccess(token, subject, request, response);
    }
	
	
	//重新onAccessDenied()在登陆前先进行验证码验证
	
	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		      
		
		           HttpServletRequest req=(HttpServletRequest)request;
		         //获取登陆表单的验证码属性		       
		       String Verification =(String)req.getParameter("Verification"); 
                   //获取验证码生成的session属性
		       String rand =(String)req.getSession().getAttribute("rand");
                     //判断两个是否不为null在进行判断防止空指针异常
		         if (StringUtils.isNotBlank(Verification) && StringUtils.isNotBlank(rand)){
		        	 
		           if (!Verification.toLowerCase().equals(rand.toLowerCase())) {
		        	   
						   req.setAttribute("shiroLoginFileure","亲：验证码不正确");
						   
				 req.getRequestDispatcher("/login.jsp").forward(req, response);
				 
				 return false;
		   }
		           
		}else if (Verification=="") {
			 req.setAttribute("Verification","请输入验证码");
			 req.getRequestDispatcher("/login.jsp").forward(req, response);			 
			 return false;
		}
			
		
		   
		return super.onAccessDenied(request, response);
	}
	
	   
	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
		   //获取主题对象      
		Subject subject = getSubject(request, response);
		//重主体中获取session
		Session session = subject.getSession();
		
		/*
		 * shiro框架认证成功以后，默认的信息是存储在Session中的， Session的缺点就是如果数据浏览器关闭，Session失效，下次必须再次登录。
		 * 从而shiro提供了记住我的功能，他的底层使用的是cookie技术，connkie(自动登录)，
		 * 本次的登陆成功会向cookie写身份信息，下次再次登陆的时候从cookie中取得身份信息自动完成登陆
		 * 但是shiro默认回去session中获取存储的信息，而cookie中写入的身份信息，shiro无法去判断
		 * 从而要吧cookie中获取用户登陆的身份信息写进session中，否则无法完成自主登陆
		 * 此时我们就需要自己自定义在isAccessAllowed方法原有的基础上，再添加内容；
		 * 场景(再登陆login.jsp的时候，勾选记住我功能，登陆的时候是需要进行一个认证的，认证身份信息
		 * 是否符合，然后再次关闭浏览器的时候，我走登陆页面，直接反问index或者其他页面，authc过滤器将会
		 * 判断之前是否认证过，不会走认证身份这条路径，我们自定义功能if条件设置：如果主体没认证(取反，为true)
		 * 并且判断是否有记住我功能，如果满足则从主体中获取身份(从记住我的cookie中获取),然后将身份共享
		 * 到session中，这样cookie中存储的用户身份即可生效。当在关闭浏览器的时候，那么session中的
		 * 信息也不存在了，再次直接反问index页面获取其他页面的时候，依旧会走这个isAccessAllowed()方法
		 * 判断之前是否认证，那么此自定义功能即生效，session中就存有身份信息了，进入任何页面不需重新登陆 页面)
		 */		
		    if (!subject.isAuthenticated() && subject.isRemembered()) {				
		    	
		    	//获取主题身份信息(重cookie中获取身份)
		    	User principals =(User)subject.getPrincipal();
		    	//共享到session
		    	session.setAttribute("user", principals);
			}
		
		
		
		return subject.isAuthenticated() || subject.isRemembered();
	}
	
	
	
}

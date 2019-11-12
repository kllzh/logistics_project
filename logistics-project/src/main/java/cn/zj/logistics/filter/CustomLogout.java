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
	
	
	//����onAccessDenied()�ڵ�½ǰ�Ƚ�����֤����֤
	
	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		      
		
		           HttpServletRequest req=(HttpServletRequest)request;
		         //��ȡ��½������֤������		       
		       String Verification =(String)req.getParameter("Verification"); 
                   //��ȡ��֤�����ɵ�session����
		       String rand =(String)req.getSession().getAttribute("rand");
                     //�ж������Ƿ�Ϊnull�ڽ����жϷ�ֹ��ָ���쳣
		         if (StringUtils.isNotBlank(Verification) && StringUtils.isNotBlank(rand)){
		        	 
		           if (!Verification.toLowerCase().equals(rand.toLowerCase())) {
		        	   
						   req.setAttribute("shiroLoginFileure","�ף���֤�벻��ȷ");
						   
				 req.getRequestDispatcher("/login.jsp").forward(req, response);
				 
				 return false;
		   }
		           
		}else if (Verification=="") {
			 req.setAttribute("Verification","��������֤��");
			 req.getRequestDispatcher("/login.jsp").forward(req, response);			 
			 return false;
		}
			
		
		   
		return super.onAccessDenied(request, response);
	}
	
	   
	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
		   //��ȡ�������      
		Subject subject = getSubject(request, response);
		//�������л�ȡsession
		Session session = subject.getSession();
		
		/*
		 * shiro�����֤�ɹ��Ժ�Ĭ�ϵ���Ϣ�Ǵ洢��Session�еģ� Session��ȱ������������������رգ�SessionʧЧ���´α����ٴε�¼��
		 * �Ӷ�shiro�ṩ�˼�ס�ҵĹ��ܣ����ĵײ�ʹ�õ���cookie������connkie(�Զ���¼)��
		 * ���εĵ�½�ɹ�����cookieд�����Ϣ���´��ٴε�½��ʱ���cookie��ȡ�������Ϣ�Զ���ɵ�½
		 * ����shiroĬ�ϻ�ȥsession�л�ȡ�洢����Ϣ����cookie��д��������Ϣ��shiro�޷�ȥ�ж�
		 * �Ӷ�Ҫ��cookie�л�ȡ�û���½�������Ϣд��session�У������޷����������½
		 * ��ʱ���Ǿ���Ҫ�Լ��Զ�����isAccessAllowed����ԭ�еĻ����ϣ���������ݣ�
		 * ����(�ٵ�½login.jsp��ʱ�򣬹�ѡ��ס�ҹ��ܣ���½��ʱ������Ҫ����һ����֤�ģ���֤�����Ϣ
		 * �Ƿ���ϣ�Ȼ���ٴιر��������ʱ�����ߵ�½ҳ�棬ֱ�ӷ���index��������ҳ�棬authc����������
		 * �ж�֮ǰ�Ƿ���֤������������֤�������·���������Զ��幦��if�������ã��������û��֤(ȡ����Ϊtrue)
		 * �����ж��Ƿ��м�ס�ҹ��ܣ����������������л�ȡ���(�Ӽ�ס�ҵ�cookie�л�ȡ),Ȼ����ݹ���
		 * ��session�У�����cookie�д洢���û���ݼ�����Ч�����ڹر��������ʱ����ôsession�е�
		 * ��ϢҲ�������ˣ��ٴ�ֱ�ӷ���indexҳ���ȡ����ҳ���ʱ�����ɻ������isAccessAllowed()����
		 * �ж�֮ǰ�Ƿ���֤����ô���Զ��幦�ܼ���Ч��session�оʹ��������Ϣ�ˣ������κ�ҳ�治�����µ�½ ҳ��)
		 */		
		    if (!subject.isAuthenticated() && subject.isRemembered()) {				
		    	
		    	//��ȡ���������Ϣ(��cookie�л�ȡ���)
		    	User principals =(User)subject.getPrincipal();
		    	//����session
		    	session.setAttribute("user", principals);
			}
		
		
		
		return subject.isAuthenticated() || subject.isRemembered();
	}
	
	
	
}

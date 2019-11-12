package cn.zj.logistics.contorller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexContorller {

	
	@RequestMapping("/index")
	public String index() {
				
		
		return "index";
	}
	
	    
	    @RequestMapping("/welcome")   
	
	    public String welcome() {
			
			
			return "welcome";
		}
}

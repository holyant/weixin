package web.action;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.util.ServletContextAware;
import com.opensymphony.xwork2.ActionSupport;

import service.HelloService;




public class HelloWorldAction extends ActionSupport implements ServletResponseAware, ServletRequestAware, ServletContextAware{
    Logger logger = Logger.getLogger(this.getClass());
    private HttpServletResponse response;
    private HttpServletRequest request;
    private ServletContext servletContext;


    public void setServletResponse(HttpServletResponse arg0) {
         this.response = arg0;
    }
    public void setServletRequest(HttpServletRequest arg0) {
         this.request = arg0;
    }
    public void setServletContext(ServletContext context) {
         this.servletContext = context;
    }


	private HelloService helloService;
	public String hello(){
		request.setAttribute("holyant", "你好");
		
	
		
		return SUCCESS;
	}
	public void setHelloService(HelloService helloService) {
		this.helloService = helloService;
	}
	
	
}

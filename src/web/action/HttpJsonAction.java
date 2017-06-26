package web.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;

public class HttpJsonAction extends ActionSupport implements ServletRequestAware{
	private HttpServletRequest request;
	
	public void test(){
		System.out.println("action开始");
		Map map = (HashMap) request.getParameterMap();
		System.out.println(map.get("query"));
		Iterator entries = map.entrySet().iterator();
		Map.Entry entry; 
	    String value = ""; 
	    while (entries.hasNext()) { 
	        entry = (Map.Entry) entries.next(); 
	        value = (String) entry.getKey(); 
	        
	    }
		System.out.println(value);
		JSONObject obj = JSONObject.fromObject(value);
		System.out.println(obj.get("query"));

		
		
	}

	
	@Override
	public void setServletRequest(HttpServletRequest arg0) {
		this.request = arg0;
	}
	
	
}

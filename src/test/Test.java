package test;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import sun.reflect.ReflectionFactory.GetReflectionFactoryAction;

import dao.HelloDao;

public class Test {
	public static void main(String[] args) {
//		BeanFactory factory = new ClassPathXmlApplicationContext(
//				"applicationContext.xml");
//		HelloDao helloDao =  (HelloDao) factory.getBean("helloDao");
//		helloDao.deleteEmp(1);
//		System.out.println("hello");
        Test test = new Test();
        test.getRemoteId();
	}
	
	public void getRemoteId(){
        String name = "userName";
        String gender = "userGender";
        String birthDate = "birthDate";
        String birthHour = "birthHour";
        String birthMin = "birthMin";
        birthDate +=" "+birthHour+":"+birthMin;
        String addrId = "borough";
        String productId = "ProductId";
        String birthDateAccurate = "BirthAccurateSelect";
        String add_url = "http://localhost:8080/si/httpJson!test";
        String query = " {\"mainUser\":{\"name\":\""+name+"\",\"gender\":\""+gender+"\",\"birthDate\":\""+birthDate+"\",\"birthDateAccurate\":\""+birthDateAccurate+"\",\"addrId\":\""+addrId+"\"},\"productId\":\""+productId+"\"}";
        System.out.println(query);
        try {
            URL url = new URL(add_url);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
//            connection.setRequestProperty("enctype","multipart/form-data");
            connection.connect();
            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            JSONObject obj = new JSONObject();
             
            String token = "d5f224c9f83874da5b5025794c773e8e";
            obj.put("test", " {\"mainUser\":{\"name\":\"");
            obj.put("query", query);
            obj.put("token", token);
            out.writeBytes(obj.toString());
            System.out.println(obj.toString());
            out.flush();
            out.close();
             
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String lines;
            StringBuffer sbf = new StringBuffer();
             while ((lines = reader.readLine()) != null) {
                    lines = new String(lines.getBytes(), "utf-8");
                    sbf.append(lines);
                }
                System.out.println(sbf);
                reader.close();
                // 断开连接
                connection.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

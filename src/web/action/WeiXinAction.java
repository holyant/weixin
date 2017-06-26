package web.action;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;

import util.SaxHandler;

import com.opensymphony.xwork2.ActionSupport;

public class WeiXinAction extends ActionSupport implements ServletRequestAware,
		SessionAware, ServletResponseAware {
	Logger logger = Logger.getLogger(this.getClass());
	private HttpServletRequest request;
	private HttpServletResponse response;
	private Map<String, Object> session;

	public String execute() throws Exception {
		logger.debug("weixin action-execute方法开始");
//		String qs = request.getQueryString();
//		Map<String, String> map = parse(qs);
		// 校验
		response.setCharacterEncoding("utf-8");
		PrintWriter pw = null;
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser parser = spf.newSAXParser();
		SaxHandler handler = new SaxHandler("xml");
		InputStream is = request.getInputStream();
		
		
		parser.parse(is, handler);
		List<HashMap<String, String>> list = handler.getList();
		
		logger.debug("holyant:"+list.toString());
		logger.debug("holyant:FromUserName:"+list.get(0).get("FromUserName"));
		logger.debug("holyant:Content:"+list.get(0).get("Content"));
		logger.debug("holyant:ToUserName:"+list.get(0).get("ToUserName"));
		logger.debug("holyant:MsgType:"+list.get(0).get("MsgType"));
		is.close();
		long CreateTime = System.currentTimeMillis() / 1000;
		String reXml = null;
		reXml = "<xml>"+
		"<ToUserName>"+list.get(0).get("FromUserName")+"</ToUserName>"+
		"<FromUserName>"+list.get(0).get("ToUserName")+"</FromUserName>"+
		"<CreateTime>"+CreateTime+"</CreateTime>"+
		"<MsgType>"+list.get(0).get("MsgType")+"</MsgType>"+
		"<Content>"+getAnswer(list.get(0).get("Content"))+"</Content>"+
		"</xml>";
		
		logger.debug("holyant"+reXml);
		try {
			pw = response.getWriter();
			pw.write(reXml);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.debug("weixin action-execute方法结束");
		return null;
	}
	//图灵机器人
	public String getAnswer(String ask) throws Exception{
		String APIKEY = "ff90d9dc3f02df998fa8459954d2b3c5";
		String INFO = URLEncoder.encode(ask, "utf-8");
		String getURL = "http://www.tuling123.com/openapi/api?key=" + APIKEY
				+ "&info=" + INFO;
		URL getUrl = new URL(getURL);
		HttpURLConnection connection = (HttpURLConnection) getUrl
				.openConnection();
		connection.connect();
	
		// 取得输入流，并使用Reader读取
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				connection.getInputStream(), "utf-8"));
		StringBuffer sb = new StringBuffer();
		String line = "";
		while ((line = reader.readLine()) != null) {
			sb.append(line);
		}
		reader.close();
		// 断开连接
		connection.disconnect();
		logger.debug(sb);
		JSONObject obj = JSONObject.fromObject(sb.toString());
		return obj.getString("text");
	}
	private Map<String, String> parse(String qs) {
		Map<String, String> re = new HashMap<String, String>();
		String[] pars = qs.split("&");
		for (int i = 0; i < pars.length; i++) {
			re.put(pars[i].split("=")[0], pars[i].split("=")[1]);
		}
		return re;
	}

	String token = "holyant";

	// 微信服务器配置验证
	public void getToken() {
		String signature = request.getParameter("signature");// 用于验证是否微信服务器
		String timestamp = request.getParameter("timestamp");// 时间戳
		String nonce = request.getParameter("nonce");// 随机数
		String echostr = request.getParameter("echostr");// 用于返回给微信服务器
		logger.debug("signature:" + signature + ";timestamp:" + timestamp
				+ ";nonce:" + nonce + ";echostr:" + echostr);
		// 对token，timestamp，nonce进行字典排序，并sha1加密
		String[] arr = new String[] { timestamp, nonce, token };
		Arrays.sort(arr);
		String s = arr[0] + arr[1] + arr[2];
		response.setCharacterEncoding("utf-8");
		PrintWriter pw = null;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			byte[] digest = md.digest(s.getBytes("utf-8"));
			pw = response.getWriter();
			logger.debug(bytes2HexString(digest));
			if (signature.toUpperCase().equals(bytes2HexString(digest))) {
				pw.write(echostr);
				logger.debug(echostr);
			} else {
				pw = response.getWriter();
				pw.write("none");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String bytes2HexString(byte[] b) {
		String ret = "";
		for (int i = 0; i < b.length; i++) {
			String hex = Integer.toHexString(b[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			ret += hex.toUpperCase();
		}
		return ret;
	}

	@Override
	public void setServletRequest(HttpServletRequest arg0) {
		this.request = arg0;
	}

	@Override
	public void setSession(Map<String, Object> arg0) {
		this.session = arg0;
	}

	@Override
	public void setServletResponse(HttpServletResponse arg0) {
		this.response = arg0;
	}
}

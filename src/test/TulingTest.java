package test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import net.sf.json.JSONObject;


public class TulingTest {
	public static void main(String[] args)  {
		try {
			String APIKEY = "ff90d9dc3f02df998fa8459954d2b3c5";
			String INFO = URLEncoder.encode("我要看新闻", "utf-8");
			String getURL = "http://www.tuling123.com/openapi/api?key=" + APIKEY + "&info=" + INFO;
			URL getUrl = new URL(getURL);
			HttpURLConnection connection = (HttpURLConnection) getUrl.openConnection();
//			connection.setRequestMethod("GET");
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
			System.out.println(sb);
			JSONObject obj = JSONObject.fromObject(sb.toString());
			System.out.println(obj.getString("text"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		

	}
}

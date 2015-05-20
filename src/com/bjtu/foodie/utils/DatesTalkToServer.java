package com.bjtu.foodie.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.bjtu.foodie.UI.AddMomentActivity;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import android.os.StrictMode;

public class DatesTalkToServer {

	//public static final String serviceAddr = "http://101.200.174.49:3000/service/";
	//10.0.2.2
	public static final String serviceAddr = "http://219.242.243.113:3000/service/";//192.168.1.103 wifi
	public static String datesGet(String url) {
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
				.detectDiskReads().detectDiskWrites().detectNetwork()
				.penaltyLog().build());

		String result = null;
        BufferedReader reader = null;
        try {
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet();
            request.setURI(new URI(serviceAddr + url));
            HttpResponse response = client.execute(request);
            reader = new BufferedReader(new InputStreamReader(response
                    .getEntity().getContent()));
 
            StringBuffer strBuffer = new StringBuffer("");
            String line = null;
            while ((line = reader.readLine()) != null) {
                strBuffer.append(line);
            }
            result = strBuffer.toString();
 
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                    reader = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
 
        return result;
		
	}

	public static String datesPost(String url,List<NameValuePair> postParameters) {
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
				.detectDiskReads().detectDiskWrites().detectNetwork()
				.penaltyLog().build());

		String result = null;
		BufferedReader reader = null;
		try {
			DefaultHttpClient client = new DefaultHttpClient();
			HttpPost request = new HttpPost();
			request.setURI(new URI(serviceAddr + url));

			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(
					postParameters);
			request.setEntity(formEntity);

			HttpResponse response = client.execute(request);
			reader = new BufferedReader(new InputStreamReader(response
					.getEntity().getContent()));

			StringBuffer strBuffer = new StringBuffer("");
			String line = null;
			while ((line = reader.readLine()) != null) {
				strBuffer.append(line);
			}
			result = strBuffer.toString();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
					reader = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return result;
	}
	
	/*public static void post2(){
		NameValuePair pair1 = new BasicNameValuePair("name", "test");

        List<NameValuePair> pairList = new ArrayList<NameValuePair>();
        pairList.add(pair1);

        try
        {
            HttpEntity requestHttpEntity = new UrlEncodedFormEntity(
                    pairList);
            // URL使用基本URL即可，其中不需要加参数
            HttpPost httpPost = new HttpPost("http://10.0.2.2:3000/service/moment/addMoment");
            // 将请求体内容加入请求中
            httpPost.setEntity(requestHttpEntity);
            // 需要客户端对象来发送请求
            HttpClient httpClient = new DefaultHttpClient();
            // 发送请求
            HttpResponse response = httpClient.execute(httpPost);
            // 显示响应
            //showResponseResult(response);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
	}
	
	public static void post3() throws JSONException, ClientProtocolException, IOException{
		HttpPost request = new HttpPost("http://10.0.2.2:3000/service/moment/addMoment");
		// 先封装一个 JSON 对象
		JSONObject param = new JSONObject();
		param.put("name", "cmm3");
		param.put("password", "123456");
		// 绑定到请求 Entry
		StringEntity se = new StringEntity(param.toString());
		request.setEntity(se);
		// 发送请求
		HttpResponse httpResponse = new DefaultHttpClient().execute(request);
		// 得到应答的字符串，这也是一个 JSON 格式保存的数据
		String retSrc = EntityUtils.toString(httpResponse.getEntity());
		// 生成 JSON 对象
		//JSONObject result = new JSONObject( retSrc);
		//String token = result.get("token");
	}
	
	public static void post4(){
	    HttpClient httpClient = new DefaultHttpClient();  
        try {  
            HttpPost httpPost = new HttpPost("http://10.0.2.2:3000/service/moment/addMoment");  
            List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();  
            JSONObject jsonObject = new JSONObject();  
            jsonObject.put("uemail", "cmm4");  
            nameValuePair.add(new BasicNameValuePair("jsonString", jsonObject  
                    .toString()));  
            //Log.i("lifeweeker", jsonObject2.toString());  
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
        }catch (Exception e){
        	
        }
	}
	
	public static void post5(String url, Class<AddMomentActivity> class1){
		//AsyncHttpClient client = new AsyncHttpClient(); 
		SyncHttpClient client = new SyncHttpClient();
        //String url = "http://ec2-54-77-212-173.eu-west-1.compute.amazonaws.com:4242/moments"; 

        JSONObject jsonObject = new JSONObject();
        try {
        	jsonObject.put("name", "cmm5");
		} catch (JSONException e) {
		
			e.printStackTrace();
		}
        StringEntity stringEntity = null; 
        try {
			stringEntity = new StringEntity(jsonObject.toString());
		} catch (UnsupportedEncodingException e) {
	
			e.printStackTrace();
		} 

        client.post(serviceAddr + url, new AsyncHttpResponseHandler() {  
			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2,
					Throwable arg3) {
				// TODO Auto-generated method stub
				
			}


			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				// TODO Auto-generated method stub
				
			}  
        });  
	}*/
}

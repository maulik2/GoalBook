package com.example.mysql;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class JSONParser {
	
	static InputStream IS = null;
	static JSONObject JB = null;
	static String json = "";
	
	//Constructor 
	public JSONParser(){
		
	}

	public JSONObject makeHttpRequest(String url, String method, List<NameValuePair> params){
		
		try{
			//check for request method (POST or GET)
			if (method == "POST"){
				
				DefaultHttpClient hc = new DefaultHttpClient();
				HttpPost hp = new HttpPost(url);
				hp.setEntity(new UrlEncodedFormEntity(params));
				
			} else if (method == "GET") {
				
				DefaultHttpClient hc = new DefaultHttpClient();
				String paramString = URLEncodedUtils.format(params,"utf-8") ;
				url += "?" + paramString;
				HttpGet hg = new HttpGet(url);
				
				HttpResponse hr = hc.execute(hg) ;
				HttpEntity he = hr.getEntity();
				IS = he.getContent();
				
			}
		}catch (UnsupportedEncodingException e){
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try{
			BufferedReader reader = new BufferedReader (new InputStreamReader (IS, "iso-8859"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while((line = reader.readLine()) != null){
				sb.append(line + "\n");
			}
			IS.close();
			json = sb.toString();
			
		} catch(Exception e){
			Log.e("Buffer Error", "Conversion Error"+e.toString());
		}
		try{
			JB = new JSONObject(json);
		} catch(JSONException e){
			Log.e("JSON Parse", e.toString());
		}
			return JB;
	}
}

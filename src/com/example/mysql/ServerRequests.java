package com.example.mysql;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public class ServerRequests {
	
	ProgressDialog pd;
	
	public static final int CONNECTION_TIMEOUT = 1000*15;
	public static final String SERVER_ADDRESS ="http://budgetbuddy.dx.am/";
	
	public ServerRequests(Context ctx){
		pd = new ProgressDialog(ctx);
		pd.setCancelable(false);
		pd.setTitle("Processing");
		pd.setMessage("Please wait...");
	}

	
	public void storeUserDataInBackgorund(User user, GetUserCallback usercallback){
		pd.show();
		new StoreUserDataAsyncTask(user, usercallback).execute();
	}
	
	public void fetchUserDataInBackgorund(User user, GetUserCallback usercallback){
		pd.show();
		new fetchUserDataAsyncTask(user, usercallback).execute();
	}
	
	public class StoreUserDataAsyncTask extends AsyncTask<Void,Void,Void>{
		
		User user;
		GetUserCallback userCallback;
		
		public StoreUserDataAsyncTask(User user, GetUserCallback usercallback){
			this.user=user;
			this.userCallback=usercallback;
		}
		
		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			ArrayList<NameValuePair> dataToSend = new ArrayList<NameValuePair>();
			dataToSend.add(new BasicNameValuePair("name",user.name));
			dataToSend.add(new BasicNameValuePair("userName",user.username));
			dataToSend.add(new BasicNameValuePair("userPass",user.password));
			
			HttpParams httpRequestParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpRequestParams, CONNECTION_TIMEOUT);
			HttpConnectionParams.setSoTimeout(httpRequestParams, CONNECTION_TIMEOUT);
			
			HttpClient client = new DefaultHttpClient(httpRequestParams);
			HttpPost post = new HttpPost(SERVER_ADDRESS+"register.php");
			
			try{
				post.setEntity(new UrlEncodedFormEntity(dataToSend));
				client.execute(post);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			pd.dismiss();
			userCallback.done(null);
			super.onPostExecute(result);
		}
		
		
	}

	public class fetchUserDataAsyncTask extends AsyncTask<Void, Void, User>{
		User user;
		GetUserCallback userCallback;

		
		public fetchUserDataAsyncTask(User user, GetUserCallback usercallback){
			this.user=user;
			this.userCallback=usercallback;
		}
		
		protected User doInBackground(Void... params) {
			// TODO Auto-generated method stub
			String login_url = SERVER_ADDRESS+"login.php";
			String userName = user.username;
			String userPass = user.password;
			
			User returnedUser = null;
			
			try {
				URL url = new URL(login_url);
				HttpURLConnection hcr = (HttpURLConnection) url.openConnection();
				hcr.setRequestMethod("POST");
				hcr.setDoOutput(true);
				hcr.setDoInput(true);
				OutputStream os = hcr.getOutputStream();
				BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
				String data = URLEncoder.encode("userName","UTF-8") + "=" + URLEncoder.encode(userName, "UTF-8") + "&" +
						URLEncoder.encode("userPass","UTF-8") + "=" + URLEncoder.encode(userPass, "UTF-8") ;
				bw.write(data);
				bw.flush();
				bw.close();
				os.close();
				
				InputStream is = hcr.getInputStream();
				BufferedReader br = new BufferedReader(new InputStreamReader(is,"iso-8859-1"));
				String response = "";
				String line = "";
				while((line = br.readLine())!=null)
				{
					response += line; 
				}
				br.close();
				is.close();
				hcr.disconnect();
				
				if(response.contains("User does not exist...Login Failed.")){
					returnedUser = null;
					
				} else {
					String name = response;
					
					returnedUser = new User(name,user.username,user.password);
				}
				
				return returnedUser;
				
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return null;
		/***	ArrayList<NameValuePair> dataToSend = new ArrayList<NameValuePair>();
			
			dataToSend.add(new BasicNameValuePair("username",user.username));
			dataToSend.add(new BasicNameValuePair("password",user.password));
			
			HttpParams httpRequestParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpRequestParams, CONNECTION_TIMEOUT);
			HttpConnectionParams.setSoTimeout(httpRequestParams, CONNECTION_TIMEOUT);
			
			HttpClient client = new DefaultHttpClient(httpRequestParams);
			HttpPost post = new HttpPost(SERVER_ADDRESS+"login.php");
			
			User returnedUser = null;
			
			try{
				post.setEntity(new UrlEncodedFormEntity(dataToSend));
				HttpResponse httpResponse =	client.execute(post);
				
				HttpEntity entity = httpResponse.getEntity();
				String result = EntityUtils.toString(entity);
				JSONObject jObject = new JSONObject(result);
				
				if(jObject.length() == 0){
					returnedUser = null;
					
				} else {
					String name = jObject.getString("name");
					
					returnedUser = new User(name,user.username,user.password);
				}
				
			} catch (Exception e) {
				
				e.printStackTrace();
			}
			
			return returnedUser;
			
			***/
		}
		
		
		@Override
		protected void onPostExecute(User returnedUser) {
			// TODO Auto-generated method stub
			super.onPostExecute(returnedUser);
			pd.dismiss();
			userCallback.done(returnedUser);
		}
	}
}

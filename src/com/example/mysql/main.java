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
import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class main extends Activity implements OnClickListener ,IApiAccessResponse {
	
	Button  bRegister, bSubmit ,bConnect;
	EditText etuserName, etuserPass;
	String loginName, loginPass;
	

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		etuserName = (EditText) findViewById(R.id.etUserName);
		etuserPass = (EditText) findViewById(R.id.etPassword);
		bSubmit = (Button) findViewById(R.id.bSubmit);
		bRegister = (Button) findViewById(R.id.bCreateUser);
		bConnect = (Button) findViewById(R.id.bOnlineConnect);
		bSubmit.setOnClickListener(this);
		bRegister.setOnClickListener(this);
		bConnect.setOnClickListener(this);
	}

/****	
	public void userReg(View v) {
		
		startActivity(new Intent(this, Register.class));
	}
	public void userLogin(View v) {
		
		
		
	}
****/

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch (v.getId()) {
		case R.id.bCreateUser:
		startActivity(new Intent(this, Register.class));
		break;
		case R.id.bSubmit:
		loginName = etuserName.getText().toString();
		loginPass = etuserPass.getText().toString();
		String method = "login";
		
		String result="";
			try {
				
				BackGroundTask bgt = new BackGroundTask(this);
				bgt.delegate = this;
				 bgt.execute(method,loginName,loginPass);
				 result = bgt.get();
				if(result.equals("Registration Success...")){
				startActivity(new Intent(this, OnlineAccount.class));
				} else
				{
					
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		//if (result.equals("Registration Success...")){
		//	startActivity(new Intent(this, OnlineContact.class));
		//}
		
		break;
		case R.id.bOnlineConnect:
			//startActivity(new Intent(this, JSONConnect.class));
			startActivity(new Intent(this, OnlineAccount.class));
			break;
	}


	
}
	
	
	public class BackGroundTask  extends AsyncTask<String,Void,String> {

		Context ctx;
		AlertDialog ad;
		public IApiAccessResponse delegate=null;
		
		BackGroundTask(Context ctx){
			
			this.ctx=ctx;
		}
		
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String reg_url = "http://budgetbuddy.dx.am/register.php";
			String login_url = "http://budgetbuddy.dx.am/login.php";
			
			String method = params[0];
			if(method.equals("register")){
				String name = params[1];
				String userName = params[2];
				String userPass = params[3];
				
				
				try {
					
					URL url = new URL(reg_url);
					
					HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
					httpURLConnection.setRequestMethod("POST");
					httpURLConnection.setDoOutput(true);
					
					OutputStream OS = httpURLConnection.getOutputStream();
					BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
					String data = URLEncoder.encode("name","UTF-8") + "=" + URLEncoder.encode(name, "UTF-8") + "&" +
							URLEncoder.encode("userName","UTF-8") + "=" + URLEncoder.encode(userName, "UTF-8") + "&" +
							URLEncoder.encode("userPass","UTF-8") + "=" + URLEncoder.encode(userPass, "UTF-8") ;
					
					bw.write(data);
					bw.flush();
					bw.close();
					OS.close();
					
					InputStream IS = httpURLConnection.getInputStream();
					IS.close();
					return "Registration Success...";
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
			
	else if (method.equals("login")) {
				
		String userName = params[1];
		String userPass = params[2];
		
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
			return response;
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
			}
			return null;
			
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
		//	ad = new AlertDialog.Builder(this).create();
		//	ad.setTitle("Login Information...");
		}

		
		@Override
		protected void onProgressUpdate(Void... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
		}
		
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			if (result.equals("Registration Success...")){
			//Toast.makeText(ctx,result,Toast.LENGTH_LONG).show();
			delegate.postResult(result);
			}
			else {
				ad.setMessage(result);
				ad.show();
				//Toast.makeText(ctx,result,Toast.LENGTH_LONG).show();
			}
			
		
		}

	}


	@Override
	public void postResult(String asyncresult) {
		// TODO Auto-generated method stub
		
	}


}

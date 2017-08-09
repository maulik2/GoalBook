package com.example.mysql;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddContact extends Activity implements OnClickListener{
	
	EditText Name, Email, Mobile;
	String name, email, mobile, userName;
	Button  bSaveInfo;
	
	UserLocalStore userLocalStore;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addcontact);
		Name = (EditText) findViewById(R.id.etName);
		Email = (EditText) findViewById(R.id.etEmail);
		Mobile = (EditText) findViewById(R.id.etMobile);
		bSaveInfo = (Button) findViewById(R.id.bSaveInfo);
		bSaveInfo.setOnClickListener(this);
		
		userLocalStore = new UserLocalStore(this);
		
		User user = userLocalStore.getLoggedInUser();
		userName = user.GetUserName(user);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		name = Name.getText().toString();
		email = Email.getText().toString();
		mobile = Mobile.getText().toString();
		
				
		BackgroundTask bg = new BackgroundTask();
		bg.execute(name,email,mobile,userName);
		
	}
	
	
	class BackgroundTask extends AsyncTask<String,Void,String>{
		
		
		String add_info_url;
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			add_info_url = "http://budgetbuddy.dx.am/add_info.php";
		}
		


		@Override
		protected String doInBackground(String... args) {
			// TODO Auto-generated method stub
			
			String name, email, mobile, userName;
			name=args[0];
			email=args[1];
			mobile=args[2];
			userName = args[3];
			
			try {
				URL url = new URL(add_info_url);
				HttpURLConnection huc = (HttpURLConnection) url.openConnection();
				huc.setRequestMethod("POST");
				huc.setDoOutput(true);
				
				OutputStream os = huc.getOutputStream();
				BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os,"utf-8"));
				
				String data_string = URLEncoder.encode("name","UTF-8")+"="+URLEncoder.encode(name,"UTF-8")+"&"+
						URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(email,"UTF-8")+"&"+
						URLEncoder.encode("mobile","UTF-8")+"="+URLEncoder.encode(mobile,"UTF-8")+"&"+
						URLEncoder.encode("userName","UTF-8")+"="+URLEncoder.encode(userName,"UTF-8");
				
				bw.write(data_string);
				bw.flush();
				bw.close();
				os.close();
				
				InputStream is = huc.getInputStream();
				is.close();
				huc.disconnect();
				
				return "One Row of data inserted...";
				
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			return null;
		}

		
		@Override
		protected void onProgressUpdate(Void... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
		}
		
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			//Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
			AlertDialog.Builder db = new AlertDialog.Builder(AddContact.this);
			db.setMessage(result);
			db.setPositiveButton("ok", null);
			db.show();
			Name.getText().clear();
			Email.getText().clear();
			Mobile.getText().clear();
		}
		
	}

}

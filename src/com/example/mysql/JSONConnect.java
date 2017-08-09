package com.example.mysql;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class JSONConnect extends Activity implements OnClickListener{

	Button  bConnection;
	EditText etHost, etDatabaseName, etUserName, etPass;
	String Host, Databasename, userName, userPass;
	
	JSONParser jp = new JSONParser();
	private static String connection_url = "http://10.0.2.2/JSONConnection/connect.php";
	private static final String Tag_success = "success";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.connection);
		etHost = (EditText) findViewById(R.id.etHost);
		etDatabaseName = (EditText) findViewById(R.id.etDatabase);
		etUserName = (EditText) findViewById(R.id.etName);
		etPass = (EditText) findViewById(R.id.etPassword);
		bConnection = (Button) findViewById(R.id.bConnection);
		bConnection.setOnClickListener(this);
	}




	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Host = etHost.getText().toString();
		Databasename = etDatabaseName.getText().toString();
		userName = etUserName.getText().toString();
		userPass = etPass.getText().toString();
		
		new Connect().execute();
	}

	
	class Connect extends AsyncTask<String, String, String>{

		@Override
		protected String doInBackground(String... args) {
			// TODO Auto-generated method stub
			
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("Host", Host));
			params.add(new BasicNameValuePair("db", Databasename));
			params.add(new BasicNameValuePair("User", userName));
			params.add(new BasicNameValuePair("Password", userPass));
			
			JSONObject json = jp.makeHttpRequest(connection_url, "POST", params);
			
			try{
				int success = json.getInt(Tag_success);
				if (success == 1){
					Toast.makeText(getApplicationContext(), "Connected :) ", Toast.LENGTH_LONG).show();
				} else {
					Toast.makeText(getApplicationContext(), " Not Connected :( ", Toast.LENGTH_LONG).show();
				} 
			}catch (JSONException e){
					e.printStackTrace();
				}
			
			return null;
		}
		
		//return null;
	}
}

package com.example.mysql;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ViewContact extends Activity implements OnClickListener {
	
	
	Button bGetJson,bParseJson;
	String JSON_STRING;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.viewcontact);
		
		bGetJson = (Button) findViewById(R.id.bGetJson);
		bParseJson = (Button) findViewById(R.id.bParseJson);
		
		bGetJson.setOnClickListener(this);
		bParseJson.setOnClickListener(this);
		
		
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch (v.getId()) {
		case R.id.bGetJson:
		
			new serverRequest().execute();
			
		break;
		case R.id.bParseJson:
			
			if(JSON_STRING==null){
				Toast.makeText(getApplicationContext(), "First Get JSON", Toast.LENGTH_LONG).show();
				
			} else {
				Intent intent = new Intent(this, DisplayListView.class);
				intent.putExtra("json_data", JSON_STRING);
				startActivity(intent);
			}
		
		break;
		}
		
	}
	
	
	class serverRequest extends AsyncTask<Void,Void,String>{

		String json_url;
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			json_url="http://budgetbuddy.dx.am/json_get_contact.php";
		}
		
		@Override
		protected String doInBackground(Void... params) {
			// TODO Auto-generated method stub
			

			try {
				URL url = new URL(json_url);
				HttpURLConnection hcr = (HttpURLConnection) url.openConnection();
				InputStream is = hcr.getInputStream();
				BufferedReader br = new BufferedReader(new InputStreamReader(is));
				StringBuilder sb = new StringBuilder();
				
				while((JSON_STRING = br.readLine()) != null)
				{
					sb.append(JSON_STRING + "\n");
				}
				br.close();
				is.close();
				hcr.disconnect();
				return sb.toString().trim();
				
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
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			TextView tvJson = (TextView) findViewById(R.id.tvJson);
			tvJson.setText(result);
			//JSON_STRING = null;
			JSON_STRING = result;
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
		}
		
		
		
	}

	

}

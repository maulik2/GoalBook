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

import com.example.mysql.AddContact.BackgroundTask;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddGoal extends Activity implements OnClickListener {

	EditText etGName, etGDueDate, etGStatus, etGSharedUser;
	String GoalName, DueDate, GoalStatus, SharedUser, userName;
	Button bSaveGoal;

	UserLocalStore userLocalStore;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addgoal);
		etGName = (EditText) findViewById(R.id.etGName);
		etGDueDate = (EditText) findViewById(R.id.etGDueDate);
		etGStatus = (EditText) findViewById(R.id.etGStatus);
		etGSharedUser = (EditText) findViewById(R.id.etGSharedUser);

		bSaveGoal = (Button) findViewById(R.id.bSaveGoal);
		bSaveGoal.setOnClickListener(this);

		userLocalStore = new UserLocalStore(this);

		User user = userLocalStore.getLoggedInUser();
		userName = user.GetUserName(user);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		GoalName = etGName.getText().toString();
		DueDate = etGDueDate.getText().toString();
		GoalStatus = etGStatus.getText().toString();
		SharedUser = etGSharedUser.getText().toString();
		
		BackgroundTask bg = new BackgroundTask();
		bg.execute(GoalName,DueDate,GoalStatus,SharedUser,userName);

	}
	
	
class BackgroundTask extends AsyncTask<String,Void,String>{
		
		
		String add_info_url;
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			add_info_url = "http://budgetbuddy.dx.am/add_goal.php";
		}
		


		@Override
		protected String doInBackground(String... args) {
			// TODO Auto-generated method stub
			
			String GoalName,DueDate,GoalStatus,SharedUser,userName;
			GoalName=args[0];
			DueDate=args[1];
			GoalStatus=args[2];
			SharedUser=args[3];
			userName = args[4];
			
			try {
				URL url = new URL(add_info_url);
				HttpURLConnection huc = (HttpURLConnection) url.openConnection();
				huc.setRequestMethod("POST");
				huc.setDoOutput(true);
				
				OutputStream os = huc.getOutputStream();
				BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os,"utf-8"));
				
				String data_string = URLEncoder.encode("GoalName","UTF-8")+"="+URLEncoder.encode(GoalName,"UTF-8")+"&"+
						URLEncoder.encode("DueDate","UTF-8")+"="+URLEncoder.encode(DueDate,"UTF-8")+"&"+
						URLEncoder.encode("GoalStatus","UTF-8")+"="+URLEncoder.encode(GoalStatus,"UTF-8")+"&"+
						URLEncoder.encode("SharedUser","UTF-8")+"="+URLEncoder.encode(SharedUser,"UTF-8")+"&"+
						URLEncoder.encode("userName","UTF-8")+"="+URLEncoder.encode(userName,"UTF-8");
				
				bw.write(data_string);
				bw.flush();
				bw.close();
				os.close();
				
				InputStream is = huc.getInputStream();
				is.close();
				huc.disconnect();
				
				return "One Row of Goal data inserted...";
				
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
			AlertDialog.Builder db = new AlertDialog.Builder(AddGoal.this);
			db.setMessage(result);
			db.setPositiveButton("ok", null);
			db.show();
			etGName.getText().clear();
			etGStatus.getText().clear();
			etGDueDate.getText().clear();
			etGSharedUser.getText().clear();
		}
		
	}

}

package com.example.mysql;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

public class ViewGoal extends Activity  {

	String JSON_STRING, json_url;
	JSONObject jsonObject;
	JSONArray jsonArray;
	GoalDetailAdapter goalAdapter;
	ListView listView;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.viewgoal);
		setContentView(R.layout.displaygoallist);
		
		
		
		//json_url="http://budgetbuddy.dx.am/json_get_goal.php";
		
		listView = (ListView) findViewById (R.id.Glistview);
		
		goalAdapter = new GoalDetailAdapter(this, R.layout.goalrowlayout);
		
		listView.setAdapter(goalAdapter);
		
		new serverRequest().execute();
		
	
	
	}
		/****
		
		try {
			
			
			
			jsonObject = new JSONObject(JSON_STRING);
			jsonArray=jsonObject.getJSONArray("server_response");
			
			int count=0;
			
			String GoalName, DueDate, GoalStatus;
			
			while (count < jsonArray.length()){
				
				JSONObject jo = jsonArray.getJSONObject(count);
				GoalName=jo.getString("GoalName");
				DueDate=jo.getString("DueDate");
				GoalStatus=jo.getString("GoalStatus");
				
				GoalDetail gd = new GoalDetail(GoalName,DueDate,GoalStatus);
				goalAdapter.add(gd);
				
				count++;
			}
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	****/

	
	class serverRequest extends AsyncTask<Void,Void,String>{

		String json_url;
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			json_url="http://budgetbuddy.dx.am/json_get_goal.php";
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
			//TextView tvJson = (TextView) findViewById(R.id.tvJson);
			//tvJson.setText(result);
			//JSON_STRING = null;
			JSON_STRING = result;
			try{
				
				jsonObject = new JSONObject(JSON_STRING);
				jsonArray=jsonObject.getJSONArray("server_response");
				
				int count=0;
				
				String GoalName, DueDate, GoalStatus;
				
				while (count < jsonArray.length()){
					
					JSONObject jo = jsonArray.getJSONObject(count);
					GoalName=jo.getString("GoalName");
					DueDate=jo.getString("DueDate");
					GoalStatus=jo.getString("GoalStatus");
					
					GoalDetail gd = new GoalDetail(GoalName,DueDate,GoalStatus);
					goalAdapter.add(gd);
					
					count++;
				}
			}  catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
		}
		
		
		
	}
	

}

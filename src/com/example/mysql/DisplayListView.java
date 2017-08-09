package com.example.mysql;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class DisplayListView extends Activity{
	
	String JSON_STRING;
	JSONObject jsonObject;
	JSONArray jsonArray;
	ContactDetailAdaptar contactAdaptar;
	ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.displaylistview);
		JSON_STRING = getIntent().getExtras().getString("json_data");
		listView = (ListView) findViewById (R.id.listview);
		
		contactAdaptar = new ContactDetailAdaptar(this, R.layout.rowlayout);
		
		listView.setAdapter(contactAdaptar);
		
		try {
			jsonObject = new JSONObject(JSON_STRING);
			jsonArray=jsonObject.getJSONArray("server_response");
			
			int count=0;
			
			String name, email, mobile;
			
			while (count < jsonArray.length()){
				
				JSONObject jo = jsonArray.getJSONObject(count);
				name=jo.getString("name");
				email=jo.getString("email");
				mobile=jo.getString("mobile");
				
				ContactDetails cd = new ContactDetails(name,email,mobile);
				contactAdaptar.add(cd);
				
				count++;
			}
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
	}
	
	

}

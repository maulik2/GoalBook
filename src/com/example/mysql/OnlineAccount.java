package com.example.mysql;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class OnlineAccount  extends Activity implements OnClickListener {

	Button  bGoal, bContact, bLogOut;
	TextView tvNetwrokStatus, tvUserName;
	User user;
	UserLocalStore userLocalStore;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.online);
		tvNetwrokStatus = (TextView) findViewById(R.id.tvNetworkStatus);
		bGoal = (Button) findViewById(R.id.bGoal);
		bContact = (Button) findViewById(R.id.bContact);
		bLogOut = (Button) findViewById(R.id.bLogout);
		tvUserName = (TextView) findViewById(R.id.tvUserName);
		bGoal.setOnClickListener(this);
		bContact.setOnClickListener(this);
		bLogOut.setOnClickListener(this);
		
		userLocalStore = new UserLocalStore(this);
		user = userLocalStore.getLoggedInUser();
		
		String name = user.name;
		 
		tvUserName.setText("Helllooo...." + name);
		
		ConnectivityManager cm =(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		
		if(ni !=null &&  ni.isConnected()){
			tvNetwrokStatus.setVisibility(View.INVISIBLE);
		}
		else
		{
			bGoal.setEnabled(false);
			bContact.setEnabled(false);
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch (v.getId()) {
		case R.id.bGoal:
			
		startActivity(new Intent(this, Goal.class));		
		
		break;
		case R.id.bContact:
		startActivity(new Intent(this, Contact.class));	
		//finish();
		break;
		
		case R.id.bLogout:
			startActivity(new Intent(this, MainAct.class));	
			finish();
			break;
		
		}
	}
	
	

}

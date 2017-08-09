package com.example.mysql;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Goal extends Activity implements OnClickListener{

	Button  bViewGoal, bAddGoal;
	User user;
	UserLocalStore userLocalStore;
	
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.goal);
		bViewGoal = (Button) findViewById(R.id.bViewGoal);
		bAddGoal = (Button) findViewById(R.id.bAddGoal);
		
		bViewGoal.setOnClickListener(this);
		bAddGoal.setOnClickListener(this);
		
		userLocalStore = new UserLocalStore(this);
		user = userLocalStore.getLoggedInUser();
		
		String name = user.name;
		
		
	}




	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	
		
		switch (v.getId()) {
		case R.id.bViewGoal:
		
			startActivity(new Intent(this, ViewGoal.class));	
			
		break;
		case R.id.bAddGoal:
		startActivity(new Intent(this, AddGoal.class));	
		//finish();
		break;
		
		}
	}
}

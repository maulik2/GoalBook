package com.example.mysql;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Contact extends Activity implements OnClickListener {
	
	Button  bViewContact, bAddContact;
	User user;
	UserLocalStore userLocalStore;
	
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.contact);
		bViewContact = (Button) findViewById(R.id.bViewContact);
		bAddContact = (Button) findViewById(R.id.bAddContact);
		
		bViewContact.setOnClickListener(this);
		bAddContact.setOnClickListener(this);
		
		userLocalStore = new UserLocalStore(this);
		user = userLocalStore.getLoggedInUser();
		
		String name = user.name;
		
		
	}




	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	
		
		switch (v.getId()) {
		case R.id.bViewContact:
		
			startActivity(new Intent(this, ViewContact.class));	
			
		break;
		case R.id.bAddContact:
		startActivity(new Intent(this, AddContact.class));	
		//finish();
		break;
		
	}
	
	
	}
}

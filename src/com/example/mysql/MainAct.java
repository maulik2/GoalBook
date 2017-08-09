package com.example.mysql;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainAct extends Activity implements OnClickListener{

	Button  bRegister, bSubmit ,bConnect;
	EditText etuserName, etuserPass;
	String loginName, loginPass;
	
	UserLocalStore userLocalStore;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainact);
		etuserName = (EditText) findViewById(R.id.etUserName);
		etuserPass = (EditText) findViewById(R.id.etPassword);
		bSubmit = (Button) findViewById(R.id.bSubmit);
		bRegister = (Button) findViewById(R.id.bCreateUser);
		//bConnect = (Button) findViewById(R.id.bOnlineConnect);
		bSubmit.setOnClickListener(this);
		bRegister.setOnClickListener(this);
		//bConnect.setOnClickListener(this);
		
		userLocalStore = new UserLocalStore(this);
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch (v.getId()) {
		case R.id.bCreateUser:
		startActivity(new Intent(this, Register.class));
		finish();
		break;
		case R.id.bSubmit:
		loginName = etuserName.getText().toString();
		loginPass = etuserPass.getText().toString();
		
		User user = new User(loginName,loginPass);
		
		authenticate(user);
		
		
		/****
		String method = "login";
		BackGroundTask bgt = new BackGroundTask(this);
		//String result="";
		  bgt.execute(method,loginName,loginPass);
		***/
		break;
		/***
		case R.id.bOnlineConnect:
			//startActivity(new Intent(this, JSONConnect.class));
			startActivity(new Intent(this, OnlineContact.class));
			break;
			
			***/
		}
		
	}

	/***
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		
		if(Isauthenticated() == true) {
			displayUserDetails();
		} else {
			startActivity(new Intent (MainAct.this, MainAct.class));
		}
		
	}
	
	***/
	private boolean Isauthenticated(){
		return false;
	}
	
	private void displayUserDetails(){
		
		
	}
	
	private void authenticate(User user){
		
		ServerRequests sr = new ServerRequests(this);
		sr.fetchUserDataInBackgorund(user, new GetUserCallback(){
			@Override
			public void done(User returnedUser) {
				if (returnedUser == null){
					showErrorMessage();
				} else {
					logUserIn(returnedUser);
				}
		}
	});
	}

	
	private void showErrorMessage(){
		AlertDialog.Builder db = new AlertDialog.Builder(MainAct.this);
		db.setMessage("Login Failed....");
		db.setPositiveButton("ok", null);
		db.show();
	}
	
	private void logUserIn(User returnedUser){
		
		userLocalStore.storeUserData(returnedUser);
		userLocalStore.setUserLoggedIn(true);
		startActivity(new Intent(this, OnlineAccount.class));
		finish();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		etuserName.getText().clear();
		etuserPass.getText().clear();
	}
	
	
}

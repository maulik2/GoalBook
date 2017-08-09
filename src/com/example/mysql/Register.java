package com.example.mysql;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Register extends Activity implements OnClickListener {
	Button bRegister;
	EditText etName, etUserName, etPass;
	String name, userName, userPass;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		etName = (EditText) findViewById(R.id.etName);
		etUserName = (EditText) findViewById(R.id.etUserName);
		etPass = (EditText) findViewById(R.id.etPassword);
		bRegister = (Button) findViewById(R.id.bRegister);
		bRegister.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		name = etName.getText().toString();
		userName = etUserName.getText().toString();
		userPass = etPass.getText().toString();
		String method = "register";

		/***
		 * BackGroundTask backgroundtask = new BackGroundTask(this);
		 * backgroundtask.execute(method,name,userName,userPass); finish();
		 ***/
		User user = new User(name, userName, userPass);

		registerUser(user);

	}

	private void registerUser(User user) {

		ServerRequests sr = new ServerRequests(this);
		sr.storeUserDataInBackgorund(user, new GetUserCallback() {
			@Override
			public void done(User returnedUser) {
				showRegisterMessage();
				// startActivity(new Intent(Register.this, MainAct.class));
				// finish();

			}
		});

	}

	private void showRegisterMessage() {
		AlertDialog db = new AlertDialog.Builder(Register.this).create();
		db.setMessage("User Registered Successfully...");
		db.setButton(Dialog.BUTTON_POSITIVE, "ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				Intent intent = new Intent(Register.this, MainAct.class);
				Bundle b = new Bundle();
				b.putBoolean("new_window", true);
				intent.putExtras(b);
				startActivity(intent);
				finish();
			}
		});
		db.show();
	}

}

package com.example.mysql;

//import com.mantri.budgetbuddy.R;

//import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity implements OnClickListener{
	
	Button  bRegister, bSubmit ,bConnect;
	EditText etuserName, etuserPass;
	String loginName, loginPass;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		etuserName = (EditText) findViewById(R.id.etUserName);
		etuserPass = (EditText) findViewById(R.id.etPassword);
		bSubmit = (Button) findViewById(R.id.bSubmit);
		bRegister = (Button) findViewById(R.id.bCreateUser);
		bConnect = (Button) findViewById(R.id.bOnlineConnect);
		bSubmit.setOnClickListener(this);
		bRegister.setOnClickListener(this);
		bConnect.setOnClickListener(this);
	}

/****	
	public void userReg(View v) {
		
		startActivity(new Intent(this, Register.class));
	}
	public void userLogin(View v) {
		
		
		
	}
****/

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch (v.getId()) {
		case R.id.bCreateUser:
		startActivity(new Intent(this, Register.class));
		break;
		case R.id.bSubmit:
		loginName = etuserName.getText().toString();
		loginPass = etuserPass.getText().toString();
		

		/****
		String method = "login";
		BackGroundTask bgt = new BackGroundTask(this);
		//String result="";
		  bgt.execute(method,loginName,loginPass);
		***/
		break;
		case R.id.bOnlineConnect:
			//startActivity(new Intent(this, JSONConnect.class));
			startActivity(new Intent(this, OnlineAccount.class));
			break;
	}


	
}
}

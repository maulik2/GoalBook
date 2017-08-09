package com.example.mysql;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ContactDetailAdaptar extends ArrayAdapter {
	
	List list = new ArrayList();

	public ContactDetailAdaptar(Context context, int resource) {
		super(context, resource);
		// TODO Auto-generated constructor stub
	}

	
	public void add(ContactDetails object) {
		// TODO Auto-generated method stub
		super.add(object);
		list.add(object);
		
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		View row;
		row = convertView;
		ContactDetailHolder contactDetailHolder; 
		if(row==null){
			LayoutInflater lf = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = lf.inflate(R.layout.rowlayout, parent,false);
			contactDetailHolder = new ContactDetailHolder();
			contactDetailHolder.tx_name = (TextView) row.findViewById(R.id.tx_name);
			contactDetailHolder.tx_email = (TextView) row.findViewById(R.id.tx_email);
			contactDetailHolder.tx_mobile = (TextView) row.findViewById(R.id.tx_mobile);
			row.setTag(contactDetailHolder);
		
		
		} else {
			contactDetailHolder = (ContactDetailHolder) row.getTag();
		}
		ContactDetails cd = (ContactDetails) this.getItem(position);
		contactDetailHolder.tx_name.setText(cd.getName());
		contactDetailHolder.tx_email.setText(cd.getEmail());
		contactDetailHolder.tx_mobile.setText(cd.getMobile());
		
		
		return row;
	}
	
	static class ContactDetailHolder
	{
		TextView tx_name, tx_email, tx_mobile;
	}

}

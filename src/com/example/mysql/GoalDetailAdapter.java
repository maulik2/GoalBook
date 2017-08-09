package com.example.mysql;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class GoalDetailAdapter extends ArrayAdapter  {
	
	
	List list = new ArrayList();

	public GoalDetailAdapter(Context context, int resource) {
		super(context, resource);
		// TODO Auto-generated constructor stub
	}

	
	public void add(GoalDetail object) {
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
		GoalDetailHolder goalDetailHolder; 
		if(row==null){
			LayoutInflater lf = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = lf.inflate(R.layout.goalrowlayout, parent,false);
			goalDetailHolder = new GoalDetailHolder();
			goalDetailHolder.tx_Gname = (TextView) row.findViewById(R.id.tx_Gname);
			goalDetailHolder.tx_Gdate = (TextView) row.findViewById(R.id.tx_Gdate);
			goalDetailHolder.tx_Gstatus = (TextView) row.findViewById(R.id.tx_Gstatus);
			row.setTag(goalDetailHolder);
		
		
		} else {
			goalDetailHolder = (GoalDetailHolder) row.getTag();
		}
		GoalDetail cd = (GoalDetail) this.getItem(position);
		goalDetailHolder.tx_Gname.setText(cd.getGoalName());
		goalDetailHolder.tx_Gdate.setText(cd.getDueDate());
		goalDetailHolder.tx_Gstatus.setText(cd.getGoalStatus());
		
		
		return row;
	}
	
	static class GoalDetailHolder
	{
		TextView tx_Gname, tx_Gdate, tx_Gstatus;
	}

}

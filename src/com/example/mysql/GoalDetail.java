package com.example.mysql;

public class GoalDetail {

	String GoalName, DueDate, GoalStatus, SharedUser;
	
	public GoalDetail(String GoalName, String DueDate, String GoalStatus, String SharedUser){
		this.setGoalName(GoalName);
		this.setDueDate(DueDate);
		this.setGoalStatus(GoalStatus);
		this.setSharedUser(SharedUser);
	}

	public GoalDetail(String GoalName, String DueDate, String GoalStatus){
		this.setGoalName(GoalName);
		this.setDueDate(DueDate);
		this.setGoalStatus(GoalStatus);
	}

	
	public String getGoalName() {
		return GoalName;
	}

	public void setGoalName(String goalName) {
		GoalName = goalName;
	}

	public String getDueDate() {
		return DueDate;
	}

	public void setDueDate(String dueDate) {
		DueDate = dueDate;
	}

	public String getGoalStatus() {
		return GoalStatus;
	}

	public void setGoalStatus(String goalStatus) {
		GoalStatus = goalStatus;
	}

	public String getSharedUser() {
		return SharedUser;
	}

	public void setSharedUser(String sharedUser) {
		SharedUser = sharedUser;
	}
	
	
	
}

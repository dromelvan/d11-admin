package org.d11.admin.model;

public class Goal extends MatchEvent {

	private Boolean penalty = false;
	private Boolean own_goal;

	public Boolean getPenalty() {
		return penalty;
	}

	public void setPenalty(Boolean penalty) {
		this.penalty = penalty;
	}

	public Boolean getOwnGoal() {
		return own_goal;
	}

	public void setOwnGoal(Boolean ownGoal) {
		this.own_goal = ownGoal;
	}

}

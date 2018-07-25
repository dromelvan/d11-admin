package org.d11.admin.model;

public class TeamSquadChange extends D11Model {

	public enum ChangeType {
		ADDED,
		REMOVED,
		CHANGED_POSITION;
	}

	private Team team;
	private Player player;
	private ChangeType changeType;

	public TeamSquadChange(Team team, Player player, ChangeType changeType) {
		this.team = team;
		this.player = player;
		this.changeType = changeType;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public ChangeType getChangeType() {
		return changeType;
	}

	public void setChangeType(ChangeType changeType) {
		this.changeType = changeType;
	}

	@Override
	public String toString() {
		if (getChangeType() == ChangeType.CHANGED_POSITION) {
			return String.format("Player %s in team %s has changed position to %s.", getPlayer().getName(), getTeam().getName(), getPlayer().getPosition());
		} else {
			return String.format("Team %s has %s player %s (%s).", getTeam().getName(), getChangeType().toString().toLowerCase(), getPlayer().getName(), getPlayer().getPosition());
		}
	}

}

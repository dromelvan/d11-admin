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
	private boolean newPlayer = true;

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

	public boolean isNewPlayer() {
		return newPlayer;
	}

	public void setNewPlayer(boolean newPlayer) {
		this.newPlayer = newPlayer;
	}

	@Override
	public String toString() {
		String string = String.format("%s %s %s (%s, %s).", getTeam().getName(), getChangeType(), getPlayer().getName(), getPlayer().getPosition(), getPlayer().getNationality());
		if (isNewPlayer()) {
			string += " *NEW*";
		}
		return string;
	}

}

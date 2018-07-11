package org.d11.admin.parser.premierleague;

public class TeamLineupChange {

	public enum ChangeType {
		ADDED,
		REMOVED;
	}

	private TeamParserObject teamParserObject;
	private PlayerParserObject playerParserObject;
	private ChangeType changeType;

	public TeamLineupChange(TeamParserObject teamParserObject, PlayerParserObject playerParserObject, ChangeType changeType) {
		this.teamParserObject = teamParserObject;
		this.playerParserObject = playerParserObject;
		this.changeType = changeType;
	}

	public TeamParserObject getTeamParserObject() {
		return teamParserObject;
	}

	public void setTeamParserObject(TeamParserObject teamParserObject) {
		this.teamParserObject = teamParserObject;
	}

	public PlayerParserObject getPlayerParserObject() {
		return playerParserObject;
	}

	public void setPlayerParserObject(PlayerParserObject playerParserObject) {
		this.playerParserObject = playerParserObject;
	}

	public ChangeType getChangeType() {
		return changeType;
	}

	public void setChangeType(ChangeType changeType) {
		this.changeType = changeType;
	}

	@Override
	public String toString() {
		return String.format("%s has %s player %s.", getTeamParserObject().getName(), getChangeType().toString().toLowerCase(), getPlayerParserObject().getName());
	}
}

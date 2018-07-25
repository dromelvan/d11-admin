package org.d11.admin.model;

import java.util.List;

public class TeamSquad extends D11Model {

	private Team team;
	private List<Player> players;

	public TeamSquad() {

	}

	public TeamSquad(Team team, List<Player> players) {
		super();
		this.team = team;
		this.players = players;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}

	public boolean contains(Player player) {
		return getPlayer(player) != null;
	}

	public Player getPlayer(Player player) {
		for (Player myPlayer : this.players) {
			if (myPlayer.equals(player)) {
				return myPlayer;
			}
		}
		return null;
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder("TeamSquad ");
		stringBuilder.append(getTeam() + "\n");
		for (Player player : players) {
			stringBuilder.append(" " + player + "\n");
		}
		return stringBuilder.toString();
	}

}

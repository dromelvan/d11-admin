package org.d11.admin.model;

public class Substitution extends MatchEvent {

	private Player player_in;

	public Substitution() {
	}

	public Player getPlayerIn() {
		return player_in;
	}

	public void setPlayerIn(Player playerIn) {
		this.player_in = playerIn;
	}

}

package org.d11.admin.model.premierleague;

import org.d11.admin.model.Player;

public class PLPlayer extends Player {

	private Integer premier_league_id;
	private String imageId;

	public PLPlayer() {
	}

	public Integer getPremierLeagueId() {
		return premier_league_id;
	}

	public void setPremierLeagueId(Integer premier_league_id) {
		this.premier_league_id = premier_league_id;
	}

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

}

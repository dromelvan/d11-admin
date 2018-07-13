package org.d11.admin.download.premierleague;

import org.d11.admin.download.D11ImageDownloader;
import org.d11.admin.model.Player;

public class PremierLeaguePlayerImageDownloader extends D11ImageDownloader {

	private int id;
	private String name;
	private String imageId;

	public PremierLeaguePlayerImageDownloader() {
		setUrl("https://premierleague-static-files.s3.amazonaws.com/premierleague/photos/players/250x250/%s.png");
		setDirectoryName("premierleague.com/player-photos");
		setFileName("%s.png");
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    @Override
	protected String formatUrl(String url) {
		return String.format(getUrl(), getImageId());
	}

	@Override
	protected String formatFileName(String fileName) {
	    Player player = new Player(getId(), getName());
		return String.format(fileName, player.getParameterizedName());
	}

}

package org.d11.admin.download.premierleague;

import org.d11.admin.download.D11Downloader;

public class PremierLeagueTeamDownloader extends D11Downloader {

	private int id;
	private String name;

	public PremierLeagueTeamDownloader() {
		setUrl("https://www.premierleague.com/clubs/%d/club/squad");
		setDownloadDirectoryName("premierleague.com/teams");
		setFileName("%s (%d).html");
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

	@Override
	protected String formatUrl(String url) {
		return String.format(getUrl(), getId());
	}

	@Override
	protected String formatFileName(String fileName) {
		return String.format(fileName, getName(), getId());
	}

}

package org.d11.admin.download.whoscored;

import org.d11.admin.download.D11Downloader;

@Deprecated
public class WhoScoredMatchDownloader extends D11Downloader {

	private int id;
	private String season;
	private int matchDay;

	public WhoScoredMatchDownloader() {
		setUrl("https://www.whoscored.com/Matches/%d/Live");
		setDirectoryName("whoscored.com/matches/%s/%02d");
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSeason() {
		return season;
	}

	public void setSeason(String season) {
		this.season = season;
	}

	public int getMatchDay() {
		return matchDay;
	}

	public void setMatchDay(int matchDay) {
		this.matchDay = matchDay;
	}

	@Override
	protected String formatUrl(String url) {
		return String.format(getUrl(), getId());
	}

	@Override
	public String formatDirectoryName(String directoryName) {
		return String.format(directoryName, getSeason(), getMatchDay());
	}

}

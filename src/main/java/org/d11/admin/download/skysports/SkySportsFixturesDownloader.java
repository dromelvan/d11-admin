package org.d11.admin.download.skysports;

import org.d11.admin.download.D11Downloader;

public class SkySportsFixturesDownloader extends D11Downloader {

	private String season;
	
	public SkySportsFixturesDownloader() {
		setUrl("https://www.whoscored.com/Matches/1285060/Live/England-Premier-League-2018-2019-Fulham-Newcastle-United");
		setDirectoryName("skysports.com/fixtures");
		setFileName("premier-league-fixtures-%s.html");		
	}

	public String getSeason() {
		return season;
	}

	public void setSeason(String season) {
		this.season = season;
	}
	
	@Override
	protected String formatFileName(String fileName) {
		return String.format(fileName, getSeason());
	}
	
}

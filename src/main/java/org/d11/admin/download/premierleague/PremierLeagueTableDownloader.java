package org.d11.admin.download.premierleague;

import org.d11.admin.download.D11Downloader;

public class PremierLeagueTableDownloader extends D11Downloader {

	public PremierLeagueTableDownloader() {
		setUrl("https://www.premierleague.com/tables");
		setDirectoryName("premierleague.com");
		setFileName("premier-league-table.html");
	}

}

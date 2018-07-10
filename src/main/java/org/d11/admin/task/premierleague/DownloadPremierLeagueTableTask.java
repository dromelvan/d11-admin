package org.d11.admin.task.premierleague;

import java.io.File;

import org.d11.admin.task.D11DownloadTask;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DownloadPremierLeagueTableTask extends D11DownloadTask<File> {

	private final static Logger logger = LoggerFactory.getLogger(DownloadPremierLeagueTableTask.class);

	public DownloadPremierLeagueTableTask() {
		setUrl("https://www.premierleague.com/tables");
	}

	@Override
	public boolean execute() {
		try {
			logger.info("Downloading Premier League table.");

			Document document = download();
			setOutputFile(new File(getDirectory("premierleague.com"), "premier-league-table.html"));
			write(document);

			setResult(getOutputFile());

			logger.info("Saved Premier League table in file {}.", getOutputFile().getAbsolutePath());
			return true;
		} catch (Exception e) {
			logger.error("Error when downloading Premier League table.");
			logger.error("Error details:", e);
		}
		return false;
	}

}

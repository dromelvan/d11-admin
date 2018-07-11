package org.d11.admin.task.premierleague;

import java.io.File;

import org.d11.admin.task.D11DownloadTask;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DownloadPremierLeagueTeamTask extends D11DownloadTask<File> {

	private String id;
	private String name;
	private final static Logger logger = LoggerFactory.getLogger(DownloadPremierLeagueTeamTask.class);

	public DownloadPremierLeagueTeamTask() {
		setUrl("https://www.premierleague.com/clubs/%s/club/squad");
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean execute() {
		try {
			logger.info("Downloading Premier League team from {}.", String.format(getUrl(), id, name));

			Document document = download(String.format(getUrl(), id, name));
			setOutputFile(new File(getDownloadDirectory("premierleague.com/teams"), String.format("%s (%s).html", getName(), getId())));
			write(document);

			setResult(getOutputFile());

			logger.info("Saved Premier League team in file {}.", getOutputFile().getAbsolutePath());
			return true;
		} catch (Exception e) {
			logger.error("Error when downloading Premier League team.");
			logger.error("Error details:", e);
		}
		return false;
	}

}

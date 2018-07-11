package org.d11.admin.task.whoscored;

import java.io.File;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import org.d11.admin.D11AdminProperties;
import org.d11.admin.reader.jsoup.SeleniumURLReader;
import org.d11.admin.task.D11Task;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.io.Files;

public class DownloadWhoScoredPlayerTask extends D11Task<File> {

	private String playerId;
	private final static String URL_PATTERN = "https://www.whoscored.com/Players/%s";
	private final static Logger logger = LoggerFactory.getLogger(DownloadWhoScoredPlayerTask.class);

	public DownloadWhoScoredPlayerTask() {
	}

	public DownloadWhoScoredPlayerTask(String playerId) {
		this.playerId = playerId;
	}

	public String getPlayerId() {
		return playerId;
	}

	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}

	@Override
	public boolean execute() {
		try {
			logger.info("Downloading info for player {}.", getPlayerId());
			URL url = new URL(String.format(DownloadWhoScoredPlayerTask.URL_PATTERN, getPlayerId()));
			SeleniumURLReader.openWebDriver();
			SeleniumURLReader reader = new SeleniumURLReader(url);
			Document document = reader.read();
			SeleniumURLReader.closeWebDriver();

			File baseFileDirectory = new File(getProperty(D11AdminProperties.BASE_DATA_DIRECTORY));
			File outputDirectory = new File(baseFileDirectory, "players");
			if (!outputDirectory.exists()) {
				outputDirectory.mkdirs();
			}

			File outputFile = new File(outputDirectory, getPlayerId() + ".html");
			Files.write(document.toString(), outputFile, StandardCharsets.UTF_8);
			setResult(outputFile);
			logger.info("Saved player info in file {}.", outputFile.getAbsolutePath());
			return true;
		} catch (Exception e) {
			logger.error("Error when downloading player {}.", getPlayerId());
			logger.error("Error details:", e);
		}
		return false;
	}
}

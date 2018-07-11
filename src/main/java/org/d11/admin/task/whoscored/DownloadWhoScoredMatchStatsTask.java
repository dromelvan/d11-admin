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

public class DownloadWhoScoredMatchStatsTask extends D11Task<File> {

    private String matchId;
    private int matchDayNumber;
    private final static String URL_PATTERN = "https://www.whoscored.com/Matches/%s/Live";
    private final static Logger logger = LoggerFactory.getLogger(DownloadWhoScoredMatchStatsTask.class);

    public DownloadWhoScoredMatchStatsTask() {}

    public DownloadWhoScoredMatchStatsTask(String matchId, int matchDayNumber) {
        this.matchId = matchId;
        this.matchDayNumber = matchDayNumber;
    }

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public int getMatchDayNumber() {
        return matchDayNumber;
    }

    public void setMatchDayNumber(int matchDayNumber) {
        this.matchDayNumber = matchDayNumber;
    }

    @Override
    public boolean execute() {
        try {
            logger.info("Downloading stats for match {}.", getMatchId());
            URL url = new URL(String.format(DownloadWhoScoredMatchStatsTask.URL_PATTERN, getMatchId()));
            SeleniumURLReader.openWebDriver();
            SeleniumURLReader reader = new SeleniumURLReader(url);
            Document document = reader.read();
            SeleniumURLReader.closeWebDriver();

            File baseFileDirectory = new File(getProperty(D11AdminProperties.BASE_DATA_DIRECTORY));
            File outputDirectory = new File(baseFileDirectory, String.valueOf(getMatchDayNumber()));
            if(!outputDirectory.exists()) {
                outputDirectory.mkdirs();
            }

            File outputFile = new File(outputDirectory, getMatchId() + ".html");
            Files.write(document.toString(), outputFile, StandardCharsets.UTF_8);
            setResult(outputFile);
            logger.info("Saved match stats in file {}.", outputFile.getAbsolutePath());
            return true;
        } catch(Exception e) {
            logger.error("Error when downloading match {}.", getMatchId());
            logger.error("Error details:" , e);
        }
        return false;
    }
}

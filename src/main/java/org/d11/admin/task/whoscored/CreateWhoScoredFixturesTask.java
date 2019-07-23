package org.d11.admin.task.whoscored;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.d11.admin.download.whoscored.WhoScoredMatchSeleniumDownloader;
import org.d11.admin.model.Match;
import org.d11.admin.parse.whoscored.WhoScoredMatchParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

public class CreateWhoScoredFixturesTask extends WhoScoredDownloaderTask<String, WhoScoredMatchSeleniumDownloader> implements Comparator<Match> {

	private String seasonName;
	private int startWhoScoredMatchId;
	@Inject
	private WhoScoredMatchParser parser;
	private final static Logger logger = LoggerFactory.getLogger(CreateWhoScoredFixturesTask.class);

    public String getSeasonName() {
		return seasonName;
	}

	public void setSeasonName(String seasonName) {
		this.seasonName = seasonName;
	}

	public int getStartWhoScoredMatchId() {
		return startWhoScoredMatchId;
	}

	public void setStartWhoScoredMatchId(int startWhoScoredMatchId) {
		this.startWhoScoredMatchId = startWhoScoredMatchId;
	}

	@Override
	public boolean execute() {
		
        WhoScoredMatchSeleniumDownloader downloader = getDownloader();
        downloader.setAutoQuit(false);

        int matchDayNumber = 1;
        int matchCount = 0;
        List<Match> matches = new ArrayList<Match>();
        
        for (int whoScoredMatchId = startWhoScoredMatchId; whoScoredMatchId < startWhoScoredMatchId + 380; ++whoScoredMatchId) {
            downloader.setWhoScoredId(whoScoredMatchId);
            downloader.setSeason(this.seasonName);
            downloader.setMatchDay(matchDayNumber);
        	            
            logger.debug("==> Handling match {}/{}: ({}).", matchCount + 1, matchDayNumber, whoScoredMatchId);

            File htmlFile = downloader.download();
            downloader.reset();

            if (htmlFile != null) {
                Match match = parser.parse(htmlFile);
                
                if (match != null) {
                	matches.add(match);
                }
            }
            logger.debug("<== Match done.");
            
            try {
            	Thread.sleep(3000);
            } catch(Exception e) {
            	// WhoScored seems to have some anti flood things that throws you errors if you go too fast.
            }
            
            ++matchCount;
            if(matchCount == 10) {
            	matchDayNumber++;
            	matchCount = 0;
            }
        }

        downloader.quit();
        
		StringBuilder matchesBuilder = new StringBuilder("matches = [  \n");
		String rowPattern = "  [ %d, %d, %d, '%s', %d ],";

        matchDayNumber = 1;
        matchCount = 0;
		
        Collections.sort(matches, this);
        
		for(Match match : matches) {
        	matchesBuilder.append(String.format(rowPattern, matchDayNumber, match.getHomeTeam().getWhoScoredId(), match.getAwayTeam().getWhoScoredId(), match.getDatetime(), match.getWhoScoredId()));
        	matchesBuilder.append("\n");			
        	
            ++matchCount;
            if(matchCount == 10) {
            	matchDayNumber++;
            	matchCount = 0;
            }        	
		}
		
        matchesBuilder.append("]");
        
        setResult(matchesBuilder.toString());
        
    	return true;
    }

	@Override
	public int compare(Match match1, Match match2) {
		return match1.getDatetime().compareTo(match2.getDatetime());
	}
	
}

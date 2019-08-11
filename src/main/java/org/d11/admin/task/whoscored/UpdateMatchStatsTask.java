package org.d11.admin.task.whoscored;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.d11.admin.D11AdminProperties;
import org.d11.admin.download.whoscored.WhoScoredMatchSeleniumDownloader;
import org.d11.admin.model.Match;
import org.d11.admin.model.MissingPlayer;
import org.d11.admin.model.UpdateMatchStatsResult;
import org.d11.admin.model.whoscored.WSMatch;
import org.d11.admin.parse.whoscored.WhoScoredMatchParser;
import org.d11.admin.write.whoscored.WhoScoredMatchWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

public class UpdateMatchStatsTask extends WhoScoredDownloaderTask<List<UpdateMatchStatsResult>, WhoScoredMatchSeleniumDownloader> {

    private List<Match> matches;
    private boolean finish;
    @Inject
    private WhoScoredMatchParser parser;
    @Inject
    private WhoScoredMatchWriter writer;
    private final static Logger logger = LoggerFactory.getLogger(UpdateMatchStatsTask.class);

    public List<Match> getMatches() {
        return matches;
    }

    public void setMatches(List<Match> matches) {
        this.matches = matches;
    }

    public boolean isFinish() {
        return finish;
    }

    public void setFinish(boolean finish) {
        this.finish = finish;
    }

    @Override
    public boolean execute() {
        if(getD11Api().isLoggedIn()) {
        	List<UpdateMatchStatsResult> result = new ArrayList<UpdateMatchStatsResult>();
        	WhoScoredMatchSeleniumDownloader downloader = getDownloader();
        	downloader.setAutoQuit(false);
        	boolean success = true;
        	try {	        	
	        	for(Match match : this.matches) {
		            logger.info("Updating match stats for match {}.", match.getId());
			            
		            downloader.setWhoScoredId(match.getWhoScoredId());
		            downloader.setSeason(match.getSeasonName());
		            downloader.setMatchDay(match.getMatchDayNumber());
		
		            File htmlFile = downloader.download();
		
		            if (htmlFile != null) {
		                WSMatch wsMatch = parser.parse(htmlFile);
		
		                if (wsMatch != null) {
		                    writer.setSeason(match.getSeasonName());
		                    writer.setMatchDayNumber(match.getMatchDayNumber());
		
		                    File jsonFile = writer.write(wsMatch);
		
		                    logger.debug("Uploading match stats to {}.", getProperty(D11AdminProperties.API_HOST));
	
		                    boolean updatePreviousPointsAndGoals = matches.indexOf(match) == 0;	                    
		                    UpdateMatchStatsResult updateMatchStatsResult = getD11Api().updateMatchStats(match, jsonFile, updatePreviousPointsAndGoals, finish);
		
		                    if(updateMatchStatsResult.isValid()) {
		                        for(String dataUpdate : updateMatchStatsResult.getDataUpdates()) {
		                            logger.info("Data update: {}", dataUpdate);
		                        }
		                        logger.info("Match stats for match {} updated.", match.getId());
		                    } else {
		                        logger.error("Could not update stats for match {}.", match.getId());
		                        for(String validationError : updateMatchStatsResult.getValidationErrors()) {
		                            logger.error("Validation error: {}.", validationError);
		                        }
		                        for(String dataError : updateMatchStatsResult.getDataErrors()) {
		                            logger.error("Data error: {}.", dataError);
		                        }
		                        for(MissingPlayer missingPlayer : updateMatchStatsResult.getMissingPlayers()) {
		                            logger.error("Missing player: {}.", missingPlayer.getPlayerName());
		                        }
		                        success = false;
		                    }		
		                    result.add(updateMatchStatsResult);	                    
		                }
		            }
	        	}
	        	setResult(result);
        	} catch(Exception e) {
        		logger.error("Update match stats failed.", e);
        		success = false;
        	}
        	downloader.quit();
        	return success;
        } else {
            logger.error("D11API user not logged in.");
        }
        return false;
    }
}

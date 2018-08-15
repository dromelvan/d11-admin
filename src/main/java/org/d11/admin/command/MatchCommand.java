package org.d11.admin.command;

import org.d11.admin.model.Match;
import org.d11.admin.model.MissingPlayer;
import org.d11.admin.model.UpdateMatchStatsResult;
import org.d11.admin.task.whoscored.UpdateMatchStatsTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.google.inject.Inject;
import com.google.inject.Provider;

@Parameters(commandDescription = "Update stats for a match.")
public class MatchCommand extends D11Command {

    @Parameter(names = { "-matchId" }, description = "D11 match id for the match we want to update.", required = true)
    private Integer matchId;
	private Provider<UpdateMatchStatsTask> provider;
	private final static Logger logger = LoggerFactory.getLogger(MatchCommand.class);

	@Inject
	public MatchCommand(Provider<UpdateMatchStatsTask> provider) {
		setName("match");
		this.provider = provider;
	}

	@Override
	public void execute() {
	    logger.info("Updating match stats for match {}.", this.matchId);

	    UpdateMatchStatsTask task = this.provider.get();
        getD11Api().login(getUser(), getPassword());
        Match match = getD11Api().getMatch(this.matchId);
        task.setMatch(match);
        if(task.execute()) {
            UpdateMatchStatsResult updateMatchStatsResult = task.getResult();
            if(!updateMatchStatsResult.isValid()) {
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
            } else {
                logger.info("Match stats for match {} updated.", this.matchId);
                for(String dataUpdate : updateMatchStatsResult.getDataUpdates()) {
                    logger.info("Data update: {}.", dataUpdate);
                }
            }
        }
	}

}

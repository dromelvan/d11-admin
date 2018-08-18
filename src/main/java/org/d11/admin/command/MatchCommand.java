package org.d11.admin.command;

import org.d11.admin.model.Match;
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
    @Parameter(names = { "-previous" }, description = "Update previous points and goals.")
    private boolean updatePreviousPointsAndGoals = false;
	private Provider<UpdateMatchStatsTask> provider;
	private final static Logger logger = LoggerFactory.getLogger(MatchCommand.class);

	@Inject
	public MatchCommand(Provider<UpdateMatchStatsTask> provider) {
		setName("match");
		this.provider = provider;
	}

	@Override
	public void execute() {
	    if(getD11Api().login(getUser(), getPassword())) {
    	    logger.info("Updating match stats for match {}.", this.matchId);

    	    UpdateMatchStatsTask task = this.provider.get();

            Match match = getD11Api().getMatch(this.matchId);
            task.setMatch(match);
            task.setUpdatePreviousPointsAndGoals(this.updatePreviousPointsAndGoals);

            if(task.execute()) {
                UpdateMatchStatsResult updateMatchStatsResult = task.getResult();
                if(updateMatchStatsResult.isValid()) {
                    for(String dataUpdate : updateMatchStatsResult.getDataUpdates()) {
                        logger.info("Data update: {}.", dataUpdate);
                    }
                    logger.info("Match stats for match {} updated.", match.getId());
                }
            }
        }
	}

}

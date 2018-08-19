package org.d11.admin.command;

import java.util.List;

import org.d11.admin.model.Match;
import org.d11.admin.model.UpdateMatchStatsResult;
import org.d11.admin.task.whoscored.UpdateMatchStatsTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.beust.jcommander.converters.IntegerConverter;
import com.google.inject.Inject;
import com.google.inject.Provider;

@Parameters(commandDescription = "Update stats for a match.")
public class UpdateCommand extends D11Command {

    @Parameter(names = { "-matchIds" }, description = "D11 match ids for the matches we want to update.", required = true, converter = IntegerConverter.class, splitter = SpaceSplitter.class)
    private List<Integer> matchIds;
	private Provider<UpdateMatchStatsTask> taskProvider;
	private final static Logger logger = LoggerFactory.getLogger(UpdateCommand.class);

	@Inject
	public UpdateCommand(Provider<UpdateMatchStatsTask> taskProvider) {
		setName("update");
		this.taskProvider = taskProvider;
	}

	@Override
	public void execute() {
	    if(getD11Api().login(getUser(), getPassword())) {
    	    for(Integer matchId : this.matchIds) {
    	        logger.info("Updating match stats for match {}.", matchId);

    	        boolean updatePreviousPointsAndGoals = this.matchIds.indexOf(matchId) == 0;

                UpdateMatchStatsTask task = this.taskProvider.get();

                Match match = getD11Api().getMatch(matchId);
                task.setMatch(match);
                task.setUpdatePreviousPointsAndGoals(updatePreviousPointsAndGoals);

                if(task.execute()) {
                    UpdateMatchStatsResult updateMatchStatsResult = task.getResult();
                    if(updateMatchStatsResult.isValid()) {
                        for(String dataUpdate : updateMatchStatsResult.getDataUpdates()) {
                            logger.info("Data update: {}.", dataUpdate);
                        }
                        logger.info("Match stats for match {} updated.", match.getId());
                    }
                }
                if(this.matchIds.indexOf(matchId) == this.matchIds.size() - 1) {
                    task.close();
                }
    	    }
	    }
	}

}

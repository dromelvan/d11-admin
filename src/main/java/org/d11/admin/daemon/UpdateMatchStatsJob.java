package org.d11.admin.daemon;

import java.util.List;

import org.d11.admin.model.Match;
import org.d11.admin.task.whoscored.UpdateMatchStatsTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UpdateMatchStatsJob extends D11DaemonJob<UpdateMatchStatsTask> {

    private final static Logger logger = LoggerFactory.getLogger(UpdateMatchStatsJob.class);

    @Override
    public void doExecute(UpdateMatchStatsTask task) {
        logger.info("Updating match stats.");

        List<Match> matches = getD11Api().getActiveMatches();
        if(!matches.isEmpty()) {
            for(Match match : matches) {
                boolean updatePreviousPointsAndGoals = matches.indexOf(match) == 0;

                task.setMatch(match);
                task.setUpdatePreviousPointsAndGoals(updatePreviousPointsAndGoals);

                task.execute();
            }
            matches = getD11Api().getActiveMatches();
        }

        if(!matches.isEmpty()) {
            reschedule("update-active-match-stats");
        } else {
            Match match = getD11Api().getUpcomingMatch();
            if(match != null) {
                reschedule("update-upcoming-match-stats", match.getLocalDateTime());
            }
        }

        logger.info("Updating match stats finished.");
    }

}

package org.d11.admin.daemon;

import org.d11.admin.model.Match;
import org.d11.admin.task.whoscored.UpdateMatchDateTimesTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UpdateMatchDateTimesJob extends D11DaemonJob<UpdateMatchDateTimesTask> {

    private final static Logger logger = LoggerFactory.getLogger(UpdateMatchDateTimesJob.class);

    @Override
    protected void doExecute(UpdateMatchDateTimesTask task) {
        logger.info("Updating match dates and kickoff times.");

        if(!task.execute()) {
            logger.error("Could not change datetimes for all matches.");
        }
        for(Match match : task.getResult()) {
            logger.info("Changed datetime for match {} to {}.", match.getId(), match.getDatetime());
        }

        logger.info("Match dates and kickoff times update finished.");
    }

}

package org.d11.admin.daemon;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.d11.admin.model.Match;
import org.d11.admin.model.MatchDay;
import org.d11.admin.task.FinishMatchDayTask;
import org.d11.admin.task.whoscored.UpdateMatchStatsTask;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

public class FinalizeMatchStatsJob extends D11DaemonJob<UpdateMatchStatsTask> {

    @Inject
    private FinishMatchDayTask finishTask;
    private final static Logger logger = LoggerFactory.getLogger(FinalizeMatchStatsJob.class);

    @Override
    public void doExecute(UpdateMatchStatsTask updateTask) {
        logger.info("Finalizing match stats.");

        LocalDate today = getNow().toLocalDate();
        LocalDate yesterday = today.minusDays(1);
        List<Match> matches = getD11Api().getMatchesByDate(yesterday);
        Map<Integer, MatchDay> matchDays = new HashMap<Integer, MatchDay>();

        logger.info("Found {} matches for date {}.", matches.size(), yesterday);
        for(Match match : matches) {
            boolean updatePreviousPointsAndGoals = matches.indexOf(match) == 0;

            updateTask.setMatch(match);
            updateTask.setUpdatePreviousPointsAndGoals(updatePreviousPointsAndGoals);

//            if(task.execute()) {
                MatchDay matchDay = getD11Api().getMatchDayBySeasonAndMatchDayNumber(match.getSeasonName(), match.getMatchDayNumber());
                matchDays.put(matchDay.getId(), matchDay);
//            }
        }

        for(MatchDay matchDay : matchDays.values()) {
            boolean finished = true;
            for(int matchId : matchDay.getMatchIds()) {
                Match match = getD11Api().getMatch(matchId);
                LocalDate matchDate = match.getLocalDateTime().toLocalDate();
                if(!today.isAfter(matchDate)) {
                    finished = false;
                    break;
                }
            }
            if(finished) {
                this.finishTask.setMatchDay(matchDay);
                this.finishTask.execute();
            }
        }

        logger.info("Finalizing match stats finished.");
    }
}

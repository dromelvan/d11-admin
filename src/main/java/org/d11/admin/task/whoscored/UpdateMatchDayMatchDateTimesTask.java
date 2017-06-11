package org.d11.admin.task.whoscored;

import org.d11.admin.task.D11Task;
import org.d11.api.MatchDay;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class UpdateMatchDayMatchDateTimesTask extends D11Task<org.d11.api.Match> {

    private MatchDay matchDay;
    @Inject
    private Provider<UpdateMatchDateTimeTask> updateMatchDateTimeTaskProvider;
    private final static Logger logger = LoggerFactory.getLogger(UpdateMatchDayMatchDateTimesTask.class);

    public UpdateMatchDayMatchDateTimesTask() {}

    public UpdateMatchDayMatchDateTimesTask(MatchDay matchDay) {
        this.matchDay = matchDay;
    }

    protected MatchDay getMatchDay() {
        return matchDay;
    }

    protected void setMatchDay(MatchDay matchDay) {
        this.matchDay = matchDay;
    }

    @Override
    public boolean execute() {
        logger.info("Updating match date times for match day {} ({})", getMatchDay().getMatchDayNumber(), getMatchDay().getId());
        int failures = 0;

        for(int matchId : getMatchDay().getMatchIds()) {
            UpdateMatchDateTimeTask updateMatchDateTimeTask = updateMatchDateTimeTaskProvider.get();
            updateMatchDateTimeTask.setMatchId(String.valueOf(matchId));
            updateMatchDateTimeTask.setMatchDayNumber(getMatchDay().getMatchDayNumber());
            if(!updateMatchDateTimeTask.execute()) {
                ++failures;
            }
        }
        logger.info("Finished updating match date times with {} failures.", failures);
        return failures == 0;
    }
}

package org.d11.admin.task;

import org.d11.admin.model.MatchDay;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FinishMatchDayTask extends D11Task<MatchDay> {

    private MatchDay matchDay;
    private final static Logger logger = LoggerFactory.getLogger(FinishMatchDayTask.class);

    public MatchDay getMatchDay() {
        return matchDay;
    }

    public void setMatchDay(MatchDay matchDay) {
        this.matchDay = matchDay;
    }

    @Override
    public boolean execute() {
        logger.info("Finishing match day {} ({}).", this.matchDay.getMatchDayNumber(), this.matchDay.getId());
        MatchDay matchDay = getD11Api().finishMatchDay(this.matchDay.getId());
        if (matchDay != null) {
            logger.info("Finished match day {} ({}).", matchDay.getMatchDayNumber(), matchDay.getId());
            setResult(matchDay);
            return true;
        }
        logger.info("Could not finished match day {} ({}).", this.matchDay.getMatchDayNumber(), this.matchDay.getId());
        return false;
    }

}

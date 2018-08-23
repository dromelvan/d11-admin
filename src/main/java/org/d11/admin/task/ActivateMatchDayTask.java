package org.d11.admin.task;

import org.d11.admin.model.MatchDay;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ActivateMatchDayTask extends D11Task<MatchDay> {

    private MatchDay matchDay;
    private final static Logger logger = LoggerFactory.getLogger(ActivateMatchDayTask.class);

    public MatchDay getMatchDay() {
        return matchDay;
    }

    public void setMatchDay(MatchDay matchDay) {
        this.matchDay = matchDay;
    }

    @Override
    public boolean execute() {
        logger.info("Activating match day {} ({}).", this.matchDay.getMatchDayNumber(), this.matchDay.getId());
        MatchDay matchDay = getD11Api().activateMatchDay(this.matchDay.getId());
        if (matchDay != null) {
            logger.info("Activated match day {} ({}).", matchDay.getMatchDayNumber(), matchDay.getId());
            setResult(matchDay);
            return true;
        }
        return false;
    }

}

package org.d11.admin.task;

import java.util.List;

import org.d11.api.D11API;
import org.d11.api.Match;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

public class GetMatchesByDateTask extends D11Task<List<Match>> {

    private LocalDate localDate;
    @Inject
    private D11API d11Api;
    private final static Logger logger = LoggerFactory.getLogger(GetMatchesByDateTask.class);

    public GetMatchesByDateTask() {}

    public GetMatchesByDateTask(LocalDate localDate) {
        this.localDate = localDate;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }

    @Override
    public boolean execute() {
        logger.info("Getting matches for date {}.", localDate);
        List<Match> matches = this.d11Api.getMatchesByDate(this.localDate);
        logger.info("Found {} matches." , matches.size());
        setResult(matches);
        return true;
    }
}

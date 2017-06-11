package org.d11.admin.task.whoscored;

import java.io.File;

import org.d11.admin.jaxb.match.Match;
import org.d11.admin.reader.jaxb.WhoScoredMatchStatisticsJAXBFileReader;
import org.d11.admin.task.D11Task;
import org.d11.api.D11API;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

public class UpdateMatchDateTimeTask extends D11Task<org.d11.api.Match> {

    private String matchId;
    private int matchDayNumber;
    @Inject
    private DownloadWhoScoredMatchStatsTask downloadWhoScoredMatchStatsTask;
    @Inject
    private ParseWhoScoredMatchStatsFileTask parseWhoScoredMatchStatsFileTask;
    @Inject
    private D11API d11Api;
    private final static Logger logger = LoggerFactory.getLogger(UpdateMatchDateTimeTask.class);

    public UpdateMatchDateTimeTask() {}

    public UpdateMatchDateTimeTask(String matchId, int matchDayNumber) {
        this.matchId = matchId;
        this.matchDayNumber = matchDayNumber;
    }

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public int getMatchDayNumber() {
        return matchDayNumber;
    }

    public void setMatchDayNumber(int matchDayNumber) {
        this.matchDayNumber = matchDayNumber;
    }

    @Override
    public boolean execute() {
        int whoScoredId = this.d11Api.getMatch(Integer.valueOf(getMatchId())).getWhoScoredId();
        this.downloadWhoScoredMatchStatsTask.setMatchId(String.valueOf(whoScoredId));
        this.downloadWhoScoredMatchStatsTask.setMatchDayNumber(getMatchDayNumber());
        if(this.downloadWhoScoredMatchStatsTask.execute()) {
            File htmlFile = this.downloadWhoScoredMatchStatsTask.getResult();

            this.parseWhoScoredMatchStatsFileTask.setSourceFile(htmlFile);
            if(this.parseWhoScoredMatchStatsFileTask.execute()) {
                File xmlFile = this.parseWhoScoredMatchStatsFileTask.getResult();

                WhoScoredMatchStatisticsJAXBFileReader whoScoredMatchStatisticsJAXBFileReader = new WhoScoredMatchStatisticsJAXBFileReader();
                for(Match match : whoScoredMatchStatisticsJAXBFileReader.read(xmlFile)) {
                    org.d11.api.Match apiMatch = this.d11Api.updateMatchDateTime(Integer.valueOf(getMatchId()), match.getDateTime());
                    if(apiMatch == null) {
                        logger.error("Could not update match datetime for match {}.", getMatchId());
                        return false;
                    } else {
                        logger.info("Changed match datetime for match {} to {}.", getMatchId(), apiMatch.getDatetime());
                        setResult(apiMatch);
                        return true;
                    }
                }
                return true;
            }
        }
        return false;
    }
}

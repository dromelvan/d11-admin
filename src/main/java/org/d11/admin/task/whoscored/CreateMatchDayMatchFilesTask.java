package org.d11.admin.task.whoscored;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.d11.admin.download.whoscored.WhoScoredMatchSeleniumDownloader;
import org.d11.admin.model.Match;
import org.d11.admin.model.MatchDay;
import org.d11.admin.model.Season;
import org.d11.admin.parse.whoscored.WhoScoredMatchParser;
import org.d11.admin.task.D11Task;
import org.d11.admin.write.whoscored.WhoScoredMatchWriter;

import com.google.inject.Inject;

public class CreateMatchDayMatchFilesTask extends D11Task<List<File>> {

    private String seasonName;
    private Integer matchDayNumber;
    @Inject
    private WhoScoredMatchSeleniumDownloader downloader;
    @Inject
    private WhoScoredMatchParser parser;
    @Inject
    private WhoScoredMatchWriter writer;

    public String getSeasonName() {
        return seasonName;
    }

    public void setSeasonName(String seasonName) {
        this.seasonName = seasonName;
    }

    public Integer getMatchDayNumber() {
        return matchDayNumber;
    }

    public void setMatchDayNumber(int matchDayNumber) {
        this.matchDayNumber = matchDayNumber;
    }

    @Override
    public boolean execute() {
        Season season = (this.seasonName != null ? getD11Api().getSeason(this.seasonName) : getD11Api().getCurrentSeason());
        MatchDay matchDay = null;
        if(this.matchDayNumber == null) {
            matchDay = getD11Api().getCurrentMatchDay();
        } else {
            matchDay = getD11Api().getMatchDayBySeasonAndMatchDayNumber(season.getName(), this.matchDayNumber);
        }

        List<File> jsonFiles = new ArrayList<File>();

        for(int matchId : matchDay.getMatchIds()) {
            Match match = getD11Api().getMatch(matchId);
            downloader.setId(match.getWhoScoredId());
            downloader.setSeason(season.getName());
            downloader.setMatchDay(matchDay.getMatchDayNumber());

            File htmlFile = downloader.download();
            downloader.reset();
            match = parser.parse(htmlFile);

            writer.setSeason(season.getName());
            writer.setMatchDayNumber(matchDay.getMatchDayNumber());

            File jsonFile = writer.write(match);
            jsonFiles.add(jsonFile);
        }
        setResult(jsonFiles);
        return true;
    }

}

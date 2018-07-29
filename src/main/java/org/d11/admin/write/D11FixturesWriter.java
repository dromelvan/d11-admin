package org.d11.admin.write;

import java.io.File;
import java.io.FileWriter;
import java.util.List;

import org.d11.admin.model.D11Match;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class D11FixturesWriter extends D11Writer<List<D11Match>> {

    private int seasonId;
    private final static Logger logger = LoggerFactory.getLogger(D11FixturesWriter.class);

    public D11FixturesWriter() {
        setDirectoryName("d11/fixtures");
        setFileName("season_%d_d11_fixtures.rb");
    }

    public int getSeasonId() {
        return seasonId;
    }

    public void setSeasonId(int seasonId) {
        this.seasonId = seasonId;
    }

    @Override
    public String formatFileName(String fileName) {
        return String.format(fileName, getSeasonId());
    }

    @Override
    public File write(List<D11Match> d11Matches) {
        setFile(new File(getDirectory(), formatFileName(getFileName())));
        logger.info("Writing file {}", getFile());
        try {
            StringBuilder stringBuilder = new StringBuilder("d11_matches = [\n");
            for(D11Match d11Match : d11Matches) {
                stringBuilder.append(String.format("  [ %d, %d, %d ],\n", d11Match.getD11MatchDayId(), d11Match.getHomeD11Team().getId(), d11Match.getAwayD11Team().getId()));
            }
            stringBuilder.append("]\n");

            stringBuilder.append("d11_matches.each do | d11_match_day_id, home_d11_team_id, away_d11_team_id |\n");
            stringBuilder.append("    status = :pending\n");
            stringBuilder.append("    d11_match = D11Match.create(d11_match_day_id: d11_match_day_id, home_d11_team_id: home_d11_team_id, away_d11_team_id: away_d11_team_id, home_team_points: 0, away_team_points: 0, status: status)\n");
            stringBuilder.append("    D11TeamMatchSquadStat.create(d11_team_id: home_d11_team_id, d11_match_id: d11_match.id)\n");
            stringBuilder.append("    D11TeamMatchSquadStat.create(d11_team_id: away_d11_team_id, d11_match_id: d11_match.id)\n");
            stringBuilder.append("end");

            FileWriter fileWriter = new FileWriter(getFile());
            fileWriter.write(stringBuilder.toString());
            fileWriter.close();
        } catch (Exception e) {
            logger.error("Could not write D11 matches to file {}.", getFile());
            logger.error("Error details:", e);
        }
        return null;
    }

}

package org.d11.admin.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.d11.admin.model.D11Match;
import org.d11.admin.model.D11Team;
import org.d11.admin.model.Season;

public class D11FixtureGenerator {

    private Season season;

    public D11FixtureGenerator(Season season) {
        this.season = season;
    }

    public Season getSeason() {
        return season;
    }

    public void setSeason(Season season) {
        this.season = season;
    }

    public List<D11Match> generate() {
        List<D11Match> d11Matches = new ArrayList<D11Match>();

        List<Integer> d11TeamIds = new ArrayList<Integer>();
        for(int id : getSeason().getD11TeamIds()) {
            d11TeamIds.add(id);
        }

        if(d11TeamIds.size() % 2 == 1) {
            // If we have an odd number of d11 teams add a dummy id. Matches with
            // the dummy team will be removed later.
            d11TeamIds.add(1);
        }

        Collections.shuffle(d11TeamIds);

        int matchDayCount = d11TeamIds.size() - 1;
        int matchDayMatchCount = d11TeamIds.size() / 2;
        String[][] matchDays = new String[matchDayCount][matchDayMatchCount];

        for(int matchDay = 0; matchDay < matchDayCount; matchDay++) {
            for(int match = 0; match < matchDayMatchCount; match++) {
                int home = (matchDay + match) % (d11TeamIds.size() - 1);
                int away = (d11TeamIds.size() - 1 - match + matchDay) % (d11TeamIds.size() - 1);
                // The last team will stay in place while the others rotate around it.
                if(match == 0) {
                    away = d11TeamIds.size() - 1;
                }
                matchDays[matchDay][match] = d11TeamIds.get(home) + ":" + d11TeamIds.get(away);
            }
        }

        // Mix it up so that home and away games are somewhat evenly distributed.
        String[][] shuffled = new String[matchDayCount][matchDayMatchCount];

        int even = 0;
        int odd = (d11TeamIds.size() / 2);
        for (int i = 0; i < matchDays.length; i++) {
            if (i % 2 == 0) {
                shuffled[i] = matchDays[even++];
            } else {
                shuffled[i] = matchDays[odd++];
            }
        }

        matchDays = shuffled;

        // The last team can't play away all the time so switch them to the home team for odd numbered match days.
        for (int matchDay = 0; matchDay < matchDays.length; matchDay++) {
            if (matchDay % 2 == 1) {
                matchDays[matchDay][0] = flip(matchDays[matchDay][0]);
            }
        }

        String[][] allMatchDays = new String[matchDayCount * 2][matchDayMatchCount];
        for(int i = 0; i < matchDays.length; ++i) {
            for(int j = 0; j < matchDays[i].length; ++j) {
                allMatchDays[i][j] = matchDays[i][j];
            }
        }
        for(int i = 0; i < matchDays.length; ++i) {
            for(int j = 0; j < matchDays[i].length; ++j) {
                allMatchDays[i + matchDays.length][j] = flip(matchDays[i][j]);
            }
        }

        int matchDayId = season.getD11MatchDayIds()[0];

        for (int i = 0; i < allMatchDays.length; i++) {
            for(String match : allMatchDays[i]) {
                String[] teamIds = match.split(":");
                if(Integer.parseInt(teamIds[0]) > 1 && Integer.parseInt(teamIds[1]) > 1) {
                    D11Team homeD11Team = new D11Team();
                    homeD11Team.setId(Integer.parseInt(teamIds[0]));
                    D11Team awayD11Team = new D11Team();
                    awayD11Team.setId(Integer.parseInt(teamIds[1]));

                    D11Match d11Match = new D11Match();
                    d11Match.setHomeD11Team(homeD11Team);
                    d11Match.setAwayD11Team(awayD11Team);
                    d11Match.setD11MatchDayId(matchDayId);

                    d11Matches.add(d11Match);
                }
            }
            ++matchDayId;
        }

        return d11Matches;
    }

    private String flip(String match) {
        String[] teamIds = match.split(":");
        return teamIds[1] + ":" + teamIds[0];
    }

}

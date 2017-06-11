package org.d11.admin.task.whoscored;

import java.io.File;

import org.d11.api.D11API;
import org.d11.api.MatchDay;
import org.jukito.JukitoModule;
import org.jukito.JukitoRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JukitoRunner.class)
public class WhoScoredTaskTest {

    public static class WhoScoredTaskTestModule extends JukitoModule {
        @Override
        protected void configureTest() {
        }
    }

    //@Before
    public void before(D11API d11Api) {
        d11Api.login("dromelvan@fake.email.com", "password");
    }

    //@Test
    public void downloadWhoScoredMatchStats(DownloadWhoScoredMatchStatsTask task) {
        task.setMatchId("1080728");
        task.setMatchDayNumber(33);
        if(task.execute()) {
            File htmlFile = task.getResult();
            System.out.println(htmlFile);
        }
    }

    //@Test
    public void parseWhoScoredMatchStatsFile(ParseWhoScoredMatchStatsFileTask task) {
        File file = new File("files/21/1080699.html");
        task.setSourceFile(file);
        task.execute();
    }

    //@Test
    public void updateMatchDate(UpdateMatchDateTimeTask task) {
        task.setMatchId("4752");
        task.setMatchDayNumber(20);
        if(task.execute()) {
            System.out.println(task.getResult());
        }
    }

    //@Test
    public void updateMatchDayMatchDates(D11API d11API, UpdateMatchDayMatchDateTimesTask task) {
        MatchDay matchDay = d11API.getUpcomingMatchDay();
        task.setMatchDay(matchDay);
        task.execute();
    }

    @Test
    public void parseWhoScoredPlayerFile(ParseWhoScoredPlayerFileTask task) {
        File file = new File("files/players/86425.html");
        task.setSourceFile(file);
        task.execute();
        System.out.println(task.getResult());
    }

    //@Test
    public void downloadWhoScoredPlayer(DownloadWhoScoredPlayerTask task) {
        task.setPlayerId("86425");
        if(task.execute()) {
            File htmlFile = task.getResult();
            System.out.println(htmlFile);
        }
    }

}

package org.d11.admin;

import java.util.List;

import org.d11.admin.model.Match;
import org.d11.admin.model.MatchDay;
import org.d11.admin.model.Season;
import org.d11.admin.model.TeamSquad;
import org.d11.api.D11API;
import org.joda.time.LocalDate;
import org.jukito.JukitoModule;
import org.jukito.JukitoRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JukitoRunner.class)
public class D11APITest {

	public static class D11APITestModule extends JukitoModule {
		@Override
		protected void configureTest() {
		}
	}

	@Before
	public void login(D11API d11Api) {
	    d11Api.login("dromelvan@fake.email.com", "password");
	}

	//@Test
	public void getSeasons(D11API d11Api) {
	    List<Season> seasons = d11Api.getSeasons();
	    for(Season season : seasons) {
	        System.out.println(season);
	    }
	}

	//@Test
	public void getCurrentSeason(D11API d11Api) {
	    Season season = d11Api.getCurrentSeason();
	    System.out.println(season);
	}

    //@Test
    public void getMatchDaysBySeason(D11API d11Api) {
        for(MatchDay matchDay : d11Api.getMatchDaysBySeason("2016-2017")) {
            System.out.println(matchDay);
        }
    }

	//@Test
	public void getMatchDayBySeasonAndNumber(D11API d11Api) {
	    System.out.println(d11Api.getMatchDayBySeasonAndMatchDayNumber("2016-2017", 38));
	}

 	//@Test
	public void getMatchesByDate(D11API d11Api) {
	    List<Match> matches = d11Api.getMatchesByDate(LocalDate.parse("2017-05-13"));
		for (Match match : matches) {
			System.out.println(match);
		}
	}

	@Test
	public void getTeamSquad(D11API d11Api) {
	    TeamSquad teamSquad = d11Api.getTeamSquad(2);
	    if(teamSquad != null) {
	        System.out.println(teamSquad);
	    }
	}
}

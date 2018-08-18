package org.d11.admin;

import java.io.File;
import java.util.List;

import org.d11.admin.model.Match;
import org.d11.admin.model.MatchDay;
import org.d11.admin.model.Player;
import org.d11.admin.model.Season;
import org.d11.admin.model.Team;
import org.d11.admin.model.TeamSquad;
import org.d11.admin.model.UpdateMatchStatsResult;
import org.d11.api.v1.D11API;
import org.d11.api.v1.UpdateMatchStatsTypeToken;
import org.joda.time.LocalDate;
import org.jukito.JukitoModule;
import org.jukito.JukitoRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.gson.Gson;

@RunWith(JukitoRunner.class)
public class D11APITest {

	public static class D11APITestModule extends JukitoModule {
		@Override
		protected void configureTest() {

		}
	}

    //@Test
    public void json() {
        Gson gson = new Gson();
        String json = "{\"validation_errors\":[\"Validation error.\"]," +
                       "\"data_errors\":[\"Invalid match. The selected match has whoscored_id 1080519 but the uploaded file contains data for match 1080519323.\"]," +
                       "\"missing_players\":[{\"player_whoscored_id\":90021212,\"player_name\":\"Foobar Shaqiri\",\"team_id\":34,\"team_name\":\"Hull\",\"position_id\":1,\"alternative_players\":[{\"id\":2998,\"country_id\":219,\"whoscored_id\":76304,\"first_name\":\"Xherdan\",\"last_name\":\"Shaqiri\",\"full_name\":null,\"date_of_birth\":\"1991-10-10\",\"height\":169,\"weight\":72,\"created_at\":\"2016-07-23T07:01:18.204Z\",\"updated_at\":\"2016-08-05T19:58:47.231Z\",\"player_photo_file_name\":\"xherdan-shaqiri.jpg\",\"player_photo_content_type\":\"image/png\",\"player_photo_file_size\":27903,\"player_photo_updated_at\":\"2016-08-04T18:42:09.013Z\",\"parameterized_name\":\"xherdan-shaqiri\"}]}]," +
                       "\"data_updates\":[\"Added player David Marshall (9002) to team Hull City FC.\",\"Added player Alfred N'Diaye (34123) to team Hull City FC.\",\"Deleted match stats for player Oumar Niasse (D11 Team: None, whoscored_id: 111141) from match Arsenal FC vs Everton FC\",\"Moved player Oumar Niasse (D11 Team: None, whoscored_id: 111141) from team Everton FC to team Hull City FC.\",\"Added player Dieumerci Mbokani (22820) to team Hull City FC.\",\"Added player Moussa Sissoko (29595) to team Tottenham Hotspur.\"]}";

        UpdateMatchStatsResult uploadResult = gson.fromJson(json, new UpdateMatchStatsTypeToken().getType());
        System.out.println(uploadResult.isValid());
    }

	// @Before
	public void login(D11API d11Api) {
		d11Api.login("dromelvan@fake.email.com", "password");
	}

	// @Test
	public void getSeasons(D11API d11Api) {
		List<Season> seasons = d11Api.getSeasons();
		for (Season season : seasons) {
			System.out.println(season);
		}
	}

    //@Test
    public void getSeason(D11API d11Api) {
        Season season = d11Api.getSeason(2);
        System.out.println(season);
    }

	// @Test
	public void getCurrentSeason(D11API d11Api) {
		Season season = d11Api.getCurrentSeason();
		System.out.println(season);
	}

	// @Test
	public void getMatchDaysBySeason(D11API d11Api) {
		for (MatchDay matchDay : d11Api.getMatchDaysBySeason("2016-2017")) {
			System.out.println(matchDay);
		}
	}

	// @Test
	public void getMatchDayBySeasonAndNumber(D11API d11Api) {
		System.out.println(d11Api.getMatchDayBySeasonAndMatchDayNumber("2016-2017", 38));
	}

	// @Test
	public void getMatchesByDate(D11API d11Api) {
		List<Match> matches = d11Api.getMatchesByDate(LocalDate.parse("2017-05-13"));
		for (Match match : matches) {
			System.out.println(match);
		}
	}

	// @Test
	public void getTeamNamed(D11API d11Api) {
		Team team = d11Api.getTeamNamed("Manchester City");
		if (team != null) {
			System.out.println(team);
		}
	}

	// @Test
	public void getTeamSquad(D11API d11Api) {
		TeamSquad teamSquad = d11Api.getTeamSquad(2);
		if (teamSquad != null) {
			System.out.println(teamSquad);
		}
	}

	// @Test
	public void getPlayer(D11API d11Api) {
		Player player = d11Api.getPlayer(1234);
		if (player != null) {
			System.out.println(player);
		}
	}

	// @Test
	public void getPlayersNamed(D11API d11Api) {
		List<Player> players = d11Api.getPlayersNamed("Steven Gerrard");
		players.forEach(System.out::println);
	}

	@Test
	public void updateMatchStats(D11API d11Api) {
	    Match match = d11Api.getMatch(4935);
	    File file = new File("src/test/resources/2016-2017/38/Hull vs Tottenham (FT).json");
	    d11Api.login("dromelvan@fake.email.com", "password");
	    UpdateMatchStatsResult uploadResult = d11Api.updateMatchStats(match, file, false);
	    if(uploadResult != null) {
	        System.out.println(uploadResult);
	    }
	}
}

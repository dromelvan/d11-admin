package org.d11.admin;

import java.io.File;

import org.d11.admin.download.whoscored.WhoScoredMatchSeleniumDownloader;
import org.d11.admin.download.whoscored.WhoScoredPlayerDownloader;
import org.d11.admin.model.Match;
import org.d11.admin.model.Player;
import org.d11.admin.parse.whoscored.WhoScoredMatchParser;
import org.d11.admin.parse.whoscored.WhoScoredPlayerParser;
import org.d11.admin.read.whoscored.MatchReader;
import org.d11.admin.task.whoscored.DownloadWhoScoredMatchStatsTask;
import org.d11.admin.task.whoscored.DownloadWhoScoredPlayerTask;
import org.d11.admin.task.whoscored.UpdateMatchDateTimeTask;
import org.d11.admin.task.whoscored.UpdateMatchDayMatchDateTimesTask;
import org.d11.admin.write.whoscored.MatchWriter;
import org.d11.api.D11API;
import org.d11.api.MatchDay;
import org.jukito.JukitoModule;
import org.jukito.JukitoRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

@RunWith(JukitoRunner.class)
public class WhoScoredTest {

	public static class WhoScoredTaskTestModule extends JukitoModule {
		@Override
		protected void configureTest() {
			bind(WebDriver.class).to(FirefoxDriver.class);
		}
	}

	// @Test
	public void downloadWhoScoredMatch(WhoScoredMatchSeleniumDownloader downloader) {
		downloader.setId(1284746);
		downloader.setSeason("2018-2019");
		downloader.setMatchDay(1);
		File htmlFile = downloader.download();
		if (htmlFile != null) {
			System.out.println(htmlFile);
		}
	}

	// @Test
	public void parseWhoScoredMatch(WhoScoredMatchParser parser) {
		// File file = new File("tmp/whoscored.com/matches/2017-2018/08/Liverpool 4-0 Brighton - Premier League 2017_2018 Live3.html");
		// File file = new File("tmp/whoscored.com/matches/2017-2018/38/Tottenham 5-4 Leicester - Premier League 2017_2018 Live.html");
		File file = new File("tmp/whoscored.com/matches/2018-2019/01/Manchester United-Leicester - Premier League 2018_2019 Live.html");
		Match match = parser.parse(file);
		System.out.println(match);
	}

	// @Test
	public void writeWhoScoredMatch(WhoScoredMatchParser parser, MatchWriter writer) {
		writer.setSeason("2017-2018");
		writer.setMatchDayNumber(38);

		File file = new File("tmp/whoscored.com/matches/2017-2018/38/Tottenham 5-4 Leicester - Premier League 2017_2018 Live.html");
		Match match = parser.parse(file);
		File jsonFile = writer.write(match);
		if (jsonFile != null) {
			System.out.println(jsonFile);
		}
	}

	// @Test
	public void readWhoScoredMatch(MatchReader reader) {
		File file = new File("data/whoscored.com/matches/2017-2018/38/Tottenham vs Leicester.json");
		Match match = reader.read(file);
		System.out.println(match);
	}

	// @Test
	public void downloadWhoScoredPlayer(WhoScoredPlayerDownloader downloader) {
		downloader.setId(83532);
		downloader.setName("Harry Kane");
		File htmlFile = downloader.download();
		if (htmlFile != null) {
			System.out.println(htmlFile);
		}
	}

	@Test
	public void parserWhoScoredPlayer(WhoScoredPlayerParser parser) {
		File file = new File("tmp/whoscored.com/players/Harry Kane (83532).html");
		Player player = parser.parse(file);
		if (player != null) {
			System.out.println(player);
		}
	}

	// @Before
	public void before(D11API d11Api) {
		d11Api.login("dromelvan@fake.email.com", "password");
	}

	// @Test
	public void downloadWhoScoredMatchStats(DownloadWhoScoredMatchStatsTask task) {
		task.setMatchId("1080728");
		task.setMatchDayNumber(33);
		if (task.execute()) {
			File htmlFile = task.getResult();
			System.out.println(htmlFile);
		}
	}

	// @Test
	public void updateMatchDate(UpdateMatchDateTimeTask task) {
		task.setMatchId("4752");
		task.setMatchDayNumber(20);
		if (task.execute()) {
			System.out.println(task.getResult());
		}
	}

	// @Test
	public void updateMatchDayMatchDates(D11API d11API, UpdateMatchDayMatchDateTimesTask task) {
		MatchDay matchDay = d11API.getUpcomingMatchDay();
		task.setMatchDay(matchDay);
		task.execute();
	}

	// @Test
	public void downloadWhoScoredPlayer(DownloadWhoScoredPlayerTask task) {
		task.setPlayerId("86425");
		if (task.execute()) {
			File htmlFile = task.getResult();
			System.out.println(htmlFile);
		}
	}

}

package org.d11.admin;

import java.io.File;
import java.util.List;

import org.d11.admin.download.FirefoxDriverProvider;
import org.d11.admin.download.WebDriverProvider;
import org.d11.admin.download.whoscored.WhoScoredMatchSeleniumDownloader;
import org.d11.admin.download.whoscored.WhoScoredPlayerDownloader;
import org.d11.admin.model.Match;
import org.d11.admin.model.Player;
import org.d11.admin.model.whoscored.WSPlayer;
import org.d11.admin.parse.whoscored.WhoScoredMatchParser;
import org.d11.admin.parse.whoscored.WhoScoredPlayerParser;
import org.d11.admin.read.whoscored.MatchReader;
import org.d11.admin.read.whoscored.PlayerReader;
import org.d11.admin.task.whoscored.CreateMatchDayMatchFilesTask;
import org.d11.admin.task.whoscored.UpdateMatchDateTimesTask;
import org.d11.admin.task.whoscored.UpdateMatchStatsTask;
import org.d11.admin.task.whoscored.WhoScoredParseMatchFileTask;
import org.d11.admin.write.whoscored.WhoScoredMatchWriter;
import org.d11.admin.write.whoscored.WhoScoredPlayerWriter;
import org.d11.api.v1.D11API;
import org.jukito.JukitoModule;
import org.jukito.JukitoRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Singleton;

@RunWith(JukitoRunner.class)
public class WhoScoredTest {

	public static class WhoScoredTaskTestModule extends JukitoModule {
	    @Override
	    protected void configureTest() {
	        bind(D11API.class).in(Singleton.class);
	        bind(WebDriverProvider.class).to(FirefoxDriverProvider.class);
	    }

	}
	
	@Test
	public void downloadWhoScoredMatch(WhoScoredMatchSeleniumDownloader downloader) {
		downloader.setWhoScoredId(1080516);
		downloader.setSeason("2016-2017");
		downloader.setMatchDay(38);
		File htmlFile = downloader.download();
		if (htmlFile != null) {
			System.out.println(htmlFile);
		}
	}

	//@Test
	public void parseWhoScoredMatch(WhoScoredMatchParser parser) {
		//File file = new File("src/test/resources/2018-2019/01/Newcastle United-Tottenham - Premier League 2018_2019 Live (FT).html");
	    //File file = new File("src/test/resources/2018-2019/01/Newcastle United 1-2 Tottenham - Premier League 2018_2019 Live.html");
	    //File file = new File("src/test/resources/2018-2019/01/Manchester United-Leicester - Premier League 2018_2019 Live (85).html");
	    File file = new File("src/test/resources/2018-2019/01/Wolverhampton Wanderers-Everton - Premier League 2018_2019 Live ().html");
		Match match = parser.parse(file);
		System.out.println(match);
	}

	//@Test
	public void writeWhoScoredMatch(WhoScoredMatchParser parser, WhoScoredMatchWriter writer) {
		writer.setSeason("2018-2019");
		writer.setMatchDayNumber(1);

        //File file = new File("src/test/resources/2018-2019/01/Newcastle United-Tottenham - Premier League 2018_2019 Live (FT).html");
        //File file = new File("src/test/resources/2018-2019/01/Newcastle United 1-2 Tottenham - Premier League 2018_2019 Live.html");
        File file = new File("src/test/resources/2018-2019/01/Manchester United-Leicester - Premier League 2018_2019 Live (85).html");

		Match match = parser.parse(file);
		File jsonFile = writer.write(match);
		if (jsonFile != null) {
			System.out.println(jsonFile);
		}
	}

	// @Test
	public void readWhoScoredMatch(MatchReader reader) {
		File file = new File("data/whoscored.com/matches/2016-2017/38/Arsenal vs Everton (FT).json");
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

	// @Test
	public void parserWhoScoredPlayer(WhoScoredPlayerParser parser) {
		File file = new File("tmp/whoscored.com/players/Harry Kane (83532).html");
		Player player = parser.parse(file);
		if (player != null) {
			System.out.println(player);
		}
	}

	// @Test
	public void writeWhoScoredPlayer(WhoScoredPlayerParser parser, WhoScoredPlayerWriter writer) {
		File file = new File("tmp/whoscored.com/players/Harry Kane (83532).html");
		Player player = parser.parse(file);
		if (player != null) {
			writer.write(player);
		}
	}

	// @Test
	public void readWhoScoredPlayer(PlayerReader reader) {
		File file = new File("data/whoscored.com/players/Harry Kane (83532).json");
		WSPlayer player = reader.read(file);
		if (player != null) {
			System.out.println(player);
		}
	}

	// @Before
	public void before(D11API d11Api) {
		d11Api.login("dromelvan@fake.email.com", "password");
	}

	//@Test
	public void updateMatchDates(D11API d11API, UpdateMatchDateTimesTask task) {
	    d11API.login("dromelvan@fake.email.com", "password");
		if(task.execute()) {
            List<Match> matches = task.getResult();
            matches.stream().forEach(System.out::println);
		}
	}

	// @Test
	public void createMatchDayMatchFilesTask(CreateMatchDayMatchFilesTask task) {
	    task.setMatchDayNumber(1);
	    task.setSeasonName("2016-2017");
		if (task.execute()) {
			for (File file : task.getResult()) {
				System.out.println(file);
			}
		}
	}

	// @Test
	public void parseMatchFileTask(WhoScoredParseMatchFileTask task, WhoScoredMatchSeleniumDownloader downloader) {
		downloader.setWhoScoredId(1080516);
		downloader.setSeason("2016-2017");
		downloader.setMatchDay(38);
		File htmlFile = downloader.download();
		if (htmlFile != null) {
			task.setFile(htmlFile);
			if (task.execute()) {
				System.out.println(task.getResult());
			}
		}
	}

	//@Test
	public void updateMatchStats(D11API d11Api, UpdateMatchStatsTask task) {
	    d11Api.login("dromelvan@fake.email.com", "password");
	    Match match = d11Api.getMatch(4940);
	    task.setMatch(match);
	    if(task.execute()) {
	        System.out.println(task.getResult());
	    }
	}
}

package org.d11.admin.task.premierleague;

import java.io.File;
import java.util.List;

import org.d11.admin.parser.premierleague.PlayerInformationParserObject;
import org.d11.admin.parser.premierleague.PlayerParserObject;
import org.d11.admin.parser.premierleague.TeamParserObject;
import org.jukito.JukitoModule;
import org.jukito.JukitoRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JukitoRunner.class)
public class PremierLeagueTest {

	public static class PremierLeagueTaskTestModule extends JukitoModule {
		@Override
		protected void configureTest() {
		}
	}

	// @Test
	public void downloadPremierLeagueTable(DownloadPremierLeagueTableTask task) {
		if (task.execute()) {
			File htmlFile = task.getResult();
			System.out.println(htmlFile);
		}
	}

	// @Test
	public void parsePremierLeagueTable(DownloadPremierLeagueTableTask downloadTask, ParsePremierLeagueTableFileTask parseTask) {
		if (downloadTask.execute()) {
			parseTask.setSourceFile(downloadTask.getResult());
			if (parseTask.execute()) {
				List<TeamParserObject> teamParserObjects = parseTask.getResult();
				teamParserObjects.stream().forEach(System.out::println);
			}
		}
	}

	// @Test
	public void downloadPremierLeagueTeam(DownloadPremierLeagueTeamTask task) {
		task.setId("1");
		task.setName("Arsenal");
		if (task.execute()) {
			File htmlFile = task.getResult();
			System.out.println(htmlFile.exists());
		}
	}

	// @Test
	public void parsePremierLeagueTeam(DownloadPremierLeagueTeamTask downloadTask, ParsePremierLeagueTeamFileTask parseTask) {
		downloadTask.setId("1");
		downloadTask.setName("Arsenal");
		if (downloadTask.execute()) {
			parseTask.setSourceFile(downloadTask.getResult());
			if (parseTask.execute()) {
				List<PlayerParserObject> playerParserObjects = parseTask.getResult();
				playerParserObjects.stream().forEach(System.out::println);
			}
		}
	}

	// @Test
	public void downloadPremierLeaguePlayer(DownloadPremierLeaguePlayerTask task) {
		task.setId("2651");
		task.setName("Petr Cech");
		if (task.execute()) {
			File htmlFile = task.getResult();
			System.out.println(htmlFile.exists());
		}
	}

	@Test
	public void parsePremierLeaguePlayer(DownloadPremierLeaguePlayerTask downloadTask, ParsePremierLeaguePlayerFileTask parseTask) {
		downloadTask.setId("2651");
		downloadTask.setName("Petr Cech");
		if (downloadTask.execute()) {
			parseTask.setSourceFile(downloadTask.getResult());
			if (parseTask.execute()) {
				List<PlayerInformationParserObject> playerParserObjects = parseTask.getResult();
				playerParserObjects.stream().forEach(System.out::println);
			}
		}
	}

}

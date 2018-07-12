package org.d11.admin.task.premierleague;

import java.io.File;
import java.util.List;

import org.d11.admin.download.premierleague.PremierLeaguePlayerDownloader;
import org.d11.admin.download.premierleague.PremierLeagueTableDownloader;
import org.d11.admin.download.premierleague.PremierLeagueTeamDownloader;
import org.d11.admin.model.Team;
import org.d11.admin.parse.premierleague.PremierLeagueTableParser;
import org.d11.admin.parser.premierleague.PlayerInformationParserObject;
import org.d11.admin.parser.premierleague.PlayerParserObject;
import org.d11.admin.parser.premierleague.TeamLineup;
import org.d11.admin.parser.premierleague.TeamLineupChange;
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
	public void downloadPremierLeagueTable(PremierLeagueTableDownloader downloader) {
		File htmlFile = downloader.download();
		if (htmlFile != null) {
			System.out.println(htmlFile);
		}
	}

	@Test
	public void parsePremierLeagueTable(PremierLeagueTableDownloader downloader, PremierLeagueTableParser parser) {
		File htmlFile = downloader.download();
		if (htmlFile != null) {
			List<Team> teams = parser.parse(htmlFile);
			teams.stream().forEach(System.out::println);
		}
	}

	// @Test
	public void downloadPremierLeagueTeam(PremierLeagueTeamDownloader downloader) {
		downloader.setId(1);
		downloader.setName("Arsenal");
		File htmlFile = downloader.download();
		if (htmlFile != null) {
			System.out.println(htmlFile.exists());
		}
	}

	// @Test
	public void parsePremierLeagueTeam(PremierLeagueTeamDownloader downloader, ParsePremierLeagueTeamFileTask parseTask) {
		downloader.setId(1);
		downloader.setName("Arsenal");
		File htmlFile = downloader.download();
		if (htmlFile != null) {
			parseTask.setSourceFile(htmlFile);
			if (parseTask.execute()) {
				List<PlayerParserObject> playerParserObjects = parseTask.getResult();
				playerParserObjects.stream().forEach(System.out::println);
			}
		}
	}

	// @Test
	public void downloadPremierLeaguePlayer(PremierLeaguePlayerDownloader downloader) {
		downloader.setId(2651);
		downloader.setName("Petr Cech");
		File htmlFile = downloader.download();
		if (htmlFile != null) {
			System.out.println(htmlFile.exists());
		}
	}

	// @Test
	public void parsePremierLeaguePlayer(PremierLeaguePlayerDownloader downloader, ParsePremierLeaguePlayerFileTask parseTask) {
		downloader.setId(2651);
		downloader.setName("Petr Cech");
		File htmlFile = downloader.download();
		if (htmlFile != null) {
			parseTask.setSourceFile(htmlFile);
			if (parseTask.execute()) {
				List<PlayerInformationParserObject> playerParserObjects = parseTask.getResult();
				playerParserObjects.stream().forEach(System.out::println);
			}
		}
	}

	// @Test
	public void createTeamLineupFile(CreateTeamLineupFileTask task) {
		TeamParserObject teamParserObject = new TeamParserObject("1", "Arsenal");
		task.setTeamParserObject(teamParserObject);
		if (task.execute()) {
			File file = task.getResult();
			System.out.println(file);
		}
	}

	// @Test
	public void createTeamLineupFiles(CreateTeamLineupFilesTask task) {
		if (task.execute()) {
			List<File> files = task.getResult();
			files.stream().forEach(System.out::println);
		}
	}

	// @Test
	public void readTeamLineupFile(ReadTeamLineupFileTask task, CreateTeamLineupFileTask createTask) {
		TeamParserObject teamParserObject = new TeamParserObject("1", "Arsenal");
		createTask.setTeamParserObject(teamParserObject);
		if (createTask.execute()) {
			File file = createTask.getResult();
			task.setFile(file);
			if (task.execute()) {
				TeamLineup teamLineup = task.getResult();
				System.out.println(teamLineup);
			}
			file.delete();
		}
	}

	// @Test
	public void compareTeamLineups(FindTeamLineupChangesTask task) {
		File directory = new File("data/premierleague.com/lineups");
		task.setDirectory(directory);
		if (task.execute()) {
			List<TeamLineupChange> teamLineupChanges = task.getResult();
			teamLineupChanges.stream().forEach(System.out::println);
		}
	}

	// @Test
	public void refreshTeamLineups(RefreshTeamLineupsTask task) {
		if (task.execute()) {
			List<TeamLineupChange> teamLineupChanges = task.getResult();
			teamLineupChanges.stream().forEach(System.out::println);
		}
	}

}

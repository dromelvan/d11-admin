package org.d11.admin;

import java.io.File;
import java.util.List;

import org.d11.admin.download.premierleague.PremierLeaguePlayerDownloader;
import org.d11.admin.download.premierleague.PremierLeaguePlayerImageDownloader;
import org.d11.admin.download.premierleague.PremierLeagueTableDownloader;
import org.d11.admin.download.premierleague.PremierLeagueTeamDownloader;
import org.d11.admin.model.Team;
import org.d11.admin.model.TeamSquadChange;
import org.d11.admin.model.premierleague.PLPlayer;
import org.d11.admin.parse.premierleague.PremierLeaguePlayerParser;
import org.d11.admin.parse.premierleague.PremierLeagueTableParser;
import org.d11.admin.parse.premierleague.PremierLeagueTeamParser;
import org.d11.admin.task.premierleague.CreateTeamSquadFilesTask;
import org.d11.admin.task.premierleague.DownloadPlayerImagesTask;
import org.d11.admin.task.premierleague.FindTeamSquadChangesTask;
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

	// @Test
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
	public void parsePremierLeagueTeam(PremierLeagueTeamDownloader downloader, PremierLeagueTeamParser parser) {
		downloader.setId(1);
		downloader.setName("Arsenal");
		File htmlFile = downloader.download();
		if (htmlFile != null) {
			List<PLPlayer> players = parser.parse(htmlFile);
			players.stream().forEach(System.out::println);
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
	public void parsePremierLeaguePlayer(PremierLeaguePlayerDownloader downloader, PremierLeaguePlayerParser parser) {
		downloader.setId(2651);
		downloader.setName("Petr Cech");
		File htmlFile = downloader.download();
		if (htmlFile != null) {
			PLPlayer player = parser.parse(htmlFile);
			System.out.println(player);
		}
	}

	// @Test
	public void createTeamSquadFiles(CreateTeamSquadFilesTask task) {
		if (task.execute()) {
			List<File> files = task.getResult();
			files.stream().forEach(System.out::println);
		}
	}

	@Test
	public void findTeamSquadChanges(FindTeamSquadChangesTask task) {
		if (task.execute()) {
			List<TeamSquadChange> teamSquadChanges = task.getResult();
			teamSquadChanges.stream().forEach(System.out::println);
		}
	}

	// @Test
	public void downloadPremierLeaugePlayerImage(PremierLeaguePlayerImageDownloader downloader) {
		downloader.setId(5178);
		downloader.setName("Mohamed Salah");
		downloader.setImageId("p118748");
		File imageFile = downloader.download();
		if (imageFile != null) {
			System.out.println(imageFile);
		}
	}

	//@Test
	public void downloadPremierLeaguePlayerImages(DownloadPlayerImagesTask task) {
		if (task.execute()) {
			List<File> files = task.getResult();
			files.stream().forEach(System.out::println);
		}
	}
}

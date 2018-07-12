package org.d11.admin.task.premierleague;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.d11.admin.download.premierleague.PremierLeagueTableDownloader;
import org.d11.admin.download.premierleague.PremierLeagueTeamDownloader;
import org.d11.admin.model.Player;
import org.d11.admin.model.Team;
import org.d11.admin.model.TeamSquad;
import org.d11.admin.parse.premierleague.PremierLeagueTableParser;
import org.d11.admin.parse.premierleague.PremierLeagueTeamParser;
import org.d11.admin.task.D11Task;
import org.d11.admin.write.premierleague.TeamSquadWriter;

import com.google.inject.Inject;

public class CreateTeamSquadFilesTask extends D11Task<List<File>> {

	@Inject
	private PremierLeagueTableDownloader tableDownloader;
	@Inject
	private PremierLeagueTableParser tableParser;
	@Inject
	private PremierLeagueTeamDownloader teamDownloader;
	@Inject
	private PremierLeagueTeamParser teamParser;
	@Inject
	private TeamSquadWriter writer;

	@Override
	public boolean execute() {
		List<File> files = new ArrayList<File>();
		File tableFile = tableDownloader.download();
		if (tableFile != null) {
			List<Team> teams = tableParser.parse(tableFile);
			for (Team team : teams) {
				teamDownloader.setId(team.getId());
				teamDownloader.setName(team.getName());
				File teamFile = teamDownloader.download();
				if (teamFile != null) {
					List<Player> players = teamParser.parse(teamFile);
					TeamSquad teamSquad = new TeamSquad(team, players);
					File teamSquadFile = writer.write(teamSquad);
					if (teamSquadFile != null) {
						files.add(teamSquadFile);
					}
				}
			}
			setResult(files);
			return true;
		}
		return false;
	}

}

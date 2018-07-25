package org.d11.admin.task.premierleague;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.d11.admin.model.Player;
import org.d11.admin.model.TeamSquad;
import org.d11.admin.model.TeamSquadChange;
import org.d11.admin.model.TeamSquadChange.ChangeType;
import org.d11.admin.read.premierleague.TeamSquadReader;
import org.d11.admin.task.D11Task;

import com.google.inject.Inject;

public class FindTeamLineupChangesTask extends D11Task<List<TeamSquadChange>> {

	@Inject
	private TeamSquadReader reader;
	private File directory;

	public File getDirectory() {
		return directory;
	}

	public void setDirectory(File directory) {
		this.directory = directory;
	}

	@Override
	public boolean execute() {
		List<TeamSquadChange> teamSquadChanges = new ArrayList<TeamSquadChange>();

		for (File teamDirectory : getDirectory().listFiles()) {
			File[] teamSquadFiles = teamDirectory.listFiles();
			if (teamSquadFiles.length > 1) {
				TeamSquad currentTeamSquad = reader.read(teamSquadFiles[teamSquadFiles.length - 1]);
				TeamSquad previousTeamSquad = getD11Api().getTeamSquad(41); //reader.read(teamSquadFiles[teamSquadFiles.length - 2]);

				for (Player player : currentTeamSquad.getPlayers()) {
					if (!previousTeamSquad.contains(player)) {
						teamSquadChanges.add(new TeamSquadChange(currentTeamSquad.getTeam(), player, ChangeType.ADDED));
					}
				}
				for (Player player : previousTeamSquad.getPlayers()) {
					if (!currentTeamSquad.contains(player)) {
						teamSquadChanges.add(new TeamSquadChange(currentTeamSquad.getTeam(), player, ChangeType.REMOVED));
					}
				}
			}
			break;
		}
		setResult(teamSquadChanges);
		return true;
	}
}

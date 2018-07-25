package org.d11.admin.task.premierleague;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.d11.admin.model.Player;
import org.d11.admin.model.Team;
import org.d11.admin.model.TeamSquad;
import org.d11.admin.model.TeamSquadChange;
import org.d11.admin.model.TeamSquadChange.ChangeType;
import org.d11.admin.read.premierleague.TeamSquadReader;
import org.d11.admin.task.D11Task;

import com.google.inject.Inject;

public class FindTeamSquadChangesTask extends D11Task<List<TeamSquadChange>> {

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
			if (teamSquadFiles.length > 0) {
				TeamSquad currentTeamSquad = reader.read(teamSquadFiles[teamSquadFiles.length - 1]);
				Team team = getD11Api().getTeamNamed(currentTeamSquad.getTeam().getName());
				if (team != null) {
					TeamSquad previousTeamSquad = getD11Api().getTeamSquad(team.getId());

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
			}
		}
		setResult(teamSquadChanges);
		return true;
	}
}

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

public class FindTeamSquadChangesTask extends D11Task<List<TeamSquadChange>> {

	@Inject
	private CreateTeamSquadFilesTask createTask;
	@Inject
	private TeamSquadReader reader;
	private File directory;
	private final static Logger logger = LoggerFactory.getLogger(FindTeamSquadChangesTask.class);

	public File getDirectory() {
		return directory;
	}

	public void setDirectory(File directory) {
		this.directory = directory;
	}

	@Override
	public boolean execute() {
		List<TeamSquadChange> teamSquadChanges = new ArrayList<TeamSquadChange>();

		logger.debug("Creating new team squad files...");
		if (createTask.execute()) {
			List<File> files = createTask.getResult();
			if (files.size() > 0) {
				File directory = files.get(0).getParentFile().getParentFile();
				setDirectory(directory);
				logger.debug("Finding team squad changes...");

				for (File teamDirectory : getDirectory().listFiles()) {
					File[] teamSquadFiles = teamDirectory.listFiles();
					if (teamSquadFiles.length > 0) {
						TeamSquad currentTeamSquad = reader.read(teamSquadFiles[teamSquadFiles.length - 1]);

						logger.debug("Handling team {}.", currentTeamSquad.getTeam().getName());
						Team team = getD11Api().getTeamNamed(currentTeamSquad.getTeam().getName());
						if (team != null) {
							TeamSquad previousTeamSquad = getD11Api().getTeamSquad(team.getId());

							for (Player player : currentTeamSquad.getPlayers()) {
								if (!previousTeamSquad.contains(player)) {
									TeamSquadChange teamSquadChange = new TeamSquadChange(currentTeamSquad.getTeam(), player, ChangeType.ADDED);
									List<Player> players = getD11Api().getPlayersNamed(player.getName());
									for (Player existingPlayer : players) {
										if (player.equals(existingPlayer)) {
											teamSquadChange.setNewPlayer(false);
											break;
										}
									}
									teamSquadChanges.add(teamSquadChange);
								} else {
									Player previousTeamSquadPlayer = previousTeamSquad.getPlayer(player);
									if (!previousTeamSquadPlayer.getPosition().equals(player.getPosition())) {
										teamSquadChanges.add(new TeamSquadChange(currentTeamSquad.getTeam(), player, ChangeType.CHANGED_POSITION));
									}
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
			}
		}

		logger.debug("Done");
		setResult(teamSquadChanges);
		return true;
	}

}

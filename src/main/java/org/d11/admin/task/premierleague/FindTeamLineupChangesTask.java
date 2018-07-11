package org.d11.admin.task.premierleague;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.d11.admin.parser.premierleague.PlayerParserObject;
import org.d11.admin.parser.premierleague.TeamLineup;
import org.d11.admin.parser.premierleague.TeamLineupChange;
import org.d11.admin.parser.premierleague.TeamLineupChange.ChangeType;
import org.d11.admin.task.D11Task;

import com.google.inject.Inject;

public class FindTeamLineupChangesTask extends D11Task<List<TeamLineupChange>> {

	@Inject
	private ReadTeamLineupFileTask readTask;
	private File directory;

	public File getDirectory() {
		return directory;
	}

	public void setDirectory(File directory) {
		this.directory = directory;
	}

	@Override
	public boolean execute() {
		List<TeamLineupChange> teamLineupChanges = new ArrayList<TeamLineupChange>();
		for (File teamDirectory : getDirectory().listFiles()) {
			File[] teamLineupFiles = teamDirectory.listFiles();
			if (teamLineupFiles.length > 1) {
				readTask.setFile(teamLineupFiles[teamLineupFiles.length - 1]);
				readTask.execute();
				TeamLineup currentTeamLineup = readTask.getResult();

				readTask.setFile(teamLineupFiles[teamLineupFiles.length - 2]);
				readTask.execute();
				TeamLineup previousTeamLineup = readTask.getResult();

				for (PlayerParserObject playerParserObject : currentTeamLineup.getPlayerParserObjects()) {
					if (!previousTeamLineup.contains(playerParserObject)) {
						teamLineupChanges.add(new TeamLineupChange(currentTeamLineup.getTeamParserObject(), playerParserObject, ChangeType.ADDED));
					}
				}
				for (PlayerParserObject playerParserObject : previousTeamLineup.getPlayerParserObjects()) {
					if (!currentTeamLineup.contains(playerParserObject)) {
						teamLineupChanges.add(new TeamLineupChange(currentTeamLineup.getTeamParserObject(), playerParserObject, ChangeType.REMOVED));
					}
				}
			}
		}
		setResult(teamLineupChanges);
		return true;
	}
}

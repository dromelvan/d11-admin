package org.d11.admin.task.premierleague;

import java.io.File;
import java.util.List;

import org.d11.admin.parser.premierleague.TeamLineupChange;
import org.d11.admin.task.D11Task;

import com.google.inject.Inject;

public class RefreshTeamLineupsTask extends D11Task<List<TeamLineupChange>> {

	@Inject
	private CreateTeamLineupFilesTask createTask;
	@Inject
	private FindTeamLineupChangesTask findTask;

	@Override
	public boolean execute() {
		if (createTask.execute()) {
			List<File> files = createTask.getResult();
			if (files.size() > 0) {
				File directory = files.get(0).getParentFile().getParentFile();
				findTask.setDirectory(directory);
				if (findTask.execute()) {
					setResult(findTask.getResult());
					return true;
				}
			}
		}
		return false;
	}

}

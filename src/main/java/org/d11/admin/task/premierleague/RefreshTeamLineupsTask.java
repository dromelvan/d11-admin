package org.d11.admin.task.premierleague;

import java.io.File;
import java.util.List;

import org.d11.admin.model.TeamSquadChange;
import org.d11.admin.task.D11Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

public class RefreshTeamLineupsTask extends D11Task<List<TeamSquadChange>> {

	@Inject
	private CreateTeamSquadFilesTask createTask;
	@Inject
	private FindTeamLineupChangesTask findTask;
	private final static Logger logger = LoggerFactory.getLogger(RefreshTeamLineupsTask.class);

	@Override
	public boolean execute() {
		logger.info("Creating new team squad files...");
		if (createTask.execute()) {
			List<File> files = createTask.getResult();
			if (files.size() > 0) {
				File directory = files.get(0).getParentFile().getParentFile();
				findTask.setDirectory(directory);
				logger.info("Finding team squad changes...");
				if (findTask.execute()) {
					setResult(findTask.getResult());
					logger.info("Done");
					return true;
				}
			}
		}
		return false;
	}

}

package org.d11.admin.task.premierleague;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.d11.admin.download.premierleague.PremierLeagueTableDownloader;
import org.d11.admin.model.Team;
import org.d11.admin.parse.premierleague.PremierLeagueTableParser;
import org.d11.admin.task.D11Task;

import com.google.inject.Inject;

public class CreateTeamLineupFilesTask extends D11Task<List<File>> {

	@Inject
	private PremierLeagueTableDownloader downloader;
	@Inject
	private PremierLeagueTableParser parser;
	@Inject
	private CreateTeamLineupFileTask createTask;

	@Override
	public boolean execute() {
		File htmlFile = downloader.download();
		if (htmlFile != null) {
			parser.setFile(htmlFile);
			List<File> files = new ArrayList<File>();
			List<Team> teams = parser.parse(htmlFile);
			for (Team team : teams) {
				// createTask.setTeamParserObject(teamParserObject);
				if (createTask.execute()) {
					File file = createTask.getResult();
					files.add(file);
				} else {
					return false;
				}
			}
			setResult(files);
			return true;
		}
		return false;
	}

}

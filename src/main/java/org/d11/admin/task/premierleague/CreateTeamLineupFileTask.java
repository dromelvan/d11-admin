package org.d11.admin.task.premierleague;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.d11.admin.download.premierleague.PremierLeagueTeamDownloader;
import org.d11.admin.parser.premierleague.PlayerParserObject;
import org.d11.admin.parser.premierleague.TeamLineup;
import org.d11.admin.parser.premierleague.TeamParserObject;
import org.d11.admin.task.D11Task;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.Inject;

public class CreateTeamLineupFileTask extends D11Task<File> {

	@Inject
	private PremierLeagueTeamDownloader downloader;
	@Inject
	private ParsePremierLeagueTeamFileTask parseTask;
	private TeamLineup teamLineup = new TeamLineup();
	private final static Logger logger = LoggerFactory.getLogger(CreateTeamLineupFileTask.class);

	public void setTeamParserObject(TeamParserObject teamParserObject) {
		this.teamLineup.setTeamParserObject(teamParserObject);
	}

	@Override
	public boolean execute() {
		downloader.setId(Integer.parseInt(this.teamLineup.getTeamParserObject().getId()));
		downloader.setName(this.teamLineup.getTeamParserObject().getName());
		File htmlFile = downloader.download();
		if (htmlFile != null) {
			parseTask.setSourceFile(htmlFile);
			if (parseTask.execute()) {
				List<PlayerParserObject> playerParserObjects = parseTask.getResult();
				this.teamLineup.setPlayerParserObjects(playerParserObjects);
				Gson gson = new GsonBuilder().setPrettyPrinting().create();

				try {
					String team = this.teamLineup.getTeamParserObject().getName();
					File file = new File(getDataDirectory("premierleague.com/lineups/" + team), team + "-" + LocalDateTime.now().toString(DateTimeFormat.forPattern("ddMMyyyy-HHmmss")) + ".json");
					FileWriter fileWriter = new FileWriter(file);
					fileWriter.write(gson.toJson(this.teamLineup));
					fileWriter.close();
					setResult(file);
					logger.info("Created team lineup file in {}.", file);
				} catch (IOException e) {
					logger.error("Could not write team lineup file.", e);
				}
				return true;
			}
		}
		return false;
	}

}

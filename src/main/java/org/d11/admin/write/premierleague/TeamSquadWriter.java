package org.d11.admin.write.premierleague;

import java.io.File;

import org.d11.admin.model.TeamSquad;
import org.d11.admin.write.JsonWriter;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;

public class TeamSquadWriter extends JsonWriter<TeamSquad> {

	private TeamSquad teamSquad;

	public TeamSquadWriter() {
		setDirectoryName("premierleague.com/squads/%s");
		setFileName("%s-%s.json");
	}

	@Override
	public String formatDirectoryName(String directoryName) {
		return String.format(directoryName, this.teamSquad.getTeam().getName());
	}

	@Override
	public String formatFileName(String fileName) {
		return String.format(fileName, this.teamSquad.getTeam().getName(), LocalDateTime.now().toString(DateTimeFormat.forPattern("ddMMyyyy-HHmmss")));
	}

	@Override
	public File write(TeamSquad teamSquad) {
		this.teamSquad = teamSquad;
		return super.write(teamSquad);
	}

}

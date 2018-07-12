package org.d11.admin.read.premierleague;

import org.d11.admin.model.TeamSquad;
import org.d11.admin.read.JsonReader;

import com.google.gson.reflect.TypeToken;

public class TeamSquadReader extends JsonReader<TeamSquad> {

	@Override
	protected TypeToken<TeamSquad> getTypeToken() {
		return new TeamSquadTypeToken();
	}
}

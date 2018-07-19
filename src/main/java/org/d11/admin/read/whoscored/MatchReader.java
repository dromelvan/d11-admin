package org.d11.admin.read.whoscored;

import org.d11.admin.model.Match;
import org.d11.admin.read.JsonReader;

import com.google.gson.reflect.TypeToken;

public class MatchReader extends JsonReader<Match> {

	@Override
	protected TypeToken<Match> getTypeToken() {
		return new MatchTypeToken();
	}
}

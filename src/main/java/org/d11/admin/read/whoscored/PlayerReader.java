package org.d11.admin.read.whoscored;

import org.d11.admin.model.whoscored.WSPlayer;
import org.d11.admin.read.JsonReader;

import com.google.gson.reflect.TypeToken;

public class PlayerReader extends JsonReader<WSPlayer> {

	@Override
	protected TypeToken<WSPlayer> getTypeToken() {
		return new WSPlayerTypeToken();
	}

}

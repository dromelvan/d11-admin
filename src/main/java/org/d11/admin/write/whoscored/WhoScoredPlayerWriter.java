package org.d11.admin.write.whoscored;

import java.io.File;

import org.d11.admin.model.Player;
import org.d11.admin.write.JsonWriter;

public class WhoScoredPlayerWriter extends JsonWriter<Player> {

	private Player player;

	public WhoScoredPlayerWriter() {
		setDirectoryName("whoscored.com/players");
		setFileName("%s (%d).json");
	}

	@Override
	public String formatFileName(String fileName) {
		return String.format(fileName, this.player.getName(), this.player.getWhoScoredId());
	}

	@Override
	public File write(Player player) {
		this.player = player;
		return super.write(player);
	}

}

package org.d11.admin.task.whoscored;

import org.d11.admin.model.Match;
import org.d11.admin.task.D11Task;
import org.d11.api.v1.D11API;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

public class CreatePlayerTask extends D11Task<Match> {

	private String playerId;
	// @Inject
	// private DownloadWhoScoredPlayerTask downloadWhoScoredPlayerTask;
	// @Inject
	// private ParseWhoScoredPlayerFileTask parseWhoScoredPlayerFileTask;
	@Inject
	private D11API d11Api;
	private final static Logger logger = LoggerFactory.getLogger(CreatePlayerTask.class);

	public CreatePlayerTask() {
	}

	public CreatePlayerTask(String playerId) {
		this.playerId = playerId;
	}

	protected String getPlayerId() {
		return playerId;
	}

	protected void setPlayerId(String playerId) {
		this.playerId = playerId;
	}

	@Override
	public boolean execute() {
		// this.downloadWhoScoredPlayerTask.setPlayerId(getPlayerId());
		// if (this.downloadWhoScoredPlayerTask.execute()) {
		// File htmlFile = this.downloadWhoScoredPlayerTask.getResult();
		// // this.parseWhoScoredPlayerFileTask.setSourceFile(htmlFile);
		// // if (this.parseWhoScoredPlayerFileTask.execute()) {
		// // To be continued
		// // }
		// }
		return false;
	}
}

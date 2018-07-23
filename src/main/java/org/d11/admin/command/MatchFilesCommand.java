package org.d11.admin.command;

import org.d11.admin.task.whoscored.CreateMatchDayMatchFilesTask;

import com.google.inject.Inject;

public class MatchFilesCommand extends D11Command {

	private String seasonName;
	private Integer matchDayNumber;
	private CreateMatchDayMatchFilesTask createMatchDayMatchFilesTask;

	@Inject
	public MatchFilesCommand(CreateMatchDayMatchFilesTask createMatchDayMatchFilesTask) {
		this.createMatchDayMatchFilesTask = createMatchDayMatchFilesTask;
	}

	public String getSeasonName() {
		return seasonName;
	}

	public void setSeasonName(String seasonName) {
		this.seasonName = seasonName;
	}

	public Integer getMatchDayNumber() {
		return matchDayNumber;
	}

	public void setMatchDayNumber(Integer matchDayNumber) {
		this.matchDayNumber = matchDayNumber;
	}

	public void execute() {
		this.createMatchDayMatchFilesTask.setSeasonName(getSeasonName());
		this.createMatchDayMatchFilesTask.setMatchDayNumber(getMatchDayNumber());
		this.createMatchDayMatchFilesTask.execute();
	}

}

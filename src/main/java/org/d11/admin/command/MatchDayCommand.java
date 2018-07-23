package org.d11.admin.command;

import org.d11.admin.task.whoscored.CreateMatchDayMatchFilesTask;

import com.google.inject.Inject;

public class MatchDayCommand extends D11Command {

	private String season;
	private Integer number;
	private CreateMatchDayMatchFilesTask createMatchDayMatchFilesTask;

	@Inject
	public MatchDayCommand(CreateMatchDayMatchFilesTask createMatchDayMatchFilesTask) {
		this.createMatchDayMatchFilesTask = createMatchDayMatchFilesTask;
	}

	public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public void execute() {
		this.createMatchDayMatchFilesTask.setSeasonName(getSeason());
		this.createMatchDayMatchFilesTask.setMatchDayNumber(getNumber());
		this.createMatchDayMatchFilesTask.execute();
	}

}

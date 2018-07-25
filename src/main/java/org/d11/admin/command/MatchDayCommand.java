package org.d11.admin.command;

import org.d11.admin.task.whoscored.CreateMatchDayMatchFilesTask;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.google.inject.Inject;
import com.google.inject.Provider;

@Parameters(commandDescription = "Downloads and parses all matches for a match day.")
public class MatchDayCommand extends D11Command {

	@Parameter(names = { "-s", "-season" }, description = "The name of the season the match day belongs to. Default is the current season.")
	private String season;
	@Parameter(names = { "-n", "-number" }, description = "The match day number for the match day. Default is the current match day of the selected season.")
	private Integer number;
	private Provider<CreateMatchDayMatchFilesTask> provider;

	@Inject
	public MatchDayCommand(Provider<CreateMatchDayMatchFilesTask> provider) {
		setName("matchday");
		this.provider = provider;
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

	@Override
	public void execute() {
		CreateMatchDayMatchFilesTask task = this.provider.get();
		task.setSeasonName(getSeason());
		task.setMatchDayNumber(getNumber());
		task.execute();
	}

}

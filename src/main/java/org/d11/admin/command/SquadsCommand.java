package org.d11.admin.command;

import java.util.List;

import org.d11.admin.model.TeamSquadChange;
import org.d11.admin.task.premierleague.FindTeamSquadChangesTask;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.beust.jcommander.converters.BooleanConverter;
import com.google.inject.Inject;
import com.google.inject.Provider;

@Parameters(commandDescription = "Downloads all team squads and finds added/removed players.")
public class SquadsCommand extends D11Command {

	@Parameter(names = { "-pc", "-positionChanges" }, description = "Include position changes in the report.", required = false, converter = BooleanConverter.class)
	private boolean positionChanges = false;
	private Provider<FindTeamSquadChangesTask> provider;

	@Inject
	public SquadsCommand(Provider<FindTeamSquadChangesTask> provider) {
		setName("squads");
		this.provider = provider;
	}

	@Override
	public void execute() {
		FindTeamSquadChangesTask task = this.provider.get();
		task.setPositionChanges(this.positionChanges);
		if (task.execute()) {
			List<TeamSquadChange> teamSquadChanges = task.getResult();
			teamSquadChanges.stream().forEach(System.out::println);
		}
	}

}

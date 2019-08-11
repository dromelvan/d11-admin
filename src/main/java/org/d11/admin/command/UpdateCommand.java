package org.d11.admin.command;

import java.util.ArrayList;
import java.util.List;

import org.d11.admin.model.Match;
import org.d11.admin.task.whoscored.UpdateMatchStatsTask;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.beust.jcommander.converters.BooleanConverter;
import com.beust.jcommander.converters.CommaParameterSplitter;
import com.beust.jcommander.converters.IntegerConverter;
import com.google.inject.Inject;
import com.google.inject.Provider;

@Parameters(commandDescription = "Update stats for a match.")
public class UpdateCommand extends D11Command {

    @Parameter(names = { "-m", "-matchIds" }, description = "Comma separated list of ids for the D11 matches we want to update.", required = true, converter = IntegerConverter.class, splitter = CommaParameterSplitter.class)
    private List<Integer> matchIds;
    @Parameter(names = { "-f", "-finish" }, description = "Finish the matches after we have updated them.", required = false, converter = BooleanConverter.class)
    private boolean finish = false;    
	private Provider<UpdateMatchStatsTask> taskProvider;

	@Inject
	public UpdateCommand(Provider<UpdateMatchStatsTask> taskProvider) {
		setName("update");
		this.taskProvider = taskProvider;
	}

	@Override
	public void execute() {
	    if(getD11Api().login(getUser(), getPassword())) {
	    	List<Match> matches = new ArrayList<Match>();
    	    for(Integer matchId : this.matchIds) {
    	    	Match match = getD11Api().getMatch(matchId);
    	    	matches.add(match);
    	    }
            UpdateMatchStatsTask task = this.taskProvider.get();

            task.setMatches(matches);
            task.setFinish(this.finish);

            task.execute();
	    }
	}

}

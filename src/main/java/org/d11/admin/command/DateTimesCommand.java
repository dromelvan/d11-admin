package org.d11.admin.command;

import org.d11.admin.model.Match;
import org.d11.admin.task.whoscored.UpdateMatchDateTimesTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beust.jcommander.Parameters;
import com.google.inject.Inject;
import com.google.inject.Provider;

@Parameters(commandDescription = "Update dates and kickoff times for the current and the upcoming match day.")
public class DateTimesCommand extends D11Command {

	private Provider<UpdateMatchDateTimesTask> provider;
	private final static Logger logger = LoggerFactory.getLogger(DateTimesCommand.class);

	@Inject
	public DateTimesCommand(Provider<UpdateMatchDateTimesTask> provider) {
		setName("datetimes");
		this.provider = provider;
	}

	@Override
	public void execute() {
System.out.println(getUser() + " " + getPassword());
        if(getD11Api().login(getUser(), getPassword())) {
    	    logger.info("Updating match dates and kickoff times.");

    	    UpdateMatchDateTimesTask task = this.provider.get();

            if(!task.execute()) {
                logger.error("Could not change datetimes for all matches.");
            }
            for(Match match : task.getResult()) {
                logger.info("Change datetime for match {} to {}.", match.getId(), match.getDatetime());
            }
        }
	}

}

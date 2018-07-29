package org.d11.admin.command;

import org.d11.admin.task.GenerateD11FixturesTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beust.jcommander.Parameters;
import com.google.inject.Inject;
import com.google.inject.Provider;

@Parameters(commandDescription = "Generates D11 fixtures for the current season.")
public class GenerateD11FixturesCommand extends D11Command {

	private Provider<GenerateD11FixturesTask> provider;
	private final static Logger logger = LoggerFactory.getLogger(GenerateD11FixturesCommand.class);

	@Inject
	public GenerateD11FixturesCommand(Provider<GenerateD11FixturesTask> provider) {
		setName("fixtures");
		this.provider = provider;
	}

	@Override
	public void execute() {
	    logger.info("Generating fixtures...");
	    GenerateD11FixturesTask task = this.provider.get();
		if (task.execute()) {
		    logger.info("Done");
		}
	}

}

package org.d11.admin;

import org.d11.admin.command.MatchDayCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class D11Admin {

	@Parameter(names = { "-help", "-h" }, description = "Display this help message.", help = true)
	private boolean help;
	@Parameter(names = { "-c", "-command" }, description = "The command to execute.", required = true)
	private String command;
	@Parameter(names = { "-commands" }, description = "List of available commands.", help = true)
	private boolean commands;
	@Parameter(names = { "-number" }, description = "The number for the object you want to perform a command on.")
	private Integer number;
	@Parameter(names = { "-season" }, description = "The name of the season you want to perform a command on.")
	private String season;

	private Injector injector;
	private final static Logger logger = LoggerFactory.getLogger(D11Admin.class);

	public static void main(String[] args) {
		D11Admin d11Admin = new D11Admin();
		JCommander jCommander = JCommander.newBuilder()
				.addObject(d11Admin)
				.build();
		jCommander.setProgramName("d11");

		try {
			jCommander.parse(args);
			d11Admin.run();
		} catch (ParameterException e) {
			jCommander.usage();
		}
	}

	public D11Admin() {
		this.injector = Guice.createInjector(new D11AdminModule());
	}

	public void run() {
		switch (this.command) {
			case "matchday":
				matchDay();
				break;
			default:
				logger.error("Command '{}' not implemented.", this.command);
		}
	}

	public void matchDay() {
		MatchDayCommand command = this.injector.getInstance(MatchDayCommand.class);
		command.setSeason(this.season);
		command.setNumber(this.number);
		command.execute();
	}

}

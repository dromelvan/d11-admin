package org.d11.admin;

import java.util.HashMap;
import java.util.Map;

import org.d11.admin.command.D11Command;
import org.d11.admin.command.SquadsCommand;
import org.d11.admin.command.MatchDayCommand;
import org.d11.admin.command.ParseCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

public class D11Admin {

	@Parameter(names = { "-help", "-h" }, description = "Display this help message.", help = true)
	private boolean help;

	private JCommander jCommander;
	private Map<String, D11Command> commands = new HashMap<String, D11Command>();
	private final static Logger logger = LoggerFactory.getLogger(D11Admin.class);

	public static void main(String[] args) {
		Injector injector = Guice.createInjector(new D11AdminModule());
		D11Admin d11Admin = injector.getInstance(D11Admin.class);
		d11Admin.start(args);
	}

	@Inject
	public D11Admin(MatchDayCommand matchDayCommand, ParseCommand parseCommand, SquadsCommand lineupsCommand) {
		this.jCommander = JCommander.newBuilder()
				.addCommand(matchDayCommand.getName(), matchDayCommand)
				.addCommand(parseCommand.getName(), parseCommand)
				.addCommand(lineupsCommand.getName(), lineupsCommand)
				.addObject(this)
				.build();
		this.jCommander.setProgramName("d11");

		this.commands.put(matchDayCommand.getName(), matchDayCommand);
		this.commands.put(parseCommand.getName(), parseCommand);
		this.commands.put(lineupsCommand.getName(), lineupsCommand);
	}

	public void start(String[] args) {
		try {
			this.jCommander.parse(args);
			D11Command d11Command = this.commands.get(jCommander.getParsedCommand());
			d11Command.execute();
		} catch (ParameterException e) {
			this.jCommander.usage();
		}
	}

}

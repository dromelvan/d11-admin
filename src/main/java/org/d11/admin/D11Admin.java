package org.d11.admin;

import java.util.HashMap;
import java.util.Map;

import org.d11.admin.command.D11Command;
import org.d11.admin.command.DaemonCommand;
import org.d11.admin.command.DateTimesCommand;
import org.d11.admin.command.GenerateD11FixturesCommand;
import org.d11.admin.command.MatchCommand;
import org.d11.admin.command.MatchDayCommand;
import org.d11.admin.command.ParseCommand;
import org.d11.admin.command.PhotosCommand;
import org.d11.admin.command.SquadsCommand;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

public class D11Admin extends D11AdminBaseObject {

	@Parameter(names = { "-help", "-h" }, description = "Display this help message.", help = true)
	private boolean help;

    @Parameter(names = "-password", description = "D11API password", password = true)
    private String password;

	private JCommander jCommander;
	private Map<String, D11Command> commands = new HashMap<String, D11Command>();

	public static void main(String[] args) {
		Injector injector = Guice.createInjector(new D11AdminModule());
		D11Admin d11Admin = injector.getInstance(D11Admin.class);
		d11Admin.execute(args);
	}

	@Inject
	public D11Admin(MatchDayCommand matchDayCommand,
	        DateTimesCommand dateTimesCommand,
	        MatchCommand matchCommand,
	        ParseCommand parseCommand,
	        SquadsCommand lineupsCommand,
	        PhotosCommand photosCommand,
	        GenerateD11FixturesCommand generateD11FixturesCommand,
	        DaemonCommand daemonCommand) {
		this.jCommander = JCommander.newBuilder()
				.addCommand(matchDayCommand.getName(), matchDayCommand)
				.addCommand(dateTimesCommand.getName(), dateTimesCommand)
				.addCommand(matchCommand.getName(), matchCommand)
				.addCommand(parseCommand.getName(), parseCommand)
				.addCommand(lineupsCommand.getName(), lineupsCommand)
				.addCommand(photosCommand.getName(), photosCommand)
				.addCommand(generateD11FixturesCommand.getName(), generateD11FixturesCommand)
				.addCommand(daemonCommand.getName(), daemonCommand)
				.addObject(this)
				.build();
		this.jCommander.setProgramName("d11");

		this.commands.put(matchDayCommand.getName(), matchDayCommand);
		this.commands.put(dateTimesCommand.getName(), dateTimesCommand);
		this.commands.put(matchCommand.getName(), matchCommand);
		this.commands.put(parseCommand.getName(), parseCommand);
		this.commands.put(lineupsCommand.getName(), lineupsCommand);
		this.commands.put(photosCommand.getName(), photosCommand);
		this.commands.put(generateD11FixturesCommand.getName(), generateD11FixturesCommand);
		this.commands.put(daemonCommand.getName(), daemonCommand);
	}

	public void execute(String[] args) {
		try {
		    if(args.length > 0) {
    			this.jCommander.parse(args);
  		        if(this.password != null) {
  		            setPassword(this.password);
		        }
    			D11Command d11Command = this.commands.get(jCommander.getParsedCommand());
    			d11Command.execute();
		    } else {
		        this.jCommander.usage();
		    }
		} catch (ParameterException e) {
			this.jCommander.usage();
		}
	}

}

package org.d11.admin.command;

import com.beust.jcommander.Parameters;

@Parameters(commandDescription = "Downloads all team squads and finds added/removed players.")
public class SquadsCommand extends D11Command {

	public SquadsCommand() {
		setName("squads");
	}

	@Override
	public void execute() {
		System.out.println("jey jey");
	}
}

package org.d11.admin.command;

import org.d11.admin.daemon.D11Daemon;

import com.beust.jcommander.Parameters;
import com.google.inject.Inject;

@Parameters(commandDescription = "Start the automagic updating daemon.")
public class DaemonCommand extends D11Command {

	private D11Daemon d11Daemon;

	@Inject
	public DaemonCommand(D11Daemon d11Daemon) {
		setName("daemon");
		this.d11Daemon = d11Daemon;
	}

	@Override
	public void execute() {
		this.d11Daemon.start();
		// try {
		// wait();
		// } catch(InterruptedException e) {}
	}

}

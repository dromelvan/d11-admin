package org.d11.admin.command;

import java.io.File;

import org.d11.admin.task.whoscored.WhoScoredParseMatchFileTask;

import com.beust.jcommander.Parameters;
import com.google.inject.Inject;
import com.google.inject.Provider;

@Parameters(commandDescription = "Select and parse file with stats for one or more matches.")
public class ParseCommand extends FileChooserCommand {

	private Provider<WhoScoredParseMatchFileTask> provider;

	@Inject
	public ParseCommand(Provider<WhoScoredParseMatchFileTask> provider) {
		setName("parse");
		this.provider = provider;
	}

	@Override
	public void execute() {
		WhoScoredParseMatchFileTask task = this.provider.get();
		for (File file : getFiles(new HtmlFileFilter())) {
			task.setFile(file);
			task.execute();
		}
	}

}

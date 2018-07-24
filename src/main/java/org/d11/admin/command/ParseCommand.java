package org.d11.admin.command;

import java.io.File;

import org.d11.admin.parse.whoscored.WhoScoredMatchParser;
import org.d11.admin.task.whoscored.WhoScoredParseMatchFileTask;

import com.beust.jcommander.Parameters;
import com.google.inject.Inject;
import com.google.inject.Provider;

@Parameters(commandDescription = "Select and parse file with stats for one or more matches.")
public class ParseCommand extends FileChooserCommand {

	private WhoScoredMatchParser parser;
	private Provider<WhoScoredParseMatchFileTask> provider;

	@Inject
	public ParseCommand(WhoScoredMatchParser parser, Provider<WhoScoredParseMatchFileTask> provider) {
		setName("parse");
		this.parser = parser;
		this.provider = provider;
	}

	public void execute() {
		WhoScoredParseMatchFileTask task = this.provider.get();
		for (File file : getFiles(new HtmlFileFilter())) {
			task.setFile(file);
			task.execute();
		}
	}

}

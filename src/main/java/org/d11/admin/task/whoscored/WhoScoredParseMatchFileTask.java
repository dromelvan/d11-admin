package org.d11.admin.task.whoscored;

import java.io.File;

import org.d11.admin.model.whoscored.WSMatch;
import org.d11.admin.parse.whoscored.WhoScoredMatchParser;
import org.d11.admin.task.D11Task;
import org.d11.admin.write.whoscored.WhoScoredMatchWriter;

import com.google.inject.Inject;

public class WhoScoredParseMatchFileTask extends D11Task<File> {

	@Inject
	private WhoScoredMatchParser parser;
	@Inject
	private WhoScoredMatchWriter writer;
	private File file;

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	@Override
	public boolean execute() {
		WSMatch wsMatch = parser.parse(file);

		if (wsMatch != null) {
			writer.setSeason(wsMatch.getSeason());
			writer.setMatchDayNumber(wsMatch.getMatchDayNumber());

			File jsonFile = writer.write(wsMatch);
			if (jsonFile != null) {
				setResult(jsonFile);
			}
		}

		return true;
	}

}

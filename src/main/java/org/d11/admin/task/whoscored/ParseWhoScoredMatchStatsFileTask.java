package org.d11.admin.task.whoscored;

import java.io.File;
import java.util.List;

import org.d11.admin.parser.whoscored.match.WhoScoredMatchParser;
import org.d11.admin.parser.whoscored.match.WhoScoredMatchParserObject;
import org.d11.admin.task.D11Task;
import org.d11.admin.writer.jaxb.WhoScoredMatchStatisticsJAXBFileWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.io.Files;
import com.google.inject.Inject;

public class ParseWhoScoredMatchStatsFileTask extends D11Task<File> {

	private File sourceFile;
	@Inject
	private WhoScoredMatchParser parser;
	private final static Logger logger = LoggerFactory.getLogger(ParseWhoScoredMatchStatsFileTask.class);

	public ParseWhoScoredMatchStatsFileTask() {
	}

	public ParseWhoScoredMatchStatsFileTask(File sourceFile) {
		this.sourceFile = sourceFile;
	}

	public WhoScoredMatchParser getParser() {
		return parser;
	}

	public void setParser(WhoScoredMatchParser parser) {
		this.parser = parser;
	}

	public File getSourceFile() {
		return sourceFile;
	}

	public void setSourceFile(File sourceFile) {
		this.sourceFile = sourceFile;
	}

	@Override
	public boolean execute() {
		try {
			logger.info("Parsing file {}.", getSourceFile().getAbsolutePath());
			getParser().setFile(getSourceFile());
			List<WhoScoredMatchParserObject> whoScoredMatchParserObjects = getParser().parse();
			if (!whoScoredMatchParserObjects.isEmpty()) {
				WhoScoredMatchParserObject whoScoredMatchParserObject = whoScoredMatchParserObjects.iterator().next();
				String targetFileName = String.format("%s-%s", whoScoredMatchParserObject.getHomeTeam().getName(), whoScoredMatchParserObject.getAwayTeam().getName());

				File targetFile = new File(getSourceFile().getParentFile(), targetFileName + ".xml");
				WhoScoredMatchStatisticsJAXBFileWriter writer = new WhoScoredMatchStatisticsJAXBFileWriter();
				writer.setFile(targetFile);
				writer.write(whoScoredMatchParserObjects);

				File newSourceFile = new File(getSourceFile().getParentFile(), targetFileName + ".html");
				if (!newSourceFile.equals(getSourceFile())) {
					Files.move(getSourceFile(), newSourceFile);
				}
				setResult(targetFile);
				return true;
			} else {
				logger.error("File {} did not contain match stats.", getSourceFile().getAbsolutePath());
			}
		} catch (Exception e) {
			logger.error("Failed when parsing match stats file.", e);
		}
		return false;
	}
}

package org.d11.admin.task.whoscored;

import java.io.File;
import java.util.Set;

import org.d11.admin.parser.whoscored.player.PlayerInformationParserObject;
import org.d11.admin.parser.whoscored.player.WhoScoredPlayerParser;
import org.d11.admin.task.D11Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

public class ParseWhoScoredPlayerFileTask extends D11Task<PlayerInformationParserObject>{

    private File sourceFile;
    @Inject
    private WhoScoredPlayerParser parser;
    private final static Logger logger = LoggerFactory.getLogger(ParseWhoScoredPlayerFileTask.class);

    public ParseWhoScoredPlayerFileTask() {}

    public ParseWhoScoredPlayerFileTask(File sourceFile) {
        this.sourceFile = sourceFile;
    }

    public WhoScoredPlayerParser getParser() {
        return parser;
    }

    public void setParser(WhoScoredPlayerParser parser) {
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
            Set<PlayerInformationParserObject> playerInformationParserObjects = getParser().parse();
            if(!playerInformationParserObjects.isEmpty()) {
                for(PlayerInformationParserObject playerInformationParserObject : playerInformationParserObjects) {
                    setResult(playerInformationParserObject);
                }
            }
        } catch(Exception e) {
            logger.error("Failed when parsing player file.", e);
        }

        return false;
    }

}

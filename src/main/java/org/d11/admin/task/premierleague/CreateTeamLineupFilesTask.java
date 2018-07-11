package org.d11.admin.task.premierleague;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.d11.admin.parser.premierleague.TeamParserObject;
import org.d11.admin.task.D11Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

public class CreateTeamLineupFilesTask extends D11Task<List<File>> {

    @Inject
    private DownloadPremierLeagueTableTask downloadTask;
    @Inject
    private ParsePremierLeagueTableFileTask parseTask;
    @Inject
    private CreateTeamLineupFileTask createTask;
    private final static Logger logger = LoggerFactory.getLogger(CreateTeamLineupFilesTask.class);

    @Override
    public boolean execute() {
        if (downloadTask.execute()) {
            parseTask.setSourceFile(downloadTask.getResult());
            if (parseTask.execute()) {
                List<File> files = new ArrayList<File>();
                List<TeamParserObject> teamParserObjects = parseTask.getResult();
                for(TeamParserObject teamParserObject : teamParserObjects) {
                    createTask.setTeamParserObject(teamParserObject);
                    if(createTask.execute()) {
                        File file = createTask.getResult();
                        files.add(file);
                    } else {
                        return false;
                    }
                }
                setResult(files);
                return true;
            }
        }
        return false;
    }

}

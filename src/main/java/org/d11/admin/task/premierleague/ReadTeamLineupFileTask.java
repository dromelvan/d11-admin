package org.d11.admin.task.premierleague;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import org.d11.admin.parser.premierleague.TeamLineup;
import org.d11.admin.task.D11Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

public class ReadTeamLineupFileTask extends D11Task<TeamLineup> {

    private File file;
    private final static Logger logger = LoggerFactory.getLogger(ReadTeamLineupFileTask.class);

    public void setFile(File file) {
        this.file = file;
    }

    @Override
    public boolean execute() {
        try {
            Gson gson = new Gson();
            JsonReader jsonReader = new JsonReader(new FileReader(this.file));
            TeamLineup teamLineup = gson.fromJson(jsonReader, new TypeToken<TeamLineup>(){}.getType());
            setResult(teamLineup);
            return true;
        } catch(FileNotFoundException e) {
            logger.error("Could not find file {}.", this.file);
        }
        return false;
    }

}

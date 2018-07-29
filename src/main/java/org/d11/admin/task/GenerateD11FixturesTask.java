package org.d11.admin.task;

import java.io.File;
import java.util.List;

import org.d11.admin.model.D11Match;
import org.d11.admin.model.Season;
import org.d11.admin.util.D11FixtureGenerator;
import org.d11.admin.write.D11FixturesWriter;

import com.google.inject.Inject;

public class GenerateD11FixturesTask extends D11Task<File> {

    @Inject
    private D11FixturesWriter writer;

    @Override
    public boolean execute() {
        Season season = getD11Api().getCurrentSeason();
        D11FixtureGenerator generator = new D11FixtureGenerator(season);
        List<D11Match> d11Matches = generator.generate();
        if(d11Matches != null && !d11Matches.isEmpty()) {
            writer.setSeasonId(season.getId());
            writer.write(d11Matches);
            setResult(writer.getFile());
            return true;
        }
        return false;
    }

}

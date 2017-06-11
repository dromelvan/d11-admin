package org.d11.admin.reader.jaxb;

import java.util.ArrayList;
import java.util.List;

import org.d11.admin.jaxb.match.Match;

public class WhoScoredMatchStatisticsJAXBFileReader extends JAXBFileReader<Match, Match> {

    public WhoScoredMatchStatisticsJAXBFileReader() {
        super(Match.class);
    }

    @Override
    protected List<Match> buildResult(Match rootElement) {
        List<Match> matches = new ArrayList<Match>();
        matches.add(rootElement);
        return matches;
    }

}

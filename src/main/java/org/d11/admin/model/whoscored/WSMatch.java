package org.d11.admin.model.whoscored;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.d11.admin.model.Match;
import org.d11.admin.parse.whoscored.WhoScoredMatchJavaScriptVariables;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WSMatch extends Match {

    private final static Logger logger = LoggerFactory.getLogger(WSMatch.class);

    public WSMatch(Map<String, Object> match) {
        setWhoScoredId((Integer) match.get(WhoScoredMatchJavaScriptVariables.MATCH_ID));

        Map matchCentreData = (Map) match.get(WhoScoredMatchJavaScriptVariables.MATCH_CENTRE_DATA);
        Pattern startTimePattern = Pattern.compile("(\\d{4}-\\d{2}-\\d{2}T\\d{1,2}:\\d{1,2}:\\d{1,2}).*");
        Matcher startTimeMatcher = startTimePattern.matcher((String) matchCentreData.get(WhoScoredMatchJavaScriptVariables.START_TIME));
        if (startTimeMatcher.matches()) {
            DateTimeFormatter dateTimeFormat = DateTimeFormat.forPattern("YYYY-MM-dd'T'HH:mm:ss");
            LocalDateTime dateTime = LocalDateTime.parse(startTimeMatcher.group(1), dateTimeFormat);
            setDatetime(dateTime.plusHours(2));
        } else {
            logger.warn("Could not parse start time from input {}.", matchCentreData.get(WhoScoredMatchJavaScriptVariables.START_TIME));
        }

        setElapsed((String) matchCentreData.get(WhoScoredMatchJavaScriptVariables.ELAPSED));

        WSTeam homeTeam = new WSTeam((Map) matchCentreData.get(WhoScoredMatchJavaScriptVariables.HOME_TEAM));
        WSTeam awayTeam = new WSTeam((Map) matchCentreData.get(WhoScoredMatchJavaScriptVariables.AWAY_TEAM));

//        homeTeamParserObject.addOwnGoals(awayTeamParserObject.getOwnGoals());
//        awayTeamParserObject.addOwnGoals(homeTeamParserObject.getOwnGoals());

        setHomeTeam(homeTeam);
        setAwayTeam(awayTeam);
    }

}

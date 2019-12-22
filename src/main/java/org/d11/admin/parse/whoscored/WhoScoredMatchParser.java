package org.d11.admin.parse.whoscored;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.d11.admin.model.Team;
import org.d11.admin.model.whoscored.WSMatch;
import org.d11.admin.parse.jsoup.JSoupJavaScriptParser;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class WhoScoredMatchParser extends JSoupJavaScriptParser<WSMatch, WhoScoredMatchJavaScriptVariables> {

	private final static Logger logger = LoggerFactory.getLogger(WhoScoredMatchParser.class);

	@Override
	protected WSMatch doParse() {
		WSMatch wsMatch = null;

		try {
			WhoScoredMatchJavaScriptVariables whoScoredMatchJavaScriptVariables = getJavaScriptVariables();
			wsMatch = new WSMatch(whoScoredMatchJavaScriptVariables);
			if(wsMatch.getElapsed() != null && wsMatch.getElapsed().equals("FT")) {
			    // Check that the game is really finished.
			    Elements elements = getDocument().getElementsByClass("finished");
			    if(elements.isEmpty()) {
			        wsMatch.setElapsed("90");
			    }
			}
		} catch (NullPointerException e) {
			Pattern matchIdPattern = Pattern.compile(".*ws_matchID = '(\\d*)'.*", Pattern.DOTALL);
			Pattern matchHeaderPattern = Pattern.compile(".*matchHeader.load\\(\\[(\\d*),(\\d*),'(.*)','(.*)','(.*)','.*',\\d*,,,,,,.*", Pattern.DOTALL);

			wsMatch = new WSMatch();

			for (Element element : getDocument().getElementsByTag("script")) {
				Matcher matchIdMatcher = matchIdPattern.matcher(element.toString());
				if (matchIdMatcher.matches()) {
					wsMatch.setWhoScoredId(Integer.parseInt(matchIdMatcher.group(1)));
				}
				Matcher matchHeaderMatcher = matchHeaderPattern.matcher(element.toString());
				if (matchHeaderMatcher.matches()) {
					wsMatch.setHomeTeam(new Team(0, Integer.parseInt(matchHeaderMatcher.group(1)), matchHeaderMatcher.group(3)));
					wsMatch.setAwayTeam(new Team(0, Integer.parseInt(matchHeaderMatcher.group(2)), matchHeaderMatcher.group(4)));

					DateTimeFormatter dateTimeFormat = DateTimeFormat.forPattern("dd/MM/YYYY HH:mm:ss");
					LocalDateTime dateTime = LocalDateTime.parse(matchHeaderMatcher.group(5), dateTimeFormat).plusHours(2);

					wsMatch.setDatetime(dateTime.toString(WSMatch.dateTimeFormatter));
					wsMatch.setElapsed("N/A");
				}
			}
		}

		// This can happen for example when a match is postponed
		if(wsMatch.getHomeTeam() == null || wsMatch.getAwayTeam() == null) {
			Pattern teamPattern = Pattern.compile("(.*)-(.*) - .*", Pattern.DOTALL);
			Matcher teamMatcher = teamPattern.matcher(getFile().getName());
			if(teamMatcher.matches()) {
				wsMatch.setHomeTeam(new Team(0, 0, teamMatcher.group(1)));
				wsMatch.setAwayTeam(new Team(0, 0, teamMatcher.group(2)));
			}	
			wsMatch.setElapsed("N/A");
		}
		
		try {
			File matchDayDirectory = getFile().getParentFile();
			File seasonDirectory = matchDayDirectory.getParentFile();

			int matchDayNumber = Integer.parseInt(matchDayDirectory.getName());
			String season = seasonDirectory.getName();

			wsMatch.setMatchDayNumber(matchDayNumber);
			wsMatch.setSeason(season);
		} catch (Exception e) {
			logger.error("Could not parse season and/or match day number.", e);
		}

		return wsMatch;
	}

}

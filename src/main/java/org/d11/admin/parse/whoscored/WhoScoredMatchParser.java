package org.d11.admin.parse.whoscored;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.d11.admin.model.Match;
import org.d11.admin.model.Team;
import org.d11.admin.parse.jsoup.JSoupJavaScriptParser;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.jsoup.nodes.Element;

public class WhoScoredMatchParser extends JSoupJavaScriptParser<Match, WhoScoredMatchJavaScriptVariables> {

	@Override
	protected Match doParse() {
		Match match = new Match();
		try {
			WhoScoredMatchJavaScriptVariables whoScoredMatchJavaScriptVariables = getJavaScriptVariables();
			System.out.println("woot");
		} catch (NullPointerException e) {
			Pattern matchIdPattern = Pattern.compile(".*ws_matchID = '(\\d*)'.*", Pattern.DOTALL);
			Pattern matchHeaderPattern = Pattern.compile(".*matchHeader.load\\(\\[(\\d*),(\\d*),'(.*)','(.*)','(.*)','.*',\\d*,,,,,,.*", Pattern.DOTALL);

			for (Element element : getDocument().getElementsByTag("script")) {
				Matcher matchIdMatcher = matchIdPattern.matcher(element.toString());
				if (matchIdMatcher.matches()) {
					match.setWhoScoredId(Integer.parseInt(matchIdMatcher.group(1)));
				}
				Matcher matchHeaderMatcher = matchHeaderPattern.matcher(element.toString());
				if (matchHeaderMatcher.matches()) {
					match.setHomeTeam(new Team(0, Integer.parseInt(matchHeaderMatcher.group(1)), matchHeaderMatcher.group(3)));
					match.setAwayTeam(new Team(0, Integer.parseInt(matchHeaderMatcher.group(2)), matchHeaderMatcher.group(4)));

					DateTimeFormatter dateTimeFormat = DateTimeFormat.forPattern("dd/MM/YYYY HH:mm:ss");
					LocalDateTime dateTime = LocalDateTime.parse(matchHeaderMatcher.group(5), dateTimeFormat).plusHours(2);

					match.setDatetime(dateTime);
					match.setElapsed("N/A");
				}
			}
		}
		return match;
	}

}

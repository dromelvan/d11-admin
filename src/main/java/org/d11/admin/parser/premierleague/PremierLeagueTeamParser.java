package org.d11.admin.parser.premierleague;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.d11.admin.parser.javascript.JavaScriptVariables;
import org.d11.admin.parser.jsoup.JSoupFileParser;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PremierLeagueTeamParser extends JSoupFileParser<PlayerParserObject, JavaScriptVariables> {

	private final static Logger logger = LoggerFactory.getLogger(PremierLeagueTeamParser.class);
	private final static Pattern pattern = Pattern.compile("\\/players\\/(\\d*)\\/(.*)\\/overview");

	@Override
	public List<PlayerParserObject> parse() {
		List<PlayerParserObject> playerParserObjects = new ArrayList<PlayerParserObject>();

		for (Element a : getDocument().getElementsByClass("playerOverviewCard")) {
			String href = a.attr("href");
			Matcher matcher = pattern.matcher(href);
			if (matcher.matches()) {
				PlayerParserObject playerParserObject = new PlayerParserObject(matcher.group(1), matcher.group(2));
				playerParserObjects.add(playerParserObject);
			} else {
				logger.error("Href {} does not match player link pattern.", href);
			}
		}
		return playerParserObjects;
	}

}

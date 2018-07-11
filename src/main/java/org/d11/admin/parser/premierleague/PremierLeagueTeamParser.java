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
	private final static Pattern pattern = Pattern.compile("\\/players\\/(\\d*)\\/.*\\/overview");

	@Override
	public List<PlayerParserObject> parse() {
		List<PlayerParserObject> playerParserObjects = new ArrayList<PlayerParserObject>();

		String team = getDocument().getElementsByTag("h1").first().text();

		for (Element a : getDocument().getElementsByClass("playerOverviewCard")) {
			String href = a.attr("href");
			Matcher matcher = pattern.matcher(href);
			if (matcher.matches()) {
			    String id = matcher.group(1);
			    String name = a.getElementsByTag("h4").first().text();
			    int number = 0;
			    try {
			        number = Integer.parseInt(a.getElementsByClass("number").first().text());
			    } catch(NumberFormatException e) {}
			    String position = a.getElementsByClass("position").first().text();
			    String nationality = a.getElementsByClass("playerCountry").first().text();
			    String imageId = a.getElementsByClass("statCardImg").first().attr("data-player");

				PlayerParserObject playerParserObject = new PlayerParserObject(id, name, number, team, position, nationality, imageId);
				playerParserObjects.add(playerParserObject);
			} else {
				logger.error("Href {} does not match player link pattern.", href);
			}
		}
		return playerParserObjects;
	}

}

package org.d11.admin.parse.premierleague;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.d11.admin.model.premierleague.PLPlayer;
import org.d11.admin.parse.jsoup.JSoupParser;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PremierLeagueTeamParser extends JSoupParser<List<PLPlayer>> {

	private final static Logger logger = LoggerFactory.getLogger(PremierLeagueTeamParser.class);
	private final static Pattern pattern = Pattern.compile("\\/players\\/(\\d*)\\/.*\\/overview");

	@Override
	protected List<PLPlayer> doParse() {
		List<PLPlayer> players = new ArrayList<PLPlayer>();

		String team = getDocument().getElementsByTag("h1").first().text();

		for (Element a : getDocument().getElementsByClass("playerOverviewCard")) {
			String href = a.attr("href");
			Matcher matcher = pattern.matcher(href);
			if (matcher.matches()) {
				int id = Integer.parseInt(matcher.group(1));
				String name = a.getElementsByTag("h4").first().text();
				int number = 0;
				try {
					number = Integer.parseInt(a.getElementsByClass("number").first().text());
				} catch (NumberFormatException e) {
				}
				String position = a.getElementsByClass("position").first().text();
				String nationality = a.getElementsByClass("playerCountry").first().text();
				String imageId = a.getElementsByClass("statCardImg").first().attr("data-player");

				PLPlayer player = new PLPlayer();
				player.setId(id);
				player.setPremierLeagueId(id);
				player.setName(name);
				player.setNumber(number);
				player.setTeam(team);
				player.setPosition(position);
				player.setNationality(nationality);
				player.setImageId(imageId);

				players.add(player);
			} else {
				logger.error("Href {} does not match player link pattern.", href);
			}
		}
		return players;
	}

}

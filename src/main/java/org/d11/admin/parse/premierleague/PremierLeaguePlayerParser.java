package org.d11.admin.parse.premierleague;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.d11.admin.model.premierleague.PLPlayer;
import org.d11.admin.parse.jsoup.JSoupParser;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.jsoup.nodes.Element;

public class PremierLeaguePlayerParser extends JSoupParser<PLPlayer> {

	private final static Pattern datePattern = Pattern.compile("\\d{2}\\/\\d{2}\\/\\d{4}");
	private final static Pattern heightPattern = Pattern.compile("(\\d{3})cm");

	@Override
	protected PLPlayer doParse() {
		Element playerContainer = getDocument().getElementsByClass("playerContainer").first();
		int number = Integer.parseInt(playerContainer.getElementsByClass("number").first().text());
		String name = playerContainer.getElementsByClass("name").first().text();

		Element img = playerContainer.getElementsByClass("img").first();
		String imageId = img.attr("data-player");

		Element playerOverviewAside = getDocument().getElementsByClass("playerOverviewAside").first();
		Element playerIntro = playerOverviewAside.getElementsByClass("playerIntro").first();
		String team = playerIntro.getElementsByClass("info").first().text();
		String position = playerIntro.getElementsByClass("info").last().text();
		Element playerSidebarTable = playerOverviewAside.getElementsByClass("playerSidebarTable").first();
		int id = Integer.parseInt(playerSidebarTable.getElementsByAttribute("data-player").attr("data-player"));

		Element personalList = getDocument().getElementsByClass("personalLists").first();
		String nationality = personalList.getElementsByClass("playerCountry").first().text();

		LocalDate dateOfBirth = null;
		int height = 0;
		for (Element info : personalList.getElementsByClass("info")) {
			Matcher dateMatcher = datePattern.matcher(info.text());
			if (dateMatcher.matches()) {
				dateOfBirth = LocalDate.parse(info.text(), DateTimeFormat.forPattern("dd/MM/yyyy"));
			}
			Matcher heightMatcher = heightPattern.matcher(info.text());
			if (heightMatcher.matches()) {
				height = Integer.parseInt(heightMatcher.group(1));
			}
		}

		PLPlayer player = new PLPlayer();
		player.setPremierLeagueId(id);
		player.setName(name);
		player.setNumber(number);
		player.setTeam(team);
		player.setPosition(position);
		player.setNationality(nationality);
		player.setDateOfBirth(dateOfBirth.toString("dd-MM-YYYY"));
		player.setHeight(height);
		player.setImageId(imageId);

		return player;
	}

}

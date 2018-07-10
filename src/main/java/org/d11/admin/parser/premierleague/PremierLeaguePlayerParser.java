package org.d11.admin.parser.premierleague;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.d11.admin.parser.javascript.JavaScriptVariables;
import org.d11.admin.parser.jsoup.JSoupFileParser;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.jsoup.nodes.Element;

public class PremierLeaguePlayerParser extends JSoupFileParser<PlayerInformationParserObject, JavaScriptVariables> {

	private final static Pattern datePattern = Pattern.compile("\\d{2}\\/\\d{2}\\/\\d{4}");
	private final static Pattern heightPattern = Pattern.compile("(\\d{3})cm");

	@Override
	public List<PlayerInformationParserObject> parse() {
		PlayerInformationParserObject playerInformationParserObject = new PlayerInformationParserObject();

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
		String id = playerSidebarTable.getElementsByAttribute("data-player").attr("data-player");

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

		playerInformationParserObject.setId(id);
		playerInformationParserObject.setName(name);
		playerInformationParserObject.setNumber(number);
		playerInformationParserObject.setTeam(team);
		playerInformationParserObject.setPosition(position);
		playerInformationParserObject.setNationality(nationality);
		playerInformationParserObject.setDateOfBirth(dateOfBirth);
		playerInformationParserObject.setHeight(height);
		playerInformationParserObject.setImageId(imageId);

		return Arrays.asList(playerInformationParserObject);
	}
}

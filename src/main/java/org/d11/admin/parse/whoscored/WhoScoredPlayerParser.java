package org.d11.admin.parse.whoscored;

import org.d11.admin.model.Player;
import org.d11.admin.parse.jsoup.JSoupParser;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.jsoup.nodes.Element;

public class WhoScoredPlayerParser extends JSoupParser<Player> {

	public enum InfoBlock {
		NAME,
		CURRENT_TEAM,
		SHIRT_NUMBER,
		POSITIONS,
		AGE,
		HEIGHT,
		WEIGHT,
		NATIONALITY;
	}

	@Override
	protected Player doParse() {
		Player player = new Player();

		for (Element linkElement : getDocument().getElementsByTag("link")) {
			if (linkElement.attr("rel") != null && linkElement.attr("rel").equals("canonical")) {
				String href = linkElement.attr("href");
				player.setWhoScoredId(Integer.parseInt(href.replaceAll("[\\D]", "")));
			}
		}
		for (Element dlElement : getDocument().getElementsByClass("player-info-block")) {
			String infoBlock = dlElement.getElementsByTag("dt").first().text();
			infoBlock = infoBlock.toUpperCase().replaceAll(" ", "_").replaceAll(":", "");
			String value = dlElement.getElementsByTag("dd").first().text();

			if (infoBlock.equals(InfoBlock.NAME.toString())) {
				player.setName(value);
			} else if (infoBlock.equals(InfoBlock.CURRENT_TEAM.toString())) {
				player.setTeam(value);
			} else if (infoBlock.equals(InfoBlock.SHIRT_NUMBER.toString())) {
				player.setNumber(Integer.parseInt(value));
			} else if (infoBlock.equals(InfoBlock.POSITIONS.toString())) {
				// TODO
			} else if (infoBlock.equals(InfoBlock.AGE.toString())) {
				value = value.substring(value.indexOf("(") + 1, value.indexOf(")")).trim();
				player.setDateOfBirth(LocalDate.parse(value, DateTimeFormat.forPattern("dd-MM-YYYY")));
			} else if (infoBlock.equals(InfoBlock.HEIGHT.toString())) {
				player.setHeight(Integer.parseInt(value.replace("cm", "")));
			} else if (infoBlock.equals(InfoBlock.WEIGHT.toString())) {
				player.setWeight(Integer.parseInt(value.replace("kg", "")));
			} else if (infoBlock.equals(InfoBlock.NATIONALITY.toString())) {
				player.setNationality(value);
			}
		}
		return player;
	}

}

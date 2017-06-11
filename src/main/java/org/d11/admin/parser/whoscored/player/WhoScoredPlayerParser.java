package org.d11.admin.parser.whoscored.player;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.d11.admin.parser.FileParser;
import org.d11.admin.parser.javascript.JavaScriptVariables;
import org.d11.admin.parser.jsoup.JSoupDocumentParser;
import org.d11.admin.reader.jsoup.JSoupFileReader;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WhoScoredPlayerParser extends JSoupDocumentParser<PlayerInformationParserObject, JavaScriptVariables> implements FileParser<PlayerInformationParserObject> {

    @Override
    public void setFile(File file) {
        JSoupFileReader jSoupFileReader = new JSoupFileReader(file);
        try {
            setDocument(jSoupFileReader.read());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

	@Override
	public Set<PlayerInformationParserObject> parse() {

		Set<PlayerInformationParserObject> playerInformationParserObjects = new HashSet<PlayerInformationParserObject>();

		Map<String, String> valueMap = new HashMap<String, String>();

		Elements playerProfileElements = getDocument().select("div#player-profile");
		Elements playerInfoBlockElements = playerProfileElements.select("dl.player-info-block");

		for (Element playerInfoBlockElement : playerInfoBlockElements) {
		    String key = playerInfoBlockElement.select("dt").text();
			if(!playerInfoBlockElement.select("dt").text().equals("Positions:")) {
			    valueMap.put(key, playerInfoBlockElement.select("dd").text());
			} else {
			    StringBuilder stringBuilder = new StringBuilder();
			    for(Element positionElement : playerInfoBlockElement.select("dd").first().getElementsByTag("li")) {
			        if(stringBuilder.length() > 0) {
			            stringBuilder.append(";");
			        }
			        stringBuilder.append(positionElement.text());
			    }
			    valueMap.put(key, stringBuilder.toString());
			}
		}

		PlayerInformationParserObject playerInformationParserObject = new PlayerInformationParserObject();
		playerInformationParserObject.setName(valueMap.get("Name:"));
		playerInformationParserObject.setNationality(valueMap.get("Nationality:"));

		String age = valueMap.get("Age:");
		if (age != null) {
			Pattern agePattern = Pattern.compile("(.*) years old \\((.*)-(.*)-(.*)\\)");
			Matcher ageMatcher = agePattern.matcher(age);
			if (ageMatcher.matches()) {
				playerInformationParserObject.setDateOfBirth(ageMatcher.group(4).trim() + "-" + ageMatcher.group(3).trim() + "-" + ageMatcher.group(2).trim());
			}
		}

		String height = valueMap.get("Height:");
		if (height != null) {
			Pattern heightPattern = Pattern.compile("(.*)cm");
			Matcher heightMatcher = heightPattern.matcher(height);
			if (heightMatcher.matches()) {
				playerInformationParserObject.setHeight(Integer.parseInt(heightMatcher.group(1)));
			}
		}

		String weight = valueMap.get("Weight:");
		if (weight != null) {
			Pattern weightPattern = Pattern.compile("(.*)kg");
			Matcher weightMatcher = weightPattern.matcher(weight);
			if (weightMatcher.matches()) {
				playerInformationParserObject.setWeight(Integer.parseInt(weightMatcher.group(1)));
			}
		}

		String shirtNumber = valueMap.get("Shirt Number:");
		if (shirtNumber != null) {
			playerInformationParserObject.setShirtNumber(Integer.parseInt(shirtNumber));
		}

		for(String position : valueMap.get("Positions:").split(";")) {
		    playerInformationParserObject.addPosition(position);
		}
		playerInformationParserObject.setFullName(valueMap.get("Full Name:"));

		playerInformationParserObjects.add(playerInformationParserObject);
		return playerInformationParserObjects;
	}
}

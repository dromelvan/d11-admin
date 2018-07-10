package org.d11.admin.parser.whoscored.match;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.d11.admin.parser.FileParser;
import org.d11.admin.parser.jsoup.JSoupDocumentParser;
import org.d11.admin.reader.jsoup.JSoupFileReader;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.jsoup.nodes.Element;

public class WhoScoredMatchParser extends JSoupDocumentParser<WhoScoredMatchParserObject, WhoScoredMatchJavaScriptVariables> implements FileParser<WhoScoredMatchParserObject> {

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
	public List<WhoScoredMatchParserObject> parse() {
		List<WhoScoredMatchParserObject> matchParserObjects = new ArrayList<WhoScoredMatchParserObject>();

		try {
			WhoScoredMatchJavaScriptVariables whoScoredMatchEventsJavaScriptVariables = getJavaScriptVariables();
			WhoScoredMatchParserObject matchParserObject = whoScoredMatchEventsJavaScriptVariables.getMatchParserObject();
			matchParserObjects.add(matchParserObject);
		} catch (NullPointerException e) {
			// The match is still pending.
			WhoScoredMatchParserObject matchParserObject = new WhoScoredMatchParserObject();

			Pattern matchIdPattern = Pattern.compile(".*ws_matchID = '(\\d*)'.*", Pattern.DOTALL);
			Pattern matchHeaderPattern = Pattern.compile(".*matchHeader.load\\(\\[(\\d*),(\\d*),'(.*)','(.*)','(.*)','.*',\\d*,,,,,,.*", Pattern.DOTALL);
			for (Element element : getDocument().getElementsByTag("script")) {
				Matcher matchIdMatcher = matchIdPattern.matcher(element.toString());
				if (matchIdMatcher.matches()) {
					matchParserObject.setWhoScoredId(Integer.parseInt(matchIdMatcher.group(1)));
				}
				Matcher matchHeaderMatcher = matchHeaderPattern.matcher(element.toString());
				if (matchHeaderMatcher.matches()) {
					matchParserObject.setHomeTeam(new WhoScoredTeamParserObject(matchHeaderMatcher.group(3), Integer.parseInt(matchHeaderMatcher.group(1))));
					matchParserObject.setAwayTeam(new WhoScoredTeamParserObject(matchHeaderMatcher.group(4), Integer.parseInt(matchHeaderMatcher.group(2))));

					DateTimeFormatter dateTimeFormat = DateTimeFormat.forPattern("dd/MM/YYYY HH:mm:ss");
					DateTime dateTime = DateTime.parse(matchHeaderMatcher.group(5), dateTimeFormat).plusHours(2);

					matchParserObject.setDateTime(dateTime);
					matchParserObject.setTimeElapsed("N/A");
				}
			}
			matchParserObjects.add(matchParserObject);
		}
		return matchParserObjects;
	}
}

package org.d11.admin.parser.premierleague;

import java.util.ArrayList;
import java.util.List;

import org.d11.admin.parser.javascript.JavaScriptVariables;
import org.d11.admin.parser.jsoup.JSoupFileParser;
import org.jsoup.nodes.Element;

public class PremierLeagueTableParser extends JSoupFileParser<TeamParserObject, JavaScriptVariables> {

	@Override
	public List<TeamParserObject> parse() {
		List<TeamParserObject> teamParserObjects = new ArrayList<TeamParserObject>();

		for (Element tableBodyContainer : getDocument().getElementsByClass("tableBodyContainer")) {
			// The elements we're looking for should look like this:
			// <tr class="tableMid" data-compseason="210" data-filtered-entry-size="20" data-filtered-table-row="127" data-filtered-table-row-name="AFC Bournemouth" data-filtered-table-row-opta="t91"
			// data-filtered-table-row-abbr="127">
			for (Element trElement : tableBodyContainer.getElementsByTag("tr")) {
				String id = trElement.attr("data-filtered-table-row");
				String name = trElement.attr("data-filtered-table-row-name");
				if (!id.isEmpty() && !name.isEmpty()) {
					TeamParserObject teamParserObject = new TeamParserObject(id, name);
					teamParserObjects.add(teamParserObject);
				}
			}
			// We're only interested in the first table since that's the one that contains the Premier League table.
			break;
		}

		return teamParserObjects;
	}

}

package org.d11.admin.parse.premierleague;

import java.util.ArrayList;
import java.util.List;

import org.d11.admin.model.Team;
import org.d11.admin.parse.jsoup.JSoupParser;
import org.jsoup.nodes.Element;

public class PremierLeagueTableParser extends JSoupParser<List<Team>> {

	@Override
	protected List<Team> doParse() {
		List<Team> teams = new ArrayList<Team>();

		for (Element tableBodyContainer : getDocument().getElementsByClass("tableBodyContainer")) {
			// The elements we're looking for should look like this:
			// <tr class="tableMid" data-compseason="210" data-filtered-entry-size="20" data-filtered-table-row="127" data-filtered-table-row-name="AFC Bournemouth" data-filtered-table-row-opta="t91"
			// data-filtered-table-row-abbr="127">
			for (Element trElement : tableBodyContainer.getElementsByTag("tr")) {
				try {
					int id = Integer.parseInt(trElement.attr("data-filtered-table-row"));
					String name = trElement.attr("data-filtered-table-row-name");
					if (id > 0 && !name.isEmpty()) {
						Team team = new Team(id, name);
						teams.add(team);
					}
				} catch (NumberFormatException e) {
				}
			}
			// We're only interested in the first table since that's the one that contains the Premier League table.
			break;
		}

		return teams;
	}

}

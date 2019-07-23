package org.d11.admin.parse.skysports;

import java.util.ArrayList;
import java.util.List;

import org.d11.admin.model.skysports.SSFixture;
import org.d11.admin.parse.jsoup.JSoupParser;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SkySportsFixturesParser extends JSoupParser<List<SSFixture>> {

	private final static Logger logger = LoggerFactory.getLogger(SkySportsFixturesParser.class);
	
	@Override
	protected List<SSFixture> doParse() {
		List<SSFixture> fixtures = new ArrayList<SSFixture>();
		
		Element fixresBody = getDocument().getElementsByClass("fixres__body").first();
		
		for(Element element : fixresBody.children()) {
			if(element.hasClass("fixres__header2")) {
				//System.out.println(element);
			}
			if(element.tagName().equals("script")) {
				System.out.println(element.hasAttr("data-role"));
			}
		}
		return fixtures;
	}

}

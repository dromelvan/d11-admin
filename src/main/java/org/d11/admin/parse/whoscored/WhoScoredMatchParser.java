package org.d11.admin.parse.whoscored;

import org.d11.admin.model.Match;
import org.d11.admin.parse.jsoup.JSoupJavaScriptParser;

public class WhoScoredMatchParser extends JSoupJavaScriptParser<Match, WhoScoredMatchJavaScriptVariables> {

    @Override
    protected Match doParse() {
        WhoScoredMatchJavaScriptVariables whoScoredMatchJavaScriptVariables = getJavaScriptVariables();
        System.out.println(whoScoredMatchJavaScriptVariables);
        return null;
    }

}

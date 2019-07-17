package org.d11.admin;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.d11.admin.download.skysports.SkySportsFixturesDownloader;
import org.d11.admin.model.skysports.SSFixture;
import org.d11.admin.parse.skysports.SkySportsFixturesParser;
import org.d11.api.v1.D11API;
import org.jukito.JukitoModule;
import org.jukito.JukitoRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.google.common.io.Files;
import com.google.inject.Singleton;

@RunWith(JukitoRunner.class)
public class SkySportsTest {

	public static class SkySportsTestModule extends JukitoModule {
	    @Override
	    protected void configureTest() {
	        bind(D11API.class).in(Singleton.class);
	    }

	}
	
//	@Test
	public void downloadSkySportsFixtures(SkySportsFixturesDownloader downloader) throws Exception {
		downloader.setSeason("2019-2020");
		File htmlFile = downloader.download();
		if (htmlFile != null) {
			System.out.println(htmlFile);
		}
	}
	
	//@Test
	public void parseSkySportsFixtures(SkySportsFixturesParser parser) {
	    File file = new File("src/test/resources/2019-2020/premier-league-fixtures-2019-2020.html");
	    List<SSFixture> fixtures = parser.parse(file);
	    for(SSFixture fixture : fixtures) {
	    	System.out.println(fixture);
	    }
	}
	
}

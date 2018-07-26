package org.d11.admin.command;

import org.d11.admin.task.premierleague.DownloadPlayerImagesTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beust.jcommander.Parameters;
import com.google.inject.Inject;
import com.google.inject.Provider;

@Parameters(commandDescription = "Downloads all available player photos from premierleague.com.")
public class PhotosCommand extends D11Command {

	private Provider<DownloadPlayerImagesTask> provider;
	private final static Logger logger = LoggerFactory.getLogger(PhotosCommand.class);

	@Inject
	public PhotosCommand(Provider<DownloadPlayerImagesTask> provider) {
		setName("photos");
		this.provider = provider;
	}

	@Override
	public void execute() {
	    logger.info("Downloading player photos...");
	    DownloadPlayerImagesTask task = this.provider.get();
		if (task.execute()) {
		    logger.info("Done");
		}
	}

}

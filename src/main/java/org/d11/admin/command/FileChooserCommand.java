package org.d11.admin.command;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.UIManager;
import javax.swing.filechooser.FileFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class FileChooserCommand extends D11Command {

	private final static Logger logger = LoggerFactory.getLogger(FileChooserCommand.class);

	public FileChooserCommand() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			logger.error("Could not set look and feel.", e);
		}
	}

	protected List<File> getFiles() {
		return getFiles(null);
	}

	protected List<File> getFiles(FileFilter fileFilter) {
		List<File> files = new ArrayList<File>();
		JFileChooser fileChooser = new JFileChooser(getPreferences().get(getClass().getName() + "_DIRECTORY_PREFERENCE", "."));
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setMultiSelectionEnabled(true);
		if (fileFilter != null) {
			fileChooser.setFileFilter(fileFilter);
		}
		if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			files.addAll(Arrays.asList(fileChooser.getSelectedFiles()));
			if (files.size() > 0) {
				getPreferences().put(getClass().getName() + "_DIRECTORY_PREFERENCE", files.get(0).getParent());
			}
		}
		return files;
	}

}

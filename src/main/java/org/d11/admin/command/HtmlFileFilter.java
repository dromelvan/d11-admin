package org.d11.admin.command;

import java.io.File;

import javax.swing.filechooser.FileFilter;

import com.google.common.io.Files;

public class HtmlFileFilter extends FileFilter {

	@Override
	public String getDescription() {
		return "D11 File";
	}

	@Override
	public boolean accept(File file) {
		if (file.isDirectory()) {
			return true;
		}
		String extension = Files.getFileExtension(file.getName());
		if (extension.equalsIgnoreCase("html")
				|| extension.equalsIgnoreCase("htm")) {
			return true;
		}
		return false;
	}
}

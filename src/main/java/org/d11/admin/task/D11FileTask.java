package org.d11.admin.task;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.jsoup.nodes.Document;

import com.google.common.io.Files;

public abstract class D11FileTask<T extends Object> extends D11Task<T> {

	private File sourceFile;
	private File outputFile;

	public File getSourceFile() {
		return sourceFile;
	}

	public void setSourceFile(File sourceFile) {
		this.sourceFile = sourceFile;
	}

	public File getOutputFile() {
		return outputFile;
	}

	public void setOutputFile(File outputFile) {
		this.outputFile = outputFile;
	}

	protected void write(Document document) throws IOException {
		Files.write(document.toString(), outputFile, StandardCharsets.UTF_8);
	}

}

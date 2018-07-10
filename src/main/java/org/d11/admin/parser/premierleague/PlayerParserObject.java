package org.d11.admin.parser.premierleague;

import org.d11.admin.parser.ParserObject;

public class PlayerParserObject extends ParserObject {

	private String id;
	private String name;

	public PlayerParserObject() {
	}

	public PlayerParserObject(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return String.format("Player { Id: %s, Name: %s }", getId(), getName());
	}

}

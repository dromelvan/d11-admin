package org.d11.admin.parser.whoscored.player;

import java.util.ArrayList;
import java.util.List;

import org.d11.admin.parser.ParserObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlayerInformationParserObject extends ParserObject {

	public String name;
	public String nationality;
	public String dateOfBirth;
	public int height = 0;
	public int weight = 0;
	public int shirtNumber = 0;
	public List<String> positions = new ArrayList<String>();
	public String fullName;
	private final static Logger logger = LoggerFactory.getLogger(PlayerInformationParserObject.class);

	public PlayerInformationParserObject() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public int getShirtNumber() {
		return shirtNumber;
	}

	public void setShirtNumber(int shirtNumber) {
		this.shirtNumber = shirtNumber;
	}

	public List<String> getPositions() {
		return positions;
	}

	public void addPosition(String positions) {
		this.positions.add(positions);
	}

	public int getPosition() {
	    if(!getPositions().isEmpty()) {
	        String position = getPositions().get(0).toLowerCase();
	        if(position.contains("goalkeeper")) {
	            return 1;
	        } else if(position.contains("defender")) {
	            return 2;
	        } else if(position.contains("midfielder")) {
	            return 4;
	        } else if(position.contains("forward")) {
	            return 5;
	        }
	        logger.warn("Could not translation position from string {}.", getPositions().get(0));
	    }
	    return 4;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	@Override
	public String toString() {
		return String.format("Name: %s Nationality: %s DOB: %s Height: %d Weight: %d Shirt number: %d Positions: %s Full Name: %s",
				getName(), getNationality(), getDateOfBirth(), getHeight(), getWeight(), getShirtNumber(), getPositions(), getFullName());
	}
}

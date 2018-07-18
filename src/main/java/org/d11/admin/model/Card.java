package org.d11.admin.model;

public class Card extends MatchEvent {

	public enum CardType {
		YELLOW,
		RED;
	}

	private int card_type;

	public int getCardType() {
		return card_type;
	}

	public void setCardType(int cardType) {
		this.card_type = cardType;
	}

}

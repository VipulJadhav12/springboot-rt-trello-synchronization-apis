package com.myorg.example.models;

import org.springframework.stereotype.Component;

@Component
public class TrelloCard {
	
	private String cardId;

	private String cardName;
	
	public String getCardId() {
		return cardId;
	}
	
	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public String getCardName() {
		return cardName;
	}

	public void setCardName(String cardDetails) {
		this.cardName = cardDetails;
	}

	@Override
	public String toString() {
		return "TrelloCard [cardId=" + cardId + ", cardDetails=" + cardName + "]";
	}

}

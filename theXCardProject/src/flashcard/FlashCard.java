package flashcard;

//Author: Roman Heiser

//Diese Klasse dient zur internen Abbildung der Karten und
//zur Interaktion mit der Datenbank. Ihre Objekte werden
//z.T. für DB-Methoden genutzt. 

public class FlashCard {
	
	private String frontSide;
	private String backSide;
	private String cardSet;
	private String status;
	
	public String getFrontSide() {
		return frontSide;
	}

	public void setFrontSide(String frontSide) {
		this.frontSide = frontSide;
	}

	public String getBackSide() {
		return backSide;
	}

	public void setBackSide(String backSide) {
		this.backSide = backSide;
	}

	public String getCardSet() {
		return cardSet;
	}

	public void setCardSet(String cardSet) {
		this.cardSet = cardSet;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}

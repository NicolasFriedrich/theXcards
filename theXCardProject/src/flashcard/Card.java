package flashcard;

public class Card {

	// Default constructor.
	public Card() {}

	// Warning: Do not set properties as private!
	public String frontSide = "";
	public String backSide = "";

	/**
	 * Constructor
	 *
	 * @param frontSide: the content of the card's front side.
	 * @param backSide: the content of the card's back side.
	 */
	public Card(String frontSide, String backSide) {
		this.frontSide = frontSide;
		this.backSide = backSide;
//		final String createdDate = "";
	}

	public String getFrontSide() {
		return frontSide;
	}

	public String getBackSide() {
		return backSide;
	}

	public void setFrontSide(String frontSide) {
		this.frontSide = frontSide;
	}

	public void setBackSide(String backSide) {
		this.backSide = backSide;
	}

}
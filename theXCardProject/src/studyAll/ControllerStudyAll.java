package studyAll;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import database.DBInterface;
import flashcard.FlashCard;
import database.Status;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ControllerStudyAll {

	@FXML
	public AnchorPane anchorPaneMyDecks;
	@FXML
	public Label frontCardContent;
	@FXML
	public Label backCardContent;
	@FXML
	public VBox frontSection;
	@FXML
	public VBox backSection;
	private final Image refreshArrowBlack = new Image(getClass().getResourceAsStream("refresh_arrow_black.png"));
	private final Image refreshArrowGray = new Image(getClass().getResourceAsStream("refresh_arrow_gray.png"));
	private static final char separatorUnixWin = File.separatorChar;
	private static final DBInterface dbi = new DBInterface();
	static ArrayList<String> cardSetList = dbi.getCardSets();
	private ArrayList<FlashCard> cardList = new ArrayList<>();
	private static int arrayIndex;
	private static boolean isActive = false;
	@FXML
	private ComboBox<String> chooseDeckComboBox;

	public void initialize() {
		for (String set : cardSetList) {
			chooseDeckComboBox.getItems().add(set);
		}
		frontCardContent.setText("Please choose a deck.");
		backCardContent.setText("There is no card to study.");
	}

	@FXML
	public void goToHome() throws IOException {
		String pathToHome = ".."+separatorUnixWin+"mainFXML"+separatorUnixWin+"viewMain.fxml";
		AnchorPane anchorPaneHome = FXMLLoader.load(getClass().getResource(pathToHome));
		anchorPaneMyDecks.getChildren().setAll(anchorPaneHome);
	}

	@FXML
	public void goToNewCard() throws IOException {
		String pathToNewCard = ".." + separatorUnixWin + "newCard" + separatorUnixWin + "viewNewCard.fxml";
		AnchorPane anchorPaneNewCard = FXMLLoader.load(getClass().getResource(pathToNewCard));
		anchorPaneMyDecks.getChildren().setAll(anchorPaneNewCard);
	}

	public void increaseArrayIndex() {
		if (arrayIndex == cardList.size() - 1) {
			arrayIndex = 0;
		} else {
			arrayIndex++;
			isActive = true;
			backCardContent.setGraphic(new ImageView(refreshArrowGray));
		}
	}

	public void setFrontWithCardContent(int arrayIndex) {
		frontCardContent.setText(cardList.get(arrayIndex).getFrontSide());
	}

	@FXML
	public void revealBackSide() {
		if (!cardList.isEmpty() && (cardList.get(arrayIndex) != null)) {
			isActive = false;
			backCardContent.setGraphic(null);
			backCardContent.setText(cardList.get(arrayIndex).getBackSide());
		}
	}

	@FXML
	public void setCardToEasy() {
		dbi.updateCardInDB(cardList.get(arrayIndex), Status.EAS.toString().toLowerCase());
		backCardContent.setText("");
		increaseArrayIndex();
		setFrontWithCardContent(arrayIndex);
	}

	@FXML
	public void setCardToGood() {
		dbi.updateCardInDB(cardList.get(arrayIndex), Status.GOO.toString().toLowerCase());
		backCardContent.setText("");
		increaseArrayIndex();
		setFrontWithCardContent(arrayIndex);
	}

	@FXML
	public void setCardToDifficult() {
		dbi.updateCardInDB(cardList.get(arrayIndex), Status.DIF.toString().toLowerCase());
		backCardContent.setText("");
		increaseArrayIndex();
		setFrontWithCardContent(arrayIndex);
	}

	@FXML
	public void deleteThisCard() {
		increaseArrayIndex();
		setFrontWithCardContent(arrayIndex);
		backCardContent.setText("");
		dbi.deleteCardFromDB(cardList.get(arrayIndex));
	}

	@FXML
	public void setRefreshArrowToGray() {
		if (isActive) {
			backCardContent.setGraphic(new ImageView(refreshArrowBlack));
		}
		else {
			backCardContent.setGraphic(null);
		}
	}

	@FXML
	public void setRefreshArrowToBlack() {
		if (isActive) {
			backCardContent.setGraphic(new ImageView(refreshArrowGray));
		}
		else {
			backCardContent.setGraphic(null);
		}
	}

	public void setCardList(String deck) {
		cardList = dbi.getCards(deck);
		setFrontWithCardContent(0);
		backCardContent.setText("");
		backCardContent.setGraphic(new ImageView(refreshArrowGray));
		isActive = true;
	}

}

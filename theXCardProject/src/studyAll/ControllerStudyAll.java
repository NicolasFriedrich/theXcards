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
	private AnchorPane anchorPaneMyDecks;
	@FXML
	private Label frontCardContent;
	@FXML
	private Label backCardContent;
	@FXML
	public VBox frontSection;
	@FXML
	public VBox backSection;
	@FXML
	private ComboBox<String> chooseDeckComboBox;

	private final Image refreshArrowBlack = new Image(getClass().getResourceAsStream("refresh_arrow_black.png"));
	private final Image refreshArrowGray = new Image(getClass().getResourceAsStream("refresh_arrow_gray.png"));
	private static final char separatorUnixWin = File.separatorChar;
	private static final DBInterface dbi = new DBInterface();
	static ArrayList<String> cardSetList = dbi.getCardSets();
	static ArrayList<FlashCard> cardList = new ArrayList<>();
	private static int arrayIndex;
	private static boolean isActive = false;

	public void initialize() {
		for (String set : cardSetList) {
			chooseDeckComboBox.getItems().add(set);
		}
		frontCardContent.setText("Please choose a deck.");
		backCardContent.setText("There is no card to study.");
	}

	@FXML
	private void goToHome() throws IOException {
		String pathToHome = ".."+separatorUnixWin+"mainFXML"+separatorUnixWin+"viewMain.fxml";
		AnchorPane anchorPaneHome = FXMLLoader.load(getClass().getResource(pathToHome));
		anchorPaneMyDecks.getChildren().setAll(anchorPaneHome);
	}

	@FXML
	private void goToNewCard() throws IOException {
		String pathToNewCard = ".." + separatorUnixWin + "newCard" + separatorUnixWin + "viewNewCard.fxml";
		AnchorPane anchorPaneNewCard = FXMLLoader.load(getClass().getResource(pathToNewCard));
		anchorPaneMyDecks.getChildren().setAll(anchorPaneNewCard);
	}

	@FXML
	private void revealBackSide() {
		if (!cardList.isEmpty() && (cardList.get(arrayIndex) != null)) {
			isActive = false;
			backCardContent.setGraphic(null);
			backCardContent.setText(cardList.get(arrayIndex).getBackSide());
		}
	}

	@FXML
	private void setCardToEasy() {
		dbi.updateCardInDB(cardList.get(arrayIndex), Status.EAS.toString().toLowerCase());
		backCardContent.setText("");
		increaseArrayIndex();
		setFrontWithCardContent(arrayIndex);
	}

	@FXML
	private void setCardToGood() {
		dbi.updateCardInDB(cardList.get(arrayIndex), Status.GOO.toString().toLowerCase());
		backCardContent.setText("");
		increaseArrayIndex();
		setFrontWithCardContent(arrayIndex);
	}

	@FXML
	private void setCardToDifficult() {
		dbi.updateCardInDB(cardList.get(arrayIndex), Status.DIF.toString().toLowerCase());
		backCardContent.setText("");
		increaseArrayIndex();
		setFrontWithCardContent(arrayIndex);
	}

	@FXML
	private void deleteThisCard() {
		increaseArrayIndex();
		setFrontWithCardContent(arrayIndex);
		backCardContent.setText("");
		dbi.deleteCardFromDB(cardList.get(arrayIndex));
	}

	@FXML
	private void setRefreshArrowToGray() {
		if (isActive) {
			backCardContent.setGraphic(new ImageView(refreshArrowBlack));
		}
		else {
			backCardContent.setGraphic(null);
		}
	}

	@FXML
	private void setRefreshArrowToBlack() {
		if (isActive) {
			backCardContent.setGraphic(new ImageView(refreshArrowGray));
		}
		else {
			backCardContent.setGraphic(null);
		}
	}

	private void increaseArrayIndex() {
		if (arrayIndex == cardList.size() - 1) {
			arrayIndex = 0;
		} else {
			arrayIndex++;
			isActive = true;
			backCardContent.setGraphic(new ImageView(refreshArrowGray));
		}
	}

	private void setFrontWithCardContent(int arrayIndex) {
		frontCardContent.setText(cardList.get(arrayIndex).getFrontSide());
	}
}

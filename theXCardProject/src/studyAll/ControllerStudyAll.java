package studyAll;

import database.DBInterface;
import database.Status;
import flashcard.FlashCard;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
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
	private ComboBox<String> comboBox;

	private final Image refreshArrowBlack = new Image(getClass().getResourceAsStream("refresh_arrow_black.png"));
	private final Image refreshArrowGray = new Image(getClass().getResourceAsStream("refresh_arrow_gray.png"));
	private static final char separatorUnixWin = File.separatorChar;
	private static final DBInterface dbi = new DBInterface();
	static ArrayList<String> cardSetList = dbi.getCardSets();
	static ArrayList<FlashCard> cardList = new ArrayList<>();
	private static int arrayIndex;

	public void initialize() {
		for (String set : cardSetList) {
			comboBox.getItems().add(set);
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
	private void authorizeLearning() {
		backCardContent.setText("");
		setRefreshArrowToGray();
		cardList = dbi.getCards(comboBox.getValue());
		arrayIndex = 0;
		frontCardContent.setText(cardList.get(arrayIndex).getFrontSide());
	}

	private boolean checkComboBoxStudyAll() {
		return comboBox.getValue() != null;
	}

	@FXML
	private void revealBackSide() {
		if (checkComboBoxStudyAll()) {
//			setRefreshArrow(null);
			backCardContent.setGraphic(null);
			backCardContent.setText(cardList.get(arrayIndex).getBackSide());
		}
	}

	@FXML
	private void setCardToEasy() {
		if (checkComboBoxStudyAll()) {
			setCardSatus(Status.EAS);
		}
	}

	@FXML
	private void setCardToGood() {
		if (checkComboBoxStudyAll()) {
			setCardSatus(Status.GOO);
		}
	}

	@FXML
	private void setCardToDifficult() {
		if (checkComboBoxStudyAll()) {
			setCardSatus(Status.DIF);
		}
	}

	private void setFrontWithCardContent(int arrayIndex) {
		frontCardContent.setText(cardList.get(arrayIndex).getFrontSide());
	}

	private void setCardSatus(Status satus) {
		dbi.updateCardInDB(cardList.get(arrayIndex), satus.toString().toLowerCase());
		backCardContent.setText("");
		increaseArrayIndex();
		setFrontWithCardContent(arrayIndex);
	}

	@FXML
	private void deleteThisCard() {
		if (checkComboBoxStudyAll()) {
			increaseArrayIndex();
			setFrontWithCardContent(arrayIndex);
			backCardContent.setText("");
			dbi.deleteCardFromDB(cardList.get(arrayIndex));
		}
	}

	@FXML
	private void setRefreshArrowToGray() {
		if(backCardContent.getText().equals("")) {
			setRefreshArrow(refreshArrowGray);
		}
	}

	@FXML
	private void setRefreshArrowToBlack() {
		if(backCardContent.getText().equals("")) {
			setRefreshArrow(refreshArrowBlack);
		}
	}

	private void setRefreshArrow(Image arrowColor) {
		if (comboBox.getValue() != null) {
			backCardContent.setGraphic(new ImageView(arrowColor));
		}
	}

	private void increaseArrayIndex() {
		if (arrayIndex == cardList.size() - 1) {
			arrayIndex = 0;
		} else {
			arrayIndex++;
			backCardContent.setGraphic(new ImageView(refreshArrowGray));
		}
	}
}

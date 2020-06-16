package newCard;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import database.DBInterface;
import flashcard.FlashCard;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ControllerNewCard {

	@FXML
	public AnchorPane anchorPaneNewCard;
	@FXML
	public TextArea cardQuestionTextArea;
	@FXML
	public TextArea cardAnswerTextArea;
	@FXML
	public Label labelStatusCheck;
	@FXML
	public ComboBox<String> chooseDeckComboBox;
	private static final char separatorUnixWin = File.separatorChar;
	private final DBInterface dbi = new DBInterface();
	private final ArrayList<String> cardSetList = dbi.getCardSets();

	public void initialize() {
		for (String set : cardSetList) {
			chooseDeckComboBox.getItems().add(set);
		}
	}

	@FXML
	public void goToHome() throws IOException {
		String pathToHome = ".."+separatorUnixWin+"mainFXML"+separatorUnixWin+"viewMain.fxml";
		AnchorPane anchorPaneHome = FXMLLoader.load(getClass().getResource(pathToHome));
		anchorPaneNewCard.getChildren().setAll(anchorPaneHome);
	}

	@FXML
	public void goToStudyAll() throws IOException {
		String pathToStudyAll = ".."+separatorUnixWin+"studyAll"+separatorUnixWin+"viewStudyAll.fxml";
		AnchorPane anchorPaneMyDecks = FXMLLoader.load(getClass().getResource(pathToStudyAll));
		anchorPaneNewCard.getChildren().setAll(anchorPaneMyDecks);
	}

	@FXML
	public void saveCard() {
		if (chooseDeckComboBox.getValue() != null) {
			FlashCard card = new FlashCard();
			card.setFrontSide(cardQuestionTextArea.getText());
			card.setBackSide(cardAnswerTextArea.getText());
			card.setCardSet(chooseDeckComboBox.getValue());
			dbi.addCardToDB(card);
		}
	}
}

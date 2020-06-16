package mainFXML;

import database.Status;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import database.DBInterface;
import flashcard.FlashCard;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ControllerMain {

	@FXML
	public VBox leftPane;
	@FXML
	public VBox MainInformations;
	@FXML
	private AnchorPane anchorPaneMain;
	@FXML
	public Label labelHomeCardsQuantity;
	private static final char separatorUnixWin = File.separatorChar;
	@FXML
	private PieChart pieChart;

	private final DBInterface dbi = new DBInterface();
	private final ArrayList<String> cardSetList = dbi.getCardSets();
	private final ArrayList<FlashCard> cardList = dbi.getCards(cardSetList.get(0));

	public void initialize() {
		assert cardList != null;
		List<FlashCard> statusNEW = cardList.stream()
				.filter(card -> card.getStatus().equals(Status.NEW.toString().toLowerCase()))
				.collect(Collectors.toList());

		List<FlashCard> statusEAS = cardList.stream()
				.filter(card -> card.getStatus().equals(Status.EAS.toString().toLowerCase()))
				.collect(Collectors.toList());

		List<FlashCard> statusGOO = cardList.stream()
				.filter(card -> card.getStatus().equals(Status.GOO.toString().toLowerCase()))
				.collect(Collectors.toList());

		List<FlashCard> statusDIF = cardList.stream()
				.filter(card -> card.getStatus().equals(Status.DIF.toString().toLowerCase()))
				.collect(Collectors.toList());

		PieChart.Data slice1 = new PieChart.Data("New " + statusNEW.size(), statusNEW.size());
		PieChart.Data slice2 = new PieChart.Data("Easy " + statusEAS.size(), statusEAS.size());
		PieChart.Data slice3 = new PieChart.Data("Good " + statusGOO.size(), statusGOO.size());
		PieChart.Data slice4 = new PieChart.Data("Difficult " + statusDIF.size(), statusDIF.size());

		pieChart.getData().addAll(slice1, slice2, slice3, slice4);
	}

	@FXML
	private void goToStudyAll() throws IOException {
		String pathToStudyAll = ".."+separatorUnixWin+"studyAll"+separatorUnixWin+"viewStudyAll.fxml";
		AnchorPane anchorPaneMyDecks = FXMLLoader.load(getClass().getResource(pathToStudyAll));
		anchorPaneMain.getChildren().setAll(anchorPaneMyDecks);
	}

	@FXML
	private void goToNewCard() throws IOException {
		String pathToNewCard = ".."+separatorUnixWin+"newCard"+separatorUnixWin+"viewNewCard.fxml";
		AnchorPane anchorPaneNewCard = FXMLLoader.load(getClass().getResource(pathToNewCard));
		anchorPaneMain.getChildren().setAll(anchorPaneNewCard);
	}
}

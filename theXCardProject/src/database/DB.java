package database;

//Author: Roman Heiser

//Diese Klasse dient der direkten Kommunikation mit einer sqlite-DB.
//Sie bildet die Grundlage für das DB-Interface.

import flashcard.FlashCard;

import java.sql.*;

public class DB {

	private static Connection connection;
	
	public void establishConnection() throws ClassNotFoundException, SQLException {
		Class.forName("org.sqlite.JDBC");
		connection = DriverManager.getConnection("jdbc:sqlite:" + this.getClass().getResource("").toString() + "cards.db");
	}
	
	public ResultSet loadCards(String cardSet) throws SQLException, ClassNotFoundException{
		establishConnection();
		PreparedStatement statement = connection.prepareStatement("SELECT * FROM cards WHERE cardset = ?");
		statement.setString(1, cardSet);
		return statement.executeQuery();
	}
	
	public void updateCard(FlashCard card, String newStatus) throws SQLException, ClassNotFoundException {
		establishConnection();
		PreparedStatement statement = connection.prepareStatement("UPDATE cards SET status = ? WHERE frontside = ?");
		statement.setString(1, newStatus);
		statement.setString(2, card.getFrontSide());
		statement.executeUpdate();
	}
	
	public void addCard(FlashCard card) throws SQLException, ClassNotFoundException{
		establishConnection();
		PreparedStatement statement = connection.prepareStatement("INSERT INTO cards values(?,?,?,'new')");
		statement.setString(1, card.getFrontSide());
		statement.setString(2, card.getBackSide());
		statement.setString(3, card.getCardSet());
		statement.executeUpdate();
	}
	
	public void deleteCard(FlashCard card) throws SQLException, ClassNotFoundException{
		establishConnection();
		PreparedStatement statement = connection.prepareStatement("DELETE FROM cards WHERE frontside = ? AND cardset = ?");
		statement.setString(1, card.getFrontSide());
		statement.setString(2, card.getCardSet());
		statement.executeUpdate();
	}
	
	public void deleteCardSet(FlashCard card) throws SQLException, ClassNotFoundException{
		establishConnection();
		PreparedStatement statement = connection.prepareStatement("DELETE FROM cards WHERE cardset = ?");
		statement.setString(1, card.getCardSet());
		statement.executeUpdate();
	}
	
	public ResultSet loadCardSets() throws SQLException, ClassNotFoundException{
		establishConnection();
		Statement statement = connection.createStatement();
		return statement.executeQuery("SELECT DISTINCT cardset FROM cards");
	}

}

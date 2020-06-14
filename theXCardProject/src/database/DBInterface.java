package database;

//Author: Roman Heiser

//Diese Klasse dient als einfaches DB-Interface für den Entwickler, das sich mittels 
//selbsterklärender Methodennamen leicht in das Programm integrieren lässt.
//Die methoden basieren auf der Übergabe von einfachen String-Parametern oder
//FlashCard-Objekten und Rückgabe einer ArrayList. 
//Durch Multithreading wird ein 'Hängen' der GUI für den Nutzer bei Methoden-Aufrufen
//vermieden.

import flashcard.FlashCard;

import java.util.ArrayList;
import java.sql.*;

public class DBInterface {

	public ArrayList<FlashCard> getCards(String cardSet) {
		DB db = new DB();
		ResultSet queryResult;
		try {
			queryResult = db.loadCards(cardSet);
			ArrayList<FlashCard> cardList = new ArrayList<>();
			while(queryResult.next()) {
				FlashCard card = new FlashCard();
				card.setFrontSide(queryResult.getString("frontside"));
				card.setBackSide(queryResult.getString("backside"));
				card.setCardSet(queryResult.getString("cardset"));
				card.setStatus(queryResult.getString("status"));
				cardList.add(card);
			}
			return  cardList;

		}
		catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public ArrayList<String> getCardSets() {
		DB db = new DB();
		ResultSet queryResult;
		try {
			queryResult = db.loadCardSets();
			ArrayList<String> cardSetList = new ArrayList<>();
			while(queryResult.next()) {
				cardSetList.add(queryResult.getString(1));
			}
			return  cardSetList;
		
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void updateCardInDB(FlashCard card, String newStatus){
		Thread thread = new Thread(() -> {
			try {
				DB db = new DB();
				db.updateCard(card, newStatus);
			}
			catch (ClassNotFoundException | SQLException e) {
					e.printStackTrace();
			}
		});
		thread.start();
	}
	
	public void addCardToDB(FlashCard card) {
		Thread thread = new Thread(() -> {
			try {
				DB db = new DB();
				db.addCard(card);
			}
			catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
		});
		thread.start();
	}
	
	public void deleteCardFromDB(FlashCard card) {
		Thread thread = new Thread(() -> {	
			try {
				DB db = new DB();
				db.deleteCard(card);
			}
			catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
		});
		thread.start();
	}
	
	public void deleteCardSetFromDB(FlashCard card) {
		Thread thread = new Thread(() -> {	
			try {
				DB db = new DB();
				db.deleteCardSet(card);
			}
			catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
		});
		thread.start();
	}
}

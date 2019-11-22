package solitaire;

import java.util.ArrayList;
import java.util.List;

import solitaire.card.Card;
import solitaire.location.Location;
import solitaire.location.Type;

public class GameManager {

	public static void onCellClick(Cell cell, int number) {
		if (cell.getBacks() >= number)
			return;
		List<Card> cards = new ArrayList<Card>();
		cards.addAll(cell.getCards().subList(number - 1, cell.getCards().size()));
		List<Card> removed = cell.getCards().subList(0, number - 1);
		cell.setCards(removed);
		Solitaire.handLocation = new Location(cell, number);
		Solitaire.hand = cards;
	}

	public static void onEmptyCellKingDrop(Cell cell) {
		List<Card> cards = new ArrayList<Card>();
		cards.addAll(Solitaire.hand);
		cell.setCards(cards);
		Solitaire.hand.clear();
		refillOnDrop();
		Solitaire.handLocation = null;
	}

	public static void onCellRightClick(Cell cell) {
		if (cell.getCards().isEmpty())
			return;
		Card card = cell.getCards().get(cell.getCards().size() - 1);
		if (Placemat.getPlacemat(card.getCardSuit()).getNumber() == card.getCardNumber() - 1) {
			List<Card> cards = new ArrayList<Card>();
			if (cell.getCards().size() != 1)
				cards.addAll(cell.getCards().subList(0, cell.getCards().size() - 1));
			Placemat.getPlacemat(card.getCardSuit()).setNumber(card.getCardNumber());
			cell.setCards(cards);
			if (cell.getCards().size() == cell.getBacks())
				cell.setBacks(cell.getBacks() - 1);
		}
	}
	
	public static void onTopRightClick() {
		if (Solitaire.deck.getTop().isEmpty())
			return;
		Card card = Solitaire.deck.getTop().get(Solitaire.deck.getTop().size() - 1);
		if (Placemat.getPlacemat(card.getCardSuit()).getNumber() == card.getCardNumber() - 1) {
			Placemat.getPlacemat(card.getCardSuit()).setNumber(card.getCardNumber());
			Solitaire.deck.getTopCard();
			Solitaire.deck.refillTop();
		}
	}

	public static void onCellDrop(Cell cell) {
		List<Card> cards = new ArrayList<Card>();
		cards.addAll(cell.getCards());
		cards.addAll(Solitaire.hand);
		cell.setCards(cards);

		Solitaire.hand.clear();
		refillOnDrop();
		Solitaire.handLocation = null;
	}

	public static void onTopClick() {
		if (Solitaire.deck.getTop().size() == 0)
			return;
		Card card = Solitaire.deck.getTopCard();
		Solitaire.handLocation = new Location(Type.TOP);
		Solitaire.hand.add(card);
	}

	public static void onDeckClick() {
		if (Solitaire.deck.getCards().isEmpty())
			Solitaire.deck.refillDeck();
		else
			Solitaire.deck.placeCardsFromDeck();
	}

	public static void onPlacematClick(Placemat placemat) {
		if (placemat.getNumber() == 0)
			return;
		Card card = Card.getCard(placemat.getNumber(), placemat.getType());
		placemat.setNumber(placemat.getNumber() - 1);
		Solitaire.handLocation = new Location(placemat);
		Solitaire.hand.add(card);
	}

	public static void onPlacematDrop(Placemat placemat) {
		placemat.setNumber(placemat.getNumber() + 1);
		Solitaire.hand.clear();
		refillOnDrop();
		Solitaire.handLocation = null;
	}

	private static void refillOnDrop() {
		Type type = Solitaire.handLocation.getLocationType();
		if (type == Type.TOP)
			Solitaire.deck.refillTop();
		if (type == Type.CELL) {
			Cell cell = (Cell) Solitaire.handLocation.getLocation();
			if (cell.getCards().size() == cell.getBacks())
				cell.setBacks(cell.getBacks() - 1);
		}
	}
}

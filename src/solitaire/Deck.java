package solitaire;

import java.util.ArrayList;
import java.util.List;

import solitaire.card.Card;

public class Deck {

	private List<Card> cards;
	private List<Card> top = new ArrayList<Card>();
	private List<Card> table = new ArrayList<Card>();

	public Deck(List<Card> cards) {
		this.cards = cards;
	}

	public List<Card> getCards() {
		return cards;
	}

	public List<Card> getTable() {
		return table;
	}

	public List<Card> getTop() {
		return top;
	}

	public void placeCardsFromDeck() {
		for (Card card : top)
			table.add(card);
		top.clear();
		int size = 3;
		if (cards.size() < 3)
			size = cards.size();
		if (!table.isEmpty())
		for (int i = 0; i < 3 - size; i++)
			top.add(table.remove(table.size() - 1));
		for (int i = 0; i < size; i++)
			top.add(cards.remove(0));
	}

	public Card getTopCard() {
		return top.remove(top.size() - 1);
	}

	public void refillTop() {
		List<Card> cards = new ArrayList<Card>();
		if (!table.isEmpty()) {
			for (int i = 0; i < 3; i++)
				if (i == 0)
					cards.add(table.remove(table.size() - 1));
				else if (!top.isEmpty())
					cards.add(top.remove(0));
			top = cards;
		}
	}

	public void refillDeck() {
		for (Card card : table)
			cards.add(card);
		for (Card card : top)
			cards.add(card);
		top.clear();
		table.clear();
	}

}
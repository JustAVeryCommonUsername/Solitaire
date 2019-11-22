package solitaire;

import java.util.List;

import solitaire.card.Card;

public class Cell {
	
	private int number;
	private List<Card> cards;
	private int backs;
	
	public Cell(int number, List<Card> cards, int backs) {
		this.number = number;
		this.cards = cards;
		this.backs = backs;
	}
	
	public static Cell getCell(int number) {
		for (Cell cell : Solitaire.cells)
			if (cell.getNumber() == number)
				return cell;
		return null;
	}
	
	public int getNumber() {
		return number;
	}
	
	public List<Card> getCards() {
		return cards;
	}
	
	public int getBacks() {
		return backs;
	}
	
	public void setBacks(int number) {
		backs = number;
	}
	
	public void setCards(List<Card> cards) {
		this.cards = cards;
	}
}

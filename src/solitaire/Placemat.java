package solitaire;

import solitaire.card.Suit;

public class Placemat {
	
	private Suit type;
	private int number = 0;
	
	public Placemat(Suit type) {
		this.type = type;
	}
	
	public int getNumber() {
		return number;
	}
	
	public Suit getType() {
		return type;
	}
	
	public void setNumber(int number) {
		this.number = number;
	}
	
	public static Placemat getPlacemat(Suit suit) {
		for (Placemat placemat : Solitaire.placemats)
			if (placemat.getType() == suit)
				return placemat;
		return null;
	}
}

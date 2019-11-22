package solitaire.card;

import java.awt.Image;
import java.awt.Toolkit;

import solitaire.Solitaire;

public enum Card {
	BACK("back"), DECK("deck"), JOKER("joker"), PLACEMAT_HEARTS("placemat_hearts"), PLACEMAT_SPADES("placemat_spades"),
	PLACEMAT_DIAMONDS("placemat_diamonds"), PLACEMAT_CLUBS("placemat_clubs"), CELL("cell"), CAD(1, Suit.DIAMONDS, "ad"),
	C2D(2, Suit.DIAMONDS, "2d"), C3D(3, Suit.DIAMONDS, "3d"), C4D(4, Suit.DIAMONDS, "4d"), C5D(5, Suit.DIAMONDS, "5d"),
	C6D(6, Suit.DIAMONDS, "6d"), C7D(7, Suit.DIAMONDS, "7d"), C8D(8, Suit.DIAMONDS, "8d"), C9D(9, Suit.DIAMONDS, "9d"),
	C10D(10, Suit.DIAMONDS, "10d"), CJD(11, Suit.DIAMONDS, "jd"), CQD(12, Suit.DIAMONDS, "qd"),
	CKD(13, Suit.DIAMONDS, "kd"), CAH(1, Suit.HEARTS, "ah"), C2H(2, Suit.HEARTS, "2h"), C3H(3, Suit.HEARTS, "3h"),
	C4H(4, Suit.HEARTS, "4h"), C5H(5, Suit.HEARTS, "5h"), C6H(6, Suit.HEARTS, "6h"), C7H(7, Suit.HEARTS, "7h"),
	C8H(8, Suit.HEARTS, "8h"), C9H(9, Suit.HEARTS, "9h"), C10H(10, Suit.HEARTS, "10h"), CJH(11, Suit.HEARTS, "jh"),
	CQH(12, Suit.HEARTS, "qh"), CKH(13, Suit.HEARTS, "kh"), CAC(1, Suit.CLUBS, "ac"), C2C(2, Suit.CLUBS, "2c"),
	C3C(3, Suit.CLUBS, "3c"), C4C(4, Suit.CLUBS, "4c"), C5C(5, Suit.CLUBS, "5c"), C6C(6, Suit.CLUBS, "6c"),
	C7C(7, Suit.CLUBS, "7c"), C8C(8, Suit.CLUBS, "8c"), C9C(9, Suit.CLUBS, "9c"), C10C(10, Suit.CLUBS, "10c"),
	CJC(11, Suit.CLUBS, "jc"), CQC(12, Suit.CLUBS, "qc"), CKC(13, Suit.CLUBS, "kc"), CAS(1, Suit.SPADES, "as"),
	C2S(2, Suit.SPADES, "2s"), C3S(3, Suit.SPADES, "3s"), C4S(4, Suit.SPADES, "4s"), C5S(5, Suit.SPADES, "5s"),
	C6S(6, Suit.SPADES, "6s"), C7S(7, Suit.SPADES, "7s"), C8S(8, Suit.SPADES, "8s"), C9S(9, Suit.SPADES, "9s"),
	C10S(10, Suit.SPADES, "10s"), CJS(11, Suit.SPADES, "js"), CQS(12, Suit.SPADES, "qs"), CKS(13, Suit.SPADES, "ks");

	private int number;
	private Suit suit;
	private String image;
	private boolean isAnExtra = false;

	Card(int number, Suit suit, String image) {
		this.number = number;
		this.suit = suit;
		this.image = image;
	}

	Card(String image) {
		isAnExtra = true;
		this.image = image;
	}

	public int getCardNumber() {
		return number;
	}

	public Suit getCardSuit() {
		return suit;
	}

	public boolean isAnExtra() {
		return isAnExtra;
	}

	public Image getCardImage() {
		if (isAnExtra)
			return Toolkit.getDefaultToolkit().getImage(getClass().getResource("/cards/" + image + ".png"));
		switch (Solitaire.texture) {
		case BIT:
			return Toolkit.getDefaultToolkit().getImage(getClass().getResource("/cards/" + image + ".png"));
		case REALISTIC:
			return Toolkit.getDefaultToolkit().getImage(getClass().getResource("/cards/hr_" + image + ".png"));
		}
		return null;
	}

	public static Card getCard(int number, Suit suit) {
		for (Card card : Card.values()) {
			if (card.isAnExtra)
				continue;
			if (card.getCardNumber() == number && card.getCardSuit() == suit)
				return card;
		}
		return null;
	}
}
package solitaire.card;

public enum Suit {
	HEARTS(Color.RED),
	SPADES(Color.BLACK),
	DIAMONDS(Color.RED),
	CLUBS(Color.BLACK);
	
	private Color color;
	
	Suit(Color color) {
		this.color = color;
	}
	
	public Color getColor() {
		return color;
	}
}

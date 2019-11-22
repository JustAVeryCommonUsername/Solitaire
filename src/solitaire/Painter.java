package solitaire;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import solitaire.card.Card;
import solitaire.card.Suit;
import solitaire.location.Type;

@SuppressWarnings("serial")
public class Painter extends JPanel {

	private JFrame frame;
	private static Painter painter;
	private int width;
	private int height;
	private int size = 2;

	public Painter(JFrame frame) {
		this.frame = frame;
		this.setBounds(0, 0, frame.getWidth(), frame.getHeight());
		Painter.painter = this;
		repaint();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		width = frame.getWidth();
		height = frame.getHeight();

		// Used to scale the images
		if ((width > 840 && height == 640) || (width > height && size == 2))
			size = 1;
		if ((height > 640 && width == 840) || (height > width && size == 1))
			size = 2;

		this.setBounds(0, 0, width, height);
		this.setBackground(new Color(1, 135, 80));

		// Hitboxes
		for (Hitbox hitbox : Solitaire.hitboxes) {
			if (hitbox.getLocation().getLocationType() == Type.DECK)
				hitbox.getLabel().setBounds(width / 13, height / 32, getSize(74), getSize(99));

			int number = Solitaire.deck.getTop().size() - 1;
			if (hitbox.getLocation().getLocationType() == Type.TOP)
				hitbox.getLabel().setBounds(width / 5 + (number * (width / 20)), height / 32, getSize(74), getSize(99));

			if (hitbox.getLocation().getLocationType() == Type.PLACEMAT) {
				int i = 0;
				for (Suit suit : Suit.values()) {
					if (((Placemat) hitbox.getLocation().getLocation()).getType() == suit)
						hitbox.getLabel().setBounds(width / 2 + (i * getSize(80)) - width / 14, height / 32,
								getSize(74), getSize(99));
					i++;
				}
			}

			if (hitbox.getLocation().getLocationType() == Type.CELL) {
				for (int k = 0; k < 7; k++) {
					if (hitbox.getLocation().getCellNumber() == 0
							&& ((Cell) hitbox.getLocation().getLocation()).getNumber() == k + 1) {
						Cell cell = (Cell) hitbox.getLocation().getLocation();
						if (cell.getCards().isEmpty())
							hitbox.getLabel().setBounds(k * (width / 7 - 10) + (width / 21), height / 5, getSize(74),
									getSize(99));
						else
							hitbox.getLabel().setBounds(0, 0, 0, 0);
						break;
					}
					for (int n = 0; n < 13 + k; n++) {
						Cell cell = (Cell) hitbox.getLocation().getLocation();
						int cellNumber = hitbox.getLocation().getCellNumber();
						if (cell.getNumber() == k + 1 && cellNumber == n + 1)
							if (cell.getCards().size() + 1 > cellNumber)
								if (cellNumber != cell.getCards().size())
									hitbox.getLabel().setBounds(k * (width / 7 - 10) + (width / 21),
											height / 5 + (n * (height / 25)), getSize(74), height / 25);
								else
									hitbox.getLabel().setBounds(k * (width / 7 - 10) + (width / 21),
											height / 5 + (n * (height / 25)), getSize(74), getSize(99));
							else
								hitbox.getLabel().setBounds(0, 0, 0, 0);
					}

				}
			}
		}

		// Placemats
		Card[] placemats = new Card[]{Card.PLACEMAT_HEARTS, Card.PLACEMAT_SPADES, Card.PLACEMAT_DIAMONDS, Card.PLACEMAT_CLUBS};
		int l = 0;
		for (Card card : placemats) {
		g.drawImage(card.getCardImage(), width / 2 + (l * getSize(80)) - width / 14, height / 32, getSize(74), getSize(99), this);
		l++;
		}
		int k = 0;
		for (Placemat placemat : Solitaire.placemats) {
			if (placemat.getNumber() != 0) {
				Card card = Card.getCard(placemat.getNumber(), placemat.getType());
				g.drawImage(card.getCardImage(), width / 2 + (k * getSize(80)) - width / 14, height / 32, getSize(74),
						getSize(99), this);
			}
			k++;
		}

		// Deck
		g.drawImage(Card.CELL.getCardImage(), width / 13, height / 32, getSize(74), getSize(99), this);
		if (!Solitaire.deck.getCards().isEmpty())
			g.drawImage(Card.BACK.getCardImage(), width / 13, height / 32, getSize(74), getSize(99), this);
		for (int i = 0; i < Solitaire.deck.getTop().size(); i++) {
			Card card = Solitaire.deck.getTop().get(i);
			g.drawImage(card.getCardImage(), width / 5 + (i * (width / 20)), height / 32, getSize(74), getSize(99),
					this);
		}

		// Card stacks
		for (int i = 0; i < 7; i++) {
			Cell cell = Cell.getCell(i + 1);
			List<Card> cards = cell.getCards();
			int back = cell.getBacks();
			g.drawImage(Card.CELL.getCardImage(), i * (width / 7 - 10) + (width / 21), height / 5, getSize(74),
					getSize(99), this);
			for (int n = 0; n < cards.size(); n++) {
				Card card;
				if (n >= back)
					card = cards.get(n);
				else
					card = Card.BACK;
				g.drawImage(card.getCardImage(), i * (width / 7 - 10) + (width / 21), height / 5 + (n * (height / 25)),
						getSize(74), getSize(99), this);
			}
		}

		// Hand
		int i = 0;
		for (Card card : Solitaire.hand) {
			g.drawImage(card.getCardImage(), MouseInfo.getPointerInfo().getLocation().x - frame.getLocationOnScreen().x,
					MouseInfo.getPointerInfo().getLocation().y - frame.getLocationOnScreen().y - 27
							+ (i * (height / 25)),
					getSize(74), getSize(100), this);
			i++;
		}

		// for (Hitbox hitbox : Solitaire.hitboxes) {Rectangle r =
		// hitbox.getLabel().getBounds(); g.drawRect(r.x, r.y, r.width, r.height); }
	}

	public static Painter getPainter() {
		return painter;
	}
	
	public int getSize(int number) {
		if (size == 1)
			number = (int) Math.floor(height / (640 / number));
		if (size == 2)
			number = (int) Math.floor(width / (640 / number));
		return number;
	}
}
package solitaire;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;

import solitaire.card.Card;
import solitaire.card.Suit;
import solitaire.location.Location;
import solitaire.location.Type;

public class Solitaire {

	private JFrame frame;
	public static List<Cell> cells = new ArrayList<Cell>();
	public static List<Placemat> placemats = new ArrayList<Placemat>();
	public static Deck deck;
	public static List<Card> hand = new ArrayList<Card>();
	public static Location handLocation;
	public static List<Hitbox> hitboxes = new ArrayList<Hitbox>();
	public static Texture texture = Texture.REALISTIC;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Solitaire window = new Solitaire();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Solitaire() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		int width = 840;
		int height = 640;
		frame = new JFrame();
		frame.setBounds(100, 100, width, height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("-| Solitaire |-");
		frame.setIconImage(Card.CKS.getCardImage());
		frame.setLayout(null);
		frame.setSize(width, height);

		List<Card> cards = getShuffledCards();
		arrangeCellStacks(cards);

		for (Suit suit : Suit.values())
			placemats.add(new Placemat(suit));

		List<Card> deck = new ArrayList<Card>();
		for (Card card : cards)
			deck.add(card);

		Solitaire.deck = new Deck(deck);

		// Hitboxes
		hitboxes.add(new Hitbox(new Location(Type.DECK), new JLabel(), frame));
		hitboxes.add(new Hitbox(new Location(Type.TOP), new JLabel(), frame));
		for (Suit suit : Suit.values())
			hitboxes.add(new Hitbox(new Location(Placemat.getPlacemat(suit)), new JLabel(), frame));

		for (int k = 0; k < 7; k++) {
			hitboxes.add(new Hitbox(new Location(Cell.getCell(k + 1), 0), new JLabel(), frame));
			for (int n = 0; n < 13 + k; n++)
				hitboxes.add(new Hitbox(new Location(Cell.getCell(k + 1), n + 1), new JLabel(), frame));
		}

		frame.add(new Painter(frame));

		// Setting the frame location to the middle of the screen
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((screen.getWidth() - frame.getWidth()) / 2);
		int y = (int) ((screen.getHeight() - frame.getHeight()) / 2);
		frame.setLocation(x, y);

		new Thread() {
			public void run() {
				while (true)
					Painter.getPainter().repaint();
			}
		}.start();

		frame.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent event) {

			}

			@Override
			public void keyReleased(KeyEvent event) {
				char key = event.getKeyChar();
				if (key == 't')
					switch (texture) {
					case BIT:
						texture = Texture.REALISTIC;
						break;
					case REALISTIC:
						texture = Texture.BIT;
						break;
					}
			}

			@Override
			public void keyTyped(KeyEvent event) {

			}
		});
	}

	private List<Card> getShuffledCards() {
		List<Card> cards = new ArrayList<Card>();
		for (Card card : Card.values()) {
			if (!card.isAnExtra())
				cards.add(card);
		}

		Collections.shuffle(cards);
		return cards;
	}

	private void arrangeCellStacks(List<Card> cards) {
		List<List<Card>> stacks = new ArrayList<List<Card>>();
		List<Integer> backs = new ArrayList<Integer>();
		for (int i = 1; i < 8; i++) {
			List<Card> sList = new ArrayList<Card>();
			for (int n = 0; n < i; n++)
				sList.add(cards.remove(n));
			stacks.add(sList);
			backs.add(i - 1);
		}
		for (int i = 1; i < 8; i++) {
			Cell cell = new Cell(i, stacks.get(i - 1), backs.get(i - 1));
			cells.add(cell);
		}
	}
}

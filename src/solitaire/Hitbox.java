package solitaire;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;

import solitaire.card.Card;
import solitaire.location.Location;
import solitaire.location.Type;

public class Hitbox {
	private JLabel label;
	private Location location;
	private JFrame frame;

	public Hitbox(Location location, JLabel label, JFrame frame) {
		this.label = label;
		this.location = location;
		this.frame = frame;
		frame.add(label);

		setLabel();
	}

	private void setLabel() {
		label.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent event) {

			}

			@Override
			public void mouseEntered(MouseEvent event) {

			}

			@Override
			public void mouseExited(MouseEvent event) {

			}

			@Override
			public void mousePressed(MouseEvent event) {
				if (event.getButton() == MouseEvent.BUTTON1)
					contactGameManager(event);
				if (event.getButton() == MouseEvent.BUTTON3) {
					if (location.getLocationType() == Type.CELL) {
						Cell cell = (Cell) location.getLocation();
						if (cell.getCards().size() == location.getCellNumber())
							GameManager.onCellRightClick(cell);
					}

					if (location.getLocationType() == Type.TOP)
						if (!Solitaire.deck.getTop().isEmpty())
							GameManager.onTopRightClick();
				}
			}

			@Override
			public void mouseReleased(MouseEvent event) {
				if (!(event.getButton() == MouseEvent.BUTTON1))
					return;

				for (Hitbox hitbox : Solitaire.hitboxes)
					if (hitbox.getLabel().getBounds().contains(
							event.getLocationOnScreen().x - frame.getLocationOnScreen().x,
							event.getLocationOnScreen().y - frame.getLocationOnScreen().y - 40)) {
						if (hitbox.getLocation().getLocationType() == Type.PLACEMAT) {
							if (Solitaire.hand.size() != 1)
								break;
							Card card = Solitaire.hand.get(0);
							Placemat placemat = (Placemat) hitbox.getLocation().getLocation();
							if (card.getCardNumber() == placemat.getNumber() + 1
									&& card.getCardSuit() == placemat.getType()) {
								GameManager.onPlacematDrop(placemat);
							}
							break;
						}

						if (hitbox.getLocation().getLocationType() == Type.CELL) {
							if (hitbox.getLocation().getCellNumber() == 0) {
								Card card = Solitaire.hand.get(0);
								if (card.getCardNumber() != 13)
									break;
								Cell cell = (Cell) hitbox.getLocation().getLocation();
								GameManager.onEmptyCellKingDrop(cell);

								break;
							}
							Card card = Solitaire.hand.get(0);
							Cell cell = (Cell) hitbox.getLocation().getLocation();
							Card top = cell.getCards().get(cell.getCards().size() - 1);
							if (card.getCardSuit().getColor() == top.getCardSuit().getColor()
									|| card.getCardNumber() + 1 != top.getCardNumber())
								break;
							GameManager.onCellDrop(cell);
						}
					}

				Location handLocation = Solitaire.handLocation;
				if (handLocation == null)
					return;
				if (handLocation.getLocationType() == Type.TOP) {
					Solitaire.deck.getTop().add(Solitaire.hand.get(0));
					Solitaire.hand.clear();
					Solitaire.handLocation = null;
					return;
				}

				if (handLocation.getLocationType() == Type.PLACEMAT) {
					Placemat placemat = (Placemat) handLocation.getLocation();
					placemat.setNumber(placemat.getNumber() + 1);
					Solitaire.hand.clear();
					Solitaire.handLocation = null;
				}

				if (handLocation.getLocationType() == Type.CELL) {
					Cell cell = (Cell) handLocation.getLocation();
					List<Card> cards = new ArrayList<Card>();
					cards.addAll(cell.getCards());
					cards.addAll(Solitaire.hand);
					cell.setCards(cards);
					Solitaire.handLocation = null;
					Solitaire.hand.clear();
				}
			}

		});
	}

	private void contactGameManager(MouseEvent event) {
		Type type = location.getLocationType();
		if (type == Type.DECK) {
			GameManager.onDeckClick();
			return;
		}

		if (type == Type.TOP) {
			GameManager.onTopClick();
			return;
		}

		if (type == Type.PLACEMAT) {
			GameManager.onPlacematClick((Placemat) location.getLocation());
		}

		if (type == Type.CELL) {
			GameManager.onCellClick((Cell) location.getLocation(), location.getCellNumber());
		}
	}

	public Location getLocation() {
		return location;
	}

	public JLabel getLabel() {
		return label;
	}
}

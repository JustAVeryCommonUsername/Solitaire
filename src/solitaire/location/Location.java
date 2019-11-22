package solitaire.location;

import solitaire.Cell;
import solitaire.Placemat;

public class Location {

	private Type type;
	private Object location;
	private int number;

	public Location(Cell cell, int number) {
		type = Type.CELL;
		location = cell;
		this.number = number;
	}

	public Location(Type type) {
		this.type = type;
	}

	public Location(Placemat placemat) {
		type = Type.PLACEMAT;
		location = placemat;
	}

	public Type getLocationType() {
		return type;
	}

	public Object getLocation() {
		return location;
	}

	public int getCellNumber() {
		return number;
	}
}

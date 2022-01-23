package a01a.e2;

import java.util.HashMap;
import java.util.Map;

public class ControllerImpl implements Controller {

	private final int size;

	private static enum CellValue {
		CELL_USED_1, CELL_USED_2, UNUSED_CELL
	}

	private boolean firstPlayerTurn = true;
	private final Map<Pair<Integer, Integer>, CellValue> map = new HashMap<>();

	public ControllerImpl(int size) {
		this.size = size;
	}

	private void nextTurn() {
		this.firstPlayerTurn = !this.firstPlayerTurn;
	}

	@Override
	public GridValue checkValidity(int position) {

		var coord = convertPosition(position);
		if (coord == null) {
			return GridValue.NOT_VALID;
		}

		if (map.size() == (this.size * this.size)) {
			// La tabella è piena
			return GridValue.FULL_GRID;
		}

		if (map.containsKey(coord)) {
			// Se contiene già la chiave, non è una cella vuota
			return GridValue.NOT_VALID;
		}

		// Altrimenti potrebbe essere una posizione valida
		if (coord.getY() == size - 1 || map.containsKey(new Pair<>(coord.getX(), coord.getY() + 1))) {
			// E' la cella in fondo
			map.put(coord, getTurn());
			nextTurn();
			return this.firstPlayerTurn == true ? GridValue.VALID_2 : GridValue.VALID_1;
		}

		return GridValue.NOT_VALID;
	}

	private Pair<Integer, Integer> convertPosition(int position) {
		if (position < (this.size * this.size) && position >= 0) {
			return new Pair<Integer, Integer>(position % size, position / size);
		}
		return null;
	}

	private CellValue getTurn() {
		if (firstPlayerTurn == true)
			return CellValue.CELL_USED_1;
		else
			return CellValue.CELL_USED_2;
	}

	@Override
	public Win hasWon() {

		for (var elem : map.keySet()) {
			// I suoi vicini in verticale sono uguali a lui?
			// Se non ha vicini->no
			Pair<Integer, Integer> dx = new Pair<>(elem.getX(), elem.getY() + 1);
			Pair<Integer, Integer> sx = new Pair<>(elem.getX(), elem.getY() - 1);
			Pair<Integer, Integer> up = new Pair<>(elem.getX() + 1, elem.getY());
			Pair<Integer, Integer> down = new Pair<>(elem.getX() - 1, elem.getY());

			if (map.getOrDefault(dx, CellValue.UNUSED_CELL) == map.get(elem)
					&& map.getOrDefault(sx, CellValue.UNUSED_CELL) == map.get(elem)) {
				return map.get(elem) == CellValue.CELL_USED_1 ? Win.WIN1 : Win.WIN2;
			}

			if (map.getOrDefault(up, CellValue.UNUSED_CELL) == map.get(elem)
					&& map.getOrDefault(down, CellValue.UNUSED_CELL) == map.get(elem)) {
				return map.get(elem) == CellValue.CELL_USED_1 ? Win.WIN1 : Win.WIN2;
			}
		}
		return Win.NOT_WIN;
	}

}

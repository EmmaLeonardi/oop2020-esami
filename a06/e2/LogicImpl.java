package a06.e2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LogicImpl implements Logic {

	private final List<String> row;
	private int projPosition = 0;
	private final int size;
	private final static int NBLOCKS = 3;
	private final static String VOID = "";
	private final static String PROJ = "*";

	public LogicImpl(int size, int min, int max) {
		this.size = size;
		row = new ArrayList<>();
		for (int i = 0; i < this.size; i++) {
			row.add(VOID);
		}
		Random random = new Random();
		int i = 0;
		while (i != NBLOCKS) {
			int randomPos = random.nextInt(size - 1) + 1;
			if (row.get(randomPos).equals(VOID)) {
				row.set(randomPos, (random.nextInt(max) + min) + "");
			}
			i++;
		}
		projPosition = 0;
		row.set(projPosition, PROJ);
	}

	@Override
	public void tick() {
		if (projPosition + 1 < size) {
			if (row.get(projPosition + 1).equals(VOID)) {
				row.set(projPosition, VOID);
				projPosition++;
			} else {
				// E' un blocco
				int value = Integer.parseInt(row.get(projPosition + 1));
				if (value - 1 == 0) {
					// Ho distrutto il blocco
					row.set(projPosition + 1,VOID);
					row.set(projPosition, VOID);
				} else {
					row.set(projPosition + 1, value - 1 + "");
					row.set(projPosition, VOID);
				}
				projPosition = 0;
			}
			row.set(projPosition, PROJ);
		}

	}

	@Override
	public List<String> getPositions() {
		return List.copyOf(row);
	}

	@Override
	public boolean isOver() {
		for(var elem: row) {
			if(elem.equals(VOID)==false&&elem.equals(PROJ)==false) {
				//Se non è un proiettile e non è il vuoto-> è un blocco, quindi 
				return false;
			}
		}
		return true;
	}

}

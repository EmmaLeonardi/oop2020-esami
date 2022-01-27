package a03a.e2;

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {

	private static final long serialVersionUID = -6218820567019985015L;
	private final Map<JButton, Pair<Integer, Integer>> cells = new HashMap<>();
	private final Logic logic;

	public GUI(int size) {
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(100 * size, 100 * size);
		this.logic = new LogicImpl(size);

		JPanel grid = new JPanel(new GridLayout(size, size));
		this.getContentPane().add(BorderLayout.CENTER, grid);

		ActionListener el = e -> {
			var button = (JButton) e.getSource();
			if (!logic.move(this.cells.get(button))) {
				JOptionPane.showMessageDialog(grid, "You won!");
			}
			refreshGrid();

		};

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				final JButton jb = new JButton(" ");
				this.cells.put(jb, new Pair<>(j, i));
				grid.add(jb);
				jb.addActionListener(el);
			}
		}
		refreshGrid();
		this.setVisible(true);
	}

	private void refreshGrid() {
		cells.entrySet().forEach(p -> {
			// Il carattere
			if (logic.enemiesPos().contains(p.getValue())) {
				p.getKey().setText("*");
			} else if (logic.getTowerPos().equals(p.getValue())) {
				p.getKey().setText("R");
			} else {
				p.getKey().setText("");
			}

			// Se è una mossa possibile
			if (logic.possibleMoves().contains(p.getValue())) {
				p.getKey().setEnabled(true);
			} else {
				p.getKey().setEnabled(false);
			}
		});
	}

}

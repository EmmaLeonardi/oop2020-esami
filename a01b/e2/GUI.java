package a01b.e2;

import javax.swing.*;

import a01b.e2.Logic.Pieces;

import java.util.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {

	private static final long serialVersionUID = -6218820567019985015L;
	private final Map<JButton, Pair<Integer, Integer>> cells = new HashMap<>();
	private final Logic logic;

	public GUI(int size) {
		logic = new LogicImpl(size);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(100 * size, 100 * size);

		JPanel panel = new JPanel(new GridLayout(size, size));
		this.getContentPane().add(panel);

		ActionListener al = e -> {
			var button = (JButton) e.getSource();
			var position = cells.get(button);
			if (logic.moveIfValid(position)) {
				cells.keySet().forEach(a -> {
					if (a.getText().equals(Pieces.K.toString())) {
						a.setText("");
					}
				});
				button.setText(Pieces.K.toString());
			} else {
				JOptionPane.showMessageDialog(panel, "Invalid move");
			}
			cells.forEach((b, c) -> {
				if (logic.getEnemiesCoord().contains(c)) {
					b.setText(Pieces.P.toString());
				}
			});
			if (logic.getEnemiesCoord().isEmpty()) {
				cells.keySet().forEach(b -> b.setEnabled(false));
				JOptionPane.showMessageDialog(panel, "You won!!");
			}

		};

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				final JButton jb = new JButton(" ");
				this.cells.put(jb, new Pair<>(j, i));
				jb.addActionListener(al);
				panel.add(jb);
				// Devo mettere il re
				if (i == size - 1 && j == size - 1) {
					jb.setText(Pieces.K.toString());
				}
			}
		}

		// Inizializzo i pedoni
		cells.forEach((b, c) -> {
			if (logic.getEnemiesCoord().contains(c)) {
				b.setText(Pieces.P.toString());
			}
		});

		this.setVisible(true);
	}

}

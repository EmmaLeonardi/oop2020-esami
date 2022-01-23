package a01a.e2;

import javax.swing.*;

import a01a.e2.Controller.GridValue;
import a01a.e2.Controller.Win;

import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {

	private static final long serialVersionUID = -6218820567019985015L;
	private final List<JButton> cells = new ArrayList<>();
	private final Controller controller;

	public GUI(int size) {
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(100 * size, 100 * size);
		this.controller = new ControllerImpl(size);

		JPanel panel = new JPanel(new GridLayout(size, size));
		this.getContentPane().add(panel);

		ActionListener al = e -> {
			var button = (JButton) e.getSource();
			var position = cells.indexOf(button);
			var out = controller.checkValidity(position);
			switch (out) {
			case FULL_GRID:
				// Fine partita
				JOptionPane.showMessageDialog(panel, "Full grid, nobody won");
				break;
			case NOT_VALID:
				JOptionPane.showMessageDialog(panel, "Move not valid");
				break;
			case VALID_1:
			case VALID_2:
				var chip = out == GridValue.VALID_1 ? "O" : "X";
				button.setText(chip);
				var win = controller.hasWon();
				if (win == Win.WIN1) {
					JOptionPane.showMessageDialog(panel, "User 1 won!!");
					// Ha vinto 1
					// Fine partita
				} else if (win == Win.WIN2) {
					JOptionPane.showMessageDialog(panel, "User 2 won!!");
					// Ha vinto 2
					// Fine partita
				} else {
					// Nessuno ha vinto
				}
				break;
			default:
				// No

			}

		};

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				final JButton jb = new JButton(" ");
				this.cells.add(jb);
				jb.addActionListener(al);
				panel.add(jb);
			}
		}
		this.setVisible(true);
	}

}

package a02b.e2;

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    
    private static final long serialVersionUID = -6218820567019985015L;
    private final Map<JButton,Pair<Integer,Integer>> map = new HashMap<>();
    private final Logic logic;
    
    public GUI(int size) {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(100*size, 100*size);
        this.logic=new LogicImpl(size);
        
        JPanel grid = new JPanel(new GridLayout(size,size));
        this.getContentPane().add(BorderLayout.CENTER,grid);
        JButton up = new JButton("UP");
        JButton down = new JButton("DOWN");
        this.getContentPane().add(BorderLayout.NORTH,up);
        this.getContentPane().add(BorderLayout.SOUTH,down);
        
        up.addActionListener(e -> {
        	logic.up();
        	refreshGrid();
        });
        
        down.addActionListener(e -> {
        	logic.down();
        	refreshGrid();
        });
        
        ActionListener al = e -> {
        	var jb = (JButton)e.getSource();
        	logic.addStar(map.get(jb));
        	refreshGrid();
        	
        };
                
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
                final JButton jb = new JButton(" ");
                this.map.put(jb,new Pair<>(j,i));
                grid.add(jb);
                jb.addActionListener(al);
            }
        }
        refreshGrid();
        this.setVisible(true);
    }    
    
    
    private void refreshGrid() {
    	map.keySet().forEach(a->a.setText(""));
    	map.keySet().forEach(a->{
    		if(logic.starCoords().contains(map.get(a))) {
    			a.setText("*");
    		}
    	});
    }
}

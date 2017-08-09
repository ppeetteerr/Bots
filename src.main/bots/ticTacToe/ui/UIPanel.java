package bots.ticTacToe.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import bots.ticTacToe.game.Position;

public class UIPanel extends JFrame {

  private final JPanel contentPane;
  private final int size;
  private final JLabel[][] labels;
  private Position clickPos;
  /**
   * Create the frame.
   */
  public UIPanel(final int size) {
    this.size = size;
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    final int width = 50;
    setBounds(100, 100, width*size, width*size);
    contentPane = new JPanel();
    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    setContentPane(contentPane);
    contentPane.setLayout(new GridLayout(size, size, 0, 0));
    
    labels = new JLabel[size][size];
    for (int row = 0; row < size; row++) {
      for (int col = 0; col < size; col++) {
        final JLabel label = new JLabel();
        label.setBorder(BorderFactory.createLineBorder(Color.black));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(new Font("Serif", Font.PLAIN, width-10));
        addClickHandler(label, row, col);
        labels[row][col] = label;
        contentPane.add(label);
      }
    }
    
  }
  
  private void addClickHandler(final JLabel label, final int row, final int col) {
    label.addMouseListener(new MouseAdapter() {
      
      @Override
      public void mouseClicked(final MouseEvent e) {
        clickPos = new Position(row, col);
      }
    });
  }
  
  public void render(final int[][] grid) {
    for (int row = 0; row < size; row++) {
      for (int col = 0; col < size; col++) {
        labels[row][col].setText(convert(grid[row][col]));
      }
    }
  }
  
  public Position getClickPos(){
    clickPos = null;
    while(clickPos==null) {
      try {
        Thread.sleep(100);
      } catch (final InterruptedException e) {
        e.printStackTrace();
      }
    }
    
    labels[clickPos.row][clickPos.col].setText("X");
    return clickPos;
  }
  
  private String convert(final int i) {
    switch (i) {
    case 0:
      return "X";
    case 1:
      return "O";
    default:
      return "";
    }
  }
  
}

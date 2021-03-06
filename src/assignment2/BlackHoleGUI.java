package assignment2;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class BlackHoleGUI {
    private JFrame frame;
    private BoardGUI boardGUI;

    private final int INITIAL_BOARD_SIZE = 5;

    /**Creates the frame with JMenu and BoardGUI.
     */
    public BlackHoleGUI() {
        frame = new JFrame("Black Hole");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        boardGUI = new BoardGUI(INITIAL_BOARD_SIZE);
        frame.getContentPane().add(boardGUI.getBoardPanel(), BorderLayout.CENTER);

        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);
        JMenu gameMenu = new JMenu("Menu");
        menuBar.add(gameMenu);
        JMenu newMenu = new JMenu("Board Size");
        gameMenu.add(newMenu);
        int[] boardSizes = new int[]{5, 7, 9};
        for (int boardSize : boardSizes) {
            JMenuItem sizeMenuItem = new JMenuItem(boardSize + "x" + boardSize);
            newMenu.add(sizeMenuItem);
            sizeMenuItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    frame.getContentPane().remove(boardGUI.getBoardPanel());
                    boardGUI = new BoardGUI(boardSize);
                    frame.getContentPane().add(boardGUI.getBoardPanel(), BorderLayout.CENTER);
                    frame.pack();
                }
            });
        }
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        gameMenu.add(exitMenuItem);
        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                System.exit(0);
            }
        });
        
        frame.pack();
        frame.setVisible(true);
    }
}

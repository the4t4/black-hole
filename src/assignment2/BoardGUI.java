package assignment2;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class BoardGUI {
    
    private Board board;
    private JButton[][] buttons;
    private JPanel boardPanel;
    
    private int player1Spaceships;
    private int player2Spaceships;
    private Point selectedField;   
    private int turn;
    
    /**Initializes fields, creates GUI, creates buttons and binds ButtonListeners and ArrowKeyListeners to them, 
     * and updates the GUI accordingly.
     * 
     * @param boardSize 
     */
    public BoardGUI(int boardSize) {
        board = new Board(boardSize);
        boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(boardSize, boardSize));
        buttons = new JButton[boardSize][boardSize];
        player1Spaceships = boardSize-1;
        player2Spaceships = boardSize-1;
        selectedField = null;
        turn = 0;
        for (int i = 0; i < board.getBoardSize(); ++i) {
            for (int j = 0; j < board.getBoardSize(); ++j) {
                JButton button = new JButton();
                button.addActionListener(new ButtonListener(i, j));
                button.addKeyListener(new ArrowKeyListener());
                button.setPreferredSize(new Dimension(60, 60));
                buttons[i][j] = button;
                boardPanel.add(button);
            }
        }
        refresh();
    }
    
    
    public JPanel getBoardPanel() {
        return boardPanel;
    }
    
    /** Checks if end of game conditions are met.
     */
    public boolean isOver() {
        if(player1Spaceships <= (board.getBoardSize()-1)/2 || player2Spaceships <= (board.getBoardSize()-1)/2) 
            return true;
        else return false;
    }
    
    /** Resets class fields to initial values.
     */
    public void reset(){
        board = new Board(board.getBoardSize());
        player1Spaceships = board.getBoardSize()-1;
        player2Spaceships = board.getBoardSize()-1;
        selectedField = null;
        turn = 0;
        refresh();
    }
    
    
    /** Updates the visuals on the board.
     */
    public void refresh() {
        for (int i = 0; i < board.getBoardSize(); ++i) {
            for (int j = 0; j < board.getBoardSize(); ++j) {
                Field field = board.get(i, j);
                JButton button = buttons[i][j];
                if (!field.isSpaceship() && !field.isBlackHole()) {
                    button.setBackground(Color.WHITE);
                }
                else{
                    button.setBackground(field.getColor());
                }
            }
        }

        if (isOver()) {
            JLabel gameOver = new JLabel(((player1Spaceships < player2Spaceships) ? "Player 1" : "Player 2") + " wins!");
            int result = JOptionPane.showConfirmDialog(boardPanel, gameOver, "Game over", JOptionPane.PLAIN_MESSAGE);
            if(result == 0) reset();
        }
    }
    
    class ButtonListener implements ActionListener {

        private int x, y;

        public ButtonListener(int x, int y) {
            this.x = x;
            this.y = y;
        }
        
        /** Selects a spaceship the user clicks on.
         * 
         * @param e ActionEvent
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if(turn % 2 == 0){
                if (board.get(x, y).isSpaceship() && board.get(x, y).getColor() == Color.RED) {
                    selectedField = new Point(x, y);
                }
                else{
                    selectedField = null;
                }
            }
            else{
                if (board.get(x, y).isSpaceship() && board.get(x, y).getColor() == Color.BLUE) {
                    selectedField = new Point(x, y);
                }
                else{
                    selectedField = null;
                }
            }
        }
    }
    
    /** Moves a selected spaceship along a chosen direction until it hits the edge, another spaceship, or the black hole.
     * Arrow keys and WASD keys determine the direction.
     */
    class ArrowKeyListener implements KeyListener{

        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
            if(selectedField != null){
                if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W){
                    int x = Math.max(selectedField.x-1, 0);
                    int y = selectedField.y;
                    while(!board.get(x, y).isSpaceship() && !board.get(x, y).isBlackHole() && x > 0){
                        x--;
                    }
                    board.get(selectedField.x,selectedField.y).setSpaceship(false);
                    if(board.get(x, y).isBlackHole()){
                        if(board.get(selectedField.x, selectedField.y).getColor() == Color.RED){
                            player1Spaceships--;
                        }
                        else player2Spaceships--;
                    }
                    else{
                       if(board.get(x, y).isSpaceship()) x++;
                       if(x == selectedField.x) turn--;
                       
                       board.get(x, y).setSpaceship(true);
                       board.get(x, y).setColor(board.get(selectedField.x,selectedField.y).getColor());
                    }
                    selectedField = null;
                    turn++;
                }
                else if(e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S){
                    int x = Math.min(selectedField.x+1, board.getBoardSize()-1);
                    int y = selectedField.y;
                    while(!board.get(x, y).isSpaceship() && !board.get(x, y).isBlackHole() && x < board.getBoardSize()-1){
                        x++;
                    }
                    board.get(selectedField.x,selectedField.y).setSpaceship(false);
                    if(board.get(x, y).isBlackHole()){
                        if(board.get(selectedField.x, selectedField.y).getColor() == Color.RED){
                            player1Spaceships--;
                        }
                        else player2Spaceships--;
                    }
                    else{
                       if(board.get(x, y).isSpaceship()) x--;
                       if(x == selectedField.x) turn--;

                       board.get(x, y).setSpaceship(true);
                       board.get(x, y).setColor(board.get(selectedField.x,selectedField.y).getColor());
                    }
                    selectedField = null;
                    turn++;
                }
                else if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A){
                    int x = selectedField.x;
                    int y = Math.max(selectedField.y-1, 0);
                    while(!board.get(x, y).isSpaceship() && !board.get(x, y).isBlackHole() && y>0){
                        y--;
                    }
                    board.get(selectedField.x,selectedField.y).setSpaceship(false);
                    if(board.get(x, y).isBlackHole()){
                        if(board.get(selectedField.x, selectedField.y).getColor() == Color.RED){
                            player1Spaceships--;
                        }
                        else player2Spaceships--;
                    }
                    else{
                       if(board.get(x, y).isSpaceship()) y++;
                       if(y == selectedField.y) turn--;

                       board.get(x, y).setSpaceship(true);
                       board.get(x, y).setColor(board.get(selectedField.x,selectedField.y).getColor());
                    }
                    selectedField = null;
                    turn++;
                }
                else if(e.getKeyCode() == KeyEvent.VK_RIGHT  || e.getKeyCode() == KeyEvent.VK_D){
                    int x = selectedField.x;
                    int y = Math.min(selectedField.y+1, board.getBoardSize()-1);
                    while(!board.get(x, y).isSpaceship() && !board.get(x, y).isBlackHole() && y < board.getBoardSize()-1){
                        y++;
                    }
                    board.get(selectedField.x,selectedField.y).setSpaceship(false);
                    if(board.get(x, y).isBlackHole()){
                        if(board.get(selectedField.x, selectedField.y).getColor() == Color.RED){
                            player1Spaceships--;
                        }
                        else player2Spaceships--;
                    }
                    else{
                       if(board.get(x, y).isSpaceship()) y--;
                       if(y == selectedField.y) turn--;

                       board.get(x, y).setSpaceship(true);
                       board.get(x, y).setColor(board.get(selectedField.x,selectedField.y).getColor());
                    }
                    selectedField = null;
                    turn++;
                }
                refresh();
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
        }
    }
}


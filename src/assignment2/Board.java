package assignment2;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;


/**Initializes fields, sets black hole, and sets spaceships.
 */
public class Board {
    private Field[][] board;
    private int boardSize;
    private ArrayList<Point> upperDiagonal;

    public Board(int boardSize) {
        this.boardSize = boardSize;
        this.upperDiagonal = new ArrayList<>();

        board = new Field[boardSize][boardSize];
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                board[i][j] = new Field();
            }
        }
        
        Field blackHole = get(boardSize/2, boardSize/2);
        blackHole.setBlackHole();
        blackHole.setColor(Color.BLACK);
        
        for(int i = 0; i < boardSize; i++){
            for(int j = i+1; j<boardSize; j++){
                if(i != boardSize/2 && j !=boardSize/2){
                upperDiagonal.add(new Point(i,j));
                }
            }
        }
        
        Collections.shuffle(upperDiagonal);
        
        for (int i = 0; i < boardSize-1; i++) {
            Point p = upperDiagonal.get(i);
            Field spaceship2 = get(p.x, p.y);
            spaceship2.setSpaceship(true);
            spaceship2.setColor(Color.BLUE);
            
            int xCord = boardSize-1 - p.x;
            int yCord = boardSize-1 - p.y;
            Field spaceship1 = get(xCord, yCord);
            spaceship1.setSpaceship(true);
            spaceship1.setColor(Color.RED);
        }
        
    }
    
    public Field get(int x, int y) {
        return board[x][y];
    }
    
    public int getBoardSize() {
        return boardSize;
    }
}

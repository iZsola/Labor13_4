package sample;

import javafx.application.Platform;
import javafx.scene.control.Alert;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class Controller implements ActionListener{
    private boolean player1turn;
    private boolean player2turn;
    private int boardSize;
    private final int symbolNr;
    private int count;
    private char[][] grid;
    private JButton[][] gameGrid;
    private boolean gameOver=false;

    public Controller(int size, int symbol)
    {
        this.boardSize=size;
        this.symbolNr=symbol;
        count=0;
        runGame();
    }

    public boolean getPlayer1turn() {
        return player1turn;
    }

    public boolean getPlayer2turn() {
        return player2turn;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public int getBoardSize() {
        return boardSize;
    }

    public void setPlayer1turn(boolean player1turn) {
        this.player1turn = player1turn;
    }

    public void setPlayer2turn(boolean player2turn) {
        this.player2turn = player2turn;
    }

    public void setBoardSize(int boardSize) {
        this.boardSize = boardSize;
    }

    @Override
    public void actionPerformed(java.awt.event.ActionEvent e) {
        count++;
        JButton b = (JButton) e.getSource();
        boolean quit=false;
        for (int i=0;i<getBoardSize() && !quit;i++)
        {
            for (int j=0;j<getBoardSize() && !quit;j++)
            {
                if (b==gameGrid[i][j])
                {
                    grid[i][j]= getPlayer1turn() ? 'X' : 'O';
                    if (getPlayer1turn())
                    {
                        ((JButton) e.getSource()).setText("X");
                        ((JButton) e.getSource()).setBackground(Color.cyan);
                        setPlayer1turn(false);
                        setPlayer2turn(true);
                    }
                    else
                    {
                        ((JButton) e.getSource()).setText("O");
                        ((JButton) e.getSource()).setBackground(Color.PINK);
                        setPlayer1turn(true);
                        setPlayer2turn(false);
                    }
                    ((JButton) e.getSource()).setEnabled(false);
                    quit=true;
                    if (checkWin(getPlayer1turn() ? 'O' : 'X'))//inverted
                    {
                        this.gameOver=true;
                        System.out.println("VAN GYOZTES\n");//Innen tudom, hogy belelpik
                    }
                }
            }
        }
        if (count==boardSize*boardSize)//Ez se megy jol
        {
            Platform.setImplicitExit(false);
            Platform.runLater(() -> {
                AlertBox.display("GAME OVER", "Draw game!");
                System.exit(2);
            });
        }
    }

    private void runGame()
    {
        JFrame gameFrame=new JFrame();
        JPanel gamePanel= new JPanel();
        int size=getBoardSize();
        gameGrid=new JButton[size][size];
        grid = new char[size][size];
        setPlayer1turn(true);

        for (int i=0;i<size;i++)
        {
            for (int j=0;j<size;j++)
            {
                gameGrid[i][j] = new JButton();
                gamePanel.add(gameGrid[i][j]);
                gameGrid[i][j].setBackground(Color.black);
                gameGrid[i][j].addActionListener(this);
                grid[i][j] = ' ';
                gameGrid[i][j].setFont(new Font("Verdana", Font.BOLD, 30));
            }
        }
        gamePanel.setLayout(new GridLayout(size, size));
        gameFrame.add(gamePanel);
        gameFrame.setVisible(true);
        gameFrame.setSize(500, 500);
        gameFrame.setLayout(new GridLayout());
        gameFrame.setTitle("Tic Tac Toe");
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    public void PrintSomething()
    {
        System.out.println("Something");
    }

    private boolean checkWin(char player)
    {
        return (checkHorizontalWin(player) || checkVerticalWin(player) || checkDiagonalWin(player));
    }

    public boolean checkHorizontalWin(char player)
    {
        for (int i=0;i<this.boardSize;i++)
        {
            int act=0;
            for (int j=0;j<this.boardSize;j++)
            {
                while (act<symbolNr && j<this.boardSize && grid[i][j]==player)
                {
                    act++;
                    j++;
                }
                if (act>=symbolNr)
                    return true;
                act=0;
            }
        }
        return false;
    }

    public boolean checkVerticalWin(char player)
    {
        for (int i=0;i<this.boardSize;i++)
        {
            int act=0;
            for (int j=0;j<this.boardSize;j++)
            {
                while (act<symbolNr && j<this.boardSize && grid[i][j]==player)
                {
                    act++;
                    j++;
                }
                if (act>=symbolNr)
                    return true;
                act=0;
            }
        }
        return false;
    }

    public boolean checkDiagonalWin(char player)
    {
        //TOP LEFT TO DOWN RIGHT
        for(int j=this.boardSize-symbolNr;j>=0;j--)
        {
            int jj=j, i=0, act=0;

            while (jj < this.boardSize && i < this.boardSize && act<symbolNr)
            {
                if (grid[i][jj]==player)
                    act++;
                else
                    act=0;
                i++;
                jj++;
            }
            if (act>=symbolNr)
                return true;
        }

        for (int i=this.boardSize-symbolNr;i>0;i--)
        {
            int ii=i, j=0, act=0;
            while (ii < this.boardSize && j < this.boardSize && act < symbolNr)
            {
                if (grid[ii][j]==player)
                    act++;
                else
                    act=0;
                ii++;
                j++;
            }
            if (act>=symbolNr)
                return true;
        }
        //TOP LEFT TO DOWN RIGHT

        //TOP RIGHT TO DOWN LEFT
        for (int j=symbolNr-1;j<this.boardSize;j++)
        {
            int jj=j, i=0, act=0;
            while (jj > 0 && i < this.boardSize && act<symbolNr)
            {
                if (grid[i][jj]==player)
                    act++;
                else
                    act=0;
                i++;
                jj--;
            }
            if (act>=symbolNr)
                return true;
        }

        for (int i=1;i<this.boardSize;i++)
        {
            int ii=i, j=this.boardSize-1, act=0;
            while(ii < this.boardSize && j > 0 && act<symbolNr)
            {
                if (grid[ii][j]==player)
                    act++;
                else
                    act=0;
                ii++;
                j--;
            }
            if (act>=symbolNr)
                return true;
        }
        //TOP RIGHT TO DOWN LEFT
        return false;
    }

}

package org.example.snakes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;


public class EnemySnakeGame extends JPanel implements ActionListener {
    Timer gameLoop;
    boolean gameOver = false;
    int boardWidth=600, boardHeight=600, velocityY, velocityX, tileSize = 25;

    EnemySnakeGame.Tile snakeHead;
    EnemySnakeGame.Tile food;
    Random random;
    ArrayList<EnemySnakeGame.Tile> snakeBody;
    Boolean isLookingForCoordinationsOfFood = true;

    public EnemySnakeGame(){
        setPreferredSize(new Dimension(this.boardWidth, this.boardHeight));
        setBackground(Color.BLACK);
        setFocusable(true); // instance of SnakeGame is able to listen to keys pressed

        snakeHead = new EnemySnakeGame.Tile(0, 0);
        snakeBody = new ArrayList<EnemySnakeGame.Tile>();

        food = new EnemySnakeGame.Tile(10, 10);
        random = new Random();

        velocityX = 0;
        velocityY = 0;

        gameLoop = new Timer(100, this);
        gameLoop.start();
    }

    private void move() {
        // eat food
        if(collision(snakeHead, food)){
            snakeBody.add(new EnemySnakeGame.Tile(food.x, food.y));
        }

        // Snake Body
        for(int i = snakeBody.size()-1; i>=0; i--){
            EnemySnakeGame.Tile snakePart = snakeBody.get(i);
            if(i == 0){
                snakePart.x = snakeHead.x;
                snakePart.y = snakeHead.y;
            }else{
                EnemySnakeGame.Tile prevSnakePart = snakeBody.get(i-1);
                snakePart.x = prevSnakePart.x;
                snakePart.y = prevSnakePart.y;
            }
        }

        // Snake Head
        snakeHead.x += velocityX;
        snakeHead.y += velocityY;

        // game over conditions
        for(int i = 0; i < snakeBody.size(); i++){
            EnemySnakeGame.Tile snakePart = snakeBody.get(i);
            // collide with the snake head
            if(collision(snakeHead, snakePart)){
                gameOver = true;
            }
        }

        if(snakeHead.x*tileSize < 0 || snakeHead.x*tileSize > boardWidth || snakeHead.y*tileSize < 0 || snakeHead.y*tileSize > boardHeight){
            gameOver = true;
        }
    }


    public void moveUp(){
        velocityX = 0;
        velocityY = -1;
    }
    public void moveDown(){
        velocityX = 0;
        velocityY = 1;
    }
    public void moveRight(){
        velocityX = 1;
        velocityY = 0;
    }
    public void moveLeft(){
        velocityX = -1;
        velocityY = 0;
    }

    public boolean collision(EnemySnakeGame.Tile tile1, EnemySnakeGame.Tile tile2){
        return tile1.x == tile2.x && tile1.y == tile2.y;
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){
        // Food
        g.setColor(Color.red);
        g.fill3DRect(food.x * tileSize, food.y * tileSize ,tileSize, tileSize, true);

        // Snake Head
        g.setColor(Color.green);
        g.fill3DRect(snakeHead.x * tileSize, snakeHead.y * tileSize, tileSize, tileSize, true);

        // Snake Body
        for(int i = 0; i < snakeBody.size(); i++){
            EnemySnakeGame.Tile snakePart = snakeBody.get(i);
            g.fill3DRect(snakePart.x * tileSize, snakePart.y * tileSize, tileSize, tileSize, true);
        }

        // Score
        g.setFont(new Font("Arial", Font.PLAIN, 16));
        if(gameOver){
            g.setColor(Color.red);
            g.drawString("Game Over: " + String.valueOf(snakeBody.size()), tileSize - 16, tileSize);
        }else{
            g.drawString("Score: " + String.valueOf(snakeBody.size()), tileSize - 16, tileSize);
        }

    }
    public void placeFood(int x, int y) {
        food.x = x;
        food.y = y;
    }
    public void placeSnakeHead(int x, int y){
        snakeHead.x = x;
        snakeHead.y = y;
    }
    @Override
    public void actionPerformed(ActionEvent e) { // action that is performed every time Timer() ms argument (100ms in this case)
        move(); // update x and y position of snake
        repaint(); // repaint() calls draw();
        if(gameOver){
            gameLoop.stop();
        }
    }
    private class Tile{
        int x, y;
        Tile(int x, int y){
            this.x = x;
            this.y = y;
        }
    }

    public Boolean getLookingForCoordinationsOfFood() {
        return isLookingForCoordinationsOfFood;
    }
}

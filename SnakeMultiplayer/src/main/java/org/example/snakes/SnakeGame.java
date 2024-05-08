package org.example.snakes;

import org.example.websockets.GameWebsocketClient;
import org.example.websockets.messages.enums.MessageType;
import org.example.websockets.messages.WebsocketMessage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

public class SnakeGame extends JPanel implements ActionListener, KeyListener {
    Timer gameLoop;
    boolean gameOver = false;
    int boardWidth=600, boardHeight=600, velocityY, velocityX, tileSize = 25;
    Tile snakeHead;
    Tile food;
    Random random;
    ArrayList<Tile> snakeBody;
    GameWebsocketClient webSocketClient;
    Boolean canSnakeMove;

    public SnakeGame(GameWebsocketClient webSocketClient){
        canSnakeMove = false;
        this.webSocketClient = webSocketClient;

        setPreferredSize(new Dimension(this.boardWidth, this.boardHeight));
        setBackground(Color.BLACK);
        addKeyListener(this);
        setFocusable(true); // instance of SnakeGame is able to listen to keys pressed

        snakeHead = new Tile(5, 5);
        snakeBody = new ArrayList<Tile>();

        food = new Tile(10, 10);
        random = new Random();
        placeFood();

        velocityX = 0;
        velocityY = 0;

        gameLoop = new Timer(100, this);
        gameLoop.start();
    }

    private void move() {
        // eat food
        if(collision(snakeHead, food)){
            snakeBody.add(new Tile(food.x, food.y));
            placeFood();
            webSocketClient.send(
                    new WebsocketMessage(
                            MessageType.FOOD_CORDS,
                            new StringBuilder()
                                    .append("{")
                                    .append("\"foodCords\":\"")
                                    .append(food.toSend())
                                    .append("\"")
                                    .append(",")
                                    .append("\"clientId\":\"")
                                    .append(webSocketClient.getClientId())
                                    .append("\"")
                                    .append(",")
                                    .append("\"roomId\":\"")
                                    .append(webSocketClient.getRoomId())
                                    .append("\"")
                                    .append("}")
                                    .toString()
                    ).toSend()
            );
        }

        // Snake Body
        for(int i = snakeBody.size()-1; i>=0; i--){
            Tile snakePart = snakeBody.get(i);
            if(i == 0){
                snakePart.x = snakeHead.x;
                snakePart.y = snakeHead.y;
            }else{
                Tile prevSnakePart = snakeBody.get(i-1);
                snakePart.x = prevSnakePart.x;
                snakePart.y = prevSnakePart.y;
            }
        }

        // Snake Head
        snakeHead.x += velocityX;
        snakeHead.y += velocityY;

        // game over conditions
        for(int i = 0; i < snakeBody.size(); i++){
            Tile snakePart = snakeBody.get(i);
            // collide with the snake head
            if(collision(snakeHead, snakePart)){
                gameOver = true;
            }
        }

        if(snakeHead.x*tileSize < 0 || snakeHead.x*tileSize > boardWidth || snakeHead.y*tileSize < 0 || snakeHead.y*tileSize > boardHeight){
            gameOver = true;
        }
    }


    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_UP && velocityY != 1 && canSnakeMove){
            velocityX = 0;
            velocityY = -1;
            webSocketClient.send(
                    new WebsocketMessage(MessageType.MOVE_MESSAGE,
                        new StringBuilder()
                        .append("{")
                        .append("\"clientId\":").append(webSocketClient.getClientId()).append(',')
                        .append("\"pressed\":\"UP\"")
                                .append(",")
                                .append("\"roomId\":\"")
                                .append(webSocketClient.getRoomId())
                                .append("\"")
                                .append(",")
                                .append("\"foodCords\":\"")
                                .append(food.toSend())
                                .append("\"")
                        .append("}")
                        .toString()
                    ).toSend()
            );
        }
        else if(e.getKeyCode() == KeyEvent.VK_DOWN && velocityY != -1 && canSnakeMove){
            velocityX = 0;
            velocityY = 1;
            webSocketClient.send(
                    new WebsocketMessage(MessageType.MOVE_MESSAGE,
                            new StringBuilder()
                                    .append("{")
                                    .append("\"clientId\":").append(webSocketClient.getClientId()).append(',')
                                    .append("\"pressed\":\"DOWN\"")
                                    .append(",")
                                    .append("\"roomId\":\"")
                                    .append(webSocketClient.getRoomId())
                                    .append("\"")
                                    .append(",")
                                    .append("\"foodCords\":\"")
                                    .append(food.toSend())
                                    .append("\"")
                                    .append("}")
                                    .toString()
                    ).toSend()
            );
        }
        else if(e.getKeyCode() == KeyEvent.VK_LEFT && velocityX != 1 && canSnakeMove){
            velocityX = -1;
            velocityY = 0;
            webSocketClient.send(
                    new WebsocketMessage(MessageType.MOVE_MESSAGE,
                            new StringBuilder()
                                    .append("{")
                                    .append("\"clientId\":").append(webSocketClient.getClientId()).append(',')
                                    .append("\"pressed\":\"LEFT\"")
                                    .append(",")
                                    .append("\"roomId\":\"")
                                    .append(webSocketClient.getRoomId())
                                    .append("\"")
                                    .append(",")
                                    .append("\"foodCords\":\"")
                                    .append(food.toSend())
                                    .append("\"")
                                    .append("}")
                                    .toString()
                    ).toSend()
            );
        }
        else if(e.getKeyCode() == KeyEvent.VK_RIGHT && velocityX != -1 && canSnakeMove){
            velocityX = 1;
            velocityY = 0;
            webSocketClient.send(
                    new WebsocketMessage(MessageType.MOVE_MESSAGE,
                            new StringBuilder()
                                    .append("{")
                                    .append("\"clientId\":").append(webSocketClient.getClientId()).append(',')
                                    .append("\"pressed\":\"RIGHT\"")
                                    .append(",")
                                    .append("\"roomId\":\"")
                                    .append(webSocketClient.getRoomId())
                                    .append("\"")
                                    .append(",")
                                    .append("\"foodCords\":\"")
                                    .append(food.toSend())
                                    .append("\"")
                                    .append("}")
                                    .toString()
                    ).toSend()
            );
        }
    }


    public boolean collision(Tile tile1, Tile tile2){
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
            Tile snakePart = snakeBody.get(i);
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
    private void placeFood() {
        food.x = random.nextInt(boardWidth/tileSize); // 600/25 = 24
        food.y = random.nextInt(boardHeight/tileSize);
    }
    public String getFoodCoordinations(){
        return new StringBuilder().append(String.valueOf(food.x)).append(',').append(String.valueOf(food.y)).toString();
    }
    @Override
    public void actionPerformed(ActionEvent e) { // action that is performed every time Timer() ms argument (100ms in this case)
        move(); // update x and y position of snake
        repaint(); // repaint() calls draw();
        if(gameOver){
            gameLoop.stop();
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {}
    // do not need
    @Override
    public void keyTyped(KeyEvent e) {

    }

    public String getSnakeHeadCoordinations() {
        return new StringBuilder().append(String.valueOf(snakeHead.getX())).append(',').append(String.valueOf(snakeHead.getY())).toString();
    }

    private class Tile{
        int x, y;
        Tile(int x, int y){
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        public String toSend(){
            return String.valueOf(x) + "," + String.valueOf(y);
        }
    }

    public Boolean getCanSnakeMove() {
        return canSnakeMove;
    }

    public void setCanSnakeMove(Boolean canSnakeMove) {
        this.canSnakeMove = canSnakeMove;
    }
}

package org.example.components;


import org.example.snakes.EnemySnakeGame;
import org.example.snakes.SnakeGame;
import org.example.websockets.GameWebsocketClient;
import org.java_websocket.client.WebSocketClient;

import javax.swing.*;
import java.awt.*;

public class MultiComponentWindow {
    JFrame frame;
    JPanel enemyGame;
    JPanel clientWindowGame;
    SnakeGame snakeGame;
    EnemySnakeGame enemySnakeGame;
    GameWebsocketClient gameWebsocketClient;

    public MultiComponentWindow(GameWebsocketClient gameWebsocketClient){
        this.frame = new JFrame();

        initializeClientGamePanel(gameWebsocketClient);
        initializeEnemyGamePanel();

        frame.add(this.enemyGame);
        frame.add(this.clientWindowGame);
        frame.setSize(1300, 700);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(Color.DARK_GRAY);

    }

    private void initializeClientGamePanel(GameWebsocketClient gameWebsocketClient) {
        this.clientWindowGame = new JPanel();
        this.clientWindowGame.setBounds(25, 25, 600, 600);
        this.snakeGame = new SnakeGame(gameWebsocketClient);
        this.clientWindowGame.add(this.snakeGame);
    }
    private void initializeEnemyGamePanel() {
        this.enemyGame = new JPanel();
        this.enemyGame.setBounds(650, 25, 600, 600);
        this.enemySnakeGame = new EnemySnakeGame();
        this.enemyGame.add(this.enemySnakeGame);
    }

    public String getFoodCoordinations(){
        return this.snakeGame.getFoodCoordinations();
    }
    public String getSnakeHeadCoordinations(){return this.snakeGame.getSnakeHeadCoordinations();}
    public EnemySnakeGame getEnemySnakeGame(){
        return this.enemySnakeGame;
    }
    public SnakeGame getClientSnakeGame() {return this.snakeGame; }

}

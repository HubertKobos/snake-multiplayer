package org.example.websockets;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.components.MultiComponentWindow;
import org.example.snakes.EnemySnakeGame;
import org.example.snakes.SnakeGame;
import org.example.websockets.messages.FoodCordsMessage;
import org.example.websockets.messages.enums.MessageType;
import org.example.websockets.messages.MoveMessage;
import org.example.websockets.messages.SnakeCordinationAndFoodResponseMessage;
import org.example.websockets.messages.WebsocketMessage;
import org.example.websockets.messages.enums.PressedKey;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import javax.swing.*;
import java.net.URI;
import java.net.URISyntaxException;

public class GameWebsocketClient extends WebSocketClient {
    private WebSocketClient cc;
    private MultiComponentWindow gameWebsocketClient;
    EnemySnakeGame enemySnakeGame;
    SnakeGame clientSnakeGame;
    private String clientId;
    JFrame frame;
    String roomId;
    ObjectMapper objectMapper;
    public GameWebsocketClient(String code, JFrame frame) throws URISyntaxException {
        super(new URI("ws://localhost:8887/" + code), new Draft_6455());
        this.frame = frame;
        this.roomId = "/" + code;
        this.objectMapper = new ObjectMapper();
        this.gameWebsocketClient = new MultiComponentWindow(this);
        this.enemySnakeGame = gameWebsocketClient.getEnemySnakeGame();
        this.clientSnakeGame = gameWebsocketClient.getClientSnakeGame();
        this.connect();
    }
    public String getClientId(){
        return this.clientId;
    }
    public void closeConnection() {
        if (this != null) {
            this.close();
        }
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        System.out.println("You are connected to ChatServer: " + getURI());
        frame.dispose();


    }

    @Override
    public void onMessage(String s) {
        System.out.println("received: "+ s);
        WebsocketMessage message = null;
        try {
            message = objectMapper.readValue(s, WebsocketMessage.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        // if we get type of message ON_OPEN_SERVER_RESPONSE we get clientId inside it, then we send new message with coordination of client snake
        if(message.getType().equals(MessageType.ON_OPEN_SERVER_RESPONSE)){
            clientId = message.getMessage();
            this.send(
                    new WebsocketMessage(MessageType.SNAKE_COORDINATION_AND_FOOD_COORDINATION,
                            new StringBuilder()
                                    .append("{")
                                    .append("\"lookingForCoordinations\":\"")
                                    .append(String.valueOf(this.enemySnakeGame.getLookingForCoordinationsOfFood()))
                                    .append("\"")
                                    .append(',')
                                    .append("\"clientId\":\"")
                                    .append(clientId)
                                    .append("\"")
                                    .append(',')
                                    .append("\"roomId\":\"")
                                    .append(roomId)
                                    .append("\"")
                                    .append(',')
                                    .append("\"foodCoordinations\":\"")
                                    .append(gameWebsocketClient.getFoodCoordinations())
                                    .append("\"")
                                    .append(',')
                                    .append("\"snakeHeadCoordinations\":\"")
                                    .append(gameWebsocketClient.getSnakeHeadCoordinations())
                                    .append("\"")
                                    .append("}")
                                    .toString()
                    ).toSend()
            );
        } else if(message.getType().equals(MessageType.SNAKE_COORDINATION_AND_FOOD_COORDINATION_RESPONSE)){
            SnakeCordinationAndFoodResponseMessage coords = null;
            try {
                coords = objectMapper.readValue(message.getMessage(), SnakeCordinationAndFoodResponseMessage.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            enemySnakeGame.placeFood(coords.getFoodCoordination()[0], coords.getFoodCoordination()[1]);
            enemySnakeGame.placeSnakeHead(coords.getSnakeHeadCoordination()[0], coords.getSnakeHeadCoordination()[1]);

            clientSnakeGame.setCanSnakeMove(true);
        } else if(message.getType().equals(MessageType.MOVE_MESSAGE_RESPONSE)){
            MoveMessage moveMessage = null;
            try {
                moveMessage = objectMapper.readValue(message.getMessage(), MoveMessage.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

            enemySnakeGame.placeFood(moveMessage.getFoodCords()[0], moveMessage.getFoodCords()[1]);

            if(moveMessage.getPressed().equals(PressedKey.UP)){
                enemySnakeGame.moveUp();
            }else if(moveMessage.getPressed().equals(PressedKey.DOWN)){
                enemySnakeGame.moveDown();
            }else if(moveMessage.getPressed().equals(PressedKey.LEFT)){
                enemySnakeGame.moveLeft();
            }else if(moveMessage.getPressed().equals(PressedKey.RIGHT)){
                enemySnakeGame.moveRight();
            }
        } else if(message.getType().equals(MessageType.FOOD_CORDS)){
            FoodCordsMessage foodCords = null;
            try {
                foodCords = objectMapper.readValue(message.getMessage(), FoodCordsMessage.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            enemySnakeGame.placeFood(foodCords.getFoodCords()[0], foodCords.getFoodCords()[1]);

        }
    }

    @Override
    public void onClose(int i, String s, boolean b) {
        System.out.println("You have been disconnected; Code: " + i + " " + s);
    }

    @Override
    public void onError(Exception e) {
        System.out.println("Exception occurred: " + e);
        e.printStackTrace();
    }

    public String getRoomId(){
        return roomId;
    }
}

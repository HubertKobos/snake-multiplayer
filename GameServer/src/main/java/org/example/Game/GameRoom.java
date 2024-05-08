package org.example.Game;

import org.example.gameServer.receivedMessages.FoodCoordinationsRequestMessage;
import org.example.gameServer.receivedMessages.SnakeCoordinationAndFoodCoordinationMessage;
import org.java_websocket.WebSocket;

import java.util.ArrayList;
import java.util.HashMap;

public class GameRoom {
    private HashMap<String, WebSocket> connections = new HashMap<String, WebSocket>(); // clientId, Websocket

    private ArrayList<SnakeCoordinationAndFoodCoordinationMessage> foodCoordinationsRequestMessages = new ArrayList<>();
    public GameRoom() {
    }

    public HashMap<String, WebSocket> getConnections() {
        return connections;
    }

    public ArrayList<SnakeCoordinationAndFoodCoordinationMessage> getFoodCoordinationsRequestMessages() {
        return foodCoordinationsRequestMessages;
    }

}

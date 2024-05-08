package org.example.gameServer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.Game.GameRoom;
import org.example.gameServer.receivedMessages.*;
import org.example.gameServer.receivedMessages.enums.MessageType;
import org.java_websocket.WebSocket;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.*;

public class GameServer extends WebSocketServer {
    Map<String, GameRoom> gameRooms; // roomId, GameRoom
    private HashMap<WebSocket, String> reverseConnections; // WebSocket, clientId
    private Random rnd;
    private ObjectMapper objectMapper;
    public GameServer(int port) throws UnknownHostException {
        super(new InetSocketAddress(port));
        this.rnd = new Random();
        this.objectMapper = new ObjectMapper();
        this.gameRooms = new HashMap<String, GameRoom>();
        this.reverseConnections = new HashMap<WebSocket, String>();
    }

    public GameServer(InetSocketAddress address) {
        super(address);
    }

    public GameServer(int port, Draft_6455 draft) {
        super(new InetSocketAddress(port), Collections.<Draft>singletonList(draft));
    }

    @Override
    public void onOpen(WebSocket webSocket, ClientHandshake clientHandshake) {
        String roomId = clientHandshake.getResourceDescriptor();
        String randomClientId = String.valueOf(generateRandomClientId());

        // object that is returned in the response with send() method to the connected client
        WebsocketMessage websocketMessage = new WebsocketMessage(MessageType.ON_OPEN_SERVER_RESPONSE, randomClientId);

        webSocket.send(websocketMessage.toSend());

        // if game room not exists yet then create new one
        if(!gameRooms.containsKey(roomId)){
            GameRoom gameRoom = new GameRoom();
            if(!reverseConnections.containsKey(webSocket)){
                reverseConnections.put(webSocket, roomId);
                if(!gameRoom.getConnections().containsKey(randomClientId)){
                    gameRoom.getConnections().put(randomClientId, webSocket);
                    gameRooms.put(roomId, gameRoom);
                }
            }
        }
        // if gameRoom already exists then add the connection to it
        else{
            if(!reverseConnections.containsKey(webSocket)){
                reverseConnections.put(webSocket, roomId);
                if(gameRooms.containsKey(roomId)){
                    GameRoom gameRoom = gameRooms.get(roomId);
                    gameRoom.getConnections().put(randomClientId, webSocket);
                }
            }
        }


    }

    @Override
    public void onClose(WebSocket webSocket, int i, String s, boolean b) {
        reverseConnections.remove(webSocket);
        webSocket.close();
    }

    @Override
    public void onMessage(WebSocket webSocket, String s) {
        WebsocketMessage message = null;
        try {
            message = objectMapper.readValue(s, WebsocketMessage.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        System.out.println("received ->" + message.getType());
        System.out.println("received ->" + message.getMessage());

        // map message and modify GameRoom with new information. We know that the gameRoom already exists because it is created in onOpen method
        if(message.getType().equals(MessageType.SNAKE_COORDINATION_AND_FOOD_COORDINATION)){
            SnakeCoordinationAndFoodCoordinationMessage snakeAndFoodCoords = null;
            try {
                snakeAndFoodCoords = objectMapper.readValue(message.getMessage(), SnakeCoordinationAndFoodCoordinationMessage.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

            // modify GameRoom with new received information
            GameRoom gameRoom = gameRooms.get(snakeAndFoodCoords.getRoomId());

            // if someone already is in the room then also send it food and snake coords
            if(gameRoom.getFoodCoordinationsRequestMessages().size() > 0){
                webSocket.send(
                        new WebsocketMessage(
                                MessageType.SNAKE_COORDINATION_AND_FOOD_COORDINATION_RESPONSE,
                                gameRoom.getFoodCoordinationsRequestMessages().get(0).toSend()
                        ).toSend()
//                        gameRoom.getFoodCoordinationsRequestMessages().get(0).toString()
                );
                SnakeCoordinationAndFoodCoordinationMessage toSend = gameRoom.getFoodCoordinationsRequestMessages().get(0);
                WebSocket websocket2 = gameRoom.getConnections().get(toSend.getClientId());
                websocket2.send(
                        new WebsocketMessage(
                                MessageType.SNAKE_COORDINATION_AND_FOOD_COORDINATION_RESPONSE,
                                snakeAndFoodCoords.toSend()
                        ).toSend()
                );
                gameRoom.getFoodCoordinationsRequestMessages().remove(0);
            }
            // if the client is first in the game room we save it coords
            else{
                gameRoom.getFoodCoordinationsRequestMessages().add(snakeAndFoodCoords);
            }
        }else if(message.getType().equals(MessageType.MOVE_MESSAGE)){
            MoveMessage moveMessage = null;
            try {
                moveMessage = objectMapper.readValue(message.getMessage(), MoveMessage.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            GameRoom gameRoom = gameRooms.get(moveMessage.getRoomId());
            for(Map.Entry<String, WebSocket> entry : gameRoom.getConnections().entrySet()){
                if(!entry.getKey().equals(moveMessage.getClientId())){
                    entry.getValue().send(
                            new WebsocketMessage(MessageType.MOVE_MESSAGE_RESPONSE,
                                    moveMessage.toSend()
                                    ).toSend()
                    );
                }
            }
            System.out.println("coonections -> " + gameRoom.getConnections());
        }else if(message.getType().equals(MessageType.FOOD_CORDS)){
            FoodCordsMessage foodCords = null;
            try {
                foodCords = objectMapper.readValue(message.getMessage(), FoodCordsMessage.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            GameRoom gameRoom = gameRooms.get(foodCords.getRoomId());
            for(Map.Entry<String, WebSocket> entry : gameRoom.getConnections().entrySet()){
                if(!entry.getKey().equals(foodCords.getClientId())){
                    entry.getValue().send(
                            new WebsocketMessage(MessageType.FOOD_CORDS,
                                    foodCords.toSend()
                            ).toSend()
                    );
                }
            }
        }


    }

    @Override
    public void onError(WebSocket webSocket, Exception e) {
        e.printStackTrace();
    }

    @Override
    public void onStart() {

    }

    private int generateRandomClientId(){
        return rnd.nextInt(1000, 9999);
    }
}

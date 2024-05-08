package org.example.websockets.messages.enums;

public enum MessageType {
    // this enums are for serializing WebsocketMessage for the server
    SNAKE_COORDINATION_AND_FOOD_COORDINATION,
    MOVE_MESSAGE,
    // this enums are for deserializing WebsocketMessage for the client
    ON_OPEN_SERVER_RESPONSE, // this type of message should return generated clientId (clientId is generated on the server)
    SNAKE_COORDINATION_AND_FOOD_COORDINATION_RESPONSE, // this type of message should return cords of snake and food of enemy player
    MOVE_MESSAGE_RESPONSE,
    FOOD_CORDS // this type of message should return cords of food after it is eaten by the snake

}

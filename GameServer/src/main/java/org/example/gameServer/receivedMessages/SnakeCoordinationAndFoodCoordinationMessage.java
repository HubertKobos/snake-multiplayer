package org.example.gameServer.receivedMessages;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SnakeCoordinationAndFoodCoordinationMessage implements Message{
    @JsonProperty("lookingForCoordinations")
    private Boolean lookingForCoordinations;
    @JsonProperty("clientId")
    private String clientId;
    @JsonProperty("roomId")
    private String roomId;
    @JsonProperty("foodCoordinations")
    private String foodCoordination;
    @JsonProperty("snakeHeadCoordinations")
    private String snakeHeadCoordination;
    public SnakeCoordinationAndFoodCoordinationMessage(){};

    public String getClientId() {
        return clientId;
    }

    public String getRoomId() {
        return roomId;
    }

    public Boolean getLookingForCoordinations() {
        return lookingForCoordinations;
    }

    public String getFoodCoordination() {
        return foodCoordination;
    }

    public String getSnakeHeadCoordination() {
        return snakeHeadCoordination;
    }

    @Override
    public String toString() {
        return "SnakeCoordinationAndFoodCoordinationMessage{" +
                "lookingForCoordinations=" + lookingForCoordinations +
                ", clientId='" + clientId + '\'' +
                ", roomId='" + roomId + '\'' +
                ", foodCoordination='" + foodCoordination + '\'' +
                ", snakeHeadCoordination='" + snakeHeadCoordination + '\'' +
                '}';
    }

    @Override
    public String toSend(){
        return new StringBuilder()
                .append("{")
                .append("\"clientId\":\"")
                .append(clientId)
                .append("\"")
                .append(",")
                .append("\"roomId\":\"")
                .append(roomId)
                .append("\"")
                .append(",")
                .append("\"foodCoordinations\":\"")
                .append(foodCoordination)
                .append("\"")
                .append(",")
                .append("\"snakeHeadCoordinations\":\"")
                .append(snakeHeadCoordination)
                .append("\"")
                .append("}")
                .toString();
    }
}

package org.example.gameServer.receivedMessages;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FoodCordsMessage implements Message{
    @JsonProperty("foodCords")
    private String foodCords;
    @JsonProperty("roomId")
    private String roomId;
    @JsonProperty("clientId")
    private String clientId;
    public FoodCordsMessage(){};

    public String getFoodCords() {
        return foodCords;
    }

    public String getRoomId() {
        return roomId;
    }

    public String getClientId() {
        return clientId;
    }

    @Override
    public String toString() {
        return "FoodCordsMessage{" +
                "foodCords='" + foodCords + '\'' +
                ", roomId='" + roomId + '\'' +
                ", clientId='" + clientId + '\'' +
                '}';
    }

    @Override
    public String toSend() {
        return new StringBuilder()
                .append("{")
                .append("\"clientId\":\"")
                .append(clientId)
                .append("\",")
                .append("\"foodCords\":\"")
                .append(foodCords)
                .append("\",")
                .append("\"roomId\":\"")
                .append(roomId)
                .append("\"")
                .append("}")
                .toString();
    }
}

package org.example.gameServer.receivedMessages;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import org.example.gameServer.enums.PressedKey;

public class MoveMessage implements Message{
    @JsonProperty("clientId")
    private String clientId;
    @JsonProperty("pressed")
    private PressedKey pressed;
    @JsonProperty("roomId")
    private String roomId;
    @JsonProperty("foodCords")
    private String foodCords;
    public MoveMessage(){};

    @Override
    public String toSend() {
        return new StringBuilder()
                .append("{")
                .append("\"clientId\":\"")
                .append(clientId)
                .append("\",")
                .append("\"pressed\":\"")
                .append(pressed)
                .append("\",")
                .append("\"roomId\":\"")
                .append(roomId)
                .append("\",")
                .append("\"foodCords\":\"")
                .append(foodCords)
                .append("\"")
                .append("}")
                .toString();
    }

    @Override
    public String toString() {
        return "MoveMessage{" +
                "clientId='" + clientId + '\'' +
                ", pressed=" + pressed +
                ", roomId='" + roomId + '\'' +
                ", foodCords='" + foodCords + '\'' +
                '}';
    }

    public String getClientId() {
        return clientId;
    }

    public PressedKey getPressed() {
        return pressed;
    }

    public String getRoomId() {
        return roomId;
    }

    public String getFoodCords() {
        return foodCords;
    }
}

package org.example.websockets.messages;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.example.websockets.messages.enums.PressedKey;


public class MoveMessage{
    @JsonProperty("clientId")
    private String clientId;
    @JsonProperty("pressed")
    private PressedKey pressed;
    @JsonProperty("roomId")
    private String roomId;
    @JsonProperty("foodCords")
    private String foodCords;
    public MoveMessage(){};

    public String getClientId() {
        return clientId;
    }

    public PressedKey getPressed() {
        return pressed;
    }

    public String getRoomId() {
        return roomId;
    }

    public int[] getFoodCords(){
        String[] split = foodCords.split(",");
        return new int[]{Integer.parseInt(split[0]), Integer.parseInt(split[1])};
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
}

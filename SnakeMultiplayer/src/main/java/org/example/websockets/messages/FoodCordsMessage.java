package org.example.websockets.messages;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FoodCordsMessage {
    @JsonProperty("foodCords")
    private String foodCords;
    @JsonProperty("roomId")
    private String roomId;
    @JsonProperty("clientId")
    private String clientId;
    public FoodCordsMessage(){};

    public int[] getFoodCords(){
        String[] split = foodCords.split(",");
        return new int[]{Integer.parseInt(split[0]), Integer.parseInt(split[1])};
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
}

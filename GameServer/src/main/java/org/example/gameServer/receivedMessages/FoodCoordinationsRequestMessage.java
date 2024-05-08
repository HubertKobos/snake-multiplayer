package org.example.gameServer.receivedMessages;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FoodCoordinationsRequestMessage {

    private Boolean lookingForCoordinations;
    private String clientId;
    private String roomId;
    public FoodCoordinationsRequestMessage(){};
    public FoodCoordinationsRequestMessage(Boolean lookingForCoordinations, String clientId, String roomId){
        this.lookingForCoordinations = lookingForCoordinations;
        this.clientId = clientId;
        this.roomId = roomId;
    }

    public Boolean getLookingForCoordinations() {
        return lookingForCoordinations;
    }

    public void setLookingForCoordinations(Boolean lookingForCoordinations) {
        this.lookingForCoordinations = lookingForCoordinations;
    }

    public String getClientId() {
        return clientId;
    }

    public String getRoomId() {
        return roomId;
    }

    @Override
    public String toString() {
        return "FoodCoordinationsRequestMessage{" +
                "lookingForCoordinations=" + lookingForCoordinations +
                ", clientId='" + clientId + '\'' +
                ", roomId='" + roomId + '\'' +
                '}';
    }
}

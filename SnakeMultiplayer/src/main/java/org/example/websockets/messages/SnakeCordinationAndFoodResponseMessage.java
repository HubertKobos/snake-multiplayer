package org.example.websockets.messages;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SnakeCordinationAndFoodResponseMessage {
    @JsonProperty("clientId")
    private String clientId;
    @JsonProperty("roomId")
    private String roomId;
    @JsonProperty("foodCoordinations")
    private String foodCoordinations;
    @JsonProperty("snakeHeadCoordinations")
    private String snakeHeadCoordinations;

    public SnakeCordinationAndFoodResponseMessage(){};
    public int[] getFoodCoordination(){
        String[] split = foodCoordinations.split(",");
        return new int[]{Integer.parseInt(split[0]), Integer.parseInt(split[1])};
    }
    public int[] getSnakeHeadCoordination(){
        String[] split = snakeHeadCoordinations.split(",");
        return new int[]{Integer.parseInt(split[0]), Integer.parseInt(split[1])};
    }

    @Override
    public String toString() {
        return "SnakeCordinationAndFoodResponseMessage{" +
                "clientId='" + clientId + '\'' +
                ", roomId='" + roomId + '\'' +
                ", foodCoordinations='" + foodCoordinations + '\'' +
                ", snakeHeadCoordinations='" + snakeHeadCoordinations + '\'' +
                '}';
    }
}

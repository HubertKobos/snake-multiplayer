package org.example.websockets.messages;

public class PlayerWebsocketMessage {
    private int foodX;
    private int foodY;
    private int snakeHeadX;
    private int snakeHeadY;
    public PlayerWebsocketMessage(){}

    public int getFoodX() {
        return foodX;
    }

    public void setFoodX(int foodX) {
        this.foodX = foodX;
    }

    public int getFoodY() {
        return foodY;
    }

    public void setFoodY(int foodY) {
        this.foodY = foodY;
    }

    public int getSnakeHeadX() {
        return snakeHeadX;
    }

    public void setSnakeHeadX(int snakeHeadX) {
        this.snakeHeadX = snakeHeadX;
    }

    public int getSnakeHeadY() {
        return snakeHeadY;
    }

    public void setSnakeHeadY(int snakeHeadY) {
        this.snakeHeadY = snakeHeadY;
    }
}

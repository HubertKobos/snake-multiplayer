package org.example.websockets.messages;

import com.fasterxml.jackson.annotation.JsonProperty;

public class onMessage {
    @JsonProperty("clientId")
    private long clientId;
    @JsonProperty("pressed")
    private String pressed;

    public onMessage(){}
    public onMessage(long clientId, String pressed) {
        this.clientId = clientId;
        this.pressed = pressed;
    }

    public long getClientId() {
        return clientId;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

    public String getPressed() {
        return pressed;
    }

    public void setPressed(String pressed) {
        this.pressed = pressed;
    }
}

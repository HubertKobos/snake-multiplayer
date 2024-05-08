package org.example.gameServer.receivedMessages;

import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.example.gameServer.receivedMessages.enums.MessageType;

import java.io.IOException;

public class WebsocketMessage {
    private MessageType type;
    @JsonDeserialize(using = RawJsonDeserializer.class)
    private String message;

    public WebsocketMessage(){};

    public WebsocketMessage(MessageType type, String message) {
        this.type = type;
        this.message = message;
    }
    public WebsocketMessage(MessageType messageType){
        this.type = messageType;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public String toSend(){
        return new StringBuilder()
                .append("{")
                .append("\"type\":\"")
                .append(this.type.toString())
                .append("\",")
                .append("\"message\":")
                .append(this.message)
                .append("}")
                .toString();
    }

    @JsonValue
    public String toJson() {
        return "{\"type\":\"" + type.toString() + "\",\"message\":" + message + "}";
    }
    @Override
    public String toString() {
        return "WebsocketMessage{" +
                "type=" + type +
                ", message='" + message + '\'' +
                '}';
    }
}
class RawJsonDeserializer extends JsonDeserializer<String> {
    @Override
    public String deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode node = p.readValueAsTree();
        return node.toString();
    }
}

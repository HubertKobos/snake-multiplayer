package org.example.gameServer.receivedMessages;

public interface Message {
    String toSend(); // format the class fields, so they are prepared in later usage in StringBuilder while sending to the clients
}

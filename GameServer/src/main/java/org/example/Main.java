package org.example;

import org.example.gameServer.GameServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.UnknownHostException;

public class Main {
    public static void main(String[] args) {
        int port = 8887; // 843 flash policy port
        try {
            port = Integer.parseInt(args[0]);
        } catch (Exception ex) {
        }
        GameServer s = null;
        try {
            s = new GameServer(port);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        s.start();
        System.out.println("ChatServer started on port: " + s.getPort());

        BufferedReader sysin = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            String in = null;
            try {
                in = sysin.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            s.broadcast(in);
            if (in.equals("exit")) {
                try {
                    s.stop(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                break;
            }
        }
    }
}
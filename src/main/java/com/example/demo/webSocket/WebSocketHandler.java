package com.example.demo.webSocket;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class WebSocketHandler extends TextWebSocketHandler {

    private WebSocketSession session;

    // This will send only to one client(most recently connected)
    public void counterIncrementedCallback(int counter) {
        System.out.println("Trying to send:" + counter);
        if (session != null && session.isOpen()) {
            try {
                System.out.println("Now sending:" + counter);
                session.sendMessage(new TextMessage("{\"value\": \"" + counter + "\"}"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Don't have open session to send:" + counter);

        }
    }

    public void sendToClient(String message){
        System.out.println("Trying to send:" + message);
        if (session != null && session.isOpen()) {
            try {
                System.out.println("Now sending:" + message);
                session.sendMessage(new TextMessage(message));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Don't have open session to send:" + message);

        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("Connection established" + session.toString());
        //Bridge.setWebSocketSession(session);
        this.session = session;
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        if ("CLOSE".equalsIgnoreCase(message.getPayload())) {
            session.close();
        } else {
            System.out.println("Received:" + message.getPayload());
            if (session != null && session.isOpen()) {
                try {
                    session.sendMessage(new TextMessage("{\"value\": \"" +  message.getPayload() + "\"}"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            //System.out.println(session.getId());
            //session.sendMessage(new TextMessage(message.getPayload()));
            //Bridge.sendToMachine(message.getPayload());
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        System.out.println(session +":"+status);
        //Bridge.setWebSocketSession(null);
    }
}
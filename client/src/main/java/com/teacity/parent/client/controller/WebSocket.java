package com.teacity.parent.client.controller;

import com.alibaba.fastjson.JSON;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

//@Before(CodeInterceptor.class)
@ServerEndpoint(value ="/newwebsocket/{userId}")
public class WebSocket {
    private static int onlineCount = 0;
    private static Map<String, WebSocket> clients = new ConcurrentHashMap<String, WebSocket>();
    private Session session;
    private String userId;



    @OnOpen

    public void onOpen(@PathParam("userId") String userId, Session session) throws IOException {
        this.userId = userId;
        this.session = session;
        addOnlineCount();
        clients.put(userId, this);
        System.out.println("已连接");

    }



    @OnClose

    public void onClose() throws IOException {

        clients.remove(userId);
        subOnlineCount();

    }



    @OnMessage

    public void onMessage(String message) throws IOException {

        Map<String,String> map= JSON.parseObject(message,Map.class);
        sendMessageTo(map.get("message"), map.get("To").toString());
    }

    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }



    public void sendMessageTo(String message, String To) throws IOException {
        // session.getBasicRemote().sendText(message);
        //session.getAsyncRemote().sendText(message);
        for (WebSocket item : clients.values()) {
            if (item.userId.equals(To) )
                item.session.getAsyncRemote().sendText(message);
        }

    }



    public void sendMessageAll(String message) throws IOException {
        for (WebSocket item : clients.values()) {
            item.session.getAsyncRemote().sendText(message);
        }

    }



    public static synchronized int getOnlineCount() {
        return onlineCount;
    }



    public static synchronized void addOnlineCount() {
        WebSocket.onlineCount++;
    }



    public static synchronized void subOnlineCount() {
        WebSocket.onlineCount--;
    }



    public static synchronized Map<String, WebSocket> getClients() {
        return clients;
    }
}

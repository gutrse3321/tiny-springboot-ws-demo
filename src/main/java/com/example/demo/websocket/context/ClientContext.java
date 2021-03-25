package com.example.demo.websocket.context;

import com.example.demo.websocket.WebSocketClient;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Tomonori
 * @mail gutrse3321@live.com
 * @data 2021-03-25 16:07
 */
public class ClientContext {

    private static ConcurrentHashMap<String, WebSocketClient> clientMap = new ConcurrentHashMap<>();

    public static void set(String clientId, WebSocketClient client) {
        if (clientMap.containsKey(clientId)) {
            clear(clientId);
        }
        clientMap.put(clientId, client);
    }

    public static WebSocketClient get(String clientId) {
        return clientMap.get(clientId);
    }

    public static void clear(String clientId) {
        clientMap.remove(clientId);
    }
}

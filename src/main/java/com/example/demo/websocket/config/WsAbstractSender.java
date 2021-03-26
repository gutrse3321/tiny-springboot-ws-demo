package com.example.demo.websocket.config;

import com.example.demo.websocket.WebSocketClient;
import com.example.demo.websocket.WebSocketParam;
import com.example.demo.websocket.context.ClientContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Tomonori
 * @mail gutrse3321@live.com
 * @data 2021-03-26 10:39
 */
public abstract class WsAbstractSender<T> {

//    @Autowired
//    RedisTemplate redisTemplate;
    private String route;
    private String clientId;

    public WsAbstractSender(String route) {
        this.route = route;
    }

    protected abstract String handle(T o);

    public void send(String clientId, T o) throws IOException {
        this.clientId = clientId;
        String data = this.handle(o);
        WebSocketParam handle = new WebSocketParam();
        handle.setRoute(this.route);
        handle.setRequest_time(String.valueOf(System.currentTimeMillis()));
        handle.setClientId(clientId);
        handle.setData(data);
        ClientContext.get(clientId)
                .send(handle);
    }

    public void sendGroup(Long village_id, T o) throws IOException {
        //TODO 缓存获取这个id组下的所有clientId
        //village_id

        List<String> list = new ArrayList<>();
        for (String clientId : list) {
            send(clientId, o);
        }
    }

    public void sendAll(T o) throws IOException {
        for (String clientId : ClientContext.clientIds()) {
            send(clientId, o);
        }
    }

    public String getClientId() {
        return this.clientId;
    }
}

package com.example.demo.websocket.sender;

import com.example.demo.persist.PushHelloModel;
import com.example.demo.websocket.WebSocketParam;
import com.example.demo.websocket.config.WsAbstractSender;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

/**
 * @author Tomonori
 * @mail gutrse3321@live.com
 * @data 2021-03-26 10:46
 */
@Component
public class PushHelloSender extends WsAbstractSender<PushHelloModel> {

    private final static String routeKey = "hello";
    private ObjectMapper mapper = new ObjectMapper();

    public PushHelloSender() {
        super(routeKey);
    }

    @Override
    protected String handle(PushHelloModel o) {
        try {
            String s = mapper.writeValueAsString(o);
            return s;
        } catch (Exception e) {}
        return "";
    }
}

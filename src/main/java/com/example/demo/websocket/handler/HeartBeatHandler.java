package com.example.demo.websocket.handler;

import com.example.demo.websocket.WebSocketParam;
import com.example.demo.websocket.config.WsAbstractHandler;
import org.springframework.stereotype.Component;

/**
 * @author Tomonori
 * @mail gutrse3321@live.com
 * @data 2021-03-26 11:25
 */
@Component
public class HeartBeatHandler extends WsAbstractHandler {

    private final static String routeKey = "heartBeat";

    public HeartBeatHandler() {
        super(routeKey);
    }

    @Override
    public WebSocketParam handle(WebSocketParam param) throws Exception {
        param.setRequest_time(String.valueOf(System.currentTimeMillis()));
        param.setData("Currently receive Heart Beat, Message From Server ... ");
        return param;
    }
}

package com.example.demo.websocket.config;

import com.example.demo.websocket.WebSocketParam;

/**
 * @author Tomonori
 * @mail gutrse3321@live.com
 * @data 2021-03-25 15:04
 *
 * 函数处理模型
 */
public abstract class WsAbstractHandler {

    private String routeKey;

    public WsAbstractHandler(String routeKey) {
        this.routeKey = routeKey;
    }

    public abstract WebSocketParam handle(WebSocketParam param) throws Exception;

    public String getRouteKey() {
        return this.routeKey;
    }
}

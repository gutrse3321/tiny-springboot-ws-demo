package com.example.demo.websocket;

import com.example.demo.websocket.config.filter.RouteFilter;
import com.example.demo.websocket.config.WsAbstractHandler;
import com.example.demo.websocket.context.ClientContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Tomonori
 * @mail gutrse3321@live.com
 * @data 2021-03-24 20:02
 */
@ServerEndpoint("/{clientId}")
@Component
public class WebSocketClient implements InitializingBean {

    //路由校验过滤器
    @Autowired
    private RouteFilter routeFilter;
    //已实现的函数
    @Autowired
    private List<WsAbstractHandler> handlerList;
    //函数池
    private HashMap<String, WsAbstractHandler> handlerPool = new HashMap<>();

    //client 连接会话
    private Session session;
    private String clientId;

    private ObjectMapper mapper = new ObjectMapper();

    /**
     * 连接成功
     * @param session
     * @param clientId
     */
    @OnOpen
    public void onOpen(Session session,
                       @PathParam("clientId") String clientId) {
        this.session = session;
        this.clientId = clientId;

        ClientContext.set(clientId, this);
        System.out.println("客户端：" + clientId + "，连接成功：" + new Date());

        try {
            send("greeting", clientId, "hello");
        } catch (Exception e) {
            System.out.println("客户端：" + clientId + "，回调信息异常：");
            e.printStackTrace();
        }
    }

    @OnClose
    public void onClose() {
        ClientContext.clear(clientId);
        System.out.println("客户端：" + clientId + "，已断开：" + new Date());
    }

    /**
     * 收到客户端消息后调用的方法
     * @param msg
     * @param session
     */
    @OnMessage
    public void onMessage(String msg, Session session) {
        WebSocketParam param = null;
        try {
            param = mapper.convertValue(msg, WebSocketParam.class);
        } catch (Exception e) {
            try {
                send("exception", clientId, "报文反序列化失败");
            } catch (IOException ioException) {
                //log什么的操作
                System.out.println("客户端：" + clientId + "，回调信息异常：");
                e.printStackTrace();
            }
            return;
        }

        routeFilter.run(param);
        WsAbstractHandler handler = handlerPool.get(param.getRoute());
        if (handler != null) {
            try {
                //回文
                WebSocketParam data = handler.handle(param);
                if (data != null) {
                    send(data);
                }
            } catch (Exception e) {
            }
        }
    }

    @OnError
    public void onError(Session session, Throwable e) {
        System.out.println("客户端：" + clientId + "，错误：" + e.getMessage());
        e.printStackTrace();
    }

    /**
     * 推送至客户端
     * @param clientId
     * @param data
     * @throws IOException
     */
    public void send(String route, String clientId, String data) throws IOException {
        WebSocketParam param = new WebSocketParam();
        param.setRoute(route);
        param.setData(data);
        param.setClientId(clientId);
        param.setRequest_time(String.valueOf(System.currentTimeMillis()));
        send(param);
    }

    public void send(WebSocketParam param) throws IOException {
        this.session
                .getBasicRemote()
                .sendText(mapper.writeValueAsString(param));
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        handlerList.forEach(handler -> handlerPool.put(handler.getRouteKey(), handler));
    }
}

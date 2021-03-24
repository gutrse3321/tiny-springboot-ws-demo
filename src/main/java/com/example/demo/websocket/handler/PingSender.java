package com.example.demo.websocket.handler;

import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Tomonori
 * @mail gutrse3321@live.com
 * @data 2021-03-24 20:02
 */
@ServerEndpoint("/ping/{holder_id}")
@Component
public class PingSender {

    //socket对象池
    private static ConcurrentHashMap<String, PingSender> socketMap = new ConcurrentHashMap<>();
    //client 连接会话
    private Session session;

    private String holderId;

    /**
     * 连接成功
     * @param session
     * @param holderId
     */
    @OnOpen
    public void onOpen(Session session,
                       @PathParam("holder_id") String holderId) {
        this.session = session;
        this.holderId = holderId;

        if (socketMap.containsKey(holderId)) {
            socketMap.remove(holderId);
        }
        socketMap.put(holderId, this);

        System.out.println("客户端：" + holderId + "，连接成功：" + new Date());

        try {
            send("成功连接 from server");
        } catch (Exception e) {
            //log什么的操作
            System.out.println("客户端：" + holderId + "，连接成功回调信息异常：");
            e.printStackTrace();
        }
    }

    @OnClose
    public void onClose() {
        if (socketMap.containsKey(holderId)) {
            socketMap.remove(holderId);
        }
        System.out.println("客户端：" + holderId + "，已断开：" + new Date());
    }

    /**
     * 收到客户端消息后调用的方法
     * @param msg
     * @param session
     */
    @OnMessage
    public void onMessage(String msg, Session session) {
        System.out.println("客户端：" + holderId + "，报文：" + msg);

        if (msg != null && msg != "") {
            try {
                //做些业务操作...
                System.out.println("服务端 <- 客户端：" + holderId + "，发送报文：" + msg);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @OnError
    public void onError(Session session, Throwable e) {
        System.out.println("客户端：" + holderId + "，错误：" + e.getMessage());
        e.printStackTrace();
    }

    /**
     * 推送至客户端
     * @param msg
     * @throws IOException
     */
    public void send(String msg) throws IOException {
        this.session
                .getBasicRemote()
                .sendText(msg);
    }

    public static void sendPong(String msg,
                                @PathParam("holder_id") String holderId) throws Exception {
        System.out.println("服务端发送 -> 客户端：" + holderId + "，报文：" + msg);
        socketMap.get(holderId)
                .send(msg);
    }
}

package com.example.demo.controller;

import com.example.demo.persist.PushHelloModel;
import com.example.demo.websocket.WebSocketClient;
import com.example.demo.websocket.sender.PushHelloSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Tomonori
 * @mail gutrse3321@live.com
 * @data 2021-03-24 19:58
 */
@RestController
@RequestMapping("/")
public class OptController {

    @Autowired
    PushHelloSender pushHelloSender;

    @PostMapping("sendMsg")
    public void send(String clientId, String msg) throws Exception {
        PushHelloModel model = new PushHelloModel();
        model.setKey("Server");
        model.setValue(msg);
        pushHelloSender.send(clientId, model);
    }
}

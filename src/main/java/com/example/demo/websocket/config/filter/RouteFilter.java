package com.example.demo.websocket.config.filter;

import com.example.demo.websocket.WebSocketParam;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @author Tomonori
 * @mail gutrse3321@live.com
 * @data 2021-03-25 14:50
 *
 * websocket过滤器
 */
@Component
public class RouteFilter {

    public void run(WebSocketParam param) {
        if (StringUtils.isEmpty(param.getRoute()) || StringUtils.isEmpty(param.getClientId())) {
            throw new RuntimeException();
        }
    }
}

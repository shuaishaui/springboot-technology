package com.shuai.springbootwebsocket.wechat.websocket;

import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

@Component
public class MessageHandshakeInterceptor implements HandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   WebSocketHandler wsHandler,Map<String, Object> attributes) throws Exception {
        String path = request.getURI().getPath();
        String[] ss = StringUtils.split(path, '/');
        if(ss.length != 2){
            return false;
        }
        if(!StringUtils.isNumeric(ss[1])){ //是否为数字
            return false;
        }
        attributes.put("uid", Long.valueOf(ss[1]));
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

    }
}

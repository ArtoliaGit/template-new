package com.bsoft.template.util;

import com.bsoft.template.common.Result;
import lombok.extern.slf4j.Slf4j;

import javax.websocket.EncodeException;
import javax.websocket.RemoteEndpoint;
import javax.websocket.Session;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * websocket 工具类
 * @author artolia
 */
@Slf4j
public class WebSocketUtil {

    private final static Map<String, Session> SESSION_MAP = new ConcurrentHashMap<>();

    private static final ThreadLocal<Integer> threadLocal = new ThreadLocal<>();

    /**
     * 存储session
     * @param session 会话
     */
    public static void putSession(Session session) {
        SESSION_MAP.put(session.getId(), session);
    }

    /**
     * 移除session
     * @param session 会话
     */
    public static void removeSession(Session session) {
        SESSION_MAP.remove(session.getId());
        threadLocal.remove();
    }

    /**
     * 获取SESSION_MAP
     */
    public static Map<String, Session> getSessionMap() {
        return SESSION_MAP;
    }

    /**
     * 发送消息
     * @param session 会话
     * @param message 消息
     * @return 发送消息状态
     */
    public static boolean sendMessage(Session session, Object message) {
        if (session == null) {
            return false;
        }
        final RemoteEndpoint.Basic basic = session.getBasicRemote();
        if (basic == null) {
            return false;
        }
        try {
            basic.sendObject(message);
            return true;
        } catch (IOException | EncodeException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 发送消息给所有人
     * @param result 消息
     */
    public static void sendAll(Result result) {
        if (SESSION_MAP.isEmpty()) {
            log.info("[SESSION_MAP] 为空");
        }

        for (Map.Entry<String, Session> item : SESSION_MAP.entrySet()) {
            Session session = item.getValue();
            boolean flag = sendMessage(session, result);
            if (!flag) {
                log.info("消息发送失败，会话id：{}", session.getId());
            }
        }
    }

    public static Integer getPages() {
        int pages = 1;
        if (threadLocal.get() != null) {
            pages = threadLocal.get();
        }
        return pages;
    }

    public static void setPages(Integer pages) {
        WebSocketUtil.threadLocal.set(pages);
    }
}

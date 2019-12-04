/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年5月31日
 * Created by ckm
 */
package com.ctb.pharmacy.rest.websocket;

/**
 * @ClassName: com.ctb.pharmacy.rest.websocket.a
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author ckm
 * @date 2019年5月31日 下午4:24:31
 */
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

import com.ctb.commons.entity.PharmacyUser;
import com.ctb.framework.commons.utils.SpringUtils;
import com.ctb.pharmacy.cache.PharmacyUserCache;

/**
 * @ServerEndpoint(value="/websocket")value值必须以/开路 备注:@ServerEndpoint注解类不支持使用@Autowire {topic}指：向哪个频道主题里发消息
 *                                                 {myname}指：这个消息是谁的。真实环境里可以使用当前登录用户信息
 */
@Component
@ServerEndpoint(value = "/websocket/{targetId}/{pharmacyId}")
public class WebsocketEndpoint {
    
    private static Logger logger = LoggerFactory.getLogger(WebsocketEndpoint.class);
    
    /**
     * 因为@ServerEndpoint不支持注入，所以使用SpringUtils获取IOC实例
     */
    private StringRedisTemplate redisTampate = SpringUtils.getBean(StringRedisTemplate.class);
    
    private RedisMessageListenerContainer redisMessageListenerContainer = SpringUtils
            .getBean(RedisMessageListenerContainer.class);
    
    // 存放该服务器该ws的所有连接。用处：比如向所有连接该ws的用户发送通知消息。
    private static CopyOnWriteArraySet<WebsocketEndpoint> sessions = new CopyOnWriteArraySet<>();
    
    private static Map<String, Session> socketMap = new HashMap<String, Session>();
    
    private Session session;
    
    private PharmacyUserCache pharmacyUserCache = SpringUtils.getBean(PharmacyUserCache.class);
    
    @OnOpen
    public void onOpen(Session session, @PathParam("targetId") String targetId,
            @PathParam("pharmacyId") String pharmacyId) {
        try {
            logger.info("websocket:打开连接,targetId:[{}]", targetId);
            this.session = session;
            sessions.add(this);
            // 内存保存连接信息
            socketMap.put(targetId, session);
            // 设置订阅监听
            SubscribeListener subscribeListener = new SubscribeListener();
            subscribeListener.setSession(session);
            subscribeListener.setStringRedisTemplate(redisTampate);
            // 设置订阅topic
            redisMessageListenerContainer.addMessageListener(subscribeListener, new ChannelTopic(targetId));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("class:[{}],method:[{}],errMsg:[{}]", WebsocketEndpoint.class, "onOpen", e.toString());
        }
    }
    
    @OnClose
    public void onClose(Session session, @PathParam("targetId") String targetId,
            @PathParam("pharmacyId") String pharmacyId) {
        try {
            sessions.remove(this);
            socketMap.remove(targetId);
            // 断开socket连接，更新在线信息
            PharmacyUser pharmacyUser = new PharmacyUser();
            pharmacyUser.setId(targetId);
            pharmacyUser.setPharmacyId(pharmacyId);
            pharmacyUserCache.updateLogOut(pharmacyUser);
            logger.info("websocket:关闭连接,topic:[{}]", targetId);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("class:[{}],method:[{}],errMsg:[{}]", WebsocketEndpoint.class, "onClose", e.toString());
        }
    }
    
    @OnMessage
    public void onMessage(Session session, String message, @PathParam("targetId") String targetId,
            @PathParam("pharmacyId") String pharmacyId) throws IOException {
        try {
            logger.info("websocket 收到消息" + message);
            PublishService publishService = SpringUtils.getBean(PublishService.class);
            publishService.publish(targetId, message);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("class:[{}],method:[{}],errMsg:[{}]", WebsocketEndpoint.class, "onMessage", e.toString());
        }
    }
    
    public void sendMessage(String message, @PathParam("targetId") String targetId,
            @PathParam("pharmacyId") String pharmacyId) throws IOException {
        try {
            Session session = socketMap.get(targetId);
            if (session != null) {// 本服务器存在topic创建连接的session则直接推送消息给客户端
                this.session = session;
                this.session.getBasicRemote().sendText(message);
            } else {// 本服务器不存在topic对应得session信息，则发布消息交由其他服务器检查并处理
                PublishService publishService = SpringUtils.getBean(PublishService.class);
                publishService.publish(targetId, message);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("class:[{}],method:[{}],errMsg:[{}]", WebsocketEndpoint.class, "sendMessage", e.toString());
        }
    }
    
    @OnError
    public void onError(Session session, Throwable error) {
        logger.error("websocket发生错误，errMsg:[{}]", error.toString());
        error.printStackTrace();
    }
    
    public Session getSession() {
        return session;
    }
    
    public void setSession(Session session) {
        this.session = session;
    }
    
    public static void main(String[] args) throws IOException {
        WebsocketEndpoint endpoint = new WebsocketEndpoint();
        endpoint.sendMessage("targetId", "U2FsdGVkX19hf++ld87hozY8TDDInJD/gD/pFKJZJjU=", "weqweoq[]=0qqweqdqwdqweq");
    }
}
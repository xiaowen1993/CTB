/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年3月23日
 * Created by cwq
 */
package com.ctb.gateway.rule;

import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;
import com.netflix.zuul.context.RequestContext;

/**
 * @ClassName: com.ctb.gateway.rule.StickyLoadBalancerRule
 * @Description: TODO(测试的时候可以使用粘性session)
 * @author cwq
 * @date 2019年3月23日 上午9:45:27
 */

public class StickyLoadBalancerRule extends AbstractLoadBalancerRule {
    
    private static final Logger logger = LoggerFactory.getLogger(StickyLoadBalancerRule.class);
    
    public StickyLoadBalancerRule() {
    }
    
    public StickyLoadBalancerRule(ILoadBalancer lb) {
        this();
        this.setLoadBalancer(lb);
    }
    
    @Override
    public void initWithNiwsConfig(IClientConfig iClientConfig) {
    }
    
    @Override
    public Server choose(Object key) {
        return this.choose(this.getLoadBalancer(), key);
    }
    
    public Server choose(ILoadBalancer lb, Object key) {
        if (lb == null) {
            logger.warn("no load balancer");
            return null;
        } else {
            Server server = null;
            int count = 0;
            
            while (true) {
                if (server == null && count++ < 10) {
                    List<Server> reachableServers = lb.getReachableServers();
                    List<Server> allServers = lb.getAllServers();
                    int upCount = reachableServers.size();
                    int serverCount = allServers.size();
                    
                    if (upCount != 0 && serverCount != 0) {
                        int nextServerIndex = this.addrHash(serverCount, count);
                        List<Server> tArr = Lists.newArrayList(allServers);
                        tArr.sort(Comparator.comparing(Server::getHost));
                        server = tArr.get(nextServerIndex);
                        if (server != null && server.isAlive() && server.isReadyToServe()) {
                            return server;
                        } else {
                            server = null;
                        }
                        continue;
                    }
                    
                    logger.warn("No up servers available from load balancer: {}", lb);
                    return null;
                }
                
                if (count >= 10) {
                    logger.warn("No available alive servers after 10 tries from load balancer: {}", lb);
                }
                
                return server;
            }
        }
    }
    
    public int addrHash(int serverCount, int offset) {
        String remoteAddr = this.getRemoteAddr();
        return Math.abs(remoteAddr.hashCode() + offset) % serverCount;
    }
    
    public String getRemoteAddr() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String forwardedFor = request.getHeader("X-FORWARDED-FOR");
        String remoteAddr = StringUtils.isEmpty(forwardedFor) ? request.getRemoteAddr() : forwardedFor;
        return remoteAddr;
    }
    
}

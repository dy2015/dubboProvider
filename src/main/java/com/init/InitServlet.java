package com.init;

import org.springframework.beans.factory.annotation.Autowired;

import com.common.collector.Ec;
import com.redis.Redis;
import com.util.PropertiesUtil;
import com.util.SmsSenderUtil;

import redis.clients.jedis.ShardedJedis;

/**
 * 应用初始化
 * 
 * @date 2016年6月30日
 * @author 
 */
public class InitServlet {
    @Autowired
    private Redis redis;
    void init() {
        String baseDir = System.getProperty("search.root") + "WEB-INF/classes/";
        
        ShardedJedis jedis = redis.getRedis();
        if(jedis.get("servelt")!=null){
        	 jedis.del("servelt");
        }
        redis.close(jedis);
        // 初始化ad-message-collector
//        try {
//            Collector.initCollector(baseDir + "collector-conf.properties");
//        } catch (Exception e) {
//            smsSend(e);
//            e.printStackTrace();
//        }
        // 启动log4j
//        try {
//            DOMConfigurator.configure(baseDir + "log4j.properties");
//        } catch (Exception e) {
////            smsSend(e);
//            e.printStackTrace();
//        }
        
//        // 初始化redis
//        try {
//            RedisContainer.init(baseDir + "redis-config.properties");
//        } catch (Exception e) {
//            smsSend(e);
//            e.printStackTrace();
//        }
        
//        // 初始化servercookie
//        try {
//            StoreManager.initialize(baseDir + "servercookieconfig.properties");
//            String serverCookieService = PropertiesUtil.I_ADSERVER.get("servercookie_service");
//            String dmpService = PropertiesUtil.I_ADSERVER.get("dmp_service");
//            String dtService = PropertiesUtil.I_ADSERVER.get("dt_service");
//            
//            if (serverCookieService != null && !"".equals(serverCookieService.trim())) {
//                ServerCookieClient.intStoreClient(serverCookieService);
//            }
//            if (dmpService != null && !"".equals(dmpService.trim())) {
//                ServerCookieClient.intStoreClient(dmpService);
//            }
//            if (dtService != null && !"".equals(dtService.trim())) {
//                ServerCookieClient.intStoreClient(dtService);
//            }
//        } catch (Exception e) {
//            smsSend(e);
//            e.printStackTrace();
//        }
//        
//        String dispatcherIP = PropertiesUtil.I_ADSERVER.get("dispatcherIP");
//        if (dispatcherIP != null && dispatcherIP.length() > 0) {
//            EffectCollector.setDispatcherIP(dispatcherIP);
//        }
    
//        // 初始化日志配置信息
//        try {
//            LogFactory.init();
//        } catch (Exception e) {
//            smsSend(e);
//            e.printStackTrace();
//        }
//        try{
//            CTROptimizer.init();
//        } catch (Exception e) {
//            smsSend(e);
//            e.printStackTrace();
//        }
//        JacksonMapperManager.initJacksonTemplate(baseDir + "ad_filter.json");
        
    }
    
    private void smsSend(Exception e) {
        SmsSenderUtil sender = new SmsSenderUtil();
        String[] telList = PropertiesUtil.I_CONFIG.getValue("phone");
        sender.send("init adserver error=" + e.getMessage(), telList);
        Ec.CENTER.log("atm adserver init error", e,Ec.DIM_COMMON_ERR);
    }
}

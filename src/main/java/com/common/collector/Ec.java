package com.common.collector;

import java.util.HashMap;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 异常收集器,,参考
 * 
 * @date 2016-6-30
 * @author 
 */
public class Ec {
	public static boolean debug = false;
	/**
	 * 默认host name获取本机第一块网卡ip地址
	 */
	public static final String HOST_NAME = "";

	public static Ec COMMON = new Ec("ATM_COMMON");
	public static Ec CENTER = new Ec("ATM_CENTER");

	public static final String DIM_INIT_ERR = "INIT_ERR";
	public static final String DIM_TASK_ERR = "TASK_ERR";
	public static final String DIM_COOKIE_ERR = "COOKIE_ERR";
	public static final String DIM_COMMON_ERR = "COMMON_ERR";

//	private IErrorStackCollector initErrCollector;
//	private IErrorStackCollector taskErrCollector;
//	private IErrorStackCollector cookieErrCollector;
//	private IErrorStackCollector commonErrCollector;

//	private Map<String, IErrorStackCollector> collectorMap = new HashMap<String, IErrorStackCollector>();

	public Ec(String sysId) {
//		initErrCollector = Collector.registerErrorStackCollector(sysId, HOST_NAME, DIM_INIT_ERR);
//		taskErrCollector = Collector.registerErrorStackCollector(sysId, HOST_NAME, DIM_TASK_ERR);
//		cookieErrCollector = Collector.registerErrorStackCollector(sysId, HOST_NAME, DIM_COOKIE_ERR);
//		commonErrCollector = Collector.registerErrorStackCollector(sysId, HOST_NAME, DIM_COMMON_ERR);
//		collectorMap.put(DIM_INIT_ERR, initErrCollector);
//		collectorMap.put(DIM_TASK_ERR, taskErrCollector);
//		collectorMap.put(DIM_COOKIE_ERR, cookieErrCollector);
//		collectorMap.put(DIM_COMMON_ERR, commonErrCollector);
	}

	public void log(String msg, Throwable e, String key) {
//		log(collectorMap.get(key), msg, e);
	}
	
//	private void log(IErrorStackCollector collector, String msg, Throwable e) {
//		if (debug && e != null) {
//			e.printStackTrace();
//		}
//		logger.error(msg, e);
//		if (collector != null) {
////			collector.produce(new ErrorStackMessage(e, msg, new MergePolicy(false, true)));
//		}
//	}

}

package com.common.constant;

import com.util.PropertiesUtil;

public class Constant {
	/** kafka配置 **/
	public static final String ZK = "zookeeper.connect";
	public static final String SEC = "serializer.class";
	public static final String BROKER = "metadata.broker.list";
	public static final String PARTITIONER = "partitioner.class";
	public static final String TOPIC = PropertiesUtil.I_CENTER.get("kafka.topic");
	public static final String GROUP = "group.id";
	public static final String PART_0 = "0";
	public static final String PART_1 = "1";
}

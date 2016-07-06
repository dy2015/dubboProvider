package com.common.kafka;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

import com.common.constant.Constant;
import com.util.PropertiesUtil;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
import kafka.serializer.StringEncoder;

public class KafkaProducer {

	private static String topic = Constant.TOPIC;

	public KafkaProducer() {
		super();
	}

	public static void produce(String k, String v) {
		Producer<String, String> producer = createProducer();
		// 开始生产消息
		producer.send(new KeyedMessage<String, String>(topic, k, v));
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private static Producer<String, String> createProducer() {
		Properties properties = new Properties();
		properties.put(Constant.ZK, PropertiesUtil.I_CENTER.get(Constant.ZK));// 声明zk
		properties.put(Constant.SEC, StringEncoder.class.getName());
		// properties.put("metadata.broker.list",
		// "127.0.0.1:9092,127.0.0.1:9093");// 声明kafka broker
		properties.put(Constant.BROKER, PropertiesUtil.I_CENTER.get(Constant.BROKER));
		properties.put(Constant.PARTITIONER, PropertiesUtil.I_CENTER.get(Constant.PARTITIONER));// 分区规则
		return new Producer<String, String>(new ProducerConfig(properties));
	}

}
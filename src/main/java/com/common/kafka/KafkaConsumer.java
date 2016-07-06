package com.common.kafka;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.springframework.stereotype.Component;

import com.common.constant.Constant;
import com.util.PropertiesUtil;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.message.MessageAndMetadata;

/**
 * @author zm
 * 
 */
@Component
public class KafkaConsumer extends Thread {

	private static String topic = Constant.TOPIC;
	private static int part_0 = 0;
	private static int part_1 = 0;

	public KafkaConsumer() {
		super();
	}

	public void init() {
		new KafkaConsumer().start();
	}

	@Override
	public void run() {
		ConsumerConnector consumer = createConsumer();
		Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
		topicCountMap.put(topic, 1); // 一次从主题中获取一个数据
		Map<String, List<KafkaStream<byte[], byte[]>>> messageStreams = consumer.createMessageStreams(topicCountMap);
		KafkaStream<byte[], byte[]> stream = messageStreams.get(topic).get(0);// 获取每次接收到的这个数据
		ConsumerIterator<byte[], byte[]> iterator = stream.iterator();
		while (iterator.hasNext()) {
			MessageAndMetadata<byte[], byte[]> mam = iterator.next();
			// System.out.println("consume: Partition [" + mam.partition() + "]
			// Message: [" + new String(mam.message()) + "]
			// Offset:["+mam.offset()+"]");
			System.out.println(mam.partition());
			if (mam.partition() == 0) {
				part_0+=Integer.parseInt(new String(mam.message()));
				System.out.println("请求queryOne方法第" + part_0 + "次");
			} else {
				part_1+=Integer.parseInt(new String(mam.message()));
				System.out.println("请求queryList方法第" + part_1 + "次");
			}
		}
	}

	private ConsumerConnector createConsumer() {
		Properties properties = new Properties();
		properties.put(Constant.ZK, PropertiesUtil.I_CENTER.get(Constant.ZK));// 声明zk
		properties.put(Constant.GROUP, PropertiesUtil.I_CENTER.get(Constant.GROUP));// 必须要使用别的组名称，
		return Consumer.createJavaConsumerConnector(new ConsumerConfig(properties));
	}

}
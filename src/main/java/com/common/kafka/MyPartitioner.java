package com.common.kafka;

import kafka.producer.Partitioner;
import kafka.utils.VerifiableProperties;

public class MyPartitioner implements Partitioner {
	public MyPartitioner(VerifiableProperties props) {
	}

	public int partition(Object key, int numPartitions) {
		int partition = 0;
		// 按特定规则分区,如把偶数消息分配到0分区；奇数消息分配到1分区；
		if (Integer.parseInt((String)key) % 2 != 0) {
			partition = 1;
		}
		// 消息自动分区，如key0会分配到dy-1分区；key1分配会到dy-0分区；
		// partition = Math.abs(k.hashCode()) % numPartitions;
		return partition;
	}
}
package com.common.task;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import com.common.collector.Ec;
import com.util.LogUtil;

/**
 * 定时任务
 * 
 * @author
 * @date 2016-6-30
 */
@Component
public class TimeTask{
	private Logger logger = LogUtil.getLog();

	public void runTask() {
		try {
			System.out.println("我是定时任务");
		} catch (Exception e) {
			logger.info("定时任务启动失败");
			Ec.CENTER.log("kafka消费失败", e, Ec.DIM_TASK_ERR);
		}
	}

}

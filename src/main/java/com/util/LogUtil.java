package com.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogUtil {
	public final static Logger logger = LoggerFactory.getLogger(LogUtil.class);;
	public static Logger getLog() {
		return logger;
	}
}

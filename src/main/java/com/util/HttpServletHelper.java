package com.util;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;


/**
 * 描述：匹配具体的浏览器，返回前台页面参数
 * @author：
 *
 */
public class HttpServletHelper {
	private static Map<String, String> contentMap = new HashMap<String, String>();
	
	static {
		contentMap.put("xml", "text/xml;charset=");
		contentMap.put("json", "text/json;charset=");
		contentMap.put("xml_utf8", "text/xml;charset=UTF-8");
		contentMap.put("json_utf8", "text/json;charset=UTF-8");
		contentMap.put("html_utf8", "text/html;charset=UTF-8");
	}

	public static void WriteXml(HttpServletResponse response, String writeText) throws Exception {
		write(response, writeText, "xml_utf8");
	}

	public static void WriteHtml(HttpServletResponse response, String writeText) throws Exception {
		write(response, writeText, "html_utf8");
	}

	public static void WriteXml(HttpServletResponse response, String writeText, String charset) throws Exception {
		write(response, writeText, "xml", charset);
	}

	public static void WriteJSON(HttpServletResponse response, String writeText) throws Exception {
		write(response, writeText, "json_utf8");
	}

	public static void WriteJSON(HttpServletResponse response, String writeText, String charset) throws Exception {
		write(response, writeText, "json", charset);
	}

	private static void write(HttpServletResponse response, String writeText, String contentType) throws Exception {
		write(response, writeText, contentType, "");
	}

	private static void write(HttpServletResponse response, String writeText, String contentType, String charset) throws Exception {
		response.setContentType(contentMap.get(contentType) + charset);
		PrintWriter out = response.getWriter();
		out.write(writeText);//
		out.flush();
		out.close();
	}
	
	
}

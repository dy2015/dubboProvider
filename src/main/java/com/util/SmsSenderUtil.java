package com.util;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

import org.slf4j.Logger;

/**
 * 发送短信。使用udp发送到公司的短信发送服务器。<br>
 * 默认支持130个字节，65个汉字，多于这个数量会截断信息。<br>
 * 请在host里添加： 10.103.11.62   sms.youku<br>
 * 
 * @author tuuboo
 * 
 */
public class SmsSenderUtil {
	
	private Logger logger = LogUtil.getLog();
	
	private static final int CONTENT_MAX_LENTH = 130;
	
	/** 短信接收的host，默认值：{@value} . */
	public static final String DEFAULT_HOST = "10.103.11.62";
	/** 短信发送服务器的默认端口号：{@value} . */
	public static final int DEFAULT_PORT = 20229;
	private SocketAddress socketAddr;

	/**
	 * 使用缺省的参数，{@value #DEFAULT_HOST}:{@value #DEFAULT_PORT}.
	 */
	public SmsSenderUtil() {
		this(DEFAULT_HOST, DEFAULT_PORT);
	}

	/**
	 * 使用指定的主机和端口.<br>
	 * @param host hostname<br>
	 * @param port the port<br>
	 */
	public SmsSenderUtil(String host, int port) {
		this(new InetSocketAddress(host, port));
	}

	/**
	 * 指定SocketAddress.<br>
	 * 
	 * @param socketAddr 短信发送服务器地址<br>
	 */
	public SmsSenderUtil(SocketAddress socketAddr) {
		super();
		this.socketAddr = socketAddr;
	}

	/**
	 * 发送短信.默认支持130个字节，65个汉字，多于这个数量会截断信息。<br>
	 * @param smsContent 短信内容<br>
	 * @param phoneNumbers 接受短信终端的号码<br>
	 * @return	true=成功、false=失败<br>
	 */
	public boolean send(String smsContent, String... phoneNumbers)  {
		
		if(phoneNumbers==null || phoneNumbers.length==0){
			return false;
		}
		for(String phoneNumber : phoneNumbers){
			if(phoneNumber==null || phoneNumber.length()==0){
				return false;
			}
		}
		return send(smsContent, join(",", phoneNumbers));
	}

	/**
	 * 发送短信。     默认支持130个字节，65个汉字，多于这个数量会截断信息。<br>
	 * @param   smsContent 短信内容<br>
	 * @param   phoneNumbers 多个手机号请用逗号隔开，或者使用{@link #send(String, String...)}函数<br>
	 * @return	true=成功、false=失败<br>
	 */
	public boolean send(String smsContent, String phoneNumbers){
		
		//参数验证
		if(smsContent==null || phoneNumbers==null || smsContent.length()==0 || phoneNumbers.length()==0){
			return false;
		}
		
		DatagramSocket socket =null;
		try{
			socket = new DatagramSocket();
			
			int length = smsContent.getBytes().length;
			if(length>CONTENT_MAX_LENTH){
				smsContent = getSubString(smsContent,CONTENT_MAX_LENTH);
			}
			
			String part = "m:" + phoneNumbers + " c:" + smsContent;
			logger.info("* 正在发送信息给收信息人：["+phoneNumbers+"],信息内容：" + smsContent);
			String content = String.format("s:%04d %s", part.getBytes("UTF8").length, part);
			byte[] data = content.getBytes("UTF8");
			socket.send(new DatagramPacket(data, data.length, this.socketAddr));
			logger.info("* 正在发送信息给收信息人：["+phoneNumbers+"],信息内容：" + smsContent + " finish！");
			
			return true;
		}catch(Exception e){
			logger.error("* 发送短信失败!",e);
		}finally{
			if(socket!=null){
				socket.close();
			}
		}
		return false;
	}

	private String join(CharSequence sep, CharSequence... strs) {
		if (strs.length == 0) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		sb.append(strs[0]);
		for (int i = 1; i < strs.length; i++) {
			sb.append(sep).append(strs[i]);
		}
		return sb.toString();
	}
	
	/**
	 * 截取字符串(长度超过subStrLen，字符串末尾追加"...")<br>
	 * @param str		字符串<br>
	 * @param subStrLen 期望长度<br>
	 * @return 期望字符串
	 */
	public  String getSubString(String str, int subStrLen) {
		
		if (str == null || subStrLen <= 0) {
		   return "";
		}  
		
		byte[] strBytes = str.getBytes(); 
		if(strBytes.length<=subStrLen){
			return str;
		}

		if (subStrLen > strBytes.length) {
		   subStrLen = strBytes.length;
		}     
		String subStr = new String(strBytes, 0, subStrLen);
		//半个汉字处理
		if (subStr.charAt(subStr.length() - 1) != str.charAt(subStr.length() - 1)) {
		   subStr = subStr.substring(0, subStr.length() - 1);
		}
		
		StringBuffer buffer = new StringBuffer();
		buffer.append(subStr).append("...");
		
		return buffer.toString();
	}

	/**
	 * 测试发送。
	 * @param args 测试
	 * @throws IOException io异常
	 */
	public static void main(String[] args){
		String content =  "娱乐宝是一个绝佳创意，将互联网金融与娱乐业对接上，"+
						  "而娱乐业将是具有无限想象力的行业，"+
						  "也@为阿里的上市打开了更大的想象力空间，"+
						  "这才是其收购文化中国并推出娱乐宝的真正目的。"+
						  "据悉，娱乐宝是阿里与国华人寿合作的一款理财产品，投资方向是影视娱乐。"+
						  "娱乐宝首期项目包括电影《小时代3》、《小时代4》、《狼图腾》、《非法操作》，"+
						  "全球首款明星主题的大型社交游戏《魔范学院》等，总投资额为7300万元人民币。"+
						  "其中，影视剧项目投资额为100元每份，游戏项目的投资额为50元每份，每个项目每人限购两份。"+
						  "投资期限约为一年，预期年化收益为7%，不承诺保本保底。"+
						  "一年内领取或退保收取3%的手续费，一年后自动全部领取。"+
						  "";
		SmsSenderUtil sms = new SmsSenderUtil(DEFAULT_HOST,DEFAULT_PORT);
		try {
			sms.send(content, "请填写手机号");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}


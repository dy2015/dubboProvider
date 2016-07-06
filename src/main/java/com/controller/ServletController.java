package com.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.common.constant.Constant;
import com.common.kafka.KafkaProducer;
import com.po.Student;
import com.redis.Redis;
import com.service.StudentService;
import com.util.LogUtil;

import redis.clients.jedis.ShardedJedis;

@Controller
public class ServletController {
	private Logger logger = LogUtil.getLog();

	@Resource
	private StudentService studentService;
	@Autowired
	private Redis redis;

	@RequestMapping("/queryOne")
	public String queryOne(HttpServletRequest request, Model model) {
		String message = "";
		KafkaProducer.produce(Constant.PART_0, "1");

		ShardedJedis jedis = redis.getRedis();
		try {
			String temp = jedis.get("servelt");
			if (temp != null) {
				int time = Integer.parseInt(jedis.ttl("servelt").toString());
				temp = String.valueOf(Integer.parseInt(temp) + 1);
				jedis.setex("servelt", time, temp);
				logger.info("redis统计下请求queryOne:" + temp + "次");
			} else {
				jedis.setex("servelt", 10, "1");
				logger.info("redis统计下请求queryOne:" + jedis.get("servelt") + "次");
			}
			redis.close(jedis);
		} catch (Exception e) {
			logger.info("redis超时");
		}
		if (null == request.getParameter("id") || "".equals(request.getParameter("id"))) {
			message = "未传入查询的ID号";
			logger.info(message);
			model.addAttribute("message", message);
			return "error";
		}
		int id = Integer.valueOf(request.getParameter("id"));
		Student s = studentService.queryOne(id);
		if (s != null) {
			model.addAttribute("student", s);
			return "showStudentOne";
		} else {
			message = "数据库中没有编号为‘" + id + "'的同学的信息！";
			logger.info(message);
			model.addAttribute("message", message);
			return "error";
		}
	}

	@RequestMapping("/queryList")
	public String queryList(HttpServletRequest request, Model model) {
		KafkaProducer.produce(Constant.PART_1, "1");
		List<Student> s = studentService.queryList();
		if (s.size() > 0) {
			model.addAttribute("student", s);
			return "showStudentList";
		} else {
			String message = "数据库中没有同学的信息！";
			logger.info(message);
			model.addAttribute("message", message);
			return "error";
		}
	}

	@RequestMapping("/insetInfo")
	public String insetInfo(HttpServletRequest request, Model model) {
		Student s = new Student();
		String message;
		String name = request.getParameter("name");
		if (null == name || "".equals(name)) {
			message = "未传入姓名";
			logger.info(message);
			model.addAttribute("message", message);
			return "error";
		}
		s.setName(name);
		String temp = "^\\d+";
		String age = request.getParameter("age");
		if (null == age || "".equals(age)) {
			message = "未传入年龄";
			logger.info(message);
			model.addAttribute("message", message);
			return "error";
		}
		if (age.matches(temp)) {
			s.setAge(Integer.valueOf(request.getParameter("age")));
		} else {
			message = "传入的年龄不是纯数字！传入的值=" + request.getParameter("age");
			logger.info(message);
			model.addAttribute("message", message);
			return "error";
		}
		try {
			studentService.insertInfo(s);
			Student student = studentService.queryOne(studentService.queryId());
			model.addAttribute("student", student);
			return "showStudentOne";
		} catch (Exception e) {
			message = "插入数据出错！";
			logger.info(message);
			model.addAttribute("message", message);
			return "error";
		}
	}

	@RequestMapping("/requestCookie")
	public String requestCookie(HttpServletRequest request, Model model, HttpServletResponse respones) throws UnsupportedEncodingException {
		Cookie c = new Cookie("dy", URLEncoder.encode("丁毅", "utf-8"));
		respones.addCookie(c);
		return "redirect:/responesCookie";
	}

	@RequestMapping("/responesCookie")
	public String responesCookie(HttpServletRequest request, Model model, HttpServletResponse respones) throws UnsupportedEncodingException {
		Cookie[] c = request.getCookies();
		for (Cookie cc : c) {
			if ("dy".equals(cc.getName())) {
				System.out.println("CookieDomain=" + cc.getDomain() + ",Cookie存放的数据是：(key=" + cc.getName() + ",value=" + URLDecoder.decode(cc.getValue(), "utf-8") + ")");
			} // end--if
		} // end--for
		return "sucess";
	}
	
	
}

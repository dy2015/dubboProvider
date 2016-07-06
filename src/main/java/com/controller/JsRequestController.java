package com.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.po.Student;
import com.service.StudentService;
import com.util.HttpServletHelper;
import com.util.LogUtil;

@Controller
public class JsRequestController {
	private Logger logger = LogUtil.getLog();

	@Resource
	private StudentService studentService;

	@RequestMapping("/commonRequest")
	public String queryOne(HttpServletRequest request, Model model) {
		String common = request.getParameter("common");
		String value = request.getParameter("value1");
		logger.info("common=" + common);
		logger.info("value=" + value);
		model.addAttribute("message", "普通js请求成功");
		return "sucess";
	}

	@RequestMapping("/ajaxRequest")
	public void queryList(HttpServletRequest request, HttpServletResponse response) {
		String common2 = request.getParameter("common2");
		String value = request.getParameter("value2");
		logger.info("common2=" + common2);
		logger.info("value=" + value);
		Student student=new Student();
        student.setId(74);
        student.setName("丁晨星宇");
		student.setAge(25);
		
		String str = JSON.toJSONString(student);
		logger.info(str);
		
		try {
			HttpServletHelper.WriteJSON(response, str);
		} catch (Exception e) {
			logger.info("ajax请求失败");
			e.printStackTrace();
		}
	}
}

package com.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.service.QueryStudentFacade;
import com.service.StudentService;
import com.vo.Student;

@Service("queryStudentFacade")
public class QueryStudentFacadeImpl implements QueryStudentFacade {
	@Resource
	private StudentService studentService;

	@Override
	public Student queryOne(int id) {
		return studentService.queryOne(id);
	}

	@Override
	public Student queryOne(String name) {
		return studentService.queryOne(name);
	}

	@Override
	public List<Student> queryList() {
		System.out.println("第一台服务");
		return studentService.queryList();
	}

	@Override
	public void insertInfo(Student s) {
		studentService.insertInfo(s);
	}

	@Override
	public int queryId() {
		return studentService.queryId();
	}

}

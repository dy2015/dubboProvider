package com.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dao.StudentDao;
import com.service.StudentService;
import com.vo.Student;
@Component("studentService")
public class StudentServiceImpl implements StudentService {
	@Autowired
	private StudentDao studentDao;
	private static int count = 0;

	public StudentServiceImpl() {
		super();
		System.out.println("StudentServiceImpl被创建第" + (++count) + "次");
	}

	public Student queryOne(int id) {
		return studentDao.queryOneById(id);
	}

	public Student queryOne(String name) {
		return studentDao.queryOneByName(name);
	}

	public List<Student> queryList() {
		return studentDao.queryList();
	}

	public void insertInfo(Student s) {
		studentDao.insertInfo(s);
	}

	public int queryId() {
		return studentDao.queryId();
	}
}

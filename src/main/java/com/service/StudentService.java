package com.service;

import java.util.List;

import com.vo.Student;


public interface StudentService {
	public Student queryOne(int id);

	public Student queryOne(String name);

	public List<Student> queryList();

	public void insertInfo(Student s);

	public int queryId();
}

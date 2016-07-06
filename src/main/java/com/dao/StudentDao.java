package com.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.po.Student;

@Component("studentDao")
public interface StudentDao {
	public Student queryOneById(int id);

	public Student queryOneByName(String name);

	public List<Student> queryList();

	public void insertInfo(Student s);

	public int queryId();
}

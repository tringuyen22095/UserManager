package com.tri.service;

import com.tri.model.Employee;

public interface EmployeeService {
	Employee save(Employee user);

	Employee findById(int id);

	java.util.List<Employee> findAll();

	Employee update(Employee user);

	void delete(int id);
}

package com.tri.service.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.tri.dao.EmployeeRepository;
import com.tri.model.Employee;
import com.tri.service.EmployeeService;

@Service
@Validated
@Transactional
public class EmployeeServiceImpl implements EmployeeService {
	@Autowired
	private EmployeeRepository repository;
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Employee save(Employee user) {
		if (repository.findOne(user.getId()) == null) {
			repository.save(user);
		}
		return null;
	}

	@Override
	public Employee findById(int id) {
		return repository.findOne(id);
	}

	@Override
	public List<Employee> findAll() {
		return repository.findAll();
	}

	@Override
	public Employee update(Employee user) {
		if (!entityManager.contains(user)) {
			user = entityManager.merge(user);
			return user;
		} else {
			return null;
		}
	}

	@Override
	public void delete(int id) {
		repository.delete(id);
	}

}

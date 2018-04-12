package com.tri.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tri.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

}
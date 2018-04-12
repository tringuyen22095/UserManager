package com.tri.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tri.model.Users;

@Repository
public interface UsersRepository extends CrudRepository<Users, Integer> {
	@Query("FROM Users u WHERE u.userName = :name")
	public Users getUserByUserName(@Param("name") String name);

	@Query("FROM Users u WHERE u.email = :email")
	public Users getUserByEmail(@Param("email") String email);
}

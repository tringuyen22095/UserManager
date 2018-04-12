package com.tri.service;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.tri.model.Users;

public interface UsersService {
	Users findUserByUserName(String username);

	Users findUserByEmail(String email);

	List<SimpleGrantedAuthority> getAuthorityByUserId();
}

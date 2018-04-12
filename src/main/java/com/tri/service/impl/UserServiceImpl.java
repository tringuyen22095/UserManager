package com.tri.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tri.common.Utils;
import com.tri.dao.UsersRepository;
import com.tri.model.Users;
import com.tri.service.UsersService;

@Service(value = "userService")
@Transactional
public class UserServiceImpl implements UsersService, UserDetailsService {

	@Autowired
	private UsersRepository userDao;

	@Override
	public Users findUserByUserName(String username) {
		return userDao.getUserByUserName(username);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Users user = findUserByUserName(username);

		if (user == null) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}

		String hash = user.getPassword();
		return new org.springframework.security.core.userdetails.User(username, hash, getAuthorityByUserId());
	}

	@Override
	public List<SimpleGrantedAuthority> getAuthorityByUserId() {
		// get role
		return Utils.getAuthorities(null);
	}

	@Override
	public Users findUserByEmail(String email) {
		return userDao.getUserByEmail(email);
	}
}
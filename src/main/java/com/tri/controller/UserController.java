package com.tri.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tri.config.JwtTokenUtil;
import com.tri.dao.UsersRepository;
import com.tri.message.CustomMessageReceive;
import com.tri.model.Users;
import com.tri.req.SocialLoginReq;
import com.tri.req.UserLoginReq;
import com.tri.service.UsersService;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UsersService userService;

	@Autowired
	private UsersRepository userDao;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@RequestMapping(value = "/signIn", method = RequestMethod.POST)
	public ResponseEntity<?> login(@Valid @RequestBody UserLoginReq body) {
		CustomMessageReceive res = new CustomMessageReceive();

		try {
			// Get data
			String userName = body.getUserName();
			String password = body.getPassword();

			// Handle
			Users m = userService.findUserByUserName(userName);
			if (m == null) {
				res.setCallStatus("Fail");
				res.setMessage("You do not have account created. Please signup and continue!");
			} else {
				UsernamePasswordAuthenticationToken x = new UsernamePasswordAuthenticationToken(userName, password);
				Authentication y = authenticationManager.authenticate(x);
				SecurityContextHolder.getContext().setAuthentication(y);

				List<SimpleGrantedAuthority> z = userService.getAuthorityByUserId();
				String token = jwtTokenUtil.doGenerateToken(m, z);

				// Set data
				res.setCallStatus("Success");
				res.setResult(token);
			}
		} catch (AuthenticationException e) {
			res.setCallStatus("Fail");
			res.setMessage("Unauthorized / Invalid email or password!");
		} catch (Exception ex) {
			res.setCallStatus("Fail");
			res.setMessage(ex.getMessage());
		}

		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/signInSocial", method = RequestMethod.POST)
	public ResponseEntity<?> login(@Valid @RequestBody SocialLoginReq body) {
		CustomMessageReceive res = new CustomMessageReceive();

		try {
			String email = body.getEmail();

			Users m = userService.findUserByEmail(email);
			if (m == null) {
				m = new Users();
				m.setEmail(email);
				m.setUserName(email);
				m.setPassword(email);
				signUpSocial(m);
			}
			UsernamePasswordAuthenticationToken x = new UsernamePasswordAuthenticationToken(email, email);
			Authentication y = authenticationManager.authenticate(x);
			SecurityContextHolder.getContext().setAuthentication(y);

			List<SimpleGrantedAuthority> z = userService.getAuthorityByUserId();
			String token = jwtTokenUtil.doGenerateToken(m, z);

			// Set data
			res.setCallStatus("Success");
			res.setResult(token);
		} catch (AuthenticationException e) {
			res.setCallStatus("Fail");
			res.setMessage("Unauthorized / Invalid email or password!");
		} catch (Exception ex) {
			res.setCallStatus("Fail");
			res.setMessage(ex.getMessage());
		}

		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/signUp", method = RequestMethod.POST)
	public ResponseEntity<?> signUp(@RequestBody Users user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		userDao.save(user);
		CustomMessageReceive res = new CustomMessageReceive();
		res.setCallStatus("Success");
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	private boolean signUpSocial(Users user) {
		boolean rs = true;
		try {
			user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
			userDao.save(user);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			rs = false;
		}
		return rs;
	}
}

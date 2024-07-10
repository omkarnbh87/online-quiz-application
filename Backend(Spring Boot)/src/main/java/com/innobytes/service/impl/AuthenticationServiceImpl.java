package com.innobytes.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.innobytes.constants.ErrorCodeEnum;
import com.innobytes.constants.Role;
import com.innobytes.dao.UserDao;
import com.innobytes.exception.QuizServiceException;
import com.innobytes.pojo.JwtResponse;
import com.innobytes.pojo.SignInRequest;
import com.innobytes.pojo.SignUpRequest;
import com.innobytes.service.AuthenticationService;
import com.innobytes.service.JwtService;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtService jwtService;

	@Override
	public boolean register(SignUpRequest signUpRequest) {
		System.out.println("AuthenticationServiceImpl.saveUser inoked");

		signUpRequest.setRole(Role.STUDENT.getRoleId());
		signUpRequest.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));

		boolean isUserSaved = userDao.saveUser(signUpRequest);

		if (!isUserSaved) {
			System.out.println("User not saved something went wrong!!!");
			// TODO throw exception
			return false;
		}
		System.out.println("User saved successfully");

		return true;
	}

	@Override
	public JwtResponse signin(SignInRequest signInRequest) {
		System.out.println("AuthenticationServiceImpl.signin invoked");

		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(signInRequest.getEmail(), signInRequest.getPassword()));
		var user = userDao.getUserByEmail(signInRequest.getEmail());

		if (user == null) {
			throw new QuizServiceException(HttpStatus.BAD_REQUEST,
					ErrorCodeEnum.USER_NOT_FOUND.getErrorCode(),
					ErrorCodeEnum.USER_NOT_FOUND.getErrorMessage());
		}

		String jwt = jwtService.generateToken(user);

		JwtResponse jwtResponse = JwtResponse.builder()
				.token(jwt)
				.role(Role.getRoleEnumById(user.getRole()).getRoleName())
				.build();

		System.out.println("generated token:" + jwt);
		return jwtResponse;
	}

}

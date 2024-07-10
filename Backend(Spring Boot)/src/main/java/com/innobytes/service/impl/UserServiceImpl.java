package com.innobytes.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.innobytes.constants.ErrorCodeEnum;
import com.innobytes.dao.UserDao;
import com.innobytes.dto.User;
import com.innobytes.exception.QuizServiceException;
import com.innobytes.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;
	
	@Override
	public UserDetailsService userDetailsService() {
		return new UserDetailsService() {

			@Override
			public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
				User user = userDao.getUserByEmail(username);
				if (user == null) {
					throw new QuizServiceException(HttpStatus.BAD_REQUEST,
							ErrorCodeEnum.USER_NOT_FOUND.getErrorCode(),
							ErrorCodeEnum.USER_NOT_FOUND.getErrorMessage());
				}
				return user;
			}

		};
	}

}

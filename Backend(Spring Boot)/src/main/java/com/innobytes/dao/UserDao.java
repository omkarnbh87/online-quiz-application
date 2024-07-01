package com.innobytes.dao;

import com.innobytes.dto.User;
import com.innobytes.pojo.SignUpRequest;

public interface UserDao {

	boolean saveUser(SignUpRequest signUpRequest);
	
	User getUserByEmail(String email);
	
	User getUserByRoleId(int roleId);
}

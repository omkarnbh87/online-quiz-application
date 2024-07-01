package com.innobytes.service;

import com.innobytes.pojo.JwtResponse;
import com.innobytes.pojo.SignInRequest;
import com.innobytes.pojo.SignUpRequest;

public interface AuthenticationService {

	public boolean register(SignUpRequest signUpRequest);
		
	JwtResponse signin(SignInRequest signInRequest);
}

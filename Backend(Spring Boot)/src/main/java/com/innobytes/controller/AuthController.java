package com.innobytes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.innobytes.pojo.JwtResponse;
import com.innobytes.pojo.SignInRequest;
import com.innobytes.pojo.SignUpRequest;
import com.innobytes.service.AuthenticationService;

@RestController
@RequestMapping("/v1/auth")
public class AuthController {

	@Autowired
	private AuthenticationService authenticationService;

	@PostMapping("/reg")
	public ResponseEntity<String> registerUser(@RequestBody SignUpRequest signUpRequest) {
		System.out.println("AuthController.registerUser invoked || signUpRequest:" + signUpRequest);

		if (authenticationService.register(signUpRequest))
			return new ResponseEntity<String>("User Register Successfully", HttpStatus.CREATED);
		return new ResponseEntity<String>("User Not Register!!!", HttpStatus.INTERNAL_SERVER_ERROR);

	}

	@PostMapping("/login")
	public ResponseEntity<JwtResponse> signin(@RequestBody SignInRequest signInRequest) {
		System.out.println("AuthController.signin invoked || signInRequest:" + signInRequest);

		return ResponseEntity.ok(authenticationService.signin(signInRequest));

	}
}

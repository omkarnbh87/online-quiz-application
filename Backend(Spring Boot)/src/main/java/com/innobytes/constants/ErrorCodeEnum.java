package com.innobytes.constants;

import lombok.Getter;

public enum ErrorCodeEnum {
	GENERIC_EXCEPTION("10001", "Something went wrong, please try later"),
	USER_NOT_FOUND("10002", "User not found for provided email"),
	INVALID_CREDENTIALS("10003", "Invalid email or password");

	@Getter
	private String errorCode;
	@Getter
	private String errorMessage;

	private ErrorCodeEnum(String errorCode, String errorMessage) {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

}

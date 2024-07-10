package com.innobytes.constants;

import lombok.Getter;

public enum Role {

	STUDENT(1,"STUDENT"),
	ADMIN(2, "ADMIN");

	@Getter
	private Integer roleId;
	@Getter
	private String roleName;

	private Role(Integer roleId, String roleName) {
		this.roleId = roleId;
		this.roleName = roleName;
	}

	public static Role getRoleEnum(String roleName) {
		for (Role e : Role.values()) {
			if (roleName.equals(e.roleName))
				return e;
		}
		return null;
	}

	public static Role getRoleEnumById(int id) {
		for (Role e : Role.values()) {
			if (id == e.roleId)
				return e;
		}
		return null;
	}

}

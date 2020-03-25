package com.petshop.models.authority;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Role {
	USER, ADMIN, ROOT;

	@JsonCreator
	public static Role create(String value) {
		if (value == null) {
			return null;
		}
		for (Role role : values()) {
			if (value.equals(role.name())) {
				return role;
			}
		}
		return null;

	}
}

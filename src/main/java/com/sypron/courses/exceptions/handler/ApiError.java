package com.sypron.courses.exceptions.handler;

import lombok.Data;

@Data
public class ApiError {

	private String  code;
	private String tech_msg;

	public ApiError(String code, String tech_msg) {
		this.code = code;
		this.tech_msg = tech_msg;
	}

}

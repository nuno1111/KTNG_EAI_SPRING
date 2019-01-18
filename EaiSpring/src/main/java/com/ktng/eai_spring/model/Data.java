package com.ktng.eai_spring.model;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author 한동훈
 * 2019-01-16
 * EAI Rest 연계를 위한 Egov(Spring) 기반  공통 모듈
 *
 */

public class Data
{
    @JsonProperty("DATA")
	private Object data;

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
    
    
	
}

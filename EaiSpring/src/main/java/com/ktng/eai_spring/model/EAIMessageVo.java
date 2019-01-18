package com.ktng.eai_spring.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author 한동훈
 * 2019-01-16
 * EAI Rest 연계를 위한 Egov(Spring) 기반  공통 모듈
 *
 */

public class EAIMessageVo {	 
	
	@JsonProperty("HEADER") 
	private Header header;
	
	@JsonProperty("BODY")
	private String body;

	public Header getHeader() {
		return header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
	
	


}
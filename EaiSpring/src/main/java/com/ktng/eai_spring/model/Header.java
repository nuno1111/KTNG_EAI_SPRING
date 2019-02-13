package com.ktng.eai_spring.model;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author 한동훈
 * 2019-01-16
 * EAI Rest 연계를 위한 Egov(Spring) 기반  공통 모듈
 *
 */

public class Header
{
    @JsonProperty("IF_ID")
	private String ifId;
	
    @JsonProperty("IF_TRC_ID")
	private String ifTrcId;
    
    @JsonProperty("ADDITIONAL_INFO")
	private String additionalInfo;
    
    @JsonProperty("RST_CD")
	private String rstCd;
    
    @JsonProperty("RST_MSG")
	private String rstMsg;
	
	public Header() {
		String uuid = UUID.randomUUID().toString();
		setIfTrcId(uuid);
	}

	public String getIfId() {
		return ifId;
	}

	public void setIfId(String ifId) {
		this.ifId = ifId;
	}

	public String getIfTrcId() {
		return ifTrcId;
	}

	public void setIfTrcId(String ifTrcId) {
		this.ifTrcId = ifTrcId;
	}	

	public String getAdditionalInfo() {
		return additionalInfo;
	}

	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}

	public String getRstCd() {
		return rstCd;
	}

	public void setRstCd(String rstCd) {
		this.rstCd = rstCd;
	}

	public String getRstMsg() {
		return rstMsg;
	}

	public void setRstMsg(String rstMsg) {
		this.rstMsg = rstMsg;
	}
}

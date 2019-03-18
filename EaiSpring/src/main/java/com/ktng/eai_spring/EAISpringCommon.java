package com.ktng.eai_spring;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ktng.eai_spring.model.Data;
import com.ktng.eai_spring.model.EAIMessageVo;
import com.ktng.eai_spring.model.Header;

/**
 * @author 한동훈
 * 2019-01-16
 * EAI Rest 연계를 위한 Egov(Spring) 기반  공통 모듈
 *
 */

public class EAISpringCommon {

	public static String callInterface(String url, String ifId, String additionalInfo, Object dataObject) throws JsonProcessingException
	{
		
		RestTemplate rt = new RestTemplate();
		
//		[2019-03-18] Header Encoding 제거 소스 추가 시작 by 한동훈
		StringHttpMessageConverter shm = (StringHttpMessageConverter)rt.getMessageConverters().get(1);		
		shm.setWriteAcceptCharset(false);
//		[2019-03-18]  Header Encoding 제거 소스 추가 끝  by 한동훈
		
		ObjectMapper objectMapper = new ObjectMapper();
		
		Header header = new Header();
		header.setIfId(ifId);
		header.setAdditionalInfo(additionalInfo);
		
		Data data = new Data();
		data.setData(dataObject);
						
		EAIMessageVo messageVO = new EAIMessageVo();		
		messageVO.setHeader(header);		

		String messageVOJson = "";
		String result = "";
		
		try {
			String dataVOJson = objectMapper.writeValueAsString(data);			
			messageVO.setBody(dataVOJson);
			messageVOJson = objectMapper.writeValueAsString(messageVO);		
		} catch (JsonProcessingException e) {
			throw e;
		}		
		
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
		headers.add(HttpHeaders.ACCEPT_CHARSET, Charset.forName("UTF-8").name());
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

		HttpEntity entity = new HttpEntity(messageVOJson, headers);
		
		url = url + "/" + ifId;
		
		result = rt.postForObject(
				url,
				entity,
				String.class);
		
//		System.out.println("messageVOJson : " + messageVOJson);

		return result;		
	}	
	
	public static String callInterface(String url, String ifId, Object dataObject) throws JsonProcessingException	
	{
		return callInterface(url, ifId, "", dataObject);
	}
}
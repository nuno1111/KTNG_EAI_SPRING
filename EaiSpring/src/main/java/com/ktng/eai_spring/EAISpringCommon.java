package com.ktng.eai_spring;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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

	public static String callInterface(String url, String ifId, String targetSystemCode ,Object dataObject) throws JsonProcessingException
	{
		
		RestTemplate rt = new RestTemplate();
//		rt.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
//		rt.getMessageConverters().add(new StringHttpMessageConverter(Charset.forName("UTF-8")));	
		
		ObjectMapper objectMapper = new ObjectMapper();
		
		Header header = new Header();
		header.setIfId(ifId);
		header.setTargetSystemCode(targetSystemCode);
		
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
		headers.add(HttpHeaders.ACCEPT_CHARSET, StandardCharsets.UTF_8.name());
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

		HttpEntity entity = new HttpEntity(messageVOJson, headers);
		
		result = rt.postForObject(
				url,
				entity,
				String.class);
		
		System.out.println("messageVOJson : " + messageVOJson);

		return result;		
	}	
	
}
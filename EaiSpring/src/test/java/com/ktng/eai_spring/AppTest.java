package com.ktng.eai_spring;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ktng.eai_spring.model.DATA;
import com.ktng.eai_spring.model.EAIMessageVo;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
    	System.out.println("TEST Start");
    	
    	// VO -> Json 변환 Json -> VO 변환 Class 
    	ObjectMapper objectMapper = new ObjectMapper();
    	
    	// EAI Config 설정
		String url = "http://10.102.6.62:9999/account"; // EAI 호출 URL 별도 Properties 관리 필요
		String ifId = "SO_PTPERP_RJ_0001"; // EAI 호출 인터페이스 ID 별도 Properties 관리 필요
		String targetSystemCode = "ERP"; // EAI 호출 TargetSystem 별도 Properties 관리 필요
		
		// EAI Input Data 및 Select Parameter설정
		// DATA클래스(VO)는 임의로 생성한것이고 기존 사용중이신 VO를 쓰시면 됩니다.
		
		// 1. 다수의 Input Data
		List listData = new ArrayList<DATA>();		
		DATA data1 = new DATA();
		data1.setPARAM("1231");
		data1.setPARAM1("123412");
		data1.setPARAM2("12341234");
		listData.add(data1);
		
		DATA data2 = new DATA();
		data2.setPARAM("1231");
		data2.setPARAM1("123412");
		data2.setPARAM2("12341234");
		listData.add(data2);		
		
		// 2. 1건의 Input Data 혹은 조회 Parameter 		
//		DATA data1 = new DATA();
//		data1.setPARAM("1231");
//		data1.setPARAM1("123412");
//		data1.setPARAM2("12341234");

		
		String bodyJson = null;
		String result = null;		
		
		try {
			// EAI전송을위해 VO데이터를 json 변환
			bodyJson = objectMapper.writeValueAsString(listData);
			
			// EAI로 REST 호출 결과 수신
			result = EAISpringCommon.callInterface(url, ifId, targetSystemCode, bodyJson);
			
			
		} catch (JsonProcessingException e) {
			// 예외 처리 부분
		}

		System.out.println("result : " + result);
		
		EAIMessageVo messageVo = null;
		
		// String 으로 읽기		
		try {			
			
			// SELECT인 경우 받은 JSON을 VO로 전환
			messageVo = objectMapper.readValue(result, EAIMessageVo.class);
			
			System.out.println("messageVo : " + messageVo);
			System.out.println("messageVo.getHeader().getRstMsg() : " + messageVo.getHeader().getRstMsg());
			System.out.println("messageVo.getHeader().getRstCd() : " + messageVo.getHeader().getRstCd());
			
			System.out.println("messageVo.getBody() : " + messageVo.getBody());
			
			// Body 부분을 객체로 전환
			// Case 1 응답이 List인경우
			List resultVOList  = objectMapper.readValue( messageVo.getBody(), ArrayList.class);
									
			for (Object object : resultVOList) {
				System.out.println("resultMap : " + (LinkedHashMap)object);
			}
			
			// Case 2 응답이 단건인경우
//			DATA resultVO  = objectMapper.readValue( messageVo.getBody(), DATA.class);									
//						
//			System.out.println("resultVO.getPARAM() : " + resultVO.getPARAM());
			
		} catch (IOException e) {		
			e.printStackTrace();
		}
		
        assertTrue( true );
    }
}

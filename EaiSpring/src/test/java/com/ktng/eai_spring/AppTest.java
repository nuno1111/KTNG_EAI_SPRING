package com.ktng.eai_spring;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ktng.eai_spring.model.Data;
import com.ktng.eai_spring.model.EAIMessageVo;
import com.ktng.eai_spring.model.InputVO;

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
    	interfaceSingular(); // 단수건 REST 송수신 테스트
//    	interfacePlural();   // 복수건 REST 송수신 테스트
		
        assertTrue( true );
    }
    
    public void interfaceSingular(){
    	System.out.println("[TEST] interfaceSingular Start");
    	
    	// VO -> Json 변환 Json -> VO 변환 Class 
    	ObjectMapper objectMapper = new ObjectMapper();
    	
    	// EAI Config 설정
		String url = "http://10.102.6.62:9999/account"; // EAI 호출 URL 별도 Properties 관리 필요
		String ifId = "MDM_MISDB_QRSRS_001"; // EAI 호출 인터페이스 ID 별도 Properties 관리 필요
		String targetSystemCode = "MIS"; // EAI 호출 TargetSystem 별도 Properties 관리 필요
		
		// EAI Input Data 및 Select Parameter설정
		// InputVO클래스(VO)는 임의로 생성한것이고 기존 사용중이신 VO를 쓰시면 됩니다.
		
		// 2. 1건의 Input Data 혹은 조회 Parameter 		
		InputVO input1 = new InputVO();
		input1.setPARAM("1231");
		input1.setPARAM1("123412");
		input1.setPARAM2("12341234");
		
		String dataJson = null;
		String result = null;		
		
		try {
			
			// EAI로 REST 호출 결과 수신
			result = EAISpringCommon.callInterface(url, ifId, targetSystemCode, input1);
			
			
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
//			System.out.println("messageVo.getBody().getData() : " + messageVo.getBody().getData());
			
			// Body 부분을 객체로 전환
			
			Data resultData = objectMapper.readValue( messageVo.getBody(), Data.class);
			
			LinkedHashMap resultMap  = (LinkedHashMap)resultData.getData();
			
			System.out.println(resultMap);
			
		} catch (IOException e) {		
			e.printStackTrace();
		}
		
		System.out.println("[TEST] interfaceSingular End");
    }
    
    public void interfacePlural(){
    	System.out.println("[TEST] interfacePlural Start");
    	
    	// VO -> Json 변환 Json -> VO 변환 Class 
    	ObjectMapper objectMapper = new ObjectMapper();
    	
    	// EAI Config 설정
		String url = "http://10.102.6.62:9999/account"; // EAI 호출 URL 별도 Properties 관리 필요
		String ifId = "MDM_MISDB_QRSRS_002"; // EAI 호출 인터페이스 ID 별도 Properties 관리 필요
		String targetSystemCode = "MIS"; // EAI 호출 TargetSystem 별도 Properties 관리 필요
		
		// EAI Input Data 및 Select Parameter설정
		// InputVO클래스(VO)는 임의로 생성한것이고 기존 사용중이신 VO를 쓰시면 됩니다.
		
		// 1. 다수의 Input Data
		List listVO = new ArrayList<InputVO>();		
		InputVO input1 = new InputVO();
		input1.setPARAM("1231");
		input1.setPARAM1("123412");
		input1.setPARAM2("12341234");
		listVO.add(input1);
		
		InputVO input2 = new InputVO();
		input2.setPARAM("1231");
		input2.setPARAM1("123412");
		input2.setPARAM2("12341234");
		listVO.add(input2);	

		
		String dataJson = null;
		String result = null;		
		
		try {
			
			// EAI로 REST 호출 결과 수신
			result = EAISpringCommon.callInterface(url, ifId, targetSystemCode, listVO);
			
			
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
			Data resultData = objectMapper.readValue( messageVo.getBody(), Data.class);
			
			List resultVOList  = (List)resultData.getData();
			
			for (Object object : resultVOList) {
				System.out.println("resultMap : " + (LinkedHashMap)object);
			}
			
			
		} catch (IOException e) {		
			e.printStackTrace();
		}
		
    	System.out.println("[TEST] interfacePlural End");

    	
    }
    
}

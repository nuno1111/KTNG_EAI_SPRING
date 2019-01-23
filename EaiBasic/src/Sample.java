import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;

public class Sample {
	public static void main(String[] args) {
		
		EAIMessageVo requestVo = new EAIMessageVo();
		
		// EAI Config 설정
		String strUrl = "http://10.102.6.62:9999/account"; // EAI 호출 URL 별도 Properties 관리 필요
		String ifId = "MDM_MISDB_QRSRS_002"; // EAI 호출 인터페이스 ID 별도 Properties 관리 필요
		String targetSystemCode = "MIS"; // (생략가능)EAI 호출 TargetSystem 별도 Properties 관리 필요
		
		
		// EAI 헤더 세팅
		requestVo.setIF_ID(ifId);
		// 생략 가능. 생략 시 java uuid의 랜덤함수를 호출하여 설정
		// 업무의 Unique id를 사용할 경우 셋팅하면 EAI에서 조회 가능
		// requestVo.setIF_TRC_ID(""); 
		requestVo.setTARGET_SYSTEM_CODE(targetSystemCode);
		requestVo.setADDITIONAL_INFO("");
		requestVo.setRST_CD("");
		requestVo.setRST_MSG("");
		
		// 클라이언트 전문 세팅부
		JSONObject bodyJsonObject = new JSONObject();
		JSONArray bodyDataJsonArrayObject = new JSONArray();
		JSONObject dataJsonObject = new JSONObject();
		dataJsonObject.put("PARAM", 123);
		dataJsonObject.put("PARAM1", "AAA");
		dataJsonObject.put("PARAM2", "가나다");
		bodyDataJsonArrayObject.put(dataJsonObject);
		dataJsonObject = new JSONObject();
		dataJsonObject.put("PARAM", 456);
		dataJsonObject.put("PARAM1", "BBB");
		dataJsonObject.put("PARAM2", "타파하");
		bodyDataJsonArrayObject.put(dataJsonObject);
		bodyJsonObject.put("DATA", bodyDataJsonArrayObject);
		
		// EAI 바디 세팅
		requestVo.setBODY(bodyJsonObject.toString());
		
		// String일 경우 아래와 같이 setBODY만 사용
//		requestVo.setBODY("\"HELLO\": \"Here's JSON string\"");

		// EAI 요청 전문
		String eaiReauestData = requestVo.getEAIRequestData();

		try {
			URL url = new URL(strUrl+"/"+ifId);
			
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setDoOutput(true);
			
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/json");
			
			OutputStream os = con.getOutputStream();
			
			os.write(eaiReauestData.getBytes("UTF-8"));
			os.flush();
			
			BufferedReader br = new BufferedReader(new InputStreamReader((con.getInputStream()), "UTF-8"));
			
			StringBuilder resultStringBuilder = new StringBuilder();
			
			String output;
			 
			while ( (output = br.readLine()) != null) {
				resultStringBuilder.append(output);
			}
			
			con.disconnect();
			
			// 응답 처리
			EAIMessageVo reponseVo = new EAIMessageVo(resultStringBuilder.toString());
			
			System.out.println("Response IF_ID = " + reponseVo.getIF_ID());
			System.out.println("Response IF_TRC_ID = " + reponseVo.getIF_TRC_ID());
			System.out.println("Response TARGET_SYSTEM_CODE = " + reponseVo.getTARGET_SYSTEM_CODE());
			System.out.println("Response ADDITIONAL_INFO = " + reponseVo.getADDITIONAL_INFO());
			System.out.println("Response RST_CD = " + reponseVo.getRST_CD());
			System.out.println("Response RST_MSG = " + reponseVo.getRST_MSG());
			
			System.out.println("Response BODY = " + reponseVo.getBODY());
			
		} catch (Exception e) {
			System.out.println("?");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}

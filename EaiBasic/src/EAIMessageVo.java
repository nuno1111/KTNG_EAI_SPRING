import java.util.UUID;

import org.json.JSONObject;

public class EAIMessageVo {
	
	private String IF_ID;
	private String IF_TRC_ID;
	private String ADDITIONAL_INFO;
	private String RST_CD;
	private String RST_MSG;
	private String BODY;
	
	public EAIMessageVo() {
		String uuid = UUID.randomUUID().toString();
		setIF_TRC_ID(uuid);
	}
	
	public EAIMessageVo(String responseData) {
		JSONObject responseJsonObject = new JSONObject(responseData);
		
		JSONObject responseHeaderJsonObject = responseJsonObject.getJSONObject("HEADER");

		// null 처리 추가
		String IF_ID = responseHeaderJsonObject.has("IF_ID") ? responseHeaderJsonObject.get("IF_ID").toString() : null;
		String IF_TRC_ID = responseHeaderJsonObject.has("IF_TRC_ID") ? responseHeaderJsonObject.get("IF_TRC_ID").toString() : null;
		String ADDITIONAL_INFO = responseHeaderJsonObject.has("ADDITIONAL_INFO") ? responseHeaderJsonObject.get("ADDITIONAL_INFO").toString() : null;
		String RST_CD = responseHeaderJsonObject.has("RST_CD") ? responseHeaderJsonObject.get("RST_CD").toString() : null;
		String RST_MSG = responseHeaderJsonObject.has("RST_MSG") ? responseHeaderJsonObject.get("RST_MSG").toString() : null;
		String BODY = responseJsonObject.has("BODY") ? responseJsonObject.get("BODY").toString() : null;
		
		setIF_ID(IF_ID);
		setIF_TRC_ID(IF_TRC_ID);
		setADDITIONAL_INFO(ADDITIONAL_INFO);
		setRST_CD(RST_CD);
		setRST_MSG(RST_MSG);
		setBODY(BODY);
	}
	
	public String getEAIRequestData() {
		JSONObject requestJsonObject = new JSONObject();
		
		JSONObject headerJsonObject = new JSONObject();
		headerJsonObject.put("IF_ID", getIF_ID());
		headerJsonObject.put("IF_TRC_ID", getIF_TRC_ID());
		headerJsonObject.put("ADDITIONAL_INFO", getADDITIONAL_INFO());
		headerJsonObject.put("RST_CD", getRST_CD());
		headerJsonObject.put("RST_MSG", getRST_MSG());
		
	    /* 기존 */
//		JSONObject bodyJsonObject = new JSONObject(getBODY());
//		requestJsonObject.put("HEADER", headerJsonObject);
//		requestJsonObject.put("BODY", bodyJsonObject.toString());
	    
		/* 신규  */
		requestJsonObject.put("HEADER", headerJsonObject);
		requestJsonObject.put("BODY", getBODY());
      
		return requestJsonObject.toString();
	}
	
	
	public String getIF_ID() {
		return IF_ID;
	}
	public void setIF_ID(String iF_ID) {
		IF_ID = iF_ID;
	}
	public String getIF_TRC_ID() {
		return IF_TRC_ID;
	}
	public void setIF_TRC_ID(String iF_TRC_ID) {
		if (!iF_TRC_ID.trim().equals("")) {
			IF_TRC_ID = iF_TRC_ID;
		}
	}
	public String getADDITIONAL_INFO() {
		return ADDITIONAL_INFO;
	}
	public void setADDITIONAL_INFO(String aDDITIONAL_INFO) {
		ADDITIONAL_INFO = aDDITIONAL_INFO;
	}
	public String getRST_CD() {
		return RST_CD;
	}
	public void setRST_CD(String rST_CD) {
		RST_CD = rST_CD;
	}
	public String getRST_MSG() {
		return RST_MSG;
	}
	public void setRST_MSG(String rST_MSG) {
		RST_MSG = rST_MSG;
	}
	public String getBODY() {
		return BODY;
	}
	public void setBODY(String bODY) {
		BODY = bODY;
	}
}
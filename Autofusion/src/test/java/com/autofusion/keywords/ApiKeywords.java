package com.autofusion.keywords;

/***
 * @author Nitin Singh
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.WebElement;

import com.autofusion.api.JSONParser;
import com.autofusion.api.MakeHttpRequest;
import com.autofusion.constants.Constants;
import com.autofusion.constants.KeywordConstant;
import com.autofusion.util.ReportUtil;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
 
@SuppressWarnings({"rawtypes","unused","all"})
public class ApiKeywords extends Keyword{
	JSONParser jsonParser = new JSONParser();
	MakeHttpRequest makeHttpRequest= new MakeHttpRequest();
	protected static JSONObject jsonObject = null;
	protected static String  sJson = "";
	protected static HttpResponse httpResponse = null;
	public static Map<String,String> runTimeDataMap = new HashMap<String, String>();
	private static String accessToken = "";
	protected static JsonElement jsonElement;
	private static String token_type;
	private static String expires_in;
	private static String refresh_token;
	ReportUtil reportUtil;
    WebElement element;
	String elementId;
	String inputValue;
	String componentName = "";
	Map<String, String> argsMap = new HashMap<String, String>();
	 
	 
	 public ApiKeywords(Logger log) {
			APP_LOG = log;
	 }
	
 /**
  * Return Json Header
  * @return
  */
	
   public Header[] getHeaderFromResponse(){
	    return getHttpResponse().getAllHeaders();
  }
  
   
   /*** 
    * Save data from Header
    * @param args
    * @return
    */
  public String apiSaveHeaderInformation(Map<String, String> args){
	  
	  String flagResult= Constants.FAIL;
	  String dataSave = "";
	  String[] data = args.get(KeywordConstant.ELEMENT_INPUT_VALUE).split(","); 
	  for(int i=0; i< data.length; i++){
		  if(getHttpResponse().containsHeader(data[i])){
			  runTimeDataMap.put(data[i], getHttpResponse().getFirstHeader(data[i]).getValue());
			  flagResult = Constants.PASS;
			  dataSave+= data[i];
		  }
	   }
	  
	  return flagResult+"#"+dataSave;
  }

/**
 * Main function to execute the API if we have Request parameter as a JSON and get the header
 * @param args
 * @return
 */
	public String apiGetHeaderInformations(Map<String, String> args){
		 
		String serviceName =  args.get("EndPoint")+args.get("Resource");
		String reqParameter = args.get("Parameters");
		String method = args.get("Method");
		
		HttpResponse httpResponse = jsonParser.makeHttpRequestJSonObjGetResponse(serviceName, method, reqParameter);
		
		setHttpResponse(httpResponse);
 		return Constants.PASS+"# ";
	}
	


/**
 * Main function to execute the API if we have Request parameter as a JSON
 * @param args
 * @return
 */
	public int executeServiceRequestDataForJsonRequest(Map<String, String> args){

		String serviceName =  args.get("EndPoint")+args.get("Resource");
		String reqParameter = args.get("Parameters");
		String method = args.get("Method");
		String header = args.get("Header");
		int  statusCode = 0;
		if(header.isEmpty()){
			System.out.println("Header Is Empty");
			header="";
			statusCode = jsonParser.makeHttpRequestJSonObjToGetStatusCode(serviceName, method, reqParameter, header);
		}
		else{
			String token = "";

			/**********Replace the authentication token ***********/
			if(header.contains("<ACCESS_TOKEN>")){
				token=getAccessToken(); 
				header = header.replace("<ACCESS_TOKEN>", token);
			}
			statusCode = jsonParser.makeHttpRequestJSonObjToGetStatusCode(serviceName, method, reqParameter,header);

		}
		return statusCode;
	}
	
	/**
	 * Main function to execute the API if we have request as key value pair
	 * @param args
	 * @return
	 */
	
	public String executeServiceRequestData(Map<String, String> args){
		//Parse the dynamic data 
		
		args = parseRequestParameter(args);
		
		String serviceName =  args.get("EndPoint")+args.get("Resource");
		String reqParameter = args.get("Parameters");
		String method = args.get("Method");
		String header =  args.get("Header");
		String[] dataArr;
		dataArr = reqParameter.split("&");
		String key="";
		String value="";
		/** for MBL APIs **/
		String token=""; 
 		
		//System.out.println(serviceName);
		//System.out.println(reqParameter);
		//System.out.println(header);
		APP_LOG.debug("Func executeServiceRequestData: serviceName ="+serviceName);
		APP_LOG.debug("Func executeServiceRequestData: reqParameter="+reqParameter);
		APP_LOG.debug("Func executeServiceRequestData: header="+header);
		
		 
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		for(int i=0; i < dataArr.length ; i++){
			value = "";
		   	String[] keyValue = dataArr[i].split("=");
		   	 key = keyValue[0];
		   	 if(keyValue.length >1)
		   		 value = keyValue[1];
		 if(!key.trim().equals("")){
			params.add(new BasicNameValuePair(key, value));
		 }
		}
		/**********Replace the authentication token ***********/
		 if(header!=null && header.contains("<ACCESS_TOKEN>")){
			 token=getAccessToken(); 
			 header = header.replace("<ACCESS_TOKEN>", token);
		 }
		 
		 String sJson = jsonParser.makeHttpRequestArray(serviceName, method, params, token, header);
		 APP_LOG.debug("JSON : "+sJson);
		 jsonElement= new Gson().fromJson(sJson, JsonElement.class);
		 setJsonElement(jsonElement); 
		
		 /******Save Authentication token details *******************************/
		 if(args.get(KeywordConstant.ELEMENT_INPUT_VALUE) != null && args.get(KeywordConstant.ELEMENT_INPUT_VALUE).equals("AuthenticateService") && !args.get("data1").equals("TestErrorMessage")){
			 String res = saveSecurityTokenDetails();	 
			 APP_LOG.debug("Authentication Error "+res);
			 if(res.contains(Constants.FAIL)){
				 return res;
			 }
		 }
		 /** Verify the Response whether it has Error Messages *****/
		 String errorMsg = apiVerifyErrorInJson();
		 if(!errorMsg.equals("")){
			 APP_LOG.debug("ErrorMessage in response "+errorMsg);
		//	 reportUtil.addKeyword("", "apiVerifyDataFromDb", Constants.PASS, "","",
			//			"", "", "TS-" , "TC-","Error","","","","", "");	
			 return Constants.PASS;  //As of now we are sending pass because in case of -ve test cases this errorMessage will be expected
		 }
		 /*******************************************************/
		 
		 if(!sJson.equals("")){
			 APP_LOG.debug("JSON :  PASS");
			   return Constants.PASS;
		 }else{
			 APP_LOG.debug("JSON :  FAIL");
			   return Constants.FAIL;
		 }
	}
	
	/**
	 * To compare 2 Json Structure
	 * @param args
	 * @return
	 */
	public String apiCompareStructureOfJson(Map<String, String> args){
		JsonParser parser = new JsonParser();
		String sourceJson = args.get(KeywordConstant.ELEMENT_INPUT_VALUE);
		
		JsonElement sourceJsonElement = parser.parse(sourceJson);
		JsonElement responseJsonElement = parser.parse(sJson);
		
		if(sourceJsonElement.equals(responseJsonElement)){
			return Constants.FAIL+"#"+responseJsonElement;
		}else{
			return Constants.FAIL+"#"+responseJsonElement;
		}
	}

	/**
	 * Main function to execute the API if we have request as JSON as a request
	 * @param args
	 * @return
	 */
	
	public String executeServiceJsonRequestData(Map<String, String> args){
		//Parse the dynamic data 
		args = parseRequestParameter(args);
		
		String serviceName =  args.get("EndPoint")+args.get("Resource");
		String reqParameter = args.get("Parameters");
		String method = args.get("Method");
		String header =  args.get("Header");
		
		String dynamicKey = runTimeValue; 
		String token = "";
		
		/**********Replace the authentication token ***********/
		 if(header.contains("<ACCESS_TOKEN>")){
			 token=getAccessToken(); 
			 header = header.replace("<ACCESS_TOKEN>", token);
		 }else if(header.contains("DYNAMIC_KEY")) {
			 header = header.replace("DYNAMIC_KEY", runTimeValue);
		 }
		 
		
		String sJson = jsonParser.makeHttpRequestJSonObjArray(serviceName, method, reqParameter, header);
		
		System.out.println(sJson);
		APP_LOG.debug("JSON : "+sJson);
			    
		jsonElement= new Gson().fromJson(sJson, JsonElement.class);
		setJsonElement(jsonElement); 
			 //  apiVerifyErrorInJson();
	    if(!sJson.equals("")){
		   return Constants.PASS+"# ";
	    }else{
		   return Constants.FAIL+"# ";
	    }
	}
	
	
	public String executeServiceJsonRequestDataToString(Map<String, String> args){
		//Parse the dynamic data 
		args = parseRequestParameter(args);
		
		String serviceName =  args.get("EndPoint")+args.get("Resource");
		String reqParameter = args.get("Parameters");
		String method = args.get("Method");
		String header =  args.get("Header");
		String token = "";
		
			
		/**********Replace the authentication token ***********/
		 if(header.contains("<ACCESS_TOKEN>")){
			 token=getAccessToken(); 
			 header = header.replace("<ACCESS_TOKEN>", token);
		 }
		 
		
		String sJson = jsonParser.makeHttpRequestJSonObjArrayToString(serviceName, method, reqParameter, header);
		
		System.out.println(sJson);
		APP_LOG.debug("JSON : "+sJson);
			    
		jsonElement= new Gson().fromJson(sJson, JsonElement.class);
		setJsonElement(jsonElement); 
			 //  apiVerifyErrorInJson();
	    if(!sJson.equals("")){
		   return Constants.PASS;//+"# ";
	    }else{
		   return Constants.FAIL;//+"# ";
	    }
	}
	/** 
	 * Call external API 
	 * As of now we are getting the header/param from test steps sheet 
	 * if any specific changes are require for calling 
	 * 
	 * @param args
	 * @return
	 */
	public String apiCallExternalApi(Map<String, String> args){
		 return executeServiceRequestData(args);
	}
	
	/***
	 * To verify the status code returned from the ApI
	 * @param argsMap
	 * @return
	 */
	public String apiVerifyStatusCode(Map<String, String> argsMap){
		
		String data = argsMap.get(KeywordConstant.ELEMENT_INPUT_VALUE);

		int statusCode = executeServiceRequestDataForJsonRequest(argsMap);
		
		if(data.equals(String.valueOf(statusCode))){
			return Constants.PASS;//+"#"+statusCode;
		}else{
			return Constants.FAIL;//+"#"+statusCode;
		}
	}
 
	
	/**
	 * To find the Error in Json response
	 * @return error message
	 */
	public String apiVerifyErrorInJson(){
		String errorMsg = "";
		try{
			
			/*if(getJsonElement().isJsonObject()){
				 JsonArray sJsonArray = getJsonElement().getAsJsonArray();
				   for (JsonElement jsonElementArr : sJsonArray) {
				    	JsonObject asJsonObject = jsonElementArr.getAsJsonObject();
				    	asJsonObject.getAsString();
				   }
			}*/
			errorMsg = getNodeValueFromJsonObject(getJsonElement(), "ErrorMessage");
		}catch(Exception e){
			errorMsg = "";
			APP_LOG.debug("Func apiVerifyErrorInJson: Exception in Error responce. Check logs.:"+e);
		}
		 return errorMsg;
	}
	
	 
/** Saving the SBWS authentication token details
 *  
 */
	
  public String saveSecurityTokenDetails(){
	  APP_LOG.debug("Func  saveSbwsTokenDetails ");
	  try{
		accessToken = getNodeValueFromJsonObject(getJsonElement(), "token");
		token_type = getNodeValueFromJsonObject(getJsonElement(), "token_type");
		expires_in = getNodeValueFromJsonObject(getJsonElement(), "expires_in");
		refresh_token = getNodeValueFromJsonObject(getJsonElement(), "refresh_token");
		
		setAccessToken(accessToken);
		setToken_type(token_type);
		setExpires_in(expires_in);
		setRefresh_token(refresh_token);
		
		return Constants.PASS+"# ";
	  }catch(Exception e){
		  String errorMsg = apiVerifyErrorInJson();
		  APP_LOG.debug("Func saveSbwsTokenDetails Error: "+errorMsg+"  ||  "+e);
		  return Constants.FAIL+"#"+errorMsg;
	  }
	 
  }
	
  public String apiSaveRunTimeValue(Map<String, String> argsMap) {
	  
	  return saveDomainId(argsMap.get(KeywordConstant.ELEMENT_LOCATOR), argsMap.get(KeywordConstant.ELEMENT_INPUT_VALUE));
	  
	  
  }
  
  public String saveDomainId(String tagName, String key){
	  APP_LOG.debug("Func  saveSbwsTokenDetails ");
	  try{
		String tagValue = getNodeValueFromJsonObject(getJsonElement(), tagName);
		runTimeValue = tagValue;
		return Constants.PASS+"# ";
	  }catch(Exception e){
		  String errorMsg = apiVerifyErrorInJson();
		  APP_LOG.debug("Func saveSbwsTokenDetails Error: "+errorMsg+"  ||  "+e);
		  return Constants.FAIL+"#"+errorMsg;
	  }
	 
  }
	
  
  /***
   * Call all the services
   * @param argsMap
   * @return
   * @throws JSONException
   */
   public String apiGetServiceRequestData(Map<String, String> argsMap) throws JSONException {
	  //To test the AuthenticateService API . 
	  /* if(getAccessToken().equals("") && !argsMap.get(KeywordConstant.ELEMENT_INPUT_VALUE).equals("AuthenticateService")){
		   apiGenerateAccessToken();	   
	   }*/
	   return executeServiceRequestData(argsMap);
   }
   
   /**
    * If we have Request in JSON format then we can use this keyword. We have different approach initially
    * but now Its a replica of apiGetServiceRequestData. Now many test cases are using this so we can not remove.
    * @param argsMap
    * @return
    * @throws JSONException
    */
   public String apiGetServiceJsonRequestData(Map<String, String> argsMap) throws JSONException {
	   
	   return executeServiceJsonRequestData(argsMap);
	}
	
   public String apiGetServiceJsonRequestDataToString(Map<String, String> argsMap) throws JSONException {
 		
		return executeServiceJsonRequestDataToString(argsMap);
	}
   
  /**  will remove after some time waiting for testing
   * Get Access Token for SBWS Mobile
   * @param argsMap
   * @return
   * @throws JSONException
   *

public String apiGetAccessToken(Map<String, String> argsMap) throws JSONException {
		  //To test the AuthenticateService API . 
		 //  if(getAccessToken().equals("") && !argsMap.get(KeywordConstant.ELEMENT_INPUT_VALUE).equals("AuthenticateService")){
			   apiGenerateAccessToken();	   
		  // }
		   return executeServiceRequestData(argsMap);
	}
/
 * 
 */

 
   /** 
 *To verify API node value
 * @param argsMap
 * @return
 */

public String apiVerifyNodeValueText(Map<String, String> argsMap){
	   
	   String apiData = " ";
	   String data = argsMap.get(KeywordConstant.ELEMENT_INPUT_VALUE);

	   if(getJsonElement().isJsonArray()){
		   apiData = getNodeElementValueArray(argsMap);
	   }else{
		   apiData = getNodeElementValueObj(argsMap);
	   }
	   actualDataPresentOnUi = apiData;
	   if(apiData.equals(data)){
		   return Constants.PASS;//+"#"+apiData;
	   }else{
		   collectFailureMessage("Expected value is not matching with Actual value.");
		   return Constants.FAIL;//+"#"+apiData;
	   }
}

/**
 * This function will verify the equality of count in the Json Array
 * @param argsMap
 * @return
 */
public String apiVerifyEqualityOfElemetCountInArray(Map<String, String> argsMap){
	   String  apiData = "";
	   int sizeOfArr = 0;
	   String data = argsMap.get(KeywordConstant.ELEMENT_INPUT_VALUE);
	   String parentNode = argsMap.get("ParentNode");
	   String elementToFind = argsMap.get("ElementId");
	   String recordNo = argsMap.get("RecordNo");
	   String[] nodeArr = parentNode.split("\\|");
	    
	   if(getJsonElement().isJsonArray()){
		   //TODO get the count of array json
		  // apiData = getNodeElementValueArray(argsMap);
	   }else{
		   try{
	 		   if(!getJsonElement().isJsonNull() &&  getJsonElement().isJsonObject()){
				   JsonElement jsonEle = getDataFromJsonObjectElement(getJsonElement(), nodeArr, 0);  
				   sizeOfArr = getArraySizeInsideJsonNode(jsonEle, elementToFind, 0);		
				}
			   apiData = String.valueOf(sizeOfArr);
		   }catch(Exception e){
			   APP_LOG.debug("Func apiVerifyEqualityOfElemetCountInArray : "+e);
		   }
		   
	   }
	      
	   if(apiData.equals(data)){
		   return Constants.PASS;//+"#"+apiData;
	   }else{
		   return Constants.FAIL;//+"#"+apiData;
	   }
	   
}


public String apiVerifyNodeValueTextNotExists(Map<String, String> argsMap){
	   
	   String apiData = " ";
	   String data = argsMap.get(KeywordConstant.ELEMENT_INPUT_VALUE);

	   if(getJsonElement().isJsonArray()){
		   apiData = getNodeElementValueArray(argsMap);
	   }else{
		   apiData = getNodeElementValueObj(argsMap);
	   }
	   actualDataPresentOnUi = apiData;
	   if(!apiData.equals(data)){
		   return Constants.PASS;//+"#"+apiData;
	   }else{
		   return Constants.FAIL;//+"#"+apiData;
	   }
}

public String apiGetServiceRequestDataErrorCase(Map<String, String> argsMap) throws JSONException {
	  
    String apiData = " " ;
	String data = argsMap.get(KeywordConstant.ELEMENT_INPUT_VALUE);
    String serviceName =  argsMap.get("EndPoint")+argsMap.get("Resource");
	String reqParameter = argsMap.get("Parameters");
	String method = argsMap.get("Method");
	String header =  argsMap.get("Header");
	String[] dataArr;
	dataArr = reqParameter.split("&");
	String key="";
	String value="";
	String token=getAccessToken(); 

	List<NameValuePair> params = new ArrayList<NameValuePair>();
	for(int i=0; i < dataArr.length ; i++){
	   	String[] keyValue = dataArr[i].split("=");
	   	 key = keyValue[0];
	   	 if(keyValue.length >1)
	   		 value = keyValue[1];
	   	if(key.equals("refresh_token")){
	   		value = getRefresh_token();
	   	}
	   	if(!key.equals("access_token")){
	   		params.add(new BasicNameValuePair(key, value));	
	   	}
	  } 
	
	 String sJson = jsonParser.makeHttpRequestArray(serviceName, method, params, token, header);
	      APP_LOG.debug("JSON : "+sJson);
	 	  sJson = sJson.replaceAll("(\\r|\\n)", "");    
		  if(sJson.equals(data))  
		  {
			  apiData = sJson ;
			  return Constants.PASS;//+"# "+apiData;
		  }else{
			   apiData = sJson ;
			   return Constants.FAIL;//+"# "+apiData;
		 }
}



   /**
    * To verify node value is not empty
    * @param argsMap
    * @return
    */

   public String apiVerifyNodeValueNotEmpty(Map<String, String> argsMap){
	   String apiData = "";

	   if(getJsonElement().isJsonArray()){
		   apiData =  getNodeElementValueArray(argsMap);
	   }else{
		   apiData =  getNodeElementValueObj(argsMap);
	   }
	   
	   if(!apiData.equals("")){
		   return Constants.PASS;//+"#"+apiData;
	   }else{
		   return Constants.FAIL;//+"#"+apiData;
	   }
	   
   }
   
   /***apiVerifyAttributeValueNotEmpty
   sJson= " {\"access_token\":\"eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJ1c2VybmFtZSI6InJiaXNodEBjZWJnbG9iYWwuY29tIiwibG9naW50b2tlbiI6ImxPMVNhcGhoTWdibGg5QTciLCJpc3MiOiIxMjcuMC4wLjEiLCJhdWQiOiJtb2JpbGVhcHAiLCJleHAiOjE0NTEzODEzODEsIm5iZiI6MTQ1MTM3Nzc4MX0.L5jX1bp8ROWQvdgpmaLNbMKz0pqjr18AfvbVO57gImX86_Sbz4VVq0npOJZSIJAiFJgbQAq5r15hxhavBRmWeo-bHpomtQGYfMGUFr8ZBPTFO_HBYt21BCU2JcxTYfNgv59j7ekpjak8eKsRfMHEuGOwcmZ4RK9pFZ-rUQGL9KfUHnFOP5zbhEq0SMUAaBOVNnNS8HAM8FYh74G2DqiudAsKyN7oICqXKJ-yAT4clWtWs8idOc5bzCZOazJOMQBMC8C9R11l7T4EoMQqlDQbXkmBQOviiJ-klRPA0d9m8B-P2lVXnif95pWU1aZFFgyOCd0tu6C3mDC50GVAKa3q8w\", "+
	    	  " \"token_type\": \"bearer\","+
	    	  " \"expires_in\": 3599,"+
	    	  " \"refresh_token\": \"2ac5e031-bff1-4cba-9e6f-8f28feeeb5b4\"}";
	    	  **/
   //TODO
   
     public String apiVerifyAttributeValueNotEmpty(Map<String, String> argsMap){
	   String apiData = "";

	   if(getJsonElement().isJsonObject()){
		   apiData =  getNodeValueFromJsonObject(getJsonElement(),argsMap.get("ElementId"));
	   } 
	   actualDataPresentOnUi=apiData;
	   if(!apiData.equals("")){
		   return Constants.PASS;//+"#"+apiData;
	   }else{
		   return Constants.FAIL;//+"#"+apiData;
	   }
	   
   }
   /***apiVerifyAttributeValue
   sJson= " {\"access_token\":\"eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJ1c2VybmFtZSI6InJiaXNodEBjZWJnbG9iYWwuY29tIiwibG9naW50b2tlbiI6ImxPMVNhcGhoTWdibGg5QTciLCJpc3MiOiIxMjcuMC4wLjEiLCJhdWQiOiJtb2JpbGVhcHAiLCJleHAiOjE0NTEzODEzODEsIm5iZiI6MTQ1MTM3Nzc4MX0.L5jX1bp8ROWQvdgpmaLNbMKz0pqjr18AfvbVO57gImX86_Sbz4VVq0npOJZSIJAiFJgbQAq5r15hxhavBRmWeo-bHpomtQGYfMGUFr8ZBPTFO_HBYt21BCU2JcxTYfNgv59j7ekpjak8eKsRfMHEuGOwcmZ4RK9pFZ-rUQGL9KfUHnFOP5zbhEq0SMUAaBOVNnNS8HAM8FYh74G2DqiudAsKyN7oICqXKJ-yAT4clWtWs8idOc5bzCZOazJOMQBMC8C9R11l7T4EoMQqlDQbXkmBQOviiJ-klRPA0d9m8B-P2lVXnif95pWU1aZFFgyOCd0tu6C3mDC50GVAKa3q8w\", "+
	    	  " \"token_type\": \"bearer\","+
	    	  " \"expires_in\": 3599,"+
	    	  " \"refresh_token\": \"2ac5e031-bff1-4cba-9e6f-8f28feeeb5b4\"}";
	    	  **/
   //TODO
   public String apiVerifyAttributeValue(Map<String, String> argsMap){
	   String apiData = " ";
	   String data = argsMap.get(KeywordConstant.ELEMENT_INPUT_VALUE);
	   if(getJsonElement().isJsonObject()){
		   apiData =  getNodeValueFromJsonObject(getJsonElement(),argsMap.get("ElementId"));
	   } 
	   
	   actualDataPresentOnUi = apiData;
	   if(apiData.toLowerCase().trim().equals(data.toLowerCase().trim())){
		   return Constants.PASS;//+"#"+apiData;
	   }else{
		   return Constants.FAIL;//+"#"+apiData;
	   }
	   
   }
   
   
   /**
    * To verify total no of record found in the Json
    * 
    * @param argsMap
    * @return
    */
  
   public String apiVerifyNumberOfRecordReturn(Map<String, String> argsMap){
	   int data = Integer.parseInt(argsMap.get(KeywordConstant.ELEMENT_INPUT_VALUE));
	   int apiRecSize = getNumberOfRecordReturn(argsMap);
	    if(data == apiRecSize){
		   return Constants.PASS;//+"#"+apiRecSize;
	   }else{
		   return Constants.FAIL;//+"#"+apiRecSize;
	   }	   
   }
   

   /**
    * To verify total no of record found in the Json
    * 
    * @param argsMap
    * @return
    */
   public int getNumberOfRecordReturn(Map<String, String> argsMap){
	   int apiRecSize = 0;
	   String parentNode = argsMap.get("ParentNode");

	   
	   if(getJsonElement().isJsonArray()){
		   JsonArray sJsonArray = jsonElement.getAsJsonArray();
		   apiRecSize = sJsonArray.size();
	   }else{
		   String[] nodeArr = parentNode.split("\\|");
		   if(!getJsonElement().isJsonNull() &&  getJsonElement().isJsonObject()){
			   JsonElement jsonEle = getDataFromJsonObjectElement(jsonElement, nodeArr, 0);
			   JsonArray sJsonArray = jsonEle.getAsJsonArray();
			   apiRecSize = sJsonArray.size();
		   }
		}
	   
	   return apiRecSize;
   }
   
   
   /**
    * It will verify count of Json array is not equal to zero
    * @param argsMap
    * @return
    */
   public String apiJsonVerifyNodeNotEmpty(Map<String, String> argsMap){
	   
	   int apiRecSize = getNumberOfRecordReturn(argsMap);
	   
	   if(apiRecSize > 0){
		   return Constants.PASS;//+"#"+apiRecSize;
	   }else{
		   return Constants.FAIL;//+"#"+apiRecSize;
	   }
   }
   
   /**
    * Verify saved value from xlsx
    * It is a reverse function of apiVerifyEqualityOfSavedValue. this will helps us if we want to 
    * use Data driven as well as non data drivver based test cases. used in SBWS Mobile.
    * @param argsMap
    * @return
    */
   
   public String apiVerifyEqualityOfSavedValueDataDriven(Map<String, String> argsMap){
 	   String data = argsMap.get(KeywordConstant.ELEMENT_INPUT_VALUE);
 	   String data1 = argsMap.get("data1");
	   String str = runTimeDataMap.get(data1);
	 
	   APP_LOG.debug("Func apiVerifySavedValue : Saved Value for "+data+"="+str+" || Xls Value="+data1);
	  actualDataPresentOnUi = str;
	  
	   if(str.equalsIgnoreCase(data)){
		   return Constants.PASS;//+"#"+str;
	   }else{
		   return Constants.FAIL;//+"#"+str;
	   }
    }
   
   /**
    * Verify saved value from xlsx
    * @param argsMap
    * @return
    */
   
   public String apiVerifyEqualityOfSavedValue(Map<String, String> argsMap){
 	   String data = argsMap.get(KeywordConstant.ELEMENT_INPUT_VALUE);
 	   String data1 = argsMap.get("data1");
	   String str = runTimeDataMap.get(data);
	 
	   APP_LOG.debug("Func apiVerifySavedValue : Saved Value for "+data+"="+str+" || Xls Value="+data1);
	  actualDataPresentOnUi = str;
	   if(str.equalsIgnoreCase(data1)){
		   return Constants.PASS;//+"#"+str;
	   }else{
		   return Constants.FAIL;//+"#"+str;
	   }
    }
   /**
    * To verify the No of record return from DB from JSON
    * @param argsMap
    * @return
    */
   public String apiVerifyDbNumberOfRecordReturn(Map<String, String> argsMap){
 	   String data = argsMap.get(KeywordConstant.ELEMENT_INPUT_VALUE);
	   String str = runTimeDataMap.get(data);
	   
	   argsMap.put(KeywordConstant.ELEMENT_INPUT_VALUE,str);
	   return apiVerifyNumberOfRecordReturn(argsMap);
   }

   /**
    * Get the data from DB and save in the valriables
    * @param argsMap
    * @return
    */
   public String apiGetAndSaveDataFromDB(Map<String, String> argsMap){
	   SQLDatabaseKeywords objSQLKeywords = new SQLDatabaseKeywords(APP_LOG);
	    
	   String data = argsMap.get(KeywordConstant.ELEMENT_INPUT_VALUE);
 	   
	   String sqlData = objSQLKeywords.sqlVerifyDataFromDb(argsMap);
	   runTimeDataMap.put(data, sqlData);
	   
	   return Constants.PASS+"#"+sqlData;
   }
   
   
   /**
    * To verify all the records found in the DB with the JSON response
    * @param argsMap
    * @return
    * @throws SQLException
    */
   public String apiVerifyDataFromDb(Map<String, String> argsMap) throws SQLException{
	   
	   String sql = argsMap.get("data1");
	   String data = argsMap.get(KeywordConstant.ELEMENT_INPUT_VALUE);

	   String schema = argsMap.get(KeywordConstant.ELEMENT_INPUT_VALUE);
	   String[] selQryArr = schema.split(",");
	   
	   SQLDatabaseKeywords objSQLKeywords = new SQLDatabaseKeywords(APP_LOG);
	   
	   Connection conn = objSQLKeywords.getOracleServerConnection(argsMap);
	   int j= 0;
	   String reportData="";
	    PreparedStatement stmt = conn.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		rs.next();
		String queryResult= rs.getObject(1).toString();
		actualDataPresentOnUi = queryResult;
		if(queryResult.equalsIgnoreCase(data)) {
			   return Constants.PASS;
		}else {
			   return Constants.FAIL;
		}
	
	/*	while(rs.next()){
			for(int i=0; i < selQryArr.length; i++ ){
				if(!selQryArr[i].equals("")){
					String selParam = selQryArr[i];
					String sqlParamVal = "";
					try{
						sqlParamVal = rs.getString(selParam);
					}catch(Exception e){
						APP_LOG.debug("Func apiVerifyDataFromDb selParam="+selParam+e);
						System.out.println(e);
					}
					argsMap.put("RecordNo", String.valueOf(j));
					argsMap.put("ElementId", selParam);
					
					String jsonString =  getNodeElementValueArray(argsMap);
					if(selParam.equals("subscribed") && sqlParamVal.equals("1")){
						sqlParamVal = "true";
					} 
					if(jsonString.equals(sqlParamVal)){
						reportUtil.addKeyword(selParam, "apiVerifyDataFromDb", Constants.PASS, sqlParamVal,"",
								"", "", "TS-"+j, "TC-","","","","",jsonString, selParam,"");	

						System.out.println("PASS :"+sqlParamVal+" :: jsonString="+jsonString);
					}else{
						System.out.println("FAIL :"+sqlParamVal+" :: jsonString="+jsonString);
						reportUtil.addKeyword(selParam, "apiVerifyDataFromDb", Constants.FAIL, sqlParamVal,"",
								"", "", "TS-"+j, "TC-","","","","",jsonString, selParam,"");	

					}
					
				}
			}
			j++;
		  }*/
		
   }
   

   /** 
	 *To compare the dates in response with current date
	 * @param argsMap
	 * @return
	 * @throws ParseException 
	 */

	public String apiCompareDates(Map<String, String> argsMap) throws ParseException{

		String apiData = " ";
		String data = argsMap.get(KeywordConstant.ELEMENT_INPUT_VALUE);
		List<String> li  = getListOfElements(argsMap);
		System.out.println("LIST : "+li);
		SimpleDateFormat sdf =  new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		Date currentDate = new Date();
		currentDate = sdf.parse(sdf.format(currentDate));
		Date responseDate ;
		for (int i = 0; i < li.size()-1; i++) {
			String d = li.get(1).replaceAll("\"","") ;
			d= d.replace("[", "");
			d= d.replace("]", "");
			//System.out.println(d);	
			responseDate = sdf.parse(d);
			System.out.println("Current Date : "+ responseDate);
			if(data.equals("Past")){
				if(currentDate.after(responseDate)){
					System.out.println("Pass");
				}else{
					return Constants.FAIL+"#"+apiData;
				}
			}	
			else if (data.equals("Upcoming")) {
				if(currentDate.before(responseDate)){
					System.out.println("Passh");
				}else{
					return Constants.FAIL+"#"+apiData;
				}	
			}
		} return Constants.PASS+"#"+apiData;
	}
	
	public List getListOfElements(Map<String, String> argsMap){

		String apiData = " ";
		String data = argsMap.get(KeywordConstant.ELEMENT_INPUT_VALUE);
		int recordNo = getNumberOfRecordReturn(argsMap);
		LinkedList<String> li  = new LinkedList<String>();
		for (int i = 0; i < recordNo; i++) {
			if(getJsonElement().isJsonArray()){
				apiData = getNodeElementValueArray(argsMap);
			}else{
				apiData = getNodeElementValueObj(argsMap);
			}
			li.add(apiData);
		}
		return li ;
	}	
	
   /**
    * if json is array and verifying single  value
    * @param args
    * @return
    * 
    * if json is array and dont have node then it will verify the api data and xls
    * 
    * [  {
	"eventenddate":["2015-12-17T17:00:00Z"],
	"timezone":["America/New_York"]}]
    */
   
   public String getNodeElementValueArray(Map<String, String> args){
	   
	   String elementToFind = args.get("ElementId");
	   int recordNo =  0;
	   
	   if(!args.get("RecordNo").equals("")){
	      recordNo = Integer.valueOf(args.get("RecordNo"));
	   }
	   
	   String apiData = getNodeValueFromJsonArray(elementToFind, recordNo);
	   
	   return apiData;
   }
   
   /**
    * It will get the array from root nodes
    * "{\"response\":{\"numFound\":2,\"start\":0,\"docs\":[    {\"description\":\"Use this"}]
    * 
    * 
    * will verify docs\":[    {\"description\":\"Use this"}
    * @param args
    * @return
    */
   public String getNodeElementValueObj(Map<String, String> args){
	   
	   String parentNode = args.get("ParentNode");
	   String elementToFind = args.get("ElementId");
	   String recordNo = args.get("RecordNo");
   
	   String apiData = getAttributeFromRootNode(parentNode, elementToFind, recordNo);
	   
	   return apiData;
   }
   
   
   
   
   /** 
    * It will verify the root node attribute data of API and xls 
    * @param args
    *    * 
    * response":{"numFound":2,"start":0,"
    * verify numFound":2,"start":0 
    */
   public String apiVerifyRootNodeAttribute(Map<String,String> args){
	   
	   String parentNode = args.get("ParentNode");
	   String elementToFind = args.get("ElementId");
	   String data = args.get(KeywordConstant.ELEMENT_INPUT_VALUE);
	   
	   String apiData = getAttributeValueOfRootNode(parentNode, elementToFind);
	   actualDataPresentOnUi = apiData;
	   if(apiData.equals(data)){
		   return Constants.PASS;//+"#"+apiData;
	   }else{
		   return Constants.FAIL;//+"#"+apiData;
	   }
   }
  
   /** 
    * 
    * @param args
    * Verify root node attribute value not empty.  
    * Example : 
    *  * response":{"numFound":2,"start":0,"
    * verify numFound":2,"start":0
    * numFound!=""
    * or 
    * Example : 
    * {"Version":"1.0","StatusCode":200,"Result":{"FirstName":"First","LastName":"Last"
    * 
    * FirstName!=""
    */
   public String apiVerifyRootNodeAttributeNotEmpty(Map<String,String> args){
	   
	   String parentNode = args.get("ParentNode");
	   String elementToFind = args.get("ElementId");
	   String data = args.get(KeywordConstant.ELEMENT_INPUT_VALUE);
	   
	   String apiData = getAttributeValueOfRootNode(parentNode, elementToFind);
	   actualDataPresentOnUi = apiData;
	   if(!apiData.equals("")){
		   return Constants.PASS;//+"#"+apiData;
	   }else{
		   return Constants.FAIL;//+"#"+apiData;
	   }
   }
   
   
   /** 
    * It will verify the root node attribute data of API and xls 
    * @param args
    * Example: 
    * {"numFound":2,"start":0,"}
    * verify numFound":2,"start":0
    */
   public String apiVerifyAttribute(Map<String,String> args){
	   
	   String elementToFind = args.get("ElementId");
	   String data = args.get(KeywordConstant.ELEMENT_INPUT_VALUE);
	   
	   String apiData = getNodeValueFromJsonObject(getJsonElement(), elementToFind);
	   
	   if(apiData.equals(data)){
		   return Constants.PASS+"#"+apiData;
	   }else{
		   return Constants.FAIL+"#"+apiData;
	   }
   }
   
   /** 
    * 
    * it will return the attribute
    * @param node
    */
   public String getAttributeValueOfRootNode(String parentNode, String elementToFind){
	   String[] nodeArr = parentNode.split("\\|");
	   String attributeValue ="" ;
	   if(!getJsonElement().isJsonNull() &&  getJsonElement().isJsonObject()){
		   JsonObject sJsonObject = jsonElement.getAsJsonObject();
		   sJsonObject = getDataFromJsonObject(sJsonObject, nodeArr, 0);
		   attributeValue = sJsonObject.get(elementToFind).getAsString();
		}
	   
	   return attributeValue;
   }
 /**
  * This function will return the attribute value from the json on the basis of node passed from the xlsx.
  * 
  * @param parentNode
  * @param elementToFind
  * @param recordNoXls  : if we have 10 record and want to get 3rd record then record no can be pass from xls
  * @return
  */
   public String getAttributeFromRootNode(String parentNode, String elementToFind , String recordNoXls){
	   String[] nodeArr = parentNode.split("\\|");
	   String attributeValue ="" ;
	   int recordNo = 0;
	   
	   if(recordNoXls != null && !recordNoXls.equals("")){
		      recordNo = Integer.valueOf(recordNoXls);
	   }
	   if(!getJsonElement().isJsonNull() &&  getJsonElement().isJsonObject()){
		   JsonElement jsonEle = getDataFromJsonObjectElement(getJsonElement(), nodeArr, 0);  
		   attributeValue = getNodeValueFromJsonArrayByJElement(jsonEle, elementToFind, recordNo);		
		}
	   
	   return attributeValue;
   }

   
   
   /**
    * Finding the attribuites from root node
    * @param sJsonObject
    * @param nodeArr
    * @param counterGDFJO
    * @return
    */
   public JsonObject getDataFromJsonObject(JsonObject sJsonObject, String[] nodeArr, int counterGDFJO){
	   for(int i = counterGDFJO; i < nodeArr.length; i++){
		  JsonElement sJsonElement = sJsonObject.get(nodeArr[counterGDFJO]);
		  if(sJsonElement.isJsonObject()){
				JsonObject sJsonObject1 = sJsonElement.getAsJsonObject();
				sJsonObject = getDataFromJsonObject(sJsonObject1, nodeArr, ++counterGDFJO);
				return sJsonObject;
		  }else if(sJsonElement.isJsonArray()){
			 	JsonObject asJsonObject = sJsonObject.getAsJsonObject();
				 return asJsonObject;
		  }else{
			  sJsonElement.getAsString();
		  		System.out.println(sJsonElement.getAsString());
		  		return sJsonObject;
		  }
	   }
	return sJsonObject;
  }	 
   
   /**
    * Get data from JSon element
    * @param sJsonElement
    * @param nodeArr
    * @param counterGDFJO
    * @return
    */
   //todo
   public JsonElement getDataFromJsonObjectElement(JsonElement sJsonElement, String[] nodeArr, int counterGDFJO){
	   
	   for(int i = counterGDFJO; i < nodeArr.length; i++){
		   JsonObject sJsonObject = sJsonElement.getAsJsonObject();
		   sJsonElement = sJsonObject.get(nodeArr[counterGDFJO]);
		  if(sJsonElement.isJsonObject()){
				sJsonElement = getDataFromJsonObjectElement(sJsonElement, nodeArr, ++counterGDFJO);
				return sJsonElement;
		  }else if(sJsonElement.isJsonArray()){
			  sJsonElement = sJsonObject.get(nodeArr[counterGDFJO]).getAsJsonArray();
			  	 return sJsonElement;
		  }else{
			  sJsonElement.getAsString();
		  		System.out.println(sJsonElement.getAsString());
		  		return sJsonElement;
		  }
	   }
	return sJsonElement;
  }
   
   /**
    * 
    * Return the attribute from Json object
    * 
    * 
    *  sJson= " {\"access_token\":\"eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJ1c2VybmFtZSI6InJiaXNodEBjZWJnbG9iYWwuY29tIiwibG9naW50b2tlbiI6ImxPMVNhcGhoTWdibGg5QTciLCJpc3MiOiIxMjcuMC4wLjEiLCJhdWQiOiJtb2JpbGVhcHAiLCJleHAiOjE0NTEzODEzODEsIm5iZiI6MTQ1MTM3Nzc4MX0.L5jX1bp8ROWQvdgpmaLNbMKz0pqjr18AfvbVO57gImX86_Sbz4VVq0npOJZSIJAiFJgbQAq5r15hxhavBRmWeo-bHpomtQGYfMGUFr8ZBPTFO_HBYt21BCU2JcxTYfNgv59j7ekpjak8eKsRfMHEuGOwcmZ4RK9pFZ-rUQGL9KfUHnFOP5zbhEq0SMUAaBOVNnNS8HAM8FYh74G2DqiudAsKyN7oICqXKJ-yAT4clWtWs8idOc5bzCZOazJOMQBMC8C9R11l7T4EoMQqlDQbXkmBQOviiJ-klRPA0d9m8B-P2lVXnif95pWU1aZFFgyOCd0tu6C3mDC50GVAKa3q8w\", "+
		    	  " \"token_type\": \"bearer\","+
		    	  " \"expires_in\": 3599,"+
		    	  " \"refresh_token\": \"2ac5e031-bff1-4cba-9e6f-8f28feeeb5b4\"}";

    * @param sJsonElement
    * @param nodeArr
    * @param counterGDFJO
    * @return
    */
		   
   public String getNodeValueFromJsonObject(JsonElement sJsonElement, String node){
 	   JsonObject sJsonObject = sJsonElement.getAsJsonObject();
	   sJsonElement = sJsonObject.get(node);
	   sJsonElement.getAsString();
	   System.out.println(sJsonElement.getAsString());
	  
	   return sJsonElement.getAsString();
  }
   
   /**
    * Get the node values from the Json Array
    * @param node
    * @param recordNo
    * @return
    */
   public String getNodeValueFromJsonArray(String node, int recordNo){
	   String apiValue = "";
	   apiValue =  getNodeValueFromJsonArrayByJElement(getJsonElement(), node, recordNo);
	   
	   return apiValue;
   }
   
   /**
    * Parsing the request parameter 
    * @param args
    * @return
    */
  public Map<String,String> parseRequestParameter(Map<String,String> args){
	  try{
		  String parameters = args.get("Parameters");
		  String resources = args.get("Resource");

		  String dynamicDataParam = args.get("DynamicDataParam");
		  String headerParam = args.get("Header");
		  
		  String param = "";
		  if(!dynamicDataParam.equals("")){
			  String[] parametersArr = dynamicDataParam.split(",");
			  for(int i=0; i < parametersArr.length; i++ ){
				  param = parametersArr[i];
				  if(runTimeDataMap.containsKey(param)){
					  parameters = parameters.replace("<"+param+">", runTimeDataMap.get(param));
				  }
			  }
		  }
		  args.put("Parameters", parameters);
		  
		  if(!dynamicDataParam.equals("")){
			  String[] parametersArr = dynamicDataParam.split(",");
			  for(int i=0; i < parametersArr.length; i++ ){
				  param = parametersArr[i];
				  if(runTimeDataMap.containsKey(param)){
					  resources = resources.replace("<"+param+">", runTimeDataMap.get(param));
				  }
			  }
			  args.put("Resource", resources);
		  }
		  
		  /************ Replace the Refresh Token *****************/
			 if(parameters.contains("<REFRESH_TOKEN>")){
				 String refToken=getRefresh_token(); 
				 APP_LOG.debug("REFRESH_TOKEN = "+refToken);
				 parameters = parameters.replace("<REFRESH_TOKEN>", refToken);
				 args.put("Parameters", parameters);
			 }
		  
	  }catch(Exception e){
		  APP_LOG.debug("Func parseRequestParameter "+e);
	  }
	  
	  return args;
  }
  
  /**
   * To clear the stored data 
   * @param args
   */
  
   public void apiClearStoredData(Map<String,String> args){
	   String data = args.get(KeywordConstant.ELEMENT_INPUT_VALUE);
	   if(runTimeDataMap.containsKey(data)){
		  runTimeDataMap.remove(data); 
	   }
   }
   
   /**
    *Save XLS data in memory (runTimeDataMap)
    * @param args 
    */
   
    public String apiSaveXlsData(Map<String,String> args){
 	   String data = args.get(KeywordConstant.ELEMENT_INPUT_VALUE);
 	   String dynamicDatParam = args.get("DynamicDataParam");
 	   System.out.println("Func apiSaveXlsData:"+dynamicDatParam+" || data="+data);
 	   if(!dynamicDatParam.equals("")){
 		  runTimeDataMap.put(dynamicDatParam,data); 
 	   }
 	   
 	  return Constants.PASS+"#";
    }
   
   
   /**
    * to get the multpile Json values 
    * @param jElement
    * @param node
    * @return
    */
   //TODO
   public String getMultipleNodeValueFromJsonArrayByJElement(JsonElement jElement, String node){
	   String apiValue = "";
	   JsonArray sJsonArray = jElement.getAsJsonArray();
	   for (JsonElement jsonElementArr : sJsonArray) {
	    	JsonObject asJsonObject = jsonElementArr.getAsJsonObject();
	    	apiValue = asJsonObject.get(node).getAsString();
	   }
	   
	   return apiValue;
   }
   
   /**
    * return the count of array inside the Json Response 
    *
    * In below sample Json Response it return count of elements array in UserEntitlement (in this case 3)
    * {"Version":"1.0","StatusCode":200,"Result":{"FirstName":"First","LastName":"Last","SubscriptionLink":"http://u=",
    * "UserEntitlement":[{"Role":"User","WebSiteId":"ADRF"},
    * {"Role":"User","WebSiteId":"REEB"},{"Role":"User","WebSiteId":"PMO"},
    * @param jElement
    * @param node
    * @param recordNo
    * @return
    */
   public int getArraySizeInsideJsonNode(JsonElement jElement, String node, int recordNo){
	 
	   try{
		   JsonArray sJsonArray = jElement.getAsJsonArray();
		   APP_LOG.debug("Func getArraySizeInsideJsonNode : "+sJsonArray.size());
		   return sJsonArray.size();
		    
	   }catch(Exception e){
		   APP_LOG.debug("Func getArraySizeInsideJsonNode "+e);
		   return 0;
	   }
  }
   
   
   /** 
    * Get the node value from array by using JElement
    * 
    * @param jElement
    * @param node
    * @param recordNo
    * @return
    */
   public String getNodeValueFromJsonArrayByJElement(JsonElement jElement, String node, int recordNo){
	   String apiValue = "";
	 
	   try{
		   if(jElement.isJsonArray()){
			   JsonArray sJsonArray = jElement.getAsJsonArray();
			   if(sJsonArray.size() >= recordNo){
				   JsonObject asJsonObject = sJsonArray.get(recordNo).getAsJsonObject();
				   if(asJsonObject.get(node).isJsonArray())
					   apiValue = asJsonObject.getAsJsonArray(node).toString();
				   else{
					   apiValue = asJsonObject.get(node).getAsString();
				   }
			   }else{//TODO
				  // return apiValue;
			   }
		   }else{
			   JsonObject sJsonObject = jElement.getAsJsonObject();
			   apiValue = sJsonObject.get(node).getAsString();
		   }
	   }catch(Exception e){
		   APP_LOG.debug("Func getNodeValueFromJsonArrayByJElement "+e);
		   return "";
	   }
	   return apiValue;
   }
   
   
   /**
    * Get multiple node values
    * 
    * @param node
    * @return
    */
   //TODO
   public String getMultipleNodeValueFromJsonArray(String node){
	   String apiValue = "";
	   JsonArray sJsonArray = jsonElement.getAsJsonArray();
	   for (JsonElement jsonElementArr : sJsonArray) {
	    	JsonObject asJsonObject = jsonElementArr.getAsJsonObject();
	    	apiValue = asJsonObject.get(node).getAsString();
	   }
	   
	   return apiValue;
   }

      
	public String getsJson() {
		return sJson;
	}


	public void setsJson(String sJson) {
		this.sJson = sJson;
	}


	public JSONObject getJsonObject() {
		return jsonObject;
	}


	public void setJsonObject(JSONObject jsonObject) {
		this.jsonObject = jsonObject;
	}



	public JsonElement getJsonElement() {
		return jsonElement;
	}



	public void setJsonElement(JsonElement jsonElement) {
		this.jsonElement = jsonElement;
	}

	public static String getAccessToken() {
		return accessToken;
	}

	public static void setAccessToken(String accessToken) {
		ApiKeywords.accessToken = accessToken;
	}

	public static String getToken_type() {
		return token_type;
	}

	public static void setToken_type(String token_type) {
		ApiKeywords.token_type = token_type;
	}

	public static String getExpires_in() {
		return expires_in;
	}

	public static void setExpires_in(String expires_in) {
		ApiKeywords.expires_in = expires_in;
	}

	public static String getRefresh_token() {
		return refresh_token;
	}

	public static void setRefresh_token(String refresh_token) {
		ApiKeywords.refresh_token = refresh_token;
	}
 

	public HttpResponse getHttpResponse() {
		return httpResponse;
	}

	public void setHttpResponse(HttpResponse httpResponse) {
		this.httpResponse = httpResponse;
	}
     
	

/*	   public String readDataFromXml(String node, String xlsData, String parentNode){
		   
		 try{ 
		    File fXmlFile = new File("D:\\RestSample.xml");//programId
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
		 	doc.getDocumentElement().normalize();
		 
		 	System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
		 	//TODO
		 		NodeList contentTypeList = doc.getElementsByTagName(node);
				for(int cTypeLstCount = 0; cTypeLstCount < contentTypeList.getLength(); cTypeLstCount++){
					String currentNodeValue = "";
					Node cTypeNode = contentTypeList.item(cTypeLstCount);
				    if(cTypeNode.getNodeType() == Node.ELEMENT_NODE){
				    	Element cTypeElement = (Element) cTypeNode;    	

				    	//Verify node value
				    	if(parentNode.equals("")){
			    			if(node.startsWith("ATTR")){
			    				String nodeAttr = node.split("\\|")[1];
			    				currentNodeValue = 	cTypeElement.getAttribute(nodeAttr);
			    			}else{
			    				currentNodeValue = 	cTypeElement.getElementsByTagName(node).item(0).getTextContent();
			    			}
			    			if(currentNodeValue.equalsIgnoreCase(xlsData)){
					    		return Constants.PASS;
					    	}
					    	
				    	}else{
					    	NodeList nList = cTypeElement.getElementsByTagName(parentNode);
			    			for (int temp = 0; temp < nList.getLength(); temp++) {
									Node nNode = nList.item(temp);
									System.out.println("\nCurrent Element :" + nNode.getNodeName());
				    				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				    					Element eElement = (Element) nNode;
				    					String xmlData = eElement.getElementsByTagName("AttachedFilePath").item(0).getTextContent();
				    					if(xmlData.equalsIgnoreCase(xlsData)){
				    						return Constants.PASS;
				    					}
				    					
				    				}
				    			  }
				    		}
				    }
				}     
			
		   }catch(Exception e){
			   
		   }
		  return "PASS";
	   }

	   public String readAttributeFromXml(String node, String xlsData, String parentNode){
		   
			 try{ 
			    File fXmlFile = new File("D:\\RestSample.xml");//programId
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.parse(fXmlFile);
			 	doc.getDocumentElement().normalize();
			 
			 	System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
			 	//TODO
			 		NodeList contentTypeList = doc.getElementsByTagName(node);
			 		for(int cTypeLstCount = 0; cTypeLstCount < contentTypeList.getLength(); cTypeLstCount++){
						String currentNodeValue = "";
						Node cTypeNode = contentTypeList.item(cTypeLstCount);
					    if(cTypeNode.getNodeType() == Node.ELEMENT_NODE){
					    	Element cTypeElement = (Element) cTypeNode;    	

					    	//Verify node value
					    	if(parentNode.equals("")){
				    			if(node.startsWith("ATTR")){
				    				String nodeAttr = node.split("\\|")[1];
				    				currentNodeValue = 	cTypeElement.getAttribute(nodeAttr);
				    			}else{
				    				currentNodeValue = 	cTypeElement.getElementsByTagName(node).item(0).getTextContent();
				    			}
				    			if(currentNodeValue.equalsIgnoreCase(xlsData)){
						    		return Constants.PASS;
						    	}
						    	
					    	}else{
						    	NodeList nList = cTypeElement.getElementsByTagName(parentNode);
				    			for (int temp = 0; temp < nList.getLength(); temp++) {
										Node nNode = nList.item(temp);
										System.out.println("\nCurrent Element :" + nNode.getNodeName());
					    				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					    					Element eElement = (Element) nNode;
					    					String xmlData = eElement.getElementsByTagName("AttachedFilePath").item(0).getTextContent();
					    					if(xmlData.equalsIgnoreCase(xlsData)){
					    						return Constants.PASS;
					    					}
					    					
					    				}
					    			  }
					    		}
					    }
					}     
				
			   }catch(Exception e){
				   
			   }
			  return "PASS";
		   }
*/
	
}
/**
 * 
 */
package com.autofusion.api;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.json.JSONObject;
import org.openqa.selenium.WebElement;

import com.autofusion.BaseClass;
import com.autofusion.constants.APIConstants;
import com.autofusion.util.ReadConfigXlsFiles;
import com.autofusion.util.ReportUtil;
import com.google.gson.JsonElement;
 
/**
 * @author nitin.singh
 *
 */
@SuppressWarnings("unused")
public class ApiBaseClass extends BaseClass{

	JSONParser jsonParser = new JSONParser();
	MakeHttpRequest makeHttpRequest= new MakeHttpRequest();
	protected static JSONObject jsonObject = null;
	protected static String  sJson = "";
	protected static HttpResponse httpResponse = null;
	public static Map<String,String> runTimeDataMap = new HashMap<String, String>();
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
	public static Map<String,String> apiConfigurationsXlsMap = new HashMap<String, String>();

	
	
	protected String accessToken = "";
	protected String refreshToken = "";
	protected String requestURL = "";
	

	public static String header = "X-APP-API_KEY="+APIConstants.HEADER_API_KEY+"&Accept="+
							APIConstants.HEADER_ACCEPT+"&Content-Type="+APIConstants.HEADER_CONTENT_TYPE;
	
	
	
	 public static void readEnvironmentConfigurationFile(){
		 	APP_LOG.debug("Reading Environment configuration file");
			ReadConfigXlsFiles objConfigXlsFiles = new ReadConfigXlsFiles();
		//	apiConfigurationsXlsMap = objConfigXlsFiles.readApiConfigurationsXls(projectPath, executionEnviroment.trim(), APP_LOG);
	 }
	
	
	public static String getHeader(String headerParam){
		
		header+=headerParam;
		
		return header;
		
	}
	
	protected void setRequestUrl(String version, String methodName){
		requestURL = APIConstants.API_END_POINT+"/"+version+"/"+methodName;
	}
	
	protected String getRequestUrl(){
		return requestURL;
	}
	
	
	public static String getCreateProfileParameter(){

		String param = "{\"payload\":{\"profile\":{\"customerName\":{\"firstName\":\"<FirstName>\",\"lastName\":\"<LastName>\"},\"password\":\"<Password>\",\"email\":\"<EmailId>\"}}}";
		
		return param;
	}
	
	public static String getTokenParameter(){

		String param = "{\"payload\":{\"profile\":{\"customerName\":{\"firstName\":\"<FirstName>\",\"lastName\":\"<LastName>\"},\"password\":\"<Password>\",\"email\":\"<EmailId>\"}}}";
		
		return param;
	}
	
	
	public void executeApi(String serviceName, String version, String param){
	//	runningComponentName = serviceName;
		
		setRequestUrl(version, serviceName);
		
		
		
		
		//String newMap = getElementAttribute(elementName);

	}
	
	
	/* public String apiGetServiceJsonRequestData(Map<String, String> argsMap) throws JSONException {
		   
		 //  return executeServiceJsonRequestData(argsMap);
	}*/
		
/*	public String apiGetServiceJsonRequestDataToString(Map<String, String> argsMap) throws JSONException {
	 		
			return executeServiceJsonRequestDataToString(argsMap);
	}*/
	  
	   
	/*   public String executeServiceJsonRequestDataToString(Map<String, String> args){
			//Parse the dynamic data 
			args = parseRequestParameter(args);
			
			String serviceName =  getRequestUrl();
			String reqParameter = args.get("Parameters");
			String method = args.get("Method");
			String header =  args.get("Header");
			String token = "";
			
			*//**********Replace the authentication token ***********//*
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
			   return Constants.PASS+"# ";
		    }else{
			   return Constants.FAIL+"# ";
		    }
		}
*/
	   
	   /**
	    * Parsing the request parameter 
	    * @param args
	    * @return
	    */
	  /*public Map<String,String> parseRequestParameter(Map<String,String> args){
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
			  
			  *//************ Replace the Refresh Token *****************//*
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
	  */
}

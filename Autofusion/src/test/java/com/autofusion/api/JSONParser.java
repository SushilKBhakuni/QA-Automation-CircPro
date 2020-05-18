package com.autofusion.api;
/**
 * @author nitin.singh
 */
 
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONObject;

import com.autofusion.BaseClass;
import com.autofusion.keywords.ApiKeywords;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
 

public class JSONParser extends BaseClass{
 
    static InputStream is = null;
    static JSONObject jObj = null;
    static JSONArray jArray = null;
    static String json = "";
    MakeHttpRequest makeHttpRequest= new MakeHttpRequest();
    // constructor
    public JSONParser() {
 
    }
 
    // function get json from url
    // by making HTTP POST or GET mehtod
    public JSONObject makeHttpRequest(String url, String method,
            List<NameValuePair> params) {
 
        // Making HTTP request
        try {
            // check for request method
            if(method == "POST"){
                HttpResponse httpResponse=makeHttpRequest.makeHttpRequest(url, "", method, new UrlEncodedFormEntity(params));                 
                HttpEntity httpEntity = httpResponse.getEntity();
                is = httpEntity.getContent();
            }else if(method == "GET"){
                String paramString = URLEncodedUtils.format(params, "utf-8");
                url += "?" + paramString; 
                HttpResponse httpResponse = makeHttpRequest.makeHttpRequest(url, "", method, new StringEntity(""));                
                is = httpResponse.getEntity().getContent();
            }           
 
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return convertToJson(is);
    }
	/**
	 * Convert input stream to JSON object 
	 * @param is
	 * @return JSONObject
	 */
	/**
	 * Convert input stream to JSON object 
	 * @param is
	 * @return JSONObject
	 */
    public JSONObject convertToJson (InputStream is){
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            json = sb.toString();
        } catch (Exception e) {
          //  Log.e("Buffer Error", "Error converting result " + e.toString());
        }
 
        // try parse the string to a JSON object
        try {
            ApiKeywords apiKeywords=new ApiKeywords(APP_LOG);            
            apiKeywords.setsJson(json);
            apiKeywords.setJsonElement(new Gson().fromJson(json, JsonElement.class));
        	jObj = new JSONObject(json);
        	//set json element
   		 	
        } catch (Exception e) {
			System.out.println("INFO:There were no json object , json object will hold \"null\"" );
			System.out.println("INFO:Try convert to array");
			try {
				jArray=new JSONArray(json);
				jObj=jArray.toJSONObject(jArray);
		    } catch (Exception e1) {			
			
				System.out.println("INFO:cannot convert the array to json");
			}
			//APP_LOGS.debug("Failure in Api execution or json parsing" + e);

        }
 
        // return JSON String
        return jObj;
    } 
    /**
	 * Convert input stream to JSON String 
	 * @param is
	 * @return JSONObject
	 */
    public String convertToString (InputStream is){
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            json = sb.toString();
        } catch (Exception e) {
        	 //  Log.e("Buffer Error", "Error converting result " + e.toString());
        }
        // return JSON String
        return json;
    }
 
    /**
     * Get the API response. Request is in key value pair
     * @param url
     * @param method
     * @param params
     * @param access_token
     * @param header
     * @return
     */
    
    public String makeHttpRequestArray(String url, String method,
            List<NameValuePair> params, String access_token, String header) {
     	
        try {
             // check for request method        	
            if(method.equals("POST")){
                HttpResponse httpResponse=makeHttpRequest.makeHttpRequest("http://172.18.14.132:8081/MobileID/manage/v1/domain", header, method, new UrlEncodedFormEntity(params));
                HttpEntity httpEntity = httpResponse.getEntity();
                is = httpEntity.getContent();
                
 
            }else if(method.equals("GET")){               
               HttpResponse httpResponse = makeHttpRequest.makeHttpRequest(url, header, method, new StringEntity(""));               
               is = httpResponse.getEntity().getContent();
            }else if(method.equals("PATCH")){
                HttpResponse httpResponse = makeHttpRequest.makeHttpRequest(url, header, method, new StringEntity(""));
                is = httpResponse.getEntity().getContent();
            }            
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return convertToString(is);
    }
    
    /**
     * It will  return the status code 
     * @param url
     * @param method
     * @param params
     * @return
     */
    public int makeHttpRequestJSonObjToGetStatusCode(String url, String method,   String params, String header) {
    	 
    	int statusCode = 0;
    	if(header.equals("")){
    		//header ="Content-Type=[application/json]"; //This is temporary to make existing work. must be passed from input.  
    	}
    	StringEntity stringEntity=new StringEntity(params,StandardCharsets.UTF_8);
    	stringEntity.setContentType("application/json;charset=UTF-8");
        try {
        	if(method.equalsIgnoreCase("PATCH")){	
	            HttpResponse httpResponse = makeHttpRequest.makeHttpRequest(url, header, method, new StringEntity(params));	            
	            is = httpResponse.getEntity().getContent();        	
	            statusCode = httpResponse.getStatusLine().getStatusCode();
        	}else if(method.equalsIgnoreCase("GET")){
	            HttpResponse httpResponse = makeHttpRequest.makeHttpRequest(url, header, method, new StringEntity(params));	            
	            is = httpResponse.getEntity().getContent();        	
	            statusCode = httpResponse.getStatusLine().getStatusCode();        		
        	}else if(method.equalsIgnoreCase("POST")){
	            HttpResponse httpResponse = makeHttpRequest.makeHttpRequest("http://172.18.14.132:8081/MobileID/manage/v1/domain", header, method, stringEntity);	            
	            is = httpResponse.getEntity().getContent();        	
	            statusCode = httpResponse.getStatusLine().getStatusCode();
        	}
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  statusCode;
     }
    
    /**
     * To hit the API with request as in JSon format
     * @param url
     * @param method
     * @param params
     * @param header
     * @return
     */
    public String makeHttpRequestJSonObjArray(String url, String method,   String params, String header) {
    //	String header ="Content-Type=[application/json]"; //This is temporary to make existing work. must be passed from input.
    	StringEntity stringEntity=new StringEntity(params,StandardCharsets.UTF_8);
    	stringEntity.setContentType("application/json");
    	try {
        	if(method.equalsIgnoreCase("PATCH")){	

                HttpResponse httpResponse = makeHttpRequest.makeHttpRequest(url, header, method, new StringEntity(params));	            
	            is = httpResponse.getEntity().getContent();        	
        	}else if(method.equalsIgnoreCase("GET")){

	         	HttpResponse httpResponse = makeHttpRequest.makeHttpRequest(url, header, method, new StringEntity(params));	            
	            is = httpResponse.getEntity().getContent();        	                     		
        	}else if(method.equalsIgnoreCase("POST")){
        		HttpResponse httpResponse = makeHttpRequest.makeHttpRequest(url, header, method, stringEntity);	            
        		is = httpResponse.getEntity().getContent();        	                
        	}
         } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

      return convertToJson(is).toString();
 
     }
    
    public String makeHttpRequestJSonObjArrayToString(String url, String method,   String params, String header) {
    //	String header ="Content-Type=[application/json]"; //This is temporary to make existing work. must be passed from input.
    	StringEntity stringEntity=new StringEntity(params,StandardCharsets.UTF_8);
    	stringEntity.setContentType("application/json");
    	try {
        	if(method.equalsIgnoreCase("PATCH")){	

                HttpResponse httpResponse = makeHttpRequest.makeHttpRequest(url, header, method, new StringEntity(params));	            
	            is = httpResponse.getEntity().getContent();        	
        	}else if(method.equalsIgnoreCase("GET")){

	         	HttpResponse httpResponse = makeHttpRequest.makeHttpRequest(url, header, method, new StringEntity(params));	            
	            is = httpResponse.getEntity().getContent();        	                     		
        	}else if(method.equalsIgnoreCase("POST")){
        		HttpResponse httpResponse = makeHttpRequest.makeHttpRequest(url, header, method, stringEntity);	            
        		is = httpResponse.getEntity().getContent();        	                
        	}
         } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

      return convertToString(is);
 
     }
    public HttpResponse makeHttpRequestJSonObjGetResponse(String url, String method,   String params) {
    	HttpResponse httpResponse = null;
    	String header ="Content-Type=application/json"; 
        try {
        	StringEntity stringEntity=new StringEntity(params,StandardCharsets.UTF_8);
        	stringEntity.setContentType("application/json");
        	if(method.equalsIgnoreCase("PATCH")){	
	         	httpResponse = makeHttpRequest.makeHttpRequest(url, header, method, stringEntity);	            
        	}else if(method.equalsIgnoreCase("GET")){
        		httpResponse = makeHttpRequest.makeHttpRequest(url, header, method, stringEntity);	                         
        		
        	}else if(method.equalsIgnoreCase("POST")){
        		httpResponse = makeHttpRequest.makeHttpRequest(url, header, method, stringEntity);	 
        	}
         } catch (Exception e) {
            e.printStackTrace();
        }              
        return httpResponse; 
     }
       
     
}

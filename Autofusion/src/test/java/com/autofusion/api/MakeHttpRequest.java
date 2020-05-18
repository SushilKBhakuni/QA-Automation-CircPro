package com.autofusion.api;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
  
public class MakeHttpRequest {
	/**
	 * makeHttpRequest- Only place where HTTP Request is made ; set the entity outside of this method and pass it to this
	 * @param url
	 * @param httpMethod
	 * @param httpEntity
	 * @param MaxAttempts
	 * @param retryIntervalInSecs
	 * @return
	 */
	public HttpResponse makeHttpRequest(String url,String header, String httpMethod, HttpEntity entity){

		HttpResponse httpResponse = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
	        if(httpMethod.equals("POST")){
	            // request method is POST 
	            HttpPost httpPost = new HttpPost(url);
	            httpPost.setEntity(entity);
	            //Iterate and set headers
	            if(!header.equals("")){
	            	String[] headerArr = header.split("&");
	            	for(int i=0; i < headerArr.length; i++){
	            		String[] headerAttr = headerArr[i].split("=");
	            		httpPost.setHeader(headerAttr[0], headerAttr[1]);
	            	}
	            }            
	           
	          //  httpPost.addHeader("Accept", "*/*");//accept all
	            httpResponse = httpClient.execute(httpPost);
	        }else if(httpMethod.equals("GET")){
	            // request method is GET
	           HttpGet httpGet = new HttpGet(url);
	           
	           if(header!=null && !header.equals("")){
		           	String[] headerArr = header.split("&");
		           	for(int i=0; i < headerArr.length; i++){
		           		String[] headerAttr = headerArr[i].split("=");
		           		httpGet.setHeader(headerAttr[0], headerAttr[1]);
		           }     
	           }
          
	           httpGet.addHeader("Accept", "*/*");//accept all
	           httpResponse = httpClient.execute(httpGet);
	        }else if(httpMethod.equals("PATCH")){
	            // request method is PATCH
	            HttpPatch httpPatch = new HttpPatch(url);
	            //Iterate and set headers
	            if(!header.equals("")){
	            	String[] headerArr = header.split("&");
	            	for(int i=0; i < headerArr.length; i++){
	            		String[] headerAttr = headerArr[i].split("=");
	            		httpPatch.setHeader(headerAttr[0], headerAttr[1]);
	            	}
	            }     
	            httpPatch.setEntity(entity);
	            httpPatch.addHeader("Accept", "*/*");//accept all
	            httpResponse = httpClient.execute(httpPatch);
	        }
	        else if(httpMethod.equals("PUT")){
	            // request method is PATCH
	            HttpPut httpPut = new HttpPut(url);
	            //Iterate and set headers
	            if(!header.equals("")){
	            	String[] headerArr = header.split("&");
	            	for(int i=0; i < headerArr.length; i++){
	            		String[] headerAttr = headerArr[i].split("=");
	            		httpPut.setHeader(headerAttr[0], headerAttr[1]);
	            	}
	            }     
	            httpPut.setEntity(entity);
	            httpPut.addHeader("Accept", "*/*");//accept all
	            httpResponse = httpClient.execute(httpPut);
	        }	        
	        else if(httpMethod.equals("DELETE")){
	            // request method is Delete
	        	HttpDeleteWithBody httpDeleteWithBody = new HttpDeleteWithBody(url);
	            if(!header.equals("")){
	            	String[] headerArr = header.split("&");
	            	for(int i=0; i < headerArr.length; i++){
	            		String[] headerAttr = headerArr[i].split("=");
	            		httpDeleteWithBody.setHeader(headerAttr[0], headerAttr[1]);
	            	}
	            }
	            httpDeleteWithBody.setEntity(entity);
	            httpDeleteWithBody.addHeader("Accept", "*/*");//accept all
	            httpResponse = httpClient.execute(httpDeleteWithBody);
	        }

	        return httpResponse;
	               
	
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Exception occured: " + e );
			return httpResponse;
		}
 	
	}
}

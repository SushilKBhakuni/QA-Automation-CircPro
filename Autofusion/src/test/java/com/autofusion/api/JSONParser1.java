package com.autofusion.api;
 
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
 
@SuppressWarnings({"deprecation","unused","resource"})

public class JSONParser1 {
 
    static InputStream is = null;
    static JSONObject jObj = null;
    static String json = "";
 
    // constructor
    public JSONParser1() {
 
    }
 
    // function get json from url
    // by making HTTP POST or GET mehtod
    @SuppressWarnings({"resource","all"})
	public JSONObject makeHttpRequest(String url, String method, List<NameValuePair> params) throws JSONException {
 
        // Making HTTP request
        try { 
            // check for request method
            if(method == "POST"){
                // request method is POST
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(url);
                httpPost.setEntity(new UrlEncodedFormEntity(params));
                
                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                is = httpEntity.getContent();
                if (httpEntity != null) {
                    String resultString = EntityUtils.toString(httpEntity); 
                    // parsing JSON and convert String to JSON Object
                     jObj = new JSONObject(resultString); 
                } else{
}
            }else if(method == "GET"){
                // request method is GET
                DefaultHttpClient httpClient = new DefaultHttpClient();
                //String paramString = URLEncodedUtils.format(params, "utf-8");
                //url += "?" + paramString;
                
                HttpGet httpGet = new HttpGet(url);
                httpGet.setHeader(HttpHeaders.ACCEPT, "application/json");
                httpGet.setHeader(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded");
                HttpResponse httpResponse = httpClient.execute(httpGet);
                HttpEntity httpEntity = httpResponse.getEntity();
                is = httpEntity.getContent();
                
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
 
        return jObj;
       
        /*
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
	 */
	        // try parse the string to a JSON object
	 /*       try {
	            jObj = new JSONObject(json);
	        } catch (JSONException e) {
	           // Log.e("JSON Parser", "Error parsing data " + e.toString());
	        }
	 
     */   // return JSON String
     //   return jObj;
 
    }
    
    public String makeHttpRequestArray(String url, String method,
            List<NameValuePair> params) {
 
        // Making HTTP request
        try {
 
            // check for request method
            if(method == "POST"){
                // request method is POST
                // defaultHttpClient
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(url);
                httpPost.addHeader("content-type", "application/json");
                httpPost.setEntity(new UrlEncodedFormEntity(params));
               // httpPost.setHeader(HttpHeaders.ACCEPT, "application/json");
           //     httpPost.setHeader(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded");
                
                //Content-Type: application/json; charset=UTF-8
                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                is = httpEntity.getContent();
                //String paramString = URLEncodedUtils.format(params, "utf-8");
                /*String paramString = URLEncodedUtils.format(params, "utf-8");
                url += "?" + paramString;
                HttpPost httpGet = new HttpPost(url);
 
                HttpResponse httpResponse = httpClient.execute(httpGet);
                HttpEntity httpEntity = httpResponse.getEntity();
                is = httpEntity.getContent();*/
 
 
            }else if(method == "GET"){
                // request method is GET
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet(url);
                httpGet.setHeader(HttpHeaders.ACCEPT, "application/json");
                HttpResponse httpResponse = httpClient.execute(httpGet);
                HttpEntity httpEntity = httpResponse.getEntity();
                is = httpEntity.getContent();
            }           
 
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
 
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
           // Log.e("Buffer Error", "Error converting result " + e.toString());
        }
 
//        // try parse the string to a JSON object
//        try {
//            jObj = new JSONObject(json);
//        } catch (JSONException e) {
//            Log.e("JSON Parser", "Error parsing data " + e.toString());
//        }
 
        // return JSON String
        return json;
 
    }
}

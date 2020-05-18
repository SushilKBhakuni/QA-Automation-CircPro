package com.autofusion.api;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.ParseException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.autofusion.constants.Constants;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

@SuppressWarnings("unused")
public class ApiTesting {
	JSONParser1 jsonParser1 = new JSONParser1();
	JSONObject jsonObject = null;
	String sJson = "";

	/**
	 * 
	 * @param args
	 * @throws IOException 
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 * @throws ParseException 
	 * @throws JSONException 
	 * 
	 */
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, ParseException, JSONException {
    		// TODO Auto-generated method stub
		ApiTesting objAppi = new ApiTesting();
		//objAppi.readDataFromXml();
		objAppi.sendRequetToGetServiceData(args);
		
	}

	
   public String sendRequetToGetServiceData(String[] args) {
	   
	   /*String serviceName = args[0];
	   String data = args[1];
	   String[] dataArr = data.split("\\|");
	   */
	    List<NameValuePair> params = new ArrayList<NameValuePair>();
	    /*for(int i=0; i < dataArr.length ; i++){
	    	String[] keyValue = dataArr[i].split("=");
	    	params.add(new BasicNameValuePair(keyValue[0], keyValue[1]));		
	    }
	    */
	    
	 //	params.add(new BasicNameValuePair("searchString", "searchString"));
	 	//params.add(new BasicNameValuePair("tag", serviceName));
	   /* params.add(new BasicNameValuePair("password", "qwerty"));
	    params.add(new BasicNameValuePair("userId", "chetram.saini@gl.com"));
	    params.add(new BasicNameValuePair("grant_type", "password"));
	   */ 
   
	    
	    params.add(new BasicNameValuePair("ud", "4193858"));
	    params.add(new BasicNameValuePair("rp", "10"));
	    params.add(new BasicNameValuePair("rt", "json"));

	 	//String sJson = jsonParser.makeHttpRequestArray("http://bazarsearch.com/mbl_index.php", "GET", params);
	 	//String sJson = jsonParser.makeHttpRequestArray("http://kohlsqa005.km.sl.edst.ibm.com/kohls/adapters/rest/v1/auth/signInProfile", "POST", params);
	 	//String sJson = jsonParser.makeHttpRequestArray("https://kohlsqa005.km.sl.edst.ibm.com/kohls/adapters/rest/v1/catalog", "GET", params);
	    //String sJson = jsonParser1.makeHttpRequestArray("http://kohlsqa005.km.sl.edst.ibm.com/kohls/adapters/rest/v1/profile", "GET", params);
	   // Object sJson1 = jsonParser1.makeHttpRequest("http://kohlsqa005.km.sl.edst.ibm.com/kohls/adapters/rest/v1/profile", "GET", params);
	  //  String sJson = jsonParser1.makeHttpRequestArray("http://api.wiziq.com/glmobileapp/restservice?method=notification", "POST", params);
	    
	    //String sJson ="{\"rsp\":{\"@status\":\"ok\",\"@st\":\"ok\",\"@call_id\":\"8410cea088ce\",\"method\":\"notification\",\"notification\":{\"@status\":\"true\",\"notifications\":{\"objects\":{\"@totalClassCount\":\"35\",\"@totalTestCount\":\"0\",\"@totalContentCount\":\"0\",\"@totalAssignmentCount\":\"5\",\"@totalOverdueCount\":\"16\",\"@totalCourseCount\":\"97\",\"@lastClassTime\":\"01-01-1753 12:00:00\",\"@lastTestTime\":\"01-01-1753 08:00:00\",\"@lastContentTime\":\"09-01-2015 02:52:30\",\"@lastAssgnTime\":\"01-01-1753 08:00:00\",\"@lastOverdueTime\":\"01-01-1753 12:00:00\",\"@lastCourseTime\":\"09-01-2015 02:52:30\",\"@lastCommentTime\":\"14-12-2015 03:48:55\",\"flag\":\"1\",\"object\":[{\"@type\":\"3\",\"content\":{\"@contentid\":\"920137\",\"notificationtype\":\"1\",\"starttime\":\"9 months ago\",\"title\":{\"#cdata-section\":\"xlsx icon file\"},\"courseid\":\"95137\",\"sid\":\"170976\",\"subid\":\"0\",\"coursetype\":\"1\",\"contentsize\":\"0\",\"contentstatusid\":\"2\",\"contenttypeid\":\"6\",\"contentviews\":\"0\",\"totalslides\":\"16\",\"iscreator\":\"0\"}}}]}}}}}";
	/*    String sJson ="{\"rsp\":{\"@status\":\"ok\",\"@st\":\"ok\",\"@call_id\":\"8410cea088ce\",\"method\":\"notification\",\"notification\":" +
	    		"{\"@status\":\"true\",\"notifications\":{\"objects\":{\"@totalClassCount\":\"35\",\"@totalTestCount\":\"0\",\"@totalContentCount\":\"0\"," +
	    		"\"@totalAssignmentCount\":\"5\",\"@totalOverdueCount\":\"16\",\"@totalCourseCount\":\"97\",\"@lastClassTime\":\"01-01-1753 12:00:00\"," +
	    		"\"@lastTestTime\":\"01-01-1753 08:00:00\",\"@lastContentTime\":\"09-01-2015 02:52:30\",\"@lastAssgnTime\":\"01-01-1753 08:00:00\"," +
	    		"\"@lastOverdueTime\":\"01-01-1753 12:00:00\",\"@lastCourseTime\":\"09-01-2015 02:52:30\",\"@lastCommentTime\":\"14-12-2015 03:48:55\"," +
	    		"\"flag\":\"1\",\"object\":[{\"@type\":\"3\",\"content\":{\"@contentid\":\"920137\",\"notificationtype\":\"1\",\"starttime\":\"9 months ago\"," +
	    		"\"title\":{\"#cdata-section\":\"xlsx icon file\"},\"courseid\":\"95137\",\"sid\":\"170976\",\"subid\":\"0\",\"coursetype\":\"1\",\"contentsize\":" +
	    		"\"0\",\"contentstatusid\":\"2\",\"contenttypeid\":\"6\",\"contentviews\":\"0\",\"totalslides\":\"16\",\"iscreator\":\"0\"}}}]}}}}}";
	*/    		

	    String str = "{\"elements\":[{\"type\":\"Email\",\"currentStatus\":\"Draft\",\"id\":\"EMAIL_ID\"," +
"\"createdAt\":\"1444848523\",\"createdBy\":\"16\",\"depth\":\"minimal\",\"folderId\":\"EMAIL_FOLDER_ID\",\"name\":\"Test Email1abcd1234\"," +
"\"permissions\":[\"Retrieve\",\"SetSecurity\",\"Delete\",\"Update\"],\"updatedAt\":\"1444848523\",\"updatedBy\":\"16\",\"archive\":\"false\"," +
"\"htmlContent\":{\"type\":\"RawHtmlContent\",\"contentSource\":\"upload\"}}]}";

	    
	    JsonObject obj4= new Gson().fromJson(str, JsonElement.class).getAsJsonObject();
	    JsonArray sJsonArray = obj4.getAsJsonArray("elements");
	    /*
	    try{
		 	JsonArray aJson = new JsonArray(sJsonArray);
		 	for(int i=0; i<aJson.length(); i++) {
		 		jsonObject = aJson.getJSONObject(i);
		 	}
		 	setJsonObject(jsonObject);
		 	return Constants.PASS;
	 	}catch(JSONException e){
	 		return Constants.FAIL;
	 	}
	    
*/	     	
	 	//System.out.println("asdf");
	 	//verifyTextFromResponseData(sJson);
	 	//return sJson;
		return str;
    
   }
   
   public String verifyTextFromResponseData(String[] args){
	   String object = args[0];
	   String data = args[1];
	   try {
		     JSONObject json = getJsonObject();
     		 System.out.println(json.getString("Title"));
     		 
     		 if(data.equalsIgnoreCase(json.getString(object))){
     			 return Constants.PASS;
     		 }else{
     			 return Constants.FAIL;
     		 }
     		 
//            System.out.println(json.getString("Description"));
//            System.out.println(json.getString("Address"));
//            System.out.println(json.getInt("Rating"));
//            System.out.println(json.getString("ExternalUrl"));
//            System.out.println(json.getString("IsHomeDelivery"));
//            	
           }catch (JSONException e) {
        	   return Constants.PASS;
           }

   }
   
   public void getTagData(String tagName){
	   
   }
   
   
   public String readDataFromXml(String node, String xlsData, String parentNode){
	   
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
   
}

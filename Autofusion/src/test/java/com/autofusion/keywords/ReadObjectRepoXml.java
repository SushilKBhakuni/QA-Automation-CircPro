package com.autofusion.keywords;
/**
 * @author nitin.singh
 */
import java.io.File;
import java.util.concurrent.ConcurrentHashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.autofusion.BaseClass;
import com.autofusion.constants.Constants;

public class ReadObjectRepoXml extends BaseClass {

	/**
	 * 
	 * @param basePath
	 *            = Base path of xml
	 * @param coponentName
	 *            = File Name
	 * @param elementName
	 *            = Variable Name
	 * @param APP_LOG
	 *            = logger
	 * @return id, name , xpath, css , classname
	 */
	public ConcurrentHashMap<String, String> getElementAttribute(String elementName) {

		String elementValue = "";
		ConcurrentHashMap<String, String> detailMap = new ConcurrentHashMap<String, String>();
		try {
			APP_LOG.debug("XML  reading start || getElement : elementName = " + elementName + " runningComponentName="
					+ getRunningComponentName());
			
			String filePath = "";
			
			if (device.equalsIgnoreCase("mobile")) {
				filePath = projectPath + "//" + device + "//" + browser + "Or//";
			} else {
				filePath = projectPath + "//" + device + "//";
			}
			String tempFilePath = filePath;
			if (!getRunningComponentName().equals("")) {
				filePath = filePath + getRunningComponentName() + ".xml";
			} else {
				filePath = filePath + Constants.OR + ".xml";
			}
			DocumentBuilderFactory dbFactory = null;
			DocumentBuilder dBuilder = null;
			/********** s ************/
			File fXmlFile = null;
			Document doc = null;
			try {
				fXmlFile = new File(filePath);
				if(!fXmlFile.exists()) {
					fXmlFile = new File(tempFilePath + Constants.OR + ".xml");	
				}
				dbFactory = DocumentBuilderFactory.newInstance();
				dBuilder = dbFactory.newDocumentBuilder();
				doc = dBuilder.parse(fXmlFile);
				doc.getDocumentElement().normalize();
			} catch (Exception e) {
				
				APP_LOG.debug(""+e);
			}
			
			APP_LOG.debug(" Root element  :" + doc.getDocumentElement().getNodeName());

			NodeList nList = doc.getElementsByTagName(elementName);// main node

			APP_LOG.debug(" element :" + elementName + " = " + nList.getLength());

			if (nList.getLength() == 0 && !getRunningComponentName().equals("")) {
				filePath = tempFilePath + Constants.OR + ".xml";

				fXmlFile = new File(filePath);
				dbFactory = DocumentBuilderFactory.newInstance();
				dBuilder = dbFactory.newDocumentBuilder();
				doc = dBuilder.parse(fXmlFile);
				doc.getDocumentElement().normalize();

				APP_LOG.debug(" Re Try with OR.XML : Root element :" + doc.getDocumentElement().getNodeName());
				APP_LOG.debug(" Root element :" + doc.getDocumentElement().getNodeName());

			}
			nList = doc.getElementsByTagName(elementName);// main node
			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);
				System.out.println("\nCurrent Element :" + nNode.getNodeName());

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;
					if (eElement.getElementsByTagName("id").getLength() == 1) {
						elementValue = eElement.getElementsByTagName("id").item(0).getTextContent();
						detailMap.put("id", elementValue);
					} else if (eElement.getElementsByTagName("name").getLength() == 1) {
						elementValue = eElement.getElementsByTagName("name").item(0).getTextContent();
						detailMap.put("name", elementValue);
					} else if (eElement.getElementsByTagName("xpath").getLength() == 1) {
						elementValue = eElement.getElementsByTagName("xpath").item(0).getTextContent();
						detailMap.put("xpath", elementValue);
					} else if (eElement.getElementsByTagName("css").getLength() == 1) {
						elementValue = eElement.getElementsByTagName("css").item(0).getTextContent();
						detailMap.put("css", elementValue);
					} else if (eElement.getElementsByTagName("class").getLength() == 1) {
						elementValue = eElement.getElementsByTagName("class").item(0).getTextContent();
						detailMap.put("class", elementValue);
					} else if (eElement.getElementsByTagName(Constants.PREFIX_FIELD_CLASSNAME).getLength() == 1) {
						elementValue = eElement.getElementsByTagName(Constants.PREFIX_FIELD_CLASSNAME).item(0)
								.getTextContent();
						detailMap.put(Constants.PREFIX_FIELD_CLASSNAME, elementValue);
					} else
						elementValue = "";
				}
			}

			APP_LOG.debug("XML reading done ||  getElement : elementValue = " + elementValue);

		} catch (Exception e) {
			APP_LOG.debug("Element not found in the  xml");
			APP_LOG.debug("getElementAttribute XML reading getElement : " + e);
		}

		return detailMap;
	}

}

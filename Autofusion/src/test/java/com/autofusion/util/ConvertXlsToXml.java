package com.autofusion.util;
/**
 * @author nitin.singh
 */
import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;


@SuppressWarnings("unused")
public class ConvertXlsToXml {

	public static void main(String[] args) {
	     
		  Document doc = null;
		  String xlsFilePath;//="C:\\GL\\AutofusionDemo\\mobile\\OR_Test.xlsx";
		  xlsFilePath =  args[0] ;
		 String  fileName =  args[1] ;
		  
		  try{
			  
			  DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			  DocumentBuilder docBuilder = null;
			try {
				docBuilder = docFactory.newDocumentBuilder();
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
			  // root elements
			  doc = docBuilder.newDocument();
			  Element rootElement = doc.createElement("or");
			  doc.appendChild(rootElement);
 			 
 				//Element xmlDescEle = doc.createElement("OR");
 				
			  Xls_Reader programXls = new Xls_Reader(xlsFilePath+"\\"+fileName);
				 for(int suiteCount = 2; suiteCount <= programXls.getRowCount("OR"); suiteCount++){
					String variableName  = programXls.getCellData("OR", "Variable Name", suiteCount);
					String xpathChrome= programXls.getCellData("OR", "xpathChrome", suiteCount);
					String id =programXls.getCellData("OR", "id", suiteCount);
					String name =programXls.getCellData("OR", "name", suiteCount);
					String css = programXls.getCellData("OR", "css", suiteCount);
					String xpath = programXls.getCellData("OR", "xpath", suiteCount);
					String link = programXls.getCellData("OR", "link", suiteCount);
					String type = programXls.getCellData("OR", "type", suiteCount);
					String dynamic = programXls.getCellData("OR", "dynamic", suiteCount);
					String tagName = programXls.getCellData("OR", "tagName", suiteCount);
					String className = programXls.getCellData("OR", "className", suiteCount);
					
				
					
						//Document dom = null;
					
						Element xmlDescEle = doc.createElement(variableName);
						rootElement.appendChild(xmlDescEle);
 
					
					if(!xpathChrome.equalsIgnoreCase("")){  
						 
						Element xmlCreateEle1 = doc.createElement("xpathChrome");
						xmlCreateEle1.appendChild(doc.createTextNode(xpathChrome));
						xmlDescEle.appendChild(xmlCreateEle1);
	
					}
					
					if(!id.equalsIgnoreCase("")){  
						 
						Element xmlCreateEle1 = doc.createElement("id");
						xmlCreateEle1.appendChild(doc.createTextNode(id));
						xmlDescEle.appendChild(xmlCreateEle1);
	
					}
					if(!name.equalsIgnoreCase("")){  
						 
						Element xmlCreateEle1 = doc.createElement("name");
						xmlCreateEle1.appendChild(doc.createTextNode(name));
						xmlDescEle.appendChild(xmlCreateEle1);
	
					}
					if(!css.equalsIgnoreCase("")){  
						 
						Element xmlCreateEle1 = doc.createElement("css");
						xmlCreateEle1.appendChild(doc.createTextNode(css));
						xmlDescEle.appendChild(xmlCreateEle1);
	
			}
					if(!xpath.equalsIgnoreCase("")){  
						 
						Element xmlCreateEle1 = doc.createElement("xpath");
						xmlCreateEle1.appendChild(doc.createTextNode(xpath));
						xmlDescEle.appendChild(xmlCreateEle1);
	
			}
					if(!link.equalsIgnoreCase("")){  
						 
						Element xmlCreateEle1 = doc.createElement("link");
						xmlCreateEle1.appendChild(doc.createTextNode(link));
						xmlDescEle.appendChild(xmlCreateEle1);
	
			}
					if(!type.equalsIgnoreCase("")){  
						 
						Element xmlCreateEle1 = doc.createElement("type");
						xmlCreateEle1.appendChild(doc.createTextNode(type));
						xmlDescEle.appendChild(xmlCreateEle1);
	
			}
					if(!dynamic.equalsIgnoreCase("")){  
						 
						Element xmlCreateEle1 = doc.createElement("dynamic");
						xmlCreateEle1.appendChild(doc.createTextNode(dynamic));
						xmlDescEle.appendChild(xmlCreateEle1);
	
			}
					if(!tagName.equalsIgnoreCase("")){  
						 
						Element xmlCreateEle1 = doc.createElement("tagName");
						xmlCreateEle1.appendChild(doc.createTextNode(tagName));
						xmlDescEle.appendChild(xmlCreateEle1);
	
			}
					if(!className.equalsIgnoreCase("")){  
						 
						Element xmlCreateEle1 = doc.createElement("className");
						xmlCreateEle1.appendChild(doc.createTextNode(className));
						xmlDescEle.appendChild(xmlCreateEle1);
	
			}
					
			
				
					/*if(!xpath.equalsIgnoreCase("")){  
										 
								Element xmlCreateEle1 = doc.createElement("xpath");
								xmlCreateEle1.appendChild(doc.createTextNode("data"));
								xmlDescEle.appendChild(xmlCreateEle1);
			
					}*/
				}
				
				/***********************Saving File ***********************/
				  TransformerFactory transformerFactory = TransformerFactory.newInstance();
				  Transformer transformer = transformerFactory.newTransformer();
				  DOMSource source = new DOMSource(doc);
				  
				  StreamResult result = new StreamResult(new File(xlsFilePath+"\\OR.xml").getAbsolutePath());
				  
					// Output to console for testing
				  StreamResult result1 = new StreamResult(System.out);
			 
				   transformer.transform(source, result);
			 	   System.out.println("File saved!");
		 }
		  
		  catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      
	}	  
}
package com.autofusion.ui;
/**
 * @author nitin.singh
 */
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.RequestContext;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import com.autofusion.constants.Constants;
import com.autofusion.util.Xlsx_Reader;
//import com.web.datascripts.ConvertIdeToKeywordXls;

/**
 * Servlet implementation class Convert
 */
@SuppressWarnings({"rawtypes","unused"})
public class Convert extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Convert() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

			HashMap requestMap = uploadFile(request, response);
			
			String idePath = requestMap.get("testCaseBasePath").toString();
			
			/*CommonUtility objCommonUtility = new CommonUtility();
			String oldName = "/ide/"+requestMap.get("fileName").toString();
			String newName = "/ide/"+requestMap.get("suiteName").toString();
			
			boolean a = objCommonUtility.renameFile(oldName, newName, idePath);
			
			
			boolean res = updateConfig(requestMap);
			
			ConvertIdeToKeywordXls convertObj = new ConvertIdeToKeywordXls();
			convertObj.convert(idePath);
			
			if(res)
				request.setAttribute("message", "success");
			else
				request.setAttribute("message", "error");
			
			request.getRequestDispatcher("/addTestCase.jsp").forward(request, response);*/
//			
//			RequestDispatcher reqDisObj = request.getRequestDispatcher("/testDetail.jsp");
//			reqDisObj.forward(request, response);
	}

	public boolean updateConfig(HashMap<String , String> request){
			
			String basePath = request.get("testCaseBasePath");
			
			Xlsx_Reader suiteXls = new Xlsx_Reader(basePath+"/ide/"+Constants.IDE_FILE_NAME);
			
			File file = new File(basePath+"/web/"+request.get("suiteName").trim()+".xlsx");
			
			if(file.exists())
			{
				return false;
			}
			int count = suiteXls.getRowCount("sheet1")+1;
			suiteXls.openXls();
			suiteXls.setCellData("sheet1", "IdeFileName", 		count, request.get("suiteName").trim());
			if(request.get("optionsRadios").equalsIgnoreCase("suite")){
				suiteXls.setCellData("sheet1", "CreateNewSuit", 	count, "Y");
				suiteXls.setCellData("sheet1", "CreateNewTestCase", count, "N");
			}else if(request.get("optionsRadios").equalsIgnoreCase("testCase")){
				suiteXls.setCellData("sheet1", "CreateNewSuit", 	count, "N");
				suiteXls.setCellData("sheet1", "CreateNewTestCase", count, "Y");
			}else if(request.get("optionsRadios").equalsIgnoreCase("appTestCase")){
				suiteXls.setCellData("sheet1", "AppendInTestCase", 	count, request.get("appendTestCase"));	
			}else{
				
			}
				
			suiteXls.setCellData("sheet1", "Description", 		count, request.get("description"));
			suiteXls.setCellData("sheet1", "Runmode", 			count, "Y");
		
			boolean res = suiteXls.writeXls();
			
			if(res){
				return true;
			}else{
				return false;
			}

	}
	public HashMap<String, String> uploadFile(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		String basePath = "";
		String formField = "";
		String formValue = "";
		response.setContentType("application/octet-stream");
		//response.setContentType("application/vnd.ms-excel");
		HashMap<String, String> requestMap = new HashMap<String, String>();
		if(ServletFileUpload.isMultipartContent(request)){
			try {
					List<FileItem> multiparts = new ServletFileUpload(
					new DiskFileItemFactory()).parseRequest((RequestContext) request);
					String name = "";
					for(FileItem item : multiparts){
						if(!item.isFormField()){
							formField = item.getFieldName();
							name = new File(item.getName()).getName();
							formValue = name;
						}else{
							formField = item.getFieldName();
							formValue = item.getString();
							if(formField.equalsIgnoreCase("testCaseBasePath")){
								basePath = formValue;		
							}
							
						}
						requestMap.put(formField, formValue);
						
						if(!name.equals("") && !basePath.equals(""))
							item.write( new File(basePath+"/ide"+ File.separator + name));
						
					}
			
				     //File uploaded successfully
					request.setAttribute("message", "File Uploaded Successfully");
				} catch (Exception ex) {
					request.setAttribute("message", "File Upload Failed due to " + ex);
				}         
				          
			}else{
				request.setAttribute("message","Sorry this Servlet only handles file upload request");
			}
		 	
			return requestMap;

     	}
	 
	 
}

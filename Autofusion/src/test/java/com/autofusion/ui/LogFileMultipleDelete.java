package com.autofusion.ui;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class LogFileDelete
 */
@SuppressWarnings("rawtypes")
@WebServlet("/LogFileMultipleDelete")
public class LogFileMultipleDelete extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LogFileMultipleDelete() {
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
		PrintWriter out=response.getWriter();
		response.setContentType("text/html");
	    //int countChkboxes=Integer.parseInt(request.getParameter("countChkBoxes"));
		String pathLog = request.getParameter("pathLog");
		
		    Map updatedTestMap =  request.getParameterMap();
		    Iterator entries = updatedTestMap.entrySet().iterator();
		    while (entries.hasNext()) 
		    {
		    	Map.Entry entry = (Map.Entry) entries.next();
		    	String key = (String)entry.getKey();
		   
		    		if (key.matches("(?i).*check.*"))
		    		{
		    			 if(updatedTestMap.containsKey(key))
						 {
							 String checkboxValue=request.getParameter(key);
							 File f1 = new File(pathLog+"//"+checkboxValue);
							 if (f1.isDirectory()) 
							 {
						          File[] list = f1.listFiles();
						          if (list != null) {
						              for (int i = 0; i < list.length; i++) {
						                  File tmpF = list[i];
						                  if (tmpF.isDirectory()) {
						                      rmdir(tmpF);
						                  }
						                  tmpF.delete();
						                out.println("Folder deleted");
						              }
						          }
						          if (!f1.delete()) {
						            System.out.println("can't delete folder : " + f1);
						          }
						     }
				         }
		             }
		      }	
	    }
				
	public static void rmdir(final File folder) {
	      // check if folder file is a real folder
	      if (folder.isDirectory()) {
	          File[] list = folder.listFiles();
	          if (list != null) {
	              for (int i = 0; i < list.length; i++) {
	                  File tmpF = list[i];
	                  if (tmpF.isDirectory()) {
	                      rmdir(tmpF);
	                  }
	                  tmpF.delete();
	              }
	          }
	          if (!folder.delete()) {
	            System.out.println("can't delete folder : " + folder);
	          }
	      }
	  }
}
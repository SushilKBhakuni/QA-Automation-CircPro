package com.autofusion.ui;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
 import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class LogFileDelete
 */
 public class LogFileDelete extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LogFileDelete() {
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
		String path=request.getParameter("Path");
		String logFileName=request.getParameter("LogFile");
		//String testBasePath = (String) request.getSession().getAttribute("testCaseBasePath");
		 File f1 = new File(path+"//"+logFileName);
		 if (f1.isDirectory()) {
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
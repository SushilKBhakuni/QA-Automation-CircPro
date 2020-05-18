package com.autofusion.ui;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.autofusion.sql.SQLManager;

public class Login extends HttpServlet 
{
	public String foldpath="";
	private static final long serialVersionUID = 1L;
	public static Properties CONFIG;
	public static Properties USER_CONFIG;
	
	/**
     * @see HttpServlet#HttpServlet()     */
    public Login() {
        super();
    }

	/**
	 * @se3e HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doPost(request,response);
	}
	
		
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession(true); 
	    session.setAttribute("userName", request.getParameter("userName"));
	    
        if(request.getParameter("selProject").equalsIgnoreCase("newProject")){
			session.setAttribute("projectName", request.getParameter("newProjectName"));
		 	response.sendRedirect("setting.jsp");
        }else{
        	String project = request.getParameter("selProject");
        	String projectFolderPath;
		 
			projectFolderPath = loginfo(project);
		 System.out.println(projectFolderPath);
        	if(projectFolderPath != null)
        	{
        		session.setAttribute("testCaseBasePath", projectFolderPath+File.separator+request.getParameter("selProject"));
        		session.setAttribute("prjBasePath", projectFolderPath);
        		session.setAttribute("projectName", request.getParameter("selProject"));
				response.sendRedirect("dashboard.jsp");
			}
	    }
  }
	
	// For getting the folder path, project name and folder name
		public String loginfo(String projname) 
		{
			String foldpath="";
			try{
				Connection con = SQLManager.getConnection();
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery("Select prj_testcase_dir from tbl_project where project_code = '"+projname+"'");
				while(rs.next())
				{
					foldpath = rs.getString(1);
					System.out.println("loginfo called"+rs.getString(1));
				}
			}catch(SQLException e){
				return foldpath;
			}
			return foldpath;	
		}    
}
package com.autofusion.ui;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

//import javax.mail.Session;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.autofusion.bean.CommonUtility;
//import org.apache.log4j.Logger;
import com.autofusion.sql.SQLManager;
import com.autofusion.web.DriverScript;

@SuppressWarnings("unused")
public class SaveSetting extends HttpServlet
 {
	private static final long serialVersionUID = 1L;
	public static Properties CONFIG;
	private Logger APP_LOGS = Logger.getLogger(DriverScript.class);
    public SaveSetting() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		createHierarchyFolder(request, response);
		try {
			insertintoDb(request, response);
			saveSettingInConfig(request, response);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
	}

	public void insertintoDb(HttpServletRequest request, HttpServletResponse response) throws SQLException
	{
		String query="", query1="";
		Connection con = SQLManager.getConnection() ;
    	Statement stmt = con.createStatement();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date dateobj = new Date();
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
		String projectName = request.getParameter("projectName");
		String folderPath = request.getParameter("testCaseBasePath");
		String projectCode = request.getParameter("projectName").replace(" ", "").trim();
		//String foldname=request.getParameter("foldname");
		String foldname="";

		query = "insert into tbl_project(project_code, project_description, prj_testcase_dir, active, change_date, creation_date) " +
				" values('"+projectCode+"','"+projectName+"','"+folderPath+"','','"+sdf.format(dateobj)+"','"+sdf.format(dateobj)+"')";
		stmt.executeUpdate(query);	
	}
	
    public void saveSettingInConfig(HttpServletRequest request, HttpServletResponse response ) throws ServletException
    {	
    	
    	 String content = "projectName="+request.getParameter("projectName")+"\r\n";
		    	content+= "mblMenu="+request.getParameter("mblMenu")+"\r\n";
		    	content+= "webMenu="+request.getParameter("webMenu")+"\r\n";
		    	content+= "apiMenu="+request.getParameter("apiMenu")+"\r\n";
		    	content+= "desktopMenu="+request.getParameter("desktopMenu")+"\r\n";
		    	content+= "manageMenu="+request.getParameter("manageMenu")+"\r\n";
		    	content+= "reportMenu="+request.getParameter("reportMenu")+"\r\n";
		    	content+= "#------- MYSQL CONFIGURATION --------#\r\n";
		    	content+= "dbUserName="+request.getParameter("dbUserName")+"\r\n";
		    	content+= "dbPassword="+request.getParameter("dbPassword")+"\r\n";
		    	content+= "#------- DASHBOARD CONFIGURATION --------#\r\n";
		    	content+= "webDashboardMenu="+request.getParameter("webDashboardMenu")+"\r\n";
		    	content+= "mblDashboardMenu="+request.getParameter("mblDashboardMenu")+"\r\n";
		    	content+= "dskDashboardMenu="+request.getParameter("dsksDashboardMenu")+"\r\n";
		    	content+= "mblIosDashboardMenu="+request.getParameter("mblIosDashboardMenu")+"\r\n";
		    	content+= "#-------ANDROID CONFIGURATION--------#\r\n";
		    	content+= "ADB_PATH="+request.getParameter("andSdkPath")+"\r\n";
		    	content+= "APK_PATH="+request.getParameter("apkPath")+"\r\n";
		    	content+= "APK_NAME="+request.getParameter("apkName")+"\r\n";
		    	content+= "APPIUM_PORT="+request.getParameter("appiumPort")+"\r\n";
		    	content+= "APK_PACKAGE="+request.getParameter("packageName")+"\r\n";
		    	content+= "LAUNCH_ACTIVITY="+request.getParameter("launchActivity")+"\r\n";
		    	content+= "ReInstallApp="+request.getParameter("reInstallApp")+"\r\n";
		    	content+= "#-------EMAIL CONFIGURATION--------#\r\n";
		    	content+= "SMTP_HOST="+request.getParameter("smtpHost")+"\r\n";
		    	content+= "SMTP_PORT="+request.getParameter("smtpPort")+"\r\n";
		    	content+= "EMAIL_CC="+request.getParameter("emailCC")+"\r\n";
		    	content+= "EMAIL_TO="+request.getParameter("emailTo")+"\r\n";
		    	content+= "EMAIL_BCC="+request.getParameter("emailBCC")+"\r\n";
		    	content+= "EMAIL_SUBJECT="+request.getParameter("subject")+"\r\n";
		    	content+= "MAIL_BODY_TEXT="+request.getParameter("emailMsg")+"\r\n";
		    	content+= "EMAIL_USER_NAME="+request.getParameter("emailUserName")+"\r\n";
		    	content+= "EMAIL_PASSWORD="+request.getParameter("emailPass")+"\r\n";
		    	content+= "#------- IOS CONFIGURATION --------#\r\n";
		    	content+= "IOS_UDID="+request.getParameter("iosUdid")+"\r\n";
		    	content+= "IOS_DEVICE_NAME="+request.getParameter("iosDeviceName")+"\r\n";
		    	content+= "IOS_PLATFORM_VER="+request.getParameter("iosPlatformVer")+"\r\n";
		    	
		    try{ 
		    	File file = new File(request.getParameter("testCaseBasePath")+File.separator+request.getParameter("projectName")+File.separator+"config.properties");
	 
				if (!file.exists()) {
					file.createNewFile();
				}
	 
				FileWriter fw = new FileWriter(file.getAbsoluteFile());
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write(content);
				bw.close();
		    }catch(Exception e){
		    	
		    }
			System.out.println("Done");
	}
   
   
    public void createHierarchyFolder(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	
    	String folderPath = request.getParameter("testCaseBasePath");
    	String projectFolderName =folderPath+File.separator+request.getParameter("projectName");
    	String workDir = request.getServletContext().getRealPath(File.separator);

    	File srcFolder = new File(workDir+File.separator+"samplestructure");
    	File destFolder = new File(projectFolderName);
    	
    	CommonUtility.copyFolder(srcFolder, destFolder);
    	
		HttpSession session = request.getSession(true);
		session.setAttribute("testCaseBasePath", destFolder.getAbsolutePath());
    }
}
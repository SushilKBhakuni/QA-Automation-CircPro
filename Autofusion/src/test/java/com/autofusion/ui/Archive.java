package com.autofusion.ui;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Archive
 */
@WebServlet("/Archive")
public class Archive extends HttpServlet {
	private static final long serialVersionUID = 1L;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Archive() {
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
		String testBasePath = (String) request.getSession().getAttribute("testCaseBasePath");
		File src = new File(path+"//"+logFileName);
		//File dest = new File("C://Archive"+"//"+logFileName);
		File dest = new File(testBasePath+"//"+"Archive"+"//"+logFileName);
		if(!dest.exists()){
			if(!src.exists()){
		       System.out.println("Directory does not exist.");
		       System.exit(0);
		    }else{
		       try{
		    	if(src.isDirectory())
				{
						if(!dest.exists())
						{
						   dest.mkdirs();
						}
			
						String files[] = src.list();
				
						for (String file : files) {
						   File srcFile = new File(src, file);
						   File destFile = new File(dest, file);
						   copyFolder(srcFile,destFile);
						}
				}else{
						InputStream in = new FileInputStream(src);
				        OutputStream out1 = new FileOutputStream(dest); 
			
				        byte[] buffer = new byte[1024];
			
				        int length;
				        
				        while ((length = in.read(buffer)) > 0){
					    	   out1.write(buffer, 0, length);
					        }
				        in.close();
				        out1.close();
				} out.println("success");
		    	
		       }catch(IOException e){
		    	   e.printStackTrace();
		    	   System.exit(0);
		       }
		    }}	
		out.close();
	}
	public static void copyFolder(File src, File dest)	throws IOException{
		if(src.isDirectory())
		{
				if(!dest.exists())
				{
				   dest.mkdirs();
				}
	
				String files[] = src.list();
		
				for (String file : files) {
				   File srcFile = new File(src, file);
				   File destFile = new File(dest, file);
				   copyFolder(srcFile,destFile);
				}
		}else{
				InputStream in = new FileInputStream(src);
		        OutputStream out = new FileOutputStream(dest); 
	
		        byte[] buffer = new byte[1024];
	
		        int length;
		        
		        while ((length = in.read(buffer)) > 0){
			    	   out.write(buffer, 0, length);
			        }
		        in.close();
		        out.close();
		}
    }
}

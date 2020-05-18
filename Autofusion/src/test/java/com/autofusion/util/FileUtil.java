package com.autofusion.util;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.autofusion.BaseClass;


public final class FileUtil {
	 public static void copyFolder(File src, File dest)
		    	throws IOException{
		 if(src.isDirectory()){

		    		//if directory not exists, create it
		    		if(!dest.exists()){
		    		   dest.mkdir();
		    		   System.out.println("Directory copied from "
		                              + src + "  to " + dest);
		    		}

		    		
		    		
		    		//list all the directory contents
		    		String files[] = src.list();

		    		for (String file : files) {
		    		   //construct the src and dest file structure
		    		   File srcFile = new File(src, file);
		    		   File destFile = new File(dest, file);
		    		   //recursive copy
		    		   copyFolder(srcFile,destFile);
		    		}

		    	}else{
		    		//if file, then copy it
		    		//Use bytes stream to support all file types
		    		InputStream in = new FileInputStream(src);
		    	        OutputStream out = new FileOutputStream(dest);

		    	        byte[] buffer = new byte[1024];

		    	        int length;
		    	        //copy the file content in bytes
		    	        while ((length = in.read(buffer)) > 0){
		    	    	   out.write(buffer, 0, length);
		    	        }

		    	        in.close();
		    	        out.close();
		    	        System.out.println("File copied from " + src + " to " + dest);
		    	}

}
	 public static void deleteFile(String dest)
	 {
	 File file = new File(dest);      
     String[] myFiles;    
         if(file.isDirectory())
        		 {
             myFiles = file.list();
             for (int i=0; i<myFiles.length; i++) {
                 File myFile = new File(file, myFiles[i]); 
                 myFile.delete();
             }
          }
	 }
	 
	 
	 
	 
	 public static void renameFile()
	 {

			File oldfile =new File("src/test/resources/jenkinsLatestReport/ExecutionReport_"+BaseClass.reportStartTime+".html");
			File newfile =new File("src/test/resources/jenkinsLatestReport/ExtentReport.html");

			if(oldfile.renameTo(newfile)){
				System.out.println("Rename succesful");
			}else{
				System.out.println("Rename failed");
			}

	    }
	 
}

package com.autofusion.email;

public class SendEMail {
	public static String createMailContents()
    {
		 String mainContents="";
		 mainContents = "<body bgcolor='#FFFFFF'><font align='justify' color='black' face='Garamond' background-color='gray'>"+
                 " <img src=\"cid:image\">"+
                  "<h3>Autofusion Automated Test Execution Report</h3></font><hr><font align='justify' face='Garamond'>";
                  
		 mainContents = mainContents +"<br>Please find attached execution report<br>";
		 mainContents = mainContents + "<br><br>Detailed execution results, are available at following location: <a href=''>Autofusion Automation Report</a>" +
                 "<br><br>For any further info/clarification please contact  <a href='mailto:@globallogic.com'>Automation Team</a>" +

                 "<br><br>Regards,<br>Autofusion Automation Team</font></body>";
		 return mainContents;
    }
}

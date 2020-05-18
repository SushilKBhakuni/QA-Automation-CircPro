package com.autofusion.util;

import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeMultipart;

import com.autofusion.ResourceConfigurations;

public class GmailUtility {
	Properties props = new Properties();
	Folder mailFolder;
	Store mailStore;
	
	
	/*public static void main(String[] args) {
		 
		GmailUtility gmail = new GmailUtility();
        gmail.getURL();
    }*/
	public String getURL()
	{
		String str = gmailConfig();
        str = str.split("https://")[1];
        str = str.split(" ")[0];
        str = str.replaceAll("If", "");
        str = str.trim();
        str = "https://"+str;
        System.out.println("URL "+ str);
        return str;      
        
	}
	public void setProperty()
	{
		props.setProperty("mail.imap.host", "imap.gmail.com");
		props.setProperty("mail.imap.port", "993");
		props.setProperty("mail.imap.connectiontimeout", "5000");
		props.setProperty("mail.imap.timeout", "5000");
		
	}
	
	public Message[] getGmailMessages()
	{
		try{
			Session session = Session.getDefaultInstance(props, null);
			mailStore = session.getStore("imaps");			
			mailStore.connect("imap.gmail.com", ResourceConfigurations.getProperty("gmailUserName"), ResourceConfigurations.getProperty("gmailPassword"));
			mailFolder = mailStore.getFolder("inbox");	            
			mailFolder.open(Folder.READ_WRITE);
			int messageCount = mailFolder.getMessageCount();
			System.out.println("Total Messages:- " + messageCount);
			return mailFolder.getMessages();
		}catch(Exception e)
		{
			return null;
		}		
	}
	public void closeGmailSession()
	{
		try {
			mailFolder.close(true);
			mailStore.close();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	public void deleteMail(Message message)
	{
		try {
			message.setFlag(Flags.Flag.DELETED, true);
			  System.out.println("Deleted");	
		} catch (MessagingException e) {
			e.printStackTrace();
		}
      	
	}
 
    public String gmailConfig() {
       
        String result ="";
        setProperty();
 
        try {
        	Message[] messages = getGmailMessages();            
            
            System.out.println("------------------------------");
 
            for (int i = 0; i < messages.length; i++) {	                
                System.out.println("Email Number " + (i + 1));
                System.out.println("Subject: " +  messages[i].getSubject());
                System.out.println("From: " +  messages[i].getFrom()[0]);
                System.out.println("Date:"+messages[i].getReceivedDate());
                //deleteMail(messages[i]);                
                MimeMultipart mimeMultipart = (MimeMultipart)messages[i].getContent();
                int count = mimeMultipart.getCount();
                for (int j = 0; j < count; j ++){
                    BodyPart bodyPart = mimeMultipart.getBodyPart(j);
                    if (bodyPart.isMimeType("text/plain")){
                        result = result + "\n" + bodyPart.getContent();
                        break;  //without break same text appears twice in my tests
                    } else if (bodyPart.isMimeType("text/html")){
                        String html = (String) bodyPart.getContent();
                        result = result + html;	
                        }
                }
                System.out.println("Body:" + result);
                
            }
 
           closeGmailSession();
 
        } catch (Exception e) {
            e.printStackTrace();
        }
		return result;
    }
 
}

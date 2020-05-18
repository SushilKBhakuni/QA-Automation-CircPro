package com.autofusion.util;
/**
 * @author nitin.singh
 */
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;



public class SendMail
{
     
      public  static boolean sendMail( String userName,	String password,
      		String[] to,
      		String[] cc,
      		String[] bcc,
      		String subject,
      		String text,
      		String attachmentPath,
    		String attachmentName,String smtpHost,
      		String smtpPort, org.apache.log4j.Logger APP_LOG){

  	  	 	if(userName == null || userName.trim().equals("")){
  	  	 		return false;
  	  	 	}
 		    
  	  	 	// Creates a Session with the following properties.
 		        Properties props = new Properties();
 		        props.put("mail.smtp.auth", "true");
 				props.put("mail.smtp.starttls.enable", "true");
 				props.put("mail.smtp.host", "smtp.gmail.com");
 		        props.put("mail.transport.protocol", smtpHost);
 		        props.put("mail.smtp.port", smtpPort);
 		        System.setProperty("https.protocols", "TLSv1.1");
 		        Session session = Session.getInstance(props,
 		      		  new javax.mail.Authenticator() {
 		      			protected PasswordAuthentication getPasswordAuthentication() {
 		      				return new PasswordAuthentication(userName, password);
 		      			}
 		      		  });
 		        try {
 		        	
 		        	session.getProperties().put("mail.smtp.starttls.enable", "true");
 		            // Create an Internet mail msg.
 		            MimeMessage msg = new MimeMessage(session);
 		            msg.setFrom(new InternetAddress(userName));

                    for(int i=0;i<to.length;i++){
                    	msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to[i]));
                    }

                    for(int i=0;i<cc.length;i++){
                    	msg.addRecipient(Message.RecipientType.CC, new InternetAddress(cc[i]));
                    }

                    for(int i=0;i<bcc.length;i++){
                    	msg.addRecipient(Message.RecipientType.BCC, new InternetAddress(bcc[i]));
                    }
 		            
// 		            msg.setRecipient(Message.RecipientType.TO, toAddress);
 		            msg.setSubject(subject);
 		            msg.setSentDate(new Date());

		            MimeBodyPart messagePart = new MimeBodyPart();
		            messagePart.setText(text);
 		            // Set the email attachment file
 		            FileDataSource fileDataSource = new FileDataSource(attachmentPath);

 		            MimeBodyPart attachmentPart = new MimeBodyPart();
 		            attachmentPart.setDataHandler(new DataHandler(fileDataSource));
 		            attachmentPart.setFileName(fileDataSource.getName());

 		            // Create Multipart E-Mail.
		            Multipart multipart = new MimeMultipart();
		            multipart.addBodyPart(messagePart);
		            multipart.addBodyPart(attachmentPart);

 		            msg.setContent(multipart);

 		            // Send the msg. Don't forget to set the username and password
 		            // to authenticate to the mail server.
 		            System.out.println("Sending email...");
 		            Transport.send(msg);
 		            System.out.println("Email Sent");
 		            APP_LOG.debug("Email Sent");
 	 		        return true;

		            
 		        } catch (MessagingException e) {
 		            e.printStackTrace();
 		           System.out.println("Error in Sending email"+e);
		            APP_LOG.debug("Error in Sending email "+e);

 	 		        return false;
 		        }
 		    }
}
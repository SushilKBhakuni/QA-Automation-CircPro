package com.autofusion.email;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.Base64;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.Message;

@SuppressWarnings({"resource","unused"})
public class MailSender {
	/**
     * Application name.
     */
    private static final String APPLICATION_NAME =
            "Gmail Automation";

    /**
     * Directory to store user credentials for this application.
     */
    private static final java.io.File DATA_STORE_DIR = new java.io.File(System.getProperty("user.dir")+"\\email\\");

    /**
     * Global instance of the {@link FileDataStoreFactory}.
     */
    private static FileDataStoreFactory DATA_STORE_FACTORY;

    /**
     * Global instance of the JSON factory.
     */
    private static final JsonFactory JSON_FACTORY =
            JacksonFactory.getDefaultInstance();

    /**
     * Global instance of the HTTP transport.
     */
    private static HttpTransport HTTP_TRANSPORT;

    /**
     * Global instance of the scopes required by this quickstart.
     */
    private static final List<String> SCOPES =
            Arrays.asList(GmailScopes.MAIL_GOOGLE_COM);

    /**
     *  Properties relative path for email address
     */
    private static final String EMAIL_PROPERTIES_RELATIVE_PATH = System.getProperty("user.dir")+"\\email\\email_recipients.txt";

    static {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Creates an authorized Credential object.
     *
     * @return an authorized Credential object.
     * @throws IOException the io exception
     */
    public static Credential authorize() throws IOException {
        // Load client secrets.
        String fileName = null;
        String username = System.getProperty("user.name");

            fileName = System.getProperty("user.dir")+"\\email\\client_secret.json";

        InputStream in = new FileInputStream(fileName);
        GoogleClientSecrets clientSecrets =
                GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow =
                new GoogleAuthorizationCodeFlow.Builder(
                        HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                        .setDataStoreFactory(DATA_STORE_FACTORY)
                        .setAccessType("offline")
                        .build();
        Credential credential = new AuthorizationCodeInstalledApp(
                flow, new LocalServerReceiver()).authorize("user");
        return credential;
    }
    /**
     * Build and return an authorized Gmail client service.
     *
     * @return an authorized Gmail client service
     * @throws IOException the io exception
     */
    public static Gmail getGmailService() throws IOException {
        Credential credential = authorize();
        return new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    /**
     * Create email mime message.
     *
     * @return the mime message
     * @throws MessagingException the messaging exception
     * @throws MessagingException the messaging exception
     */
    public static MimeMessage createEmail(String file) throws MessagingException, javax.mail.MessagingException {
        Properties props = new Properties();
        javax.mail.Session session = javax.mail.Session.getDefaultInstance(props, null);

        MimeMessage email = new MimeMessage(session);
        email.setFrom(new InternetAddress(""));
        email.addRecipient(javax.mail.Message.RecipientType.TO,
                new InternetAddress(""));
        try{
            String username = System.getProperty("user.name");
            String fileName;
            Charset charset = Charset.forName("US-ASCII");

                fileName = System.getProperty("user.dir")+"\\email\\email_recipients.txt";
                System.out.println(" File Name :  "+fileName);
                File file1 = new File(fileName);
    			FileReader fileReader = new FileReader(file1);
    			BufferedReader br = new BufferedReader(fileReader);
    			//StringBuffer stringBuffer = new StringBuffer();
            //BufferedReader br = new BufferedReader(new FileReader(fileName),charset);
                //BufferedReader br = Files.newBufferedReader(fileName,charset);
            String line;
            while ((line = br.readLine()) != null)
            {
                email.addRecipient(javax.mail.Message.RecipientType.CC,
                        new InternetAddress(line));
            }

        } catch (Exception e) {
            System.out.println("Failed to read file. Sending email to default recipients");
            email.addRecipient(javax.mail.Message.RecipientType.TO,
                    new InternetAddress("@globallogic.com"));
            email.addRecipient(javax.mail.Message.RecipientType.TO,
                    new InternetAddress("@globallogic.com"));
            email.addRecipient(javax.mail.Message.RecipientType.TO,
                    new InternetAddress("@globallogic.com"));


        }
        email.setSubject("Autofusion Automated Execution Report");

        String str = SendEMail.createMailContents();
        MimeBodyPart mimeBodyPart = new MimeBodyPart();

        mimeBodyPart.setContent(str, "text/html; charset=utf-8");
        //email.setContent(SendMailAPI.createMailContents(), "text/html; charset=utf-8");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(mimeBodyPart);
        mimeBodyPart = new MimeBodyPart();
        DataSource source = new FileDataSource(new File(System.getProperty("user.dir")+"\\email\\AutofusionLogo.PNG"));
        mimeBodyPart.setDataHandler(new DataHandler(source));
        mimeBodyPart.setFileName(file);
        mimeBodyPart.setHeader("Content-ID", "<image>");
        multipart.addBodyPart(mimeBodyPart);
        
        MimeBodyPart attachBodyPart = new MimeBodyPart();
        DataSource source1 = new FileDataSource(file);

        attachBodyPart.setDataHandler(new DataHandler(source1));
        attachBodyPart.setFileName("ExtentReport.html");

        multipart.addBodyPart(attachBodyPart);
        email.setContent(multipart);

        return email;
    }

    /**
     * Create message with email message.
     *
     * @param email the Mime Message email
     * @return the message
     * @throws MessagingException the messaging exception
     * @throws IOException        the io exception
     * @throws MessagingException the messaging exception
     */
    public static Message createMessageWithEmail(MimeMessage email)
            throws MessagingException, IOException, javax.mail.MessagingException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        email.writeTo(bytes);
        String encodedEmail = Base64.encodeBase64URLSafeString(bytes.toByteArray());
        Message message = new Message();
        message.setRaw(encodedEmail);
        return message;
    }



    /**
     * Send message.
     *
     * @param service the gmail service
     * @param userId  the user id of the email
     * @param email   the Mime Message email
     * @throws MessagingException the messaging exception
     * @throws IOException        the io exception
     * @throws MessagingException the messaging exception
     */
    public static void sendMessage(Gmail service, String userId, MimeMessage email)
            throws MessagingException, IOException, javax.mail.MessagingException {
        Message message = createMessageWithEmail(email);
        try {
            service.users().messages().send(userId, message).execute();

        } catch (Exception e) {
            System.out.println("Internet Error. Failed to send Email");
        }

    }

    /**
     * Send mail.
     *
     * @throws IOException the io exception
     */
    public static void sendMail(String fileName) throws IOException {
        // Build a new authorized API client service.
        Gmail service = getGmailService();
        // Print the labels in the user's account.
        String user = "me";

        try {

            MimeMessage m = createEmail(fileName);

            //Message msg = createMessageWithEmail(m);
            sendMessage(service, "me", m);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static Set<String> loadEmailAddress() throws IOException {
        Properties p = new Properties();
        InputStream input = null;
        input = new MailSender().getClass().getResourceAsStream(EMAIL_PROPERTIES_RELATIVE_PATH);
        p.load(input);
        return p.stringPropertyNames();

    }

    public static void  main(String args[])
    {
        try {
        	System.out.println(System.getProperty("user.dir"));
            sendMail(System.getProperty("user.dir")+"\\email\\email_recipients.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

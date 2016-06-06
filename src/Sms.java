
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Sms /*implements Runnable*/
{
		static private String to = "sistema.control.bienes.blanks@gmail.com";
		static private String tituloCorreo = "Título del correo";
		static private String contenidoCorreo = "<h1>This is a html content test email.</h1>";
		
		public Sms(String to_argc, String titCor_argc, String conCor_argc)
		{
			to = to_argc;
			tituloCorreo = titCor_argc; 
			contenidoCorreo = conCor_argc;
 
			  Properties props = new Properties();  
			  props.put("mail.smtp.host", "smtp.gmail.com");  
			  props.put("mail.smtp.socketFactory.port", "587"); 
			  props.put("mail.smtp.socketFactory.class",  
			            "javax.net.ssl.SSLSocketFactory");  
			  props.put("mail.smtp.auth", "true");  
			  props.put("mail.smtp.port", "587");
			  props.put("mail.smtp.starttls.enable", "true"); 
			  Session session = Session.getDefaultInstance(props,  
			   new javax.mail.Authenticator() {  
			   protected PasswordAuthentication getPasswordAuthentication() {  
			   return new PasswordAuthentication("sistema.control.bienes.blanks@gmail.com","blanksc5Fj76iZ");//change accordingly  
			   }  
			  });  
				  try 
				  {  
				   MimeMessage message = new MimeMessage(session);  
				   //1 = high, 3 = normal, 5 = low
				   message.setHeader("X-Priority", "1"); 
				   message.setFrom(new InternetAddress("sistema.control.bienes.blanks@gmail.com"));//change in like manner 
				   message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));  
				   message.setSubject(tituloCorreo);
				   message.setContent(contenidoCorreo, "text/html");
				   Transport.send(message);  
				   System.out.println("SE ENVÍA CORREO!!!");  
				  } 
				  catch (MessagingException e) 
				  {
					  throw new RuntimeException(e);
				  }	
		}

}
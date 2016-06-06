/* Copyright (c) 2001-2005, The HSQL Development Group
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 *
 * Neither the name of the HSQL Development Group nor the names of its
 * contributors may be used to endorse or promote products derived from this
 * software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL HSQL DEVELOPMENT GROUP, HSQLDB.ORG,
 * OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
//jt.2.new.ini
import com.intermec.datacollection.rfid.BasicBRIReader;
import com.intermec.datacollection.rfid.BasicReaderEvent;
import com.intermec.datacollection.rfid.BasicReaderEventListener;
import com.intermec.datacollection.rfid.BasicReaderException;
//jt.2.new.end
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
/**
 * Title:        Testdb
 * Description:  simple hello world db example of a
 *               standalone persistent db application
 *
 *               every time it runs it adds four more rows to sample_table
 *               it does a query and prints the results to standard out
 *
 * Author: Karl Meissner karl@meissnersd.com
 */
/**
 * jt.1 Jonathan Tito
 * jt.1 13 de Enero de 2016
 * jt.1 Modificaciones para conectarse a BDD embebida con data de prueba
 * 
 * jt.2 Jonthan Tito
 * jt.2 14 de Enero de 2015
 * jt.2 Modificaciones para usar las librerias de Intermec
 * */
public class Testdb 
{

    private static BasicBRIReader m_Reader = null;
    static Connection conn = null;
    static String sResponse = null;
    private static String etiqueta = null;
    //static Properties props = null;
    //static Session session = null;
    static boolean bandera = false;
    
    public static void main(String[] args) throws Exception 
    {
    	try
    	{
	    	m_Reader = new BasicBRIReader();
	    	//m_Reader.open("tcp://192.168.0.112"); //jt PARA WINDOWS
	    	m_Reader.open();
	    	/*
	    	Class.forName("org.hsqldb.jdbcDriver");
	    	conn = DriverManager.getConnection("jdbc:hsqldb:file:" //jt.1.old.ln
	            + "/tmp/dataflash/hsqldb-2.3.3/hsqldb/lib/bddjt5",    // filenames
	            "SA",                     // username
	            ""); 
	            */
	    	/*****correo ini*******/
	    	
	    	Properties props = new Properties();  
			  props.put("mail.smtp.host", "smtp.gmail.com");  
			  props.put("mail.smtp.socketFactory.port", "587"); 
			  props.put("mail.smtp.socketFactory.class",  
			            "javax.net.ssl.SSLSocketFactory");  
			  props.put("mail.smtp.auth", "true");  
			  props.put("mail.smtp.port", "587");
			  props.put("mail.smtp.starttls.enable", "true"); 
			  final Session session = Session.getDefaultInstance(props,  
			   new javax.mail.Authenticator() {  
			   protected PasswordAuthentication getPasswordAuthentication() {  
			   return new PasswordAuthentication("sistema.control.bienes.blanks@gmail.com","blanksc5Fj76iZ");//change accordingly  
			   }  
			  });  
			  
	    	/*****correo end*******/
			 
			  //enviarCorreo("soporte.tecnico@blanks.ec","Hope A", "Hope c", session); //SI FUNCIONA
			  while(true)
			  {
				  if(principal())
				  {
					  enviarCorreo("soporte.tecnico@blanks.ec","Hope jt", "Hope jt", session); //SI FUNCIONA
					  
					  /****************************NO FUNCIONO INI**********************************************/
					  /*
					  new Thread(new Runnable() {
              	        @Override
              	        public void run() {
              	        	try {
								//enviarCorreo("soporte.tecnico@blanks.ec","Hope jt", "Hope jt", session);
              	        		Properties props = new Properties();  
              				  props.put("mail.smtp.host", "smtp.gmail.com");  
              				  props.put("mail.smtp.socketFactory.port", "587"); 
              				  props.put("mail.smtp.socketFactory.class",  
              				            "javax.net.ssl.SSLSocketFactory");  
              				  props.put("mail.smtp.auth", "true");  
              				  props.put("mail.smtp.port", "587");
              				  props.put("mail.smtp.starttls.enable", "true"); 
              				  final Session session = Session.getDefaultInstance(props,  
              				   new javax.mail.Authenticator() {  
              				   protected PasswordAuthentication getPasswordAuthentication() {  
              				   return new PasswordAuthentication("sistema.control.bienes.blanks@gmail.com","blanksc5Fj76iZ");//change accordingly  
              				   }  
              				  });  
              				MimeMessage message = new MimeMessage(session);  
              				//1 = high, 3 = normal, 5 = low
              	    		message.setHeader("X-Priority", "1"); 
              	    		message.setFrom(new InternetAddress("sistema.control.bienes.blanks@gmail.com"));//change in like manner 
              	    		//message.addRecipient(Message.RecipientType.TO,new InternetAddress(correo));  
              	    		message.addRecipient(Message.RecipientType.TO,new InternetAddress("soporte.tecnico@blanks.ec"));
              			   //message.setSubject(titulo);
              	    		message.setSubject("Test hope");
              	    		//message.setContent(contenido, "text/html");
              	    		message.setContent("Test contenido", "text/html");
              	    		Transport.send(message);  
              	    		System.out.println("SE ENVÍA CORREO!!!");  
							} catch (MessagingException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} 
              	        }
						}).start();
						*/
					  /**********************************NO FUNCIONO END****************************************/
				  }
				  
			  }
    	}
		catch (BasicReaderException brExcept)
		{
			displayBasicReaderException(brExcept);
		}


    }
			

	private static boolean principal() throws InterruptedException, Exception
	{
	/*	
		new Thread(new Runnable() {
	        @Override
	        public void run() {
	        	*/
	        	
		if(!(consultarEtiquetas().equals(null)))
		  {
			  String lista[] = consultarEtiquetas();
	            int listaLinUnifLenght = lista.length;
	            for(int ih = 0; ih <= listaLinUnifLenght -1; ih++)
	            {
	            	int tamItmListLinUnif = lista[ih].length();
	            	 if(lista[ih].length() == 25 || lista[ih].length() == 26)
	                {
	                    etiqueta=lista[ih];
	                    etiqueta=etiqueta.trim();
	                    System.out.println("Etiqueta del READER: " + etiqueta);
	                    
	                    //try {
							if(consultarBDD(etiqueta))
							{
							sendBRICommand("WRITEGPO 3 ON"); 
							  Thread.sleep(1000);
							/*try {
								this.wait(1000);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							*/
							  sendBRICommand("WRITEGPO 3 OFF"); 
							  return true; //old
							  //bandera = true; //new
							  //enviarCorreo("soporte.tecnico@blanks.ec","Hope", "Hope c", session);
							}
							/*
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							*/
						}
	                }
	            }
		/*      
		  }
	
	        }
		}).start();
		*/
		return false; //old
		//bandera = false;//new
		//return bandera;//new
	}
    
    private static String[] consultarEtiquetas()
    {
    	
    
			//m_Reader.open();
    		/******************para probar en windows ini***************************/
    		
    		 /******************para probar en windows end***************************/
	    	String datain = "";
	    	datain = sendBRICommand("READ");
	    	System.out.println("El datain q entrega el READER es: "+ datain);
	    	if (datain.indexOf("OK")>0)
	        {
	            String [ ] listaLinUnif = null; 
	            listaLinUnif = datain.split("\r"); 
	            //m_Reader.close();
	            return listaLinUnif;
	        }
			
		
		return null;
	
    }
    private static boolean consultarBDD(String etiqueta_a_consultar) throws Exception
    {
    	
    	Class.forName("org.hsqldb.jdbcDriver");
    	conn = DriverManager.getConnection("jdbc:hsqldb:file:" //jt.1.old.ln
            + "/tmp/dataflash/hsqldb-2.3.3/hsqldb/lib/bddjt5",    // filenames
            "SA",                     // username
            ""); 
            
            
    	 /*********************************************CADENA DE CONEXIÓN EN WINDOWS INI***********************************************/
        /*
        conn = DriverManager.getConnection("jdbc:hsqldb:" //jt.1.old.ln
                + "C://Users/BLANKS PC2/Desktop/RESPALDOS JTITO/JTito II/IF2A/BASE DE DATOS PARA EL IF2A/hypersonicsql/hsqldb-2.3.3/hsqldb-2.3.3/hsqldb/lib/mydbjt",    // filenames
                "SA",                     // username
                ""); 
        */
        /***********************************************CADENA DE CONEXIÓN EN WINDOWS END**********************************************/
    	System.out.print("BDD conectada!");
    	Statement stmt = conn.createStatement();
    
                    ResultSet rsI = stmt.executeQuery("select i_rfid from Item_Table where i_rfid = '" + etiqueta_a_consultar + "'" );
                    while (rsI.next()) 
                    {
                        if(etiqueta.equals(rsI.getString("i_rfid")))
                        {
                            System.out.println("COINCIDENCIA ENCONTRADA");
                            
                            /*
                        	sendBRICommand("WRITEGPO 3 ON"); 
                            Thread.sleep(1000);
                            sendBRICommand("WRITEGPO 3 OFF"); 
                            */
                            
                            /*CIERRO CONEXIÓN PARA LIBERAR RECURSOS INI**/
                            
                            stmt.execute("SHUTDOWN");
                            stmt.close();
                            conn.close();
                            
                            /*CIERRO CONEXIÓN PARA LIBERAR RECURSOS END**/
                            return true;
                        }
                    }
                
            
        
    	/*************************BLOQUE 1 MOVIDO END*********************************/
    	return false;
    }
    
    private static void enviarCorreo(String correo, String titulo, String contenido, Session session) throws MessagingException
	{
    		MimeMessage message = new MimeMessage(session);  
			//1 = high, 3 = normal, 5 = low
    		message.setHeader("X-Priority", "1"); 
    		message.setFrom(new InternetAddress("sistema.control.bienes.blanks@gmail.com"));//change in like manner 
    		message.addRecipient(Message.RecipientType.TO,new InternetAddress(correo));  
    		//message.addRecipient(Message.RecipientType.TO,new InternetAddress("soporte.tecnico@blanks.ec"));
		   message.setSubject(titulo);
    		//message.setSubject("Test hope");
    		message.setContent(contenido, "text/html");
    		//message.setContent("Test contenido", "text/html");
    		Transport.send(message);  
    		System.out.println("SE ENVÍA CORREO!!!");  
	
	}
   

    private static void displayBasicReaderException(BasicReaderException brExcept)
	{
		System.out.println("BasicReaderException: ");
		System.out.println(brExcept.getMessage());
		System.out.println("\n");
	}
    
        
    public void shutdown() throws SQLException {

        Statement st = conn.createStatement();

        // db writes out to files and performs clean shuts down
        // otherwise there will be an unclean shutdown
        // when program ends
        st.execute("SHUTDOWN");
        conn.close();    // if there are no other open connection
    }


//use for SQL command SELECT
    public synchronized void query(String expression, Connection connx) throws SQLException {

        Statement st = null;
        ResultSet rs = null;

        st = connx.createStatement();         // statement objects can be reused with

        // repeated calls to execute but we
        // choose to make a new one each time
        rs = st.executeQuery(expression);    // run the query

        // do something with the result set.
        dump(rs);
        st.close();    // NOTE!! if you close a statement the associated ResultSet is

        // closed too
        // so you should copy the contents to some other object.
        // the result set is invalidated also  if you recycle an Statement
        // and try to execute some other query before the result set has been
        // completely examined.
    }

//use for SQL commands CREATE, DROP, INSERT and UPDATE
    public synchronized void update(String expression, Connection connx) throws SQLException {

        Statement st = null;

        st = connx.createStatement();    // statements

        int i = st.executeUpdate(expression);    // run the query
        

        if (i == -1) {
            System.out.println("db error : " + expression);
        }

        st.close();
    }    // void update()

    public static void dump(ResultSet rs) throws SQLException {

        // the order of the rows in a cursor
        // are implementation dependent unless you use the SQL ORDER statement
        ResultSetMetaData meta   = rs.getMetaData();
        int               colmax = meta.getColumnCount();
        int               i;
        Object            o = null;

        // the result set is a cursor into the data.  You can only
        // point to one row at a time
        // assume we are pointing to BEFORE the first row
        // rs.next() points to next row and returns true
        // or false if there is no next row, which breaks the loop
        for (; rs.next(); ) {
            for (i = 0; i < colmax; ++i) {
                o = rs.getObject(i + 1);    // Is SQL the first column is indexed

                // with 1 not 0
                System.out.print(o.toString() + " ");
            }

            System.out.println(" ");
        }
    }
    private static String sendBRICommand(String comando)
	{
		sResponse = "";
		try
		{
			sResponse = m_Reader.execute(comando);
		}
		catch (BasicReaderException brExcept)
		{
			displayBasicReaderException(brExcept);
		}
		return sResponse;
	}
    public void exitApp()
  	{
  		try
  		{
  			if (m_Reader != null)
  			{
  				m_Reader.close();
  			}
  		}
  		catch (BasicReaderException brExcept)
  		{
  			displayBasicReaderException(brExcept);
  		}
  		System.exit(0);
  	}
  
}
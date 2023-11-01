package com.isoServer.spring.isoServer;

import java.io.IOException;

import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISORequestListener;
import org.jpos.iso.ISOServer;
import org.jpos.iso.ISOSource;
import org.jpos.iso.ServerChannel;
import org.jpos.iso.channel.PostChannel;
import org.jpos.iso.packager.EuroPackager;
import org.jpos.util.LogSource;
import org.jpos.util.Logger;
import org.jpos.util.SimpleLogListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements ISORequestListener {

	public static void main(String[] args) throws IOException {
		
		Logger logger = new Logger();
		logger.addListener(new SimpleLogListener(System.out));

		ServerChannel channel = new PostChannel(new EuroPackager());

		((LogSource) channel).setLogger(logger, "channel");

		ISOServer server = new ISOServer(8001, channel, null);

		server.setLogger(logger, "server");
		server.addISORequestListener (new Application());
		new Thread(server).start();
		
		
		
		SpringApplication.run(Application.class, args);
	}
	
	public boolean process (ISOSource source, ISOMsg m) {
	       try {
	    	   
//	    	   if (m.getString("02")== null) {
//	    		   m.setResponseMTI ();
//		           m.set (39, "05");
//		           source.send (m);
//	    	   } else {
//	    		   m.setResponseMTI ();
//		           m.set (39, "00");
//		           source.send (m);
//	    	   }
	    	//  m.setResponseMTI ();
//	    	  String pan =  m.getString("02")== null ? "05" : "00";
//	    	  m.set(39,pan);
	    	  System.out.println(m.getMTI());
	    	  if(m.getMTI().equalsIgnoreCase("0100") || m.getMTI().equals("0200")){
	    		  System.out.println(m.getMTI());
	    		  if(!m.getString(03).equals("310000")) {
	    			  m.setResponseMTI ();
	    			  m.set("39","55");
	    			  source.send (m);
	    		  } else if(!m.getString(02).equals("4160211510908933") || m.getString(02)== null) {
	    			  m.setResponseMTI ();
	    			  m.set("39","14");
	    			  source.send (m);
	    		  } else {
	    			  m.setResponseMTI ();
	    			  m.set("39","00");
	    			  source.send (m);
	    		  }
	    	  }
	    	  
	    	  
	    	 // source.send (m);
	           
	       } catch (ISOException e) {
	           e.printStackTrace();
	       } catch (IOException e) {
	           e.printStackTrace();
	       }
	       return true;
	}

}
//System.out.println(m.getString("02"));
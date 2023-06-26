package pt.estgp.domem.utils;

import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.apache.log4j.Logger;


public class ServerStatus {

	private static final Logger logger = Logger.getLogger("");
	
	private static 	ConfigProperties configProps = new ConfigProperties("application.properties");

	public boolean isMySQlOnline() {

		Connection connection = null;
		
		String jdbcUrl = configProps.getPropertyValue("jdbc.url");
		String userName = configProps.getPropertyValue("jdbc.username");
		String passWord = configProps.getPropertyValue("jdbc.password");
				
		try {
			connection = DriverManager.getConnection(jdbcUrl + "?user=" + userName +"&password=" + passWord);
		} catch (SQLException ex) {
			logger.error("ocorreu excepcao: " + ex);
		}
		
		final String CHECK_SQL_QUERY = "SELECT 1";
		
	    boolean isOnline = false;
	    
	    try {
	    	@SuppressWarnings("unused")
			PreparedStatement statement = connection.prepareStatement(CHECK_SQL_QUERY);
	    	
	    	connection.close();	 
	        isOnline = true;
	    } catch (SQLException | NullPointerException e) {
	        // handle SQL error here!
	    }    
	    
	   return isOnline;
		
	}
	
	
	public boolean isPilightOnline() {
		
		String hostName = configProps.getPropertyValue("pilightws.hostname");
		int port = Integer.parseInt(configProps.getPropertyValue("pilightws.port"));
		
		boolean result = false;
		
		try {
	         
	        Socket socket = new Socket();
	        socket.setSoTimeout(200); //miliseg
	        socket.connect(new InetSocketAddress(hostName, port), 200);
	        
	        
	        if(socket != null)
	        {
	        	socket.close();	        	
	        
	        	//online
		        result = true;
	        }
	    }
	    catch (Exception e)
	    {	
	    	 //offline
	    	return false;
	    }
		
		return result;
	}
	
	
	public boolean isEmoncmsOnline() {
		
		boolean result = false;
		
		String url = configProps.getPropertyValue("emoncms.url");
		
		try {
            URL siteURL = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) siteURL.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
 
            int code = connection.getResponseCode();
            if (code == 200) {
            	
            	result = true;
            }
        } catch (Exception e) {
        	
        	result = false;
        }
        
        return result;
	}
	
	
	
	
	
}




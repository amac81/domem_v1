package pt.estgp.domem.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.SystemUtils;
import org.apache.log4j.Logger;

public class CommandLine
{
	private static final Logger logger = Logger.getLogger("");
	
	/**
	 * @param internetProtocolAddress The internet protocol address to ping
	 * @return True if the address is responsive, false otherwise
	 * @throws IOException
	 */
	public synchronized static boolean isReachable(String internetProtocolAddress) 
	{
		List<String> command = new ArrayList<String>();
		command.add("ping");

		if (SystemUtils.IS_OS_WINDOWS)
		{
			command.add("-n");
		}
		else if (SystemUtils.IS_OS_UNIX)
		{
			command.add("-c");
		}
		else
		{
			logger.error("SO nao suportado");
		}

		command.add("1");
		command.add(internetProtocolAddress);
		try{
			ProcessBuilder processBuilder = new ProcessBuilder(command);
			Process process = processBuilder.start();

			BufferedReader standardOutput = new BufferedReader(new InputStreamReader(process.getInputStream()));

			String outputLine;

			while ((outputLine = standardOutput.readLine()) != null)
			{
				// Picks up Windows and Unix unreachable hosts
				if (outputLine.toLowerCase().contains("destination host unreachable"))
				{
					return false;
				}
				
				if (outputLine.toLowerCase().contains("Request timed out"))
				{
					return false;
				}
				
				if (outputLine.toLowerCase().contains("100% packet loss"))
				{
					return false;
				}
				
				if (outputLine.toLowerCase().contains("100% loss"))
				{
					return false;
				}
								
			}
		}catch(Exception ex){
			logger.error("ocorreu excepcao: " + ex);
			return false;
		}

		return true;
	}
	
	
	public synchronized static Object[] pilightDaemonControl(String controlFlag) 
	{
		List<String> command = new ArrayList<String>();
		@SuppressWarnings("unused")
		int exitVal = 0;
		boolean commandProcessResult = false;
		List <String> outputTextList = new ArrayList<String>(); 
		
		Object[] resultArray = new Object[2];
			
		if (SystemUtils.IS_OS_UNIX)
		{
			command.add("service");
			command.add("pilight");
			command.add(controlFlag); //controlFlag = start||stop			
		}
		else
		{
			logger.error("SO nao suportado");
		}
	
		try{
			ProcessBuilder processBuilder = new ProcessBuilder(command);
			Process process = processBuilder.start();
			exitVal = process.waitFor();
			
			BufferedReader standardOutput = new BufferedReader(new InputStreamReader(process.getInputStream()));

			String outputLine;

			while ((outputLine = standardOutput.readLine()) != null)
			{
				outputTextList.add(outputLine);
				
				// Picks up Windows and Unix unreachable hosts
				if (outputLine.toLowerCase().contains("done"))
				{
				    commandProcessResult = true;
				}
			
			}
		}catch(Exception ex){
			logger.error("ocorreu excepcao: " + ex);
			
			commandProcessResult = false;
		}
			resultArray[0] = commandProcessResult;
			resultArray[1] = outputTextList;
		
			return resultArray;
	}
	
	
}
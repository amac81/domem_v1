package pt.estgp.domem.utils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

import org.apache.log4j.Logger;

public class ConfigProperties {
	
   private static final Logger logger = Logger.getLogger("");
   
   private HashMap<String, String> properties = new HashMap<String,String>();
   
   public ConfigProperties(String fileName)
   {
	   this.init(fileName);
   }
   
   public void init(String fileName){
	   
	   //Ler ficheiro de resources folder
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource(fileName).getFile());
		
		try (Scanner scanner = new Scanner(file)) {
			String props[] = null;
			
			while (scanner.hasNextLine()) {
 				 String line = scanner.nextLine().trim().replace("\\","");
 				 
 				 if(!line.startsWith("#") && !line.isEmpty()){ //ignorar comentarios e linhas vazias
 					 props = line.split("=");
 					 this.properties.put(props[0], props[1]);
				 } 
			}
			
			scanner.close();

		} catch (IOException e) {
			logger.error("Erro ao ler o ficheiro [maintenance.properties]." + e);
		}

   }
   
   public String getPropertyValue(String propertyName){	   	
	   return this.properties.get(propertyName);
   }
   
}
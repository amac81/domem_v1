package pt.estgp.domem.utils;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.log4j.Logger;

public class EncryptionTool {

	private static final Logger logger = Logger.getLogger("");
	private static final String key = "Bar12345Bar12345"; // 128 bit key

	/**
	 * Retorna uma string encriptada com algoritmo AES 
	 * e chave de 16 bytes (128bits)
	 *
	 * @param  strToEncrypt string para encriptar
	 * @param  key chave de 16 bytes
	 * @return string encriptada	 
	 *  
	 */
	public static String encrypt(String strToEncrypt){

		String result = null;		

		try{

			// Create key and cipher
			Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
			Cipher cipher = Cipher.getInstance("AES");

			// encrypt the text
			cipher.init(Cipher.ENCRYPT_MODE, aesKey);
			byte[] encrypted = cipher.doFinal(strToEncrypt.getBytes("UTF-8"));

			StringBuilder sb = new StringBuilder();
			for (byte b: encrypted) {
				sb.append((char)b);
			}

			// the encrypted String
			result = sb.toString();		

		}
		catch (Exception ex){ 
			logger.error("Ocorreu erro a encriptar [ " + strToEncrypt + " ]", ex);
		}

		return result;

	}
	
	/**
	 * Retorna uma string desencriptada com algoritmo AES 
	 * e chave de 16 bytes (128bits)
	 *
	 * @param  encryptedStr string para desencriptar
	 * @param  key chave de 16 bytes
	 * @return string desencriptada	 
	 *  
	 */
	public static String decrypt(String encryptedStr){
		String result = null;
		try{
			// Create key and cipher
			Key aesKey = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
			Cipher cipher = Cipher.getInstance("AES");

			// now convert the string to byte array
			// for decryption
			byte[] bb = new byte[encryptedStr.length()];
			for (int i=0; i<encryptedStr.length(); i++) {
				bb[i] = (byte) encryptedStr.charAt(i);
			}

			// decrypt the text
			cipher.init(Cipher.DECRYPT_MODE, aesKey);
			result = new String(cipher.doFinal(bb));
			
		}
		catch (Exception ex){
			logger.error("Ocorreu erro a desencriptar [" + encryptedStr + "]" + ex);
		}
		
		return result;
	}
}

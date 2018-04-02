package com.search.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tika.io.IOUtils;

/**
 * 
 * @author oonyimadu
 *
 */
public class WalkerUtils
{
	private static final Logger logger = LogManager.getLogger(WalkerUtils.class);
	
	public static String fileTimeToString(FileTime fileTime)
	{
		 SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
	     String dateCreatedString = df.format(fileTime.toMillis());
	     return dateCreatedString;
	}
	
	public static Date fileTimeToDate(FileTime fileTime) 
	{
		String ftime = fileTime.toString();
		
		return StringToDate(ftime);
	}
	
	public static Date StringToDate(String string) 
	{
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-ddH:m:sZ");
		Date date = null;
		try {
			date = formatter.parse(string);
		} catch (ParseException e) {
			
			date = new Date();
		}
		return date;
	}
	
	/**
	 * 
	 * @param attrs
	 * @return String fileType
	 */ 
	public static String checkDocumentType(BasicFileAttributes attrs )
	{
		
		String fileType = "UNKNOWN";
		if(attrs.isOther()== true)
		{
			fileType = "OTHER";
			
		}
		else if(attrs.isDirectory() == true)
		{
			fileType = "DIRECTORY";
		}
		else if(attrs.isRegularFile() == true)
		{
			fileType = "FILE";
		}
		else if(attrs.isSymbolicLink() == true)
		{
			fileType = "SYMBOLICLINK";
		}
		 
		return fileType;
	}
	
	public static String generateString() 
	{
        String uuid = UUID.randomUUID().toString();
        uuid = StringUtils.replace(uuid, "-", "");
        return uuid;
    }
	
	public static String convertFileNameToID(String fullPath)
	{
		if(StringUtils.isNotBlank(fullPath))
		{
			
			fullPath = StringUtils.replace(fullPath, "/", "_");
			fullPath = StringUtils.replace(fullPath, ":", "_");
			fullPath = StringUtils.replace(fullPath, "\\", "_");
			fullPath = StringUtils.replace(fullPath," ","_");
		}
		return fullPath;
	}
	
	/**
	 * 
	 * @param path
	 * @return String
	 */
	public static String generateMD5CheckSum(Path path)
	{
		
		String md5 = null;
	
		try(InputStream inputStream = Files.newInputStream(path))
		{
			
			md5 = DigestUtils.md5Hex(inputStream);
		} 
		catch (IOException e) 
		{
			
			System.err.println(e);
		}
		return md5;
	}
	
	/**
	 * Generates an MD5 checkSum for a document
	 * @param string
	 * @return
	 */
	public static String generateMD5CheckSum(String string) 
	{
		String checksum = "";
		try {
			MessageDigest md = MessageDigest.getInstance("SHA1");
			InputStream is = IOUtils.toInputStream(string);
			byte[] dataBytes = new byte[1024];

		    int nread = 0;
		    
		    while ((nread = is.read(dataBytes)) != -1)
		    {
		      md.update(dataBytes, 0, nread);
		    };

		    byte[] mdbytes = md.digest();
		    StringBuffer sb = new StringBuffer("");
		    for (int i = 0; i < mdbytes.length; i++) {
		    	sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
		    }

		    checksum = sb.toString();
		    
		} catch (NoSuchAlgorithmException |IOException e) {
			
			logger.error(e.getMessage());
			
		}
        return checksum;
    }
	
	/**
	public static void main(String[] args)
	{
		Path path = Paths.get("pom.xml");
		System.out.println(generateMD5CheckSum("pom.xml"));
		System.out.println(generateMD5CheckSum2(path));
		System.out.println(generateMD5CheckSum2(Paths.get("a1.txt")));
		System.out.println(generateMD5CheckSum2(Paths.get("a2.txt")));
	}
	*/
}	
	
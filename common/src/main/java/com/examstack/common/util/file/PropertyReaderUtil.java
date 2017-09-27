package com.examstack.common.util.file;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

public class PropertyReaderUtil {

	public static final String PROPERITIES_PATH="custome.properties";
	public static final String SYS_PROPERITIES_PATH="sys-config.properties";

	/**
	 * 读取properties并返回所有的pros对象
	 * @return
	 * @throws FileNotFoundException
	 */
	public static Properties getProperties() throws FileNotFoundException{
		
		InputStream inputStream = new FileInputStream(PropertyReaderUtil.class.getClassLoader().getResource(PROPERITIES_PATH).getPath());
		
		Properties pros = new Properties();
		try {
			pros.load(inputStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pros;
	}

	/**
	 * 读取custome.properties文件，根据key查找value并返回，如果没有查到，则返回null
	 * @param propertyStr
	 * @return strPropertyValue
	 * @throws FileNotFoundException
	 */
public static String getCustomPropertyByPropertyStr(String propertyStr) throws FileNotFoundException{
		

		Properties pros = new Properties();
		String strPropertyValue = null;
		
		try {
			pros = getProperties();
			strPropertyValue = pros.getProperty(propertyStr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return strPropertyValue;
	}
	
	public static Properties getSysProperties() throws FileNotFoundException{
		InputStream inputStream = new FileInputStream(PropertyReaderUtil.class.getClassLoader().getResource(SYS_PROPERITIES_PATH).getPath());
		
		Properties pros = new Properties();
		try {
			pros.load(inputStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pros;
	}
	
	public static Properties getProperties(String path) throws FileNotFoundException{
		InputStream inputStream = new FileInputStream(path);
		
		Properties pros = new Properties();
		try {
			pros.load(inputStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pros;
	}
	
	public static Properties getProperties(HttpServletRequest request) throws FileNotFoundException{
		
		String path = request.getSession()
				.getServletContext()
				.getRealPath(PROPERITIES_PATH);
		InputStream inputStream = new FileInputStream(path);
		
		Properties pros = new Properties();
		try {
			pros.load(inputStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pros;
	}
	
	public static Properties getSysProperties(HttpServletRequest request) throws FileNotFoundException{
		
		String path = request.getSession()
				.getServletContext()
				.getRealPath(SYS_PROPERITIES_PATH);
		InputStream inputStream = new FileInputStream(path);
		
		Properties pros = new Properties();
		try {
			pros.load(inputStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pros;
	}
}

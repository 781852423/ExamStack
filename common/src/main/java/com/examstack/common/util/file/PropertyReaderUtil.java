package com.examstack.common.util.file;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

public class PropertyReaderUtil {

	public static final String PROPERITIES_PATH="/custome.properties";
	public static final String SYS_PROPERITIES_PATH="/WEB-INF/classes/sys-config.properties";
	/*static
	{
		System.out.println("XXXX" + PropertyReaderUtil.class.getClassLoader().getResource("custome.properties")); //发现可以输出东西
		System.out.println("XXXX" + PropertyReaderUtil.class.getClassLoader().getResource("/custome.properties"));
	}*/
	public static Properties getProperties() throws FileNotFoundException{
		
		InputStream inputStream = new FileInputStream(PropertyReaderUtil.class.getClassLoader().getResource(PROPERITIES_PATH).getPath()); // 测试发现不行，必须设置绝对路径? 2017-8-2
		
		Properties pros = new Properties();
		try {
			pros.load(inputStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pros;
	}
	
	public static Properties getSysProperties() throws FileNotFoundException{
		InputStream inputStream = new FileInputStream(SYS_PROPERITIES_PATH);
		
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

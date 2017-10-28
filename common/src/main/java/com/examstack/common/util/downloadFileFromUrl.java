package com.examstack.common.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class downloadFileFromUrl {

	/**
	 * 从网络Url中下载文件
	 * @param urlStr
	 * @param fileName
	 * @param savePath
	 * @throws IOException
	 */
	public static void  downLoadFromUrl(String urlStr,String fileName,String savePath) throws IOException{
		URL url = new URL(urlStr);  
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();  
                //设置超时间为3秒
		conn.setConnectTimeout(3*1000);
		
		//防止屏蔽程序抓取而返回403错误
		conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

		//得到输入流
		InputStream inputStream = conn.getInputStream();  
		//获取自己数组
		byte[] getData = readInputStream(inputStream);    

		//文件保存位置
		File saveDir = new File(savePath);
		if(!saveDir.exists()){
			saveDir.mkdir();
		}
		File file = new File(saveDir+File.separator+fileName);    
		FileOutputStream fos = new FileOutputStream(file);     
		fos.write(getData); 
		if(fos!=null){
			fos.close();  
		}
		if(inputStream!=null){
			inputStream.close();
		}


		System.out.println("info:"+url+" download success"); 

	}



	/**
	 * 从输入流中获取字节数组
	 * @param inputStream
	 * @return
	 * @throws IOException
	 */
	public static  byte[] readInputStream(InputStream inputStream) throws IOException {  
		byte[] buffer = new byte[1024];  
		int len = 0;  
		ByteArrayOutputStream bos = new ByteArrayOutputStream();  
		while((len = inputStream.read(buffer)) != -1) {  
			bos.write(buffer, 0, len);  
		}  
		bos.close();  
		return bos.toByteArray();  
	}  

	public static void main(String[] args) {
		// http://115.231.73.218:8184/static/picture/wide/8-4.png wide
		// http://115.231.73.218:8184/static/picture/thin/2.png  thin
		String prexFixString = "http://115.231.73.218:8184/static/picture/thin/";
		String descinationFolderString = "C:/Users/jie/Desktop/主机管理/认知能力测试题目/atip_files/tupian/xijiagong";
		int i = 16;
		try{
			for(i=1; i < 33; i++)
			{		
				System.out.println(prexFixString+i + ".png");
				downLoadFromUrl(prexFixString+i + ".png",
						i + ".png",descinationFolderString);
				
				for(int j=1; j<=4; j++)
				{
					System.out.println(prexFixString+i +"-" + j + ".png");
					downLoadFromUrl(prexFixString+i +"-" + j + ".png",
							i +"-" + j + ".png",descinationFolderString);
				}
			}
			
		}catch (Exception e) {
			System.out.println("估计没有文件了，就停止");
			System.out.println(e.getMessage());
			System.exit(0);
		}
	}

}

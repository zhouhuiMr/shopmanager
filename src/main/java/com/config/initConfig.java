package com.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.Properties;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

@Component
public class initConfig implements CommandLineRunner {
	public final static String picAuthCodeName = "picauthcode";
	
	
	public static String TOKENKEY = "";
	public static String USERLOGINEXPIRES = "";

	@Override
	public void run(String... args) throws Exception {
		System.out.println("---------- project config init ----------");
		getFileAttribute();
	}
	
	private void getFileAttribute() {
		Properties properties = getPropertiesFile();
		if(properties != null) {
			TOKENKEY = properties.getProperty("tokenKey");
			USERLOGINEXPIRES = properties.getProperty("userLoginExpires");
		}
	}

	private Properties getPropertiesFile() {
		String dir = "";
		Properties properties = null;
		InputStream inputStream = null;
		try {
			dir = ResourceUtils.getURL("classpath:").getPath();
			String configFile = URLDecoder.decode(dir + "config.properties", "utf-8");
			File file = new File(configFile);
			if (file.exists()) {
				properties = new Properties();
				inputStream = new FileInputStream(file);
				properties.load(inputStream);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if(inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return properties;
	}
}

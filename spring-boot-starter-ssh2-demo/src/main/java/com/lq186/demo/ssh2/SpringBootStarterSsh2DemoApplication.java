package com.lq186.demo.ssh2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.lq186.spring.ssh2.SSH2Client;

@SpringBootApplication
public class SpringBootStarterSsh2DemoApplication {
	
	public static void main(String[] args) {
		
		ApplicationContext context = SpringApplication.run(SpringBootStarterSsh2DemoApplication.class, args);
		
		SSH2Client ssh2Client = context.getBean(SSH2Client.class);
		try {
			String result = ssh2Client.execCommand("ls", 30 * 1000);
			System.out.println("------------------------------------");
			System.out.println(result);
			System.out.println("------------------------------------");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

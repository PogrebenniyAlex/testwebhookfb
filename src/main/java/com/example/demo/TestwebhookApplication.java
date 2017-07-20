package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@SpringBootApplication
@Controller
public class TestwebhookApplication {


	@RequestMapping("/")
	@ResponseBody
	String home() {
		return "Hello World and kek!";
	}

	@RequestMapping("/webhook")
	@ResponseBody
	String webHookEndPoint(Map<String, String> request){
		String s = request.get("hub.challenge");
		return s;
	}

	public static void main(String[] args) {
		SpringApplication.run(TestwebhookApplication.class, args);
	}
}

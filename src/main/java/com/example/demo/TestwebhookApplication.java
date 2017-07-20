package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SpringBootApplication
@Controller
public class TestwebhookApplication {

	private List<Map<String, String>> mapList = new ArrayList<>();

	@RequestMapping("/")
	@ResponseBody
	String home() {
		return "Hello World and kek!";
	}

	@RequestMapping("/requests")
	@ResponseBody
	List<Map<String, String>> homerequests() {
		return mapList;
	}

	@RequestMapping("/webhook")
	@ResponseBody
	String webHookEndPoint(Map<String, String> request){
		mapList.add(request);
		String s = request.get("hub.challenge");
		return s;
	}

	public static void main(String[] args) {
		SpringApplication.run(TestwebhookApplication.class, args);
	}
}

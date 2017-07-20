package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@Controller
public class TestwebhookApplication {

	private List<String> mapList = new ArrayList<>();

	@RequestMapping(value = "/", method = RequestMethod.GET)
	@ResponseBody
	String home() {
		return "Hello World and kek!";
	}

	@RequestMapping(value = "/requests", method = RequestMethod.GET)
	@ResponseBody
	List<String> homerequests() {
		return mapList;
	}

	@RequestMapping(value = "/webhook", method = RequestMethod.GET)
	@ResponseBody
	String webHookEndPoint(@RequestParam(value = "hub_mode", required = false) String mode,
                           @RequestParam(value = "hub_challenge ", required = false) String challenge ,
                           @RequestParam(value = "hub_verify_token ", required = false) String verify_token ){
		mapList.add(challenge);
		/*String s = request.get("hub.challenge");
		return s;*/
		return challenge;
	}

	public static void main(String[] args) {
		SpringApplication.run(TestwebhookApplication.class, args);
	}
}

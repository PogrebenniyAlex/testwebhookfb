package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Enumeration;
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
	String webHookEndPoint(@RequestParam(value = "hub.mode", required = false) String mode,
                           @RequestParam(value = "hub.challenge", required = false) String challenge ,
                           @RequestParam(value = "hub.verify_token ", required = false) String verify_token, HttpServletRequest servletRequest){
		//mapList.add(servletRequest.getParameterMap());
        Enumeration<String> enumName = servletRequest.getParameterNames();
        while(enumName.hasMoreElements()){
            String name  = enumName.nextElement();
            mapList.add("ParamerName: "+name+"-->"+servletRequest.getParameter(name));
        }
        mapList.add(challenge);
		/*String s = request.get("hub.challenge");
		return s;*/
		return challenge;
	}

	public static void main(String[] args) {
		SpringApplication.run(TestwebhookApplication.class, args);
	}
}

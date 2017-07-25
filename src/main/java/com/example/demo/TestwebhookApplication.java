package com.example.demo;

import com.example.demo.webSocket.WebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

@SpringBootApplication
@Controller
public class TestwebhookApplication {

    @Autowired
    private WebSocketHandler webSocketHandler;

	private static List<String> mapList = new ArrayList<>();

    @RequestMapping(value = "/", method = RequestMethod.GET)
	@ResponseBody
	String home() {
		return "Hello World and kek!";
	}

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    String index() {
        return "index";
    }

	@RequestMapping(value = "/requests", method = RequestMethod.GET)
	@ResponseBody
	List<String> homerequests() {
		return mapList;
	}

    @RequestMapping(value = "/requests/empty", method = RequestMethod.GET)
    @ResponseBody
    String homerequestsempty() {
	    mapList = new ArrayList<>();
        return mapList.isEmpty() ? "Success" : "error";
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

    @RequestMapping(value = "/webhook", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    String webHookEndPointPost(@RequestBody LikesObj subscribeObject){

        StringBuffer s = new StringBuffer("");

        s.append("field : ").append(subscribeObject.getObject()).append("; ");
        s.append("value : { ");

        subscribeObject.getEntry().forEach(s1 -> s.append(s1).append("; "));

        s.append("}");

        mapList.add(s.toString());

        webSocketHandler.sendToClient(s.toString());

        return s.toString();
    }

	public static void main(String[] args) {
		SpringApplication.run(TestwebhookApplication.class, args);
	}

    private static class LikesObj{

        private String object;
        private List<Object> entry;

        public LikesObj() {
        }

        public String getObject() {
            return object;
        }

        public void setObject(String object) {
            this.object = object;
        }

        public List<Object> getEntry() {
            return entry;
        }

        public void setEntry(List<Object> entry) {
            this.entry = entry;
        }

        @Override
        public String toString() {
            return "LikesObj{" +
                    "object='" + object + '\'' +
                    '}';
        }

        public static List<String> getMapList() {
            return mapList;
        }

        public static void setMapList(List<String> mapList) {
            TestwebhookApplication.mapList = mapList;
        }

    }
}

package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

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

    @RequestMapping(value = "/webhook", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    String webHookEndPointPost(@RequestBody LikesObj subscribeObject){

        StringBuffer s = new StringBuffer("");

        /*s.append("{ ");

        Set<String> strings = subscribeObject.keySet();
        strings.forEach(keys -> {
            s.append(keys).append(" : ").append(subscribeObject.get(keys)).append("; ");
        });

        s.append(" }");*/

        s.append("field : ").append((String)subscribeObject.getObject()).append("; ");
        s.append("value : {");

        subscribeObject.getEntry().forEach((s1, o) -> {
            s.append(s1).append(" : ").append(o).append(";");
        });

        s.append("}");

       // s.append(subscribeObject.getObject());

        mapList.add(s.toString());

        return s.toString();
    }

    @RequestMapping(value = "/sub/{id}", method = RequestMethod.GET)
    @ResponseBody
    String sub(@PathVariable("id") String id){

        String url = "https://graph.facebook.com/v2.8/" + id + "/subscriptions?object=user&" +
                "callback_url=https://testwebhookfb.herokuapp.com/webhook" +
                "&fields=feed" +
                "&verify_token=MyVerifyString";

        mapList.add(url);
        RestTemplate restTemplate = new RestTemplate();
        Verify verify = null;
        try{
            verify = restTemplate.postForObject(url, null, Verify.class);
            mapList.add(verify.toString());
        }catch (Exception e){
            mapList.add(e.getMessage());
        }

        return verify.toString();


    }

	public static void main(String[] args) {
		SpringApplication.run(TestwebhookApplication.class, args);
	}


	private static class Verify{
        private String success;

        public String getSuccess() {
            return success;
        }

        public void setSuccess(String success) {
            this.success = success;
        }

        @Override
        public String toString() {
            return "Verify{" +
                    "success='" + success + '\'' +
                    '}';
        }
    }

    private static class Likes{
        private String field;


        @Override
        public String toString() {
            return "Likes{" +
                    "field='" + field + '\'' +
                    '}';
        }
    }

    private static class LikesObj{
        private String object;

        private Map<String, String> entry;

        public LikesObj() {
        }

        public String getObject() {
            return object;
        }

        public void setObject(String object) {
            this.object = object;
        }

        public Map<String, String> getEntry() {
            return entry;
        }

        public void setEntry(Map<String, String> entry) {
            this.entry = entry;
        }

        @Override
        public String toString() {
            return "LikesObj{" +
                    "object='" + object + '\'' +
                    '}';
        }
    }
}

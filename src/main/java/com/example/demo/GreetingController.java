package com.example.demo;

import com.example.demo.entity.Greeting;
import com.example.demo.entity.HelloMessage;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

/**
 * Created by Pogrebenniy Aleksandr on 24.07.2017.
 */

@Controller
public class GreetingController {


    //@MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) throws Exception {
        //Thread.sleep(3000); // simulated delay
        return new Greeting("Hello, " + message.getName() + "!");
    }

}
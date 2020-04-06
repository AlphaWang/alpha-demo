package com.alphawang.demo.alphawebsocket.server;

import java.security.Principal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
public class WebSocketController {
    
    @Autowired
    private SimpMessageSendingOperations messageOperations;

    /**
     * @SendToUser annotation:
     * 
     * allows us to send a message to a user destination via “/user/{sessionId}/…” 
     * rather than “/user/{user}/…“.
     */
    @MessageMapping("/message")
    @SendToUser("/queue/reply")
    public String processMessageFromClient(@Payload String msg, Principal principal) {
        log.info("get message: " + msg);

        String reply = "REP_" +  msg;
//        messageOperations.convertAndSendToUser(principal.getName(), "/queue/reply", reply);
        
        return reply;
    }

    @MessageMapping("/greeting")
    @SendToUser("/queue/reply")
    public String processMessageFromClientGreetring(@Payload String msg, Principal principal) {
        log.info("get message: " + msg);

        String reply = "GREETING_" +  msg;
//        messageOperations.convertAndSendToUser(principal.getName(), "/queue/reply", reply);

        return reply;
    }

    /**
     * @SendToUser points to “queue/errors” 
     * but the message will be sent to “/user/queue/errors“.
     */
    @MessageExceptionHandler
    @SendToUser("/queue/errors")
    public String handleException(Throwable exception) {
        return exception.getMessage();
    }
    
    @RequestMapping("/ping")
    @ResponseBody
    public String ping() {
        return "pong from web-socket";
    }

}

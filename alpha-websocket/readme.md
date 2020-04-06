

## Concepts
To communicate with the WebSocket server, 
the client has to initiate the WebSocket connection by sending an HTTP request to a server with an Upgrade header set properly:

```
GET ws://websocket.example.com/ HTTP/1.1
Origin: http://example.com
Connection: Upgrade
Host: websocket.example.com
Upgrade: websocket    
```

Please note that the WebSocket URLs use ws and wss schemes, the second one signifies secure WebSockets.

The server responds back by sending the Upgrade header in the response if WebSockets support is enabled.
```
HTTP/1.1 101 WebSocket Protocol Handshake
Date: Wed, 16 Oct 2013 10:07:34 GMT
Connection: Upgrade
Upgrade: WebSocket   
```
Once this process (also known as WebSocket handshake) is completed, 
the initial HTTP connection is replaced by WebSocket connection on top of same TCP/IP connection after which 
either parties can share data.


## Reference
https://www.baeldung.com/websockets-spring
https://github.com/eugenp/tutorials/blob/master/spring-websockets/

https://www.baeldung.com/spring-websockets-send-message-to-user
https://www.baeldung.com/spring-websockets-sendtouser

https://www.devglan.com/spring-boot/spring-boot-websocket-example 



CLIENT
https://www.baeldung.com/websockets-api-java-spring-client
http://www.programmingforliving.com/2013/08/jsr-356-java-api-for-websocket-client-api.html 


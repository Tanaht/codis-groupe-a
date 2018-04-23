package fr.istic.sit.codisgroupea.controller;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;

import java.util.concurrent.TimeUnit;

import static fr.istic.sit.codisgroupea.Constante.PORT_WEBSOCKET;
import static fr.istic.sit.codisgroupea.Constante.REGISTER_STOMP_END_POINTS;

//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DemandSocketControllerTests {

    private final String urlToWebSocket = "http://localhost:"+PORT_WEBSOCKET+"/"+REGISTER_STOMP_END_POINTS;

    @Before
    public void init(){
        /*WebSocketStompClient stompClient = new WebSocketStompClient(new SockJsClient(createTransportClient()));
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        StompSession stompSession = stompClient.connect(urlToWebSocket, new StompSessionHandlerAdapter() {
        }).get(1, TimeUnit.SECONDS);*/
    }
}

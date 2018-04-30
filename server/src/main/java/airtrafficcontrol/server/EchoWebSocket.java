package airtrafficcontrol.server;

import org.eclipse.jetty.websocket.api.*;
import org.eclipse.jetty.websocket.api.annotations.*;
import java.io.*;
import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.*;

@WebSocket
public class EchoWebSocket {

    // Store sessions if you want to, for example, broadcast a message to all users
    private static final Queue<Session> sessions = new ConcurrentLinkedQueue<>();
    private static List<Session> sess_array = new ArrayList<Session>();

    @OnWebSocketConnect
    public void connected(Session session) {

        sessions.add(session);
        sess_array.add(session);
    }

    @OnWebSocketClose
    public void closed(Session session, int statusCode, String reason) {
        try {
            session.getRemote().sendString("exit");
        } catch (IOException e) {
            e.printStackTrace();
        }
        sessions.remove(session);
    }

    @OnWebSocketMessage
    public void message(Session session, String message) throws IOException {
        System.out.println("Got: " + message);   // Print message
        int index = sess_array.indexOf(session);
        if(message.equals("Takeoff"))
        {
            System.out.println("Takeoff");
        }

        session.getRemote().sendString(index + ":" + message); // and send it back
        if (message.equals("exit"))
            closed(session,123,"bye");
    }



}
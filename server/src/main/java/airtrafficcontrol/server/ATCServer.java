package airtrafficcontrol.server;

import static spark.Spark.*;

public class ATCServer {
    public static void main(String[] args)
    {
        port(8080);

        webSocket("/echo", EchoWebSocket.class);
        //init(); // Needed if you don't define any HTTP routes after your WebSocket routes
        get("/hello", (req, res) -> "Hello World");
    }

}


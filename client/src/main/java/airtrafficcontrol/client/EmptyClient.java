package airtrafficcontrol.client;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;
import java.util.Scanner;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

public class EmptyClient extends WebSocketClient {

	public EmptyClient(URI serverUri, Draft draft) {
		super(serverUri, draft);
	}

	public EmptyClient(URI serverURI) {
		super(serverURI);
	}

	@Override
	public void onOpen(ServerHandshake handshakedata) {
		send("Hello, it is me. Akshat :)");
		System.out.println("new connection opened");
        Scanner reader = new Scanner(System.in);  // Reading from System.in
        System.out.println("Enter a number: ");
        String n = reader.nextLine(); // Scans the next token of the input as an int.
//once finished
        reader.close();
        send(n);
	}

	@Override
	public void onClose(int code, String reason, boolean remote) {
		System.out.println("closed with exit code " + code + " additional info: " + reason);
	}

    int count = 0;
	@Override
	public void onMessage(String message) {
		System.out.println("received message: " + message);

		if(message.equals("exit"))
            this.close();

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (count < 2)
            send("Hello, it is me. again:"+count);
		else if (count ==2)
		    send("exit");
        count ++;
	}


	@Override
	public void onError(Exception ex) {
		System.err.println("an error occurred:" + ex);
	}


	public static void main(String[] args) throws URISyntaxException {

		WebSocketClient client = new EmptyClient(new URI("ws://localhost:8080/echo"));
		client.connect();

	}
}
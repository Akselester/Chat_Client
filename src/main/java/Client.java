import lombok.extern.java.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

@Log
public class Client {
    public static final String IP = "localhost";
    public static final int PORT = 8080;
    private String username;

    public void start() {
        try {
            log.info("== Establishing connection to Server... ==");
            Socket socket = new Socket(IP, PORT);
            PrintWriter output = new PrintWriter(socket.getOutputStream());
            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

            if (socket.isConnected()) {
                log.info("=================== OK ===================");
                new Thread(() -> listen(socket)).start();
                registration(input, output);
            }

            while (true) {
                String message = input.readLine();
                if (message.equals("@exit")) {
                    return;
                }
                output.println(message);
                output.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void listen(Socket socket) {
        try {
            BufferedReader serverInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String incomingMessage;
            while ((incomingMessage = serverInput.readLine()) != null) {
                if (incomingMessage.startsWith(username)) {
                    incomingMessage = "YOU << " + incomingMessage.split(" >> ")[1];
                }
                System.out.println(incomingMessage);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void registration(BufferedReader input, PrintWriter output) throws IOException {
        System.out.println("Please, enter your login");
        String login = input.readLine();
        username = login;
        System.out.println("Please, enter your password");
        String password = input.readLine();
        output.println(login + " " + password);
        output.flush();
    }
}

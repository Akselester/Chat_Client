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

    public void start() {
        try {
            Socket socket = new Socket(IP, PORT);
            PrintWriter output = new PrintWriter(socket.getOutputStream());
            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

            if (socket.isConnected()) {
                new Thread(() -> listen(socket)).start();
                registration(input, output);
            }

            while (true) {
                String message = input.readLine();
                if (message.equals("@exit")) {
                    break;
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
                System.out.println(incomingMessage);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void registration(BufferedReader input, PrintWriter output) throws IOException {
        System.out.println("Please, enter your login");
        String login = input.readLine();
        System.out.println("Please, enter your password");
        String password = input.readLine();
        output.println("@reg" + " " + login + " " + password);
        output.flush();
    }
}

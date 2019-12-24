import lombok.extern.java.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

@Log
public class Main {
    public static final String IP = "localhost";
    public static final int PORT = 8080;

    public static void main(String[] args) {
        try {
            Socket socket = null;
            while (true) {
                if (socket == null) {
                    log.info("Start");
                    socket = new Socket(IP, PORT);

                    PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
                    printWriter.write("Registration");
                    printWriter.flush();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

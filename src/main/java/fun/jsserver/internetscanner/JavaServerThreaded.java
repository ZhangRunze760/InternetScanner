package fun.jsserver.internetscanner;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
public class JavaServerThreaded extends Thread {
    private final int port;

    public JavaServerThreaded(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            InternetScanner.LOGGER.info("InternetScanner已开启。经过映射主机25565端口的连接均会被上报。");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                new Thread(new ClientHandler(clientSocket)).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class Message {
        private String message;
        private boolean flag;

        public Message(String message, boolean flag) {
            this.message = message;
            this.flag = flag;
        }
    }

    private static class ClientHandler implements Runnable {
        private final Socket clientSocket;
        private final Gson gson = new Gson();

        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
                String jsonMessage = in.readLine();
                Message receivedMessage = gson.fromJson(jsonMessage, Message.class);
                if(receivedMessage.flag) InternetScanner.LOGGER.warn(String.format("新的IP尝试连接，地址： %s 。", receivedMessage.message));
                else InternetScanner.LOGGER.info(String.format("IP地址断开连接，地址：%s。", receivedMessage.message));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

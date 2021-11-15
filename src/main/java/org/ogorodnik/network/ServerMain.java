package org.ogorodnik.network;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerMain {

    public static void main(String[] args) throws IOException {
        Server server = new Server();
        int serverPort = server.setPort(3000);
        String webAppPath = server.setWebAppPath("/Users/alexone/IdeaProjects/WebServer/resources/webapp");
        try (ServerSocket serverSocket = server.start(serverPort)) {
            ServerLogic serverLogic = new ServerLogic();

            while (true) {
                try (Socket socket = serverLogic.accept(serverSocket);
                     BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                     BufferedOutputStream writer = new BufferedOutputStream(socket.getOutputStream())) {
                    String targetPath = serverLogic.readFromClient(reader);
                    String path = webAppPath + targetPath;
                    serverLogic.writeToClient(writer, path);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

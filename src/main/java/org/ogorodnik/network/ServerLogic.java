package org.ogorodnik.network;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

class ServerLogic {
    private final static String LINE_END = "\n";

    Socket accept(ServerSocket serverSocket) throws IOException {
        return serverSocket.accept();
    }

    String readFromClient(BufferedReader reader) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        while (true) {
            String lineFromClient = reader.readLine();
            stringBuilder.append(lineFromClient).append("\n");
            if (lineFromClient.isEmpty()) {
                break;
            }
        }
        String clientRequest = stringBuilder.toString();
        String[] subStrings = clientRequest.split(" ");
        return subStrings[1];
    }

    void writeToClient(BufferedOutputStream writer, String path) throws IOException {
        try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(path))) {
            writer.write("HTTP/1.1 200 OK".getBytes());
            writer.write(LINE_END.getBytes());
            writer.write(LINE_END.getBytes());

            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                writer.write(buffer, 0, bytesRead);
            }
            writer.flush();
        }
    }
}

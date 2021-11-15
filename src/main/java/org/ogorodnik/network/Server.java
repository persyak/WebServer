package org.ogorodnik.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

class Server {

    ServerSocket start(int port) throws IOException {
        return new ServerSocket(port);

    }

    String setWebAppPath(String webAppPath) {
        return webAppPath;
    }

    int setPort(int port) {
        return port;
    }
}

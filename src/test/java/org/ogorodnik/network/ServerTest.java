package org.ogorodnik.network;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.ServerSocket;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ServerTest {

    @BeforeEach
    public void before() throws IOException {
        new File("testInput").mkdir();
        new File("testInput/index.html").createNewFile();
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("testInput/index.html"))) {
            bufferedWriter.write("Test");
        }
    }

    @Test
    public void testServerStart() throws IOException {
        Server server = new Server();
        ServerSocket serverSocket = server.start(1111);
        assertEquals(1111, serverSocket.getLocalPort());
    }

    @Test
    public void testReadFromClient() throws IOException {
        ServerLogic serverLogic = new ServerLogic();
        BufferedReader bufferedReader =
                new BufferedReader(new InputStreamReader(new ByteArrayInputStream("GET testInput/index.html blablabla\n\n".getBytes())));
        assertEquals("testInput/index.html", serverLogic.readFromClient(bufferedReader));
    }

    @Test
    public void testWriteToClient() throws IOException {
        ServerLogic serverLogic = new ServerLogic();
        byte[] bytes = new byte[100];
        BufferedOutputStream writer = new BufferedOutputStream(new FileOutputStream(new File("testInput/test.txt")));
        serverLogic.writeToClient(writer, "testInput/index.html");
        BufferedReader bufferedReader = new BufferedReader(new FileReader("testInput/test.txt"));
        assertEquals("HTTP/1.1 200 OK", bufferedReader.readLine());
        assertEquals("", bufferedReader.readLine());
        assertEquals("Test", bufferedReader.readLine());
    }

    @AfterEach
    public void after() {
        new File("testInput/test.txt").delete();
        new File("testInput/index.html").delete();
        new File("testInput").delete();
    }
}

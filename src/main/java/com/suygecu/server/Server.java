package com.suygecu.server;

import com.suygecu.packet.InSorrow;
import com.suygecu.packet.PacketRegistry;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;

public class Server {
    private static final int PORT = 8081;

    public static void main(String[] args) {
        Server server = new Server();
        try {
            DataBaseConnection.getConnection();
            System.out.println("Connection to database established.");
            server.startServer();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void startServer() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started on port " + PORT + ". Waiting for clients...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getRemoteSocketAddress());
                new Thread(() -> handleClient(clientSocket)).start();
            }
        }
    }

    private void handleClient(Socket clientSocket) {
        try (DataInputStream inputClientStream = new DataInputStream(clientSocket.getInputStream());
             DataOutputStream outClientStream = new DataOutputStream(clientSocket.getOutputStream())) {

            while (!clientSocket.isClosed()) {
                int packetId = inputClientStream.readInt();
                System.out.println("Получен пакет с ID: " + packetId);

                try {
                    InSorrow packet = PacketRegistry.createPacket(packetId);
                    packet.readPacket(inputClientStream);  // Сначала читаем пакет
                    packet.processPacket();                // Затем обрабатываем пакет

                    int responsePacketId = packet.getPacketId();
                    packet.writePacket(outClientStream);         // Отправляем обратно содержимое пакета

                    outClientStream.flush();
                    System.out.println("Пакет с ID: " + responsePacketId + " обработан и отправлен клиенту");
                } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
                    System.err.println("Ошибка при обработке пакета: " + e.getMessage());
                    outClientStream.writeInt(-1);
                    outClientStream.flush();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Соединение с клиентом закрыто.");
        }
    }
}
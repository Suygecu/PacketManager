package com.suygecu.server;

import com.suygecu.packet.InSorrow;
import com.suygecu.packet.PacketRegistry;
import com.suygecu.packet.SideOnly;
import com.suygecu.util.Side;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Random;

public class Server {
    private static final int PORT = 8081;
    private static final boolean isRunningServer = true;
    private static final Random random = new Random();

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

            while (isRunningServer) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getRemoteSocketAddress());
                new Thread(() -> handleClient(clientSocket)).start();
            }
        }
    }

    private void handleClient(Socket clientSocket) {
        try (DataInputStream inputClientStream = new DataInputStream(clientSocket.getInputStream());
             DataOutputStream outClientStream = new DataOutputStream(clientSocket.getOutputStream())) {

            while (isRunningServer) {
                int packetId = inputClientStream.readInt(); // Читаю ID пакета
                System.out.println("Получен пакет с ID: " + packetId);
                System.out.println();

                try {
                    InSorrow packet = PacketRegistry.createPacket(packetId, Side.SERVER);
                    if (packet != null) {
                        System.out.println("Пакет с ID: " + packetId + " создан для стороны SERVER");

                        packet.readPacket(inputClientStream); // Читаю содержимое пакета
                        packet.processPacket(); // Обрабатываю пакет (записываю в базу данных или выполняю другие действия)
                        System.out.println("Пакет с ID: " + packetId + " обработан сервером.");

                        int responsePacketId = random.nextInt(3) + 1;
                        InSorrow responsePacket = PacketRegistry.createPacket(responsePacketId, Side.SERVER);
                        if (responsePacket != null) {
                            responsePacket.writePacket(outClientStream);
                            outClientStream.writeInt(responsePacketId);
                            outClientStream.flush();
                            System.out.println("Ответный пакет с ID: " + responsePacketId + " отправлен клиенту");
                            System.out.println();
                        } else {
                            System.out.println("Не удалось создать ответный пакет");
                        }
                    } else {
                        System.out.println("Не удалось создать пакет с ID: " + packetId + " для стороны SERVER");
                    }
                } catch (IOException | InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException e) {
                    System.err.println("Ошибка при обработке пакета: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Соединение с клиентом закрыто.");
        }
    }
}
package com.suygecu.client;

import com.suygecu.packet.InSorrow;
import com.suygecu.packet.PacketRegistry;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Client {
    private static final boolean isRunningClient = true;
    private static final int RETRY_DELAY_SEC = 2;
    private static final Random random = new Random();

    public static void main(String[] args) {
        try (Socket serverSocket = new Socket("127.0.0.1", 8081);
             DataInputStream inputServerStream = new DataInputStream(serverSocket.getInputStream());
             DataOutputStream outServerStream = new DataOutputStream(serverSocket.getOutputStream())) {

            System.out.println("Соединение с сервером установлено.");

            while (isRunningClient) {
                int packetId = random.nextInt(3) + 1;
                System.out.println("Отправка пакета с ID: " + packetId);

                try {
                    InSorrow packet = PacketRegistry.createPacket(packetId);


                    packet.writePacket(outServerStream);
                    outServerStream.flush();
                    System.out.println("Пакет с ID: " + packetId + " был отправлен на сервер");

                    while (inputServerStream.available() <= 0) {
                        TimeUnit.MILLISECONDS.sleep(100);
                    }

                    int receivedPacketId = inputServerStream.readInt();
                    System.out.println("Получен ответный пакет с ID от сервера: " + receivedPacketId);

                    InSorrow responsePacket = PacketRegistry.createPacket(receivedPacketId);
                    responsePacket.readPacket(inputServerStream);
                    responsePacket.processPacket();
                    System.out.println("Ответный пакет с ID: " + receivedPacketId + " был обработан клиентом.");
                } catch (IOException | InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                try {
                    TimeUnit.SECONDS.sleep(RETRY_DELAY_SEC);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }

        } catch (IOException e) {
            System.err.println("Не удалось установить соединение с сервером: " + e.getMessage());
        }
    }
}
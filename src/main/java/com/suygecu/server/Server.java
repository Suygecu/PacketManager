package com.suygecu;

import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.Socket;


public  class Server {

    private static boolean isRunningServer = true;
    static PacketRegistry packetRegistry = new PacketRegistry();

    public static void main(String[] args) throws IOException {
        serverSocket();
    }
    public static void serverSocket() throws IOException {
        ServerSocket serverSocket = new ServerSocket(8081);

        while (isRunningServer) {
            Socket clientSocket = serverSocket.accept();


        }
    }

    public static void handleClient(Socket clientSocket) throws IOException {
        try (DataInputStream inputClientStream = new DataInputStream(clientSocket.getInputStream());
             DataOutputStream outputStreamClient = new DataOutputStream(clientSocket.getOutputStream())) {
            PacketSorrow packet = new PacketSorrow(packetRegistry, inputClientStream.readInt());
            packet.readPacket(inputClientStream);

        } catch (InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException ex) {
            ex.printStackTrace();
        }

    }
        }

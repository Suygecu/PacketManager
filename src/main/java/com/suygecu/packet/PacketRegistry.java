package com.suygecu.packet;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;


public class PacketRegistry {
    private static final Map<Integer, Class<? extends InSorrow>> packetMap = new HashMap<>();

    static {
        registerPackets();
    }

    public static void registerPackets() {
        packetMap.put(3, PacketSorrow.class);
        packetMap.put(2, SaveDataPacket.class);
        packetMap.put(1, ResponsePacket.class);
    }

    public static InSorrow createPacket(int packetId) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<? extends InSorrow> packetClass = packetMap.get(packetId);
        if (packetClass != null) {
            return packetClass.getDeclaredConstructor().newInstance();
        } else {
            throw new NoSuchMethodException("Не найден пакет с номером " + packetId);
        }
    }
}

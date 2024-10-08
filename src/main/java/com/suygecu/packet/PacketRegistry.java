package com.suygecu;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class PacketRegistry {
    private static final Map<Integer, Class<? extends InSorrow>> packetMap = new HashMap<>();

    public static void  registerPacket(Class<? extends InSorrow> packetClass, int packetId) {
        packetMap.put(packetId, packetClass);
    }


    public InSorrow createPacket(int packetId) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<? extends InSorrow> packetClass = packetMap.get(packetId);
        if (packetClass != null) {
            return packetClass.getDeclaredConstructor().newInstance();
        }else {
            throw new NoSuchMethodException("Не найден пакет с номером " + packetId);
        }
    }




}

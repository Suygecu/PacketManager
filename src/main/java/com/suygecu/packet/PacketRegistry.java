package com.suygecu.packet;

import com.suygecu.util.Side;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;


public class PacketRegistry {
    private static final Map<Integer, PacketEntry> packetMap = new HashMap<>();

    static {
        registerPackets();
    }

    public static void registerPackets() {
        registerPacket(2, SaveDataPacket.class, Side.SERVER);
        registerPacket(1, ResponsePacket.class, Side.CLIENT);
        registerPacket(3, PacketSorrow.class, Side.SERVER);


    }
    public static void registerPacket(int packetId, Class<? extends InSorrow> packetClass, Side side) {
        packetMap.put(packetId, new PacketEntry(packetClass, side));
    }

    public static InSorrow createPacket(int packetId, Side receivingSide)
            throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        PacketEntry entry = packetMap.get(packetId);

        if (entry != null) {
            Class<? extends InSorrow> packetClass = entry.packetClass;

            if (packetClass.isAnnotationPresent(SideOnly.class)) {
                SideOnly sideOnly = packetClass.getAnnotation(SideOnly.class);
                if (sideOnly.value() != receivingSide) {
                    System.out.println("Пакет с ID " + packetId + " предназначен для стороны " + sideOnly.value() + ", текущая сторона: " + receivingSide);

                }
            }
            return packetClass.getDeclaredConstructor().newInstance();
        } else {
            throw new NoSuchMethodException("Пакет с ID " + packetId + " не зарегистрирован.");
        }
    }

    private static class PacketEntry {
        final Class<? extends InSorrow> packetClass;
        final Side side;

        PacketEntry(Class<? extends InSorrow> packetClass, Side side) {
            this.packetClass = packetClass;
            this.side = side;
        }
    }

}

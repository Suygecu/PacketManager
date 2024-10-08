package com.suygecu;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class PacketSorrow extends InSorrow {
    private final PacketRegistry packetRegistry;
    private final int packetId;

    public PacketSorrow(PacketRegistry packetRegistry, int packetId) {
        this.packetRegistry = packetRegistry;
        this.packetId = packetId;
    }
    @Override
    public void writePacket(DataOutput output) throws IOException {
        output.writeInt(packetId);
        writePacket(output);


    }

    @Override
    public void readPacket(DataInput input) throws IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        int packetId = input.readInt();
        InSorrow packet = packetRegistry.createPacket(packetId);
        packet.readPacket(input);
    }

    @Override
    public void processPacket() {
        return ;
    }

    @Override
    public int getPacketId() {
        return packetId;
    }
}

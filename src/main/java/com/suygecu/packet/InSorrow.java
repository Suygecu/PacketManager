package com.suygecu.packet;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public abstract class InSorrow {

    private int packetId;

    public int getPacketId() {
        return packetId;
    }

    public void setPacketId(int packetId) {
        this.packetId = packetId;
    }

    public abstract void writePacket(DataOutput output) throws IOException;

    public abstract void readPacket(DataInput input) throws IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException;

    public abstract void processPacket();


}

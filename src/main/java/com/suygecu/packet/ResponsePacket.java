package com.suygecu.packet;

import com.suygecu.server.DataBaseConnection;
import com.suygecu.util.Side;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ResponsePacket extends InSorrow {

    private String packetDescription;

    public ResponsePacket() {
        setPacketId(1);
    }

    public void setPacketDescription(String packetDescription) {
        this.packetDescription = packetDescription;
    }

    public String getPacketDescription() {
        return packetDescription;
    }

    @Override
    public void writePacket(DataOutput output) throws IOException {
        output.writeInt(getPacketId());
    }

    @Override
    public void readPacket(DataInput input) throws IOException {
        input.readInt();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void processPacket() {
        System.out.println("Это пакет клиента, ничего не делаю " );
    }
}
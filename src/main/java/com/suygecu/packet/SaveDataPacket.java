package com.suygecu.packet;

import com.suygecu.server.DataBaseConnection;
import com.suygecu.util.Side;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SaveDataPacket extends InSorrow {


    public SaveDataPacket() {
        setPacketId(2);
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
    @SideOnly(Side.SERVER)
    public void processPacket() {
        String processedDescription = "Пакет: " + getClass().getName() + " ID: " + getPacketId();
        try (PreparedStatement statement = DataBaseConnection.getConnection().prepareStatement(
                "INSERT INTO packets (packet_id, packet_description) VALUES (?, ?)")) {
            statement.setInt(1, getPacketId());
            statement.setString(2, processedDescription);
            statement.executeUpdate();
            System.out.println("Packet " + processedDescription + " was saved");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
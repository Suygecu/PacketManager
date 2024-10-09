package com.suygecu.packet;

import com.suygecu.server.DataBaseConnection;

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
        output.writeUTF(packetDescription != null ? packetDescription : "Undefined Packet Description");
        System.out.println("Packet " + packetDescription + " was sent");
    }

    @Override
    public void readPacket(DataInput input) throws IOException {
        packetDescription = input.readUTF();
        System.out.println("Packet " + packetDescription + " was received");
    }

    @Override
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
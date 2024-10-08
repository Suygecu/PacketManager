package com.suygecu;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class SaveDataPacket extends InSorrow {

   private String data;

    public SaveDataPacket(String data) {
        this.data = data;
    }

    public SaveDataPacket() {

    }

    @Override
    public void writePacket(DataOutput output) throws IOException {
        output.writeInt(getPacketId()); // Записываем ID пакета
        output.writeUTF(data);
    }

    @Override
    public void readPacket(DataInput input) throws IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        this.data = input.readUTF();
    }

    @Override
    public void processPacket() {

    }

    @Override
    public int getPacketId() {
        return 1;
    }
}
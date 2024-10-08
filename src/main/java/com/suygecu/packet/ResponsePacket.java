package com.suygecu;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class ResponsePacket extends InSorrow {
    private String response;

    public ResponsePacket(String response) {
        this.response = response;
    }

    public ResponsePacket( ) {

    }



    @Override
    public void writePacket(DataOutput output) throws IOException {
        output.writeInt(getPacketId()); // Записываем ID пакета
        output.writeUTF(response);
    }

    @Override
    public void readPacket(DataInput input) throws IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

    }

    @Override
    public void processPacket() {

    }

    @Override
    public int getPacketId() {
        return 0;
    }
}

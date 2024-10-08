package com.suygecu;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public abstract class InSorrow {

    public abstract void writePacket(DataOutput output) throws IOException;

    public abstract void readPacket(DataInput input) throws IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException;

    public abstract void processPacket();

    public abstract int getPacketId();

}

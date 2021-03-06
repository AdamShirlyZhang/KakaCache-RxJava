package com.im4j.kakacache.rxjava.core.disk.converter;

import com.im4j.kakacache.rxjava.common.utils.L;
import com.im4j.kakacache.rxjava.common.utils.Utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;

/**
 * 序列化-数据转换器
 * @version alafighting 2016-07
 */
public class SerializableDiskConverter implements IDiskConverter {

    @Override
    public Object load(InputStream source, Type type) {
        Object value = null;
        ObjectInputStream oin = null;
        try {
            oin = new ObjectInputStream(source);
            value = oin.readObject();
        } catch (IOException | ClassNotFoundException e) {
            L.log(e);
        } finally {
            Utils.close(oin);
        }
        return value;
    }

    @Override
    public boolean writer(OutputStream sink, Object data) {
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(sink);
            oos.writeObject(data);
            oos.flush(); //缓冲流
            return true;
        } catch (IOException e) {
            L.log(e);
            return false;
        } finally {
            Utils.close(oos);
        }
    }

}

package com.xyf.platform.base.cache;


import com.jfinal.plugin.redis.serializer.ISerializer;
import net.sf.ehcache.CacheException;
import org.nustaq.serialization.FSTObjectInput;
import org.nustaq.serialization.FSTObjectOutput;
import redis.clients.util.SafeEncoder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * fst序列化：
 * 1、jfinal自带了fst序列化，但是jfinal自带的版本较高
 * 同时j2cache使用的fst序列化是1.x的，为了两者兼容。
 * <p>
 * 2、java序列化，j2cache保存biginteger会出现 invalid stream header: EFBFBDEF 的问题；
 * fst序列化没问题，但是又不兼容jfinal自带的redisPlugin fst序列化
 * j2cache的fst和redisPlugin是不同的版本。此序列化是为了兼容。
 */
public class LeFstSerializer implements ISerializer {

    public static final ISerializer me = new LeFstSerializer();

    @Override
    public byte[] keyToBytes(String key) {
        return SafeEncoder.encode(key);
    }

    @Override
    public String keyFromBytes(byte[] bytes) {
        return SafeEncoder.encode(bytes);
    }

    @Override
    public byte[] fieldToBytes(Object field) {
        return valueToBytes(field);
    }

    @Override
    public Object fieldFromBytes(byte[] bytes) {
        return valueFromBytes(bytes);
    }

    @Override
    public byte[] valueToBytes(Object obj) {
        ByteArrayOutputStream out = null;
        FSTObjectOutput fout = null;
        try {
            out = new ByteArrayOutputStream();
            fout = new FSTObjectOutput(out);
            fout.writeObject(obj);
            return out.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fout != null)
                try {
                    fout.close();
                } catch (IOException e) {
                }
        }
        return null;
    }


    @Override
    public Object valueFromBytes(byte[] bytes) {
        if (bytes == null || bytes.length == 0)
            return null;
        FSTObjectInput in = null;
        try {
            in = new FSTObjectInput(new ByteArrayInputStream(bytes));
            return in.readObject();
        } catch (ClassNotFoundException e) {
            throw new CacheException(e);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null)
                try {
                    in.close();
                } catch (IOException e) {
                }
        }
        return null;
    }
}


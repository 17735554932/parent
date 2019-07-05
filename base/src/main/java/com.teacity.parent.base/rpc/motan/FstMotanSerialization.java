package com.teacity.parent.base.rpc.motan;

import com.weibo.api.motan.codec.Serialization;
import com.weibo.api.motan.common.URLParamType;
import com.weibo.api.motan.core.extension.ExtensionLoader;
import com.weibo.api.motan.core.extension.SpiMeta;
import org.nustaq.serialization.FSTConfiguration;

import java.io.IOException;

/**
 * Motan 序列化，替代hessian，有 BigDecimal BUG
 * @author wangyq
 *
 */
@SpiMeta(name = "fst")
public class FstMotanSerialization implements Serialization {

    private static FSTConfiguration fst = FSTConfiguration.createDefaultConfiguration();

    @Override
    public byte[] serialize(Object obj) throws IOException {
        if (obj == null) {
            return null;
        }
        return fst.asByteArray(obj);
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clz) throws IOException {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        return (T) fst.asObject(bytes);
    }

    @Override
    public byte[] serializeMulti(Object[] data) throws IOException {
        return serialize(data);
    }

    @Override
    public Object[] deserializeMulti(byte[] data, Class<?>[] classes) throws IOException {
        return (Object[]) fst.asObject(data);
    }

    @Override
    public int getSerializationNumber() {
        return 0;
    }
}

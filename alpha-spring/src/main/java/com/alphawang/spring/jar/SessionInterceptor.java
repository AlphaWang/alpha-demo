package com.alphawang.spring.jar;

import com.alphawang.spring.jar.SessionHolder;
import com.google.common.base.Charsets;
import com.google.common.primitives.Longs;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.ByteBuffer;
import java.util.UUID;
import java.util.zip.CRC32;

@Slf4j
@Component
public class SessionInterceptor implements HandlerInterceptor {

    /**
     * is SubSessionHolder
     */
    @Autowired
    private SessionHolder sessionHolder;

    private Hex hexCoder = new Hex();
    

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String sid =  generate(UUID.randomUUID());

        if (sessionHolder != null) {
            sessionHolder.setCartSessionId(sid);
            log.warn("sessionHolder from interceptor: {}", sessionHolder.hashCode());
        } else {
            log.error("sessionHolder from interceptor is NULL !!!!!!!!!!");
        }
        
        return true;
    }

    String generate(UUID uuid) {
        int capacity = 8 * 2;

        ByteBuffer data = ByteBuffer.allocate(capacity);
        data.putLong(uuid.getMostSignificantBits());
        data.putLong(uuid.getLeastSignificantBits());
        data.flip();
        byte[] checksum = crc32(data.array());

        StringBuilder result = new StringBuilder();
        String mostSignificantBitsStr = longToHexString(uuid.getMostSignificantBits());
        result.append(mostSignificantBitsStr);
        String leastSignificantBitsStr = longToHexString(uuid.getLeastSignificantBits());
        result.append(leastSignificantBitsStr);

        log.debug("UUID {} mostSignificantBitsHexStr : {}, leastSignificantBitsHexStr : {}", uuid, mostSignificantBitsStr, leastSignificantBitsStr);

        result.append(new String(hexCoder.encode(checksum), Charsets.UTF_8));
        return result.toString();
    }

    byte[] crc32(byte[] data) {
        try {
            int length = 4;
            int srcPos = 4;

            CRC32 checksum = new CRC32();
            checksum.update(data, 0, data.length);
            byte[] b64 = Longs.toByteArray(checksum.getValue());
            byte[] b32 = new byte[length];
            System.arraycopy(b64, srcPos, b32, 0, length);
            return b32;
        } catch (Exception e) {
            throw new RuntimeException("Error while calculating checksum", e);
        }
    }

    String longToHexString(Long value) {
        String hex = String.format("%016x", value);
        log.debug("Hex for {} is [{}].", value, hex);
        return hex;
    }
    
}

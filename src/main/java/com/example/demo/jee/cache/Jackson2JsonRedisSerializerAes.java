package com.example.demo.jee.cache;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper; 
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException; 
import org.springframework.util.Assert; 

import java.nio.charset.Charset; 

/** 
* Created by gjp on 2017/10/11. 
* 
*/ 
public class Jackson2JsonRedisSerializerAes<T> implements RedisSerializer<T> { 

    private static final String PWD = "123456789asdfghjkl"; 

    public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8"); 
    private final JavaType javaType; 
    private ObjectMapper objectMapper = new ObjectMapper(); 

    public Jackson2JsonRedisSerializerAes(Class<T> type) { 
        this.javaType = this.getJavaType(type); 
    } 

    public Jackson2JsonRedisSerializerAes(JavaType javaType) { 
        this.javaType = javaType; 
    } 

    public T deserialize(byte[] bytes) throws SerializationException { 
        if(null == bytes || bytes.length ==0) { 
            return null; 
        } else { 
            try { 
               byte[] result = AESUtil.decryptByte(bytes,PWD.getBytes(DEFAULT_CHARSET)); 

                return this.objectMapper.readValue(result, 0, bytes.length, this.javaType); 
            } catch (Exception var3) { 
                throw new SerializationException("Could not read JSON: " + var3.getMessage(), var3); 
            } 
        } 
    } 

    public byte[] serialize(Object t) throws SerializationException { 
        if(t == null) { 
            return new byte[0]; 
        } else { 
            try { 
                 byte[] temp = this.objectMapper.writeValueAsBytes(t); 

                return AESUtil.encryptbyte(temp,PWD.getBytes(DEFAULT_CHARSET)); 
                //return this.objectMapper.writeValueAsBytes(t); 
            } catch (Exception var3) { 
                throw new SerializationException("Could not write JSON: " + var3.getMessage(), var3); 
            } 
        } 
    } 

    public void setObjectMapper(ObjectMapper objectMapper) { 
        Assert.notNull(objectMapper, "'objectMapper' must not be null"); 
        this.objectMapper = objectMapper; 
    } 

    protected JavaType getJavaType(Class<?> clazz) { 
        return TypeFactory.defaultInstance().constructType(clazz); 
    } 
} 
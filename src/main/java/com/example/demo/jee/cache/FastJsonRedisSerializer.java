package com.example.demo.jee.cache;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.util.Assert;

import java.io.IOException;
import java.nio.charset.Charset;

public class FastJsonRedisSerializer<T> implements RedisSerializer<T> {
  
    public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
    private ObjectMapper objectMapper = new ObjectMapper();
    private final JavaType javaType;
    private Class<T> clazz;  
  
    public FastJsonRedisSerializer(Class<T> clazz) {  
        super();  
        this.javaType = this.getJavaType(clazz);
    }

    protected JavaType getJavaType(Class<?> clazz) {
        return TypeFactory.defaultInstance().constructType(clazz);
    }
    @Override  
    public byte[] serialize(T t) throws SerializationException {
        if (null == t) {  
            return new byte[0];  
        }  
        return JSON.toJSONString(t, SerializerFeature.WriteClassName).getBytes(DEFAULT_CHARSET);
    }  
  
    @Override  
    public T deserialize(byte[] bytes) throws SerializationException {  
        if (null == bytes || bytes.length <= 0) {  
            return null;  
        }  
        String str = new String(bytes, DEFAULT_CHARSET);
        try {
            return this.objectMapper.readValue(bytes, 0, bytes.length, this.javaType);
        } catch (IOException e) {
            throw new SerializationException("Could not read JSON: " + e.getMessage(), e);
        }
    }

    public void setObjectMapper(ObjectMapper objectMapper) {
        Assert.notNull(objectMapper, "'objectMapper' must not be null");
        this.objectMapper = objectMapper;
    }
} 
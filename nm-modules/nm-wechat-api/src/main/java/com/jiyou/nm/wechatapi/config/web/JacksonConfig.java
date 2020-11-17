package com.jiyou.nm.wechatapi.config.web;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.io.IOException;

/**
 * @author : cpc
 * @date : 2017-10-30
 **/
@Configuration
public class JacksonConfig {
    @Bean
    @Primary
    @ConditionalOnMissingBean(ObjectMapper.class)
    public ObjectMapper jacksonObjectMapper(Jackson2ObjectMapperBuilder builder) {
        ObjectMapper objectMapper = builder.createXmlMapper(false).build();
        objectMapper.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>() {
            @Override
            public void serialize(Object value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
                if(value==null){
                    if (value instanceof Integer) {
                        jsonGenerator.writeString("0");
                    } else if (value instanceof String) {
                        jsonGenerator.writeString("");
                    } else if (value instanceof Double) {
                        jsonGenerator.writeString("0.0");
                    } else  {
                        jsonGenerator.writeString("");
                    }

                }else{
                    jsonGenerator.writeObject(value);
                }
            }
        });
        return objectMapper;
    }
}

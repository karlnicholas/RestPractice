package com.elderbyte.spring.cloud.bootstrap.feign;

import feign.Response;
import feign.Util;
import feign.codec.Decoder;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Optional;

/**
 * Decodes 404 Responses into empty optional.
 * If no Optional is defined, throws a HttpClientErrorException.
 */
public class OptionalFrom404Decoder implements Decoder {

    private final Decoder delegate;
    private HttpStatusExceptionBuilder exceptionBuilder = new HttpStatusExceptionBuilder();


    public OptionalFrom404Decoder(Decoder delegate){
        if(delegate == null) throw new IllegalArgumentException("delegate was null!");
        this.delegate = delegate;
    }

    @Override
    public Object decode(Response response, Type type) throws IOException {
        if(isOptional(type)){
            if (response.status() == 404){
                return Optional.empty();
            }else{
                Type enclosedType = Util.resolveLastTypeParameter(type, Optional.class);
                return Optional.of(delegate.decode(response, enclosedType));
            }
        }else{
           if(response.status() == 404){
               throw exceptionBuilder.buildException(response)
                       .map(e -> (RuntimeException)e)
                       .orElse(new IllegalStateException("exceptionBuilder failed -> should never happen! Response-Status: " + response.status()));
           }
        }
        return delegate.decode(response, type);
    }

    static boolean isOptional(Type type) {
        if (!(type instanceof ParameterizedType)) return false;
        ParameterizedType parameterizedType = (ParameterizedType) type;
        return parameterizedType.getRawType().equals(Optional.class);
    }
}
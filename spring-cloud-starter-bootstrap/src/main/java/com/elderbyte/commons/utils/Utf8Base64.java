package com.elderbyte.commons.utils;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

public class Utf8Base64 {

    public static String decodeUtf8(String base64){
        try {
            return new String(Base64.getDecoder().decode(base64), "UTF8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String encodeUtf8(String raw){
        try {
            return new String(Base64.getEncoder().encode(raw.getBytes("UTF8")));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

}

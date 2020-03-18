package com.qf.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


import java.lang.reflect.Type;
/**
 * @program: hz-shop
 * @description:
 * @author: Mr.jiang
 * @create: 2020-03-17 14:59
 **/



    public class JsonUtil {
    private static Gson gson = (new GsonBuilder()).enableComplexMapKeySerialization().serializeNulls().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    public JsonUtil() {
    }

    public static String toJson(Object obj) {
        return gson.toJson(obj);
    }

    public static <T> T fromJson(String json, Class<T> classOfT) {
        return gson.fromJson(json, classOfT);
    }

    public static JsonObject fromJson(String json) {
        return (new JsonParser()).parse(json).getAsJsonObject();
    }

    public static <T> T fromJson(String json, Type type) {
        return gson.fromJson(json, type);
    }
}

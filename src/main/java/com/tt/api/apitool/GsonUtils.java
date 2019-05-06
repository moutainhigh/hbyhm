/*
 * @Description: file content
 * @Author: tt
 * @Date: 2019-02-12 17:27:33
 * @LastEditTime: 2019-02-12 17:53:44
 * @LastEditors: tt
 */
package com.tt.api.apitool;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * Json工具类.
 */
public class GsonUtils {
  private static Gson gson = new GsonBuilder().create();

  public static String toJson(Object value) {
    return gson.toJson(value);
  }

  public static <T> T fromJson(String json, Class<T> classOfT) throws JsonParseException {
    return gson.fromJson(json, classOfT);
  }

  @SuppressWarnings("unchecked")
  public static <T> T fromJson(String json, Type typeOfT) throws JsonParseException {
    return (T) gson.fromJson(json, typeOfT);
  }
}

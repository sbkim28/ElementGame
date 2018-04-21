package com.ignited.element.data;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Properties;

public class DataManager {

    public static String getPath(String name){
        return DataManager.class.getResource(name).getPath();
        //return "D:\\sbkim28\\programs\\java\\workspace\\XElement\\src\\com\\ignited\\element\\data\\ElementList_en.json";
    }

    public static InputStream getInputStream(String name){
        return DataManager.class.getResourceAsStream(name);
    }

    public static String read(String name){
        return read(name, Charset.defaultCharset().name());
    }

    public static String read(String name, String charset){
        StringBuilder builder = new StringBuilder();
        try(InputStreamReader reader = new InputStreamReader(getInputStream(name))){
            char[] buf = new char[512];
            int len;
            while ((len = reader.read(buf)) != -1){
                builder.append(new String(buf, 0, len));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

    public static Properties loadProp(String name) {
        return loadProp(name, Charset.defaultCharset().name());
    }

    public static Properties loadProp(String name, String charset){
        if(name.lastIndexOf(".properties") != 9){
            name = name + ".properties";
        }
        Properties properties = new Properties();
        try (InputStreamReader reader = new InputStreamReader(getInputStream(name), charset)) {
            properties.load(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

}

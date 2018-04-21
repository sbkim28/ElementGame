package com.ignited.element.element;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.ignited.element.data.DataManager;

import java.io.*;
import java.util.*;

public class ElementManager {

    private static final Map<String, List<Element>> elements;

    static {
        elements = new HashMap<>();
    }

    private static List<Element> initList(String key){
        JsonArray array;

        try (InputStreamReader reader = new InputStreamReader(DataManager.getInputStream("ElementList_" + key + ".json")) ){
            array = new JsonParser().parse(reader).getAsJsonArray();
        } catch (IOException e) {
            return null;
        }
        Element[] tmp = new Element[array.size()];
        Gson gson = new Gson();
        for (int i = 0; i < array.size();++i){
            tmp[i] = gson.fromJson(array.get(i), Element.class);
        }
        return new UnchangableList<>(tmp);
    }

    public static List<Element> get(Locale locale){
        String lang = locale.getLanguage();
        if(elements.containsKey(lang)){
            return elements.get(lang);
        }else {
            List<Element> tmp = initList(lang);
            if(tmp != null){
                elements.put(lang, tmp);
            }
            return tmp;
        }
    }

    private static class UnchangableList<E> extends AbstractList<E> {

        private final E[] e;

        public UnchangableList(E[] e) {
            this.e = Arrays.copyOf(e, e.length);
        }

        @Override
        public E get(int index) {
            return e[index];
        }

        @Override
        public int size() {
            return e.length;
        }

    }
}

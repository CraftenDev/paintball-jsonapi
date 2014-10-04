package de.craften.plugins.paintballjsonapi.jsonize;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Jsonize {
    public static Collection<Object> collection(Collection<? extends Jsonizable> collection) {
        Collection<Object> jsonizedCollection = new ArrayList<>(collection.size());
        for (Jsonizable j : collection)
            jsonizedCollection.add(j.jsonize());
        return jsonizedCollection;
    }

    public static <T extends Jsonizable> Map<String, Object> map(Collection<T> collection, KeyRetreiver<T> kr) {
        Map<String, Object> jsonizedMap = new HashMap<>();
        for (T entry : collection) {
            jsonizedMap.put(kr.getKey(entry), entry.jsonize());
        }
        return jsonizedMap;
    }

    public static <T extends Jsonizable> Map<String, Object> map(HashMap<String, T> map) {
        Map<String, Object> jsonizedMap = new HashMap<>();
        for (Map.Entry<String, T> entry : map.entrySet()) {
            jsonizedMap.put(entry.getKey(), entry.getValue().jsonize());
        }
        return jsonizedMap;
    }

    public static interface KeyRetreiver<T> {
        public String getKey(T value);
    }
}

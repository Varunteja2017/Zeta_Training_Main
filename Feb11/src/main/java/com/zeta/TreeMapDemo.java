package com.zeta;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class TreeMapDemo {
    public static void main(String[] args) {
//		basics();
        customKey();
    }

    private static void customKey() {
        Map<MyKey, String> map = new TreeMap<>();
        MyKey key = new MyKey(1);
        MyKey key1 = new MyKey((int)1.0);
        map.put(key, "value1");
        System.out.println(map.get(key1));

    }

    private static void basics() {
        Map<String, String> map = new HashMap<>();
        map.put("1", "value1"); // entry
        System.out.println(map.put("1", "value2")); // entry
        System.out.println(map.put(null, "value2")); // entry
        map.remove(null);
        System.out.println(map);
        map.forEach((key, value) -> System.out.println(key));
    }
}

package com.zeta;

import java.util.HashMap;
import java.util.Map;
class MyKey{

}
public class MapDemo1 {
    public static void main(String[] args) {
        basics();
        customKey();
    }

    private static void customKey() {
        Map<MyKey, String> map=new HashMap<>();
        MyKey key1= new MyKey();
        MyKey key2= new MyKey();
        map.put(key1, "Value1");
        map.put(key2,"Value2");
        System.out.println(map.get(key2));

    }

    private static void basics() {
        Map<String, String> map=new HashMap<>();
        map.put("1","Value1");
        map.put(null,"Value2");
        System.out.println(map);
        map.remove(null);
        map.forEach((key,value)-> System.out.println(key));
    }
}

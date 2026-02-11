package com.zeta;


public class MyKey implements Comparable<MyKey> {
    int id;

    public MyKey(int i) {
        this.id = i;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        MyKey other = (MyKey) obj;
        return this.id == other.id;
    }

    @Override
    public int compareTo(MyKey	otherKey) {
        return this.id - otherKey.id;
    }
}
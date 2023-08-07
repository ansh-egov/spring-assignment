package com.example.freshers.Assignment.Reflection;

import java.util.ArrayList;

// We can define a generic class by using a general type T and use any type of data type in place of T
public class List<T> {
    private ArrayList<T> list;

    public int getLength(){
        return list.size();
    }
    public void addVar(T e){
        list.add(e);
    }
}

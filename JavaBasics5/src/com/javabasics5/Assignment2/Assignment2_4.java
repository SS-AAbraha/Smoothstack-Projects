package com.javabasics5.Assignment2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Assignment2_4 {
    public static void main(String[] args){
        List<String> strList = new ArrayList<>();
        strList.add("Adx");
        strList.add("XAzXxZ");
        strList.add("ZaxX");
        strList.add("ABCD");
        strList.add("ABxCxDX");

        System.out.println(removeAllX(strList));
    }

    public static List<String> removeAllX(List<String> strList){
        List<String> returnList = strList.stream().map(x -> {
            StringBuilder noXStr = new StringBuilder("");
            for(int i=0;i<x.length();i++){
                if(x.toLowerCase().charAt(i) != 'x'){
                    noXStr.append(x.charAt(i));
                }
            }
            return noXStr.toString();
        }).collect(Collectors.toList());

        returnList.forEach(x -> {
            System.out.println(x);
        });

        return returnList;
    }
}

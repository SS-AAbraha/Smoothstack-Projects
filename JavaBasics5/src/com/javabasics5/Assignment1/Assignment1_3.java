package com.javabasics5.Assignment1;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Assignment1_3 {

    public static void main(String[] args){

        String[] strArray = {"Ada","Eve","Bill","Alice", "ali"};
        List<String> strList = Arrays.asList(strArray);

        List<String> customStringList = function(strList);
        customStringList.forEach(l -> System.out.println(l));
    }

    public static List<String> function(List<String> strList){
        List<String> returnList = strList.stream().filter(l -> (l.charAt(0) == 'a' && l.length() == 3)).collect(Collectors.toList());
        return returnList;
    }
}

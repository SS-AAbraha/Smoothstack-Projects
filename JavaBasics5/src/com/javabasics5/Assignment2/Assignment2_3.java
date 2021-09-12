package com.javabasics5.Assignment2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Assignment2_3 {

    public static void main(String[] args){
        Integer[] intArray = {1,2,3,5};
        List<Integer> intList = Arrays.asList(intArray);

        System.out.println(doubleIntegers(intList));
    }

    public static List<Integer> doubleIntegers(List<Integer> intList){
        List<Integer> returnList = new ArrayList<>();
        intList.forEach(x -> returnList.add(x*2));

        return returnList;
    }
}

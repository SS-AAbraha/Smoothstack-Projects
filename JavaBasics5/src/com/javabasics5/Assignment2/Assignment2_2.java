package com.javabasics5.Assignment2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Assignment2_2 {

    public static void main(String[] args) {
        List<Integer> intList = new ArrayList<>();
        intList.add(1);
        intList.add(22);
        intList.add(93);
        intList.add(886);

        System.out.println(getLastDigit(intList));
    }

    public static List<Integer> getLastDigit(List<Integer> intList){
        List<Integer> returnList = new ArrayList<>();
        intList.forEach(x -> returnList.add(x%10));

        return returnList;
    }
}

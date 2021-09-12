package com.javabasics5.Assignment1;

import java.util.ArrayList;
import java.util.List;

public class Assignment1_2 {

    public static void main(String[] args){
        List<Integer> intList = new ArrayList<>();
        intList.add(3);
        intList.add(6);
        intList.add(11);
        intList.add(20);

        System.out.println(intListToString(intList));

    }

    public static String intListToString(List<Integer> intList){
        StringBuilder sb = new StringBuilder();
        intList.forEach(x -> {
            if(x%2 == 0)
                sb.append("e"+x);
            else
                sb.append("o"+x);
            sb.append(",");
        });
        sb.deleteCharAt(sb.length()-1);

        return sb.toString();
    }
}

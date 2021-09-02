package com.javabasics2;

//import java.util.*;

public class Assignment1 {

    public static void main(String[] args){

        if(args.length > 0){
            float sum = 0;
            for (int i=0;i< args.length;i++) {
                try {
                    sum += Float.parseFloat(args[i]);
                }catch(Exception e){
                    System.out.println("Invalid input");
                    e.printStackTrace();
                }
            }
            System.out.println("SUM: " + sum);
        }else {
            System.out.println("no user input!");
        }

    }
}

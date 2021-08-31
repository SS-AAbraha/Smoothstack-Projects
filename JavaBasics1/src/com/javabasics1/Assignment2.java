package com.javabasics1;

import java.util.*;

public class Assignment2 {

    public static void main(String[] args){
        Random rand = new Random();
        Scanner myScanner = new Scanner(System.in);
        int triesRemaining = 5;

        int generatedNum = rand.nextInt(100);
        System.out.println("number:" + generatedNum);

        while(triesRemaining>0) {
            System.out.printf("guess the generated number: ");
            int userInput = myScanner.nextInt();
            triesRemaining--;

            if (userInput > (generatedNum-10) && userInput < (generatedNum+10)){
                System.out.println("Number was " + generatedNum);
                break;
            }
            System.out.println("");
        }

    }
}

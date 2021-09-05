package com.javabasics3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;

// Search a specific character from an input file.
public class Assignment3 {

    public static void main(String[] args){
        String cwd = System.getProperty("user.dir");
        String filename = "input.txt";
        String filepath = cwd +"/"+ filename;
        char charToSearch = GetUserInput();

        if(charToSearch == 0){
            System.out.println("Input Error");
        }else {
            int count = 0;
            try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
                int input;
                do {
                    input = br.read();
                    if (input == charToSearch || input == charToSearch - 32) { // check for lower and uppercase letters
                        count++;
                    }
                } while (input != -1);
            } catch (Exception e) {
                e.printStackTrace();
            }

            System.out.println(count);
        }

    }

    public static char GetUserInput(){
        Scanner myScanner = new Scanner(System.in);
        char c = 'a';
        boolean error = false;

        System.out.printf("insert character to search for: ");
        while(!error) {
            String input = myScanner.next();
            try {
                c = input.toLowerCase().charAt(0);
                int cToInt = (int) c;
                if(122 >= cToInt && cToInt >= 97)
                    return c;
                else
                    System.out.printf("please enter a valid character: ");

            } catch (Exception e) {
                e.printStackTrace();
                error = true;
            }
        }

        return 0;
    }

}


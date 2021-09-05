package com.javabasics3;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

//append to a .txt file
public class Assignment2 {

    public static void main(String[] args) {
        String cwd = System.getProperty("user.dir");
        String filename = cwd + "/input.txt";
        System.out.println(filename);

        try{
            Files.writeString(Paths.get(filename), "\nThis is appeneded", StandardOpenOption.APPEND);
        }catch (Exception e){
            e.printStackTrace();
        }

    }


}

package com.javabasics3;

import java.io.*;
import java.net.URL;

//Read all files in a directory
public class Assignment1 {

    public static void main(String[] args){
        System.out.println();
        String cwd = System.getProperty("user.dir");
        String dirPath = cwd + "/custom dir";

        getAllFiles(dirPath,1);

    }

    //this method will print all files within the given directory and its subdirectories
    public static void getAllFiles(String cwd, int iterationNum){
        File directory = new File(cwd);
        File[] files = directory.listFiles();

        System.out.println("(dir) "+ directory.getName() + ": ");   // prints current directory name

        for(int i=0; i<files.length; i++){
            for(int j=0;j<iterationNum;j++){ // uses whitespace(tab) to organize name printing
                System.out.printf("\t");
            }
            if(files[i].isFile()) {
                System.out.println("(file) " + files[i].getName()); // prints current filename
            }
            if(files[i].isDirectory()) {
                String subDirPath = cwd + "/" + files[i].getName();
//                File subDir = new File(subDirPath);
                getAllFiles(subDirPath, iterationNum+1); // iterates method to print files within the directory
            }
        }
//        System.out.println();
    }
}

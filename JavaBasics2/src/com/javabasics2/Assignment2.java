package com.javabasics2;

public class Assignment2 {

    public static void main(String[] args){
        int[][] array = {{0,1,25,3,40},
                        {10,15,5}};
        int highestVal=0;
        int x=0;
        int y=0;

        for(int i=0;i<array.length;i++){
            for(int j=0;j< array[i].length;j++){
                if(array[i][j] > highestVal){
                    highestVal = array[i][j];
                    x = i;
                    y = j;
                }
            }
        }

        System.out.println("highest number: "+array[x][y]+" \nlocated at: ["+x+","+y+"]");
    }
}
